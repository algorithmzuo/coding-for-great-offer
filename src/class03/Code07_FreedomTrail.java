package class03;

import java.util.ArrayList;
import java.util.HashMap;

// 本题测试链接 : https://leetcode.com/problems/freedom-trail/
public class Code07_FreedomTrail {

	public static int findRotateSteps(String r, String k) {
		char[] ring = r.toCharArray();
		int N = ring.length;
		HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < N; i++) {
			if (!map.containsKey(ring[i])) {
				map.put(ring[i], new ArrayList<>());
			}
			map.get(ring[i]).add(i);
		}
		char[] str = k.toCharArray();
		int M = str.length;
		int[][] dp = new int[N][M + 1];
		// hashmap
		// dp[][] == -1 : 表示没算过！
		for (int i = 0; i < N; i++) {
			for (int j = 0; j <= M; j++) {
				dp[i][j] = -1;
			}
		}
		return process(0, 0, str, map, N, dp);
	}

	// 电话里：指针指着的上一个按键preButton
	// 目标里：此时要搞定哪个字符？keyIndex
	// map : key 一种字符 value: 哪些位置拥有这个字符
	// N: 电话大小
	// f(0, 0, aim, map, N)
	public static int process(int preButton, int index, char[] str, HashMap<Character, ArrayList<Integer>> map, int N,
			int[][] dp) {
		if (dp[preButton][index] != -1) {
			return dp[preButton][index];
		}
		int ans = Integer.MAX_VALUE;
		if (index == str.length) {
			ans = 0;
		} else {
			// 还有字符需要搞定呢！
			char cur = str[index];
			ArrayList<Integer> nextPositions = map.get(cur);
			for (int next : nextPositions) {
				int cost = dial(preButton, next, N) + 1 + process(next, index + 1, str, map, N, dp);
				ans = Math.min(ans, cost);
			}
		}
		dp[preButton][index] = ans;
		return ans;
	}

	public static int dial(int i1, int i2, int size) {
		return Math.min(Math.abs(i1 - i2), Math.min(i1, i2) + size - Math.max(i1, i2));
	}

}
