package class20;

public class Code04_PalindromeWays {

	public static int ways1(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] s = str.toCharArray();
		char[] path = new char[s.length];
		return process(str.toCharArray(), 0, path, 0);
	}

	public static int process(char[] s, int si, char[] path, int pi) {
		if (si == s.length) {
			return isP(path, pi) ? 1 : 0;
		}
		int ans = process(s, si + 1, path, pi);
		path[pi] = s[si];
		ans += process(s, si + 1, path, pi + 1);
		return ans;
	}

	public static boolean isP(char[] path, int pi) {
		if (pi == 0) {
			return false;
		}
		int L = 0;
		int R = pi - 1;
		while (L < R) {
			if (path[L++] != path[R--]) {
				return false;
			}
		}
		return true;
	}

	public static int ways2(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] s = str.toCharArray();
		int n = s.length;
		int[][] dp = new int[n][n];
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}
		for (int i = 0; i < n - 1; i++) {
			dp[i][i + 1] = s[i] == s[i + 1] ? 3 : 2;
		}
		for (int L = n - 3; L >= 0; L--) {
			for (int R = L + 2; R < n; R++) {
				dp[L][R] = dp[L + 1][R] + dp[L][R - 1] - dp[L + 1][R - 1];
				if (s[L] == s[R]) {
					dp[L][R] += dp[L + 1][R - 1] + 1;
				}
			}
		}
		return dp[0][n - 1];
	}

	public static String randomString(int len, int types) {
		char[] str = new char[len];
		for (int i = 0; i < str.length; i++) {
			str[i] = (char) ('a' + (int) (Math.random() * types));
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int N = 10;
		int types = 5;
		int testTimes = 100000;
		System.out.println("测试开始");
		for (int i = 0; i < testTimes; i++) {
			int len = (int) (Math.random() * N);
			String str = randomString(len, types);
			int ans1 = ways1(str);
			int ans2 = ways2(str);
			if (ans1 != ans2) {
				System.out.println(str);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");
	}

}
