package class21;

import java.util.HashSet;

public class Code01_TarjanAndDisjointSetsForLCA {

	// tree是以为数组，大小为N，表示一共有N个节点，编号为0~N-1
	// tree[i] = j 表示点i的父亲是点j，
	// tree可以保证一定是二叉树或者多叉树，且一定不是森林
	//
	// queries是二维数组，大小为M*2，比如
	// [
	// [4,9], 想查询4和9之间的最低公共祖先
	// [3,7], 想查询3和7之间的最低公共祖先
	// [8,1], 想查询8和1之间的最低公共祖先
	// ...
	// ]
	//
	// tree数组和queries数组里面的所有值，都一定保证在0~N-1之间
	// 要求返回一个一维数组，大小为M，表示每一条查询的答案

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

	// 最优解
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
			if (!equal(ans1, ans2)) {
				System.out.println("出错了！");
			}
		}
		System.out.println("功能测试结束");

		System.out.println("===============");

		System.out.println("性能测试开始");
		System.out.println("如果树呈现链状，方法1会特别慢");
		System.out.println("不过该文件中生成树的方式很难让其变成链状");
		System.out.println("所以性能差异并不明显");
		System.out.println("但是在查询条数很多时，可以看到方法2还是比方法1块");

		int size = 10000;
		int ques = 1000000;
		System.out.println("节点个数 : " + size + ", 查询语句条数 : " + ques);

		int[] tree = generateTreeArray(size);
		int[][] queries = generateQueries(ques, size);
		long start;
		long end;

		start = System.currentTimeMillis();
		query1(tree, queries);
		end = System.currentTimeMillis();
		System.out.println("方法1运行时间(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		query2(tree, queries);
		end = System.currentTimeMillis();
		System.out.println("方法2运行时间(毫秒) : " + (end - start));

		System.out.println("性能测试结束");

	}

}
