package class48;

public class Problem_0499_TheMazeIII {

	// 节点：来到了哪？(r,c)这个位置
	// 从哪个方向来的！d -> 0 1 2 3 4
	// 之前做了什么决定让你来到这个位置。
	public static class Node {
		public int r;
		public int c;
		public int d;
		public String p;

		public Node(int row, int col, int dir, String path) {
			r = row;
			c = col;
			d = dir;
			p = path;
		}

	}

	public static String findShortestWay(int[][] maze, int[] ball, int[] hole) {
		int n = maze.length;
		int m = maze[0].length;
		Node[] q1 = new Node[n * m], q2 = new Node[n * m];
		int s1 = 0, s2 = 0;
		boolean[][][] visited = new boolean[maze.length][maze[0].length][4];
		s1 = spread(maze, n, m, new Node(ball[0], ball[1], 4, ""), visited, q1, s1);
		while (s1 != 0) {
			for (int i = 0; i < s1; i++) {
				Node cur = q1[i];
				if (hole[0] == cur.r && hole[1] == cur.c) {
					return cur.p;
				}
				s2 = spread(maze, n, m, cur, visited, q2, s2);
			}
			Node[] tmp = q1;
			q1 = q2;
			q2 = tmp;
			s1 = s2;
			s2 = 0;
		}
		return "impossible";
	}

	public static int[][] to = { { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 0 }, { 0, 0 } };

	public static String[] re = { "d", "l", "r", "u" };

	// maze迷宫，走的格子
	// n 行数
	// m 列数
	// 当前来到的节点，cur -> (r,c) 方向 路径（决定）
	// v [行][列][方向] 一个格子，其实在宽度有限遍历时，是4个点！
	// q 下一层的队列
	// s 下一层队列填到了哪，size
	// 当前点cur，该分裂分裂，该继续走继续走，所产生的一下层的点，进入q，s++
	// 返回值：q增长到了哪？返回size -> s
	public static int spread(int[][] maze, int n, int m, 
			Node cur, boolean[][][] v, Node[] q, int s) {
		int d = cur.d;
		int r = cur.r + to[d][0];
		int c = cur.c + to[d][1];
		// 分裂去！
		if (d == 4 || r < 0 || r == n || c < 0 || c == m || maze[r][c] != 0) {
			for (int i = 0; i < 4; i++) {
				if (i != d) {
					r = cur.r + to[i][0];
					c = cur.c + to[i][1];
					if (r >= 0 && r < n && c >= 0 && c < m && maze[r][c] == 0 && !v[r][c][i]) {
						v[r][c][i] = true;
						Node next = new Node(r, c, i, cur.p + re[i]);
						q[s++] = next;
					}
				}
			}
		} else { // 不分裂！继续走！
			if (!v[r][c][d]) {
				v[r][c][d] = true;
				q[s++] = new Node(r, c, d, cur.p);
			}
		}
		return s;
	}

}
