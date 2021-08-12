package class32;

public class Problem_0204_CountPrimes {

	public static int countPrimes(int n) {
		if (n < 3) {
			return 0;
		}
		// j已经不是素数了，f[j] = true;
		boolean[] f = new boolean[n];
		int count = n / 2; // 所有偶数都不要，还剩几个数
		// 跳过了1、2    3、5、7、
		for (int i = 3; i * i < n; i += 2) {
			if (f[i]) {
				continue;
			}
			// 3 -> 3 * 3 = 9   3 * 5 = 15   3 * 7 = 21
			// 7 -> 7 * 7 = 49  7 * 9 = 63
			// 13 -> 13 * 13  13 * 15
			for (int j = i * i; j < n; j += 2 * i) {
				if (!f[j]) {
					--count;
					f[j] = true;
				}
			}
		}
		return count;
	}

}
