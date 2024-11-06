import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import Parallel.ParallelBubbleSort;
import Parallel.ParallelCountingSort;
import Parallel.ParallelMergeSort;
import Parallel.ParallelQuickSort;
import Serial.BubbleSort;
import Serial.CountingSort;
import Serial.MergeSort;
import Serial.QuickSort;

public class PerformanceAnalyzer {
    public static void main(String[] args) {
        // Tamanhos dos conjuntos de dados
        int[] dataSizes = {10, 100, 1000, 10000, 100000};
        // Quantidade de threads
        int[] threadCounts = {1, 2, 4, 8, 16};

        // Execução
        for (int dataSize : dataSizes) {
            // Array gerado randomicamente com o tamanho pré-determinado
            int[] originalArray = generateRandomArray(dataSize);
            System.out.println("\nTamanho do conjunto de dados: " + dataSize);

            // Cria um arquivo CSV para cada tamanho de array
            String fileName = "performance_results_" + dataSize + ".csv";
            try (FileWriter csvWriter = new FileWriter(fileName)) {
                // Escreve o cabeçalho do CSV
                csvWriter.append("Algoritmo,NumThreads,Tempo(ns),Tempo(ms)\n");

                // Execuções Seriais
                long timeBubbleSerial = analyzeSerialSort(originalArray, "Bubble Sort Serial", arr -> {
                    BubbleSort.bubbleSort(arr);
                });
                csvWriter.append("Bubble Sort Serial,1," + timeBubbleSerial + "," + (timeBubbleSerial / 1_000_000) + "\n");

                long timeCountingSerial = analyzeSerialSort(originalArray, "Counting Sort Serial", arr -> {
                    CountingSort.countingSort(arr);
                });
                csvWriter.append("Counting Sort Serial,1," + timeCountingSerial + "," + (timeCountingSerial / 1_000_000) + "\n");

                long timeMergeSerial = analyzeSerialSort(originalArray, "Merge Sort Serial", arr -> {
                    MergeSort.mergeSort(arr, 0, arr.length - 1);
                });
                csvWriter.append("Merge Sort Serial,1," + timeMergeSerial + "," + (timeMergeSerial / 1_000_000) + "\n");

                long timeQuickSerial = analyzeSerialSort(originalArray, "Quick Sort Serial", arr -> {
                    QuickSort.quickSort(arr, 0, arr.length - 1);
                });
                csvWriter.append("Quick Sort Serial,1," + timeQuickSerial + "," + (timeQuickSerial / 1_000_000) + "\n");

                // Execuções Paralelas com variação de threads
                for (int numThreads : threadCounts) {
                    System.out.println("\nThreads usadas: " + numThreads);

                    ForkJoinPool pool = new ForkJoinPool(numThreads);
                    try {
                        long timeBubbleParallel = analyzeParallelSort(pool, originalArray, "Bubble Sort Paralelo", (arr) -> {
                            ParallelBubbleSort.bubbleSort(arr, numThreads);
                        });
                        csvWriter.append("Bubble Sort Paralelo," + numThreads + "," + timeBubbleParallel + "," + (timeBubbleParallel / 1_000_000) + "\n");

                        long timeCountingParallel = analyzeParallelSort(pool, originalArray, "Counting Sort Paralelo", (arr) -> {
                            ParallelCountingSort.countingSort(arr, numThreads);
                        });
                        csvWriter.append("Counting Sort Paralelo," + numThreads + "," + timeCountingParallel + "," + (timeCountingParallel / 1_000_000) + "\n");

                        long timeMergeParallel = analyzeParallelSort(pool, originalArray, "Merge Sort Paralelo", (arr) -> {
                            ParallelMergeSort.mergeSort(arr, numThreads);
                        });
                        csvWriter.append("Merge Sort Paralelo," + numThreads + "," + timeMergeParallel + "," + (timeMergeParallel / 1_000_000) + "\n");

                        long timeQuickParallel = analyzeParallelSort(pool, originalArray, "Quick Sort Paralelo", (arr) -> {
                            ParallelQuickSort.quickSort(arr, 0, arr.length - 1, numThreads);
                        });
                        csvWriter.append("Quick Sort Paralelo," + numThreads + "," + timeQuickParallel + "," + (timeQuickParallel / 1_000_000) + "\n");
                    } finally {
                        pool.shutdown(); // Fecha explicitamente o pool de threads
                    }
                }

                csvWriter.flush();
                System.out.println("Resultados salvos em: " + fileName);
            } catch (IOException e) {
                System.out.println("Erro ao escrever o arquivo CSV: " + e.getMessage());
            }
        }
    }

    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000000);
        }
        return arr;
    }

    private static long analyzeSerialSort(int[] originalArray, String algorithmName, SerialSortAlgorithm sortAlgorithm) {
        int[] arr = originalArray.clone();
        long startTime = System.nanoTime();
        sortAlgorithm.sort(arr);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(algorithmName + " - Tempo: " + duration + " ns (" + (duration / 1_000_000) + " ms)");
        return duration;
    }

    private static long analyzeParallelSort(ForkJoinPool pool, int[] originalArray, String algorithmName, ParallelSortAlgorithm sortAlgorithm) {
        int[] arr = originalArray.clone();
        long startTime = System.nanoTime();
        pool.submit(() -> sortAlgorithm.sort(arr)).join();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(algorithmName + " - Tempo: " + duration + " ns (" + (duration / 1_000_000) + " ms)");
        return duration;
    }

    @FunctionalInterface
    interface SerialSortAlgorithm {
        void sort(int[] arr);
    }

    @FunctionalInterface
    interface ParallelSortAlgorithm {
        void sort(int[] arr);
    }
}
