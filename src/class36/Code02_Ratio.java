package class36;

import java.util.HashMap;

// 来自网易
public class Code02_Ratio {

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
