package class01;

public class Code05_LongestIncreasingPath {

	public static int longestIncreasingPath1(int[][] matrix) {
		int ans = 0;
		int N = matrix.length;
		int M = matrix[0].length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ans = Math.max(ans, process1(matrix, i, j));
			}
		}
		return ans;
	}

	// 从m[i][j]开始走，走出来的最长递增链，返回！
	public static int process1(int[][] m, int i, int j) {
		int up = i > 0 && m[i][j] < m[i - 1][j] ? process1(m, i - 1, j) : 0;
		int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? process1(m, i + 1, j) : 0;
		int left = j > 0 && m[i][j] < m[i][j - 1] ? process1(m, i, j - 1) : 0;
		int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? process1(m, i, j + 1) : 0;
		return Math.max(Math.max(up, down), Math.max(left, right)) + 1;
	}

	public static int longestIncreasingPath2(int[][] matrix) {
		int ans = 0;
		int N = matrix.length;
		int M = matrix[0].length;
		int[][] dp = new int[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ans = Math.max(ans, process2(matrix, i, j, dp));
			}
		}
		return ans;
	}

	// 从m[i][j]开始走，走出来的最长递增链，返回！
	public static int process2(int[][] m, int i, int j, int[][] dp) {
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		// (i,j)不越界
		int up = i > 0 && m[i][j] < m[i - 1][j] ? process2(m, i - 1, j, dp) : 0;
		int down = i < (m.length - 1) && m[i][j] < m[i + 1][j] ? process2(m, i + 1, j, dp) : 0;
		int left = j > 0 && m[i][j] < m[i][j - 1] ? process2(m, i, j - 1, dp) : 0;
		int right = j < (m[0].length - 1) && m[i][j] < m[i][j + 1] ? process2(m, i, j + 1, dp) : 0;
		int ans = Math.max(Math.max(up, down), Math.max(left, right)) + 1;
		dp[i][j] = ans;
		return ans;
	}

}
