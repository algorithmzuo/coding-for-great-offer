package class03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

// 本题测试链接 : https://leetcode.com/problems/freedom-trail/
public class Code07_FreedomTrail {

	// 提交时把findRotateSteps1方法名字改成findRotateSteps可以直接通过
	public static int findRotateSteps1(String r, String k) {
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

	// 以下方法来自陆振星同学
	// 可以省掉枚举行为
	// 提交时把findRotateSteps2方法名字改成findRotateSteps可以直接通过
	// 来龙去脉 : https://www.mashibing.com/question/detail/67299
	// 例 :
	// ring = aaca
	// key = ca
	// 首先来到 2 位置的 c, 下一步有 0 1 3 三个位置的 a 可以选择
	// 只需要在 1 3 里面选择最优的即可, 因为 0 位置的 a 在任何情况下都不会比 1 位置的 a 更优
	// 这是对的
	// 应该是可以的，课上讲述的方法根据数据量能过，就没有继续优化了。
	// 如下的贪心方式不对：
	// 当前来到的位置，然后要去下一个字符，左边离下一个字符近就选左边，左边离下一个字符近就选右边。
	// 两条路只选1条，彻底的每一步都选当前步最优。
	// 但陆振星同学的贪心方式是：
	// 1）当前来到的位置，然后要去下一个字符，左边离下一个字符最近的位置，去往左边，然后可能性展开，选最好的解；
	// 2）当前来到的位置，然后要去下一个字符，右边离下一个字符最近的位置，去往右边，然后可能性展开，选最好的解；
	// 1）和2）中最好的解，选出来，返回。
	// 这是对的，因为如果去往离左右两边都更远的位置，那么为什么不在走的过程中，顺带就满足了下一个字符呢？
	// 这样还能省掉枚举行为
	// 如下所有代码都提交，再次注意：提交时把findRotateSteps2方法名字改成findRotateSteps可以直接通过
	private int ringLength;
	private char[] key;
	private ArrayList<TreeSet<Integer>> ringSet;
	private final HashMap<Integer, Integer> dp = new HashMap<>();

	public int findRotateSteps2(String ring, String key) {
		char[] chars = ring.toCharArray();
		this.key = key.toCharArray();
		ringLength = chars.length;
		ringSet = new ArrayList<>();
		for (int i = 0; i < 26; i++) {
			ringSet.add(new TreeSet<>());
		}
		for (int i = 0; i < chars.length; i++) {
			ringSet.get(chars[i] - 'a').add(i);
		}
		return findRotateSteps(0, 0);
	}

	// kIndex : 当前要搞定的字符
	// cur : 当前所在的位置
	private int findRotateSteps(int kIndex, int cur) {
		if (kIndex == key.length) {
			return 0;
		}
		// kIndex 和 cur 的最大值为 100, 所以用 十进制的低两位 表示 cur, 用高两位表示 kIndex
		int k = kIndex * 100 + cur - 1;
		Integer v = dp.get(k);
		if (v != null) {
			return v;
		} else {
			v = Integer.MAX_VALUE;
		}
		// key[kIndex] 的所有位置
		TreeSet<Integer> treeSet = ringSet.get(key[kIndex] - 'a');
		// 从 cur 向左走, 最近的符合 key[kIndex] 的位置
		Integer floor = treeSet.floor(cur);
		if (floor == null) {
			floor = treeSet.last();
		}
		int len = Math.abs(cur - floor);
		len = Math.min(len, ringLength - len);
		v = Math.min(v, len + 1 + findRotateSteps(kIndex + 1, floor));
		// 从 cur 向右走, 最近的符合 key[kIndex] 的位置
		Integer ceiling = treeSet.ceiling(cur);
		if (ceiling == null) {
			ceiling = treeSet.first();
		}
		len = Math.abs(cur - ceiling);
		len = Math.min(len, ringLength - len);
		v = Math.min(v, len + 1 + findRotateSteps(kIndex + 1, ceiling));
		dp.put(k, v);
		return v;
	}

}
