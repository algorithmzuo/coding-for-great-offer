package class27;

import java.util.Arrays;
import java.util.HashMap;

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
			// nums = 8 , 4
			f(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = map1;
		} else {
			// nums == 7 4 -> map1 3 -> map2
			f(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = init(1 << (nums << 1));
			f(programs, size, 0, 0, 0, nums - (nums >> 1), map2);
		}
		// 16 mask : 0..00 1111.1111(16个)
		// 12 mask : 0..00 1111.1111(12个)
		int mask = (1 << (nums << 1)) - 1;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < map1.length; i++) {
			if (map1[i] != Integer.MAX_VALUE && map2[mask & (~i)] != Integer.MAX_VALUE) {
				ans = Math.min(ans, map1[i] + map2[mask & (~i)]);
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}

	// [
	// [9, 1, 100]
	// [2, 9 , 50]
	// ...
	// ]
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
		// (0, 1, ? )
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
		// programs[0...size-1]
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

//	// 如果nums，2 * nums 只乐队
//	// (1 << (nums << 1)) - 1
//	// programs 洗数据 size
//	// nums = 8   16只乐队
//	
//	// process(programs, size, (1 << (nums << 1)) - 1, 0, 4, 0, 0)
//	
//	public static int minCost = Integer.MAX_VALUE;
//	
//	public static int[] map = new int[1 << 16]; // map初始化全变成系统最大
//	
//	
//
//	public static void process(int[][] programs, int size, int index, int rest, int pick, int cost) {
//		if (rest == 0) {
//			
//			map[pick] = Math.min(map[pick], cost);
//			
//		} else { // 还有项目可挑
//			if (index != size) {
//				// 不考虑当前的项目！programs[index];
//				process(programs, size, index + 1, rest, pick, cost);
//				// 考虑当前的项目！programs[index];
//				int x = programs[index][0];
//				int y = programs[index][1];
//				int cur = (1 << x) | (1 << y);
//				if ((pick & cur) == 0) { // 终于可以考虑了！
//					process(programs, size, index + 1, rest - 1, pick | cur, cost + programs[index][2]);
//				}
//			}
//		}
//	}
//	
//	
//	public static void zuo(int[] arr, int index, int rest) {
//		if(rest == 0) {
//			停止
//		}
//		if(index != arr.length) {
//			zuo(arr, index + 1, rest);
//			zuo(arr, index + 1, rest - 1);
//		}
//	}

	// 同学提供的解，很不错
	// 不做要求
	public static HashMap<Integer, Integer> cache = new HashMap<>();

	public static int minCost2(int[][] programs, int nums) {
		// 调整成 programs[i][0] < programs[i][1]
		for (int i = 0; i < programs.length; i++) {
			if (programs[i][0] > programs[i][1]) {
				int t = programs[i][0];
				programs[i][0] = programs[i][1];
				programs[i][1] = t;
			}
		}
		// 排序
		Arrays.sort(programs,
				((o1, o2) -> o1[0] - o2[0] != 0 ? o1[0] - o2[0] : o1[1] - o2[1] != 0 ? o1[1] - o2[1] : o1[2] - o2[2]));
		cache.clear();
		return minCost2(programs, 0, nums, 0);
	}

	// 返回 [index, 最后], 选择 nums 次, 的最小花费, 已经选择过的队记录在 status 中
	// 如果无法满足题意, 返回 -1
	// 看似有三个参数, 其实 nums 是可以根据 status 算出来的, 是冗余的
	public static int minCost2(int[][] programs, int index, int nums, int status) {
		if (nums == 0) {
			return 0;
		} else if (index == programs.length) {
			return -1;
		}

		Integer key = status * programs.length + index;
		Integer ans = cache.get(key);
		if (ans != null) {
			return ans;
		} else {
			ans = Integer.MAX_VALUE;
		}

		// 对于 [index, next-1] 范围的项目, programs[ ][0] 是一样的, 假设都为 乐队x
		int next = index + 1;
		while (next < programs.length && programs[index][0] == programs[next][0]) {
			next++;
		}
		if ((status & (1 << programs[index][0])) != 0) {
			return minCost2(programs, next, nums, status);
		}
		status |= 1 << programs[index][0];
		for (int i = index; i < next; i++) {
			// 一个项目的两个乐队相同, 只取最优的
			if (i - 1 >= index && programs[i][1] == programs[i - 1][1]) {
				continue;
			}
			if ((status & (1 << programs[i][1])) == 0) {
				// 选择当前项目, 并跳到 next
				int t = minCost2(programs, next, nums - 1, status | 1 << programs[i][1]);
				if (t != -1) {
					ans = Math.min(ans, t + programs[i][2]);
				}
			}
		}
		// 算到这里, 如果 ans == Integer.MAX_VALUE, 由于后续过程 乐队x 不会再出现, 返回-1
		ans = ans == Integer.MAX_VALUE ? -1 : ans;
		cache.put(key, ans);
		return ans;
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
	public static int[][] copyPrograms(int[][] programs) {
		int n = programs.length;
		int[][] ans = new int[n][3];
		for (int i = 0; i < n; i++) {
			ans[i][0] = programs[i][0];
			ans[i][1] = programs[i][1];
			ans[i][2] = programs[i][2];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 4;
		int V = 100;
		int T = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < T; i++) {
			int nums = (int) (Math.random() * N) + 1;
			int[][] programs1 = randomPrograms(nums, V);
			int[][] programs2 = copyPrograms(programs1);
			int[][] programs3 = copyPrograms(programs1);
			int ans1 = right(programs1, nums);
			int ans2 = minCost(programs2, nums);
			int ans3 = minCost2(programs3, nums);
			if (ans1 != ans2 || ans1 != ans3) {
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

		programs = randomPrograms(10, V);
		start = System.currentTimeMillis();
		minCost2(programs, 10);
		end = System.currentTimeMillis();
		System.out.println("minCost2方法，在nums=10时候的运行时间(毫秒) : " + (end - start));
	}

}
