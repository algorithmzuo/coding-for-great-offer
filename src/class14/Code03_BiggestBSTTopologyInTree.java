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

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int n = (int) in.nval;
			in.nextToken();
			int h = (int) in.nval;
			int[][] tree = new int[n + 1][3];
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
			out.println(maxBSTTopology(h, tree, new int[n + 1]));
			out.flush();
		}
	}

	// h: 代表当前的头节点
	// t: 代表树 t[i][0]是i节点的父节点、t[i][1]是i节点的左孩子、t[i][2]是i节点的右孩子
	// m: i节点为头的最大bst拓扑结构大小 -> m[i]
	// 返回: 以h为头的整棵树上，最大bst拓扑结构的大小
	public static int maxBSTTopology(int h, int[][] t, int[] m) {
		if (h == 0) {
			return 0;
		}
		int l = t[h][1];
		int r = t[h][2];
		int c = 0;
		int p1 = maxBSTTopology(l, t, m);
		int p2 = maxBSTTopology(r, t, m);
		while (l < h && m[l] != 0) {
			l = t[l][2];
		}
		if (m[l] != 0) {
			c = m[l];
			while (l != h) {
				m[l] -= c;
				l = t[l][0];
			}
		}
		while (r > h && m[r] != 0) {
			r = t[r][1];
		}
		if (m[r] != 0) {
			c = m[r];
			while (r != h) {
				m[r] -= c;
				r = t[r][0];
			}
		}
		m[h] = m[t[h][1]] + m[t[h][2]] + 1;
		return Math.max(Math.max(p1, p2), m[h]);
	}

}
