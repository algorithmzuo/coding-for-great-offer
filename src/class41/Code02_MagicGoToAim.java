package class41;

import java.util.PriorityQueue;

/*
 * N个结点之间，表世界存在双向通行的道路，里世界存在双向通行的传送门.
 * 若走表世界的道路，花费一分钟.
 * 若走里世界的传送门，不花费时间，但是接下来一分钟不能走传送门.
 * 输入： T为测试用例的组数，对于每组数据:
 * 第一行：N M1 M2 N代表结点的个数1到N
 * 接下来M1行 每行两个数，u和v，表示表世界u和v之间存在道路
 * 接下来M2行 每行两个数，u和v，表示里世界u和v之间存在传送门
 * 现在处于1号结点，最终要到达N号结点，求最小的到达时间 保证所有输入均有效，不存在环等情况 
 */
public class Code02_MagicGoToAim {

	// roads[i]是一个数组，表示i能走路达到的城市有哪些，每条路都花费1分钟
	// gates[i]是一个数组，表示i能传送达到的城市有哪些
	// 返回从0到n-1的最少用时
	public static int fast(int n, int[][] roads, int[][] gates) {
		int[][] distance = new int[2][n];
		for (int i = 1; i < n; i++) {
			distance[0][i] = Integer.MAX_VALUE;
			distance[1][i] = Integer.MAX_VALUE;
		}
		PriorityQueue<Node> heap = new PriorityQueue<>((a, b) -> a.cost - b.cost);
		heap.add(new Node(0, 0, 0));
		boolean[][] visited = new boolean[2][n];
		while (!heap.isEmpty()) {
			Node cur = heap.poll();
			if (visited[cur.preTransfer][cur.city]) {
				continue;
			}
			visited[cur.preTransfer][cur.city] = true;
			for (int next : roads[cur.city]) {
				if (distance[0][next] > cur.cost + 1) {
					distance[0][next] = cur.cost + 1;
					heap.add(new Node(0, next, distance[0][next]));
				}
			}
			if (cur.preTransfer == 0) {
				for (int next : gates[cur.city]) {
					if (distance[1][next] > cur.cost) {
						distance[1][next] = cur.cost;
						heap.add(new Node(1, next, distance[0][next]));
					}
				}
			}
		}
		return Math.min(distance[0][n - 1], distance[1][n - 1]);
	}

	public static class Node {
		public int preTransfer;
		public int city;
		public int cost;

		public Node(int a, int b, int c) {
			preTransfer = a;
			city = b;
			cost = c;
		}
	}

}