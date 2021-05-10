package class06;

import java.util.ArrayList;
import java.util.HashMap;

public class Code04_MostEOR {

	// 暴力方法
	public static int comparator(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[] eor = new int[N];
		eor[0] = arr[0];
		for (int i = 1; i < N; i++) {
			eor[i] = eor[i - 1] ^ arr[i];
		}
		return process(eor, 1, new ArrayList<>());
	}

	public static int process(int[] eor, int index, ArrayList<Integer> parts) {
		int ans = 0;
		if (index == eor.length) {
			parts.add(eor.length);
			ans = eorZeroParts(eor, parts);
			parts.remove(parts.size() - 1);
		} else {
			int p1 = process(eor, index + 1, parts);
			parts.add(index);
			int p2 = process(eor, index + 1, parts);
			parts.remove(parts.size() - 1);
			ans = Math.max(p1, p2);
		}
		return ans;
	}

	public static int eorZeroParts(int[] eor, ArrayList<Integer> parts) {
		int L = 0;
		int ans = 0;
		for (Integer end : parts) {
			if ((eor[end - 1] ^ (L == 0 ? 0 : eor[L - 1])) == 0) {
				ans++;
			}
			L = end;
		}
		return ans;
	}

	// 时间复杂度O(N)的方法
	public static int mostEOR(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		int[] dp = new int[N];
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int sum = 0;
		for (int i = 0; i < arr.length; i++) {
			sum ^= arr[i];
			if (map.containsKey(sum)) {
				int pre = map.get(sum);
				dp[i] = pre == -1 ? 1 : (dp[pre] + 1);
			}
			if (i > 0) {
				dp[i] = Math.max(dp[i - 1], dp[i]);
			}
			map.put(sum, i);
		}
		return dp[dp.length - 1];
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 150000;
		int maxSize = 12;
		int maxValue = 3;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int res = mostEOR(arr);
			int comp = comparator(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}

}
