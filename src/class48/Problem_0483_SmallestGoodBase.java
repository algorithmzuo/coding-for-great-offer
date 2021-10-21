package class48;

public class Problem_0483_SmallestGoodBase {

	public static String smallestGoodBase(String n) {
		long num = Long.valueOf(n);
		for (int m = (int) (Math.log(num + 1) / Math.log(2)); m > 2; m--) {
			long l = (long) (Math.pow(num, 1.0 / m));
			long r = (long) (Math.pow(num, 1.0 / (m - 1))) + 1L;
			while (l <= r) {
				long k = l + ((r - l) >> 1);
				long sum = 0L;
				long base = 1L;
				for (int i = 0; i < m && sum <= num; i++) {
					sum += base;
					base *= k;
				}
				if (sum < num) {
					l = k + 1;
				} else if (sum > num) {
					r = k - 1;
				} else {
					return String.valueOf(k);
				}
			}
		}
		return String.valueOf(num - 1);
	}

}
