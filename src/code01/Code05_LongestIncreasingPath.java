package code01;

public class Code05_LongestIncreasingPath {

	public static int maxPath(int[][] matrix) {
		int ans = Integer.MIN_VALUE;
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[0].length; col++) {
				ans = Math.max(ans, process(matrix, row, col));
			}
		}
		return ans;
	}

	// 假设在matrix中，从i行，j列出发，能走出的最长递增路径，返回最长递增路径的长度
	public static int process(int[][] matrix, int i, int j) {
		if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length) {
			return -1;
		}
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;
		if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
			up = process(matrix, i - 1, j);
		}
		if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
			down = process(matrix, i + 1, j);
		}
		if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
			left = process(matrix, i, j - 1);
		}
		if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
			right = process(matrix, i, j + 1);
		}
		return 1 + Math.max(Math.max(up, down), Math.max(left, right));
	}

	public static int maxPath2(int[][] matrix) {
		int ans = Integer.MIN_VALUE;

		int[][] dp = new int[matrix.length][matrix[0].length];

		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[0].length; col++) {
				ans = Math.max(ans, process(matrix, row, col, dp));
			}
		}
		return ans;
	}

	// 假设在matrix中，从i行，j列出发，能走出的最长递增路径，返回
	// dp[i][j] == 0 process(i,j) 之前没遇到过
	// dp[i][j] != 0 process(i,j) 之前已经算过了
	public static int process(int[][] matrix, int i, int j, int[][] dp) {
		if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length) {
			return -1;
		}
		if (dp[i][j] != 0) {
			return dp[i][j];
		}
		int next1 = 0;
		int next2 = 0;
		int next3 = 0;
		int next4 = 0;
		if (i - 1 >= 0 && matrix[i - 1][j] > matrix[i][j]) {
			next1 = process(matrix, i - 1, j, dp);
		}
		if (i + 1 < matrix.length && matrix[i + 1][j] > matrix[i][j]) {
			next2 = process(matrix, i + 1, j, dp);
		}
		if (j - 1 >= 0 && matrix[i][j - 1] > matrix[i][j]) {
			next3 = process(matrix, i, j - 1, dp);
		}
		if (j + 1 < matrix[0].length && matrix[i][j + 1] > matrix[i][j]) {
			next4 = process(matrix, i, j + 1, dp);
		}
		int ans = 1 + Math.max(Math.max(next1, next2), Math.max(next3, next4));
		dp[i][j] = ans;
		return ans;
	}

	public static int longestIncreasingPath(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int[][] dp = new int[m.length][m[0].length];
		// dp[i][j] (i,j)出发，走出的最长链长度
		int max = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				// 每一个(i,j)位置出发，都尝试
				max = Math.max(max, maxIncrease(m, dp, i + 1, j, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, dp, i, j + 1, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, dp, i - 1, j, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, dp, i, j - 1, m[i][j]) + 1);
			}

		}
		return max;
	}

	// 来到的当前位置是i,j位置
	// p 上一步值是什么
	// 从(i,j)位置出发，走出的最长链，要求：上一步是可以迈到当前步上的
	public static int maxIncrease(int[][] m, int[][] dp, int i, int j, int p) {
		if (i < 0 || i >= m.length || j < 0 || j >= m[0].length || m[i][j] <= p) {
			return 0;
		}
		if (dp[i][j] == 0) { // i,j 出发，当前没算过
			dp[i][j] = maxIncrease(m, dp, i + 1, j, m[i][j]) + 1;
			dp[i][j] = Math.max(dp[i][j], maxIncrease(m, dp, i, j + 1, m[i][j]) + 1);
			dp[i][j] = Math.max(dp[i][j], maxIncrease(m, dp, i - 1, j, m[i][j]) + 1);
			dp[i][j] = Math.max(dp[i][j], maxIncrease(m, dp, i, j - 1, m[i][j]) + 1);
		}
		return dp[i][j];
	}

}
