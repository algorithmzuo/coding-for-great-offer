package class53;

import java.util.ArrayList;
import java.util.List;

// 来自真实面试，同学给我的问题
public class Code01_LongestAddOneIncreasingSubarray {

	// 给定数组arr，找到连续+1递增的最长子数组
	// 比如：1，3，5，2，1，2，3，4，1，2，3，5，4中，
	// 连续+1递增的最长子数组是1，2，3，4
	public static List<Integer> findMaxLenSequence(List<Integer> list) {
		List<Integer> ans = new ArrayList<>();
		if (list.size() == 0) {
			return ans;
		}
		int curl = 0;
		int curr = 0;
		int curLen = 1;
		int maxl = 0;
		int maxr = 0;
		int maxLen = 1;
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).equals(list.get(i - 1) + 1)) {
				curr++;
				curLen++;
			} else {
				if (curLen > maxLen) {
					maxl = curl;
					maxr = curr;
					maxLen = curLen;
				}
				curl = i;
				curr = i;
				curLen = 1;
			}
		}
		if (curLen > maxLen) {
			maxl = curl;
			maxr = curr;
			maxLen = curLen;
		}
		for (int i = maxl; i <= maxr; i++) {
			ans.add(list.get(i));
		}
		return ans;
	}

	// 为了测试
	// 这是暴力方法，只是为了验证正式方法是否正确
	public static List<Integer> test(List<Integer> list) {
		List<Integer> ans = new ArrayList<>();
		if (list.size() == 0) {
			return ans;
		}
		int maxLen = 1;
		int maxl = 0;
		int maxr = 0;
		for (int l = 0; l < list.size(); l++) {
			for (int r = l + 1; r < list.size(); r++) {
				if (is(list, l, r)) {
					if (r - l + 1 > maxLen) {
						maxLen = r - l + 1;
						maxl = l;
						maxr = r;
					}
				}
			}
		}
		for (int i = maxl; i <= maxr; i++) {
			ans.add(list.get(i));
		}
		return ans;
	}

	// 为了测试
	public static boolean is(List<Integer> list, int l, int r) {
		for (int i = l + 1; i <= r; i++) {
			if (!list.get(i).equals(list.get(i - 1) + 1)) {
				return false;
			}
		}
		return true;
	}

	// 为了测试
	public static List<Integer> randomList(int len, int value) {
		List<Integer> ans = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			ans.add((int) (Math.random() * value));
		}
		return ans;
	}

	// 为了测试
	public static void printList(List<Integer> list) {
		for (int num : list) {
			System.out.print(num + " ");
		}
		System.out.println();
	}

	// 为了测试
	public static boolean sameList(List<Integer> l1, List<Integer> l2) {
		if (l1.size() != l2.size()) {
			return false;
		}
		for (int i = 0; i < l1.size(); i++) {
			if (!l1.get(i).equals(l2.get(i))) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// 你的例子
		List<Integer> list = new ArrayList<>();
		list.add(89);
		list.add(2);
		list.add(73);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		list.add(8);
		list.add(10);
		list.add(12);
		list.add(15);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(8);
		list.add(9);
		list.add(10);
		list.add(11);
		list.add(23);
		list.add(54);
		System.out.println("例子：");
		printList(list);

		List<Integer> ans = findMaxLenSequence(list);
		System.out.println("答案：");
		printList(ans);

		int len = 100;
		int value = 100;
		int testTime = 10000;
		System.out.println("随机测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * len);
			List<Integer> arr = randomList(n, value);
			List<Integer> ans1 = findMaxLenSequence(arr);
			List<Integer> ans2 = test(arr);
			if (!sameList(ans1, ans2)) {
				printList(ans1);
				printList(ans2);
				System.out.println("出错了!");
			}
		}
		System.out.println("随机测试结束");

	}

}
