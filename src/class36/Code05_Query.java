package class36;

// 来自美团
public class Code05_Query {

	public static class SegmentTree {
		private int[] max;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int N) {
			max = new int[N << 2];
			change = new int[N << 2];
			update = new boolean[N << 2];
			for (int i = 0; i < max.length; i++) {
				max[i] = Integer.MIN_VALUE;
			}
		}

		private void pushUp(int rt) {
			max[rt] = Math.max(max[rt << 1], max[rt << 1 | 1]);
		}

		// ln表示左子树元素结点个数，rn表示右子树结点个数
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

	public static class Query {
		public int[] sum1;
		public int[] sum2;
		public SegmentTree st;
		public int m;

		public Query(int[] arr) {
			int n = arr.length;
			m = arr.length + 1;
			sum1 = new int[m];
			sum2 = new int[m];
			st = new SegmentTree(m);
			for (int i = 0; i < n; i++) {
				sum1[i + 1] = sum1[i] + arr[i];
				sum2[i + 1] = sum2[i] + arr[i] * arr[i];
				st.update(i + 1, i + 1, arr[i], 1, m, 1);
			}

		}

		public int querySum(int L, int R) {
			return sum1[R] - sum1[L - 1];
		}

		public int queryAim(int L, int R) {
			int sumPower2 = querySum(L, R);
			sumPower2 *= sumPower2;
			return sum2[R] - sum2[L - 1] + (R - L - 1) * sumPower2;
		}

		public int queryMax(int L, int R) {
			return st.query(L, R, 1, m, 1);
		}

	}

	public static void main(String[] args) {
		int[] arr = { 1, 1, 2, 3 };
		Query q = new Query(arr);
		System.out.println(q.querySum(1, 3));
		System.out.println(q.queryAim(2, 4));
		System.out.println(q.queryMax(1, 4));

	}

}
