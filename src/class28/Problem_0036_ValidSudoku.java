package class28;

public class Problem_0036_ValidSudoku {

	public static boolean isValidSudoku(char[][] board) {
		boolean[][] row = new boolean[9][10];
		boolean[][] col = new boolean[9][10];
		boolean[][] bucket = new boolean[9][10];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int bid = 3 * (i / 3) + (j / 3);
				if (board[i][j] != '.') {
					int num = board[i][j] - '0';
					if (row[i][num] || col[j][num] || bucket[bid][num]) {
						return false;
					}
					row[i][num] = true;
					col[j][num] = true;
					bucket[bid][num] = true;
				}
			}
		}
		return true;
	}

}
