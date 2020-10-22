package com.company;

/**
 * @author: yansu
 * @date: 2020/10/21
 */
public class Amazon {
    /**
     * dp
     * https://leetcode-cn.com/problems/longest-palindromic-substring/
     */
    public String longestPalindrome(String s) {
        //state is the longest of the given length string
        int len = s.length();
        if (len < 2) return s;
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        // 0 <= i < j < len
        int start = 0;
        int maxLen = 1;
        char[] chars = s.toCharArray();
        for (int j = 1; j < len; j++) {
            for (int i = 0; i < j; i++) {
                if (chars[i] != chars[j]) dp[i][j] = false;
                else {
                    //cannot contract
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else
                        dp[i][j] = dp[i+1][j-1];
                }
                if (dp[i][j]) {
                    if (maxLen < j - i + 1) {
                        maxLen = j - i + 1;
                        start = i;
                    }
                }
            }
        }
        return s.substring(start, start + maxLen);
    }
    //manacher can do O(N)

    //https://leetcode-cn.com/problems/maximum-subarray/
    public int maxSubArray(int[] nums) {
        //dp[i] is subarray ends with i
        //dp[i] can be either the start of a new segment or the end.
        /* dp, O(n) space */
        //if (nums.length == 1) return nums[0];
        //int[] dp = new int[nums.length];
        //dp[0] = nums[0];
        //int res = Integer.MIN_VALUE;
        //for (int i = 0; i < nums.length; i++) {
        //    if (i > 0)
        //        dp[i] = Integer.max(dp[i-1] + nums[i], nums[i]);
        //    res = Integer.max(dp[i], res);
        //}
        //return res;

        if (nums.length == 1) return nums[0];

        int dp_0 = nums[0];
        int dp_i = dp_0;
        int dp_pre = dp_0;
        int res = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (i > 0)
                dp_i = Integer.max(dp_pre + nums[i], nums[i]);
            res = Integer.max(dp_i, res);
            dp_pre = dp_i;
        }
        return res;






    }

    public static void main(String[] args) {
        int[] nums = {1,2};
        new Amazon().maxSubArray(nums);
    }
}
