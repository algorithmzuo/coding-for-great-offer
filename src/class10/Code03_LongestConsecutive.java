package class10;

import java.util.HashMap;

public class Code03_LongestConsecutive {

	public static int longestConsecutive(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = 1;
		// 不区分开头和结尾
		// (key, value)
		// key所在的连续区域，这个区域一共有几个数，value
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < arr.length; i++) { // 从左到右遍历每一个数字
			if (!map.containsKey(arr[i])) {
				map.put(arr[i], 1); // arr[i]  ~ arr[i]
 				if (map.containsKey(arr[i] - 1)) { // 是否有arr[i] - 1的结尾
					max = Math.max(max, merge(map, arr[i] - 1, arr[i]));
				}
				if (map.containsKey(arr[i] + 1)) { // 是否有arr[i] + 1的开头
					max = Math.max(max, merge(map, arr[i], arr[i] + 1));
				}
			}
		}
		return max;
	}

	public static int merge(HashMap<Integer, Integer> map, int preRangeEnd, int curRangeStart) {
		int preRangeStart = preRangeEnd - map.get(preRangeEnd) + 1;
		int curRangeEnd = curRangeStart + map.get(curRangeStart) - 1;
		int len = curRangeEnd - preRangeStart + 1;
		map.put(preRangeStart, len);
		map.put(curRangeEnd, len);
		return len;
	}

	public static void main(String[] args) {
		int[] arr = { 100, 4, 200, 1, 3, 2 };
		System.out.println(longestConsecutive(arr));

	}

}
