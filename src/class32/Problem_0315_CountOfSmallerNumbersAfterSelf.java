package class32;

import java.util.ArrayList;
import java.util.List;

public class Problem_0315_CountOfSmallerNumbersAfterSelf {

	public static class Node {
		public int value;
		public int index;

		public Node(int v, int i) {
			value = v;
			index = i;
		}
	}

	public static List<Integer> countSmaller(int[] nums) {
		List<Integer> ans = new ArrayList<>();
		if (nums == null) {
			return ans;
		}
		for (int i = 0; i < nums.length; i++) {
			ans.add(0);
		}
		if (nums.length < 2) {
			return ans;
		}
		Node[] arr = new Node[nums.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = new Node(nums[i], i);
		}
		process(arr, 0, arr.length - 1, ans);
		return ans;
	}

	public static void process(Node[] arr, int l, int r, List<Integer> ans) {
		if (l == r) {
			return;
		}
		int mid = l + ((r - l) >> 1);
		process(arr, l, mid, ans);
		process(arr, mid + 1, r, ans);
		merge(arr, l, mid, r, ans);
	}

	public static void merge(Node[] arr, int l, int m, int r, List<Integer> ans) {
		Node[] help = new Node[r - l + 1];
		int i = help.length - 1;
		int p1 = m;
		int p2 = r;
		while (p1 >= l && p2 >= m + 1) {
			if (arr[p1].value > arr[p2].value) {
				ans.set(arr[p1].index, ans.get(arr[p1].index) + p2 - m);
			}
			help[i--] = arr[p1].value > arr[p2].value ? arr[p1--] : arr[p2--];
		}
		while (p1 >= l) {
			help[i--] = arr[p1--];
		}
		while (p2 >= m + 1) {
			help[i--] = arr[p2--];
		}
		for (i = 0; i < help.length; i++) {
			arr[l + i] = help[i];
		}
	}

}
