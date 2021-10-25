package class50;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Problem_0675_CutOffTreesForGolfEvent {

	public static int cutOffTree(List<List<Integer>> forest) {
		int n = forest.size(), m = forest.get(0).size();
		PriorityQueue<int[]> cells = new PriorityQueue<>((a, b) -> a[2] - b[2]);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				int val = forest.get(i).get(j);
				if (val > 1) {
					cells.add(new int[] { i, j, val });
				}
			}
		}
		int res = 0;
		int curX = 0, curY = 0;
		while (!cells.isEmpty()) {
			int[] curCell = cells.poll();
			int step = dist(forest, curX, curY, curCell[0], curCell[1]);
			if (step == -1) {
				return -1;
			}
			res += step;
			curX = curCell[0];
			curY = curCell[1];
			forest.get(curX).set(curY, 1);
		}
		return res;
	}

	public static int dist(List<List<Integer>> forest, int sr, int sc, int tr, int tc) {
		if (sr == tr && sc == tc) {
			return 0;
		}
		int n = forest.size();
		int m = forest.get(0).size();
		boolean[][] seen = new boolean[n][m];
		LinkedList<int[]> deque = new LinkedList<>();
		deque.offerFirst(new int[] { 0, sr, sc });
		int[] dr = { -1, 1, 0, 0 };
		int[] dc = { 0, 0, -1, 1 };
		while (!deque.isEmpty()) {
			int[] cur = deque.pollFirst();
			int step = cur[0], r = cur[1], c = cur[2];
			seen[r][c] = true;
			for (int di = 0; di < 4; ++di) {
				int nr = r + dr[di];
				int nc = c + dc[di];
				if (nr >= 0 && nr < n && nc >= 0 && nc < m && !seen[nr][nc]) {
					if (nr == tr && nc == tc) {
						return step + 1;
					}
					boolean closer = (di == 0 && r > tr) || (di == 1 && r < tr) || (di == 2 && c > tc)
							|| (di == 3 && c < tc);
					if (forest.get(nr).get(nc) > 0) {
						if (closer) {
							deque.offerFirst(new int[] { step + 1, nr, nc });
						} else {
							deque.offerLast(new int[] { step + 1, nr, nc });
						}
					}
				}
			}
		}
		return -1;
	}

}
