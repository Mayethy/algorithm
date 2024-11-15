package org.lyq.dp.lcs;

import java.util.*;

/**
 * ClassName: LongestCommonSubsequenceBruteForce
 * Package: org.lyq.dp.LCS
 * Description:
 *
 * @author 林宁
 * 2024/11/15 18:22
 */
public class MemoizationLongestCommonSubsequence {

    private Map<String, Set<String>> memo = new HashMap<>();

    public Map<String, Object> getLCS(String str1, String str2) {
        Set<String> lcsSet = lcsHelper(str1, str2, str1.length(), str2.length());
        int length = lcsSet.isEmpty() ? 0 : lcsSet.iterator().next().length();

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("length", length);
        result.put("subsequences", new ArrayList<>(lcsSet));
        return result;
    }

    private Set<String> lcsHelper(String str1, String str2, int m, int n) {
        // 如果已经计算过该状态，直接返回结果
        String key = m + "," + n;
        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        // 基础情况：空字符串时，LCS 为一个空集
        if (m == 0 || n == 0) {
            Set<String> baseResult = new HashSet<>();
            baseResult.add("");
            return baseResult;
        }

        Set<String> result = new HashSet<>();
        if (str1.charAt(m - 1) == str2.charAt(n - 1)) {
            // 当前字符相等，将字符加入每个子结果
            Set<String> subResults = lcsHelper(str1, str2, m - 1, n - 1);
            for (String sub : subResults) {
                result.add(sub + str1.charAt(m - 1));
            }
        } else {
            // 当前字符不相等，分别探索两种可能
            Set<String> result1 = lcsHelper(str1, str2, m - 1, n);
            Set<String> result2 = lcsHelper(str1, str2, m, n - 1);

            // 取长度较长的结果，若相等则合并
            if (!result1.isEmpty() && !result2.isEmpty()) {
                int len1 = result1.iterator().next().length();
                int len2 = result2.iterator().next().length();
                if (len1 > len2) {
                    result.addAll(result1);
                } else if (len1 < len2) {
                    result.addAll(result2);
                } else {
                    result.addAll(result1);
                    result.addAll(result2);
                }
            } else {
                result.addAll(result1);
                result.addAll(result2);
            }
        }

        // 存入备忘录并返回
        memo.put(key, result);
        return result;
    }

    public static void main(String[] args) {
        String X = "ABCBDAB";
        String Y = "BDCABA";

        MemoizationLongestCommonSubsequence lcsSolver = new MemoizationLongestCommonSubsequence();

        Map<String, Object> result = lcsSolver.getLCS(X, Y);
        System.out.println("Length: " + result.get("length"));
        System.out.println("LCS: " + result.get("subsequences"));
    }
}