package class21;

import java.util.HashMap;

public class Code01_TallestBillboard {

	public int tallestBillboard(int[] rods) {
		// key  集合对的某个差
		// value  满足差值为key的集合对中，最好的一对，较小集合的累加和
		// 较大 -> value + key
		HashMap<Integer, Integer> dp = new HashMap<>(), cur;
		dp.put(0, 0);// 空集   和  空集    
		for (int num : rods) {
			if (num != 0) {
				// cur 内部数据完全和dp一样
				cur = new HashMap<>(dp); // 考虑x之前的集合差值状况拷贝下来
				for (int d : cur.keySet()) {
					int diffMore = cur.get(d); // 最好的一对，较小集合的累加和
					// x决定放入，比较大的那个
					dp.put(d + num, Math.max(diffMore, dp.getOrDefault(num + d, 0)));
					// x决定放入，比较小的那个
					// 新的差值 Math.abs(x - d)
					// 之前差值为Math.abs(x - d)，的那一对，就要和这一对，决策一下
					// 之前那一对，较小集合的累加和diffXD
					int diffXD = dp.getOrDefault(Math.abs(num - d), 0);
					if (d >= num) { // x决定放入比较小的那个, 但是放入之后，没有超过这一对较大的那个
						dp.put(d - num, Math.max(diffMore + num, diffXD));
					} else { // x决定放入比较小的那个, 但是放入之后，没有超过这一对较大的那个
						dp.put(num - d, Math.max(diffMore + d, diffXD));
					}
				}
			}
		}
		return dp.get(0);
	}

}
