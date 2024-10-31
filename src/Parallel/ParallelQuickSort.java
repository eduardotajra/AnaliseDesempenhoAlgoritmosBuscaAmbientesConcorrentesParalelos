package Parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelQuickSort {

    public static void quickSort(int[] arr, int low, int high, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {
            quickSortHelper(arr, low, high, executor, numThreads);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            while (!executor.isTerminated()) {
                // Aguarda a conclusão de todas as tarefas
            }
        }
    }

    private static void quickSortHelper(int[] arr, int low, int high, ExecutorService executor, int depth) throws Exception {
        if (low < high) {
            int pi = partition(arr, low, high);

            // Limita a profundidade para evitar sobrecarga de threads
            if (depth > 1) {
                Future<?> leftTask = executor.submit(() -> {
                    try {
                        quickSortHelper(arr, low, pi - 1, executor, depth / 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                Future<?> rightTask = executor.submit(() -> {
                    try {
                        quickSortHelper(arr, pi + 1, high, executor, depth / 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                // Aguarda as duas tarefas terminarem
                leftTask.get();
                rightTask.get();
            } else {
                // Alterna para execução serial quando a profundidade é limitada
                quickSortSerial(arr, low, pi - 1);
                quickSortSerial(arr, pi + 1, high);
            }
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

    private static void quickSortSerial(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortSerial(arr, low, pi - 1);
            quickSortSerial(arr, pi + 1, high);
        }
    }
}
