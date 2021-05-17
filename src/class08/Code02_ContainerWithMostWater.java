package class08;

public class Code02_ContainerWithMostWater {

	public static int maxArea1(int[] h) {
		int max = 0;
		int N = h.length;
		for (int i = 0; i < N; i++) { // h[i]
			for (int j = i + 1; j < N; j++) { // h[j]
				max = Math.max(max, Math.min(h[i], h[j]) * (j - i));
			}
		}
		return max;
	}

	public static int maxArea2(int[] h) {
		int max = 0;
		int l = 0;
		int r = h.length - 1;
		while (l < r) {
			max = Math.max(max, Math.min(h[l], h[r]) * (r - l));
			if (h[l] > h[r]) {
				r--;
			} else {
				l++;
			}
		}
		return max;
	}

}
