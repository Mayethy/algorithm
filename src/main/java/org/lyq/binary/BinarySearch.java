package org.lyq.binary;

/**
 * ClassName: BinarySearch
 * Package: org.lyq.binary
 * Description:
 *
 * @author 林宁
 * 2024/11/10 13:54
 */
public class BinarySearch {
    public int binarySearchIterative(int[] array, int target, int left, int right) {

        // 当左指针不超过右指针时循环
        while (left <= right) {
            // 计算中间位置
            int mid = left + (right - left) / 2; // 防止(left + right)可能引起的溢出

            // 检查中间位置的值
            if (array[mid] == target) {
                return mid; // 找到目标值，返回索引
            } else if (array[mid] < target) {
                left = mid + 1; // 目标在右侧子数组
            } else {
                right = mid - 1; // 目标在左侧子数组
            }
        }

        // 未找到目标值
        return -1;
    }

    public int binarySearchRecursive(int[] array, int target, int left, int right) {
        // 基本情况：如果搜索范围无效，则目标不在数组中
        if (left > right) {
            return -1;
        }

        // 计算中间位置
        int mid = left + (right - left) / 2;

        // 检查中间位置的值
        if (array[mid] == target) {
            return mid; // 找到目标值，返回索引
        } else if (array[mid] < target) {
            // 如果目标值大于中间值，则只搜索右半部分
            return binarySearchRecursive(array, target, mid + 1, right);
        } else {
            // 如果目标值小于中间值，则只搜索左半部分
            return binarySearchRecursive(array, target, left, mid - 1);
        }
    }

}
