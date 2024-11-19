package org.lyq.greedy.bag;

import java.util.Arrays;

public class KnapsackProblem {
    static class Item {
        int value;
        int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        // 计算单位重量的价值
        double getValuePerWeight() {
            return (double)value / weight;
        }
    }

    // 贪价值
    public static double maximizeValue(Item[] items, int capacity) {
        Arrays.sort(items, (a, b) -> b.value - a.value); // 按价值降序排序
        return fillKnapsack(items, capacity);
    }

    // 贪重量
    public static double minimizeWeight(Item[] items, int capacity) {
        Arrays.sort(items, (a, b) -> a.weight - b.weight); // 按重量升序排序
        return fillKnapsack(items, capacity);
    }

    // 贪单位重量价值
    public static double maximizeValuePerWeight(Item[] items, int capacity) {
        Arrays.sort(items, (a, b) -> Double.compare(b.getValuePerWeight(), a.getValuePerWeight())); // 按单位重量价值降序排序
        return fillKnapsack(items, capacity);
    }

    // 填充背包
    private static double fillKnapsack(Item[] items, int capacity) {
        double totalValue = 0.0;
        for (Item item : items) {
            if (capacity <= 0) {
                break;
            }
            int amount = Math.min(item.weight, capacity);
            totalValue += item.value * ((double)amount / item.weight);
            capacity -= amount;
        }
        return totalValue;
    }

    public static void main(String[] args) {
        Item[] items = new Item[]{new Item(25, 18), new Item(24, 15), new Item(15, 10)};
        int capacity = 20;

        System.out.println("Maximize Value: " + maximizeValue(items, capacity));
        System.out.println("Minimize Weight: " + minimizeWeight(items, capacity));
        System.out.println("Maximize Value-to-Weight Ratio: " + maximizeValuePerWeight(items, capacity));
    }
}
