package class49;

import java.util.Arrays;

public class Problem_0587_ErectTheFence {

	public static int[][] outerTrees(int[][] points) {
		int n = points.length;
		int s = 0;
		int[][] stack = new int[n << 1][];
		Arrays.sort(points, (a, b) -> a[0] != b[0] ? a[0] - b[0] : a[1] - b[1]);
		for (int i = 0; i < n; i++) {
			while (s > 1 && cross(stack[s - 2], stack[s - 1], points[i]) > 0) {
				s--;
			}
			stack[s++] = points[i];
		}
		for (int i = n - 2; i >= 0; i--) {
			while (s > 1 && cross(stack[s - 2], stack[s - 1], points[i]) > 0) {
				s--;
			}
			stack[s++] = points[i];
		}
		// 去重返回
		Arrays.sort(stack, 0, s, (a, b) -> b[0] == a[0] ? b[1] - a[1] : b[0] - a[0]);
		n = 1;
		for (int i = 1; i < s; i++) {
			if (stack[i][0] != stack[i - 1][0] || stack[i][1] != stack[i - 1][1]) {
				stack[n++] = stack[i];
			}
		}
		return Arrays.copyOf(stack, n);
	}

	// 叉乘
	public static int cross(int[] a, int[] b, int[] c) {
		return (b[1] - a[1]) * (c[0] - b[0]) - (b[0] - a[0]) * (c[1] - b[1]);
	}

}
