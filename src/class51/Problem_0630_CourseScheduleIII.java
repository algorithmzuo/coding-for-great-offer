package class51;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Problem_0630_CourseScheduleIII {

	public static int scheduleCourse(int[][] courses) {
		Arrays.sort(courses, (a, b) -> a[1] - b[1]);
		// 持续时间的大根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		int time = 0;
		for (int[] c : courses) {
			if (time + c[0] <= c[1]) {
				heap.add(c[0]);
				time += c[0];
			} else {
				if (!heap.isEmpty() && heap.peek() > c[0]) {
					heap.add(c[0]);
					time += c[0] - heap.poll();
				}
			}
		}
		return heap.size();
	}

}
