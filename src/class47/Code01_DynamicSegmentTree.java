package class47;

// 只支持单点增加 + 范围查询的动态开点线段树（累加和）
public class Code01_DynamicSegmentTree {

	public static class Node {
		public int sum;
		public Node left;
		public Node right;
	}

	// arr[0] -> 1
	// 线段树，从1开始下标!
	public static class DynamicSegmentTree {
		public Node root;
		public int size;

		public DynamicSegmentTree(int max) {
			root = new Node();
			size = max;
		}

		// 下标i这个位置的数，增加v
		public void add(int i, int v) {
			add(root, 1, size, i, v);
		}

		// c-> cur 当前节点！表达的范围 l~r
		// i位置的数，增加v
		// 潜台词！i一定在l~r范围上！
		private void add(Node c, int l, int r, int i, int v) {
			if (l == r) {
				c.sum += v;
			} else { // l~r 还可以划分
				int mid = (l + r) / 2;
				if (i <= mid) { // l ~ mid
					if (c.left == null) {
						c.left = new Node();
					}
					add(c.left, l, mid, i, v);
				} else {  // mid + 1 ~ r
					if (c.right == null) {
						c.right = new Node();
					}
					add(c.right, mid + 1, r, i, v);
				}
				c.sum = (c.left != null ? c.left.sum : 0) + (c.right != null ? c.right.sum : 0);
			}
		}

		// s~e范围的累加和，告诉我！
		public int query(int s, int e) {
			return query(root, 1, size, s, e);
		}

		// 当前节点c，表达的范围l~r
		// 收到了一个任务，s~e这个任务！
		// s~e这个任务，影响了多少l~r范围的数，把答案返回！
		private int query(Node c, int l, int r, int s, int e) {
			if (c == null) {
				return 0;
			}
			if (s <= l && r <= e) { // 3~6  1~100任务
				return c.sum;
			}
			// 有影响，但又不是全影响
			// l ~ r
			// l~mid    mid+1~r
			int mid = (l + r) / 2;
			// 1~100  
			// 1~50  51 ~ 100 
			// 任务  s~e  53~76
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
