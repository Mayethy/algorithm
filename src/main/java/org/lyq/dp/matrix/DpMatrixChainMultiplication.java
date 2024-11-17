package org.lyq.dp.matrix;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: MatrixChainMultiplication
 * Package: org.lyq.dp
 * Description:
 *
 * @author 林宁
 * 2024/11/15 18:13
 */
public class DpMatrixChainMultiplication {

    // 功能：找到矩阵链乘法所需的最小标量乘法次数，并返回括号化顺序
    public static Map<String, Object> matrixChainOrder(int[] p, int n) {
        // m[i][j] 保存计算矩阵 Ai 到 Aj 的乘积所需的最少标量乘法次数
        int[][] m = new int[n][n];
        // s[i][j] 保存最优括号化的分割位置
        int[][] s = new int[n][n];

        // 链长，从2开始，因为长度为1的链不需要计算
        for (int l = 2; l < n; l++) { // l 表示当前考虑的子链长度
            for (int i = 1; i <= n - l; i++) {
                int j = i + l - 1; // 计算子链的结束位置
                m[i][j] = Integer.MAX_VALUE; // 初始化为最大值
                for (int k = i; k < j; k++) { // 尝试所有可能的分割点
                    // q - 当前分割点的成本/标量乘法次数
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) { // 更新最小成本
                        m[i][j] = q;
                        s[i][j] = k; // 记录最优分割点
                    }
                }
            }
        }

        // 生成括号化字符串
        String optimalParenthesization = getOptimalParens(s, 1, n - 1);

        // 使用 Map 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("minCost", m[1][n - 1]); // 最小乘法次数
        result.put("optimalParenthesization", optimalParenthesization); // 最优括号化顺序
        return result;
    }

    // 辅助函数：生成最优括号化字符串
    private static String getOptimalParens(int[][] s, int i, int j) {
        if (i == j) {
            return "A" + i; // 单个矩阵直接返回其标识
        } else {
            String left = getOptimalParens(s, i, s[i][j]); // 左子链的括号化
            String right = getOptimalParens(s, s[i][j] + 1, j); // 右子链的括号化
            return "(" + left + " x " + right + ")"; // 合并字符串
        }
    }

    // 主方法
    public static void main(String[] args) {
        int[] p = {30, 35, 15, 5, 10, 20, 25}; // 示例矩阵的维度
        int n = p.length; // 矩阵的个数

        // 调用方法获取结果
        Map<String, Object> result = matrixChainOrder(p, n);

        // 输出最小乘法次数和括号化字符串
        System.out.println("min: " + result.get("minCost"));
        System.out.println("plan: " + result.get("optimalParenthesization"));
    }
}