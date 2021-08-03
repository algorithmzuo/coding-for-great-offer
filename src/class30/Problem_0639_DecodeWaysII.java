package class30;

public class Problem_0639_DecodeWaysII {

	public static long mod = 1000000007;

	public static int numDecodings1(String str) {
		long[] dp = new long[str.length()];
		return (int) ways1(str.toCharArray(), 0, dp);
	}

	public static long ways1(char[] s, int i, long[] dp) {
		if (i == s.length) {
			return 1;
		}
		if (s[i] == '0') {
			return 0;
		}
		if (dp[i] != 0) {
			return dp[i];
		}
		long ans = ways1(s, i + 1, dp) * (s[i] == '*' ? 9 : 1);
		if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
			if (i + 1 < s.length) {
				if (s[i + 1] == '*') {
					ans += ways1(s, i + 2, dp) * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
				} else {
					if (s[i] == '*') {
						ans += ways1(s, i + 2, dp) * (s[i + 1] < '7' ? 2 : 1);
					} else {
						ans += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? ways1(s, i + 2, dp) : 0;
					}
				}
			}
		}
		ans %= mod;
		dp[i] = ans;
		return ans;
	}

	public static int numDecodings2(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		long[] dp = new long[n + 1];
		dp[n] = 1;
		for (int i = n - 1; i >= 0; i--) {
			if (s[i] != '0') {
				dp[i] = dp[i + 1] * (s[i] == '*' ? 9 : 1);
				if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
					if (i + 1 < n) {
						if (s[i + 1] == '*') {
							dp[i] += dp[i + 2] * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
						} else {
							if (s[i] == '*') {
								dp[i] += dp[i + 2] * (s[i + 1] < '7' ? 2 : 1);
							} else {
								dp[i] += ((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? dp[i + 2] : 0;
							}
						}
					}
				}
				dp[i] %= mod;
			}
		}
		return (int) dp[0];
	}

	public static int numDecodings3(String str) {
		char[] s = str.toCharArray();
		int n = s.length;
		long a = 1;
		long b = 1;
		long c = 0;
		for (int i = n - 1; i >= 0; i--) {
			if (s[i] != '0') {
				c = b * (s[i] == '*' ? 9 : 1);
				if (s[i] == '1' || s[i] == '2' || s[i] == '*') {
					if (i + 1 < n) {
						if (s[i + 1] == '*') {
							c += a * (s[i] == '*' ? 15 : (s[i] == '1' ? 9 : 6));
						} else {
							if (s[i] == '*') {
								c += a * (s[i + 1] < '7' ? 2 : 1);
							} else {
								c += a * (((s[i] - '0') * 10 + s[i + 1] - '0') < 27 ? 1 : 0);
							}
						}
					}
				}
			}
			c %= mod;
			a = b;
			b = c;
			c = 0;
		}
		return (int) b;
	}

}
