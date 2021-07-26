package class27;

import java.util.Arrays;

public class Code01_PickBands {

	// 每一个项目都有三个数，[a,b,c]表示这个项目a和b乐队参演，花费为c
	// 每一个乐队可能在多个项目里都出现了，但是只能挑一次
	// nums是可以挑选的项目数量，所以一定会有nums*2只乐队被挑选出来
	// 乐队的全部数量一定是nums*2，且标号一定是0 ~ nums*2-1
	// 返回一共挑nums轮(也就意味着一定请到所有的乐队)，最少花费是多少？
	public static int minCost(int[][] programs, int nums) {
		if (nums == 0 || programs == null || programs.length == 0) {
			return 0;
		}
		int size = clean(programs);
		int[] map1 = init(1 << (nums << 1));
		int[] map2 = null;
		if ((nums & 1) == 0) {
			f(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = map1;
		} else {
			f(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = init(1 << (nums << 1));
			f(programs, size, 0, 0, 0, nums - (nums >> 1), map2);
		}
		int mask = (1 << (nums << 1)) - 1;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < map1.length; i++) {
			if (map1[i] != Integer.MAX_VALUE && map2[mask & (~i)] != Integer.MAX_VALUE) {
				ans = Math.min(ans, map1[i] + map2[mask & (~i)]);
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	public static int clean(int[][] programs) {
		int x = 0;
		int y = 0;
		for (int[] p : programs) {
			x = Math.min(p[0], p[1]);
			y = Math.max(p[0], p[1]);
			p[0] = x;
			p[1] = y;
		}
		Arrays.sort(programs, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] != b[1] ? (a[1] - b[1]) : (a[2] - b[2])));
		x = programs[0][0];
		y = programs[0][1];
		int n = programs.length;
		for (int i = 1; i < n; i++) {
			if (programs[i][0] == x && programs[i][1] == y) {
				programs[i] = null;
			} else {
				x = programs[i][0];
				y = programs[i][1];
			}
		}
		int size = 1;
		for (int i = 1; i < n; i++) {
			if (programs[i] != null) {
				programs[size++] = programs[i];
			}
		}
		return size;
	}

	public static int[] init(int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = Integer.MAX_VALUE;
		}
		return arr;
	}

	public static void f(int[][] programs, int size, int index, int status, int cost, int rest, int[] map) {
		if (rest == 0) {
			map[status] = Math.min(map[status], cost);
		} else {
			if (index != size) {
				f(programs, size, index + 1, status, cost, rest, map);
				int pick = 0 | (1 << programs[index][0]) | (1 << programs[index][1]);
				if ((pick & status) == 0) {
					f(programs, size, index + 1, status | pick, cost + programs[index][2], rest - 1, map);
				}
			}
		}
	}

	// 为了测试
	public static int right(int[][] programs, int nums) {
		min = Integer.MAX_VALUE;
		r(programs, 0, nums, 0, 0);
		return min == Integer.MAX_VALUE ? -1 : min;
	}

	public static int min = Integer.MAX_VALUE;

	public static void r(int[][] programs, int index, int rest, int pick, int cost) {
		if (rest == 0) {
			min = Math.min(min, cost);
		} else {
			if (index < programs.length) {
				r(programs, index + 1, rest, pick, cost);
				int cur = (1 << programs[index][0]) | (1 << programs[index][1]);
				if ((pick & cur) == 0) {
					r(programs, index + 1, rest - 1, pick | cur, cost + programs[index][2]);
				}
			}
		}
	}

	// 为了测试
	public static int[][] randomPrograms(int N, int V) {
		int nums = N << 1;
		int n = nums * (nums - 1);
		int[][] programs = new int[n][3];
		for (int i = 0; i < n; i++) {
			int a = (int) (Math.random() * nums);
			int b = 0;
			do {
				b = (int) (Math.random() * nums);
			} while (b == a);
			programs[i][0] = a;
			programs[i][1] = b;
			programs[i][2] = (int) (Math.random() * V) + 1;
		}
		return programs;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 4;
		int V = 100;
		int T = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < T; i++) {
			int nums = (int) (Math.random() * N) + 1;
			int[][] programs = randomPrograms(nums, V);
			int ans1 = right(programs, nums);
			int ans2 = minCost(programs, nums);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");

		long start;
		long end;
		int[][] programs;

		programs = randomPrograms(7, V);
		start = System.currentTimeMillis();
		right(programs, 7);
		end = System.currentTimeMillis();
		System.out.println("right方法，在nums=7时候的运行时间(毫秒) : " + (end - start));

		programs = randomPrograms(10, V);
		start = System.currentTimeMillis();
		minCost(programs, 10);
		end = System.currentTimeMillis();
		System.out.println("minCost方法，在nums=10时候的运行时间(毫秒) : " + (end - start));

	}

}
