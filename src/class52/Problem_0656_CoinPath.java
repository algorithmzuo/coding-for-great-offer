package class52;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Problem_0656_CoinPath {

	public static List<Integer> cheapestJump(int[] arr, int jump) {
		int n = arr.length;
		int[] best = new int[n];
		int[] last = new int[n];
		int[] size = new int[n];
		Arrays.fill(best, Integer.MAX_VALUE);
		Arrays.fill(last, -1);
		best[0] = 0;
		for (int i = 0; i < n; i++) {
			if (arr[i] != -1) {
				for (int j = Math.max(0, i - jump); j < i; j++) {
					if (arr[j] != -1) {
						int cur = best[j] + arr[i];
						if (cur < best[i] || (cur == best[i] && size[i] - 1 < size[j])) {
							best[i] = cur;
							last[i] = j;
							size[i] = size[j] + 1;
						}
					}
				}
			}
		}
		List<Integer> path = new LinkedList<>();
		for (int cur = n - 1; cur >= 0; cur = last[cur]) {
			path.add(0, cur + 1);
		}
		return path.get(0) != 1 ? new LinkedList<>() : path;
	}

}
