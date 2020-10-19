package com.company.designpattern;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

//Definition for singly-linked list.
class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

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
    public static void main(String[] args) {
        int[] test= {-4,-1,0,3,10};
        int[] ints = new DailyChallenge().sortedSquares(test);
        System.out.println("hello");
        boolean b = new DailyChallenge().backspaceCompare("##c", "a#d#c");
        System.out.println("hello " + b);
    }

}
