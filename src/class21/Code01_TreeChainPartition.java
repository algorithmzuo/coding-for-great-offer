package class21;

import java.util.HashMap;

public class Code01_TreeChainPartition {

	public static class TreeChain {
		private int tim;
		private int n;
		private int h;
		private int[][] tree;
		private int[] val;
		private int[] fa;
		private int[] dep;
		private int[] son;
		private int[] siz;
		private int[] top;
		private int[] dfn;
		private int[] tnw;
		private SegmentTree seg;

		public TreeChain(int[] father, int[] values) {
			initTree(father, values);
			dfs1(h, 0);
			dfs2(h, h);
			seg = new SegmentTree(tnw);
			seg.build(1, n, 1);
		}

		private void initTree(int[] father, int[] values) {
			tim = 0;
			n = father.length + 1;
			tree = new int[n][];
			val = new int[n];
			fa = new int[n];
			dep = new int[n];
			son = new int[n];
			siz = new int[n];
			top = new int[n];
			dfn = new int[n];
			tnw = new int[n--];
			int[] cnum = new int[n];
			for (int i = 0; i < n; i++) {
				val[i + 1] = values[i];
			}
			for (int i = 0; i < n; i++) {
				if (father[i] == i) {
					h = i + 1;
				} else {
					cnum[father[i]]++;
				}
			}
			tree[0] = new int[0];
			for (int i = 0; i < n; i++) {
				tree[i + 1] = new int[cnum[i]];
			}
			for (int i = 0; i < n; i++) {
				if (i + 1 != h) {
					tree[father[i] + 1][--cnum[father[i]]] = i + 1;
				}
			}
		}

		private void dfs1(int u, int f) {
			fa[u] = f;
			dep[u] = dep[f] + 1;
			siz[u] = 1;
			int maxSize = -1;
			for (int v : tree[u]) {
				dfs1(v, u);
				siz[u] += siz[v];
				if (siz[v] > maxSize) {
					maxSize = siz[v];
					son[u] = v;
				}
			}
		}

		private void dfs2(int u, int t) {
			dfn[u] = ++tim;
			top[u] = t;
			tnw[tim] = val[u];
			if (son[u] != 0) {
				dfs2(son[u], t);
				for (int v : tree[u]) {
					if (v != son[u]) {
						dfs2(v, v);
					}
				}
			}
		}

		public void addSubtree(int head, int value) {
			head++;
			seg.add(dfn[head], dfn[head] + siz[head] - 1, value, 1, n, 1);
		}

		public int querySubtree(int head) {
			head++;
			return seg.query(dfn[head], dfn[head] + siz[head] - 1, 1, n, 1);
		}

		public void addChain(int a, int b, int v) {
			a++;
			b++;
			while (top[a] != top[b]) {
				if (dep[top[a]] > dep[top[b]]) {
					seg.add(dfn[top[a]], dfn[a], v, 1, n, 1);
					a = fa[top[a]];
				} else {
					seg.add(dfn[top[b]], dfn[b], v, 1, n, 1);
					b = fa[top[b]];
				}
			}
			if (dep[a] > dep[b]) {
				seg.add(dfn[b], dfn[a], v, 1, n, 1);
			} else {
				seg.add(dfn[a], dfn[b], v, 1, n, 1);
			}
		}

		public int queryChain(int a, int b) {
			a++;
			b++;
			int ans = 0;
			while (top[a] != top[b]) {
				if (dep[top[a]] > dep[top[b]]) {
					ans += seg.query(dfn[top[a]], dfn[a], 1, n, 1);
					a = fa[top[a]];
				} else {
					ans += seg.query(dfn[top[b]], dfn[b], 1, n, 1);
					b = fa[top[b]];
				}
			}
			if (dep[a] > dep[b]) {
				ans += seg.query(dfn[b], dfn[a], 1, n, 1);
			} else {
				ans += seg.query(dfn[a], dfn[b], 1, n, 1);
			}
			return ans;
		}
	}

	public static class SegmentTree {
		private int MAXN;
		private int[] arr;
		private int[] sum;
		private int[] lazy;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int[] origin) {
			MAXN = origin.length;
			arr = origin;
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
			change = new int[MAXN << 2];
			update = new boolean[MAXN << 2];
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				lazy[rt << 1] = 0;
				lazy[rt << 1 | 1] = 0;
				sum[rt << 1] = change[rt] * ln;
				sum[rt << 1 | 1] = change[rt] * rn;
				update[rt] = false;
			}
			if (lazy[rt] != 0) {
				lazy[rt << 1] += lazy[rt];
				sum[rt << 1] += lazy[rt] * ln;
				lazy[rt << 1 | 1] += lazy[rt];
				sum[rt << 1 | 1] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}

		public void build(int l, int r, int rt) {
			if (l == r) {
				sum[rt] = arr[l];
				return;
			}
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			pushUp(rt);
		}

		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				sum[rt] = C * (r - l + 1);
				lazy[rt] = 0;
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

		public void add(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				sum[rt] += C * (r - l + 1);
				lazy[rt] += C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				add(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				add(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public int query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return sum[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}

	}

	// 为了测试，这个结构是暴力但正确的方法
	public static class Right {
		private int n;
		private int[][] tree;
		private int[] fa;
		private int[] val;
		private HashMap<Integer, Integer> path;

		public Right(int[] father, int[] value) {
			n = father.length;
			tree = new int[n][];
			fa = new int[n];
			val = new int[n];
			for (int i = 0; i < n; i++) {
				fa[i] = father[i];
				val[i] = value[i];
			}
			int[] help = new int[n];
			int h = 0;
			for (int i = 0; i < n; i++) {
				if (father[i] == i) {
					h = i;
				} else {
					help[father[i]]++;
				}
			}
			for (int i = 0; i < n; i++) {
				tree[i] = new int[help[i]];
			}
			for (int i = 0; i < n; i++) {
				if (i != h) {
					tree[father[i]][--help[father[i]]] = i;
				}
			}
			path = new HashMap<>();
		}

		public void addSubtree(int head, int value) {
			val[head] += value;
			for (int next : tree[head]) {
				addSubtree(next, value);
			}
		}

		public int querySubtree(int head) {
			int ans = val[head];
			for (int next : tree[head]) {
				ans += querySubtree(next);
			}
			return ans;
		}

		public void addChain(int a, int b, int v) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			while (!path.containsKey(b)) {
				val[b] += v;
				b = fa[b];
			}
			val[b] += v;
			while (path.get(b) != null) {
				b = path.get(b);
				val[b] += v;
			}
		}

		public int queryChain(int a, int b) {
			path.clear();
			path.put(a, null);
			while (a != fa[a]) {
				path.put(fa[a], a);
				a = fa[a];
			}
			int ans = 0;
			while (!path.containsKey(b)) {
				ans += val[b];
				b = fa[b];
			}
			ans += val[b];
			while (path.get(b) != null) {
				b = path.get(b);
				ans += val[b];
			}
			return ans;
		}

	}

	// 为了测试
	// 随机生成N个节点树，可能是多叉树，并且一定不是森林
	// 输入参数N要大于0
	public static int[] generateFatherArray(int N) {
		int[] order = new int[N];
		for (int i = 0; i < N; i++) {
			order[i] = i;
		}
		for (int i = N - 1; i >= 0; i--) {
			swap(order, i, (int) (Math.random() * (i + 1)));
		}
		int[] ans = new int[N];
		ans[order[0]] = order[0];
		for (int i = 1; i < N; i++) {
			ans[order[i]] = order[(int) (Math.random() * i)];
		}
		return ans;
	}

	// 为了测试
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 为了测试
	public static int[] generateValueArray(int N, int V) {
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = (int) (Math.random() * V) + 1;
		}
		return ans;
	}

	// 对数器
	public static void main(String[] args) {
		int N = 50000;
		int V = 100000;
		int[] father = generateFatherArray(N);
		int[] values = generateValueArray(N, V);
		TreeChain tc = new TreeChain(father, values);
		Right right = new Right(father, values);
		int testTime = 1000000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			double decision = Math.random();
			if (decision < 0.25) {
				int head = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addSubtree(head, value);
				right.addSubtree(head, value);
			} else if (decision < 0.5) {
				int head = (int) (Math.random() * N);
				if (tc.querySubtree(head) != right.querySubtree(head)) {
					System.out.println("出错了!");
				}
			} else if (decision < 0.75) {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				int value = (int) (Math.random() * V);
				tc.addChain(a, b, value);
				right.addChain(a, b, value);
			} else {
				int a = (int) (Math.random() * N);
				int b = (int) (Math.random() * N);
				if (tc.queryChain(a, b) != right.queryChain(a, b)) {
					System.out.println("出错了!");
				}
			}
		}
		System.out.println("测试结束");
	}

}
