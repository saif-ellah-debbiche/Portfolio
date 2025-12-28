package org.example.portfolio.services;
import java.util.Arrays;
class Solution {

public int maxSome(int[] nums,int i) {
    if(i>=nums.length) return 0;
    int currentElement=nums[i];
   int currentSum=currentElement;
    int currentIndex=i+1;
   int nexIndex=currentIndex;

   while(currentIndex<nums.length&&nums[currentIndex]==currentElement){
       currentSum+=nums[currentIndex];
           currentIndex+=1;
   }

   while(nexIndex<nums.length&&nums[nexIndex]<=currentElement+1) nexIndex+=1;
   return Math.max(currentSum+maxSome(nums,nexIndex),maxSome(nums,currentIndex));

}
 public static void main(String[] args){
        Solution solution=new Solution();
        int[] nums={2,3,4};
        Arrays.sort(nums);
        System.out.println(solution.maxSome(nums,0));
 }

}