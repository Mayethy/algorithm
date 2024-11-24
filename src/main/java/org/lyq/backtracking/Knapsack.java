package org.lyq.backtracking;

import java.util.Arrays;

/**
 * ClassName: Knapsack
 * Package: org.lyq.backtracking
 * Description:
 *
 * @author 林宁
 * 2024/11/23 15:13
 */

public class Knapsack {
    private int capacity;
    private int[] values;
    private int[] weights;
    private int bestValue;
    private int n;

    public Knapsack(int capacity, int[] values, int[] weights) {
        this.capacity = capacity;
        this.values = values;
        this.weights = weights;
        this.n = values.length;
        this.bestValue = 0;
    }

    public int solveWithBound1() {
        int[] include = new int[n];
        branchAndBound(0, 0, 0, include, true);
        return bestValue;
    }

    public int solveWithBound2() {
        int[] include = new int[n];
        branchAndBound(0, 0, 0, include, false);
        return bestValue;
    }

    private void branchAndBound(int i, int profit, int weight, int[] include, boolean useBound1) {
        if (weight > capacity) {
            return;
        }

        if (i == n) {
            if (profit > bestValue) {
                bestValue = profit;
            }
            return;
        }

        // 不包含第i个物品
        branchAndBound(i + 1, profit, weight, include, useBound1);

        // 包含第i个物品
        if (weight + weights[i] <= capacity) {
            include[i] = 1;
            branchAndBound(i + 1, profit + values[i], weight + weights[i], include, useBound1);
            include[i] = 0; // 回溯
        }

        // 使用限界函数1
        if (useBound1 && profit + bound1(i, weight) > bestValue) {
            branchAndBound(i + 1, profit + values[i], weight + weights[i], include, useBound1);
        }

        // 使用限界函数2
        if (!useBound1 && profit + bound2(i, weight) > bestValue) {
            branchAndBound(i + 1, profit + values[i], weight + weights[i], include, useBound1);
        }
    }

    // 限界函数1: 当前价值 + 剩余价值
    private int bound1(int i, int weight) {
        int remainingCapacity = capacity - weight;
        int j = i;
        int bound = 0;
        while (j < n && weights[j] <= remainingCapacity) {
            remainingCapacity -= weights[j];
            bound += values[j];
            j++;
        }
        if (j < n) {
            bound += (remainingCapacity * (double)values[j] / weights[j]);
        }
        return bound;
    }

    // 限界函数2: 贪心思想
    private int bound2(int i, int weight) {
        Item[] items = new Item[n - i];
        for (int j = i; j < n; j++) {
            items[j - i] = new Item(values[j], weights[j]);
        }
        Arrays.sort(items, (a, b) -> Double.compare(b.valuePerWeight(), a.valuePerWeight()));

        int remainingCapacity = capacity - weight;
        int bound = 0;
        for (Item item : items) {
            if (item.weight <= remainingCapacity) {
                bound += item.value;
                remainingCapacity -= item.weight;
            } else {
                bound += (remainingCapacity * item.valuePerWeight());
                break;
            }
        }
        return bound;
    }

    private static class Item {
        int value;
        int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        public double valuePerWeight() {
            return (double) value / weight;
        }
    }

    public static void main(String[] args) {
        int capacity = 20;
        int[] values = {25, 24, 15};
        int[] weights = {18, 15, 10};

        Knapsack knapsack = new Knapsack(capacity, values, weights);

        // 使用限界函数1
        knapsack.bestValue = 0;
        int maxProfit1 = knapsack.solveWithBound1();
        System.out.println("Maximum value with Bound1: " + maxProfit1);

        // 使用限界函数2
        knapsack.bestValue = 0;
        int maxProfit2 = knapsack.solveWithBound2();
        System.out.println("Maximum value with Bound2: " + maxProfit2);
    }
}
