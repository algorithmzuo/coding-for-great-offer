package class01;

import java.util.Arrays;

/*
 * 给定两个数组x和hp，长度都是N。
 * x数组一定是有序的，x[i]表示i号怪兽在x轴上的位置
 * hp数组不要求有序，hp[i]表示i号怪兽的血量 
 * 为了方便起见，可以认为x数组和hp数组中没有负数。
 * 再给定一个正数range，表示如果法师释放技能的范围长度(直径！)
 * 被打到的每只怪兽损失1点血量。
 * 返回要把所有怪兽血量清空，至少需要释放多少次aoe技能？
 * 三个参数：int[] x, int[] hp, int range
 * 返回：int 次数
 * */
public class Code06_AOE {

	// 纯暴力解法
	// 太容易超时
	// 只能小样本量使用
	public static int minAoe1(int[] x, int[] hp, int range) {
		boolean allClear = true;
		for (int i = 0; i < hp.length; i++) {
			if (hp[i] > 0) {
				allClear = false;
				break;
			}
		}
		if (allClear) {
			return 0;
		} else {
			int ans = Integer.MAX_VALUE;
			for (int left = 0; left < x.length; left++) {
				if (hasHp(x, hp, left, range)) {
					minusOneHp(x, hp, left, range);
					ans = Math.min(ans, 1 + minAoe1(x, hp, range));
					addOneHp(x, hp, left, range);
				}
			}
			return ans;
		}
	}

	public static boolean hasHp(int[] x, int[] hp, int left, int range) {
		for (int index = left; index < x.length && x[index] - x[left] <= range; index++) {
			if (hp[index] > 0) {
				return true;
			}
		}
		return false;
	}

	public static void minusOneHp(int[] x, int[] hp, int left, int range) {
		for (int index = left; index < x.length && x[index] - x[left] <= range; index++) {
			hp[index]--;
		}
	}

	public static void addOneHp(int[] x, int[] hp, int left, int range) {
		for (int index = left; index < x.length && x[index] - x[left] <= range; index++) {
			hp[index]++;
		}
	}

	// 为了验证
	// 不用线段树，但是贪心的思路，和课上一样
	// 1) 总是用技能的最左边缘刮死当前最左侧的没死的怪物
	// 2) 然后向右找下一个没死的怪物，重复步骤1)
	public static int minAoe2(int[] x, int[] hp, int range) {
		int n = x.length;
        // cover[i]表示元素i为AOE最左侧点时，能否覆盖的最右侧元素的位置
		int[] cover = new int[n];
		int r = 0;
		for (int i = 0; i < n; i++) {
			while (r < n && x[r] - x[i] <= range) {
				r++;
			}
			cover[i] = r;
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			if (hp[i] > 0) {
				int minus = hp[i];
				for (int index = i; index <= cover[i]; index++) {
					hp[index] -= minus;
				}
				ans += minus;
			}
		}
		return ans;
	}

	// 正式方法
	// 关键点就是：
	// 1) 线段树
	// 2) 总是用技能的最左边缘刮死当前最左侧的没死的怪物
	// 3) 然后向右找下一个没死的怪物，重复步骤2)
	public static int minAoe3(int[] x, int[] hp, int range) {
		int n = x.length;
		int[] cover = new int[n];
		int r = 0;
		// cover[i] : 如果i位置是技能的最左侧，技能往右的range范围内，最右影响到哪
		for (int i = 0; i < n; i++) {
			while (r < n && x[r] - x[i] <= range) {
				r++;
			}
			cover[i] = r - 1;
		}
		SegmentTree st = new SegmentTree(hp);
		st.build(1, n, 1);
		int ans = 0;
		for (int i = 1; i <= n; i++) {
			int leftHP = st.query(i, i, 1, n, 1);
			if (leftHP > 0) {
				ans += leftHP;
				st.add(i, cover[i - 1] + 1, -leftHP, 1, n, 1);
			}
		}
		return ans;
	}

	public static class SegmentTree {
		private int MAXN;
		private int[] arr;
		private int[] sum;
		private int[] lazy;

		public SegmentTree(int[] origin) {
			MAXN = origin.length + 1;
			arr = new int[MAXN];
			for (int i = 1; i < MAXN; i++) {
				arr[i] = origin[i - 1];
			}
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
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

	// 为了测试
	public static int[] randomArray(int n, int valueMax) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * valueMax) + 1;
		}
		return ans;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int N = arr.length;
		int[] ans = new int[N];
		for (int i = 0; i < N; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 50;
		int X = 500;
		int H = 60;
		int R = 10;
		int testTime = 50000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[] x2 = randomArray(len, X);
			Arrays.sort(x2);
			int[] hp2 = randomArray(len, H);
			int[] x3 = copyArray(x2);
			int[] hp3 = copyArray(hp2);
			int range = (int) (Math.random() * R) + 1;
			int ans2 = minAoe2(x2, hp2, range);
			int ans3 = minAoe3(x3, hp3, range);
			if (ans2 != ans3) {
				System.out.println("出错了！");
			}
		}
		System.out.println("测试结束");
	}

}
