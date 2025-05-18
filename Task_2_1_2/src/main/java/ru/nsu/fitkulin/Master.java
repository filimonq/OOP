package ru.nsu.fitkulin;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Arrays;

public class Master {
    private final long[] numbers;
    private final int port;
    private final ConcurrentHashMap<Integer, Task> taskPool = new ConcurrentHashMap<>();
    private final int minChunkSize = 1;
    private final int workerCount;
    private boolean finalResult = false;

    public Master(long[] numbers, int workerCount, int port) {
        this.numbers = numbers;
        this.workerCount = workerCount;
        this.port = port;
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
                try (Socket worker = server.accept()) {
                    worker.setSoTimeout(1000);
                    DataInputStream in = new DataInputStream(worker.getInputStream());
                    DataOutputStream out = new DataOutputStream(worker.getOutputStream());

                    String request = in.readUTF();
                    if ("REQUEST_TASK".equals(request)) {
                        if (allTasksCompleted()) {
                            out.writeUTF("NO_TASKS");
                            out.flush();
                        } else {
                            Task task = getNextTask();
                            if (task != null) {
                                out.writeUTF("TASK");
                                out.writeInt(task.getId());
                                out.writeInt(task.getNumbers().length);
                                for (long num : task.getNumbers()) {
                                    out.writeLong(num);
                                }
                                out.flush();

                                boolean hasNonPrime = in.readBoolean();
                                submitResult(task.getId(), hasNonPrime);
                            } else {
                                out.writeUTF("NO_TASKS");
                                out.flush();
                            }
                        }
                    }
                } catch (SocketTimeoutException e) {
                    // игнорируем таймаут accept
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
        }
    }

    private synchronized Task getNextTask() {
        for (Task task : taskPool.values()) {
            if (!task.isCompleted()) {
                return task;
            }
        }
        return null;
    }

    private synchronized void submitResult(int taskId, boolean hasNonPrime) {
        if (allTasksCompleted()) {
            System.out.println("Ignoring result for task " + taskId
                    + ": all tasks already completed");
            return;
        }
        Task task = taskPool.get(taskId);
        if (task != null && !task.isCompleted()) {
            task.setResult(hasNonPrime);
            finalResult |= hasNonPrime;
            System.out.println("Task " + taskId + " completed: " + hasNonPrime
                    + ", finalResult: " + finalResult);
        } else {
            System.out.println("Ignoring result for task " + taskId
                    + ": task null or already completed");
        }
    }

    private boolean allTasksCompleted() {
        return taskPool.values().stream().allMatch(Task::isCompleted);
    }
}