package class46;

import java.util.ArrayList;
import java.util.Arrays;

// 本题的解法思路与leetcode 621题 TaskScheduler 问题是一样的
public class Problem_0358_RearrangeStringKDistanceApart {

	public static class Count {
		public int cha;
		public int times;

		public Count(int c, int t) {
			cha = c;
			times = t;
		}
	}

	public String rearrangeString(String s, int k) {
		if (s == null || s.length() < k) {
			return s;
		}
		char[] str = s.toCharArray();
		Count[] counts = new Count[256];
		for (int i = 0; i < 256; i++) {
			counts[i] = new Count(i, 0);
		}
		int maxCount = 0;
		for (char task : str) {
			counts[task].times++;
			maxCount = Math.max(maxCount, counts[task].times);
		}
		int maxKinds = 0;
		for (int task = 0; task < 256; task++) {
			if (counts[task].times == maxCount) {
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
		Arrays.sort(counts, (a, b) -> (b.times - a.times));
		int i = 0;
		for (; i < 256 && counts[i].times == maxCount; i++) {
			char cha = (char) counts[i].cha;
			for (int j = 0; j < maxCount; j++) {
				ans.get(j).append(cha);
			}
		}
		int out = 0;
		for (; i < 256; i++) {
			char cha = (char) counts[i].cha;
			for (int j = 0; j < counts[i].times; j++) {
				ans.get(out).append(cha);
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
