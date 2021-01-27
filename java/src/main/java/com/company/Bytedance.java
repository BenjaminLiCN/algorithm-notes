package com.company;

/**
 * @author: yansu
 * @date: 2020/10/21
 */
public class Bytedance {
    public int maxArea(int[] height) {
        // capacity = minHeight * gap
        //每次选一个最低的开始，剔除已选，匹配剩下任意一个gap最高的，如果gap小于等于上一次的gap直接放弃
        //int res = 0;
        //for (int i = 0; i < height.length; i++) {
        //    for (int j = 0; j < height.length; j++) {
        //        if (j == i) continue;
        //        if (height[j] >= height[i]) res = Integer.max(res, height[i] * Math.abs((j - i)));
        //    }
        //}
        int i = 0, j = height.length - 1;
        int res = j * Integer.min(height[i], height[j]);
        while (i != j) {
            if (height[i] > height[j]) {
                j--;
            } else {
                i++;
            }
            res = Integer.max(res, (j - i) * Integer.min(height[j], height[i]));
        }
        return res;
    }
    public int maxProfit(int[] prices, int k) {
        if (prices.length == 0) return 0;
        if (k > prices.length / 2) return maxProfix_inf(prices);
        int[][] dp = new int[prices.length][2];
        //dp[i][k][0] = if k > 0, max(dp[i-1][k-1][1] + prices[i], dp[i-1][k][0]);
        //dp[i][k][1] = max(dp[i-1][k][1], dp[i-1][k][0] - prices[i])
        for (int i = 0; i < prices.length; i++) {
            for (int t = k; t >= 1; t--) {
                if (i - 1 == -1) {
                    dp[i][0] = 0;
                    dp[i][1] = -prices[i];
                } else {
                    dp[i][0] = Integer.max(dp[i-1][1] + prices[i], dp[i-1][0]);
                    dp[i][1] = Integer.max(dp[i-1][1], dp[i-1][0] - prices[i]);
                }
            }
        }
        return dp[prices.length - 1][0];
    }

    private int maxProfix_inf(int[] prices) {
        int n = prices.length;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int temp = dp_i_0;
            dp_i_0 = Math.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Math.max(dp_i_1, temp - prices[i]);
        }
        return dp_i_0;
    }

    public static void main(String[] args) {
        int[] input = {1,8,6,2,5,4,8,3,7};
        int res = new Bytedance().maxArea(input);
        System.out.println(res);
    }
}
