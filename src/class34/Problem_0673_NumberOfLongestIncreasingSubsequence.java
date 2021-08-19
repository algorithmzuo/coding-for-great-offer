package class34;

import java.util.ArrayList;
import java.util.TreeMap;

public class Problem_0673_NumberOfLongestIncreasingSubsequence {

	public static int findNumberOfLIS(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
		int find = 0;
		int count = 0;
		for (int num : nums) {
			find = search(dp, num);
			if (find == 0) {
				count = 1;
			} else {
				TreeMap<Integer, Integer> pre = dp.get(find - 1);
				count = pre.firstEntry().getValue()
						- ((pre.ceilingKey(num) != null) ? pre.get(pre.ceilingKey(num)) : 0);
			}
			if (find == dp.size()) {
				dp.add(new TreeMap<Integer, Integer>());
				dp.get(find).put(num, count);
			} else {
				dp.get(find).put(num, dp.get(find).firstEntry().getValue() + count);
			}
		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

	// >=num 最左的位置返回
	public static int search(ArrayList<TreeMap<Integer, Integer>> dp, int num) {
		int l = 0;
		int r = dp.size() - 1;
		int m = 0;
		int ans = dp.size();
		while (l <= r) {
			m = (l + r) / 2;
			if (dp.get(m).firstKey() >= num) {
				ans = m;
				r = m - 1;
			} else {
				l = m + 1;
			}
		}
		return ans;
	}

}
