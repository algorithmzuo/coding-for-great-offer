package class27;

public class Problem_0007_ReverseInteger {

	public static int reverse(int x) {
		boolean neg = ((x >>> 31) & 1) == 1;
		x = neg ? x : -x;
		int m = Integer.MIN_VALUE / 10;
		int o = Integer.MIN_VALUE % 10;
		int res = 0;
		while (x != 0) {
			if (res < m || (res == m && x % 10 < o)) {
				return 0;
			}
			res = res * 10 + x % 10;
			x /= 10;
		}
		return neg ? res : Math.abs(res);
	}

}
