package code05;

/*
 * 给定一个数组arr，长度为N，arr中的值不是0就是1
 * arr[i]表示第i栈灯的状态，0代表灭灯，1代表亮灯
 * 每一栈灯都有开关，但是按下i号灯的开关，会同时改变i-1、i、i+2栈灯的状态
 * 问题一：
 * 如果N栈灯排成一条直线,请问最少按下多少次开关,能让灯都亮起来
 * 排成一条直线说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关只能影响0和1位置的灯
 * N-1号灯的开关只能影响N-2和N-1位置的灯
 * 
 * 问题二：
 * 如果N栈灯排成一个圈,请问最少按下多少次开关,能让灯都亮起来
 * 排成一个圈说明：
 * i为中间位置时，i号灯的开关能影响i-1、i和i+1
 * 0号灯的开关能影响N-1、0和1位置的灯
 * N-1号灯的开关能影响N-2、N-1和0位置的灯
 * 
 * */
public class Code04_LightProblem {

	// 无环改灯问题的暴力版本
	public static int noLoopRight(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		return f1(arr, 0);
	}

	public static int f1(int[] arr, int i) {
		if (i == arr.length) {
			return valid(arr) ? 0 : Integer.MAX_VALUE;
		}
		int p1 = f1(arr, i + 1);
		change1(arr, i);
		int p2 = f1(arr, i + 1);
		change1(arr, i);
		p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
		return Math.min(p1, p2);
	}

	public static void change1(int[] arr, int i) {
		if (i == 0) {
			arr[0] ^= 1;
			arr[1] ^= 1;
		} else if (i == arr.length - 1) {
			arr[i - 1] ^= 1;
			arr[i] ^= 1;
		} else {
			arr[i - 1] ^= 1;
			arr[i] ^= 1;
			arr[i + 1] ^= 1;
		}
	}

	public static boolean valid(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == 0) {
				return false;
			}
		}
		return true;
	}

	// 无环改灯问题的递归版本
	public static int noLoopMinStep1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		// 不变0位置的状态
		int p1 = process1(arr, 2, arr[0], arr[1]);
		// 改变0位置的状态
		int p2 = process1(arr, 2, arr[0] ^ 1, arr[1] ^ 1);
		if (p2 != Integer.MAX_VALUE) {
			p2++;
		}
		return Math.min(p1, p2);
	}

	public static int process1(int[] arr, int nextIndex, int preStatus, int curStatus) {
		if (nextIndex == arr.length) {
			return preStatus != curStatus ? (Integer.MAX_VALUE) : (curStatus ^ 1);
		}
		// i < arr.length
		if (preStatus == 0) { // 一定要改变
			curStatus ^= 1;
			int cur = arr[nextIndex] ^ 1;
			int next = process1(arr, nextIndex + 1, curStatus, cur);
			return next == Integer.MAX_VALUE ? next : (next + 1);
		} else { // 一定不能改变
			return process1(arr, nextIndex + 1, curStatus, arr[nextIndex]);
		}
	}

	// 无环改灯问题的迭代版本
	public static int noLoopMinStep2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		int p1 = traceNoLoop(arr, arr[0], arr[1]);
		int p2 = traceNoLoop(arr, arr[0] ^ 1, arr[1] ^ 1);
		p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
		return Math.min(p1, p2);
	}

	public static int traceNoLoop(int[] arr, int preStatus, int curStatus) {
		int i = 2;
		int op = 0;
		while (i != arr.length) {
			if (preStatus == 0) {
				op++;
				preStatus = curStatus ^ 1;
				curStatus = arr[i++] ^ 1;
			} else {
				preStatus = curStatus;
				curStatus = arr[i++];
			}
		}
		return (preStatus != curStatus) ? Integer.MAX_VALUE : (op + (curStatus ^ 1));
	}

	// 有环改灯问题的暴力版本
	public static int loopRight(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		return f2(arr, 0);
	}

	public static int f2(int[] arr, int i) {
		if (i == arr.length) {
			return valid(arr) ? 0 : Integer.MAX_VALUE;
		}
		int p1 = f2(arr, i + 1);
		change2(arr, i);
		int p2 = f2(arr, i + 1);
		change2(arr, i);
		p2 = (p2 == Integer.MAX_VALUE) ? p2 : (p2 + 1);
		return Math.min(p1, p2);
	}

	public static void change2(int[] arr, int i) {
		arr[lastIndex(i, arr.length)] ^= 1;
		arr[i] ^= 1;
		arr[nextIndex(i, arr.length)] ^= 1;
	}

	public static int lastIndex(int i, int N) {
		return i == 0 ? (N - 1) : (i - 1);
	}

	public static int nextIndex(int i, int N) {
		return i == N - 1 ? 0 : (i + 1);
	}

	// 有环改灯问题的递归版本
	public static int loopMinStep1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		if (arr.length == 3) {
			return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		// 0不变，1不变
		int p1 = process2(arr, 3, arr[1], arr[2], arr[arr.length - 1], arr[0]);
		// 0改变，1不变
		int p2 = process2(arr, 3, arr[1] ^ 1, arr[2], arr[arr.length - 1] ^ 1, arr[0] ^ 1);
		// 0不变，1改变
		int p3 = process2(arr, 3, arr[1] ^ 1, arr[2] ^ 1, arr[arr.length - 1], arr[0] ^ 1);
		// 0改变，1改变
		int p4 = process2(arr, 3, arr[1], arr[2] ^ 1, arr[arr.length - 1] ^ 1, arr[0]);
		p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
		p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
		p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
		return Math.min(Math.min(p1, p2), Math.min(p3, p4));
	}

	public static int process2(int[] arr, int nextIndex, int preStatus, int curStatus, int endStatus, int firstStatus) {
		if (nextIndex == arr.length) {
			return (endStatus != firstStatus || endStatus != preStatus) ? Integer.MAX_VALUE : (endStatus ^ 1);
		}
		int curStay = (nextIndex == arr.length - 1) ? endStatus : arr[nextIndex];
		int curChange = (nextIndex == arr.length - 1) ? (endStatus ^ 1) : (arr[nextIndex] ^ 1);
		int endChange = (nextIndex == arr.length - 1) ? curChange : endStatus;
		if (preStatus == 0) {
			int next = process2(arr, nextIndex + 1, curStatus ^ 1, curChange, endChange, firstStatus);
			return next == Integer.MAX_VALUE ? next : (next + 1);
		} else {
			return process2(arr, nextIndex + 1, curStatus, curStay, endStatus, firstStatus);
		}
	}

	// 有环改灯问题的迭代版本
	public static int loopMinStep2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		if (arr.length == 1) {
			return arr[0] == 1 ? 0 : 1;
		}
		if (arr.length == 2) {
			return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		if (arr.length == 3) {
			return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
		}
		// 0不变，1不变
		int p1 = traceLoop(arr, arr[1], arr[2], arr[arr.length - 1], arr[0]);
		// 0改变，1不变
		int p2 = traceLoop(arr, arr[1] ^ 1, arr[2], arr[arr.length - 1] ^ 1, arr[0] ^ 1);
		// 0不变，1改变
		int p3 = traceLoop(arr, arr[1] ^ 1, arr[2] ^ 1, arr[arr.length - 1], arr[0] ^ 1);
		// 0改变，1改变
		int p4 = traceLoop(arr, arr[1], arr[2] ^ 1, arr[arr.length - 1] ^ 1, arr[0]);
		p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
		p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
		p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
		return Math.min(Math.min(p1, p2), Math.min(p3, p4));
	}

	public static int traceLoop(int[] arr, int preStatus, int curStatus, int endStatus, int firstStatus) {
		int i = 3;
		int op = 0;
		while (i < arr.length - 1) {
			if (preStatus == 0) {
				op++;
				preStatus = curStatus ^ 1;
				curStatus = (arr[i++] ^ 1);
			} else {
				preStatus = curStatus;
				curStatus = arr[i++];
			}
		}
		if (preStatus == 0) {
			op++;
			preStatus = curStatus ^ 1;
			endStatus ^= 1;
			curStatus = endStatus;
		} else {
			preStatus = curStatus;
			curStatus = endStatus;
		}
		return (endStatus != firstStatus || endStatus != preStatus) ? Integer.MAX_VALUE : (op + (endStatus ^ 1));
	}

	// 生成长度为len的随机数组，值只有0和1两种值
	public static int[] randomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 2);
		}
		return arr;
	}

	public static void main(String[] args) {
		System.out.println("如果没有任何Oops打印，说明所有方法都正确");
		System.out.println("test begin");
		int testTime = 20000;
		int lenMax = 12;
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * lenMax);
			int[] arr = randomArray(len);
			int ans1 = noLoopRight(arr);
			int ans2 = noLoopMinStep1(arr);
			int ans3 = noLoopMinStep2(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("1 Oops!");
			}
		}
		for (int i = 0; i < testTime; i++) {
			int len = (int) (Math.random() * lenMax);
			int[] arr = randomArray(len);
			int ans1 = loopRight(arr);
			int ans2 = loopMinStep1(arr);
			int ans3 = loopMinStep2(arr);
			if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("2 Oops!");
			}
		}
		System.out.println("test end");

		int len = 100000000;
		System.out.println("性能测试");
		System.out.println("数组大小：" + len);
		int[] arr = randomArray(len);
		long start = 0;
		long end = 0;
		start = System.currentTimeMillis();
		noLoopMinStep2(arr);
		end = System.currentTimeMillis();
		System.out.println("noLoopMinStep2 run time: " + (end - start) + "(ms)");

		start = System.currentTimeMillis();
		loopMinStep2(arr);
		end = System.currentTimeMillis();
		System.out.println("loopMinStep2 run time: " + (end - start) + "(ms)");
	}

}
