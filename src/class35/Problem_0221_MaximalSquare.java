package class35;

public class Problem_0221_MaximalSquare {

	public static int maximalSquare(char[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int N = m.length;
		int M = m[0].length;
		int[][] dp = new int[N + 1][M + 1];
		int max = 0;
		for (int i = 0; i < N; i++) {
			if (m[i][0] == '1') {
				dp[i][0] = 1;
				max = 1;
			}
		}
		for (int j = 1; j < M; j++) {
			if (m[0][j] == '1') {
				dp[0][j] = 1;
				max = 1;
			}
		}
		for (int i = 1; i < N; i++) {
			for (int j = 1; j < M; j++) {
				if (m[i][j] == '1') {
					dp[i][j] = Math.min(
							Math.min(dp[i - 1][j],
									dp[i][j - 1]), 
							dp[i - 1][j - 1]) 
							+ 1;
					max = Math.max(max, dp[i][j]);
				}
			}
		}
		return max * max;
	}

}
