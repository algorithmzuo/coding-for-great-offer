package class22;

// 本题测试链接 : https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
public class Code01_MaximumSumof3NonOverlappingSubarrays {

//	public static int[] maxSumArray1(int[] arr) {
//		int N = arr.length;
//		int[] help = new int[N];
//		// help[i] 子数组必须以i位置结尾的情况下，累加和最大是多少？
//		help[0] = arr[0];
//		for (int i = 1; i < N; i++) {
//			int p1 = arr[i];
//			int p2 = arr[i] + help[i - 1];
//			help[i] = Math.max(p1, p2);
//		}
//		// dp[i] 在0~i范围上，随意选一个子数组，累加和最大是多少？
//		int[] dp = new int[N];
//		dp[0] = help[0];
//		for (int i = 1; i < N; i++) {
//			int p1 = help[i];
//			int p2 = dp[i - 1];
//			dp[i] = Math.max(p1, p2);
//		}
//		return dp;
//	}
//
//	public static int maxSumLenK(int[] arr, int k) {
//		int N = arr.length;
//		// 子数组必须以i位置的数结尾，长度一定要是K，累加和最大是多少？
//		// help[0] help[k-2] ...
//		int sum = 0;
//		for (int i = 0; i < k - 1; i++) {
//			sum += arr[i];
//		}
//		// 0...k-2 k-1 sum
//		int[] help = new int[N];
//		for (int i = k - 1; i < N; i++) {
//			// 0..k-2 
//			// 01..k-1
//			sum += arr[i];
//			help[i] = sum;
//			// i == k-1  
//			sum -= arr[i - k + 1];
//		}
//		// help[i] - > dp[i]  0-..i  K
//
//	}

	public static int[] maxSumOfThreeSubarrays(int[] nums, int k) {
		int N = nums.length;
		int[] range = new int[N];
		int[] left = new int[N];
		int sum = 0;
		for (int i = 0; i < k; i++) {
			sum += nums[i];
		}
		range[0] = sum;
		left[k - 1] = 0;
		int max = sum;
		for (int i = k; i < N; i++) {
			sum = sum - nums[i - k] + nums[i];
			range[i - k + 1] = sum;
			left[i] = left[i - 1];
			if (sum > max) {
				max = sum;
				left[i] = i - k + 1;
			}
		}
		sum = 0;
		for (int i = N - 1; i >= N - k; i--) {
			sum += nums[i];
		}
		max = sum;
		int[] right = new int[N];
		right[N - k] = N - k;
		for (int i = N - k - 1; i >= 0; i--) {
			sum = sum - nums[i + k] + nums[i];
			right[i] = right[i + 1];
			if (sum >= max) {
				max = sum;
				right[i] = i;
			}
		}
		int a = 0;
		int b = 0;
		int c = 0;
		max = 0;
		for (int i = k; i < N - 2 * k + 1; i++) { // 中间一块的起始点 (0...k-1)选不了 i == N-1
			int part1 = range[left[i - 1]];
			int part2 = range[i];
			int part3 = range[right[i + k]];
			if (part1 + part2 + part3 > max) {
				max = part1 + part2 + part3;
				a = left[i - 1];
				b = i;
				c = right[i + k];
			}
		}
		return new int[] { a, b, c };
	}

}
