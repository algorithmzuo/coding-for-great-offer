package class43;

public class Problem_0248_StrobogrammaticNumberIII {

	public static int strobogrammaticInRange(String l, String h) {
		char[] low = l.toCharArray();
		char[] high = h.toCharArray();
		if (!equalMore(low, high)) {
			return 0;
		}
		int lowLen = low.length;
		int highLen = high.length;
		if (lowLen == highLen) {
			int up1 = up(low, 0, false, 1);
			int up2 = up(high, 0, false, 1);
			return up1 - up2 + (valid(high) ? 1 : 0);
		}
		int ans = 0;
		for (int i = lowLen + 1; i < highLen; i++) {
			ans += numLen(i, true);
		}
		ans += up(low, 0, false, 1);
		ans += down(high, 0, false, 1);
		return ans;
	}

	public static boolean equalMore(char[] low, char[] cur) {
		if (low.length != cur.length) {
			return low.length < cur.length;
		}
		for (int i = 0; i < low.length; i++) {
			if (low[i] != cur[i]) {
				return low[i] < cur[i];
			}
		}
		return true;
	}

	public static boolean valid(char[] str) {
		int L = 0;
		int R = str.length - 1;
		while (L <= R) {
			boolean t = L != R;
			if (convert(str[L++], t) != str[R--]) {
				return false;
			}
		}
		return true;
	}

	public static int convert(char cha, boolean diff) {
		switch (cha) {
		case '0':
			return '0';
		case '1':
			return '1';
		case '6':
			return diff ? '9' : -1;
		case '8':
			return '8';
		case '9':
			return diff ? '6' : -1;
		default:
			return -1;
		}
	}

	// ll < =
	// rs < = >
	public static int down(char[] high, int left, boolean ll, int rs) {
		int N = high.length;
		int right = N - 1 - left;
		if (left > right) {
			return ll || (!ll && rs != 2) ? 1 : 0;
		}
		if (ll) {
			return num(N - (left << 1));
		} else {
			int ways = 0;
			for (char cha = (N != 1 && left == 0) ? '1' : '0'; cha < high[left]; cha++) {
				if (convert(cha, left != right) != -1) {
					ways += down(high, left + 1, true, rs);
				}
			}
			int convert = convert(high[left], left != right);
			if (convert != -1) {
				if (convert < high[right]) {
					ways += down(high, left + 1, false, 0);
				} else if (convert == high[right]) {
					ways += down(high, left + 1, false, rs);
				} else {
					ways += down(high, left + 1, false, 2);
				}
			}
			return ways;
		}
	}

	// lm > =
	// rs < = >
	public static int up(char[] low, int left, boolean lm, int rs) {
		int N = low.length;
		int right = N - 1 - left;
		if (left > right) {
			return lm || (!lm && rs != 0) ? 1 : 0;
		}
		if (lm) {
			return num(N - (left << 1));
		} else {
			int ways = 0;
			for (char cha = (char) (low[left] + 1); cha <= '9'; cha++) {
				if (convert(cha, left != right) != -1) {
					ways += up(low, left + 1, true, rs);
				}
			}
			int convert = convert(low[left], left != right);
			if (convert != -1) {
				if (convert < low[right]) {
					ways += up(low, left + 1, false, 0);
				} else if (convert == low[right]) {
					ways += up(low, left + 1, false, rs);
				} else {
					ways += up(low, left + 1, false, 2);
				}
			}
			return ways;
		}
	}

	public static int num(int bits) {
		if (bits == 1) {
			return 3;
		}
		if (bits == 2) {
			return 5;
		}
		int p2 = 3;
		int p1 = 5;
		int ans = 0;
		for (int i = 3; i <= bits; i++) {
			ans = 5 * p2;
			p2 = p1;
			p1 = ans;
		}
		return ans;
	}

	public static int numLen(int bits, boolean first) {
		if (bits == 0) {
			return 1;
		}
		if (bits == 1) {
			return 3;
		}
		if (first) {
			return numLen(bits - 2, false) << 2;
		} else {
			return numLen(bits - 2, false) * 5;
		}
	}

}
