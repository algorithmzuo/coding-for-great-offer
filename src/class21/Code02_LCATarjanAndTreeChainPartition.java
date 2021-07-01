package class21;

import java.util.HashSet;

public class Code02_LCATarjanAndTreeChainPartition {

	// 给定数组tree大小为N，表示一共有N个节点
	// tree[i] = j 表示点i的父亲是点j，tree一定是一棵树而不是森林
	// queries是二维数组，大小为M*2，每一个长度为2的数组都表示一条查询
	// [4,9], 表示想查询4和9之间的最低公共祖先…
	// [3,7], 表示想查询3和7之间的最低公共祖先…
	// tree和queries里面的所有值，都一定在0~N-1之间
	// 返回一个数组ans，大小为M，ans[i]表示第i条查询的答案

	// 暴力方法
	public static int[] query1(int[] tree, int[][] queries) {
		int M = queries.length;
		int[] ans = new int[M];
		HashSet<Integer> path = new HashSet<>();
		for (int i = 0; i < M; i++) {
			int jump = queries[i][0];
			while (tree[jump] != jump) {
				path.add(jump);
				jump = tree[jump];
			}
			path.add(jump);
			jump = queries[i][1];
			while (!path.contains(jump)) {
				jump = tree[jump];
			}
			ans[i] = jump;
			path.clear();
		}
		return ans;
	}

	// 离线批量查询最优解 -> Tarjan + 并查集
	// 如果有M条查询，时间复杂度O(N + M)
	// 但是必须把M条查询一次给全，不支持在线查询
	public static int[] query2(int[] tree, int[][] queries) {
		int N = tree.length;
		int M = queries.length;
		int[] help = new int[N];
		int h = 0;
		for (int i = 0; i < N; i++) {
			if (tree[i] == i) {
				h = i;
			} else {
				help[tree[i]]++;
			}
		}
		int[][] mt = new int[N][];
		for (int i = 0; i < N; i++) {
			mt[i] = new int[help[i]];
		}
		for (int i = 0; i < N; i++) {
			if (i != h) {
				mt[tree[i]][--help[tree[i]]] = i;
			}
		}
		for (int i = 0; i < M; i++) {
			if (queries[i][0] != queries[i][1]) {
				help[queries[i][0]]++;
				help[queries[i][1]]++;
			}
		}
		int[][] mq = new int[N][];
		int[][] mi = new int[N][];
		for (int i = 0; i < N; i++) {
			mq[i] = new int[help[i]];
			mi[i] = new int[help[i]];
		}
		for (int i = 0; i < M; i++) {
			if (queries[i][0] != queries[i][1]) {
				mq[queries[i][0]][--help[queries[i][0]]] = queries[i][1];
				mi[queries[i][0]][help[queries[i][0]]] = i;
				mq[queries[i][1]][--help[queries[i][1]]] = queries[i][0];
				mi[queries[i][1]][help[queries[i][1]]] = i;
			}
		}
		int[] ans = new int[M];
		UnionFind uf = new UnionFind(N);
		process(h, mt, mq, mi, uf, ans);
		for (int i = 0; i < M; i++) {
			if (queries[i][0] == queries[i][1]) {
				ans[i] = queries[i][0];
			}
		}
		return ans;
	}

	public static void process(int head, int[][] mt, int[][] mq, int[][] mi, UnionFind uf, int[] ans) {
		for (int next : mt[head]) {
			process(next, mt, mq, mi, uf, ans);
			uf.union(head, next);
			uf.setTag(head, head);
		}
		int[] q = mq[head];
		int[] i = mi[head];
		for (int k = 0; k < q.length; k++) {
			int tag = uf.getTag(q[k]);
			if (tag != -1) {
				ans[i[k]] = tag;
			}
		}
	}

	public static class UnionFind {
		private int[] f;
		private int[] s;
		private int[] t;
		private int[] h;

		public UnionFind(int N) {
			f = new int[N];
			s = new int[N];
			t = new int[N];
			h = new int[N];
			for (int i = 0; i < N; i++) {
				f[i] = i;
				s[i] = 1;
				t[i] = -1;
			}
		}

		private int find(int i) {
			int j = 0;
			while (i != f[i]) {
				h[j++] = i;
				i = f[i];
			}
			while (j > 0) {
				h[--j] = i;
			}
			return i;
		}

		public void union(int i, int j) {
			int fi = find(i);
			int fj = find(j);
			if (fi != fj) {
				int si = s[fi];
				int sj = s[fj];
				int m = si >= sj ? fi : fj;
				int l = m == fi ? fj : fi;
				f[l] = m;
				s[m] += s[l];
			}
		}

		public void setTag(int i, int tag) {
			t[find(i)] = tag;
		}

		public int getTag(int i) {
			return t[find(i)];
		}

	}

	// 在线查询最优解 -> 树链剖分
	// 空间复杂度O(N), 支持在线查询，单次查询时间复杂度O(logN)
	// 如果有M次查询，时间复杂度O(N + M * logN)
	public static int[] query3(int[] tree, int[][] queries) {
		TreeChain tc = new TreeChain(tree);
		int M = queries.length;
		int[] ans = new int[M];
		for (int i = 0; i < M; i++) {
			if (queries[i][0] == queries[i][1]) {
				ans[i] = queries[i][0];
			} else {
				ans[i] = tc.lca(queries[i][0], queries[i][1]);
			}
		}
		return ans;
	}

	public static class TreeChain {
		private int n;
		private int h;
		private int[][] tree;
		private int[] fa;
		private int[] dep;
		private int[] son;
		private int[] siz;
		private int[] top;

		public TreeChain(int[] father) {
			initTree(father);
			dfs1(h, 0);
			dfs2(h, h);
		}

		private void initTree(int[] father) {
			n = father.length + 1;
			tree = new int[n][];
			fa = new int[n];
			dep = new int[n];
			son = new int[n];
			siz = new int[n];
			top = new int[n--];
			int[] cnum = new int[n];
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
			top[u] = t;
			if (son[u] != 0) {
				dfs2(son[u], t);
				for (int v : tree[u]) {
					if (v != son[u]) {
						dfs2(v, v);
					}
				}
			}
		}

		public int lca(int a, int b) {
			a++;
			b++;
			while (top[a] != top[b]) {
				if (dep[top[a]] > dep[top[b]]) {
					a = fa[top[a]];
				} else {
					b = fa[top[b]];
				}
			}
			return (dep[a] < dep[b] ? a : b) - 1;
		}
	}

	// 为了测试
	// 随机生成N个节点树，可能是多叉树，并且一定不是森林
	// 输入参数N要大于0
	public static int[] generateTreeArray(int N) {
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
	// 随机生成M条查询，点有N个，点的编号在0~N-1之间
	// 输入参数M和N都要大于0
	public static int[][] generateQueries(int M, int N) {
		int[][] ans = new int[M][2];
		for (int i = 0; i < M; i++) {
			ans[i][0] = (int) (Math.random() * N);
			ans[i][1] = (int) (Math.random() * N);
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
	public static boolean equal(int[] a, int[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 1000;
		int M = 200;
		int testTime = 50000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int size = (int) (Math.random() * N) + 1;
			int ques = (int) (Math.random() * M) + 1;
			int[] tree = generateTreeArray(size);
			int[][] queries = generateQueries(ques, size);
			int[] ans1 = query1(tree, queries);
			int[] ans2 = query2(tree, queries);
			int[] ans3 = query3(tree, queries);
			if (!equal(ans1, ans2) || !equal(ans1, ans3)) {
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("功能测试结束");
	}

}
