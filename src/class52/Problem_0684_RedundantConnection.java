package class52;

public class Problem_0684_RedundantConnection {

	public static int[] findRedundantConnection(int[][] edges) {
		int N = edges.length;
		UnionFind uf = new UnionFind(N);
		for (int i = 0; i < N; i++) {
			int a = edges[i][0];
			int b = edges[i][1];
			if (uf.same(a, b)) {
				return new int[] { a, b };
			}
			uf.union(a, b);
		}
		return null;
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
