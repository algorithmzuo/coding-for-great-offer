//不要拷贝包信息的内容
package class06;

//本文件是牛客题目解答
//这是牛客的测试链接：
//https://www.nowcoder.com/questionTerminal/fe30a13b5fb84b339cb6cb3f70dca699
//把如下的全部代码拷贝进编辑器（java）
//把import下方的类名改为Main
//可以直接通过

import java.util.Scanner;

public class Code04_NiuNiuSplitField {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int n = in.nextInt();
			int m = in.nextInt();
			int[][] matrix = new int[n][m];
			for (int i = 0; i < n; i++) {
				char[] chas = in.next().toCharArray();
				for (int j = 0; j < m; j++) {
					matrix[i][j] = chas[j] - '0';
				}
			}
			System.out.println(maxMinSumIn16(matrix));
		}
		in.close();
	}

	public static int maxMinSumIn16(int[][] matrix) {
		if (matrix == null || matrix.length < 4 || matrix[0].length < 4) {
			return 0;
		}
		int[][] record = generateSumRecord(matrix);
		int col = matrix[0].length;
		int res = Integer.MIN_VALUE;
		for (int c1 = 0; c1 < col - 3; c1++) {
			for (int c2 = c1 + 1; c2 < col - 2; c2++) {
				for (int c3 = c2 + 1; c3 < col - 1; c3++) {
					res = Math.max(res, bestSplit(record, c1, c2, c3));
				}
			}
		}
		return res;
	}

	public static int[][] generateSumRecord(int[][] matrix) {
		int row = matrix.length;
		int col = matrix[0].length;
		int[][] record = new int[row][col];
		record[0][0] = matrix[0][0];
		for (int i = 1; i < row; i++) {
			record[i][0] = record[i - 1][0] + matrix[i][0];
		}
		for (int j = 1; j < col; j++) {
			record[0][j] = record[0][j - 1] + matrix[0][j];
		}
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				record[i][j] = record[i][j - 1] + record[i - 1][j] - record[i - 1][j - 1] + matrix[i][j];
			}
		}
		return record;
	}

	public static int bestSplit(int[][] record, int c1, int c2, int c3) {
		int[] up2 = bestMinSplit2FromUp(record, c1, c2, c3);
		int[] down2 = bestMinSplit2FromDown(record, c1, c2, c3);
		int max = Integer.MIN_VALUE;
		for (int mid = 1; mid < record.length - 2; mid++) {
			max = Math.max(max, Math.min(up2[mid], down2[mid + 1]));
		}
		return max;
	}

	public static int[] bestMinSplit2FromUp(int[][] record, int c1, int c2, int c3) {
		int size = record.length;
		int[] max = new int[size];
		int split = 0;
		for (int i = 1; i < size; i++) {
			int minsMax = twoValuesMin(record, c1, c2, c3, 0, split, i);
			for (; split < i - 1; split++) {
				int nextMin = twoValuesMin(record, c1, c2, c3, 0, split + 1, i);
				if (nextMin >= minsMax) {
					minsMax = nextMin;
				} else {
					break;
				}
			}
			max[i] = minsMax;
		}
		return max;
	}

	public static int[] bestMinSplit2FromDown(int[][] record, int c1, int c2, int c3) {
		int size = record.length;
		int[] max = new int[size];
		int split = size - 1;
		for (int i = size - 2; i >= 0; i--) {
			int minsMax = twoValuesMin(record, c1, c2, c3, i, split - 1, size - 1);
			for (; split > i + 1; split--) {
				int nextMin = twoValuesMin(record, c1, c2, c3, i, split - 2, size - 1);
				if (nextMin >= minsMax) {
					minsMax = nextMin;
				} else {
					break;
				}
			}
			max[i] = minsMax;
		}
		return max;
	}

	// 两部分最小值返回
	public static int twoValuesMin(int[][] record, int c1, int c2, int c3, int i, int split, int j) {
		return Math.min(value(record, c1, c2, c3, i, split), value(record, c1, c2, c3, split + 1, j));
	}

	// 提取出最小值
	public static int value(int[][] record, int c1, int c2, int c3, int prow, int crow) {
		int value1 = area(record, prow, 0, crow, c1);
		int value2 = area(record, prow, c1 + 1, crow, c2);
		int value3 = area(record, prow, c2 + 1, crow, c3);
		int value4 = area(record, prow, c3 + 1, crow, record[0].length - 1);
		return Math.min(Math.min(value1, value2), Math.min(value3, value4));
	}

	// 算出区域内的累加和
	public static int area(int[][] record, int i1, int j1, int i2, int j2) {
		int all = record[i2][j2];
		int left = j1 > 0 ? record[i2][j1 - 1] : 0;
		int up = i1 > 0 ? record[i1 - 1][j2] : 0;
		int makeUp = (i1 > 0 && j1 > 0) ? record[i1 - 1][j1 - 1] : 0;
		return all - left - up + makeUp;
	}

}