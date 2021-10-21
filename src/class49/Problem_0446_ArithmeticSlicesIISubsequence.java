package class49;

import java.util.HashMap;

public class Problem_0446_ArithmeticSlicesIISubsequence {

	// 时间复杂度是O(N^2)，最优解的时间复杂度
	// for循环里，会自动忽略长度为1和长度为2的序列
	// 核心是这一句：
	// countMap.get(i).put(dif, countMap.get(i).getOrDefault(dif, 0) + count + 1);
	// 尤其注意最后的+1操作
	public static int numberOfArithmeticSlices(int[] arr) {
		int N = arr.length;
		int all = 0;
		HashMap<Integer, HashMap<Integer, Integer>> countMap = new HashMap<>();
		for (int i = 0; i < N; i++) {
			countMap.put(i, new HashMap<>());
			for (int j = i - 1; j >= 0; j--) {
				long diff = (long) arr[i] - (long) arr[j];
				if (diff <= Integer.MIN_VALUE || diff > Integer.MAX_VALUE) {
					continue;
				}
				int dif = (int) diff;
				int count = countMap.get(j).getOrDefault(dif, 0);
				all += count;
				countMap.get(i).put(dif, countMap.get(i).getOrDefault(dif, 0) + count + 1);
			}
		}
		return all;
	}

}
