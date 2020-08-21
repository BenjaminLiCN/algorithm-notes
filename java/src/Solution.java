package com.company;

import java.util.*;
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int v) {
        this.val = v;
    }
}
/*
 * @author: yansu
 * @date: 2020/8/17
 */
public class Solution {
    private void countCharsMap(String s, Map<String, Integer> map) {
        char[] chars = s.toCharArray();
        for (char k: chars) {
            String key = String.valueOf(k);
            if (map.containsKey(key)) {
                map.put(key, (int)map.get(key) + 1);
            } else {
                map.put(key, 0);
            }
        }
    }
    //substring min length
    //public String minWindow(String s, String t) {
    //    Map<String, Integer> needs = new HashMap<>(16), window = new HashMap<>(16);
    //    char[] chars = t.toCharArray();
    //    countCharsMap(t, needs);
    //
    //    int left = 0, right = 0;
    //    int valid = 0;
    //    while (right < s.length()) {
    //        char c = chars[right];
    //        right++;
    //        //window update
    //
    //        while (true) { // while window has matched the needs
    //            char out = chars[left];
    //            left++;
    //            //update window
    //
    //        }
    //    }
    //
    //}




    private List<List<Integer>> res = new ArrayList<>();

    private int leftBoundBinarySearch(int[] nums, int target) {
        int left, right, mid;
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (left >= nums.length || nums[left] != target) return -1;
        return left;
    }
    private int rightBoundBinarySearch(int[] nums, int target) {
        int left, right, mid;
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (right >= nums.length || nums[right] != target) return -1;
        return right;
    }
    public int[] searchRange(int[] nums, int target) {
        //left search
        int leftBound = leftBoundBinarySearch(nums, target);
        int rightBound = rightBoundBinarySearch(nums, target);
        int[] res = new int[2];

        res[0] = leftBound;
        res[1] = rightBound;
        return res;
    }

    public List<List<Integer>> permute(int[] nums) {
        List<Integer> path = new ArrayList<>();
        backtrack(path, nums);
        return res;
    }

    private void backtrack(List<Integer> path, int[] nums) {
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }
        for (int n: nums) {
            if (path.contains(n)) {
                continue;
            }
            path.add(n);
            backtrack(path, nums);
            path.remove(path.size() - 1);
        }
    }

    // F(S)=F(S-C)+1
    private Map<Integer, Integer> coinMap = new HashMap<>(16);
    public int coinChange(int[] coins, int amount) {
        if (coinMap.containsKey(amount)) return coinMap.get(amount);
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int subProblem = coinChange(coins, amount - coin);
            if (subProblem == -1) continue;
            res = Integer.min(subProblem + 1, res);
        }
        res = res == Integer.MAX_VALUE ? -1 : res;
        coinMap.put(amount, res);
        return res;
    }

    //dp table version
    public int coinChange2(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, 99);
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int coin: coins) {
                if (i - coin >= 0) {
                    dp[i] = Integer.min(dp[i - coin] + 1, dp[i]);
                }
            }
        }

        return dp[amount] == 99 ? -1 : dp[amount];

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //        int[] coins = {2};
        //        int num = solution.coinChange2(coins, 3);
        ////        System.out.println(num);
        //        int[] nums = {1,2,3};
        //        List<List<Integer>> permute = solution.permute(nums);
        //        System.out.println("hi");
        int[] nums = {1, 2, 2, 2, 4, 5};
        int[] res = solution.searchRange(nums, 2);
        System.out.println("gekk");
    }
}
