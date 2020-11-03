package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yansu
 * @date: 2020/10/9
 */
public class DpLearner {


    // https://leetcode-cn.com/problems/jump-game-ii/
    public int jump(int[] nums) {
        // greedy [2,3,1,1,4]
        int start = 0;
        int end = 1;
        int steps = 0;
        while (end < nums.length) {
            int incr = 0;
            int maxPos = 0;
            for (int i = start; i < end; i++) {
                maxPos = Integer.max(maxPos, i + nums[i]);
            }
            steps++;
            start = end;
            end = maxPos + 1;
        }
        return steps;
    }

    // https://leetcode-cn.com/problems/jump-game/
    public boolean canJump(int[] nums) {
        int maxLen = 0;
        for (int i = 0; i < nums.length; i++) {
            if (maxLen >= nums.length) return true;
            if (i > maxLen) return false;
            maxLen = Integer.max(maxLen, i + nums[i]);
        }
        return true;
    }
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        if (m == 0) return 0;
        if (obstacleGrid[0][0] == 1) return 0;
        int n = obstacleGrid[0].length;
        if (obstacleGrid[m-1][n-1] == 1) return 0;
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 1) break;
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            if (obstacleGrid[0][i] == 1) break;
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 1) dp[i][j] = 0;
                else dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
    public int minPathSum(int[][] grid) {
        int rows = grid.length;
        if (rows == 0) return 0;
        int cols = grid[0].length;
        int[][] dp = new int[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++) {
                if (i == 0 && j == 0) {dp[i][j] = grid[0][0];}
                else if (i == 0) dp[i][j] = dp[i][j-1] + grid[i][j];
                else if (j == 0) dp[i][j] = dp[i-1][j] + grid[i][j];
                else dp[i][j] = Integer.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        return dp[rows-1][cols-1];
    }
    //https://leetcode-cn.com/problems/triangle/
    private Integer[][] memoTriangle;
    public int minimumTotal(List<List<Integer>> triangle) {
        if (triangle.size() == 0) return 0;
        int rows = triangle.size();
        int cols = triangle.get(0).size();
        memoTriangle = new Integer[rows][cols];
        return dpTriangle(triangle,0,0);
    }

    private int dpTriangle(List<List<Integer>> triangle,int row, int col) {
        if (row == triangle.size()) return 0;
        Integer memoTemp = memoTriangle[row][col];
        if (memoTemp != null) return memoTemp;
        int res = triangle.get(row).get(col) + Integer.min(dpTriangle(triangle, row + 1, col), dpTriangle(triangle, row + 1, col + 1));
        return memoTriangle[row][col] = res;
    }

    private Map<TreeNode, Integer> map = new HashMap<>(16);
    //actions: put, not_put
    ////state: covered not put, put, not covered
    //public int minCameraCover(TreeNode root) {
    //    int[] res = dpCam(root);
    //    return Integer.max(res[0], res[1]);
    //}
    //
    //private int[] dpCam(TreeNode root) {
    //    if (root == null) return new int[]{0,0};
    //    int[] left = dpCam(root.left);
    //    int[] right = dpCam(root.right);
    //    int set = 1 + left[0] + right[0];
    //}

    private int dp3(TreeNode root) {
        if (root == null) return 0;
        Integer val = map.getOrDefault(root, -1);
        if (val != -1) return val;
        int do_rob = root.val + (root.left == null ? 0 : dp3(root.left.left) + dp3(root.left.right)) + (root.right == null ? 0 : dp3(root.right.right) + dp3(root.right.left));
        int do_not_rob = dp3(root.left) + dp3(root.right);
        return Integer.max(do_not_rob, do_rob);
    }

    private int[] memo;
    private int dp1(int[] nums, int start) {
        if (start >= nums.length) return 0;
        if (memo[start] != -1) return memo[start];
        int res = Integer.max(dp1(nums, start + 2) + nums[start], dp1(nums, start+1));
        memo[start] = res;
        return res;
    }
    public int rob(int[] nums) {
        memo = new int[nums.length];
        Arrays.fill(memo, -1);
        return dp1(nums, 0);
    }

    public int robBottomUp(int[] nums) {
        int len = nums.length;
        int[] dp = new int[len + 2];
        int dp_i_1 = 0, dp_i_2 = 0;
        int dp_i = 0;
        for (int i = len - 1; i >= 0; i--) {
            //dp[i] = Integer.max(dp[i+1], dp[i+2] + nums[i]);
            dp_i = Integer.max(dp_i_1, dp_i_2 + nums[i]);
            dp_i_2 = dp_i_1;
            dp_i_1 = dp_i;
        }
        return dp[0];
    }

    //knapsack https://zhuanlan.zhihu.com/p/93857890
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 == 1) return false;
        //find elements that can fit in a knapsack of size sum/2
        boolean[] dp = new boolean[sum / 2 + 1];
        for (int i = 1; i <= nums.length; i++) {
            for (int j = sum / 2; j >= nums[i-1]; j--) {
                dp[j] = dp[j] || dp[j-nums[i-1]];
            }
        }
        return dp[sum/2];
    }
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int i = 1; i <= coins.length; i++) {
            for (int j = coins[i-1]; j <= amount; j++) {
                if(dp[j] - 1 > dp[j - coins[i-1]])
                    dp[j] = 1 + dp[j - coins[i-1]];
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
    public int findTargetSumWays(int[] nums, int S) {
        int sum = Arrays.stream(nums).sum();
        int target = (sum + S) / 2;
        if (S > sum || sum < -S) return 0;
        if ((S + sum) % 2 == 1) return 0;
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= nums.length; i++)
            for (int j = target; j >= nums[i-1]; j--) {
                dp[j] = dp[j] + dp[j-nums[i-1]];
            }
        return dp[target];
    }
    public static void main(String[] args) {
        //01 knapsack
        //dp[i][j] = Integer.max(dp[i-1][j], dp[i-1][j-w[i]] + v[i]);
        //full knapsack
        // dp[i][j] = Integer.max(dp[i-1][j], dp[i][j-w[i]] + v[i]);
        //bounded knapsack n[i], number of i-th item
        //add a for loop
        Integer a = 100;
        Integer b = 100;
        System.out.println(a==b);
        a = 188;
        b = 188;
        System.out.println(a==b);
        int[] nums = {2,3,1,1,4};
        String s = "aabb";
        String front = s.substring(0, s.length() / 2);
        String back = s.substring(s.length()/2 + 1, s.length());
        System.out.println(new DpLearner().jump(nums));
    }
}
