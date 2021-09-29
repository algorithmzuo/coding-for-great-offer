package class44;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0317_ShortestDistanceFromAllBuildings {

	// 如果grid中0比较少，用这个方法比较好
	public static int shortestDistance1(int[][] grid) {
		int ans = Integer.MAX_VALUE;
		int N = grid.length;
		int M = grid[0].length;
		int buildings = 0;
		Position[][] positions = new Position[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (grid[i][j] == 1) {
					buildings++;
				}
				positions[i][j] = new Position(i, j, grid[i][j]);
			}
		}
		if (buildings == 0) {
			return 0;
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				ans = Math.min(ans, bfs(positions, buildings, i, j));
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	public static int bfs(Position[][] positions, int buildings, int i, int j) {
		if (positions[i][j].v != 0) {
			return Integer.MAX_VALUE;
		}
		HashMap<Position, Integer> levels = new HashMap<>();
		Queue<Position> queue = new LinkedList<>();
		Position from = positions[i][j];
		levels.put(from, 0);
		queue.add(from);
		int ans = 0;
		int solved = 0;
		while (!queue.isEmpty() && solved != buildings) {
			Position cur = queue.poll();
			int level = levels.get(cur);
			if (cur.v == 1) {
				ans += level;
				solved++;
			} else {
				add(queue, levels, positions, cur.r - 1, cur.c, level + 1);
				add(queue, levels, positions, cur.r + 1, cur.c, level + 1);
				add(queue, levels, positions, cur.r, cur.c - 1, level + 1);
				add(queue, levels, positions, cur.r, cur.c + 1, level + 1);
			}
		}
		return solved == buildings ? ans : Integer.MAX_VALUE;
	}

	public static class Position {
		public int r;
		public int c;
		public int v;

		public Position(int row, int col, int value) {
			r = row;
			c = col;
			v = value;
		}
	}

	public static void add(Queue<Position> q, HashMap<Position, Integer> l, Position[][] p, int i, int j, int level) {
		if (i >= 0 && i < p.length && j >= 0 && j < p[0].length && p[i][j].v != 2 && !l.containsKey(p[i][j])) {
			l.put(p[i][j], level);
			q.add(p[i][j]);
		}
	}

	// 如果grid中1比较少，用这个方法比较好
	public static int shortestDistance2(int[][] grid) {
		int N = grid.length;
		int M = grid[0].length;
		int ones = 0;
		int zeros = 0;
		Info[][] infos = new Info[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (grid[i][j] == 1) {
					infos[i][j] = new Info(i, j, 1, ones++);
				} else if (grid[i][j] == 0) {
					infos[i][j] = new Info(i, j, 0, zeros++);
				} else {
					infos[i][j] = new Info(i, j, 2, Integer.MAX_VALUE);
				}
			}
		}
		if (ones == 0) {
			return 0;
		}
		int[][] distance = new int[ones][zeros];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (infos[i][j].v == 1) {
					bfs(infos, i, j, distance);
				}
			}
		}
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < zeros; i++) {
			int sum = 0;
			for (int j = 0; j < ones; j++) {
				if (distance[j][i] == 0) {
					sum = Integer.MAX_VALUE;
					break;
				} else {
					sum += distance[j][i];
				}
			}
			ans = Math.min(ans, sum);
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	public static class Info {
		public int r;
		public int c;
		public int v;
		public int t;

		public Info(int row, int col, int value, int th) {
			r = row;
			c = col;
			v = value;
			t = th;
		}
	}

	public static void bfs(Info[][] infos, int i, int j, int[][] distance) {
		HashMap<Info, Integer> levels = new HashMap<>();
		Queue<Info> queue = new LinkedList<>();
		Info from = infos[i][j];
		add(queue, levels, infos, from.r - 1, from.c, 1);
		add(queue, levels, infos, from.r + 1, from.c, 1);
		add(queue, levels, infos, from.r, from.c - 1, 1);
		add(queue, levels, infos, from.r, from.c + 1, 1);
		while (!queue.isEmpty()) {
			Info cur = queue.poll();
			int level = levels.get(cur);
			distance[from.t][cur.t] = level;
			add(queue, levels, infos, cur.r - 1, cur.c, level + 1);
			add(queue, levels, infos, cur.r + 1, cur.c, level + 1);
			add(queue, levels, infos, cur.r, cur.c - 1, level + 1);
			add(queue, levels, infos, cur.r, cur.c + 1, level + 1);
		}
	}

	public static void add(Queue<Info> q, HashMap<Info, Integer> l, Info[][] infos, int i, int j, int level) {
		if (i >= 0 && i < infos.length && j >= 0 && j < infos[0].length && infos[i][j].v == 0
				&& !l.containsKey(infos[i][j])) {
			l.put(infos[i][j], level);
			q.add(infos[i][j]);
		}
	}

	// 方法三的大流程和方法二完全一样，从每一个1出发，而不从0出发
	// 运行时间快主要是因为常数优化，以下是优化点：
	// 1) 宽度优先遍历时，一次解决一层，不是一个一个遍历：
	// int size = que.size();
	// level++;
	// for (int k = 0; k < size; k++) { ... }
	// 2) pass的值每次减1何用？只有之前所有的1都到达的0，才有必要继续尝试的意思
	// 也就是说，如果某个1，自我封闭，之前的1根本到不了现在这个1附近的0，就没必要继续尝试了
	// if (nextr >= 0 && nextr < grid.length
	// && nextc >= 0 && nextc < grid[0].length
	// && grid[nextr][nextc] == pass)
	// 3) int[] trans = { 0, 1, 0, -1, 0 }; 的作用是迅速算出上、下、左、右
	// 4) 如果某个1在计算时，它周围已经没有pass值了，可以提前宣告1之间是不连通的
	// step = bfs(grid, dist, i, j, pass--, trans);
	// if (step == Integer.MAX_VALUE) {
	// return -1;
	// }
	// 5) 最要的优化，每个1到某个0的距离是逐渐叠加的，每个1给所有的0叠一次（宽度优先遍历）
	// dist[nextr][nextc] += level;
	public static int shortestDistance3(int[][] grid) {
		int[][] dist = new int[grid.length][grid[0].length];
		int pass = 0;
		int step = Integer.MAX_VALUE;
		int[] trans = { 0, 1, 0, -1, 0 };
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == 1) {
					step = bfs(grid, dist, i, j, pass--, trans);
					if (step == Integer.MAX_VALUE) {
						return -1;
					}
				}
			}
		}
		return step == Integer.MAX_VALUE ? -1 : step;
	}

	public static int bfs(int[][] grid, int[][] dist, int row, int col, int pass, int[] trans) {
		Queue<int[]> que = new LinkedList<int[]>();
		que.offer(new int[] { row, col });
		int level = 0;
		int ans = Integer.MAX_VALUE;
		while (!que.isEmpty()) {
			int size = que.size();
			level++;
			for (int k = 0; k < size; k++) {
				int[] node = que.poll();
				for (int i = 1; i < trans.length; i++) {
					int nextr = node[0] + trans[i - 1];
					int nextc = node[1] + trans[i];
					if (nextr >= 0 && nextr < grid.length && nextc >= 0 && nextc < grid[0].length
							&& grid[nextr][nextc] == pass) {
						que.offer(new int[] { nextr, nextc });
						dist[nextr][nextc] += level;
						ans = Math.min(ans, dist[nextr][nextc]);
						grid[nextr][nextc]--;
					}
				}
			}
		}
		return ans;
	}

}
