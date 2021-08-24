package class36;

import java.util.ArrayList;
import java.util.HashMap;

// 来自三七互娱
// Leetcode原题 : https://leetcode.com/problems/bus-routes/
public class Code12_BusRoutes {

	public static int numBusesToDestination(int[][] routes, int source, int target) {
		if (source == target) {
			return 0;
		}
		int n = routes.length;
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < routes[i].length; j++) {
				if (!map.containsKey(routes[i][j])) {
					map.put(routes[i][j], new ArrayList<>());
				}
				map.get(routes[i][j]).add(i);
			}
		}
		int[] queue = new int[n];
		int l = 0;
		int r = 0;
		boolean[] set = new boolean[n];
		for (int route : map.get(source)) {
			queue[r++] = route;
			set[route] = true;
		}
		int len = 1;
		while (l != r) {
			int size = r - l;
			for (int i = 0; i < size; i++) {
				int curRoute = queue[l++];
				for (int station : routes[curRoute]) {
					if (station == target) {
						return len;
					}
					if (map.containsKey(station)) {
						for (int nextRoute : map.get(station)) {
							if (!set[nextRoute]) {
								set[nextRoute] = true;
								queue[r++] = nextRoute;
							}
						}
						map.remove(station);
					}
				}
			}
			len++;
		}
		return -1;
	}

}
