package class35;

import java.util.ArrayList;
import java.util.TreeMap;

public class Problem_0673_NumberOfLongestIncreasingSubsequence {

	// 好理解的方法，时间复杂度O(N^2)
	public static int findNumberOfLIS1(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int n = nums.length;
		int[] lens = new int[n];
		int[] cnts = new int[n];
		lens[0] = 1;
		cnts[0] = 1;
		int maxLen = 1;
		int allCnt = 1;
		for (int i = 1; i < n; i++) {
			int preLen = 0;
			int preCnt = 1;
			for (int j = 0; j < i; j++) {
				if (nums[j] >= nums[i] || preLen > lens[j]) {
					continue;
				}
				if (preLen < lens[j]) {
					preLen = lens[j];
					preCnt = cnts[j];
				} else {
					preCnt += cnts[j];
				}
			}
			lens[i] = preLen + 1;
			cnts[i] = preCnt;
			if (maxLen < lens[i]) {
				maxLen = lens[i];
				allCnt = cnts[i];
			} else if (maxLen == lens[i]) {
				allCnt += cnts[i];
			}
		}
		return allCnt;
	}

	// 优化后的最优解，时间复杂度O(N*logN)
	public static int findNumberOfLIS2(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		ArrayList<TreeMap<Integer, Integer>> dp = new ArrayList<>();
		int len = 0;
		int cnt = 0;
		for (int num : nums) {
			// num之前的长度，num到哪个长度len+1
			len = search(dp, num);
			// cnt : 最终要去加底下的记录，才是应该填入的value
			if (len == 0) {
				cnt = 1;
			} else {
				TreeMap<Integer, Integer> p = dp.get(len - 1);
				cnt = p.firstEntry().getValue() - (p.ceilingKey(num) != null ? p.get(p.ceilingKey(num)) : 0);
			}
			if (len == dp.size()) {
				dp.add(new TreeMap<Integer, Integer>());
				dp.get(len).put(num, cnt);
			} else {
				dp.get(len).put(num, dp.get(len).firstEntry().getValue() + cnt);
			}
		}
		return dp.get(dp.size() - 1).firstEntry().getValue();
	}

	// 二分查找，返回>=num最左的位置
	public static int search(ArrayList<TreeMap<Integer, Integer>> dp, int num) {
		int l = 0, r = dp.size() - 1, m = 0;
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
