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
    public static void main(String[] args) {
        int[] input = {1,8,6,2,5,4,8,3,7};
        int res = new Bytedance().maxArea(input);
        System.out.println(res);
    }
}
