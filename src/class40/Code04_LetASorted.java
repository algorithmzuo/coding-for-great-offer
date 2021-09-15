package class40;

// 给定两个数组A和B，长度都是N
// A[i]不可以在A中和其他数交换，只可以选择和B[i]交换(0<=i<n)
// 你的目的是让A有序，返回你能不能做到
public class Code04_LetASorted {

	public static boolean letASorted(int[] A, int[] B) {
		return process(A, B, 0, Integer.MIN_VALUE);
	}

	public static boolean process(int[] A, int[] B, int i, int lastA) {
		if (i == A.length) {
			return true;
		}
		if (A[i] >= lastA && process(A, B, i, A[i])) {
			return true;
		}
		if (B[i] >= lastA && process(A, B, i, B[i])) {
			return true;
		}
		return false;
	}

}
