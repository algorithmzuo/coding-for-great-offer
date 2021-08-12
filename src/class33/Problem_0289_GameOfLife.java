package class33;

// 有关这个游戏更有意思、更完整的内容：
// https://www.bilibili.com/video/BV1rJ411n7ri
// 也推荐这个up主
public class Problem_0289_GameOfLife {

	public static void gameOfLife(int[][] board) {
		int N = board.length;
		int M = board[0].length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				int neighbors = neighbors(board, i, j);
				if (neighbors == 3 || (board[i][j] == 1 && neighbors == 2)) {
					set(board, i, j);
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				board[i][j] = get(board, i, j);
			}
		}
	}

	public static int neighbors(int[][] board, int i, int j) {
		int count = 0;
		count += ok(board, i - 1, j - 1) ? 1 : 0;
		count += ok(board, i - 1, j) ? 1 : 0;
		count += ok(board, i - 1, j + 1) ? 1 : 0;
		count += ok(board, i, j - 1) ? 1 : 0;
		count += ok(board, i, j + 1) ? 1 : 0;
		count += ok(board, i + 1, j - 1) ? 1 : 0;
		count += ok(board, i + 1, j) ? 1 : 0;
		count += ok(board, i + 1, j + 1) ? 1 : 0;
		return count;
	}

	public static boolean ok(int[][] board, int i, int j) {
		return i >= 0 && i < board.length && j >= 0 && j < board[0].length && (board[i][j] & 1) == 1;
	}

	public static void set(int[][] board, int i, int j) {
		board[i][j] |= 2;
	}

	public static int get(int[][] board, int i, int j) {
		return board[i][j] >> 1;
	}

}
