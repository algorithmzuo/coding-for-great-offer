package class53;

import java.util.Arrays;

// 来自真实面试，同学给我的问题
// arr数组长度为n, magic数组长度为m
// 含义：
// arr = { 3, 1, 4, 5, 7 }
// 如果完全不改变arr中的值，那么收益就是累加和 = 3 + 1 + 4 + 5 + 7 = 20
// magics = {
//      {0,2,5} 表示arr[0~2]中的任何一个值，都能改成5
//      {3,4,3} 表示arr[3~4]中的任何一个值，都能改成3
//      {1,3,7} 表示arr[1~3]中的任何一个值，都能改成7
// }
// 就是说，magics中的任何一组数据{a,b,c}，都表示一种操作，
// 你可以选择arr[a~b]中任何一个数字，变成c。
// 并且每一种操作，都可以执行任意次
// 其中 0 <= a <= b < n
// 那么经过若干次的魔法操作，你当然可能得到arr的更大的累加和
// 返回arr尽可能大的累加和
// n <= 10^7
// m <= 10^6
// arr中的值和c的范围 <= 10^12
public class Code03_MagicSum {

	// 暴力解，写出来为了验证正式方法而已
	public static int maxSum1(int[] arr, int[][] magics) {
		int[] help = Arrays.copyOf(arr, arr.length);
		for (int[] m : magics) {
			int l = m[0];
			int r = m[1];
			int c = m[2];
			for (int i = l; i <= r; i++) {
				help[i] = Math.max(help[i], c);
			}
		}
		int sum = 0;
		for (int num : help) {
			sum += num;
		}
		return sum;
	}

	// O(N) + O(M * logM) + O(M * logN) + O(N * logN)
	public static int maxSum2(int[] arr, int[][] magics) {
		int n = arr.length;
		SegmentTree2 st = new SegmentTree2(n);
		Arrays.sort(magics, (a, b) -> (a[2] - b[2]));
		for (int[] magic : magics) {
			st.update(magic[0] + 1, magic[1] + 1, magic[2], 1, n, 1);
		}
		int ans = 0;
		for (int i = 0; i < n; i++) {
			ans += Math.max(st.query(i + 1, i + 1, 1, n, 1), arr[i]);
		}
		return ans;
	}

	// 这是一棵普通的线段树
	// 区间上维持最大值的线段树
	// 支持区间值更新
	// 支持区间最大值查询
	public static class SegmentTree2 {
		private int[] max;
		private int[] change;
		private boolean[] update;

		public SegmentTree2(int size) {
			int N = size + 1;
			max = new int[N << 2];
			change = new int[N << 2];
			update = new boolean[N << 2];
		}

		private void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				max[rt << 1] = change[rt];
				max[rt << 1 | 1] = change[rt];
				update[rt] = false;
			}
		}

		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				max[rt] = C;
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

		public int query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return max[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			int left = 0;
			int right = 0;
			if (L <= mid) {
				left = query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				right = query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return Math.max(left, right);
		}

	}

	// O(N) + O(M * logM) + O(M * logN) + O(N)
	public static int maxSum3(int[] arr, int[][] magics) {
		int n = arr.length;
		SegmentTree3 st = new SegmentTree3(n);
		Arrays.sort(magics, (a, b) -> (a[2] - b[2]));
		for (int[] magic : magics) {
			st.update(magic[0] + 1, magic[1] + 1, magic[2], 1, n, 1);
		}
		int ans = 0;
		int[] query = st.buildSingleQuery(n);
		for (int i = 0; i < n; i++) {
			ans += Math.max(query[i], arr[i]);
		}
		return ans;
	}

	// 为方法三特别定制的线段树
	// 区间上维持最大值的线段树
	// 支持区间值更新
	// 为本道题定制了一个方法：
	// 假设全是单点查询，请统一返回所有单点的结果(一个结果数组，里面有所有单点记录)
	public static class SegmentTree3 {
		private int[] max;
		private int[] change;
		private boolean[] update;

		public SegmentTree3(int size) {
			int N = size + 1;
			max = new int[N << 2];
			change = new int[N << 2];
			update = new boolean[N << 2];
		}

		private void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				max[rt << 1] = change[rt];
				max[rt << 1 | 1] = change[rt];
				update[rt] = false;
			}
		}

		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				max[rt] = C;
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

		public int index = 0;

		public int[] buildSingleQuery(int n) {
			int[] ans = new int[n + 1];
			process(ans, 1, n, 1);
			return ans;
		}

		private void process(int[] ans, int l, int r, int rt) {
			if (l == r) {
				ans[index++] = max[rt];
			} else {
				int mid = (l + r) >> 1;
				pushDown(rt, mid - l + 1, r - mid);
				process(ans, l, mid, rt << 1);
				process(ans, mid + 1, r, rt << 1 | 1);
			}
		}

	}

	// 为了测试
	public static int[] generateRandomArray(int n, int value) {
		int[] arr = new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = (int) (Math.random() * value) + 1;
		}
		return arr;
	}

	// 为了测试
	public static int[][] generateRandomMagics(int n, int m, int value) {
		int[][] magics = new int[m][3];
		for (int[] magic : magics) {
			int a = (int) (Math.random() * n);
			int b = (int) (Math.random() * n);
			int c = (int) (Math.random() * value) + 1;
			magic[0] = Math.min(a, b);
			magic[1] = Math.max(a, b);
			magic[2] = c;
		}
		return magics;
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 30;
		int m = 15;
		int v = 1000;
		int testTimes = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int N = (int) (Math.random() * n) + 1;
			int M = (int) (Math.random() * m) + 1;
			int[] arr = generateRandomArray(N, v);
			int[][] magics = generateRandomMagics(N, M, v);
			int ans1 = maxSum1(arr, magics);
			int ans2 = maxSum2(arr, magics);
			int ans3 = maxSum3(arr, magics);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				for (int[] magic : magics) {
					System.out.println("[ " + magic[0] + " , " + magic[1] + " , " + magic[2] + " ] ");
				}
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");

		System.out.println("性能测试开始");
		System.out.println("n的数据量将达到10^7");
		System.out.println("m的数据量将达到10^6");
		System.out.println("为了防止溢出，每个值的范围控制在10以内");

		n = 10000000;
		m = 1000000;
		v = 10;

		int[] arr = generateRandomArray(n, v);
		int[][] magics = generateRandomMagics(n, m, v);

		long start;
		long end;

		start = System.currentTimeMillis();
		int ans2 = maxSum2(arr, magics);
		end = System.currentTimeMillis();
		System.out.println("方法二的结果 : " + ans2 + ", 方法二的运行时间: " + (end - start) + " 毫秒");

		start = System.currentTimeMillis();
		int ans3 = maxSum3(arr, magics);
		end = System.currentTimeMillis();
		System.out.println("方法三的结果 : " + ans3 + ", 方法三的运行时间: " + (end - start) + " 毫秒");

		System.out.println("性能测试结束");

	}

}
