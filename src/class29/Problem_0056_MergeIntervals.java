package class29;

import java.util.Arrays;

public class Problem_0056_MergeIntervals {

	public static int[][] merge(int[][] intervals) {
		if (intervals.length == 0) {
			return new int[0][0];
		}
		Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
		int s = intervals[0][0];
		int e = intervals[0][1];
		int size = 0;
		for (int i = 1; i < intervals.length; i++) {
			if (intervals[i][0] > e) {
				intervals[size][0] = s;
				intervals[size++][1] = e;
				s = intervals[i][0];
				e = intervals[i][1];
			} else {
				e = Math.max(e, intervals[i][1]);
			}
		}
		intervals[size][0] = s;
		intervals[size++][1] = e;
		return Arrays.copyOf(intervals, size);
	}

}
