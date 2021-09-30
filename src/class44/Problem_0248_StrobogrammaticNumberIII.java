package class44;

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
		// lowLen = 3 hightLen = 7
		// 4 5 6
		for (int i = lowLen + 1; i < highLen; i++) {
			ans += all(i);
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

	// low [左边已经做完决定了 left.....right 右边已经做完决定了]
	// 左边已经做完决定的部分，如果大于low的原始，leftMore = true;
	// 左边已经做完决定的部分，如果不大于low的原始，那一定是相等，不可能小于，leftMore = false;
	// 右边已经做完决定的部分，如果大于low的原始，rs = 2;
	// 右边已经做完决定的部分，如果等于low的原始，rs = 1;
	// 右边已经做完决定的部分，如果小于low的原始，rs = 0;
	// rs < = >
	//    0 1 2
	// 返回 ：没做决定的部分，随意变，几个有效的情况？返回！
	public static int up(char[] low, int left, boolean leftMore, int rs) {
		int N = low.length;
		int right = N - 1 - left;
		if (left > right) { // 都做完决定了！
			// 如果左边做完决定的部分大于原始 或者 如果左边做完决定的部分等于原始&左边做完决定的部分不小于原始
			// 有效！
			// 否则，无效！
			return leftMore || (!leftMore && rs != 0) ? 1 : 0;
		}
		
		// 如果上面没有return，说明决定没做完，就继续做
		if (leftMore) { // 如果左边做完决定的部分大于原始
			return num(N - (left << 1));
		} else { // 如果左边做完决定的部分等于原始
			int ways = 0;
			// 当前left做的决定，大于原始的left
			for (char cha = (char) (low[left] + 1); cha <= '9'; cha++) {
				if (convert(cha, left != right) != -1) {
					ways += up(low, left + 1, true, rs);
				}
			}
			// 当前left做的决定，等于原始的left
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

	// 1 : 0 1 8
	// 2 :
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

	// 如果是最开始 :
	// Y X X X Y
	// -> 1 X X X 1
	// -> 8 X X X 8
	// -> 9 X X X 6
	// -> 6 X X X 9
	// 如果不是最开始 :
	// Y X X X Y
	// -> 0 X X X 0
	// -> 1 X X X 1
	// -> 8 X X X 8
	// -> 9 X X X 6
	// -> 6 X X X 9
	// 所有的len位数，有几个有效的？
	public static int all(int len) {
		int ans = (len & 1) == 0 ? 1 : 3;
		for (int i = (len & 1) == 0 ? 2 : 3; i < len; i += 2) {
			ans *= 5;
		}
		return ans << 2;
	}
	
	// 我们课上讲的
	public static int all(int len, boolean init) {
		if (len == 0) { // init == true，不可能调用all(0)
			return 1;
		}
		if (len == 1) { 
			return 3;
		}
		if (init) {
			return all(len - 2, false) << 2;
		} else {
			return all(len - 2, false) * 5;
		}
	}
	

}
