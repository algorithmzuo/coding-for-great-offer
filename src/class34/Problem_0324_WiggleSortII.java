package class34;

public class Problem_0324_WiggleSortII {

	// 时间复杂度O(N)，额外空间复杂度O(1)
	public static void wiggleSort(int[] nums) {
		if (nums == null || nums.length < 2) {
			return;
		}
		int N = nums.length;
		// 小 中 右
		findIndexNum(nums, 0, nums.length - 1, N / 2);
		if ((N & 1) == 0) {
			// R L -> L R
			shuffle(nums, 0, nums.length - 1);
			// R1 L1 R2 L2 R3 L3 R4 L4
			// L4 R4 L3 R3 L2 R2 L1 R1 -> 代码中的方式，可以的！
			// L1 R1 L2 R2 L3 R3 L4 R4 -> 课上的分析是不行的！不能两两换
			reverse(nums, 0, nums.length - 1);
		} else {
			shuffle(nums, 1, nums.length - 1);
		}
	}

	public static int findIndexNum(int[] arr, int L, int R, int index) {
		int pivot = 0;
		int[] range = null;
		while (L < R) {
			pivot = arr[L + (int) (Math.random() * (R - L + 1))];
			range = partition(arr, L, R, pivot);
			if (index >= range[0] && index <= range[1]) {
				return arr[index];
			} else if (index < range[0]) {
				R = range[0] - 1;
			} else {
				L = range[1] + 1;
			}
		}
		return arr[L];
	}

	public static int[] partition(int[] arr, int L, int R, int pivot) {
		int less = L - 1;
		int more = R + 1;
		int cur = L;
		while (cur < more) {
			if (arr[cur] < pivot) {
				swap(arr, ++less, cur++);
			} else if (arr[cur] > pivot) {
				swap(arr, cur, --more);
			} else {
				cur++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}

	public static void shuffle(int[] nums, int l, int r) {
		while (r - l + 1 > 0) {
			int lenAndOne = r - l + 2;
			int bloom = 3;
			int k = 1;
			while (bloom <= lenAndOne / 3) {
				bloom *= 3;
				k++;
			}
			int m = (bloom - 1) / 2;
			int mid = (l + r) / 2;
			rotate(nums, l + m, mid, mid + m);
			cycles(nums, l - 1, bloom, k);
			l = l + bloom - 1;
		}
	}

	public static void cycles(int[] nums, int base, int bloom, int k) {
		for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
			int next = (2 * trigger) % bloom;
			int cur = next;
			int record = nums[next + base];
			int tmp = 0;
			nums[next + base] = nums[trigger + base];
			while (cur != trigger) {
				next = (2 * cur) % bloom;
				tmp = nums[next + base];
				nums[next + base] = record;
				cur = next;
				record = tmp;
			}
		}
	}

	public static void rotate(int[] arr, int l, int m, int r) {
		reverse(arr, l, m);
		reverse(arr, m + 1, r);
		reverse(arr, l, r);
	}

	public static void reverse(int[] arr, int l, int r) {
		while (l < r) {
			swap(arr, l++, r--);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	// 为了测试，暴力方法
	// 把arr全排列尝试一遍，
	// 其中任何一个排列能做到 < > < > ... 返回true;
	// 如果没有任何一个排列能做到，返回false;
	public static boolean test(int[] arr) {
		return process(arr, 0);
	}

	// 为了测试
	public static boolean process(int[] arr, int index) {
		if (index == arr.length) {
			return valid(arr);
		}
		for (int i = index; i < arr.length; i++) {
			swap(arr, index, i);
			if (process(arr, index + 1)) {
				return true;
			}
			swap(arr, index, i);
		}
		return false;
	}

	// 为了测试
	public static boolean valid(int[] arr) {
		boolean more = true;
		for (int i = 1; i < arr.length; i++) {
			if ((more && arr[i - 1] >= arr[i]) || (!more && arr[i - 1] <= arr[i])) {
				return false;
			}
			more = !more;
		}
		return true;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int V = 10;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr1 = randomArray(n, V);
			int[] arr2 = copyArray(arr1);
			wiggleSort(arr1);
			if (valid(arr1) != test(arr2)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
