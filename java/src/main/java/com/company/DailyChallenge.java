package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

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
        int[] nums = {8,1,2,2,3};
        new DailyChallenge().smallerNumbersThanCurrent(nums);
    }

}
