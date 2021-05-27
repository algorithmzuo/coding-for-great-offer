package class11;

import java.util.ArrayList;
import java.util.List;

// 本题测试链接 : https://leetcode.com/problems/palindrome-partitioning-ii/
public class Code02_PalindromePartitioningII {

	// 测试链接只测了本题的第一问，直接提交可以通过
	public static int minCut(String s) {
		if (s == null || s.length() < 2) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[][] checkMap = createCheckMap(str, N);
		int[] dp = new int[N + 1];
		dp[N] = 0;
		for (int i = N - 1; i >= 0; i--) {
			if (checkMap[i][N - 1]) {
				dp[i] = 1;
			} else {
				int next = Integer.MAX_VALUE;
				for (int j = i; j < N; j++) {
					if (checkMap[i][j]) {
						next = Math.min(next, dp[j + 1]);
					}
				}
				dp[i] = 1 + next;
			}
		}
		return dp[0] - 1;
	}

	public static boolean[][] createCheckMap(char[] str, int N) {
		boolean[][] ans = new boolean[N][N];
		for (int i = 0; i < N - 1; i++) {
			ans[i][i] = true;
			ans[i][i + 1] = str[i] == str[i + 1];
		}
		ans[N - 1][N - 1] = true;
		for (int i = N - 3; i >= 0; i--) {
			for (int j = i + 2; j < N; j++) {
				ans[i][j] = str[i] == str[j] && ans[i + 1][j - 1];
			}
		}
		return ans;
	}

	// 本题第二问，返回其中一种结果
	public static List<String> minCutOneWay(String s) {
		List<String> ans = new ArrayList<>();
		if (s == null || s.length() < 2) {
			ans.add(s);
		} else {
			char[] str = s.toCharArray();
			int N = str.length;
			boolean[][] checkMap = createCheckMap(str, N);
			int[] dp = new int[N + 1];
			dp[N] = 0;
			for (int i = N - 1; i >= 0; i--) {
				if (checkMap[i][N - 1]) {
					dp[i] = 1;
				} else {
					int next = Integer.MAX_VALUE;
					for (int j = i; j < N; j++) {
						if (checkMap[i][j]) {
							next = Math.min(next, dp[j + 1]);
						}
					}
					dp[i] = 1 + next;
				}
			}
			for (int i = 0, j = 1; j <= N; j++) {
				if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
					ans.add(s.substring(i, j));
					i = j;
				}
			}
		}
		return ans;
	}

	// 本题第三问，返回所有结果
	public static List<List<String>> minCutAllWays(String s) {
		List<List<String>> ans = new ArrayList<>();
		if (s == null || s.length() < 2) {
			List<String> cur = new ArrayList<>();
			cur.add(s);
			ans.add(cur);
		} else {
			char[] str = s.toCharArray();
			int N = str.length;
			boolean[][] checkMap = createCheckMap(str, N);
			int[] dp = new int[N + 1];
			dp[N] = 0;
			for (int i = N - 1; i >= 0; i--) {
				if (checkMap[i][N - 1]) {
					dp[i] = 1;
				} else {
					int next = Integer.MAX_VALUE;
					for (int j = i; j < N; j++) {
						if (checkMap[i][j]) {
							next = Math.min(next, dp[j + 1]);
						}
					}
					dp[i] = 1 + next;
				}
			}
			process(s, 0, 1, checkMap, dp, new ArrayList<>(), ans);
		}
		return ans;
	}

	public static void process(String s, int i, int j, boolean[][] checkMap, int[] dp, List<String> path,
			List<List<String>> ans) {
		if (j == s.length()) {
			if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
				path.add(s.substring(i, j));
				ans.add(copyStringList(path));
				path.remove(path.size() - 1);
			}
		} else {
			if (checkMap[i][j - 1] && dp[i] == dp[j] + 1) {
				path.add(s.substring(i, j));
				process(s, j, j + 1, checkMap, dp, path, ans);
				path.remove(path.size() - 1);
			}
			process(s, i, j + 1, checkMap, dp, path, ans);
		}
	}

	public static List<String> copyStringList(List<String> list) {
		List<String> ans = new ArrayList<>();
		for (String str : list) {
			ans.add(str);
		}
		return ans;
	}

	public static void main(String[] args) {
		String s = null;
		List<String> ans2 = null;
		List<List<String>> ans3 = null;

		System.out.println("本题第二问，返回其中一种结果测试开始");
		s = "abacbc";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();

		s = "aabccbac";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();

		s = "aabaa";
		ans2 = minCutOneWay(s);
		for (String str : ans2) {
			System.out.print(str + " ");
		}
		System.out.println();
		System.out.println("本题第二问，返回其中一种结果测试结束");
		System.out.println();
		System.out.println("本题第三问，返回所有可能结果测试开始");
		s = "cbbbcbc";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println();

		s = "aaaaaa";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println();

		s = "fcfffcffcc";
		ans3 = minCutAllWays(s);
		for (List<String> way : ans3) {
			for (String str : way) {
				System.out.print(str + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("本题第三问，返回所有可能结果测试结束");
	}

}
