package class36;

// 来自网易
// 规定：L[1]对应A，L[2]对应B，L[3]对应C，...，L[25]对应Y
// S1 = a
// S(i) = S(i-1) + L[i] + reverse(invert(S(i-1)));
// 解释invert操作：
// S1 = a
// S2 = aby
// 假设invert(S(2)) = 甲乙丙
// a + 甲 = 26, 那么 甲 = 26 - 1 = 25 -> y
// b + 乙 = 26, 那么 乙 = 26 - 2 = 24 -> x
// y + 丙 = 26, 那么 丙 = 26 - 25 = 1 -> a
// 如上就是每一位的计算方式，所以invert(S2) = yxa
// 所以S3 = S2 + L[3] + reverse(invert(S2)) = aby + c + axy = abycaxy
// invert(abycaxy) = yxawyba, 再reverse = abywaxy
// 所以S4 = abycaxy + d + abywaxy = abycaxydabywaxy
// 直到S25结束
// 给定两个参数n和k，返回Sn的第k位是什么字符，n从1开始，k从1开始
// 比如n=4，k=2，表示S4的第2个字符是什么，返回b字符
public class Code01_ReverseInvertString {

	public static int[] lens = null;

	public static void fillLens() {
		lens = new int[26];
		lens[1] = 1;
		for (int i = 2; i <= 25; i++) {
			lens[i] = (lens[i - 1] << 1) + 1;
		}
	}

	public static char kth(int n, int k) {
		if (lens == null) {
			fillLens();
		}
		if (n == 1) {
			return 'a';
		}
		int half = lens[n - 1];
		if (k <= half) {
			return kth(n - 1, k);
		} else if (k == half + 1) {
			return (char) ('a' + n - 1);
		} else {
			int rightK = k - half - 1;
			int nextK = half + 1 - rightK;
			return invert(kth(n - 1, nextK));
		}
	}

	public static char invert(char c) {
		return (char) (('a' << 1) + 24 - c);
	}

	// 为了测试
	public static String generateString(int n) {
		String s = "a";
		for (int i = 2; i <= n; i++) {
			s = s + (char) ('a' + i - 1) + reverseInvert(s);
		}
		return s;
	}

	// 为了测试
	public static String reverseInvert(String s) {
		char[] invert = invert(s).toCharArray();
		for (int l = 0, r = invert.length - 1; l < r; l++, r--) {
			char tmp = invert[l];
			invert[l] = invert[r];
			invert[r] = tmp;
		}
		return String.valueOf(invert);
	}

	// 为了测试
	public static String invert(String s) {
		char[] str = s.toCharArray();
		for (int i = 0; i < str.length; i++) {
			str[i] = invert(str[i]);
		}
		return String.valueOf(str);
	}

	// 为了测试
	public static void main(String[] args) {
		int n = 20;
		String str = generateString(n);
		int len = str.length();
		System.out.println("测试开始");
		for (int i = 1; i <= len; i++) {
			if (str.charAt(i - 1) != kth(n, i)) {
				System.out.println(i);
				System.out.println(str.charAt(i - 1));
				System.out.println(kth(n, i));
				System.out.println("出错了！");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
