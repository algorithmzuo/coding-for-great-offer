package class48;

import java.util.Arrays;

// 来自学员问题
// 比如{ 5, 3, 1, 4 }
// 全部数字对是：(5,3)、(5,1)、(5,4)、(3,1)、(3,4)、(1,4)
// 数字对的差值绝对值： 2、4、1、2、1、3
// 差值绝对值排序后：1、1、2、2、3、4
// 给定一个数组arr，和一个正数k
// 返回arr中所有数字对差值的绝对值，第k小的是多少
// arr = { 5, 3, 1, 4 }, k = 4
// 返回2
public class Code01_MinKthPairMinusABS {

	public static int kthABS1(int[] arr, int k) {
		int n = arr.length;
		int m = ((n - 1) * n) >> 1;
		if (m == 0 || k < 1 || k > m) {
			return -1;
		}
		int[] abs = new int[m];
		int size = 0;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n; j++) {
				abs[size++] = Math.abs(arr[i] - arr[j]);
			}
		}
		Arrays.sort(abs);
		return abs[k - 1];
	}

	public static int kthABS2(int[] arr, int k) {
		int n = arr.length;
		if (n < 2 || k < 1 || k > ((n * (n - 1)) >> 1)) {
			return -1;
		}
		Arrays.sort(arr);
		int left = 0;
		int right = arr[n - 1] - arr[0];
		int mid = 0;
		int rightest = -1;
		while (left <= right) {
			mid = (left + right) / 2;
			if (valid(arr, mid, k)) {
				rightest = mid;
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}
		return rightest + 1;
	}

	// 假设arr中的所有数字对，差值绝对值<=limit的个数为x
	// 如果 x < k，达标，返回true
	// 如果 x >= k，不达标，返回false
	public static boolean valid(int[] arr, int limit, int k) {
		int num = 0;
		for (int l = 0, r = 1; l < arr.length; r = Math.max(r, ++l)) {
			while (r < arr.length && arr[r] - arr[l] <= limit) {
				r++;
			}
			num += r - l - 1;
		}
		return num < k;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int size = 100;
		int value = 100;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * size);
			int k = (int) (Math.random() * (n * (n - 1) / 2)) + 1;
			int[] arr = randomArray(n, value);
			int ans1 = kthABS1(arr, k);
			int ans2 = kthABS2(arr, k);
			if (ans1 != ans2) {
				System.out.print("arr : ");
				for (int num : arr) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("k : " + k);
				System.out.println("ans1 : " + ans1);
				System.out.println("ans2 : " + ans2);
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");

		long start;
		long end;
		int n = 500000;
		int v = 50000000;
		int[] arr = randomArray(n, v);
		int k = (int) (Math.random() * (n * (n - 1) / 2)) + 1;
		start = System.currentTimeMillis();
		kthABS2(arr, k);
		end = System.currentTimeMillis();
		System.out.println("大数据量运行时间（毫秒）：" + (end - start));
	}

}
