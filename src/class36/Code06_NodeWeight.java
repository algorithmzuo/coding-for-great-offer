package class36;

import java.util.HashMap;

// 来自美团
public class Code06_NodeWeight {

	public static void w(int h, int[][] m, int[] w, int[] c) {
		if (m[h].length == 0) {
			return;
		}
		HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> weihts = new HashMap<Integer, Integer>();
		for (int child : m[h]) {
			w(child, m, w, c);
			colors.put(c[child], colors.getOrDefault(c[child], 0) + 1);
			weihts.put(c[child], weihts.getOrDefault(c[child], 0) + w[c[child]]);
		}
		for (int color : colors.keySet()) {
			w[h] = Math.max(w[h], colors.get(color) + weihts.get(color));
		}
	}

}
