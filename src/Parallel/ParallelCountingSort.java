package Parallel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class ParallelCountingSort {
    public static void countingSort(int[] arr, int numThreads) {
        if (arr.length == 0) {
            return; // Retorna imediatamente se o array estiver vazio
        }

        // Encontra o valor máximo no array para definir o tamanho do array de contagem
        int max = findMax(arr);

        // Cria o array de contagem de forma thread-safe
        AtomicIntegerArray count = new AtomicIntegerArray(max + 1);

        // Cria o pool de threads
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Contagem das ocorrências em paralelo
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            executor.execute(() -> count.incrementAndGet(arr[index]));
        }

        // Aguarda todas as threads terminarem
        executor.shutdown();
        while (!executor.isTerminated()) {
            // Espera a conclusão de todas as tarefas
        }

        // Transforma o array de contagem em uma soma cumulativa
        for (int i = 1; i < count.length(); i++) {
            count.set(i, count.get(i) + count.get(i - 1));
        }

        // Cria o array de saída
        int[] output = new int[arr.length];

        // Preenche o array de saída em paralelo
        ExecutorService executorOutput = Executors.newFixedThreadPool(numThreads);
        for (int i = arr.length - 1; i >= 0; i--) {
            int index = i;
            executorOutput.execute(() -> {
                int value = arr[index];
                int position = count.decrementAndGet(value);
                output[position] = value;
            });
        }

        // Aguarda todas as threads terminarem
        executorOutput.shutdown();
        while (!executorOutput.isTerminated()) {
            // Espera a conclusão de todas as tarefas
        }

        // Copia os valores ordenados de volta para o array original
        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    private static int findMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
}
