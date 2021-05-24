package class10;

// 本题测试链接 : https://leetcode-cn.com/problems/boolean-evaluation-lcci/
public class Code06_BooleanEvaluation {

	public static int countEval1(String express, int desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		return f(exp, desired, 0, exp.length - 1);
	}

	public static int f(char[] str, int desired, int L, int R) {
		if (L == R) {
			if (str[L] == '1') {
				return desired;
			} else {
				return desired ^ 1;
			}
		}
		int res = 0;
		if (desired == 1) {
			for (int i = L + 1; i < R; i += 2) {
				switch (str[i]) {
				case '&':
					res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
					break;
				case '|':
					res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
					res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
					break;
				case '^':
					res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
					break;
				}
			}
		} else {
			for (int i = L + 1; i < R; i += 2) {
				switch (str[i]) {
				case '&':
					res += f(str, 0, L, i - 1) * f(str, 1, i + 1, R);
					res += f(str, 1, L, i - 1) * f(str, 0, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
					break;
				case '|':
					res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
					break;
				case '^':
					res += f(str, 1, L, i - 1) * f(str, 1, i + 1, R);
					res += f(str, 0, L, i - 1) * f(str, 0, i + 1, R);
					break;
				}
			}
		}
		return res;
	}

	public static int countEval2(String express, int desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		int N = exp.length;
		int[][][] dp = new int[2][N][N];
		dp[0][0][0] = exp[0] == '0' ? 1 : 0;
		dp[1][0][0] = dp[0][0][0] ^ 1;
		for (int i = 2; i < exp.length; i += 2) {
			dp[0][i][i] = exp[i] == '1' ? 0 : 1;
			dp[1][i][i] = exp[i] == '0' ? 0 : 1;
			for (int j = i - 2; j >= 0; j -= 2) {
				for (int k = j; k < i; k += 2) {
					if (exp[k + 1] == '&') {
						dp[1][j][i] += dp[1][j][k] * dp[1][k + 2][i];
						dp[0][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[0][k + 2][i] + dp[0][j][k] * dp[1][k + 2][i];
					} else if (exp[k + 1] == '|') {
						dp[1][j][i] += (dp[0][j][k] + dp[1][j][k]) * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
						dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i];
					} else {
						dp[1][j][i] += dp[0][j][k] * dp[1][k + 2][i] + dp[1][j][k] * dp[0][k + 2][i];
						dp[0][j][i] += dp[0][j][k] * dp[0][k + 2][i] + dp[1][j][k] * dp[1][k + 2][i];
					}
				}
			}
		}
		return dp[desired][0][N - 1];
	}

}
