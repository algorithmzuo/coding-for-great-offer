package class43;

import java.util.Arrays;

// 来自360笔试
// 给定一个正数数组arr，长度为n，下标0~n-1
// arr中的0、n-1位置不需要达标，它们分别是最左、最右的位置
// 中间位置i需要达标，达标的条件是 : arr[i-1] > arr[i] 或者 arr[i+1] > arr[i]，哪个都可以
// 你每一步可以进行如下操作：对任何位置的数让其-1
// 你的目的是让arr[1~n-1]都达标
// 返回至少要多少步可以完成这个目标
// 数据规模 : 数组长度 <= 5000，数组中的值<=500
public class Code01_MinusOneTimes {

	public static final int INVALID = Integer.MAX_VALUE;

	// 纯暴力方法，只是为了结果对
	// 时间复杂度极差
	public static int minCost0(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int base = min - n;
		return process0(arr, base, 0);
	}

	public static int process0(int[] arr, int base, int index) {
		if (index == arr.length) {
			for (int i = 1; i < arr.length - 1; i++) {
				if (arr[i - 1] <= arr[i] && arr[i] >= arr[i + 1]) {
					return INVALID;
				}
			}
			return 0;
		} else {
			int ans = INVALID;
			int tmp = arr[index];
			for (int cost = 0; arr[index] >= base; cost++, arr[index]--) {
				int next = process0(arr, base, index + 1);
				if (next != INVALID) {
					ans = Math.min(ans, cost + next);
				}
			}
			arr[index] = tmp;
			return ans;
		}
	}

	// 递归方法，已经把尝试写出
	public static int minCost1(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] += arr.length - min;
		}
		return process1(arr, 1, arr[0], true);
	}

	public static int process1(int[] arr, int index, int pre, boolean preOk) {
		if (index == arr.length - 1) {
			return preOk || pre < arr[index] ? 0 : INVALID;
		}
		int ans = INVALID;
		if (preOk) {
			for (int cur = arr[index]; cur >= 0; cur--) {
				int next = process1(arr, index + 1, cur, cur < pre);
				if (next != INVALID) {
					ans = Math.min(ans, arr[index] - cur + next);
				}
			}
		} else {
			for (int cur = arr[index]; cur > pre; cur--) {
				int next = process1(arr, index + 1, cur, false);
				if (next != INVALID) {
					ans = Math.min(ans, arr[index] - cur + next);
				}
			}
		}
		return ans;
	}

	// 初改动态规划方法，就是参考minCost1，改出来的版本
	public static int minCost2(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			arr[i] += n - min;
		}
		int[][][] dp = new int[n][2][];
		for (int i = 1; i < n; i++) {
			dp[i][0] = new int[arr[i - 1] + 1];
			dp[i][1] = new int[arr[i - 1] + 1];
			Arrays.fill(dp[i][0], INVALID);
			Arrays.fill(dp[i][1], INVALID);
		}
		for (int pre = 0; pre <= arr[n - 2]; pre++) {
			dp[n - 1][0][pre] = pre < arr[n - 1] ? 0 : INVALID;
			dp[n - 1][1][pre] = 0;
		}
		for (int index = n - 2; index >= 1; index--) {
			for (int pre = 0; pre <= arr[index - 1]; pre++) {
				for (int cur = arr[index]; cur > pre; cur--) {
					int next = dp[index + 1][0][cur];
					if (next != INVALID) {
						dp[index][0][pre] = Math.min(dp[index][0][pre], arr[index] - cur + next);
					}
				}
				for (int cur = arr[index]; cur >= 0; cur--) {
					int next = dp[index + 1][cur < pre ? 1 : 0][cur];
					if (next != INVALID) {
						dp[index][1][pre] = Math.min(dp[index][1][pre], arr[index] - cur + next);
					}
				}
			}
		}
		return dp[1][1][arr[0]];
	}

	// 动态规划方法，minCost2 + 枚举优化，改出来的版本
	// 很可惜不是最优解
	// 但是非常有意义
	public static int minCost3(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int min = INVALID;
		for (int num : arr) {
			min = Math.min(min, num);
		}
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			arr[i] += n - min;
		}
		int[][][] dp = new int[n][2][];
		for (int i = 1; i < n; i++) {
			dp[i][0] = new int[arr[i - 1] + 1];
			dp[i][1] = new int[arr[i - 1] + 1];
		}
		for (int p = 0; p <= arr[n - 2]; p++) {
			dp[n - 1][0][p] = p < arr[n - 1] ? 0 : INVALID;
		}
		int[][] best = best(dp, n - 1, arr[n - 2]);
		for (int i = n - 2; i >= 1; i--) {
			for (int p = 0; p <= arr[i - 1]; p++) {
				if (arr[i] < p) {
					dp[i][1][p] = best[1][arr[i]];
				} else {
					dp[i][1][p] = Math.min(best[0][p], p > 0 ? best[1][p - 1] : INVALID);
				}
				dp[i][0][p] = arr[i] <= p ? INVALID : best[0][p + 1];
			}
			best = best(dp, i, arr[i - 1]);
		}
		return dp[1][1][arr[0]];
	}

	public static int[][] best(int[][][] dp, int i, int v) {
		int[][] best = new int[2][v + 1];
		best[0][v] = dp[i][0][v];
		for (int p = v - 1; p >= 0; p--) {
			best[0][p] = dp[i][0][p] == INVALID ? INVALID : v - p + dp[i][0][p];
			best[0][p] = Math.min(best[0][p], best[0][p + 1]);
		}
		best[1][0] = dp[i][1][0] == INVALID ? INVALID : v + dp[i][1][0];
		for (int p = 1; p <= v; p++) {
			best[1][p] = dp[i][1][p] == INVALID ? INVALID : v - p + dp[i][1][p];
			best[1][p] = Math.min(best[1][p], best[1][p - 1]);
		}
		return best;
	}

	// 最终的最优解，贪心
	public static int yeah(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length;
		int[] nums = new int[n + 2];
		nums[0] = Integer.MAX_VALUE;
		nums[n + 1] = Integer.MAX_VALUE;
		for (int i = 0; i < arr.length; i++) {
			nums[i + 1] = arr[i];
		}
		int[] leftCost = new int[n + 2];
		int pre = nums[0];
		int change = 0;
		for (int i = 1; i <= n; i++) {
			change = Math.min(pre - 1, nums[i]);
			leftCost[i] = nums[i] - change + leftCost[i - 1];
			pre = change;
		}
		int[] rightCost = new int[n + 2];
		pre = nums[n + 1];
		for (int i = n; i >= 1; i--) {
			change = Math.min(pre - 1, nums[i]);
			rightCost[i] = nums[i] - change + rightCost[i + 1];
			pre = change;
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 1; i <= n; i++) {
			ans = Math.min(ans, leftCost[i] + rightCost[i + 1]);
		}
		return ans;
	}

	// 为了测试
	public static int[] randomArray(int len, int v) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 7;
		int v = 10;
		int testTime = 100;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len) + 1;
			int[] arr = randomArray(n, v);
			int[] arr0 = copyArray(arr);
			int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int[] arr4 = copyArray(arr);
			int ans0 = minCost0(arr0);
			int ans1 = minCost1(arr1);
			int ans2 = minCost2(arr2);
			int ans3 = minCost3(arr3);
			int ans4 = yeah(arr4);
			if (ans0 != ans1 || ans0 != ans2 || ans0 != ans3 || ans0 != ans4) {
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("功能测试结束");

		System.out.println("性能测试开始");

		len = 10000;
		v = 500;
		System.out.println("生成随机数组长度：" + len + ", 生成随机数组值的范围：[1, " + v + "]");

		int[] arr = randomArray(len, v);
		int[] arr3 = copyArray(arr);
		int[] arrYeah = copyArray(arr);
		long start;
		long end;
		start = System.currentTimeMillis();
		int ans3 = minCost3(arr3);
		end = System.currentTimeMillis();
		System.out.println("minCost3的运行结果: " + ans3 + ", 时间(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		int ansYeah = yeah(arrYeah);
		end = System.currentTimeMillis();
		System.out.println("yeah的运行结果: " + ansYeah + ", 时间(毫秒) : " + (end - start));

		System.out.println("性能测试结束");

	}

}
