package class01;

import java.util.Arrays;

/*
 * 给定两个数组x和hp，长度都是N。
 * x数组一定是有序的，x[i]表示i号怪兽在x轴上的位置；hp数组不要求有序，hp[i]表示i号怪兽的血量 
 * 为了方便起见，可以认为x数组和hp数组中没有负数。
 * 再给定一个正数range，表示如果法师站在x位置，
 * 那么法师可以用aoe技能打到[x-range,x+range]范围上的怪兽，被打到的每只怪兽损失1点血量。
 * 返回要把所有怪兽血量清空，至少需要释放多少次aoe技能？
 * 三个参数：int[] x, int[] hp, int range
 * 返回：int 次数
 * 
 * */
public class Code06_AOE {

	// 纯暴力解法
	public static int minAoe1(int[] x, int[] hp, int range) {
		int N = x.length;
		int[] coverLeft = new int[N];
		int[] coverRight = new int[N];
		int left = 0;
		int right = 0;
		for (int i = 0; i < N; i++) {
			while (x[i] - x[left] > range) {
				left++;
			}
			while (right < N && x[right] - x[i] <= range) {
				right++;
			}
			coverLeft[i] = left;
			coverRight[i] = right - 1;
		}
		return process(hp, coverLeft, coverRight);
	}

	public static int process(int[] hp, int[] coverLeft, int[] coverRight) {
		int N = hp.length;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			for (int f = coverLeft[i]; f <= coverRight[i]; f++) {
				if (hp[f] > 0) {
					int[] next = aoe(hp, coverLeft[i], coverRight[i]);
					ans = Math.min(ans, 1 + process(next, coverLeft, coverRight));
					break;
				}
			}
		}
		return ans == Integer.MAX_VALUE ? 0 : ans;
	}

	public static int[] aoe(int[] hp, int L, int R) {
		int N = hp.length;
		int[] next = new int[N];
		for (int i = 0; i < N; i++) {
			next[i] = hp[i];
		}
		for (int i = L; i <= R; i++) {
			next[i] -= next[i] > 0 ? 1 : 0;
		}
		return next;
	}

	// 贪心策略：永远让最左边缘最优的变成0，也就是选择：
	// 一定能覆盖到最左边缘, 但是尽量靠右的中心点
	// 等到最左边缘变成0之后，再去找下一个最左边缘...
	public static int minAoe2(int[] x, int[] hp, int range) {
		int N = x.length;
		int ans = 0;
		for (int i = 0; i < N; i++) {
			if (hp[i] > 0) {
				int triggerPost = i;
				while (triggerPost < N && x[triggerPost] - x[i] <= range) {
					triggerPost++;
				}
				ans += hp[i];
				aoe(x, hp, i, triggerPost - 1, range);
			}
		}
		return ans;
	}

	public static void aoe(int[] x, int[] hp, int L, int trigger, int range) {
		int N = x.length;
		int RPost = trigger;
		while (RPost < N && x[RPost] - x[trigger] <= range) {
			RPost++;
		}
		int minus = hp[L];
		for (int i = L; i < RPost; i++) {
			hp[i] = Math.max(0, hp[i] - minus);
		}
	}

	// 贪心策略和方法二一样，但是需要用线段树，可优化成O(N * logN)的方法，
	public static int minAoe3(int[] x, int[] hp, int range) {
		int N = x.length;
		// coverLeft[i]：如果以i为中心点放技能，左侧能影响到哪，下标从1开始，不从0开始
		// coverRight[i]：如果以i为中心点放技能，右侧能影响到哪，下标从1开始，不从0开始
		int[] coverLeft = new int[N + 1];
		int[] coverRight = new int[N + 1];
		int left = 0;
		int right = 0;
		for (int i = 0; i < N; i++) {
			while (x[i] - x[left] > range) {
				left++;
			}
			while (right < N && x[right] - x[i] <= range) {
				right++;
			}
			coverLeft[i + 1] = left + 1;
			coverRight[i + 1] = right;
		}
		// best[i]: 如果i是最左边缘点，选哪个点做技能中心点最好，下标从1开始，不从0开始
		int[] best = new int[N + 1];
		int trigger = 0;
		for (int i = 0; i < N; i++) {
			while (trigger < N && x[trigger] - x[i] <= range) {
				trigger++;
			}
			best[i + 1] = trigger;
		}
		SegmentTree st = new SegmentTree(hp);
		st.build(1, N, 1);
		int ans = 0;
		for (int i = 1; i <= N; i++) {
			long leftEdge = st.query(i, i, 1, N, 1);
			if (leftEdge > 0) {
				ans += leftEdge;
				int t = best[i];
				int l = coverLeft[t];
				int r = coverRight[t];
				st.add(l, r, (int) (-leftEdge), 1, N, 1);
			}
		}
		return ans;
	}

	public static class SegmentTree {
		// arr[]为原序列的信息从0开始，但在arr里是从1开始的
		// sum[]模拟线段树维护区间和
		// lazy[]为累加懒惰标记
		// change[]为更新的值
		// update[]为更新慵懒标记
		private int MAXN;
		private int[] arr;
		private int[] sum;
		private int[] lazy;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int[] origin) {
			MAXN = origin.length + 1;
			arr = new int[MAXN]; // arr[0] 不用 从1开始使用
			for (int i = 1; i < MAXN; i++) {
				arr[i] = origin[i - 1];
			}
			sum = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围的累加和信息

			lazy = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围沒有往下傳遞的纍加任務
			change = new int[MAXN << 2]; // 用来支持脑补概念中，某一个范围有没有更新操作的任务
			update = new boolean[MAXN << 2]; // 用来支持脑补概念中，某一个范围更新任务，更新成了什么
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		// 之前的，所有懒增加，和懒更新，从父范围，发给左右两个子范围
		// 分发策略是什么
		// ln表示左子树元素结点个数，rn表示右子树结点个数
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

		// 在初始化阶段，先把sum数组，填好
		// 在arr[l~r]范围上，去build，1~N，
		// rt : 这个范围在sum中的下标
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
			// 当前任务躲不掉，无法懒更新，要往下发
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

		// L..R -> 任务范围 ,所有的值累加上C
		// l,r -> 表达的范围
		// rt 去哪找l，r范围上的信息
		public void add(int L, int R, int C, int l, int r, int rt) {
			// 任务的范围彻底覆盖了，当前表达的范围
			if (L <= l && r <= R) {
				sum[rt] += C * (r - l + 1);
				lazy[rt] += C;
				return;
			}
			// 任务并没有把l...r全包住
			// 要把当前任务往下发
			// 任务 L, R 没有把本身表达范围 l,r 彻底包住
			int mid = (l + r) >> 1; // l..mid (rt << 1) mid+1...r(rt << 1 | 1)
			// 下发之前所有攒的懒任务
			pushDown(rt, mid - l + 1, r - mid);
			// 左孩子是否需要接到任务
			if (L <= mid) {
				add(L, R, C, l, mid, rt << 1);
			}
			// 右孩子是否需要接到任务
			if (R > mid) {
				add(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			// 左右孩子做完任务后，我更新我的sum信息
			pushUp(rt);
		}

		// 1~6 累加和是多少？ 1~8 rt
		public long query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return sum[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			long ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}

	}

	// for test
	public static int[] randomArray(int n, int valueMax) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * valueMax) + 1;
		}
		return ans;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		int N = arr.length;
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 500;
		int X = 10000;
		int H = 50;
		int R = 10;
		int time = 5000;
		System.out.println("test begin");
		for (int i = 0; i < time; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] x = randomArray(len, X);
			Arrays.sort(x);
			int[] hp = randomArray(len, H);
			int range = (int) (Math.random() * R) + 1;
			int[] x2 = copyArray(x);
			int[] hp2 = copyArray(hp);
			int ans2 = minAoe2(x2, hp2, range);
			// 已经测过下面注释掉的内容，注意minAoe1非常慢，
			// 所以想加入对比需要把数据量(N, X, H, R, time)改小
//			int[] x1 = copyArray(x);
//			int[] hp1 = copyArray(hp);
//			int ans1 = minAoe1(x1, hp1, range);
//			if (ans1 != ans2) {
//				System.out.println("Oops!");
//				System.out.println(ans1 + "," + ans2);
//			}
			int[] x3 = copyArray(x);
			int[] hp3 = copyArray(hp);
			int ans3 = minAoe3(x3, hp3, range);
			if (ans2 != ans3) {
				System.out.println("Oops!");
				System.out.println(ans2 + "," + ans3);
			}
		}
		System.out.println("test end");
	}

}
