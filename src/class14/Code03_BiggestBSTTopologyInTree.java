package class14;

// 本题测试链接 : https://www.nowcoder.com/practice/e13bceaca5b14860b83cbcc4912c5d4a
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 提交以下所有代码，并把主类名改成Main
// 可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_BiggestBSTTopologyInTree {

	public static int MAXN = 200001;

	public static int[][] tree = new int[MAXN][3];

	public static int[] record = new int[MAXN];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				tree[i][0] = 0;
				tree[i][1] = 0;
				tree[i][2] = 0;
				record[i] = 0;
			}
			in.nextToken();
			int h = (int) in.nval;
			for (int i = 1; i <= n; i++) {
				in.nextToken();
				int c = (int) in.nval;
				in.nextToken();
				int l = (int) in.nval;
				in.nextToken();
				int r = (int) in.nval;
				tree[l][0] = c;
				tree[r][0] = c;
				tree[c][1] = l;
				tree[c][2] = r;
			}
			out.println(maxBSTTopology(h));
			out.flush();
		}
	}

	// h: 代表当前的头节点
	// t: 代表树 t[i][0]是i节点的父节点、t[i][1]是i节点的左孩子、t[i][2]是i节点的右孩子
	// m: i节点为头的最大bst拓扑结构大小 -> m[i]
	// 返回: 以h为头的整棵树上，最大bst拓扑结构的大小
	public static int maxBSTTopology(int h) {
		if (h == 0) {
			return 0;
		}
		int l = tree[h][1];
		int r = tree[h][2];
		int p1 = maxBSTTopology(l);
		int p2 = maxBSTTopology(r);
		int tmp = l;
		while (tmp < h && record[tmp] != 0) {
			tmp = tree[tmp][2];
		}
		// 不用沿途修改所有节点的记录了
		// 因为再往上遍历的节点，边界最多包括当前的头节点 + 左孩子 + 一直往左
		// 所以只需要修改左孩子的边界即可
		// 也就是说，从当前节点的左孩子的右孩子开始，一直往右的所有点都不会再遇到了
		record[l] -= record[tmp];
		tmp = r;
		while (tmp > h && record[tmp] != 0) {
			tmp = tree[tmp][1];
		}
		// 不用沿途修改所有节点的记录了
		// 因为再往上遍历的节点，边界最多包括当前的头节点 + 右孩子 + 一直往左
		// 所以只需要修改右孩子的边界即可
		// 也就是说，从当前节点的右孩子的左孩子开始，一直往左的所有点都不会再遇到了
		record[r] -= record[tmp];
		record[h] = record[l] + record[r] + 1;
		return Math.max(Math.max(p1, p2), record[h]);
	}

}
