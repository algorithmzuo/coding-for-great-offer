package class33;

import java.util.ArrayList;

public class Problem_0210_CourseScheduleII {

	public static int[] findOrder(int numCourses, int[][] prerequisites) {
		ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
		for (int i = 0; i < numCourses; i++) {
			graph.add(new ArrayList<>());
		}
		int[] indegree = new int[numCourses];
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			graph.get(from).add(to);
			indegree[to]++;
		}
		int[] zeroQueue = new int[numCourses];
		int l = 0;
		int r = 0;
		for (int i = 0; i < numCourses; i++) {
			if (indegree[i] == 0) {
				zeroQueue[r++] = i;
			}
		}
		int count = 0;
		while (l < r) {
			int cur = zeroQueue[l++];
			count++;
			for (int next : graph.get(cur)) {
				if (--indegree[next] == 0) {
					zeroQueue[r++] = next;
				}
			}
		}
		return count == numCourses ? zeroQueue : new int[0];
	}

}
