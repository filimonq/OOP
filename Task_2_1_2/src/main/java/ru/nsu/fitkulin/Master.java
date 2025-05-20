package ru.nsu.fitkulin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;

/**
 * The Master class is responsible for
 * managing the pool of tasks and distributing them to Worker instances.
 * It listens for connections from Workers, assigns tasks,
 * and collects results to determine if any non-prime numbers are present in the entire dataset.
 */
public class Master {
    private final long[] numbers;
    private final int port;
    private final ConcurrentHashMap<Integer, Task> taskPool = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Long> taskDeadlines = new ConcurrentHashMap<>();
    private final int minChunkSize = 1;
    private final int workerCount;
    private boolean finalResult = false;
    private static final long TASK_TIMEOUT_MS = 1000;
    private final CountDownLatch completionLatch;
    private int workerIndex = 0;

    public Master(long[] numbers, int workerCount, int port, CountDownLatch completionLatch) {
        this.numbers = numbers;
        this.workerCount = workerCount;
        this.port = port;
        this.completionLatch = completionLatch;
        initializeTaskPool();
    }

    private void initializeTaskPool() {
        int chunkSize = Math.max(minChunkSize, numbers.length / workerCount);
        System.out.println("Using chunkSize: " + chunkSize);
        int totalTasks = (int) Math.ceil((double) numbers.length / chunkSize);
        for (int i = 0; i < totalTasks; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);
            long[] chunk = Arrays.copyOfRange(numbers, start, end);
            taskPool.put(i, new Task(i, chunk));
        }
    }

    public void start(Runnable onReady) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            server.setSoTimeout(1000);
            System.out.println("Master started on port " + port);
            onReady.run();
            long endTime = System.currentTimeMillis() + 2000;
            while (System.currentTimeMillis() < endTime) {
                checkTaskTimeouts();
                try (Socket worker = server.accept()) {
                    worker.setSoTimeout(1000);
                    DataInputStream in = new DataInputStream(worker.getInputStream());
                    DataOutputStream out = new DataOutputStream(worker.getOutputStream());

                    String request = in.readUTF();
                    if ("REQUEST_TASKS".equals(request)) {
                        if (allTasksCompleted()) {
                            out.writeUTF("NO_TASKS");
                            out.flush();
                        } else {
                            List<Task> tasks = getNextTasks();
                            if (!tasks.isEmpty()) {
                                out.writeUTF("TASKS");
                                out.writeInt(tasks.size());
                                for (Task task : tasks) {
                                    out.writeInt(task.getId());
                                    out.writeInt(task.getNumbers().length);
                                    for (long num : task.getNumbers()) {
                                        out.writeLong(num);
                                    }
                                }
                                out.flush();
                                for (Task task : tasks) {
                                    taskDeadlines.put(task.getId(), System.currentTimeMillis() + TASK_TIMEOUT_MS);
                                }
                                try {
                                    for (Task task : tasks) {
                                        boolean hasNonPrime = in.readBoolean();
                                        taskDeadlines.remove(task.getId());
                                        submitResult(task.getId(), hasNonPrime);
                                    }
                                } catch (IOException e) {
                                    System.err.println("Worker failed to return results: " + e.getMessage());
                                }
                            } else {
                                out.writeUTF("NO_TASKS");
                                out.flush();
                            }
                        }
                    }
                } catch (SocketTimeoutException e) {
                    //
                } catch (IOException e) {
                    System.err.println("Master IO error: " + e.getMessage());
                }
            }
            synchronized (System.out) {
                System.out.println("Before final result output: finalResult = " + finalResult);
                System.out.println("Final result: " + finalResult);
                System.out.flush();
            }
        } catch (IOException e) {
            System.err.println("Master server error: " + e.getMessage());
        } finally {
            if (server != null) {
                try {
                    server.close();
                    Thread.sleep(300);
                } catch (IOException | InterruptedException e) {
                    System.err.println("Error closing server socket: " + e.getMessage());
                }
            }
            if (completionLatch != null) {
                completionLatch.countDown();
            }
        }
    }

    private void checkTaskTimeouts() {
        long currentTime = System.currentTimeMillis();
        taskDeadlines.forEach((taskId, deadline) -> {
            if (currentTime > deadline) {
                Task task = taskPool.get(taskId);
                if (task != null && !task.isCompleted()) {
                    System.out.println("Task " + taskId + " timed out, marking as free");
                    taskDeadlines.remove(taskId);
                }
            }
        });
    }

    private synchronized List<Task> getNextTasks() {
        List<Task> tasks = new ArrayList<>();
        int totalTasks = taskPool.size();
        if (totalTasks == 0) return tasks;

        int task1Idx = workerIndex % totalTasks;
        int task2Idx = (workerIndex + 1) % totalTasks;
        workerIndex++;

        Task task1 = taskPool.get(task1Idx);
        Task task2 = taskPool.get(task2Idx);

        if (task1 != null && !task1.isCompleted()) tasks.add(task1);
        if (task2 != null && !task2.isCompleted() && task2Idx != task1Idx) tasks.add(task2);

        return tasks;
    }

    private synchronized void submitResult(int taskId, boolean hasNonPrime) {
        if (allTasksCompleted()) {
            System.out.println("Ignoring result for task "
                    + taskId + ": all tasks already completed");
            return;
        }
        Task task = taskPool.get(taskId);
        if (task != null && !task.isCompleted()) {
            task.setResult(hasNonPrime);
            finalResult |= hasNonPrime;
            System.out.println("Task " + taskId + " completed: "
                    + hasNonPrime + ", finalResult: " + finalResult);
        } else {
            System.out.println("Ignoring result for task "
                    + taskId + ": task null or already completed");
        }
    }

    private boolean allTasksCompleted() {
        return taskPool.values().stream().allMatch(Task::isCompleted);
    }
}