package class52;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Problem_0656_CoinPath {

	// arr 0 -> n-1
	// arr[i] = -1 死了！
	public static int minCost(int[] arr, int jump) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int n = arr.length;
		if (arr[0] == -1 || arr[n - 1] == -1) {
			return -1;
		}
		// dp[i] : 从0位置开始出发，到达i位置的最小代价
		int[] dp = new int[n];
		dp[0] = arr[0];
		for (int i = 1; i < n; i++) {
			dp[i] = Integer.MAX_VALUE;
			if (arr[i] != -1) {
				for (int pre = Math.max(0, i - jump); pre < i; pre++) {
					if (dp[pre] != -1) {
						dp[i] = Math.min(dp[i], dp[pre] + arr[i]);
					}
				}
			}
			dp[i] = dp[i] == Integer.MAX_VALUE ? -1 : dp[i];
		}
		return dp[n - 1];
	}

	public static List<Integer> cheapestJump(int[] arr, int jump) {
		int n = arr.length;
		int[] best = new int[n];
		int[] last = new int[n];
		int[] size = new int[n];
		Arrays.fill(best, Integer.MAX_VALUE);
		Arrays.fill(last, -1);
		best[0] = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] != -1) {
				for (int j = Math.max(0, i - jump); j < i; j++) {
					if (arr[j] != -1) {
						int cur = best[j] + arr[i];
						// 1) 代价低换方案！
						// 2) 代价一样，但是点更多，换方案！
						if (cur < best[i] || (cur == best[i] && size[i] - 1 < size[j])) {
							best[i] = cur;
							last[i] = j;
							size[i] = size[j] + 1;
						}
					}
				}
			}
		}
		List<Integer> path = new LinkedList<>();
		for (int cur = n - 1; cur >= 0; cur = last[cur]) {
			path.add(0, cur + 1);
		}
		return path.get(0) != 1 ? new LinkedList<>() : path;
	}

}
