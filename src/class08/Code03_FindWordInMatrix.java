package class08;

/*
 * 给定一个char[][] matrix，也就是char类型的二维数组，再给定一个字符串word，
 * 可以从任何一个某个位置出发，可以走上下左右，能不能找到word？
 * 比如：
 * char[][] m = { 
 *     { 'a', 'b', 'z' }, 
 *     { 'c', 'd', 'o' }, 
 *     { 'f', 'e', 'o' }, 
 * };
 * word = "zooe"
 * 是可以找到的
 * 
 * 设定1：可以走重复路的情况下，返回能不能找到
 * 比如，word = "zoooz"，是可以找到的，z -> o -> o -> o -> z，因为允许走一条路径中已经走过的字符
 * 
 * 设定2：不可以走重复路的情况下，返回能不能找到
 * 比如，word = "zoooz"，是不可以找到的，因为允许走一条路径中已经走过的字符不能重复走
 * 
 * 写出两种设定下的code
 * 
 * */
public class Code03_FindWordInMatrix {

	// 可以走重复的设定
	public static boolean findWord1(char[][] m, String word) {
		if (word == null || word.equals("")) {
			return true;
		}
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return false;
		}
		char[] w = word.toCharArray();
		int N = m.length;
		int M = m[0].length;
		int len = w.length;
		// dp[i][j][k]表示：必须以m[i][j]这个字符结尾的情况下，能不能找到w[0...k]这个前缀串
		boolean[][][] dp = new boolean[N][M][len];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				dp[i][j][0] = m[i][j] == w[0];
			}
		}
		for (int k = 1; k < len; k++) {
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					dp[i][j][k] = (m[i][j] == w[k] && checkPrevious(dp, i, j, k));
				}
			}
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (dp[i][j][len - 1]) {
					return true;
				}
			}
		}
		return false;
	}

	// 可以走重复路
	// 从m[i][j]这个字符出发，能不能找到str[k...]这个后缀串
	public static boolean canLoop(char[][] m, int i, int j, char[] str, int k) {
		if (k == str.length) {
			return true;
		}
		if (i == -1 || i == m.length || j == -1 || j == m[0].length || m[i][j] != str[k]) {
			return false;
		}
		// 不越界！m[i][j] == str[k] 对的上的！
		// str[k+1....]
		boolean ans = false;
		if (canLoop(m, i + 1, j, str, k + 1) || canLoop(m, i - 1, j, str, k + 1) || canLoop(m, i, j + 1, str, k + 1)
				|| canLoop(m, i, j - 1, str, k + 1)) {
			ans = true;
		}
		return ans;
	}

	// 不能走重复路
	// 从m[i][j]这个字符出发，能不能找到str[k...]这个后缀串
	public static boolean noLoop(char[][] m, int i, int j, char[] str, int k) {
		if (k == str.length) {
			return true;
		}
		if (i == -1 || i == m.length || j == -1 || j == m[0].length || m[i][j] != str[k]) {
			return false;
		}
		// 不越界！也不是回头路！m[i][j] == str[k] 也对的上！
		m[i][j] = 0;
		boolean ans = false;
		if (noLoop(m, i + 1, j, str, k + 1) || noLoop(m, i - 1, j, str, k + 1) || noLoop(m, i, j + 1, str, k + 1)
				|| noLoop(m, i, j - 1, str, k + 1)) {
			ans = true;
		}
		m[i][j] = str[k];
		return ans;
	}

	public static boolean checkPrevious(boolean[][][] dp, int i, int j, int k) {
		boolean up = i > 0 ? (dp[i - 1][j][k - 1]) : false;
		boolean down = i < dp.length - 1 ? (dp[i + 1][j][k - 1]) : false;
		boolean left = j > 0 ? (dp[i][j - 1][k - 1]) : false;
		boolean right = j < dp[0].length - 1 ? (dp[i][j + 1][k - 1]) : false;
		return up || down || left || right;
	}

	// 不可以走重复路的设定
	public static boolean findWord2(char[][] m, String word) {
		if (word == null || word.equals("")) {
			return true;
		}
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return false;
		}
		char[] w = word.toCharArray();
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				if (process(m, i, j, w, 0)) {
					return true;
				}
			}
		}
		return false;
	}

	// 从m[i][j]这个字符出发，能不能找到w[k...]这个后缀串
	public static boolean process(char[][] m, int i, int j, char[] str, int k) {
		if (k == str.length) {
			return true;
		}
		if (i == -1 || i == m.length || j == -1 || j == m[0].length || m[i][j] == 0 || m[i][j] != str[k]) {
			return false;
		}
		m[i][j] = 0;
		boolean ans = false;
		if (process(m, i + 1, j, str, k + 1) || process(m, i - 1, j, str, k + 1) || process(m, i, j + 1, str, k + 1)
				|| process(m, i, j - 1, str, k + 1)) {
			ans = true;
		}
		m[i][j] = str[k];
		return ans;
	}

	public static void main(String[] args) {
		char[][] m = { { 'a', 'b', 'z' }, { 'c', 'd', 'o' }, { 'f', 'e', 'o' }, };
		String word1 = "zoooz";
		String word2 = "zoo";
		// 可以走重复路的设定
		System.out.println(findWord1(m, word1));
		System.out.println(findWord1(m, word2));
		// 不可以走重复路的设定
		System.out.println(findWord2(m, word1));
		System.out.println(findWord2(m, word2));

	}

}
