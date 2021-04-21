package class01;

import java.util.HashMap;

// leetcode 494题
public class Code07_TargetSum {

	public static int findTargetSumWays1(int[] arr, int s) {
		return process1(arr, 0, s);
	}

	// 可以自由使用arr[index....]所有的数字！
	// 搞出rest这个数，方法数是多少？返回
	// index == 7 rest = 13
	// map "7_13" 256
	public static int process1(int[] arr, int index, int rest) {
		if (index == arr.length) { // 没数了！
			return rest == 0 ? 1 : 0;
		}
		// 还有数！arr[index] arr[index+1 ... ]
		return process1(arr, index + 1, rest - arr[index]) + process1(arr, index + 1, rest + arr[index]);
	}

	public static int findTargetSumWays2(int[] arr, int s) {
		return process2(arr, 0, s, new HashMap<>());
	}

	public static int process2(int[] arr, int index, int rest, HashMap<Integer, HashMap<Integer, Integer>> dp) {
		if (dp.containsKey(index) && dp.get(index).containsKey(rest)) {
			return dp.get(index).get(rest);
		}
		// 否则，没命中！
		int ans = 0;
		if (index == arr.length) {
			ans = rest == 0 ? 1 : 0;
		} else {
			ans = process2(arr, index + 1, rest - arr[index], dp) + process2(arr, index + 1, rest + arr[index], dp);
		}
		if (!dp.containsKey(index)) {
			dp.put(index, new HashMap<>());
		}
		dp.get(index).put(rest, ans);
		return ans;
	}

	// 优化点一 :
	// 你可以认为arr中都是非负数
	// 因为即便是arr中有负数，比如[3,-4,2]
	// 因为你能在每个数前面用+或者-号
	// 所以[3,-4,2]其实和[3,4,2]达成一样的效果
	// 那么我们就全把arr变成非负数，不会影响结果的
	// 优化点二 :
	// 如果arr都是非负数，并且所有数的累加和是sum
	// 那么如果target<sum，很明显没有任何方法可以达到target，可以直接返回0
	// 优化点三 :
	// 因为题目要求一定要使用所有数字去拼target，
	// 所以不管这些数字怎么用+和-折腾，最终的结果都一定不会改变奇偶性
	// 所以，如果所有数的累加和是sum，
	// 并且与target的奇偶性不一样，没有任何方法可以达到target，可以直接返回0
	// 优化点四 :
	// 比如说给定一个数组, arr = [1, 2, 3, 4, 5] 并且 target = 3
	// 其中一个方案是 : +1 -2 +3 -4 +5 = 3
	// 该方案中取了正的集合为P = {1，3，5}
	// 该方案中取了负的集合为N = {2，4}
	// 所以任何一种方案，都一定有 sum(P) - sum(N) = target
	// 现在我们来处理一下这个等式，把左右两边都加上sum(P) + sum(N)，那么就会变成如下：
	// sum(P) - sum(N) + sum(P) + sum(N) = target + sum(P) + sum(N)
	// 2 * sum(P) = target + 数组所有数的累加和
	// sum(P) = (target + 数组所有数的累加和) / 2
	// 也就是说，任何一个集合，只要累加和是(target + 数组所有数的累加和) / 2
	// 那么就一定对应一种target的方式
	// 也就是说，比如非负数组arr，target = 7, 而所有数累加和是11
	// 求使用所有数字的情况下，多少方法最后形成7？
	// 其实就是求有多少个子集的累加和是9 -> (7 + 11) / 2
	// 优化点五 :
	// 二维动态规划的空间压缩技巧
	public static int findTargetSumWays3(int[] arr, int target) {
		int sum = 0;
		for (int n : arr) {
			sum += n;
		}
		return sum < target || ((target & 1) ^ (sum & 1)) != 0 ? 0 : subset(arr, (target + sum) >> 1);
	}

	public static int subset(int[] nums, int s) {
		int[] dp = new int[s + 1];
		dp[0] = 1;
		for (int n : nums) {
			for (int i = s; i >= n; i--) {
				dp[i] += dp[i - n];
			}
		}
		return dp[s];
	}

}
