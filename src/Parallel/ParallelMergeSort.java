package Parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelMergeSort {
    private static final int THRESHOLD = 1000; // Limite para alternar para execução serial

    public static void mergeSort(int[] arr, int numThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try {
            mergeSortHelper(arr, 0, arr.length - 1, executor, numThreads);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            while (!executor.isTerminated()) {
                // Aguarda a conclusão de todas as tarefas
            }
        }
    }

    private static void mergeSortHelper(int[] arr, int left, int right, ExecutorService executor, int depth) throws Exception {
        if (right - left < THRESHOLD) {
            // Usa a versão serial quando o tamanho do problema é pequeno
            mergeSortSerial(arr, left, right);
        } else {
            int mid = left + (right - left) / 2;

            // Limita a profundidade para evitar sobrecarga de threads
            if (depth > 1) {
                Future<?> leftTask = executor.submit(() -> {
                    try {
                        mergeSortHelper(arr, left, mid, executor, depth / 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                Future<?> rightTask = executor.submit(() -> {
                    try {
                        mergeSortHelper(arr, mid + 1, right, executor, depth / 2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                // Aguarda as duas tarefas terminarem
                leftTask.get();
                rightTask.get();
            } else {
                // Alterna para execução serial se a profundidade for atingida
                mergeSortSerial(arr, left, mid);
                mergeSortSerial(arr, mid + 1, right);
            }

            merge(arr, left, mid, right);
        }
    }

    private static void mergeSortSerial(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortSerial(arr, left, mid);
            mergeSortSerial(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private static void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}



