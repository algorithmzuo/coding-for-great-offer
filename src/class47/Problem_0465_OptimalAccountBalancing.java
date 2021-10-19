package class47;

import java.util.Arrays;
import java.util.HashMap;

// 需要证明：
// 一个集合中，假设整体的累加和为K，
// 不管该集合用了什么样的0集合划分方案，当一个新的数到来时：
// 1) 如果该数是-K，那么任何0集合的划分方案中，因为新数字的加入，0集合的数量都会+1
// 2) 如果该数不是-K，那么任何0集合的划分方案中，0集合的数量都会不变
public class Problem_0465_OptimalAccountBalancing {

	// 用位信息替代集合结构的暴力尝试
	public static int minTransfers1(int[][] transactions) {
		// 不管转账有几笔，最终每个人收到的钱，如果是0，不进入debt数组
		// 只有最终收到的钱，不等于0的人，进入debt数组
		// 收到的钱，4，说明该给出去！
		// 收到的钱，-4，说明该要回来！
		// debt数组的累加和，必为0！
		int[] debt = debts(transactions);
		int N = debt.length;
		return N - process1(debt, (1 << N) - 1, 0, N);
	}

	// set -> int -> 不使用值 -> 只使用状态！
	// 001110 0号人，不在集合里；1、2、3号人在集合里，4、5号人不在集合里！
	// sum -> set这个集合累加和是多少？sum被set决定的！
	// debt数组，收到的钱的数组(固定)
	// N, debt的长度(固定)
	// 返回值含义 : set这个集合中，最多能划分出多少个小集合累加和是0，返回累加和是0的小集合最多的数量
	public static int process1(int[] debt, int set, int sum, int N) {
		// set中只有一个人的时候！
		// debt中，没有0的，所以每个人一定都需要转账！
		if ((set & (set - 1)) == 0) {
			return 0;
		}
		int value = 0;
		int max = 0;
		// 尝试，每一个人都最后考虑
		// 0,如果最后考虑
		// 1,如果最后考虑
		// 2,如果最后考虑
		// ....
		// n-1，最后考虑
		// 011001
		// 0（在）
		// 1（不能考虑！因为不在集合里！）
		for (int i = 0; i < N; i++) {
			value = debt[i];
			if ((set & (1 << i)) != 0) { // i号人真的在集合里，才能考虑！
				// 011001
				// 3号人在
				// 3号人之前，010001（考虑0号人和4号人剩下的情况）
				// process ( set ^ (1 << i) , sum - value )
				max = Math.max(max, process1(debt, set ^ (1 << i), sum - value, N));
			}
		}
		return sum == 0 ? max + 1 : max;
	}

	// 上面的尝试过程 + 记忆化搜索
	// 最优解
	public static int minTransfers2(int[][] transactions) {
		int[] debt = debts(transactions);
		int N = debt.length;
		int sum = 0;
		for (int num : debt) {
			sum += num;
		}
		int[] dp = new int[1 << N];
		Arrays.fill(dp, -1);
		return N - process2(debt, (1 << N) - 1, sum, N, dp);
	}

	public static int process2(int[] debt, int set, int sum, int N, int[] dp) {
		if (dp[set] != -1) {
			return dp[set];
		}
		int ans = 0;
		if ((set & (set - 1)) != 0) {
			int value = 0;
			int max = 0;
			for (int i = 0; i < N; i++) {
				value = debt[i];
				if ((set & (1 << i)) != 0) {
					max = Math.max(max, process2(debt, set ^ (1 << i), sum - value, N, dp));
				}
			}
			ans = sum == 0 ? max + 1 : max;
		}
		dp[set] = ans;
		return ans;
	}

	public static int[] debts(int[][] transactions) {
		HashMap<Integer, Integer> map = new HashMap<>();
		for (int[] tran : transactions) {
			map.put(tran[0], map.getOrDefault(tran[0], 0) + tran[2]);
			map.put(tran[1], map.getOrDefault(tran[1], 0) - tran[2]);
		}
		int N = 0;
		for (int value : map.values()) {
			if (value != 0) {
				N++;
			}
		}
		int[] debt = new int[N];
		int index = 0;
		for (int value : map.values()) {
			if (value != 0) {
				debt[index++] = value;
			}
		}
		return debt;
	}

	// 为了测试
	public static int[][] randomTrans(int s, int n, int m) {
		int[][] trans = new int[s][3];
		for (int i = 0; i < s; i++) {
			trans[i][0] = (int) (Math.random() * n);
			trans[i][1] = (int) (Math.random() * n);
			trans[i][2] = (int) (Math.random() * m) + 1;
		}
		return trans;
	}

	// 为了测试
	public static void main(String[] args) {
		int s = 8;
		int n = 8;
		int m = 10;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int[][] trans = randomTrans(s, n, m);
			int ans1 = minTransfers1(trans);
			int ans2 = minTransfers2(trans);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
		System.out.println("测试结束");
	}

}
