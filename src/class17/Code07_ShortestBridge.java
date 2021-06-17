package class17;

public class Code07_ShortestBridge {

	// 本题测试链接 : https://leetcode.com/problems/shortest-bridge/
	public static int shortestBridge(int[][] m) {
		int N = m.length;
		int M = m[0].length;
		int all = N * M;
		int island = 0;
		int[] curs = new int[all];
		int[] nexts = new int[all];
		int[][] records = new int[2][all];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (m[i][j] == 1) {
					int queueSize = infect(m, i, j, N, M, curs, 0, records[island]);
					int V = 1;
					while (queueSize != 0) {
						V++;
						queueSize = bfs(N, M, all, V, curs, queueSize, nexts, records[island]);
						int[] tmp = curs;
						curs = nexts;
						nexts = tmp;
					}
					island++;
				}
			}
		}
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < all; i++) {
			min = Math.min(min, records[0][i] + records[1][i]);
		}
		return min - 3;
	}

	public static int infect(int[][] m, int i, int j, int N, int M, int[] curs, int index, int[] record) {
		if (i < 0 || i == N || j < 0 || j == M || m[i][j] != 1) {
			return index;
		}
		m[i][j] = 2;
		int p = i * M + j;
		record[p] = 1;
		curs[index++] = p;
		index = infect(m, i - 1, j, N, M, curs, index, record);
		index = infect(m, i + 1, j, N, M, curs, index, record);
		index = infect(m, i, j - 1, N, M, curs, index, record);
		index = infect(m, i, j + 1, N, M, curs, index, record);
		return index;
	}

	public static int bfs(int N, int M, int all, int V, int[] curs, int size, int[] nexts, int[] record) {
		int nexti = 0;
		for (int i = 0; i < size; i++) {
			int up = curs[i] < M ? -1 : curs[i] - M;
			int down = curs[i] + M >= all ? -1 : curs[i] + M;
			int left = curs[i] % M == 0 ? -1 : curs[i] - 1;
			int right = curs[i] % M == M - 1 ? -1 : curs[i] + 1;
			if (up != -1 && record[up] == 0) {
				record[up] = V;
				nexts[nexti++] = up;
			}
			if (down != -1 && record[down] == 0) {
				record[down] = V;
				nexts[nexti++] = down;
			}
			if (left != -1 && record[left] == 0) {
				record[left] = V;
				nexts[nexti++] = left;
			}
			if (right != -1 && record[right] == 0) {
				record[right] = V;
				nexts[nexti++] = right;
			}
		}
		return nexti;
	}

}
