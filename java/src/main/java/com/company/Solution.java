package com.company;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
class UnionFind {
    /*
    *
    * use array to implement the unionfind idea
    *
     */
    // elements count
    private int count;
    // x's parent is parent[x]
    private int[] parent;
    public UnionFind(int n) {
        this.count = n;
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;// let each element points to themselves
        }
    }
    //union operation connects p's parent to q's parent
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;
        //merge two trees
        parent[rootP] = rootQ;
        //or
        //parent[rootQ] = rootP;
        count--;
    }

    //main time complexity here, worst N if the tree is not regulated
    public int find(int x) {
        //until the very top root's parent is root itself
        while(parent[x] != x) {
            parent[x] = parent[parent[x]];// with this optimisation, tc is O(1)
            x = parent[x];
        }
        return x;
    }

    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

}
/*
 * @author: yansu
 * @date: 2020/8/17
 */
public class Solution {
    //https://leetcode-cn.com/problems/binary-tree-cameras/
    public int minCameraCover(TreeNode root) {
        //dp state: set, not set but covered, not set not covered
        //root has camera
        return 0;
    }
    //watched doesnt aware if any children monitor root
    private int dpCam(TreeNode root, boolean placeCam, boolean watched) {
        //if (watched)
        //if (root == null) return 0;
        //if (placeCam) {
        //    int cams = 1 + dpCam(root.left, false, true) + dpCam(root.right, false, true);
        //} else {
        //    if (watched) {
        //        int cams =
        //    } else {
        //
        //    }
        //}
        return 0;
    }

    //https://leetcode-cn.com/problems/path-sum-ii/
    private List<List<Integer>> resOfPathSum;
    private LinkedList<Integer> tempSum;
    public List<List<Integer>> pathSum2(TreeNode root, int sum) {
        resOfPathSum = new ArrayList<>();
        tempSum = new LinkedList<>();
        if (root == null) return resOfPathSum;
        findSumPath(root, sum);
        return resOfPathSum;
    }
    public void findSumPath(TreeNode root, int sum) {
        if (root == null) return;
        tempSum.addLast(root.val);
        if (root.left == null && root.right == null && root.val == sum) {
            resOfPathSum.add(new ArrayList<>(tempSum));
        }
        findSumPath(root.left, sum - root.val);
        findSumPath(root.right, sum - root.val);
        tempSum.removeLast();
    }
    //https://leetcode-cn.com/problems/path-sum-iii/
    public int pathSum(TreeNode root, int sum) {
        if (root == null) return 0;
        return countPath(root, sum) + pathSum(root.left, sum - root.val) + pathSum(root.right, sum - root.val);
    }

    private int countPath(TreeNode root, int sum) {
        if (sum == 0) return 1;
        if (root == null) return 0;
        return countPath(root.left, sum - root.val) + countPath(root.right, sum - root.val);
    }
    //flodfill, minesweeping, color fill
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        //1. floodfill framework
        int origColor = image[sr][sc];
        fill(image, sr, sc, origColor, newColor);
        return image;
        //2. use backtracking to avoid infinite loop when newColor == originalColor
    }
    private void fill(int[][] image, int sr, int sc, int origColor, int newColor) {
        if (!isValid(image, sr, sc)) return;
        if (image[sr][sc] != origColor || image[sr][sc] == -1) return;
        image[sr][sc] = -1;
        fill(image, sr + 1, sc, origColor, newColor);
        fill(image, sr - 1, sc, origColor, newColor);
        fill(image, sr, sc + 1, origColor, newColor);
        fill(image, sr, sc - 1, origColor, newColor);
        image[sr][sc] = newColor;
    }

    private boolean isValid(int[][] image, int sr, int sc) {
        return sr >= 0 && sr < image.length && sc < image[0].length && sc >= 0;
    }

    //minesweep
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        String a = "";
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        } else {
            int mines = nearMines(board, x, y);
            if (mines > 0) {
                board[x][y] = (char) mines;
            } else {
                mineSweepFill(board, x, y);
            }
            return board;
        }
        //mark board
    }

    private void mineSweepFill(char[][] board, int x, int y) {}

    private int nearMines(char[][] board, int x, int y) {
        //int[][] direction = {{0,1},{1,0},{-1,0},{0,-1},{1,1},{1,-1},{-1,1},{-1,-1}};
        //for (int i = 0; i < direction.length; i++) {
        //    int m = direction[i][0] + x;
        //    int n = direction[i][1] + y;
        //    if (board[m][n])
        //}
        return 0;
    }

    // union find problems
    // problem 130, find wrapped area
    public void solve(char[][] board) {
        // see 'O's as children of the same parent, so the 'O's in the middle are not connected to dummy
        if (board.length == 0) return;
        int rows = board.length;
        int cols = board[0].length;
        //extra space reserved for dummy
        UnionFind uf = new UnionFind(rows * cols + 1);
        int dummy = rows * cols;
        // for all cells on the boundary, if 'O', union with dummy
        for (int i = 0; i < rows; i++) {
            if (board[i][0] == 'O') uf.union(i*cols,dummy);
            if (board[i][cols - 1] == 'O') uf.union(i*cols+cols-1,dummy);
        }
        for (int i = 0; i < cols; i++) {
            if (board[0][i] == 'O') uf.union(i, dummy);
            if (board[rows - 1][i] == 'O') uf.union(( rows - 1 ) * cols + i, dummy);
        }
        // the rest that is still 'O', union with adjacent 'O's
        int[][] direction = {{0,1},{1,0},{0,-1},{-1,0}};
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (board[i][j] == 'O') {
                    for (int k = 0; k < 4; k++) {
                        int x = direction[k][0] + i;
                        int y = direction[k][1] + j;
                        if (board[x][y] == 'O') {
                            uf.union(x * cols + y, i * cols + j);
                        }
                    }
                }
            }
        }
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (board[i][j] == 'O')
                    if(!uf.isConnected(i * cols + j, dummy)) {
                        board[i][j] = 'X';
                    }
            }
        }
    }
    //leetcode-cn problem 990
    public boolean equationsPossible(String[] equations) {
        // for all the == equations, union those characters;
        UnionFind uf = new UnionFind(26);
        for (String equation : equations) {
            if (equation.charAt(1) == '=') {
                char p = equation.charAt(0);
                char q = equation.charAt(3);
                uf.union(p - 'a', q - 'a');
            }
        }
        // for all the != equations, if the union already exists, return false
        for (String equation : equations) {
            if (equation.charAt(1) == '!') {
                char p = equation.charAt(0);
                char q = equation.charAt(3);
                if (uf.isConnected(p - 'a', q - 'a')) return false;
            }
        }
        return true;
    }
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
    //house robber III
    /*
    在上次打劫完一条街道之后和一圈房屋后，小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为“根”。 除了“根”之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。 如果两个直接相连的房子在同一天晚上被打劫，房屋将自动报警。计算在不触动警报的情况下，小偷一晚能够盗取的最高金额。

    private int dp3(TreeNode start) {
        if (start == null) return 0;
        int res = Integer.MIN_VALUE;

        //a-bc-defg a-defg b c
        int left = dp3(root.left);
        int right = dp3(root.right);

        return result;
    }
    public int robIII(TreeNode root) {
        if (root == null) return 0;
        return dp3(root);
    }*/

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

        String words = "<span class=\"read-count\">阅读数：641</span>";
        String regex = ".*?(\\d+)(?=</span>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(words);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
