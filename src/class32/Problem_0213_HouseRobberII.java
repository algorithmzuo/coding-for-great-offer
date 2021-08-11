package class32;

public class Problem_0213_HouseRobberII {

	public static int rob(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return Math.max(nums[0], nums[1]);
		}
		int pre2 = nums[0];
		int pre1 = Math.max(nums[0], nums[1]);
		for (int i = 2; i < nums.length - 1; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans1 = pre1;
		pre2 = nums[1];
		pre1 = Math.max(nums[1], nums[2]);
		for (int i = 3; i < nums.length; i++) {
			int tmp = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = tmp;
		}
		int ans2 = pre1;
		return Math.max(ans1, ans2);
	}

}
