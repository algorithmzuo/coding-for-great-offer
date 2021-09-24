package class43;

import java.util.TreeMap;

// 提交时需要修改类名、构造方法名
// 从Problem_0352_DataStreamAsDisjointIntervals改成SummaryRanges
public class Problem_0352_DataStreamAsDisjointIntervals {

	private TreeMap<Integer, Integer> rangeMap;

	public Problem_0352_DataStreamAsDisjointIntervals() {
		rangeMap = new TreeMap<>();
	}

	public void addNum(int val) {
		if (!rangeMap.containsKey(val)) {
			rangeMap.put(val, 1);
			merge(merge(rangeMap.floorKey(val - 1), val), rangeMap.ceilingKey(val + 1));
		}
	}

	private Integer merge(Integer pre, Integer cur) {
		if (pre == null && cur == null) {
			return null;
		}
		if (pre == null || cur == null) {
			return pre != null ? pre : cur;
		}
		if (pre + rangeMap.get(pre) >= cur) {
			rangeMap.put(pre, Math.max(pre + rangeMap.get(pre), cur + rangeMap.get(cur)) - pre);
			rangeMap.remove(cur);
			return pre;
		} else {
			return cur;
		}
	}

	public int[][] getIntervals() {
		int N = rangeMap.size();
		int[][] ans = new int[N][];
		int index = 0;
		for (Integer key : rangeMap.keySet()) {
			ans[index++] = new int[] { key, key + rangeMap.get(key) - 1 };
		}
		return ans;
	}

}
