package class07;

import java.util.Arrays;

public class Code06_SnakeGame {

	public static int walk1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				int[] ans = process(matrix, i, j);
				res = Math.max(res, Math.max(ans[0], ans[1]));
			}
		}
		return res;
	}

	// 从假想的最优左侧到达(i,j)的旅程中
	// 0) 在没有使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
	// 1) 在使用过能力的情况下，返回路径最大和，没有可能到达的话，返回负
	public static int[] process(int[][] m, int i, int j) {
		if (j == 0) { // (i,j)就是最左侧的位置
			return new int[] { m[i][j], -m[i][j] };
		}
		int[] preAns = process(m, i, j - 1);
		// 所有的路中，完全不使用能力的情况下，能够到达的最好长度是多大
		int preUnuse = preAns[0];
		// 所有的路中，使用过一次能力的情况下，能够到达的最好长度是多大
		int preUse = preAns[1];
		if (i - 1 >= 0) {
			preAns = process(m, i - 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		if (i + 1 < m.length) {
			preAns = process(m, i + 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		// preUnuse 之前旅程，没用过能力
		// preUse 之前旅程，已经使用过能力了
		int no = -1; // 之前没使用过能力，当前位置也不使用能力，的最优解
		int yes = -1; // 不管是之前使用能力，还是当前使用了能力，请保证能力只使用一次，最优解
		if (preUnuse >= 0) {
			no = m[i][j] + preUnuse;
			yes = -m[i][j] + preUnuse;
		}
		if (preUse >= 0) {
			yes = Math.max(yes, m[i][j] + preUse);
		}
		return new int[] { no, yes };
	}

	public static int walk2(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int[][][] dp = new int[matrix.length][matrix[0].length][2];
		for (int i = 0; i < dp.length; i++) {
			dp[i][0][0] = matrix[i][0];
			dp[i][0][1] = -matrix[i][0];
			max = Math.max(max, Math.max(dp[i][0][0], dp[i][0][1]));
		}
		for (int j = 1; j < matrix[0].length; j++) {
			for (int i = 0; i < matrix.length; i++) {
				int preUnuse = dp[i][j - 1][0];
				int preUse = dp[i][j - 1][1];
				if (i - 1 >= 0) {
					preUnuse = Math.max(preUnuse, dp[i - 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i - 1][j - 1][1]);
				}
				if (i + 1 < matrix.length) {
					preUnuse = Math.max(preUnuse, dp[i + 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i + 1][j - 1][1]);
				}
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
				if (preUnuse >= 0) {
					dp[i][j][0] = matrix[i][j] + preUnuse;
					dp[i][j][1] = -matrix[i][j] + preUnuse;
				}
				if (preUse >= 0) {
					dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preUse);
				}
				max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
			}
		}
		return max;
	}

	public static int[][] generateRandomArray(int row, int col, int value) {
		int[][] arr = new int[(int) (Math.random() * row) + 1][(int) (Math.random() * col) + 1];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				arr[i][j] = (int) (Math.random() * value) * (Math.random() > 0.5 ? -1 : 1);
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		int times = 1000000;
		for (int i = 0; i < times; i++) {
			int[][] matrix = generateRandomArray(5, 5, 10);
			int ans1 = walk1(matrix);
			int ans2 = walk2(matrix);
			if (ans1 != ans2) {
				for (int j = 0; j < matrix.length; j++) {
					System.out.println(Arrays.toString(matrix[j]));
				}
				System.out.println("Oops   ans1: " + ans1 + "   ans2:" + ans2);
				break;
			}
		}
		System.out.println("finish");
	}

}
