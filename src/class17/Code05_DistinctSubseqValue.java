package class17;

// 本题测试链接 : https://leetcode.com/problems/distinct-subsequences-ii/
public class Code05_DistinctSubseqValue {

	public static int distinctSubseqII(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		int m = 1000000007;
		char[] str = s.toCharArray();
		int[] count = new int[26];
		int all = 1; // 算空集
		for (char x : str) {
			int add = (all - count[x - 'a'] + m) % m;
			all += add;
			all %= m;
			count[x - 'a'] += add;
			count[x - 'a'] %= m;
		}
		return all - 1;
	}

}
