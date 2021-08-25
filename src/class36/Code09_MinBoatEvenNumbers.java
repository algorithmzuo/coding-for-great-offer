package class36;

import java.util.Arrays;

// 来自腾讯
// 给定一个正数数组arr，代表每个人的体重。给定一个正数limit代表船的载重，所有船都是同样的载重量
// 每个人的体重都一定不大于船的载重
// 要求：
// 1, 可以1个人单独一搜船
// 2, 一艘船如果坐2人，两个人的体重相加需要是偶数，且总体重不能超过船的载重
// 3, 一艘船最多坐2人
// 返回如果想所有人同时坐船，船的最小数量
public class Code09_MinBoatEvenNumbers {

	public static int minBoat(int[] arr, int limit) {
		Arrays.sort(arr);
		int odd = 0;
		int even = 0;
		for (int num : arr) {
			if ((num & 1) == 0) {
				even++;
			} else {
				odd++;
			}
		}
		int[] odds = new int[odd];
		int[] evens = new int[even];
		for (int i = arr.length - 1; i >= 0; i--) {
			if ((arr[i] & 1) == 0) {
				evens[--even] = arr[i];
			} else {
				odds[--odd] = arr[i];
			}
		}
		return min(odds, limit) + min(evens, limit);
	}

	public static int min(int[] arr, int limit) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int N = arr.length;
		if (arr[N - 1] > limit) {
			return -1;
		}
		int lessR = -1;
		for (int i = N - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return N;
		}
		int L = lessR;
		int R = lessR + 1;
		int noUsed = 0;
		while (L >= 0) {
			int solved = 0;
			while (R < N && arr[L] + arr[R] <= limit) {
				R++;
				solved++;
			}
			if (solved == 0) {
				noUsed++;
				L--;
			} else {
				L = Math.max(-1, L - solved);
			}
		}
		int all = lessR + 1;
		int used = all - noUsed;
		int moreUnsolved = (N - all) - used;
		return used + ((noUsed + 1) >> 1) + moreUnsolved;
	}

}
