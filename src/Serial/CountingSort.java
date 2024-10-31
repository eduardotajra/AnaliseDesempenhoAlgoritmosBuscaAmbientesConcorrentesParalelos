package Serial;
public class CountingSort {
    public static void countingSort(int[] arr) {
        int max = findMax(arr);
        int[] count = new int[max + 1];
        int[] output = new int[arr.length];

        for (int num : arr) {
            count[num]++;
        }

        for (int i = 1; i < count.length; i++) {
            count[i] += count[i - 1];
        }

        for (int i = arr.length - 1; i >= 0; i--) {
            output[count[arr[i]] - 1] = arr[i];
            count[arr[i]]--;
        }

        System.arraycopy(output, 0, arr, 0, arr.length);
    }

    private static int findMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }
        return max;
    }
}
