package org.lyq.dp.matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: BruteForceMatrixChainMultiplication
 * Package: org.lyq.dp.matrix
 * Description:
 *
 * @author 林宁
 * 2024/11/17 16:03
 */
public class BruteForceMatrixChainMultiplication {


    // 功能：穷举法计算矩阵链乘法的最小乘法次数和括号化顺序
    public static Map<String, Object> matrixChainOrder(int[] p) {
        int n = p.length - 1; // 矩阵数量为 n
        Map<String, Object> result = new HashMap<>();
        int minCost = Integer.MAX_VALUE;
        String optimalParenthesization = "";

        // 使用穷举法尝试所有可能的括号化方案
        for (int i = 1; i < (1 << (n - 1)); i++) {
            int cost = 0;
            StringBuilder parentheses = new StringBuilder();
            boolean isValid = true;

            // 检查当前二进制表示的括号化方案是否有效
            for (int j = 0; j < n - 1; j++) {
                if ((i & (1 << j)) != 0) {
                    if (parentheses.length() > 0 && parentheses.charAt(parentheses.length() - 1) == '(') {
                        isValid = false;
                        break;
                    }
                    parentheses.append("(");
                } else {
                    parentheses.append(")");
                }
            }

            // 如果括号化方案无效，则跳过
            if (!isValid) {
                continue;
            }

            // 计算当前括号化方案的乘法次数
            int openCount = 0;
            for (int j = 0; j < n - 1; j++) {
                if (parentheses.charAt(j) == '(') {
                    openCount++;
                } else {
                    int k = j;
                    while (k < n - 1 && parentheses.charAt(k) == ')') {
                        k++;
                    }
                    int dim1 = p[j];
                    int dim2 = p[k + 1];
                    int dim3 = p[j + 1];
                    cost += dim1 * dim2 * dim3;
                    j = k - 1;
                    openCount--;
                }
            }

            // 更新最小乘法次数和最佳括号化顺序
            if (cost < minCost) {
                minCost = cost;
                optimalParenthesization = parentheses.toString();
            }
        }

        // 使用 Map 返回结果
        result.put("minCost", minCost); // 最小乘法次数
        result.put("optimalParenthesization", optimalParenthesization); // 最优括号化顺序
        return result;
    }

    // 主方法
    public static void main(String[] args) {
        int[] p = {30, 35, 15, 5, 10, 20, 25}; // 示例矩阵的维度

        // 调用穷举法解决问题
        Map<String, Object> result = matrixChainOrder(p);

        // 输出最小乘法次数和括号化顺序
        System.out.println("min: " + result.get("minCost"));
        System.out.println("plan: " + result.get("optimalParenthesization"));
    }
}
