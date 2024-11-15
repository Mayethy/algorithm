package org.lyq.dp;

/**
 * ClassName: MatrixChainMultiplication
 * Package: org.lyq.dp
 * Description:
 *
 * @author 林宁
 * 2024/11/15 18:13
 */
public class MatrixChainMultiplication {

    // 功能：找到矩阵链乘法所需的最小标量乘法次数
    public static int matrixChainOrder(int[] p, int n) {
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

        // 打印最优括号化方案
        printOptimalParens(s, 1, n - 1);

        return m[1][n - 1]; // 返回整个链乘的最小乘法次数
    }

    // 辅助函数：打印最优括号化方案
    private static void printOptimalParens(int[][] s, int i, int j) {
        if (i == j) {
            System.out.print("A" + i); // 单个矩阵直接打印
        } else {
            System.out.print("("); // 开始括号
            printOptimalParens(s, i, s[i][j]); // 递归打印左边部分
            printOptimalParens(s, s[i][j] + 1, j); // 递归打印右边部分
            System.out.print(")"); // 结束括号
        }
    }

    public static void main(String[] args) {
        int[] p = {30, 35, 15, 5, 10, 20, 25}; // 示例矩阵的维度
        int n = p.length; // 矩阵的个数
        System.out.println("最小的乘法次数是 " + matrixChainOrder(p, n)); // 输出最小乘法次数
    }
}