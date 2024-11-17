package org.lyq.dp.matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: RecursiveMatrixChainMultiplication
 * Package: org.lyq.dp.matrix
 * Description:
 *
 * @author 林宁
 * 2024/11/17 16:05
 */
public class RecursiveMatrixChainMultiplication {

    // 功能：找到矩阵链乘法所需的最小标量乘法次数，并返回括号化顺序
    public static Map<String, Object> matrixChainOrder(int[] p, int n) {
        // 调用递归方法计算最小乘法次数和最优括号化顺序
        Map<String, Object> result = recursiveChainOrder(p, 1, n - 1);

        // 返回最小乘法次数和最优括号化顺序
        return result;
    }

    // 辅助递归函数：计算矩阵链乘法的最小乘法次数和最优括号化顺序
    public static Map<String, Object> recursiveChainOrder(int[] p, int i, int j) {
        // 基本情况：当只有一个矩阵时，乘法次数为 0
        if (i == j) {
            Map<String, Object> baseCase = new HashMap<>();
            baseCase.put("minCost", 0); // 只有一个矩阵时乘法次数为 0
            baseCase.put("parenthesization", "A" + i); // 单个矩阵的标识
            return baseCase;
        }

        int minCost = Integer.MAX_VALUE;
        String optimalParenthesization = "";

        // 枚举所有可能的分割点 k
        for (int k = i; k < j; k++) {
            // 递归计算左子链的最小乘法次数
            Map<String, Object> left = recursiveChainOrder(p, i, k);
            // 递归计算右子链的最小乘法次数
            Map<String, Object> right = recursiveChainOrder(p, k + 1, j);

            // 获取左子链和右子链的最小乘法次数
            int leftMinCost = (int) left.get("minCost");
            int rightMinCost = (int) right.get("minCost");

            // 计算当前分割点的乘法次数
            int cost = leftMinCost + rightMinCost + p[i - 1] * p[k] * p[j];

            // 更新最优解
            if (cost < minCost) {
                minCost = cost;
                optimalParenthesization = "(" + left.get("parenthesization") + " x " + right.get("parenthesization") + ")";
            }
        }

        // 返回当前子链的最小乘法次数和最优括号化顺序
        Map<String, Object> result = new HashMap<>();
        result.put("minCost", minCost);
        result.put("parenthesization", optimalParenthesization);
        return result;
    }

    // 主方法
    public static void main(String[] args) {
        int[] p = {30, 35, 15, 5, 10, 20, 25}; // 示例矩阵的维度
        int n = p.length; // 矩阵的个数

        // 调用递归法计算最小乘法次数和最佳括号化顺序
        Map<String, Object> result = matrixChainOrder(p, n);

        // 输出最小乘法次数和最佳括号化顺序
        System.out.println("min " + result.get("minCost"));
        System.out.println("plan " + result.get("parenthesization"));
    }
}
