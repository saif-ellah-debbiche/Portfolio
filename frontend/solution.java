
class Solution {
    public int getMax(int[] nums,int i){
        if(i<0) return  0; 
        int currentValue=nums[i];
        return Math.max(getMax(nums,i-2)+currentValue, getMax(nums,i-1));       
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] nums={2,7,9,6,1};
        System.out.println(s.getMax(nums,nums.length-1));
    }
}