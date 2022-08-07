package class18;

// 牛客的测试链接：
// https://www.nowcoder.com/practice/7201cacf73e7495aa5f88b223bbbf6d1
// 不要提交包信息，把import底下的类名改成Main，提交下面的代码可以直接通过
// 因为测试平台会卡空间，所以把set换成了动态加和减的结构
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Code04_TopKSumCrossTwoArrays {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int N = (int) in.nval;
			in.nextToken();
			int K = (int) in.nval;
			int[] arr1 = new int[N];
			int[] arr2 = new int[N];
			for (int i = 0; i < N; i++) {
				in.nextToken();
				arr1[i] = (int) in.nval;
			}
			for (int i = 0; i < N; i++) {
				in.nextToken();
				arr2[i] = (int) in.nval;
			}
			int[] topK = topKSum(arr1, arr2, K);
			for (int i = 0; i < K; i++) {
				out.print(topK[i] + " ");
			}
			out.println();
			out.flush();
		}
	}

	// 放入大根堆中的结构
	public static class Node {
		public int index1;// arr1中的位置
		public int index2;// arr2中的位置
		public int sum;// arr1[index1] + arr2[index2]的值

		public Node(int i1, int i2, int s) {
			index1 = i1;
			index2 = i2;
			sum = s;
		}
	}

	// 生成大根堆的比较器
	public static class MaxHeapComp implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o2.sum - o1.sum;
		}
	}

	public static int[] topKSum(int[] arr1, int[] arr2, int topK) {
		if (arr1 == null || arr2 == null || topK < 1) {
			return null;
		}
		int N = arr1.length;
		int M = arr2.length;
		topK = Math.min(topK, N * M);
		int[] res = new int[topK];
		int resIndex = 0;
		PriorityQueue<Node> maxHeap = new PriorityQueue<>(new MaxHeapComp());
		HashSet<Long> set = new HashSet<>();
		int i1 = N - 1;
		int i2 = M - 1;
		maxHeap.add(new Node(i1, i2, arr1[i1] + arr2[i2]));
		set.add(x(i1, i2, M));
		while (resIndex != topK) {
			Node curNode = maxHeap.poll();
			res[resIndex++] = curNode.sum;
			i1 = curNode.index1;
			i2 = curNode.index2;
			set.remove(x(i1, i2, M));
			if (i1 - 1 >= 0 && !set.contains(x(i1 - 1, i2, M))) {
				set.add(x(i1 - 1, i2, M));
				maxHeap.add(new Node(i1 - 1, i2, arr1[i1 - 1] + arr2[i2]));
			}
			if (i2 - 1 >= 0 && !set.contains(x(i1, i2 - 1, M))) {
				set.add(x(i1, i2 - 1, M));
				maxHeap.add(new Node(i1, i2 - 1, arr1[i1] + arr2[i2 - 1]));
			}
		}
		return res;
	}

	public static long x(int i1, int i2, int M) {
		return (long) i1 * (long) M + (long) i2;
	}

}
