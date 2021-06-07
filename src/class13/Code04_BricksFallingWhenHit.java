package class13;

// 本题测试链接 : https://leetcode.com/problems/bricks-falling-when-hit/
public class Code04_BricksFallingWhenHit {

	public static int[] hitBricks(int[][] grid, int[][] hits) {
		for (int i = 0; i < hits.length; i++) {
			if (grid[hits[i][0]][hits[i][1]] == 1) {
				grid[hits[i][0]][hits[i][1]] = 2;
			}
		}
		UnionFind unionFind = new UnionFind(grid);
		int[] ans = new int[hits.length];
		for (int i = hits.length - 1; i >= 0; i--) {
			if (grid[hits[i][0]][hits[i][1]] == 2) {
				ans[i] = unionFind.finger(hits[i][0], hits[i][1]);
			}
		}
		return ans;
	}

	// 并查集
	public static class UnionFind {
		private int N;
		private int M;
		// 有多少块砖，连到了天花板上
		private int cellingAll;
		// 原始矩阵，因为炮弹的影响，1 -> 2
		private int[][] grid;
		// cellingSet[i] = true; i 是头节点，所在的集合是天花板集合
		private boolean[] cellingSet;
		private int[] fatherMap;
		private int[] sizeMap;
		private int[] stack;

		public UnionFind(int[][] matrix) {
			initSpace(matrix);
			initConnect();
		}

		private void initSpace(int[][] matrix) {
			grid = matrix;
			N = grid.length;
			M = grid[0].length;
			int all = N * M;
			cellingAll = 0;
			cellingSet = new boolean[all];
			fatherMap = new int[all];
			sizeMap = new int[all];
			stack = new int[all];
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < M; col++) {
					if (grid[row][col] == 1) {
						int index = row * M + col;
						fatherMap[index] = index;
						sizeMap[index] = 1;
						if (row == 0) {
							cellingSet[index] = true;
							cellingAll++;
						}
					}
				}
			}
		}

		private void initConnect() {
			for (int row = 0; row < N; row++) {
				for (int col = 0; col < M; col++) {
					union(row, col, row - 1, col);
					union(row, col, row + 1, col);
					union(row, col, row, col - 1);
					union(row, col, row, col + 1);
				}
			}
		}

		private int find(int row, int col) {
			int stackSize = 0;
			int index = row * M + col;
			while (index != fatherMap[index]) {
				stack[stackSize++] = index;
				index = fatherMap[index];
			}
			while (stackSize != 0) {
				fatherMap[stack[--stackSize]] = index;
			}
			return index;
		}

		private void union(int r1, int c1, int r2, int c2) {
			if (valid(r1, c1) && valid(r2, c2)) {
				int father1 = find(r1, c1);
				int father2 = find(r2, c2);
				if (father1 != father2) {
					int size1 = sizeMap[father1];
					int size2 = sizeMap[father2];
					boolean status1 = cellingSet[father1];
					boolean status2 = cellingSet[father2];
					if (size1 <= size2) {
						fatherMap[father1] = father2;
						sizeMap[father2] = size1 + size2;
						if (status1 ^ status2) {
							cellingSet[father2] = true;
							cellingAll += status1 ? size2 : size1;
						}
					} else {
						fatherMap[father2] = father1;
						sizeMap[father1] = size1 + size2;
						if (status1 ^ status2) {
							cellingSet[father1] = true;
							cellingAll += status1 ? size2 : size1;
						}
					}
				}
			}
		}

		private boolean valid(int row, int col) {
			return row >= 0 && row < N && col >= 0 && col < M && grid[row][col] == 1;
		}

		public int cellingNum() {
			return cellingAll;
		}

		public int finger(int row, int col) {
			grid[row][col] = 1;
			int cur = row * M + col;
			if (row == 0) {
				cellingSet[cur] = true;
				cellingAll++;
			}
			fatherMap[cur] = cur;
			sizeMap[cur] = 1;
			int pre = cellingAll;
			union(row, col, row - 1, col);
			union(row, col, row + 1, col);
			union(row, col, row, col - 1);
			union(row, col, row, col + 1);
			int now = cellingAll;
			if (row == 0) {
				return now - pre;
			} else {
				return now == pre ? 0 : now - pre - 1;
			}
		}
	}

}
