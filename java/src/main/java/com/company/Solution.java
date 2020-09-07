package com.company;
import java.util.*;

import com.company.ObjectOrientedDesign.LRUCache;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    public TreeNode(int v) {
        this.val = v;
    }
}
class ObjectOrientedDesign {
    // OOD
    //LRU, linked hashmap
    static class LRUCache{
        class Node {
            int key;
            int value;
            Node prev;
            Node next;
            public Node(int key, int value) {
                this.key = key;
                this.value = value;
            }
        }
        private final Node head;
        private final Node tail;
        private int capacity;
        private HashMap<Integer, Node> map;
        public LRUCache(int capacity) {
            map = new HashMap<>(16);
            head = new Node(-1, -1);
            tail = new Node(-1, -1);
            head.next = tail;
            tail.prev = head;
            this.capacity = capacity;
        }

        public Integer get(int key) {
            Node node = map.get(key);
            if (node != null) {
                //delete and insert to head
                delete(node);
                insertHead(node);
            }
            return node == null ? -1 : node.value;
        }

        public void put(int key, int value) {
            //absent, create key append to head
            Node node = map.get(key);
            if (node == null) {
                if (map.size() >= capacity) {
                    //delete the last valid node obsolete
                    Node temp = tail.prev.prev;
                    map.remove(tail.prev.key);
                    temp.next = tail;
                    tail.prev = temp;
                }
                node = new Node(key, value);
                insertHead(node);
                map.put(key, node);
            } else {//present, delete and append to head
                if (head.next != node) {
                    delete(node);
                    insertHead(node);
                }
                node.value = value;
            }
        }

        public void delete(Node node) {
            if (node != null && node.key != -1) {
                Node suc = node.next;
                Node pre = node.prev;
                pre.next = suc;
                suc.prev = pre;
            }
        }
        private void insertHead(Node node) {
            if (node != null) {
                Node temp = head.next;
                head.next = node;
                node.prev = head;
                node.next = temp;
                temp.prev = node;
            }
        }
    }
}
/*
 * @author: yansu
 * @date: 2020/8/17
 */
public class Solution {
    // egg drop
    private int[][] visited;
    public int dpEggDrop(int n, int k) {
        if (k == 0) return k;
        if (k == 1) return n;
        if (visited[n][k] != -1) return visited[n][k];
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            res = Integer.min(
               res , Integer.max(dpEggDrop(i - 1,k - 1), dpEggDrop(n - k, k)) + 1
            ) ; // choice made
        }
        visited[n][k] = res;
        return res;
    }
    public int superEggDrop(int K, int N) {
        visited = new int[N+1][K+1];
        int[] temp = new int[K + 1];
        Arrays.fill(temp, -1);
        Arrays.fill(visited, temp);
        return dpEggDrop(N, K);
    }


    //house robber
    //house robber II circle
    public int robCircle(int[] nums) {
        //1. initial
        int n = nums.length;
        int[] initial = Arrays.copyOfRange(nums, 0, n - 1);
        int[] tail = Arrays.copyOfRange(nums, 1, n);
        return Integer.max(robDpTable(initial), robDpTable(tail));
        //2. tail
    }


    //house robber I top bottom
    public int robDpTable(int[] nums){
        int n = nums.length;
        int dp_i_1 = 0, dp_i_2 = 0;
        int dp = 0;//dp[n] == 0
        for (int i = n-1; i >= 0; i--) {
            dp = Integer.max(dp_i_1, dp_i_2 + nums[i]);
            dp_i_2 = dp_i_1;
            dp_i_1 = dp;
        }
        return dp;
    }
    //bottom up
    private int[] robbed;
    private int dp(int[] nums, int start) {
        if (start >= nums.length) return 0;
        if (robbed[start] != -1) return robbed[start];
        int res = Integer.MIN_VALUE;
        res = Integer.max(dp(nums, start + 1), dp(nums, start + 2) + nums[start]);
        robbed[start] = res;
        return res;
    }
    public int robRecWithMemo(int[] nums){
       int n = nums.length;
       robbed = new int [n];
       Arrays.fill(robbed, -1);
       return dp(nums, 0);
    }


    //stock exchange section
    //1. k = 1, one transaction allowed, state of k is omitted
    public int maxProfit(int[] prices) { //standard framework
        int length = prices.length;
        if (length == 0) return 0;
        int[][] dp = new int[length][2];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                dp[0][0] = 0;
                dp[0][1] = -prices[i];
            } else {
                dp[i][0] = Integer.max(dp[i-1][0], dp[i-1][1] + prices[i]);
                dp[i][1] = Integer.max(dp[i-1][1], dp[i-1][0] - prices[i]);
            }
        }

        return dp[length-1][0];
    }
    public int maxProfit2(int[] prices) { //space O(1)
        int length = prices.length;
        if (length == 0) return 0;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < length; i++) {
            dp_i_0 = Integer.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Integer.max(dp_i_1, -prices[i]);
        }
        return dp_i_0;
    }
    //2. k = +infinity, no limit
    public int maxProfitInf(int[] prices) {
        // because k equals inf, so k is the same as k-1, state of k is omitted
        int length = prices.length;
        if (length == 0) return 0;
        int dp_i_0 = 0, dp_i_1 = Integer.MIN_VALUE;
        for (int i = 0; i < length; i++) {
            int temp = dp_i_0;//only diff
            dp_i_0 = Integer.max(dp_i_0, dp_i_1 + prices[i]);
            dp_i_1 = Integer.max(dp_i_1, temp - prices[i]);//only diff with last problem
        }
        return dp_i_0;
    }
    //3. k = inf with cooldown
    public int maxProfitInfCooldown(int[] prices) {
        int length = prices.length;
        if (length == 0) return 0;
        int[][] dp = new int[length][2];
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                dp[0][0] = 0;
                dp[0][1] = -prices[i];
            } else {
                dp[i][0] = Integer.max(dp[i-1][0], dp[i-1][1] + prices[i]);
                dp[i][1] = Integer.max(dp[i-1][1], dp[i-1][0] - prices[i]);
            }
        }

        return dp[length-1][0];
    }


    private void countCharsMap(String s, Map<String, Integer> map) {
        char[] chars = s.toCharArray();
        for (char k: chars) {
            String key = String.valueOf(k);
            if (map.containsKey(key)) {
                map.put(key, (int)map.get(key) + 1);
            } else {
                map.put(key, 1);
            }
        }
    }

    //substring min length
    public String minWindow(String s, String t) {
        if (s.length() < t.length()) return "";
        Map<String, Integer> needs = new HashMap<>(16), window = new HashMap<>(16);
        char[] chars = s.toCharArray();
        countCharsMap(t, needs);

        int left = 0, right = 0, start = 0;
        int valid = 0, minLen = Integer.MAX_VALUE;// minLen is to filter out cases that never have valid chars
        while (right < s.length()) {
            //update right char to window
            char c = chars[right];
            String key = String.valueOf(c);
            if (needs.containsKey(key)) {
                countCharsMap(key, window);
                if (window.get(key).equals(needs.get(key))) {
                    valid++;
                }
            }
            right++;
            //window update
            while (valid == needs.size()) {
                String out = String.valueOf(chars[left]);
                if (minLen > right - left) {
                    start = left;
                    minLen = right - left;
                }
                if (window.containsKey(out)) {
                    window.put(out, window.get(out) - 1);
                    if (window.get(out) < needs.get(out)) {
                        valid--;
                    }
                }
                left++;
                //update window
            }
        }
        return minLen == Integer.MAX_VALUE ? "" : String.valueOf(chars, start, minLen);
    }




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
        //int[] nums = {1, 2, 2, 2, 4, 5};
        //int[] res = solution.searchRange(nums, 2);
        //System.out.println("gekk");

        //sliding window
        //minWindow
        //String s = "ADOBECODEBANC";
        //String t = "ABC";
        //String output = solution.minWindow("a", "aa");
        //System.out.println(output);
        //LRU cache
        LRUCache cache = new LRUCache(2);
        cache.put(1,1);
        cache.put(2,2);
        cache.put(3,3);
        cache.get(1);
    }
}
