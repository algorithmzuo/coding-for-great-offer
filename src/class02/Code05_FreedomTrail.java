package class02;

import java.util.ArrayList;
import java.util.HashMap;

public class Code05_FreedomTrail {

	public static int dial(int i1, int i2, int size) {
		return Math.min(Math.abs(i1 - i2), Math.min(i1, i2) + size - Math.max(i1, i2));
	}

	public static int findRotateSteps1(String r, String k) {
		char[] ring = r.toCharArray();
		int rsize = ring.length;
		HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < rsize; i++) {
			if (!map.containsKey(ring[i])) {
				map.put(ring[i], new ArrayList<>());
			}
			map.get(ring[i]).add(i);
		}
		return minSteps1(0, 0, k.toCharArray(), map, rsize);
	}

	// preStrIndex : 目前被对齐的位置是什么位置
	// keyIndex : 请搞定key[keyIndex...]
	// key      : 目标串（固定参数）
	// map      : 任何一个字符，什么位置上有它（固定参数）
	// rsize    : 电话表盘上一共的位置个数（固定参数）
	// 返回最低代价
	public static int minSteps1(
			int preStrIndex,
			int keyIndex, 
			char[] key, 
			HashMap<Character, ArrayList<Integer>> map,
			int rsize) {
		if (keyIndex == key.length) {
			return 0;
		}
		int ans = Integer.MAX_VALUE;
		// key[keyIndex]
		for (int curStrIndex : map.get(key[keyIndex])) {
			int step = dial(preStrIndex, curStrIndex, rsize) + 1
					+ minSteps1(curStrIndex, keyIndex + 1, key, map, rsize);
			ans = Math.min(ans, step);
		}
		return ans;
	}

	public static int findRotateSteps2(String r, String k) {
		char[] ring = r.toCharArray();
		int rsize = ring.length;
		HashMap<Character, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < rsize; i++) {
			if (!map.containsKey(ring[i])) {
				map.put(ring[i], new ArrayList<>());
			}
			map.get(ring[i]).add(i);
		}
		int[][] dp = new int[rsize][k.length() + 1];
		for (int i = 0; i < rsize; i++) {
			for (int j = 0; j <= k.length(); j++) {
				dp[i][j] = -1;
			}
		}
		return minSteps2(0, 0, k.toCharArray(), map, rsize, dp);
	}

	public static int minSteps2(int preStrIndex, int keyIndex, char[] key, HashMap<Character, ArrayList<Integer>> map,
			int rsize, int[][] dp) {
		if (dp[preStrIndex][keyIndex] != -1) {
			return dp[preStrIndex][keyIndex];
		}
		if (keyIndex == key.length) {
			dp[preStrIndex][keyIndex] = 0;
			return 0;
		}
		int ans = Integer.MAX_VALUE;
		for (int curStrIndex : map.get(key[keyIndex])) {
			int step = dial(preStrIndex, curStrIndex, rsize) + 1
					+ minSteps2(curStrIndex, keyIndex + 1, key, map, rsize, dp);
			ans = Math.min(ans, step);
		}
		dp[preStrIndex][keyIndex] = ans;
		return ans;
	}

	// 彻底动态规划的方法请同学们自己改出

}
