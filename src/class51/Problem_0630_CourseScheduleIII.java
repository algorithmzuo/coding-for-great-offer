package class51;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Problem_0630_CourseScheduleIII {

	public static int scheduleCourse(int[][] courses) {
		// courses[i]  = {花费，截止}
		Arrays.sort(courses, (a, b) -> a[1] - b[1]);
		// 花费时间的大根堆
		PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
		// 时间点
		int time = 0;
		for (int[] c : courses) {
			// 
			if (time + c[0] <= c[1]) { // 当前时间 + 花费 <= 截止时间的
				heap.add(c[0]);
				time += c[0];
			} else { // 当前时间 + 花费 > 截止时间的, 只有淘汰掉某课，当前的课才能进来！
				// 
				
				
				if (!heap.isEmpty() && heap.peek() > c[0]) {
//					time -= heap.poll();
//					heap.add(c[0]);
//					time += c[0];
					heap.add(c[0]);
					time += c[0] - heap.poll();
				}
			}
		}
		return heap.size();
	}

}
