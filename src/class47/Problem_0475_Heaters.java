package class47;

import java.util.Arrays;

public class Problem_0475_Heaters {

	// 比如地点是7, 9, 14
	// 供暖点的位置: 1 3 4 5 13 15 17
	// 先看地点7
	// 由1供暖，半径是6
	// 由3供暖，半径是4
	// 由4供暖，半径是3
	// 由5供暖，半径是2
	// 由13供暖，半径是6
	// 由此可知，地点7应该由供暖点5来供暖，半径是2
	// 再看地点9
	// 供暖点不回退
	// 由5供暖，半径是4
	// 由13供暖，半径是4
	// 由15供暖，半径是6
	// 由此可知，地点9应该由供暖点13来供暖，半径是4
	// 为什么是13而不是5？因为接下来的地点都会更靠右，所以半径一样的时候，就应该选更右的供暖点
	// 再看地点14
	// 供暖点不回退
	// 由13供暖，半径是1
	// 由15供暖，半径是1
	// 由17供暖，半径是3
	// 由此可知，地点14应该由供暖点15来供暖，半径是1
	// 以此类推
	public static int findRadius(int[] houses, int[] heaters) {
		Arrays.sort(houses);
		Arrays.sort(heaters);
		int ans = 0;
		// 时间复杂度O(N)
		// i是地点，j是供暖点
		for (int i = 0, j = 0; i < houses.length; i++) {
			while (!best(houses, heaters, i, j)) {
				j++;
			}
			ans = Math.max(ans, Math.abs(heaters[j] - houses[i]));
		}
		return ans;
	}

	// 这个函数含义：
	// 当前的地点houses[i]由heaters[j]来供暖是最优的吗？
	// 当前的地点houses[i]由heaters[j]来供暖，产生的半径是a
	// 当前的地点houses[i]由heaters[j + 1]来供暖，产生的半径是b
	// 如果a < b, 说明是最优，供暖不应该跳下一个位置
	// 如果a >= b, 说明不是最优，应该跳下一个位置
	public static boolean best(int[] houses, int[] heaters, int i, int j) {
		return j == heaters.length - 1 
				|| Math.abs(heaters[j] - houses[i]) < Math.abs(heaters[j + 1] - houses[i]);
	}

	// 下面这个方法不对，你能找出原因嘛？^_^
	public static int findRadius2(int[] houses, int[] heaters) {
		Arrays.sort(houses);
		Arrays.sort(heaters);
		int ans = 0;
		// 时间复杂度O(N)
		// i是地点，j是供暖点
		for (int i = 0, j = 0; i < houses.length; i++) {
			while (!best2(houses, heaters, i, j)) {
				j++;
			}
			ans = Math.max(ans, Math.abs(heaters[j] - houses[i]));
		}
		return ans;
	}

	public static boolean best2(int[] houses, int[] heaters, int i, int j) {
		return j == heaters.length - 1 || Math.abs(heaters[j] - houses[i]) <= Math.abs(heaters[j + 1] - houses[i]);
	}

	// 为了测试
	public static int[] randomArray(int len, int v) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * v) + 1;
		}
		return arr;
	}

	// 为了测试
	public static void main(String[] args) {
		int len = 5;
		int v = 10;
		int testTime = 10000;
		for (int i = 0; i < testTime; i++) {
			int[] a = randomArray(len, v);
			int[] b = randomArray(len, v);
			int ans1 = findRadius(a, b);
			int ans2 = findRadius2(a, b);
			if (ans1 != ans2) {
				System.out.println("A : ");
				for (int num : a) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println("B : ");
				for (int num : b) {
					System.out.print(num + " ");
				}
				System.out.println();
				System.out.println(ans1);
				System.out.println(ans2);
				break;
			}
		}
	}

}
