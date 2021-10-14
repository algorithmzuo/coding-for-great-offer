package class47;

import java.util.HashMap;

// 需要证明：
// 一个集合中，假设整体的累加和为K，
// 不管该集合用了什么样的0集合划分方案，当一个新的数到来时：
// 1) 如果该数是-K，那么任何0集合的划分方案中，因为新数字的加入，0集合的数量都会+1
// 2) 如果该数不是-K，那么任何0集合的划分方案中，0集合的数量都会不变
public class Problem_0465_OptimalAccountBalancing {

	// 用位信息替代集合结构的暴力尝试
	public static int minTransfers1(int[][] transactions) {
		int[] debt = debts(transactions);
		int N = debt.length;
		int sum = 0;
		for (int num : debt) {
			sum += num;
		}
		return N - process1(debt, (1 << N) - 1, sum, N);
	}

	public static int process1(int[] debt, int set, int sum, int N) {
		if ((set & (set - 1)) == 0) {
			return 0;
		}
		int value = 0;
		int max = 0;
		for (int i = 0; i < N; i++) {
			value = debt[i];
			if ((set & (1 << i)) != 0) {
				max = Math.max(max, process1(debt, set ^ (1 << i), sum - value, N));
			}
		}
		return sum == 0 ? max + 1 : max;
	}

	// 记忆化搜索的解
	public static int minTransfers2(int[][] transactions) {
		int[] debt = debts(transactions);
		int N = debt.length;
		int sum = 0;
		for (int num : debt) {
			sum += num;
		}
		int[] dp = new int[1 << N];
		for (int i = 0; i < dp.length; i++) {
			dp[i] = -1;
		}
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

	public static int[][] randomTrans(int s, int n, int m) {
		int[][] trans = new int[s][3];
		for (int i = 0; i < s; i++) {
			trans[i][0] = (int) (Math.random() * n);
			trans[i][1] = (int) (Math.random() * n);
			trans[i][2] = (int) (Math.random() * m) + 1;
		}
		return trans;
	}

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
