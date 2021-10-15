package class47;

// 同时支持范围增加 + 范围修改 + 范围查询的动态开点线段树（累加和）
public class Code02_DynamicSegmentTree {

	public static class Node {
		public int sum;
		public int lazy;
		public int change;
		public boolean update;
		public Node left;
		public Node right;

		public Node() {
			sum = 0;
			lazy = 0;
			change = 0;
			update = false;
			left = null;
			right = null;
		}
	}

	public static class DynamicSegmentTree {
		public Node root;
		public int size;

		public DynamicSegmentTree(int max) {
			root = new Node();
			size = max;
		}

		private void pushUp(Node c) {
			c.sum = c.left.sum + c.right.sum;
		}

		private void pushDown(Node p, int ln, int rn) {
			if (p.left == null) {
				p.left = new Node();
			}
			if (p.right == null) {
				p.right = new Node();
			}
			if (p.update) {
				p.left.update = true;
				p.right.update = true;
				p.left.change = p.change;
				p.right.change = p.change;
				p.left.lazy = 0;
				p.right.lazy = 0;
				p.left.sum = p.change * ln;
				p.right.sum = p.change * rn;
				p.update = false;
			}
			if (p.lazy != 0) {
				p.left.lazy += p.lazy;
				p.right.lazy += p.lazy;
				p.left.sum += p.lazy * ln;
				p.right.sum += p.lazy * rn;
				p.lazy = 0;
			}
		}

		public void update(int s, int e, int v) {
			update(root, 1, size, s, e, v);
		}

		private void update(Node c, int l, int r, int s, int e, int v) {
			if (s <= l && r <= e) {
				c.update = true;
				c.change = v;
				c.sum = v * (r - l + 1);
				c.lazy = 0;
			} else {
				int mid = (l + r) >> 1;
				pushDown(c, mid - l + 1, r - mid);
				if (s <= mid) {
					update(c.left, l, mid, s, e, v);
				}
				if (e > mid) {
					update(c.right, mid + 1, r, s, e, v);
				}
				pushUp(c);
			}
		}

		public void add(int s, int e, int v) {
			add(root, 1, size, s, e, v);
		}

		private void add(Node c, int l, int r, int s, int e, int v) {
			if (s <= l && r <= e) {
				c.sum += v * (r - l + 1);
				c.lazy += v;
			} else {
				int mid = (l + r) >> 1;
				pushDown(c, mid - l + 1, r - mid);
				if (s <= mid) {
					add(c.left, l, mid, s, e, v);
				}
				if (e > mid) {
					add(c.right, mid + 1, r, s, e, v);
				}
				pushUp(c);
			}
		}

		public int query(int s, int e) {
			return query(root, 1, size, s, e);
		}

		private int query(Node c, int l, int r, int s, int e) {
			if (s <= l && r <= e) {
				return c.sum;
			}
			int mid = (l + r) >> 1;
			pushDown(c, mid - l + 1, r - mid);
			int ans = 0;
			if (s <= mid) {
				ans += query(c.left, l, mid, s, e);
			}
			if (e > mid) {
				ans += query(c.right, mid + 1, r, s, e);
			}
			return ans;
		}

	}

	public static class Right {
		public int[] arr;

		public Right(int size) {
			arr = new int[size + 1];
		}

		public void add(int s, int e, int v) {
			for (int i = s; i <= e; i++) {
				arr[i] += v;
			}
		}

		public void update(int s, int e, int v) {
			for (int i = s; i <= e; i++) {
				arr[i] = v;
			}
		}

		public int query(int s, int e) {
			int sum = 0;
			for (int i = s; i <= e; i++) {
				sum += arr[i];
			}
			return sum;
		}

	}

	public static void main(String[] args) {
		int n = 1000;
		int value = 50;
		int createTimes = 5000;
		int operateTimes = 5000;
		System.out.println("测试开始");
		for (int i = 0; i < createTimes; i++) {
			int size = (int) (Math.random() * n) + 1;
			DynamicSegmentTree dst = new DynamicSegmentTree(size);
			Right right = new Right(size);
			for (int k = 0; k < operateTimes; k++) {
				double choose = Math.random();
				if (choose < 0.333) {
					int a = (int) (Math.random() * size) + 1;
					int b = (int) (Math.random() * size) + 1;
					int s = Math.min(a, b);
					int e = Math.max(a, b);
					int v = (int) (Math.random() * value);
					dst.update(s, e, v);
					right.update(s, e, v);
				} else if (choose < 0.666) {
					int a = (int) (Math.random() * size) + 1;
					int b = (int) (Math.random() * size) + 1;
					int s = Math.min(a, b);
					int e = Math.max(a, b);
					int v = (int) (Math.random() * value);
					dst.add(s, e, v);
					right.add(s, e, v);
				} else {
					int a = (int) (Math.random() * size) + 1;
					int b = (int) (Math.random() * size) + 1;
					int s = Math.min(a, b);
					int e = Math.max(a, b);
					int ans1 = dst.query(s, e);
					int ans2 = right.query(s, e);
					if (ans1 != ans2) {
						System.out.println("出错了!");
						System.out.println(ans1);
						System.out.println(ans2);
					}
				}
			}
		}
		System.out.println("测试结束");
	}

}
