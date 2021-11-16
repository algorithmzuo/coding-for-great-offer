package class53;

// 测试链接 : https://leetcode.com/problems/guess-number-higher-or-lower-ii/
public class Code05_GuessNumberHigherOrLowerII {

	// 暴力递归
	public static int getMoneyAmount1(int n) {
		return process1(1, n);
	}

	// 假设现在在L ~ R的范围上, 猜数字
	// 返回：确保获胜的最小现金数，不管答案是哪个数字
	// 注意：所谓的“确保获胜”，以及“不管答案是哪个数字”，意味着你每次永远面临猜错的最差情况！
	public static int process1(int L, int R) {
		// 说明L~R范围，只剩下一个数字了，那不用猜了，获胜了
		if (L == R) {
			return 0;
		}
		// 说明L~R范围，只剩下两个数字了
		// 比如： 5 6
		// 假设永远会遇到最差情况，
		// 那么当然猜5，因为最差情况下，也只需要耗费5的代价，然后就知道了答案是6
		// 不能猜6，因为最差情况下，需要耗费6的代价，然后才知道答案是5
		// 所以当然选代价低的！请深刻理解：每次永远面临猜错的最差情况！
		if (L == R - 1) {
			return L;
		}
		// 如果说明L~R范围，不仅仅两个数字
		// 比如：5 6 7 8 9
		// 首先尝试5，如果最差情况出现，代价为：5 + 6~9范围上的尝试
		// 最后尝试9，如果最差情况出现，代价为：9 + 5~8范围上的尝试
		int ans = Math.min(L + process1(L + 1, R), R + process1(L, R - 1));
		// 进而尝试6，如果最差情况出现，代价为：6 + Max { 5~5范围上的尝试 ，7~9范围上的尝试}
		// 这是因为猜了6，会告诉你，猜高了还是猜低了，所以左右两侧的待定范围，一定会只走一侧
		// 又因为永远会遇到最差情况，所以，一定会走最难受的那一侧，所以是 Max { 5~5范围上的尝试 ，7~9范围上的尝试}
		// 进而尝试7，如果最差情况出现，代价为：7 + Max { 5~6范围上的尝试 ，8~9范围上的尝试}
		// 进而尝试8，如果最差情况出现，代价为：8 + Max { 5~7范围上的尝试 ，9~9范围上的尝试}
		// 所有尝试中，取代价最小值
		for (int M = L + 1; M < R; M++) {
			ans = Math.min(ans, M + Math.max(process1(L, M - 1), process1(M + 1, R)));
		}
		return ans;
	}

	// 上面的暴力递归改动态规划
	// 提交到leetcode上可以直接通过
	public static int getMoneyAmount2(int n) {
		int[][] dp = new int[n + 1][n + 1];
		for (int i = 1; i < n; i++) {
			dp[i][i + 1] = i;
		}
		for (int L = n - 2; L >= 1; L--) {
			for (int R = L + 2; R <= n; R++) {
				dp[L][R] = Math.min(L + dp[L + 1][R], R + dp[L][R - 1]);
				for (int M = L + 1; M < R; M++) {
					dp[L][R] = Math.min(dp[L][R], M + Math.max(dp[L][M - 1], dp[M + 1][R]));
				}
			}
		}
		return dp[1][n];
	}

}
