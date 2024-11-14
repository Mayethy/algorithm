package org.lyq.recursion.select;

import java.util.Random;

public class QuickSelect {

    // 用于找到数组中第 k 小的元素 (k 从 1 开始计数)
    public int select(int[] array, int k) {
        if (array == null || array.length == 0 || k < 1 || k > array.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        return quickSelect(array, 0, array.length - 1, k - 1);
    }

    // 快速选择算法的实现
    private int quickSelect(int[] array, int left, int right, int k) {
        if (left == right) {
            return array[left];
        }

        int pivotIndex = partition(array, left, right);

        if (k == pivotIndex) {
            return array[k];
        } else if (k < pivotIndex) {
            return quickSelect(array, left, pivotIndex - 1, k);
        } else {
            return quickSelect(array, pivotIndex + 1, right, k);
        }
    }
    // 分区函数
    private static int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;
        return i + 1;
    }

    // 交换数组中的两个元素
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
