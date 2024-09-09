package ru.nsu.fitkulin;

public class Main {
    static int[] HeapSort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);  // выносим макс элемент в верх дерева
        }
        for (int i = n - 1; i >= 0; i--) {
            int tmp = arr[0]; // меняем макс и мин элементы
            arr[0] = arr[i];
            arr[i] = tmp;
            heapify(arr, i, 0); // находим наиб. элемент
        }
        return arr;
    }
    static void heapify(int[] arr, int n, int k) {
        int curr = k;                           //        curr         //
        int left = 2 * k + 1;                   //       /    \        //
        int right = 2 * k + 2;                  //    left    right    //
        if (left < n && arr[left] > arr[curr]) {
            curr = left;
        }
        if (right < n && arr[right] > arr[curr]) {
            curr = right;
        }
        if (curr != k) {
            int tmp = arr[k];
            arr[k] = arr[curr];
            arr[curr] = tmp;
            heapify(arr, n, curr);
        }
    }
    public static void main(String[] args) {

    }
}