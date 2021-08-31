package class36;

import java.util.ArrayList;
import java.util.HashMap;

// 来自三七互娱
// Leetcode原题 : https://leetcode.com/problems/bus-routes/
public class Code12_BusRoutes {

	// 0 : [1,3,7,0]
	// 1 : [7,9,6,2]
	// ....
	// 返回：返回换乘几次+1 -> 返回一共坐了多少条线的公交。
	public static int numBusesToDestination(int[][] routes, int source, int target) {
		if (source == target) {
			return 0;
		}
		int n = routes.length;
		// key : 车站
		// value : list -> 该车站拥有哪些线路！
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < routes[i].length; j++) {
				if (!map.containsKey(routes[i][j])) {
					map.put(routes[i][j], new ArrayList<>());
				}
				map.get(routes[i][j]).add(i);
			}
		}
		ArrayList<Integer> queue = new ArrayList<>();
		boolean[] set = new boolean[n];
		for (int route : map.get(source)) {
			queue.add(route);
			set[route] = true;
		}
		int len = 1;
		while (!queue.isEmpty()) {
			ArrayList<Integer> nextLevel = new ArrayList<>();
			for (int route : queue) {
				int[] bus = routes[route];
				for (int station : bus) {
					if (station == target) {
						return len;
					}
					for (int nextRoute : map.get(station)) {
						if (!set[nextRoute]) {
							nextLevel.add(nextRoute);
							set[nextRoute] = true;
						}
					}
				}
			}
			queue = nextLevel;
			len++;
		}
		return -1;
	}

}
