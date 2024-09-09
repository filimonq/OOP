package ru.nsu.fitkulin;
import java.util.Scanner;

public class Main {
    static void printArray(int[] arr)
    {
        int N = arr.length;
        for (int j : arr) {
            System.out.print(j + " ");
        }
        System.out.println();
    }
    void HeapSort(int[] arr, int N) {
        for (int i = N / 2 - 1; i >= 0; i--)
        {
            heapify(arr, N, i);  // выносим макс элемент в верх дерева
        }
        for (int i = N - 1; i >= 0; i--)
        {
            int tmp = arr[0]; // меняем макс и мин элементы
            arr[0] = arr[i];
            arr[i] = tmp;
            heapify(arr, i, 0); // находим наиб. элемент
        }

    }
    void heapify(int[] arr, int N, int k)
    {
        int curr = k;                           //        curr         //
        int left = 2 * k + 1;                   //       /    \        //
        int right = 2 * k + 2;                  //    left    right    //
        if (left < N && arr[left] > arr[curr])
        {
            curr = left;
        }
        if (right < N && arr[right] > arr[curr])
        {
            curr = right;
        }
        if (curr != k)
        {
            int tmp = arr[k];
            arr[k] = arr[curr];
            arr[curr] = tmp;
            heapify(arr, N, curr);
        }
    }
    public static void main(String[] args) {

    }
}