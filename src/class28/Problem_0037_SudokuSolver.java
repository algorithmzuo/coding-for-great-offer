package class28;

public class Problem_0037_SudokuSolver {

	public static void solveSudoku(char[][] board) {
		boolean[][] row = new boolean[9][10];
		boolean[][] col = new boolean[9][10];
		boolean[][] bucket = new boolean[9][10];
		initMaps(board, row, col, bucket);
		process(board, 0, 0, row, col, bucket);
	}

	public static void initMaps(char[][] board, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int bid = 3 * (i / 3) + (j / 3);
				if (board[i][j] != '.') {
					int num = board[i][j] - '0';
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
				}
			}
		}
	}

	//  当前来到(i,j)这个位置，如果已经有数字，跳到下一个位置上
	//                      如果没有数字，尝试1~9，不能和row、col、bucket冲突
	public static boolean process(char[][] board, int i, int j, boolean[][] row, boolean[][] col, boolean[][] bucket) {
		if (i == 9) {
			return true;
		}
		// 当离开(i，j)，应该去哪？(nexti, nextj)
		int nexti = j != 8 ? i : i + 1;
		int nextj = j != 8 ? j + 1 : 0;
		if (board[i][j] != '.') {
			return process(board, nexti, nextj, row, col, bucket);
		} else {
			// 可以尝试1~9
			int bid = 3 * (i / 3) + (j / 3);
			for (int num = 1; num <= 9; num++) { // 尝试每一个数字1~9
				if ((!row[i][num]) && (!col[j][num]) && (!bucket[bid][num])) {
					// 可以尝试num
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
					board[i][j] = (char) (num + '0');
					if (process(board, nexti, nextj, row, col, bucket)) {
						return true;
					}
					row[i][num] = false;
					col[j][num] = false;
					bucket[bid][num] = false;
					board[i][j] = '.';
				}
			}
			return false;
		}
	}

}
