package class30;

public class Problem_0125_ValidPalindrome {

	public static boolean isPalindrome(String s) {
		if (s == null || s.length() == 0) {
			return true;
		}
		char[] str = s.toCharArray();
		int L = 0;
		int R = str.length - 1;
		while (L < R) {
			if (validChar(str[L]) && validChar(str[R])) {
				if (!equal(str[L], str[R])) {
					return false;
				}
				L++;
				R--;
			} else {
				L += validChar(str[L]) ? 0 : 1;
				R -= validChar(str[R]) ? 0 : 1;
			}
		}
		return true;
	}

	public static boolean validChar(char c) {
		return isLetter(c) || isNumber(c);
	}

	public static boolean equal(char c1, char c2) {
		if (isNumber(c1) || isNumber(c2)) {
			return c1 == c2;
		}
		return (c1 == c2) || (Math.max(c1, c2) - Math.min(c1, c2) == 32);
	}

	public static boolean isLetter(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}

	public static boolean isNumber(char c) {
		return (c >= '0' && c <= '9');
	}

}
