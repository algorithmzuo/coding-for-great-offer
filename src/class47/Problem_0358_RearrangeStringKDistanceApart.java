package class47;

import java.util.ArrayList;
import java.util.Arrays;

// 本题的解法思路与leetcode 621题 TaskScheduler 问题是一样的
public class Problem_0358_RearrangeStringKDistanceApart {

	public String rearrangeString(String s, int k) {
		if (s == null || s.length() < k) {
			return s;
		}
		char[] str = s.toCharArray();
		int[][] cnts = new int[256][2];
		for (int i = 0; i < 256; i++) {
			cnts[i] = new int[] { i, 0 };
		}
		int maxCount = 0;
		for (char task : str) {
			cnts[task][1]++;
			maxCount = Math.max(maxCount, cnts[task][1]);
		}
		int maxKinds = 0;
		for (int task = 0; task < 256; task++) {
			if (cnts[task][1] == maxCount) {
				maxKinds++;
			}
		}
		int N = str.length;
		if (!isValid(N, k, maxCount, maxKinds)) {
			return "";
		}
		ArrayList<StringBuilder> ans = new ArrayList<>();
		for (int i = 0; i < maxCount; i++) {
			ans.add(new StringBuilder());
		}
		Arrays.sort(cnts, (a, b) -> (b[1] - a[1]));
		int i = 0;
		for (; i < 256 && cnts[i][1] == maxCount; i++) {
			for (int j = 0; j < maxCount; j++) {
				ans.get(j).append((char) cnts[i][0]);
			}
		}
		int out = 0;
		for (; i < 256; i++) {
			for (int j = 0; j < cnts[i][1]; j++) {
				ans.get(out).append((char) cnts[i][0]);
				out = out == ans.size() - 2 ? 0 : out + 1;
			}
		}
		StringBuilder builder = new StringBuilder();
		for (StringBuilder b : ans) {
			builder.append(b.toString());
		}
		return builder.toString();
	}

	public static boolean isValid(int N, int k, int maxCount, int maxKinds) {
		int restTasks = N - maxKinds;
		int spaces = k * (maxCount - 1);
		return spaces - restTasks <= 0;
	}

}
