import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
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
        // Tamanho dos cojuntos de dados
        int[] dataSizes = {10, 100, 1000, 10000, 100000};
        //Quantidade de threads
        int[] threadCounts = {1, 2, 4, 8, 16};

        //Execução
        for (int dataSize : dataSizes) {
            //Array gerado randomicamente com o tamanho pré determinado
            int[] originalArray = generateRandomArray(dataSize);
            System.out.println("\nTamanho do conjunto de dados: " + dataSize);

            // Cria um arquivo CSV para cada tamanho de array
            String fileName = "performance_results_" + dataSize + ".csv";
            try (FileWriter csvWriter = new FileWriter(fileName)) {
                // Escreve o cabeçalho do CSV
                csvWriter.append("Algoritmo,NumThreads,Tempo(ns),Tempo(ms)\n");

                // Execuções Seriais
                // Teste de BubbleSort serial
                long timeBubbleSerial = analyzeSerialSort(originalArray, "Bubble Sort Serial", arr -> {
                    BubbleSort.bubbleSort(arr);
                });
                csvWriter.append("Bubble Sort Serial,1," + timeBubbleSerial + "," + (timeBubbleSerial / 1_000_000) + "\n");

                // Teste de CoutingSort serial
                long timeCountingSerial = analyzeSerialSort(originalArray, "Counting Sort Serial", arr -> {
                    CountingSort.countingSort(arr);
                });
                csvWriter.append("Counting Sort Serial,1," + timeCountingSerial + "," + (timeCountingSerial / 1_000_000) + "\n");

                // Teste de MergeSort serial
                long timeMergeSerial = analyzeSerialSort(originalArray, "Merge Sort Serial", arr -> {
                    MergeSort.mergeSort(arr, 0, arr.length - 1);
                });
                csvWriter.append("Merge Sort Serial,1," + timeMergeSerial + "," + (timeMergeSerial / 1_000_000) + "\n");

                // Teste de QuickSort serial
                long timeQuickSerial = analyzeSerialSort(originalArray, "Quick Sort Serial", arr -> {
                    QuickSort.quickSort(arr, 0, arr.length - 1);
                });
                csvWriter.append("Quick Sort Serial,1," + timeQuickSerial + "," + (timeQuickSerial / 1_000_000) + "\n");

                // Execuções Paralelas com variação de threads
                for (int numThreads : threadCounts) {
                    System.out.println("\nThreads usadas: " + numThreads);

                    // Teste de BubbleSort paralelo
                    long timeBubbleParallel = analyzeParallelSort(originalArray, numThreads, "Bubble Sort Paralelo", (arr, threads) -> {
                        ParallelBubbleSort.bubbleSort(arr, threads);
                    });
                    csvWriter.append("Bubble Sort Paralelo," + numThreads + "," + timeBubbleParallel + "," + (timeBubbleParallel / 1_000_000) + "\n");

                    // Teste de CountingSort paralelo
                    long timeCountingParallel = analyzeParallelSort(originalArray, numThreads, "Counting Sort Paralelo", (arr, threads) -> {
                        ParallelCountingSort.countingSort(arr, threads);
                    });
                    csvWriter.append("Counting Sort Paralelo," + numThreads + "," + timeCountingParallel + "," + (timeCountingParallel / 1_000_000) + "\n");

                    // Teste de MergeSort paralelo
                    long timeMergeParallel = analyzeParallelSort(originalArray, numThreads, "Merge Sort Paralelo", (arr, threads) -> {
                        ParallelMergeSort.mergeSort(arr, threads);
                    });
                    csvWriter.append("Merge Sort Paralelo," + numThreads + "," + timeMergeParallel + "," + (timeMergeParallel / 1_000_000) + "\n");

                    // Teste de QuickSort paralelo
                    long timeQuickParallel = analyzeParallelSort(originalArray, numThreads, "Quick Sort Paralelo", (arr, threads) -> {
                        ParallelQuickSort.quickSort(arr, 0, arr.length - 1, threads);
                    });
                    csvWriter.append("Quick Sort Paralelo," + numThreads + "," + timeQuickParallel + "," + (timeQuickParallel / 1_000_000) + "\n");
                }

                csvWriter.flush();
                System.out.println("Resultados salvos em: " + fileName);
            } catch (IOException e) {
                System.out.println("Erro ao escrever o arquivo CSV: " + e.getMessage());
            }
        }
    }

    // Geração do array aleatório com o tamanho definido previamente
    private static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(1000000); // Intervalo de valores aleatórios
        }
        return arr;
    }

    // Execução e contagem de tempo do método serial passado por parâmetro
    private static long analyzeSerialSort(int[] originalArray, String algorithmName, SerialSortAlgorithm sortAlgorithm) {
        int[] arr = originalArray.clone();
        long startTime = System.nanoTime();
        sortAlgorithm.sort(arr);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(algorithmName + " - Tempo: " + duration + " ns (" + (duration / 1_000_000) + " ms)");
        return duration;
    }

    // Execução e contagem de tempo do método paralelo passado por parâmetro
    private static long analyzeParallelSort(int[] originalArray, int numThreads, String algorithmName, ParallelSortAlgorithm sortAlgorithm) {
        int[] arr = originalArray.clone();
        long startTime = System.nanoTime();
        sortAlgorithm.sort(arr, numThreads);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println(algorithmName + " - Tempo: " + duration + " ns (" + (duration / 1_000_000) + " ms)");
        return duration;
    }

    // Definição para poder tipar e passar o método Serial por parâmetro
    @FunctionalInterface
    interface SerialSortAlgorithm {
        void sort(int[] arr);
    }

    // Definição para poder tipar e passar o método Paralelo por parâmetro
    @FunctionalInterface
    interface ParallelSortAlgorithm {
        void sort(int[] arr, int numThreads);
    }
}
