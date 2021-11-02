package class52;

public class Problem_0685_RedundantConnectionII {

	public static int[] findRedundantDirectedConnection(int[][] edges) {
		int N = edges.length;
		UnionFind uf = new UnionFind(N);
		int[] pre = new int[N + 1];
		int[] first = null;
		int[] second = null;
		int[] circle = null;
		for (int i = 0; i < N; i++) {
			int a = edges[i][0];
			int b = edges[i][1];
			if (pre[b] != 0) {
				first = new int[] { pre[b], b };
				second = edges[i];
			} else {
				pre[b] = a;
				if (uf.same(a, b)) {
					circle = edges[i];
				} else {
					uf.union(a, b);
				}
			}
		}
		return first != null ? (circle != null ? first : second) : circle;
	}

	public static class UnionFind {
		private int[] f;
		private int[] s;
		private int[] h;

		public UnionFind(int N) {
			f = new int[N + 1];
			s = new int[N + 1];
			h = new int[N + 1];
			for (int i = 0; i <= N; i++) {
				f[i] = i;
				s[i] = 1;
			}
		}

		private int find(int i) {
			int hi = 0;
			while (i != f[i]) {
				h[hi++] = i;
				i = f[i];
			}
			while (hi > 0) {
				f[h[--hi]] = i;
			}
			return i;
		}

		public boolean same(int i, int j) {
			return find(i) == find(j);
		}

		public void union(int i, int j) {
			int fi = find(i);
			int fj = find(j);
			if (fi != fj) {
				if (s[fi] >= s[fj]) {
					f[fj] = fi;
					s[fi] = s[fi] + s[fj];
				} else {
					f[fi] = fj;
					s[fj] = s[fi] + s[fj];
				}
			}
		}

	}

}
