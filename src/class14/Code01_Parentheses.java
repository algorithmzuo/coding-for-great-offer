package class14;

public class Code01_Parentheses {

	public static boolean valid(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		for (int i = 0; i < str.length; i++) {
			count += str[i] == '(' ? 1 : -1;
			if (count < 0) {
				return false;
			}
		}
		return count == 0;
	}

	public static int needParentheses(String s) {
		char[] str = s.toCharArray();
		int count = 0;
		int need = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				count++;
			} else { // 遇到的是')'
				if (count == 0) {
					need++;
				} else {
					count--;
				}
			}
		}
		return count + need;
	}

	public static boolean isValid(char[] str) {
		if (str == null || str.length == 0) {
			return false;
		}
		int status = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ')' && str[i] != '(') {
				return false;
			}
			if (str[i] == ')' && --status < 0) {
				return false;
			}
			if (str[i] == '(') {
				status++;
			}
		}
		return status == 0;
	}

	public static int deep(String s) {
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		int count = 0;
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				max = Math.max(max, ++count);
			} else {
				count--;
			}
		}
		return max;
	}

	// s只由(和)组成
	// 求最长有效括号子串长度
	public static int maxLength(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		// dp[i] : 子串必须以i位置结尾的情况下，往左最远能扩出多长的有效区域
		int[] dp = new int[str.length];
		// dp[0] = 0; （  ）
		int pre = 0;
		int ans = 0;
		for (int i = 1; i < str.length; i++) {
			if (str[i] == ')') {
				// 当前谁和i位置的)，去配！
				pre = i - dp[i - 1] - 1; // 与str[i]配对的左括号的位置 pre
				if (pre >= 0 && str[pre] == '(') {
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
				}
			}
			ans = Math.max(ans, dp[i]);
		}
		return ans;
	}

}
