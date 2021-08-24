package class35;

import java.util.Arrays;

// 来自小红书
// [0,4,7] ： 0表示这里石头没有颜色，如果变红代价是4，如果变蓝代价是7
// [1,X,X] ： 1表示这里石头已经是红，而且不能改颜色，所以后两个数X无意义
// [2,X,X] ： 2表示这里石头已经是蓝，而且不能改颜色，所以后两个数X无意义
// 颜色只可能是0、1、2，代价一定>=0
// 给你一批这样的小数组，要求最后必须所有石头都有颜色，且红色和蓝色一样多，返回最小代价
// 如果怎么都无法做到所有石头都有颜色、且红色和蓝色一样多，返回-1
public class Code02_MagicStone {

	public static int minCost(int[][] stones) {
		int n = stones.length;
		if ((n & 1) != 0) {
			return -1;
		}
		Arrays.sort(stones, (a, b) -> a[0] == 0 && b[0] == 0 ? (b[1] - b[2] - a[1] + a[2]) : (a[0] - b[0]));
		int zero = 0;
		int red = 0;
		int blue = 0;
		int cost = 0;
		for (int i = 0; i < n; i++) {
			if (stones[i][0] == 0) {
				zero++;
				cost += stones[i][1];
			} else if (stones[i][0] == 1) {
				red++;
			} else {
				blue++;
			}
		}
		if (red > (n >> 1) || blue > (n >> 1)) {
			return -1;
		}
		blue = zero - ((n >> 1) - red);
		for (int i = 0; i < blue; i++) {
			cost += stones[i][2] - stones[i][1];
		}
		return cost;
	}

	public static void main(String[] args) {
		int[][] stones = { { 1, 5, 3 }, { 2, 7, 9 }, { 0, 6, 4 }, { 0, 7, 9 }, { 0, 2, 1 }, { 0, 5, 9 } };
		System.out.println(minCost(stones));
	}

}
