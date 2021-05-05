package class04;

// 本题测试链接 : https://leetcode.com/problems/maximum-subarray/
public class Code02_SubArrayMaxSum {

	public static int maxSubArray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		for (int i = 0; i < arr.length; i++) {
			cur += arr[i];
			max = Math.max(max, cur);
			cur = cur < 0 ? 0 : cur;
		}
		return max;
	}

	public static int maxSubArray2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		// 上一步，dp的值
		// dp[0]
		int pre = arr[0];
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			pre = Math.max(arr[i], arr[i] + pre);
			max =  Math.max(max, pre);
		}
		return max;
	}

}
