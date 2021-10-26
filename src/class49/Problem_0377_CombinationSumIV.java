package class49;

import java.util.Arrays;

public class Problem_0377_CombinationSumIV {

	// 当前剩下的值是rest，
	// nums中所有的值，都可能作为分解rest的，第一块！全试一试
	// nums, 无重复，都是正
	// rest,
	public static int ways(int rest, int[] nums) {
		if (rest < 0) {
			return 0;
		}
		if (rest == 0) {
			return 1;
		}
		int ways = 0;
		for (int num : nums) {
			ways += ways(rest - num, nums);
		}
		return ways;
	}

	public static int[] dp = new int[1001];

	public static int combinationSum41(int[] nums, int target) {
		Arrays.fill(dp, 0, target + 1, -1);
		return process1(nums, target);
	}

	public static int process1(int[] nums, int rest) {
		if (rest < 0) {
			return 0;
		}
		if (dp[rest] != -1) {
			return dp[rest];
		}
		int ans = 0;
		if (rest == 0) {
			ans = 1;
		} else {
			for (int num : nums) {
				ans += process1(nums, rest - num);
			}
		}
		dp[rest] = ans;
		return ans;
	}

	// 剪枝 + 严格位置依赖的动态规划
	public static int combinationSum42(int[] nums, int target) {
		Arrays.sort(nums);
		int[] dp = new int[target + 1];
		dp[0] = 1;
		for (int rest = 1; rest <= target; rest++) {
			for (int i = 0; i < nums.length && nums[i] <= rest; i++) {
				dp[rest] += dp[rest - nums[i]];
			}
		}
		return dp[target];
	}

}
