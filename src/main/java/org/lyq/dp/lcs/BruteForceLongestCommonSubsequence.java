package org.lyq.dp.lcs;


import java.util.*;

/**
 * ClassName: BruteForceLongestCommonSubsequence
 * Package: org.lyq.dp.lcs
 * Description:
 *
 * @author 林宁
 * 2024/11/15 19:20
 */
public class BruteForceLongestCommonSubsequence {

    public Map<String, Object> getLCS(String str1, String str2) {
        // 获取两个字符串的所有子序列
        Set<String> subsequences1 = generateSubsequences(str1);
        Set<String> subsequences2 = generateSubsequences(str2);

        // 找到所有公共子序列
        Set<String> commonSubsequences = new HashSet<>(subsequences1);
        commonSubsequences.retainAll(subsequences2);

        // 找出最长的公共子序列
        int maxLength = 0;
        Set<String> longestSubsequences = new HashSet<>();
        for (String subsequence : commonSubsequences) {
            if (subsequence.length() > maxLength) {
                maxLength = subsequence.length();
                longestSubsequences.clear();
                longestSubsequences.add(subsequence);
            } else if (subsequence.length() == maxLength) {
                longestSubsequences.add(subsequence);
            }
        }

        // 构造返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("length", maxLength);
        result.put("subsequences", new ArrayList<>(longestSubsequences));
        return result;
    }

    private Set<String> generateSubsequences(String str) {
        Set<String> subsequences = new HashSet<>();
        int n = str.length();
        int totalSubsequences = 1 << n; // 2^n 个子序列

        for (int i = 0; i < totalSubsequences; i++) {
            StringBuilder subsequence = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) {
                    subsequence.append(str.charAt(j));
                }
            }
            subsequences.add(subsequence.toString());
        }
        return subsequences;
    }

    public static void main(String[] args) {
        BruteForceLongestCommonSubsequence lcsSolver = new BruteForceLongestCommonSubsequence();
        String str1 = "ABCBDAB";
        String str2 = "BDCABA";

        Map<String, Object> result = lcsSolver.getLCS(str1, str2);
        System.out.println("Length: " + result.get("length"));
        System.out.println("LCS: " + result.get("subsequences"));
    }

}