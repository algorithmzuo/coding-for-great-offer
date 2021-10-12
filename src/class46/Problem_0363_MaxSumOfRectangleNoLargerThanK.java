package class46;

import java.util.TreeSet;

public class Problem_0363_MaxSumOfRectangleNoLargerThanK {

	public static int maxSumSubmatrix(int[][] matrix, int k) {
		if (matrix == null || matrix[0] == null) {
			return 0;
		}
		if (matrix.length > matrix[0].length) {
			matrix = rotate(matrix);
		}
		int row = matrix.length;
		int col = matrix[0].length;
		int res = Integer.MIN_VALUE;
		TreeSet<Integer> sumSet = new TreeSet<>();
		for (int s = 0; s < row; s++) {
			int[] colSum = new int[col];
			for (int e = s; e < row; e++) {
				sumSet.add(0);
				int rowSum = 0;
				for (int c = 0; c < col; c++) {
					colSum[c] += matrix[e][c];
					rowSum += colSum[c];
					Integer it = sumSet.ceiling(rowSum - k);
					if (it != null) {
						res = Math.max(res, rowSum - it);
					}
					sumSet.add(rowSum);
				}
				sumSet.clear();
			}
		}
		return res;
	}

	public static int[][] rotate(int[][] matrix) {
		int N = matrix.length;
		int M = matrix[0].length;
		int[][] r = new int[M][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				r[j][i] = matrix[i][j];
			}
		}
		return r;
	}

}
