package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import com.sun.source.tree.Tree;

// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};

/**
 * @author: yansu
 * @date: 2020/10/15
 */
public class DailyChallenge {
    //{-4,-1,0,3,10}
    public int[] sortedSquares(int[] A) {
        CopyOnWriteArrayList
        //dual pointer as max value is either the first or the last element's square
        int start = 0;
        int end = A.length - 1;
        int[] res = new int[A.length];
        int idx = end;
        while (idx>=0) {
            int tail = A[end] * A[end];
            int head = A[start] * A[start];
            if (tail> head) {
                res[idx] = tail;
                end--;
            } else {
                res[idx] = head;
                start++;
            }
            idx--;
        }
        return res;
    }
    public Node connectDFS(Node root) {
        dfsConnect(root, null);
        return root;
    }
    private void dfsConnect(Node cur, Node next) {
        if (cur == null) return;
        cur.next = next;
        dfsConnect(cur.left, cur.right);
        dfsConnect(cur.right, next == null ? null : next.left);
    }
    public Node connectBFS(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Node pre = null;
            for (int i = 0; i < levelSize; i++) {
                Node member = queue.poll();
                if (pre != null) {
                    pre.next = member;
                }
                pre = member;
                if (member.left != null) queue.offer(member.left);
                if (member.right != null) queue.offer(member.right);
            }
        }
        return root;
    }
    public ListNode removeNthFromEnd(ListNode head, int n) {
        //Stack<ListNode> stack = new Stack<>();
        //ListNode node = head;
        //while (node != null) {
        //    stack.push(node);
        //    node = node.next;
        //}
        //ListNode target = null;
        //while (n > 0) {
        //    ListNode temp = stack.pop();
        //    if (n-- == 1) target = temp;
        //}
        //if (!stack.empty()) {
        //    ListNode pre = stack.pop();
        //    pre.next = target.next;
        //} else {
        //    return target;
        //}
        //return head;
        ListNode dummy = new ListNode(-1);
        ListNode pre = dummy;
        dummy.next = head;
        ListNode left = head, right = head;
        for (int i = 0; i < n; i++) {
            right = right.next;
        }
        while (right != null) {
            left = left.next;
            right = right.next;
            pre = pre.next;
        }
        pre.next = left.next;
        return dummy.next;
    }
    public boolean backspaceCompare(String S, String T) {
        return produceEditedText(S).equals(produceEditedText(T));
    }
    private String produceEditedText(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '#') {
                int size = sb.length();
                if (size > 0)
                    sb.deleteCharAt(size - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    private static void mergeList(ListNode head, ListNode reversed) {
        ListNode merged = head;
        while (reversed != null) {
            ListNode tempNext = merged.next;
            merged.next = reversed;
            ListNode tempReversedNext = reversed.next;
            reversed.next = tempNext;
            merged = tempNext;
            reversed = tempReversedNext;
        }
    }
    // https://leetcode-cn.com/problems/reorder-list/
    public void reorderList(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        int count = 0;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            count++;
        }
        //odd neglects the last node
        boolean isEven = count % 2 == 1;
        ListNode start = isEven ? slow.next : slow;

        //reverse list
        ListNode dummy = new ListNode(-1);
        while (start.next != null) {
            ListNode next = start.next;
            ListNode dummyNext = dummy.next;
            dummy.next = start;
            start.next = dummyNext;
            start = next;
        }
        ListNode reversed;
        if (!isEven) reversed = dummy.next.next;
        else reversed = dummy.next;

        ListNode merged = head;
        while (reversed != null) {
            ListNode tempNext = merged.next;
            merged.next = reversed;
            ListNode tempReversedNext = reversed.next;
            reversed.next = tempNext;
            merged = tempNext;
            reversed = tempReversedNext;
        }

    }

    public boolean isLongPressedName(String name, String typed) {
        if (name.length() > typed.length()) return false;
        if (name.length() == typed.length() && !name.equals(typed)) return false;
        if (name.charAt(0) != typed.charAt(0)) return false;
        int[] typedIdx = extractPos(typed);
        int[] nameIdx = extractPos(name);
        int namePtr = 0;
        //record typed first pos of each unique char
        int[] tightTyped = Arrays.stream(typedIdx).filter(i -> i != 0).toArray();
        int[] tightName = Arrays.stream(nameIdx).filter(i -> i != 0).toArray();
        if (tightName.length != tightTyped.length) return false;
        int sumTyped = 0, sumName = 0;
        for (int i = 0; i < tightName.length; i++) {
            if (tightName[i] > tightTyped[i]) return false;
            if (name.charAt(sumName) != typed.charAt(sumTyped)) return false;
            sumName += tightName[i];
            sumTyped += tightTyped[i];
        }
        return true;
    }

    private int[] extractPos(String typed) {
        int[] typedIdx = new int[typed.length()];
        typedIdx[0] = 1;
        int count = 1;
        for (int i = 1; i < typedIdx.length; i++) {
            if (typed.charAt(i) != typed.charAt(i-1)) {
                typedIdx[i] = 1;
                count = 1;
            } else {
                count++;
                typedIdx[i-count+1] = count;
            }
        }
        return typedIdx;
    }

    //https://leetcode-cn.com/problems/partition-labels/
    public List<Integer> partitionLabels(String S) {
        //char: {firstIdx: ?, lastIdx: ?}; then replace the map array with last idx.
        int[] map = new int[S.length()];
        Map<Character, Integer[]> charsMap = new HashMap<>(16);
        for (int i = 0; i < S.length(); i++) {
            if (charsMap.containsKey(S.charAt(i))) {
                Integer[] rec = charsMap.get(S.charAt(i));
                rec[1] = i;
                charsMap.put(S.charAt(i), rec);
            } else {
                Integer[] rec = new Integer[2];
                rec[0] = i;
                rec[1] = i;
                charsMap.put(S.charAt(i), rec);
            }
        }
        //go through charsmap
        charsMap.forEach((key, value) -> {
            map[value[0]] = value[1];
            map[value[1]] = value[1];
        });
        int start = 0;
        List<Integer> res = new ArrayList<>();
        int end = charsMap.get(S.charAt(start))[1];
        if (end == S.length() - 1) res.add(end+1);
        while (end != S.length() - 1) {
            int max = 0;
            for (int i = start + 1; i <= end; i++) {
                if (map[i] > map[start] && map[i] > max) {
                    end = map[i];
                    max = map[i];
                }
            }
            res.add(end - start + 1);
            start = end + 1;
            if (start == S.length()) return res;
            end = charsMap.get(S.charAt(start))[1];
            if (end == S.length() - 1) {
                res.add(end - start + 1);
                return res;
            }
        }
        return res;
        //biggest last index within the same char

    }

    //https://leetcode-cn.com/problems/how-many-numbers-are-smaller-than-the-current-number/
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] sorted = Arrays.copyOf(nums, nums.length);
        Arrays.sort(sorted);
        Map<Integer, Integer> map = new HashMap<>();
        map.put(sorted[0], 0);
        int sameCount = 1;
        for (int i = 1; i < nums.length; i++) {
            if (sorted[i] == sorted[i - 1]) {
                sameCount++;
            } else {
                map.put(sorted[i], map.get(sorted[i - 1]) + sameCount);
                sameCount = 1;
            }
        }
        int idx = 0;
        for (int n : nums) {
            sorted[idx++] = map.get(n);
        }
        return sorted;

    }

    //iterative solution
    public List<Integer> preorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        while (root != null || !stack.empty()) {
            while (root != null) {
                res.add(root.val);
                stack.push(root);
                root = root.left;
            }
            if (root == null && !stack.empty()) {
                root = stack.pop().right;
            }

        }
        return res;
    }
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        while (root != null || !stack.empty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.empty()) {
                TreeNode mid = stack.pop();
                res.add(mid.val);
                root = mid.right;
            }
        }
        return res;
    }
    public List<Integer> postorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        TreeNode pre = null;
        while (root != null || !stack.empty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.peek();
            if (pre == root.right || root.right == null) {
                pre = root;
                res.add(stack.pop().val);
                root = null;
            } else {
                root = root.right;
            }
        }
        return res;
    }
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : arr) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        return map.size() == new HashSet<>(map.values()).size();

    }

    public int sumNumbers(TreeNode root) {
        if (root == null) return 0;
        backtrack(root, 0);
        return res;

    }
    private int res = 0;
    private void backtrack(TreeNode root, int sum) {
        if (root == null) return;
        sum = 10 * sum + root.val;
        if (root.left == null && root.right == null) {
            res += sum;
            return;
        }
        backtrack(root.left, sum);
        backtrack(root.right, sum);
    }
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set1 = new HashSet<>();
        for (int n : nums1) {
            set1.add(n);
        }
        HashSet<Integer> set2 = new HashSet<>();
        for (int n : nums2) {
            set2.add(n);
        }
        Set<Integer> res = new HashSet<>();
        for (int e : set1) {
            if (set2.contains(e)) {
                res.add(e);
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();

    }

    public boolean validMountainArray(int[] A) {
        if (A.length < 3) return false;
        int start = A.length / 2;
        while (start + 1 < A.length && A[start] <= A[start + 1]) {
            if (A[start] == A[start + 1]) return false;
            start++;
        }
        while (start > 0 && A[start] <= A[start - 1]) {
            if (A[start] == A[start - 1]) return false;
            start--;
        }
        if (start == A.length - 1 || start == 0) {
            return false;
        }
        int left = start - 1, right = start + 1;
        while (left != 0 || right != A.length - 1) {
            if (left > 0) {
                if (A[left - 1] >= A[left]) return false;
                left--;
            }
            if (right + 1 < A.length) {
                if (A[right+1] >= A[right]) return false;
                right++;
            }
        }
        return true;
    }
    public int count(TreeNode root) {
        if (root == null) return 0;
        return 1 + count(root.right) + count(root.left);
    }

    public int[][] kClosest(int[][] points, int K) {
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[0] * o2[0] +o2[1] * o2[1] - o1[0] * o1[0] - o1[1] * o1[1]);
        for (int[] pos : points) {
            if (pq.size() < K)
                pq.offer(pos);
            else if (pq.comparator().compare(pos, pq.peek()) > 0) {
                pq.poll();
                pq.offer(pos);
            }
        }
        return pq.toArray(new int[][]{});

    }
    //1234
    //1243
    //1324
    //1342
    //1423
    //1432
    //2134
    //231
    //312
    public void nextPermutation(int[] nums) {
        // back to front search for first ascending pair, swap, if none found, sort
        int minPos = -1;
        for (int i = nums.length - 1; i >= 0; i--) {
            if (i != 0) {
                if (nums[i] > nums[i-1]) {
                    //record a min
                    for (int j = nums.length - 1; j >= i; j--) {
                        if (nums[j] > nums[i-1]) {
                            minPos = j;
                            break;
                        }
                    }
                    swapPos(nums, i-1, minPos);
                    Arrays.sort(nums, i, nums.length);
                    return;
                }
            } else {
                Arrays.sort(nums);
            }
        }
    }
    private void swapPos(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    //public int[][] insert(int[][] intervals, int[] newInterval) {
    //    if (intervals.length == 0) return intervals;
    //    //right/left < all intervals, left in middle, left > all intervals
    //    int left = newInterval[0], right = newInterval[1];
    //    if (right < intervals[0][0] || left > intervals[intervals.length - 1][1] ) {
    //        return intervals;
    //    }
    //    //1. left is the new lower boundry
    //    //2. left is not
    //    //3. right is
    //    //4. right is not
    //    //5. left-right has not intersection
    //    //leftPos[0] is whether in bound
    //    //leftPos[1] is index of interval
    //    int[] leftPos = new int[2];
    //
    //}[4,2,5,7]
    public int[] sortArrayByParityII(int[] A) {
        int cur = 0, it = 0;
        while (cur < A.length) {
            if (isGood(A, cur, cur)) {
                cur++;
                it = cur;
            } else {
                while (!isGood(A, cur, it)) {
                    it++;
                }
                int temp = A[cur];
                A[cur] = A[it];
                A[it] = temp;
            }
        }
        return A;
    }
    private boolean isGood(int[] A, int cur, int it) {
        return (A[it] & 1) == 0 && (cur & 1) == 0 || (A[it] & 1) == 1 && (cur & 1) == 1;
    }


    public static void main(String[] args) {
        //int[] test= {-4,-1,0,3,10};
        //int[] ints = new DailyChallenge().sortedSquares(test);
        //System.out.println("hello");
        //boolean b = new DailyChallenge().backspaceCompare("##c", "a#d#c");
        //System.out.println("hello " + b);
        ListNode start = new ListNode(1);
        ListNode head = start;
        start.next=new ListNode(2);
        start.next.next =new ListNode(3);
        start.next.next.next =new ListNode(4);
        start.next.next.next.next =new ListNode(5);
        //while (start != null) {
        //    System.out.println(start.val);
        //    start = start.next;
        //}
        //ListNode dummy = new ListNode(-1);
        //while (head.next != null) {
        //    ListNode next = head.next;
        //    ListNode dummyNext = dummy.next;
        //    dummy.next = head;
        //    head.next = dummyNext;
        //    head = next;
        //}
        //
        //ListNode a = new ListNode(1);
        //a.next = new ListNode(2);
        //ListNode b = new ListNode(3);
        //b.next = new ListNode(4);
        //
        //mergeList(a, b);
        //dummy = a;
        //while (dummy != null) {
        //    System.out.println(dummy.val);
        //    dummy = dummy.next;
        //}
        int[] nums = {4,2,5,7};
        int[][] input = {{-5, 4}, {-6, -5}, {4, 6}};
        new DailyChallenge().sortArrayByParityII(nums);
    }

}
