package class51;

import java.util.HashMap;

public class Problem_1035_UncrossedLines {

	// 针对这个题的题意，做的动态规划
	public static int maxUncrossedLines1(int[] A, int[] B) {
		if (A == null || A.length == 0 || B == null || B.length == 0) {
			return 0;
		}
		int N = A.length;
		int M = B.length;
		// dp[i][j]代表: A[0...i]对应B[0...j]最多能划几条线
		int[][] dp = new int[N][M];
		if (A[0] == B[0]) {
			dp[0][0] = 1;
		}
		for (int j = 1; j < M; j++) {
			dp[0][j] = A[0] == B[j] ? 1 : dp[0][j - 1];
		}
		for (int i = 1; i < N; i++) {
			dp[i][0] = A[i] == B[0] ? 1 : dp[i - 1][0];
		}
		// 某个值(key)，上次在A中出现的位置(value)
		HashMap<Integer, Integer> AvalueLastIndex = new HashMap<>();
		AvalueLastIndex.put(A[0], 0);
		// 某个值(key)，上次在B中出现的位置(value)
		HashMap<Integer, Integer> BvalueLastIndex = new HashMap<>();
		for (int i = 1; i < N; i++) {
			AvalueLastIndex.put(A[i], i);
			BvalueLastIndex.put(B[0], 0);
			for (int j = 1; j < M; j++) {
				BvalueLastIndex.put(B[j], j);
				// 可能性1，就是不让A[i]去划线
				int p1 = dp[i - 1][j];
				// 可能性2，就是不让B[j]去划线
				int p2 = dp[i][j - 1];
				// 可能性3，就是要让A[i]去划线，那么如果A[i]==5，它跟谁划线？
				// 贪心的点：一定是在B[0...j]中，尽量靠右侧的5
				int p3 = 0;
				if (BvalueLastIndex.containsKey(A[i])) {
					int last = BvalueLastIndex.get(A[i]);
					p3 = (last > 0 ? dp[i - 1][last - 1] : 0) + 1;
				}
				// 可能性4，就是要让B[j]去划线，那么如果B[j]==7，它跟谁划线？
				// 贪心的点：一定是在A[0...i]中，尽量靠右侧的7
				int p4 = 0;
				if (AvalueLastIndex.containsKey(B[j])) {
					int last = AvalueLastIndex.get(B[j]);
					p4 = (last > 0 ? dp[last - 1][j - 1] : 0) + 1;
				}
				dp[i][j] = Math.max(Math.max(p1, p2), Math.max(p3, p4));
			}
			BvalueLastIndex.clear();
		}
		return dp[N - 1][M - 1];
	}

	// 但是其实这个题，不就是求两个数组的最长公共子序列吗？
	public static int maxUncrossedLines2(int[] A, int[] B) {
		if (A == null || A.length == 0 || B == null || B.length == 0) {
			return 0;
		}
		int N = A.length;
		int M = B.length;
		int[][] dp = new int[N][M];
		dp[0][0] = A[0] == B[0] ? 1 : 0;
		for (int j = 1; j < M; j++) {
			dp[0][j] = A[0] == B[j] ? 1 : dp[0][j - 1];
		}
		for (int i = 1; i < N; i++) {
			dp[i][0] = A[i] == B[0] ? 1 : dp[i - 1][0];
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				int p1 = dp[i - 1][j];
				int p2 = dp[i][j - 1];
				int p3 = A[i] == B[j] ? (1 + dp[i - 1][j - 1]) : 0;
				dp[i][j] = Math.max(p1, Math.max(p2, p3));
			}
		}
		return dp[N - 1][M - 1];
	}

}
