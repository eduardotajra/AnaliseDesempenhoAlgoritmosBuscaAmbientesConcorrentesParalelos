import java.util.Arrays;
import Parallel.ParallelBubbleSort;
import Parallel.ParallelCountingSort;
import Parallel.ParallelMergeSort;
import Parallel.ParallelQuickSort;
import Serial.BubbleSort;
import Serial.CountingSort;
import Serial.MergeSort;
import Serial.QuickSort;

public class App {
    public static void main(String[] args) throws Exception {
        int[] arr = {64, 25, 12, 22, 11};
        int numThreads = 4; // Número de threads desejado

        // Criacao de copias dos arrays para manter os testes consistentes
        int[] arrSerialBubble = arr.clone();
        int[] arrSerialCounting = arr.clone();
        int[] arrSerialMerge = arr.clone();
        int[] arrSerialQuick = arr.clone();

        int[] arrParallelBubble = arr.clone();
        int[] arrParallelCounting = arr.clone();
        int[] arrParallelMerge = arr.clone();
        int[] arrParallelQuick = arr.clone();

        // Execucao de Bubble Sort serial
        long start = System.nanoTime();
        BubbleSort.bubbleSort(arrSerialBubble);
        long end = System.nanoTime();
        System.out.println("Bubble Sort Serial: " + Arrays.toString(arrSerialBubble) + " - Tempo: " + (end - start) + " ns");

        // Execucao de Counting Sort serial
        start = System.nanoTime();
        CountingSort.countingSort(arrSerialCounting);
        end = System.nanoTime();
        System.out.println("Counting Sort Serial: " + Arrays.toString(arrSerialCounting) + " - Tempo: " + (end - start) + " ns");

        // Execucao de Merge Sort serial
        start = System.nanoTime();
        MergeSort.mergeSort(arrSerialMerge, 0, arrSerialMerge.length - 1);
        end = System.nanoTime();
        System.out.println("Merge Sort Serial: " + Arrays.toString(arrSerialMerge) + " - Tempo: " + (end - start) + " ns");

        // Execucao de Quick Sort serial
        start = System.nanoTime();
        QuickSort.quickSort(arrSerialQuick, 0, arrSerialQuick.length - 1);
        end = System.nanoTime();
        System.out.println("Quick Sort Serial: " + Arrays.toString(arrSerialQuick) + " - Tempo: " + (end - start) + " ns");

        // Execucao de Bubble Sort paralelo
        start = System.nanoTime();
        ParallelBubbleSort.bubbleSort(arrParallelBubble, numThreads);
        end = System.nanoTime();
        System.out.println("Bubble Sort Paralelo: " + Arrays.toString(arrParallelBubble) + " - Tempo: " + (end - start) + " ns");

        // Execucao de Counting Sort paralelo
        start = System.nanoTime();
        ParallelCountingSort.countingSort(arrParallelCounting, numThreads);
        end = System.nanoTime();
        System.out.println("Counting Sort Paralelo: " + Arrays.toString(arrParallelCounting) + " - Tempo: " + (end - start) + " ns");

        // Execucao de Merge Sort paralelo
        start = System.nanoTime();
        ParallelMergeSort.mergeSort(arrParallelMerge, numThreads);
        end = System.nanoTime();
        System.out.println("Merge Sort Paralelo: " + Arrays.toString(arrParallelMerge) + " - Tempo: " + (end - start) + " ns");

        // Execucao de Quick Sort paralelo
        start = System.nanoTime();
        ParallelQuickSort.quickSort(arrParallelQuick, 0, arrParallelQuick.length - 1, numThreads);
        end = System.nanoTime();
        System.out.println("Quick Sort Paralelo: " + Arrays.toString(arrParallelQuick) + " - Tempo: " + (end - start) + " ns");
    }
}
