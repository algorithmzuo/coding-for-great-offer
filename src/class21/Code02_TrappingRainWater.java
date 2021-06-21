package class21;

public class Code02_TrappingRainWater {

	/*
	 * 给定一个正整数数组arr，把arr想象成一个直方图。返回这个直方图如果装水，能装下几格水？
	 * 
	 */

	public static int water1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int water = 0;
		for (int i = 1; i < N - 1; i++) {
			int leftMax = Integer.MIN_VALUE;
			for (int j = 0; j < i; j++) {
				leftMax = Math.max(leftMax, arr[j]);
			}
			int rightMax = Integer.MIN_VALUE;
			for (int j = i + 1; j < N; j++) {
				rightMax = Math.max(rightMax, arr[j]);
			}
			water += Math.max(Math.min(leftMax, rightMax) - arr[i], 0);
		}
		return water;
	}

	public static int water2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int[] leftMaxs = new int[N];
		leftMaxs[0] = arr[0];
		for (int i = 1; i < N; i++) {
			leftMaxs[i] = Math.max(leftMaxs[i - 1], arr[i]);
		}

		int[] rightMaxs = new int[N];
		rightMaxs[N - 1] = arr[N - 1];
		for (int i = N - 2; i >= 0; i--) {
			rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i]);
		}
		int water = 0;
		for (int i = 1; i < N - 1; i++) {
			water += Math.max(Math.min(leftMaxs[i - 1], rightMaxs[i + 1]) - arr[i], 0);
		}
		return water;
	}

	public static int water3(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int[] rightMaxs = new int[N];
		rightMaxs[N - 1] = arr[N - 1];
		for (int i = N - 2; i >= 0; i--) {
			rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i]);
		}
		int water = 0;
		int leftMax = arr[0];
		for (int i = 1; i < N - 1; i++) {
			water += Math.max(Math.min(leftMax, rightMaxs[i + 1]) - arr[i], 0);
			leftMax = Math.max(leftMax, arr[i]);
		}
		return water;
	}

	public static int water4(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int L = 1;
		int leftMax = arr[0];
		int R = N - 2;
		int rightMax = arr[N - 1];
		int water = 0;
		while (L <= R) {
			if (leftMax <= rightMax) {
				water += Math.max(0, leftMax - arr[L]);
				leftMax = Math.max(leftMax, arr[L++]);
			} else {
				water += Math.max(0, rightMax - arr[R]);
				rightMax = Math.max(rightMax, arr[R--]);
			}
		}
		return water;
	}

	// for test
	public static int[] generateRandomArray(int len, int value) {
		int[] ans = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (int) (Math.random() * value) + 1;
		}
		return ans;
	}

	public static void main(String[] args) {
		int len = 100;
		int value = 200;
		int testTimes = 100000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			int[] arr = generateRandomArray(len, value);
			int ans1 = water1(arr);
			int ans2 = water2(arr);
			int ans3 = water3(arr);
			int ans4 = water4(arr);
			if (ans1 != ans2 || ans3 != ans4 || ans1 != ans3) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
