package class02;

import java.util.Arrays;

// 本题测试链接 : https://leetcode.com/problems/closest-subsequence-sum/
// 本题数据量描述:
// 1 <= nums.length <= 40
// -10^7 <= nums[i] <= 10^7
// -10^9 <= goal <= 10^9
// 通过这个数据量描述可知，需要用到分治，因为数组长度不大
// 而值很大，用动态规划的话，表会爆
public class Code07_ClosestSubsequenceSum {

	public static long[] l = new long[1 << 20];
	public static long[] r = new long[1 << 20];

	public static int minAbsDifference(int[] nums, int goal) {
		if (nums == null || nums.length == 0) {
			return goal;
		}
		int N = nums.length;
		int M = N >> 1;
		int le = process(nums, 0, M, 0, 0, l);
		int re = process(nums, M, N, 0, 0, r);
		long g = goal;
		long ans = Math.abs(g);
		Arrays.sort(l, 0, le);
		Arrays.sort(r, 0, re--);
		for (int i = 0; i < le; i++) {
			long rest = g - l[i];
			while (re > 0 && Math.abs(rest - r[re - 1]) <= Math.abs(rest - r[re])) {
				re--;
			}
			ans = Math.min(ans, Math.abs(rest - r[re]));
		}
		return (int) ans;
	}

	public static int process(int[] nums, int index, int end, long sum, int fill, long[] arr) {
		if (index == end) {
			arr[fill++] = sum;
		} else {
			fill = process(nums, index + 1, end, sum, fill, arr);
			fill = process(nums, index + 1, end, sum + nums[index], fill, arr);
		}
		return fill;
	}

}
