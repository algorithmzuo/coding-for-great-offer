package class40;

// 给定两个数组A和B，长度都是N
// A[i]不可以在A中和其他数交换，只可以选择和B[i]交换(0<=i<n)
// 你的目的是让A有序，返回你能不能做到
public class Code04_LetASorted {

	public static boolean letASorted1(int[] A, int[] B) {
		return process1(A, B, 0, Integer.MIN_VALUE);
	}

	// 当前推进到了i位置，对于A和B都是i位置
	// A[i]前一个数字，lastA
	// 能否通过题意中的操作，A[i] B[i] 让A整体有序
	public static boolean process1(int[] A, int[] B, int i, int lastA) {
		if (i == A.length) {
			return true;
		}
		// 第一种选择 : A[i]不和B[i]交换
		if (A[i] >= lastA && process1(A, B, i + 1, A[i])) {
			return true;
		}
		// 第二种选择 : A[i]和B[i]交换
		if (B[i] >= lastA && process1(A, B, i + 1, B[i])) {
			return true;
		}
		return false;
	}

	public static boolean letASorted2(int[] A, int[] B) {
		return process2(A, B, 0, true);
	}

	// 当前推进到了i位置，对于A和B都是i位置
	// A[i]前一个数字是否来自A ：
	// 如果来自A，fromA = true；如果来自B，fromA = false；
	// 能否通过题意中的操作，A[i] B[i] 让A整体有序
	// 好处：可变参数成了int + boolean，时间复杂度可以做到O(N)
	public static boolean process2(int[] A, int[] B, int i, boolean fromA) {
		if (i == A.length) {
			return true;
		}
		if (i == 0 || (A[i] >= (fromA ? A[i - 1] : B[i - 1])) && process2(A, B, i + 1, true)) {
			return true;
		}
		if (i == 0 || (B[i] >= (fromA ? A[i - 1] : B[i - 1])) && process2(A, B, i + 1, false)) {
			return true;
		}
		return false;
	}

	// 也可以彻底的贪心：就让A此时的值尽量小！也是可以的。时间复杂度O(N)

}
