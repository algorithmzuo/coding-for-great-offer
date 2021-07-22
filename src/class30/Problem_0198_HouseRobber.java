package class30;

public class Problem_0198_HouseRobber {

	public static int rob1(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		int N = nums.length;
		int[] dp = new int[N];
		dp[0] = nums[0];
		dp[1] = Math.max(nums[0], nums[1]);
		for (int i = 2; i < N; i++) {
			dp[i] = Math.max(dp[i - 1], nums[i] + dp[i - 2]);
		}
		return dp[N - 1];
	}

	public static int rob2(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		if (nums.length == 1) {
			return nums[0];
		}
		int pre2 = nums[0];
		int pre1 = Math.max(nums[0], nums[1]);
		for (int i = 2; i < nums.length; i++) {
			int cur = Math.max(pre1, nums[i] + pre2);
			pre2 = pre1;
			pre1 = cur;
		}
		return pre1;
	}

}
