package class14;

import java.util.Arrays;
import java.util.HashSet;

public class Code05_SmallestUnFormedSum {

	public static int unformedSum1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 1;
		}
		HashSet<Integer> set = new HashSet<Integer>();
		process(arr, 0, 0, set);
		int min = Integer.MAX_VALUE;
		for (int i = 0; i != arr.length; i++) {
			min = Math.min(min, arr[i]);
		}
		for (int i = min + 1; i != Integer.MIN_VALUE; i++) {
			if (!set.contains(i)) {
				return i;
			}
		}
		return 0;
	}

	public static void process(int[] arr, int i, int sum, HashSet<Integer> set) {
		if (i == arr.length) {
			set.add(sum);
			return;
		}
		process(arr, i + 1, sum, set);
		process(arr, i + 1, sum + arr[i], set);
	}

	public static int unformedSum2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 1;
		}
		int sum = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i != arr.length; i++) {
			sum += arr[i];
			min = Math.min(min, arr[i]);
		}
		// boolean[][] dp ...
		int N = arr.length;
		boolean[][] dp = new boolean[N][sum + 1];
		for (int i = 0; i < N; i++) {// arr[0..i] 0
			dp[i][0] = true;
		}
		dp[0][arr[0]] = true;
		for (int i = 1; i < N; i++) {
			for (int j = 1; j <= sum; j++) {
				dp[i][j] = dp[i - 1][j] || ((j - arr[i] >= 0) ? dp[i - 1][j - arr[i]] : false);
			}
		}
		for (int j = min; j <= sum; j++) {
			if (!dp[N - 1][j]) {
				return j;
			}
		}
		return sum + 1;
	}

	// 已知arr中肯定有1这个数
	public static int unformedSum3(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		Arrays.sort(arr); // O (N * logN)
		int range = 1;
		// arr[0] == 1
		for (int i = 1; i != arr.length; i++) {
			if (arr[i] > range + 1) {
				return range + 1;
			} else {
				range += arr[i];
			}
		}
		return range + 1;
	}

	public static int[] generateArray(int len, int maxValue) {
		int[] res = new int[len];
		for (int i = 0; i != res.length; i++) {
			res[i] = (int) (Math.random() * maxValue) + 1;
		}
		return res;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int len = 27;
		int max = 30;
		int[] arr = generateArray(len, max);
		printArray(arr);
		long start = System.currentTimeMillis();
		System.out.println(unformedSum1(arr));
		long end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		start = System.currentTimeMillis();
		System.out.println(unformedSum2(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");
		System.out.println("======================================");

		System.out.println("set arr[0] to 1");
		arr[0] = 1;
		start = System.currentTimeMillis();
		System.out.println(unformedSum3(arr));
		end = System.currentTimeMillis();
		System.out.println("cost time: " + (end - start) + " ms");

	}
}
