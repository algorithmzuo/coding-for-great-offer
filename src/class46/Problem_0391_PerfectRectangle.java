package class46;

import java.util.HashMap;

public class Problem_0391_PerfectRectangle {

	public static boolean isRectangleCover(int[][] matrix) {
		if (matrix.length == 0 || matrix[0].length == 0) {
			return false;
		}
		int l = Integer.MAX_VALUE;
		int r = Integer.MIN_VALUE;
		int d = Integer.MAX_VALUE;
		int u = Integer.MIN_VALUE;
		HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
		int area = 0;
		for (int[] rect : matrix) {
			add(map, rect[0], rect[1]);
			add(map, rect[0], rect[3]);
			add(map, rect[2], rect[1]);
			add(map, rect[2], rect[3]);
			area += (rect[2] - rect[0]) * (rect[3] - rect[1]);
			l = Math.min(rect[0], l);
			d = Math.min(rect[1], d);
			r = Math.max(rect[2], r);
			u = Math.max(rect[3], u);
		}
		return checkPoints(map, l, d, r, u) && area == (r - l) * (u - d);
	}

	public static void add(HashMap<Integer, HashMap<Integer, Integer>> map, int row, int col) {
		if (!map.containsKey(row)) {
			map.put(row, new HashMap<>());
		}
		map.get(row).put(col, map.get(row).getOrDefault(col, 0) + 1);
	}

	public static boolean checkPoints(HashMap<Integer, HashMap<Integer, Integer>> map, int l, int d, int r, int u) {
		if (map.get(l).getOrDefault(d, 0) != 1
				|| map.get(l).getOrDefault(u, 0) != 1
				|| map.get(r).getOrDefault(d, 0) != 1
				|| map.get(r).getOrDefault(u, 0) != 1) {
			return false;
		}
		map.get(l).remove(d);
		map.get(l).remove(u);
		map.get(r).remove(d);
		map.get(r).remove(u);
		for (int key : map.keySet()) {
			for (int value : map.get(key).values()) {
				if ((value & 1) != 0) {
					return false;
				}
			}
		}
		return true;
	}

}
