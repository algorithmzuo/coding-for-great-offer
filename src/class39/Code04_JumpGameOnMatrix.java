package class39;

// 来自京东
// 给定一个二维数组matrix，matrix[i][j] = k代表:
// 从(i,j)位置可以随意往右跳<=k步，或者从(i,j)位置可以随意往下跳<=k步
// 如果matrix[i][j] = 0，代表来到(i,j)位置必须停止
// 返回从matrix左上角到右下角，至少要跳几次
// 已知matrix中行数n <= 5000, 列数m <= 5000
public class Code04_JumpGameOnMatrix {

	// 暴力方法，仅仅是做对数器
	// 如果无法到达会返回系统最大值
	public static int jump1(int[][] map) {
		return process(map, 0, 0);
	}

	public static int process(int[][] map, int row, int col) {
		if (row == map.length - 1 && col == map[0].length - 1) {
			return 0;
		}
		if (map[row][col] == 0) {
			return Integer.MAX_VALUE;
		}
		int next = Integer.MAX_VALUE;
		for (int down = row + 1; down < map.length && (down - row) <= map[row][col]; down++) {
			next = Math.min(next, process(map, down, col));
		}
		for (int right = col + 1; right < map[0].length && (right - col) <= map[row][col]; right++) {
			next = Math.min(next, process(map, row, right));
		}
		return next != Integer.MAX_VALUE ? (next + 1) : next;
	}

	// 优化方法, 利用线段树做枚举优化
	public static int jump2(int[][] arr) {
		int n = arr.length;
		int m = arr[0].length;
		int[][] map = new int[n + 1][m + 1];
		for (int a = 0, b = 1; a < n; a++, b++) {
			for (int c = 0, d = 1; c < m; c++, d++) {
				map[b][d] = arr[a][c];
			}
		}
		SegmentTree[] rowTrees = new SegmentTree[n + 1];
		for (int i = 1; i <= n; i++) {
			rowTrees[i] = new SegmentTree(m);
		}
		SegmentTree[] colTrees = new SegmentTree[m + 1];
		for (int i = 1; i <= m; i++) {
			colTrees[i] = new SegmentTree(n);
		}
		rowTrees[n].update(m, m, 0, 1, m, 1);
		colTrees[m].update(n, n, 0, 1, n, 1);
		for (int col = m - 1; col >= 1; col--) {
			if (map[n][col] != 0) {
				int left = col + 1;
				int right = Math.min(col + map[n][col], m);
				int next = rowTrees[n].query(left, right, 1, m, 1);
				if (next != Integer.MAX_VALUE) {
					rowTrees[n].update(col, col, next + 1, 1, m, 1);
					colTrees[col].update(n, n, next + 1, 1, n, 1);
				}
			}
		}
		for (int row = n - 1; row >= 1; row--) {
			if (map[row][m] != 0) {
				int up = row + 1;
				int down = Math.min(row + map[row][m], n);
				int next = colTrees[m].query(up, down, 1, n, 1);
				if (next != Integer.MAX_VALUE) {
					rowTrees[row].update(m, m, next + 1, 1, m, 1);
					colTrees[m].update(row, row, next + 1, 1, n, 1);
				}
			}
		}
		for (int row = n - 1; row >= 1; row--) {
			for (int col = m - 1; col >= 1; col--) {
				if (map[row][col] != 0) {
					int left = col + 1;
					int right = Math.min(col + map[row][col], m);
					int next1 = rowTrees[row].query(left, right, 1, m, 1);
					int up = row + 1;
					int down = Math.min(row + map[row][col], n);
					int next2 = colTrees[col].query(up, down, 1, n, 1);
					int next = Math.min(next1, next2);
					if (next != Integer.MAX_VALUE) {
						rowTrees[row].update(col, col, next + 1, 1, m, 1);
						colTrees[col].update(row, row, next + 1, 1, n, 1);
					}
				}
			}
		}
		return rowTrees[1].query(1, 1, 1, m, 1);
	}

	// 区间查询最小值的线段树
	// 注意下标从1开始，不从0开始
	// 比如你传入size = 8
	// 则位置对应为1~8，而不是0~7
	public static class SegmentTree {
		private int[] min;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int size) {
			int N = size + 1;
			min = new int[N << 2];
			change = new int[N << 2];
			update = new boolean[N << 2];
			update(1, size, Integer.MAX_VALUE, 1, size, 1);
		}

		private void pushUp(int rt) {
			min[rt] = Math.min(min[rt << 1], min[rt << 1 | 1]);
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				min[rt << 1] = change[rt];
				min[rt << 1 | 1] = change[rt];
				update[rt] = false;
			}
		}

		// 最后三个参数是固定的, 每次传入相同的值即可:
		// l = 1(固定)
		// r = size(你设置的线段树大小)
		// rt = 1(固定)
		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				min[rt] = C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				update(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		// 最后三个参数是固定的, 每次传入相同的值即可:
		// l = 1(固定)
		// r = size(你设置的线段树大小)
		// rt = 1(固定)
		public int query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return min[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int left = Integer.MAX_VALUE;
			int right = Integer.MAX_VALUE;
			if (L <= mid) {
				left = query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				right = query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return Math.min(left, right);
		}

	}

	// 为了测试
	public static int[][] randomMatrix(int n, int m, int v) {
		int[][] ans = new int[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				ans[i][j] = (int) (Math.random() * v);
			}
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		// 先展示一下线段树的用法，假设N=100
		// 初始化时，1~100所有位置的值都是系统最大
		System.out.println("线段树展示开始");
		int N = 100;
		SegmentTree st = new SegmentTree(N);
		// 查询8~19范围上的最小值
		System.out.println(st.query(8, 19, 1, N, 1));
		// 把6~14范围上对应的值都修改成56
		st.update(6, 14, 56, 1, N, 1);
		// 查询8~19范围上的最小值
		System.out.println(st.query(8, 19, 1, N, 1));
		// 以上是线段树的用法，你可以随意使用update和query方法
		// 线段树的详解请看体系学习班
		System.out.println("线段树展示结束");

		// 以下为正式测试
		int len = 10;
		int value = 8;
		int testTimes = 10000;
		System.out.println("对数器测试开始");
		for (int i = 0; i < testTimes; i++) {
			int n = (int) (Math.random() * len) + 1;
			int m = (int) (Math.random() * len) + 1;
			int[][] map = randomMatrix(n, m, value);
			int ans1 = jump1(map);
			int ans2 = jump2(map);
			if (ans1 != ans2) {
				System.out.println("出错了!");
			}
		}
		System.out.println("对数器测试结束");
	}

}
