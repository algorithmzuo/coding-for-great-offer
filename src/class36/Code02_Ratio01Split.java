package class36;

import java.util.HashMap;

// 来自网易
// 把一个01字符串切成多个部分，要求每一部分的0和1比例一样，同时要求尽可能多的划分
// 比如 : 01010101
// 01 01 01 01 这是一种切法，0和1比例为 1 : 1
// 0101 0101 也是一种切法，0和1比例为 1 : 1
// 两种切法都符合要求，但是那么尽可能多的划分为第一种切法，部分数为4
// 比如 : 00001111
// 只有一种切法就是00001111整体作为一块，那么尽可能多的划分，部分数为1
// 给定一个01字符串str，假设长度为N，要求返回一个长度为N的数组ans
// 其中ans[i] = str[0...i]这个前缀串，要求每一部分的0和1比例一样，同时要求尽可能多的划分下，部分数是多少
// 输入: str = "010100001"
// 输出: ans = [1, 1, 1, 2, 1, 2, 1, 1, 3]
public class Code02_Ratio01Split {

	public static int[] split(int[] arr) {
		HashMap<Integer, HashMap<Integer, Integer>> pre = new HashMap<>();
		int n = arr.length;
		int[] ans = new int[n];
		int zero = 0;
		int one = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] == 0) {
				zero++;
			} else {
				one++;
			}
			if (zero == 0 || one == 0) {
				ans[i] = i + 1;
			} else {
				int gcd = gcd(zero, one);
				int a = zero / gcd;
				int b = one / gcd;
				if (!pre.containsKey(a)) {
					pre.put(a, new HashMap<>());
				}
				if (!pre.get(a).containsKey(b)) {
					pre.get(a).put(b, 1);
				} else {
					pre.get(a).put(b, pre.get(a).get(b) + 1);
				}
				ans[i] = pre.get(a).get(b);
			}
		}
		return ans;
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	public static void main(String[] args) {
		int[] arr = { 0, 1, 0, 1, 0, 1, 1, 0 };
		int[] ans = split(arr);
		for (int i = 0; i < ans.length; i++) {
			System.out.print(ans[i] + " ");
		}
	}

}
