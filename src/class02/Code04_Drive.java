package class02;

public class Code04_Drive {

	/*
	 * 司机调度 时间限制： 3000MS 内存限制： 589824KB 题目描述：
	 * 正值下班高峰时期，现有可载客司机数2N人，调度中心将调度相关司机服务A、B两个出行高峰区域。 第 i 个司机前往服务A区域可得收入为
	 * income[i][0]，前往服务B区域可得收入为 income[i][1]。
	 * 返回将每位司机调度完成服务后，所有司机总可得的最高收入金额，要求每个区域都有 N 位司机服务。 输入描述 10 20 20 40 # 如上：
	 * 第一个司机服务 A 区域，收入为 10元 第一个司机服务 B 区域，收入为 20元 第二个司机服务 A 区域，收入为 20元 第二个司机服务 B
	 * 区域，收入为 40元 输入参数以 '#' 结束输入 输出描述 最高总收入为 10 + 40= 50，每个区域都有一半司机服务
	 * 参数及相关数据异常请输出：error 样例输入 : 10 30 100 200 150 50 60 20 # 样例输出 440
	 */

	// 给定一个N*2的正数矩阵matix，N一定是偶数，可以保证。
	// 一定要让A区域分到N/2个司机，让B区域也分到N/2个司机
	// 返回最大的总收益
	// 这是暴力解
	public static int maxMoney1(int[][] matrix) {
		int N = matrix.length;
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = i;
		}
		return process1(arr, 0, matrix);
	}

	public static int process1(int[] arr, int index, int[][] matrix) {
		int money = 0;
		if (index == arr.length) {
			for (int i = 0; i < arr.length; i++) {
				money += i < (arr.length / 2) ? matrix[arr[i]][0] : matrix[arr[i]][1];
			}
		} else {
			for (int i = index; i < arr.length; i++) {
				swap(arr, index, i);
				money = Math.max(money, process1(arr, index + 1, matrix));
				swap(arr, index, i);
			}
		}
		return money;
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// 这是优良尝试解
	public static int maxMoney2(int[][] matrix) {
		return process2(matrix, 0, matrix.length / 2);
	}

	// 从i开始到最后所有的司机，在A区域还有aRest个名额的情况下，返回最优分配的收益
	public static int process2(int[][] matrix, int i, int aRest) {
		if (aRest < 0) {
			return -1;
		}
		if (i == matrix.length) {
			return aRest == 0 ? 0 : -1;
		}
		int goToA = -1;
		int nextA = process2(matrix, i + 1, aRest - 1);
		if (nextA != -1) {
			goToA = matrix[i][0] + nextA;
		}
		int goToB = -1;
		int nextB = process2(matrix, i + 1, aRest);
		if (nextB != -1) {
			goToB = matrix[i][1] + nextB;
		}
		return Math.max(goToA, goToB);
	}

	// 这是动态规划解
	public static int maxMoney3(int[][] matrix) {
		int N = matrix.length;
		int M = N >> 1;
		int[][] dp = new int[N + 1][M + 1];
		for (int j = 1; j <= M; j++) {
			dp[N][j] = -1;
		}
		for (int i = N - 1; i >= 0; i--) {
			for (int j = 0; j <= M; j++) {
				int goToA = -1;
				if (j - 1 >= 0 && dp[i + 1][j - 1] != -1) {
					goToA = matrix[i][0] + dp[i + 1][j - 1];
				}
				int goToB = -1;
				if (dp[i + 1][j] != -1) {
					goToB = matrix[i][1] + dp[i + 1][j];
				}
				dp[i][j] = Math.max(goToA, goToB);
			}
		}
		return dp[0][M];
	}

	// 返回随机len*2大小的正数矩阵
	// 值在0~value-1之间
	public static int[][] randomMatrix(int len, int value) {
		int[][] ans = new int[len << 1][2];
		for (int i = 0; i < ans.length; i++) {
			ans[i][0] = (int) (Math.random() * value);
			ans[i][1] = (int) (Math.random() * value);
		}
		return ans;
	}

	public static void main(String[] args) {
		int N = 5;
		int value = 100;
		int testTime = 500;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * N) + 1;
			int[][] matrix = randomMatrix(len, value);
			int ans1 = maxMoney1(matrix);
			int ans2 = maxMoney2(matrix);
			int ans3 = maxMoney3(matrix);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
				System.out.println("Oops!");
			}
		}
		System.out.println("测试结束");
	}

}
