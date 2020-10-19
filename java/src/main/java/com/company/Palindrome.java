package com.company;

/**
 * @author: yansu
 * @date: 2020/10/14
 */
public class Palindrome {
    // https://leetcode-cn.com/problems/palindrome-partitioning-ii/
    //public int minCut(String s) {
    //    //for every slot, options are cut or not cut
    //    //if this part only one char, then it's okay
    //    int[] dp = new int[s.length()+1];
    //    //dp[n] number of cuts needed for 0-n substring
    //    dp[0] = 0;
    //    dp[1] = 0;
    //    for (int i = 1; i < dp.length; i++) {
    //        dp[i] = dp[i-1] + 1;
    //    }
    //
    //}
    private boolean isPalindrome(String str)
    {
        // Pointers pointing to the beginning
        // and the end of the string
        int i = 0, j = str.length() - 1;
        // While there are characters toc compare
        while (i < j) {
            // If there is a mismatch
            if (str.charAt(i) != str.charAt(j))
                return false;
            // Increment first pointer and
            // decrement the other
            i++;
            j--;
        }
        // Given string is a palindrome
        return true;
    }
    //fold half and compare, time complexity logn
    public boolean isPalindrome(int x) {
        if (x < 0 || x % 10 == 0 && x != 0) return false;
        int lastHalf = 0;
        while (x > lastHalf) {
            int lastDigit = x % 10;
            lastHalf = 10 * lastHalf + lastDigit;
            x = x / 10;
        }
       return lastHalf == x || (x / 10) == lastHalf;

    }
    //longest palindrome

    public static void main(String[] args) {
        new Palindrome().isPalindrome(121);
        long start = System.currentTimeMillis();
        //int length = String.valueOf(1231412456).length();
        int length = (int) StrictMath.log10(1231412456);
        long end = System.currentTimeMillis();
        System.out.println(length + " " + (end - start));

    }
}
