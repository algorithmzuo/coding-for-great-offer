package class53;

// 来自真实面试，同学给我的问题
// 限制：0 <= start <= end，0 <= target <= 64
// [start,end]范围上的数字，有多少数字二进制中1的个数等于target
public class Code06_StartToEndBinaryOneTarget {

	// 暴力方法
	// 为了验证
	public static long nums1(long start, long end, int target) {
		if (start < 0 || end < 0 || start > end || target < 0) {
			return -1;
		}
		long ans = 0;
		for (long i = start; i <= end; i++) {
			if (bitOne(i) == target) {
				ans++;
			}
		}
		return ans;
	}

	public static int bitOne(long num) {
		int bits = 0;
		for (int i = 63; i >= 0; i--) {
			if ((num & (1L << i)) != 0) {
				bits++;
			}
		}
		return bits;
	}

	// 正式方法
	public static long nums2(long start, long end, int target) {
		if (start < 0 || end < 0 || start > end || target < 0) {
			return -1;
		}
		if (start == 0 && end == 0) {
			return target == 0 ? 1 : 0;
		}
		int ehigh = 62;
		while ((end & (1L << ehigh)) == 0) {
			ehigh--;
		}
		if (start == 0) {
			return process(ehigh, 0, target, end);
		} else {
			start--;
			int shigh = 62;
			while (shigh >= 0 && (start & (1L << shigh)) == 0) {
				shigh--;
			}
			return process(ehigh, 0, target, end) - process(shigh, 0, target, start);
		}
	}

	// 如果num最高位的1在i位，也就是说num[i...0]才有意义，比i再高的位置都是0
	// 那么从第i位开始做决定，依次往低位进行决定
	// index : 当前来到哪一位做决定，index在i~0之间，从高到低
	// less : 之间做的决定是不是已经比num小了
	// rest : 还剩几个1需要出现
	// long process(index, less, rest, num)含义 :
	// [i...index+1]上面已经做过决定了，接下来要在[index...0]上面做决定
	// 在[i...index+1]上面的决定是不是比num[i...index+1]小，
	// 如果是，less = 1
	// 如果还没有，一定说明之前的决定==num[i...index+1]，此时less = 0
	// 在[index...0]上面做决定的过程中一定要出现rest个1
	// 返回[index...0]上，有多少合法的决定
	// 这个方法可以改动态规划，因为：index范围62~0, less范围0或者1，rest范围0~target
	// 自己改动态规划吧
	// 只有index、less、rest这三个有效可变参数、num是固定参数
	// 所以可以改成三维动态规划
	public static long process(int index, int less, int rest, long num) {
		if (rest > index + 1) {
			return 0;
		}
		if (rest == 0) {
			return 1L;
		}
		// 0 < rest <= index + 1
		if (less == 1) {
			if (rest == index + 1) {
				return 1;
			} else {
				return process(index - 1, 1, rest - 1, num) + process(index - 1, 1, rest, num);
			}
		} else { // less == 0
			if (rest == index + 1) {
				return (num & (1L << index)) == 0 ? 0 : process(index - 1, 0, rest - 1, num);
			} else {
				if ((num & (1L << index)) == 0) {
					return process(index - 1, 0, rest, num);
				} else {
					return process(index - 1, 0, rest - 1, num) + process(index - 1, 1, rest, num);
				}
			}
		}
	}

	public static void main(String[] args) {
		long range = 600L;
		System.out.println("测试开始");
		for (long start = 0L; start < range; start++) {
			for (long end = start; end < range; end++) {
				int target = (int) (Math.random() * 10);
				long ans1 = nums1(start, end, target);
				long ans2 = nums2(start, end, target);
				if (ans1 != ans2) {
					System.out.println("出错了！");
				}
			}
		}
		System.out.println("测试结束");
	}

}
