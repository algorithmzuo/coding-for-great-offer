package class50;

public class Problem_0644_MaximumAverageSubarrayII {

	// 时间复杂度O(N * log(MaxValue))
	public double findMaxAverage1(int[] nums, int k) {
		int n = nums.length;
		if (n == 1) {
			return (double) nums[0];
		}
		double L = 0;
		double R = 0;
		double[] arr = new double[n];
		for (int i = 0; i < n; i++) {
			arr[i] = nums[i];
			L = Math.min(L, arr[i]);
			R = Math.max(R, arr[i]);
		}
		double E = 0.00001;
		double M = 0;
		double ans = 0;
		while (L + E <= R) {
			M = (L + R) / 2;
			if (f(arr, M) >= k) {
				ans = M;
				L = M;
			} else {
				R = M;
			}
		}
		return ans;
	}

	// 返回arr中平均值大于等于v的最长子数组长度
	// 体系学习班, 40节，第4题
	public static int f(double[] arr, double v) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] -= v;
		}
		int n = arr.length;
		double[] sums = new double[n];
		int[] ends = new int[n];
		sums[n - 1] = arr[n - 1];
		ends[n - 1] = n - 1;
		for (int i = n - 2; i >= 0; i--) {
			if (sums[i + 1] >= 0) {
				sums[i] = arr[i] + sums[i + 1];
				ends[i] = ends[i + 1];
			} else {
				sums[i] = arr[i];
				ends[i] = i;
			}
		}
		int end = 0;
		double sum = 0;
		int ans = 0;
		for (int i = 0; i < n; i++) {
			while (end < n && sum + sums[end] >= 0) {
				sum += sums[end];
				end = ends[end] + 1;
			}
			ans = Math.max(ans, end - i);
			if (end > i) {
				sum -= arr[i];
			} else {
				end = i + 1;
			}
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] += v;
		}
		return ans;
	}

	// 时间复杂度O(N)
	// 论文名字 : An Optimal Algorithm for the Maximum-Density Segment Problem
	// 论文链接 : https://arxiv.org/pdf/cs/0311020.pdf
	// 原论文中将这一问题泛化了
	public double findMaxAverage2(int[] nums, int k) {
		int n = nums.length;
		int[] s = new int[n];
		s[0] = nums[0];
		for (int i = 1; i < n; i++) {
			s[i] = s[i - 1] + nums[i];
		}
		int[] q = new int[n];
		int l = 0;
		int r = 0;
		double ans = (double) Integer.MIN_VALUE;
		for (int j = k - 1; j < n; j++) {
			while (r - l >= 2 && density(s, q[l], q[r - 1] - 1) >= density(s, q[l], j - k)) {
				r--;
			}
			q[r++] = j - k + 1;
			while (r - l >= 2 && density(s, q[l], q[l + 1] - 1) <= density(s, q[l], j)) {
				l++;
			}
			ans = Math.max(ans, density(s, q[l], j));
		}
		return ans;
	}

	public double density(int[] s, int l, int r) {
		return (l == 0 ? ((double) s[r]) : ((double) s[r] - s[l - 1])) / (r - l + 1);
	}

}
