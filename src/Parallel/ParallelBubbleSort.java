package Parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelBubbleSort {
    public static void bubbleSort(int[] arr, int numThreads) {
        int n = arr.length;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = 0; i < n - 1; i++) {
            int finalI = i;
            executor.execute(() -> {
                for (int j = 0; j < n - finalI - 1; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
                }
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Aguarda até que todas as threads terminem
        }
    }
}

