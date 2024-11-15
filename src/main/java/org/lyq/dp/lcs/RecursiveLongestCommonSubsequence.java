package org.lyq.dp.lcs;

import java.util.*;

/**
 * ClassName: RecursiveLongestCommonSubsequence
 * Package: org.lyq.dp.lcs
 * Description:
 *
 * @author 林宁
 * 2024/11/15 18:24
 */
public class RecursiveLongestCommonSubsequence {

    public Map<String, Object> getLCS(String str1, String str2) {
        Set<String> lcsSet = new HashSet<>();
        int length = lcsRecursive(str1, str2, str1.length(), str2.length(), "", lcsSet);

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("length", length);
        result.put("subsequences", new ArrayList<>(lcsSet));
        return result;
    }

    // 递归方法求解 LCS 的长度
    private int lcsRecursive(String str1, String str2, int m, int n, String current, Set<String> lcsSet) {
        if (m == 0 || n == 0) {
            // 如果已经到达字符串的末尾，记录子序列
            lcsSet.add(new StringBuilder(current).reverse().toString());
            return current.length();
        }

        if (str1.charAt(m - 1) == str2.charAt(n - 1)) {
            // 如果当前字符相等，将其添加到当前路径，并继续递归
            return lcsRecursive(str1, str2, m - 1, n - 1, current + str1.charAt(m - 1), lcsSet);
        } else {
            // 如果字符不相等，分别递归两个分支
            Set<String> tempSet1 = new HashSet<>();
            Set<String> tempSet2 = new HashSet<>();
            int length1 = lcsRecursive(str1, str2, m - 1, n, current, tempSet1);
            int length2 = lcsRecursive(str1, str2, m, n - 1, current, tempSet2);

            // 取两者的最长结果，并合并子序列集
            if (length1 > length2) {
                lcsSet.addAll(tempSet1);
                return length1;
            } else if (length1 < length2) {
                lcsSet.addAll(tempSet2);
                return length2;
            } else {
                lcsSet.addAll(tempSet1);
                lcsSet.addAll(tempSet2);
                return length1;
            }
        }
    }

    public static void main(String[] args) {
        RecursiveLongestCommonSubsequence lcsSolver = new RecursiveLongestCommonSubsequence();
        String X = "ABCBDAB";
        String Y = "BDCABA";

        Map<String, Object> result = lcsSolver.getLCS(X, Y);
        System.out.println("Length: " + result.get("length"));
        System.out.println("LCS: " + result.get("subsequences"));
    }
}