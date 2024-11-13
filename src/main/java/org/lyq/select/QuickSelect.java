package org.lyq.select;

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
    private int partition(int[] array, int left, int right) {
        int pivotIndex = left + new Random().nextInt(right - left + 1);
        int pivotValue = array[pivotIndex];
        swap(array, pivotIndex, right); // 将 pivot 移到数组末尾
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            if (array[i] < pivotValue) {
                swap(array, storeIndex, i);
                storeIndex++;
            }
        }
        swap(array, storeIndex, right); // 将 pivot 移回分区点
        return storeIndex;
    }

    // 交换数组中的两个元素
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}
