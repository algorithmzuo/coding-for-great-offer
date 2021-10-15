package class47;

// 只支持单点增加 + 范围查询的动态开点线段树
public class Code01_DynamicSegmentTree {

	public static class Node {
		public int sum;
		public Node left;
		public Node right;

		public Node() {
			sum = 0;
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

		public void add(int i, int v) {
			add(root, 1, size, i, v);
		}

		private void add(Node c, int l, int r, int i, int v) {
			if (l == r) {
				c.sum += v;
			} else {
				int mid = (l + r) / 2;
				if (i <= mid) {
					if (c.left == null) {
						c.left = new Node();
					}
					add(c.left, l, mid, i, v);
				} else {
					if (c.right == null) {
						c.right = new Node();
					}
					add(c.right, mid + 1, r, i, v);
				}
				c.sum = (c.left != null ? c.left.sum : 0) + (c.right != null ? c.right.sum : 0);
			}
		}

		public int query(int s, int e) {
			return query(root, 1, size, s, e);
		}

		private int query(Node c, int l, int r, int s, int e) {
			if (c == null) {
				return 0;
			}
			if (s <= l && r <= e) {
				return c.sum;
			}
			int mid = (l + r) / 2;
			if (e <= mid) {
				return query(c.left, l, mid, s, e);
			} else if (s > mid) {
				return query(c.right, mid + 1, r, s, e);
			} else {
				return query(c.left, l, mid, s, e) + query(c.right, mid + 1, r, s, e);
			}
		}

	}

	public static class Right {
		public int[] arr;

		public Right(int size) {
			arr = new int[size + 1];
		}

		public void add(int i, int v) {
			arr[i] += v;
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
		int size = 10000;
		int testTime = 50000;
		int value = 500;
		DynamicSegmentTree dst = new DynamicSegmentTree(size);
		Right right = new Right(size);
		System.out.println("测试开始");
		for (int k = 0; k < testTime; k++) {
			if (Math.random() < 0.5) {
				int i = (int) (Math.random() * size) + 1;
				int v = (int) (Math.random() * value);
				dst.add(i, v);
				right.add(i, v);
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
		System.out.println("测试结束");
	}

}
