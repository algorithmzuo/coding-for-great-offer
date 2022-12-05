package class29;

// 本题原本的设定是给定的有序数组中没有重复的数字
// 但是在课上讲的时候，我们按照可以有重复数字来讲的
// 而这正是Leetcode 81题的设定
// 也就是说，这个版本是可以通过Leetcode 33、Leetcode 81两个题目的
// 而且都是最优解
// Leetcode 33 : https://leetcode.cn/problems/search-in-rotated-sorted-array
// Leetcode 81 : https://leetcode.cn/problems/search-in-rotated-sorted-array-ii
// 注意在提交leetcode 81题时，请把code中返回下标的地方，改成返回boolean类型的返回值
public class Problem_0033_SearchInRotatedSortedArray {

	// arr，原本是有序数组，旋转过，而且左部分长度不知道
	// 找num
	// num所在的位置返回
	public static int search(int[] arr, int num) {
		int L = 0;
		int R = arr.length - 1;
		int M = 0;
		while (L <= R) {
			// M = L + ((R - L) >> 1)
			M = (L + R) / 2;
			if (arr[M] == num) {
				return M;
			}
			// arr[M] != num
			// [L] == [M] == [R] != num 无法二分
			if (arr[L] == arr[M] && arr[M] == arr[R]) {
				while (L != M && arr[L] == arr[M]) {
					L++;
				}
				// 1) L == M L...M 一路都相等
				// 2) 从L到M终于找到了一个不等的位置
				if (L == M) { // L...M 一路都相等
					L = M + 1;
					continue;
				}
			}
			// ...
			// arr[M] != num
			// [L] [M] [R] 不都一样的情况, 如何二分的逻辑
			if (arr[L] != arr[M]) {
				if (arr[M] > arr[L]) { // L...M 一定有序 
					if (num >= arr[L] && num < arr[M]) { //  3  [L] == 1    [M]   = 5   L...M - 1
						R = M - 1;
					} else { // 9    [L] == 2    [M]   =  7   M... R
						L = M + 1;
					}
				} else { // [L] > [M]    L....M  存在断点
					if (num > arr[M] && num <= arr[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				}
			} else { /// [L] [M] [R] 不都一样，  [L] === [M] -> [M]!=[R]
				if (arr[M] < arr[R]) {
					if (num > arr[M] && num <= arr[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				} else {
					if (num >= arr[L] && num < arr[M]) {
						R = M - 1;
					} else {
						L = M + 1;
					}
				}
			}
		}
		return -1;
	}

}
