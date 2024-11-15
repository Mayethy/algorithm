package org.lyq.dp.lcs;

import java.util.*;

/**
 * ClassName: LongestCommonSubsequence
 * Package: org.lyq.dp
 * Description:
 *
 * @author 林宁
 * 2024/11/15 18:16
 */
public class DpLongestCommonSubsequence {

    public Map<String, Object> getLCS(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        // 动态规划计算 LCS 的长度
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // 回溯获取所有的最长公共子序列
        Set<String> result = new HashSet<>();
        backtrack(dp, str1, str2, m, n, new StringBuilder(), result);

        // 构造返回值
        Map<String, Object> output = new HashMap<>();
        output.put("length", dp[m][n]);
        output.put("subsequences", new ArrayList<>(result));
        return output;
    }

    private void backtrack(int[][] dp, String str1, String str2, int i, int j, StringBuilder path, Set<String> result) {
        if (i == 0 || j == 0) {
            result.add(path.reverse().toString());
            path.reverse(); // 复原 path
            return;
        }

        if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
            path.append(str1.charAt(i - 1));
            backtrack(dp, str1, str2, i - 1, j - 1, path, result);
            path.deleteCharAt(path.length() - 1);
        } else {
            if (dp[i - 1][j] == dp[i][j]) {
                backtrack(dp, str1, str2, i - 1, j, path, result);
            }
            if (dp[i][j - 1] == dp[i][j]) {
                backtrack(dp, str1, str2, i, j - 1, path, result);
            }
        }
    }

    // 测试方法
    public static void main(String[] args) {
        DpLongestCommonSubsequence lcsSolver = new DpLongestCommonSubsequence();
        String str1 = "ABCBDAB";
        String str2 = "BDCABA";
        Map<String, Object> result = lcsSolver.getLCS(str1, str2);
        System.out.println("Length: " + result.get("length"));
        System.out.println("LCS: " + result.get("subsequences"));
    }
}