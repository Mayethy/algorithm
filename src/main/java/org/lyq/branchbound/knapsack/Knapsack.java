package org.lyq.branchbound.knapsack;

import java.util.PriorityQueue;

/**
 * ClassName: Knapsack
 * Package: org.lyq.branchbound.knapsack
 * Description:
 *
 * @author 林宁
 * 2024/11/24 20:01
 */
import java.util.PriorityQueue;

public class Knapsack {
    // 定义物品属性
    private int[] values;  // 物品的价值
    private int[] weights; // 物品的重量
    private int capacity;  // 背包容量
    private int n;         // 物品数量

    // 构造函数
    public Knapsack(int[] values, int[] weights, int capacity) {
        this.values = values;
        this.weights = weights;
        this.capacity = capacity;
        this.n = values.length;
    }

    // 优先级1：结点的价值上界
    public int solveWithUpperBoundPriority() {
        // 优先队列，按价值上界降序排列
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> Double.compare(b.upperBound, a.upperBound));
        // 初始化根节点
        Node root = new Node(0, 0, 0, calculateUpperBound(0, 0, 0));
        pq.offer(root);

        int maxValue = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 如果当前价值已经超过最大价值，则更新
            if (current.currentValue > maxValue) {
                maxValue = current.currentValue;
            }

            // 如果到达叶子结点或上界小于当前最大值，则剪枝
            if (current.index >= n || current.upperBound <= maxValue) {
                continue;
            }

            // 尝试选择当前物品
            if (current.currentWeight + weights[current.index] <= capacity) {
                Node leftChild = new Node(
                        current.index + 1,
                        current.currentWeight + weights[current.index],
                        current.currentValue + values[current.index],
                        calculateUpperBound(current.index + 1, current.currentWeight + weights[current.index], current.currentValue + values[current.index])
                );
                pq.offer(leftChild);
            }

            // 尝试不选择当前物品
            Node rightChild = new Node(
                    current.index + 1,
                    current.currentWeight,
                    current.currentValue,
                    calculateUpperBound(current.index + 1, current.currentWeight, current.currentValue)
            );
            pq.offer(rightChild);
        }

        return maxValue;
    }

    // 优先级2：结点的当前价值
    public int solveWithCurrentValuePriority() {
        // 优先队列，按当前价值降序排列
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> b.currentValue - a.currentValue);
        // 初始化根节点
        Node root = new Node(0, 0, 0, 0);
        pq.offer(root);

        int maxValue = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            // 如果当前价值已经超过最大价值，则更新
            if (current.currentValue > maxValue) {
                maxValue = current.currentValue;
            }

            // 如果到达叶子结点，则剪枝
            if (current.index >= n) {
                continue;
            }

            // 尝试选择当前物品
            if (current.currentWeight + weights[current.index] <= capacity) {
                Node leftChild = new Node(
                        current.index + 1,
                        current.currentWeight + weights[current.index],
                        current.currentValue + values[current.index],
                        0
                );
                pq.offer(leftChild);
            }

            // 尝试不选择当前物品
            Node rightChild = new Node(
                    current.index + 1,
                    current.currentWeight,
                    current.currentValue,
                    0
            );
            pq.offer(rightChild);
        }

        return maxValue;
    }

    // 计算当前节点的价值上界
    private double calculateUpperBound(int index, int currentWeight, int currentValue) {
        if (currentWeight >= capacity) {
            return 0; // 超过容量，无法增加价值
        }

        double upperBound = currentValue;
        int remainingCapacity = capacity - currentWeight;

        // 贪心法估算上界
        for (int i = index; i < n; i++) {
            if (weights[i] <= remainingCapacity) {
                remainingCapacity -= weights[i];
                upperBound += values[i];
            } else {
                upperBound += values[i] * ((double) remainingCapacity / weights[i]);
                break;
            }
        }

        return upperBound;
    }

    // 定义结点类
    private static class Node {
        int index;           // 当前物品索引
        int currentWeight;   // 当前总重量
        int currentValue;    // 当前总价值
        double upperBound;   // 当前结点的价值上界

        public Node(int index, int currentWeight, int currentValue, double upperBound) {
            this.index = index;
            this.currentWeight = currentWeight;
            this.currentValue = currentValue;
            this.upperBound = upperBound;
        }
    }

    // 测试主方法
    public static void main(String[] args) {
        int[] values = {25, 24, 15};
        int[] weights = {18, 15, 10};
        int capacity = 20;

        Knapsack solver = new Knapsack(values, weights, capacity);

        System.out.println("Maximum value 1:" + solver.solveWithUpperBoundPriority());
        System.out.println("Maximum value 2:" + solver.solveWithCurrentValuePriority());
    }
}