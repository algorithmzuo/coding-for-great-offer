package class40;

import java.util.ArrayList;
import java.util.HashMap;

// 腾讯
// 分裂问题
// 一个数n，可以分裂成一个数组[n/2, n%2, n/2]
// 这个数组中哪个数不是1或者0，就继续分裂下去
// 比如 n = 5，一开始分裂成[2, 1, 2]
// [2, 1, 2]这个数组中不是1或者0的数，会继续分裂下去，比如两个2就继续分裂
// [2, 1, 2] -> [1, 0, 1, 1, 1, 0, 1]
// 那么我们说，5最后分裂成[1, 0, 1, 1, 1, 0, 1]
// 每一个数都可以这么分裂，在最终分裂的数组中，假设下标从1开始
// 给定三个数n、l、r，返回n的最终分裂数组里[l,r]范围上有几个1
// n <= 2 ^ 50，n是long类型
// r - l <= 50000，l和r是int类型
// 我们的课加个码:
// n是long类型随意多大都行
// l和r也是long类型随意多大都行，但要保证l<=r
public class Code01_SplitTo01 {

//	public static long nums3(long n, long l, long r) {
//		HashMap<Long, Long> lenMap = new HashMap<>();
//		len(n, lenMap);
//		HashMap<Long, Long> onesMap = new HashMap<>();
//		ones(n, onesMap);
//	}

	// n = 100
	// n = 100, 最终裂变的数组，长度多少？
	// n = 50, 最终裂变的数组，长度多少？
	// n = 25, 最终裂变的数组，长度多少？
	// ..
	// n = 1 ,.最终裂变的数组，长度多少？
	// 请都填写到lenMap中去！
	public static long len(long n, HashMap<Long, Long> lenMap) {
		if (n == 1 || n == 0) {
			lenMap.put(n, 1L);
			return 1;
		} else {
			// n > 1
			long half = len(n / 2, lenMap);
			long all = half * 2 + 1;
			lenMap.put(n, all);
			return all;
		}
	}

	// n = 100
	// n = 100, 最终裂变的数组中，一共有几个1
	// n = 50, 最终裂变的数组，一共有几个1
	// n = 25, 最终裂变的数组，一共有几个1
	// ..
	// n = 1 ,.最终裂变的数组，一共有几个1
	// 请都填写到onesMap中去！
	public static long ones(long num, HashMap<Long, Long> onesMap) {
		if (num == 1 || num == 0) {
			onesMap.put(num, num);
			return num;
		}
		// n > 1
		long half = ones(num / 2, onesMap);
		long mid = num % 2 == 1 ? 1 : 0;
		long all = half * 2 + mid;
		onesMap.put(num, all);
		return all;
	}

	//

	public static long nums1(long n, long l, long r) {
		if (n == 1 || n == 0) {
			return n == 1 ? 1 : 0;
		}
		long half = size(n / 2);
		long left = l > half ? 0 : nums1(n / 2, l, Math.min(half, r));
		long mid = (l > half + 1 || r < half + 1) ? 0 : (n & 1);
		long right = r > half + 1 ? nums1(n / 2, Math.max(l - half - 1, 1), r - half - 1) : 0;
		return left + mid + right;
	}

	public static long size(long n) {
		if (n == 1 || n == 0) {
			return 1;
		} else {
			long half = size(n / 2);
			return (half << 1) + 1;
		}
	}

	public static long nums2(long n, long l, long r) {
		HashMap<Long, Long> allMap = new HashMap<>();
		return dp(n, l, r, allMap);
	}

	public static long dp(long n, long l, long r, HashMap<Long, Long> allMap) {
		if (n == 1 || n == 0) {
			return n == 1 ? 1 : 0;
		}
		long half = size(n / 2);
		long all = (half << 1) + 1;
		long mid = n & 1;
		if (l == 1 && r >= all) {
			if (allMap.containsKey(n)) {
				return allMap.get(n);
			} else {
				long count = dp(n / 2, 1, half, allMap);
				long ans = (count << 1) + mid;
				allMap.put(n, ans);
				return ans;
			}
		} else {
			mid = (l > half + 1 || r < half + 1) ? 0 : mid;
			long left = l > half ? 0 : dp(n / 2, l, Math.min(half, r), allMap);
			long right = r > half + 1 ? dp(n / 2, Math.max(l - half - 1, 1), r - half - 1, allMap) : 0;
			return left + mid + right;
		}
	}

	// 为了测试
	// 彻底生成n的最终分裂数组返回
	public static ArrayList<Integer> test(long n) {
		ArrayList<Integer> arr = new ArrayList<>();
		process(n, arr);
		return arr;
	}

	public static void process(long n, ArrayList<Integer> arr) {
		if (n == 1 || n == 0) {
			arr.add((int) n);
		} else {
			process(n / 2, arr);
			arr.add((int) (n % 2));
			process(n / 2, arr);
		}
	}

	public static void main(String[] args) {
		long num = 671;
		ArrayList<Integer> ans = test(num);
		int testTime = 10000;
		System.out.println("功能测试开始");
		for (int i = 0; i < testTime; i++) {
			int a = (int) (Math.random() * ans.size()) + 1;
			int b = (int) (Math.random() * ans.size()) + 1;
			int l = Math.min(a, b);
			int r = Math.max(a, b);
			int ans1 = 0;
			for (int j = l - 1; j < r; j++) {
				if (ans.get(j) == 1) {
					ans1++;
				}
			}
			long ans2 = nums1(num, l, r);
			long ans3 = nums2(num, l, r);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("出错了!");
			}
		}
		System.out.println("功能测试结束");
		System.out.println("==============");

		System.out.println("性能测试开始");
		num = (2L << 50) + 22517998136L;
		long l = 30000L;
		long r = 800000200L;
		long start;
		long end;
		start = System.currentTimeMillis();
		System.out.println("nums1结果 : " + nums1(num, l, r));
		end = System.currentTimeMillis();
		System.out.println("nums1花费时间(毫秒) : " + (end - start));

		start = System.currentTimeMillis();
		System.out.println("nums2结果 : " + nums2(num, l, r));
		end = System.currentTimeMillis();
		System.out.println("nums2花费时间(毫秒) : " + (end - start));
		System.out.println("性能测试结束");
		System.out.println("==============");

		System.out.println("单独展示nums2方法强悍程度测试开始");
		num = (2L << 55) + 22517998136L;
		l = 30000L;
		r = 6431000002000L;
		start = System.currentTimeMillis();
		System.out.println("nums2结果 : " + nums2(num, l, r));
		end = System.currentTimeMillis();
		System.out.println("nums2花费时间(毫秒) : " + (end - start));
		System.out.println("单独展示nums2方法强悍程度测试结束");
		System.out.println("==============");

	}

}
