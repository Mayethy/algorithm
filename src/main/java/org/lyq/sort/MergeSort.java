package org.lyq.sort;

import java.util.Arrays;

/**
 * ClassName: MergeSort
 * Package: org.lyq.sort
 * Description:
 *
 * @author 林宁
 * 2024/11/11 11:56
 */
public class MergeSort {

    public static void mergeSort(int[] array) {
        if (array.length < 2) {return;}
        int mid = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        mergeSort(left);
        mergeSort(right);

        merge(array, left, right);
    }

    private static void merge(int[] result, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
    }

}
