package class45;

import java.util.Arrays;

// 来自京东笔试
// 小明手中有n块积木，并且小明知道每块积木的重量。现在小明希望将这些积木堆起来
// 要求是任意一块积木如果想堆在另一块积木上面，那么要求：
// 1) 上面的积木重量不能小于下面的积木重量
// 2) 上面积木的重量减去下面积木的重量不能超过x
// 3) 每堆中最下面的积木没有重量要求
// 现在小明有一个机会，除了这n块积木，还可以获得k块任意重量的积木。
// 小明希望将积木堆在一起，同时希望积木堆的数量越少越好，你能帮他找到最好的方案么？
// 输入描述:
// 第一行三个整数n,k,x，1<=n<=200000，0<=x,k<=1000000000
// 第二行n个整数，表示积木的重量，任意整数范围都在[1,1000000000]
// 样例输出：
// 13 1 38
// 20 20 80 70 70 70 420 5 1 5 1 60 90
// 输出：2
// 解释：
// 两堆分别是
// 1 1 5 5 20 20 x 60 70 70 70 80 90
// 420
// 其中x是一个任意重量的积木，夹在20和60之间可以让积木继续往上搭
public class Code01_SplitBuildingBlock {

	public static int minSplit(int[] arr, int k, int x) {
		Arrays.sort(arr);
		int n = arr.length;
		int[] needs = new int[n];
		int size = 0;
		int splits = 1;
		for (int i = 1; i < n; i++) {
			if (arr[i] - arr[i - 1] > x) {
				needs[size++] = arr[i] - arr[i - 1];
				splits++;
			}
		}
		if (splits == 1 || x == 0) {
			return splits;
		}
		Arrays.sort(needs, 0, size);
		for (int i = 0; i < size; i++) {
			int need = (needs[i] - 1) / x;
			if (k >= need) {
				splits--;
				k -= need;
			} else {
				break;
			}
		}
		return splits;
	}

}
