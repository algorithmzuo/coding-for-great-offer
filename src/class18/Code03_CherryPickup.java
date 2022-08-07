package class18;

// 牛客的测试链接：
// https://www.nowcoder.com/questionTerminal/8ecfe02124674e908b2aae65aad4efdf
// 请同学们务必参考如下代码中关于输入、输出的处理
// 这是输入输出处理效率很高的写法
// 把如下的全部代码拷贝进java编辑器
// 把文件大类名字改成Main，可以直接通过
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;

public class Code03_CherryPickup {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StreamTokenizer in = new StreamTokenizer(br);
		PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
		while (in.nextToken() != StreamTokenizer.TT_EOF) {
			int N = (int) in.nval;
			in.nextToken();
			int M = (int) in.nval;
			int[][] matrix = new int[N][M];
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					in.nextToken();
					matrix[i][j] = (int) in.nval;
				}
			}
			out.println(cherryPickup(matrix));
			out.flush();
		}
	}

	public static int cherryPickup(int[][] grid) {
		int N = grid.length;
		int M = grid[0].length;
		int[][][] dp = new int[N][M][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				for (int k = 0; k < N; k++) {
					dp[i][j][k] = Integer.MIN_VALUE;
				}
			}
		}
		int ans = process(grid, 0, 0, 0, dp);
		return ans < 0 ? 0 : ans;
	}

	public static int process(int[][] grid, int x1, int y1, int x2, int[][][] dp) {
		if (x1 == grid.length || y1 == grid[0].length || x2 == grid.length || x1 + y1 - x2 == grid[0].length) {
			return Integer.MIN_VALUE;
		}
		if (dp[x1][y1][x2] != Integer.MIN_VALUE) {
			return dp[x1][y1][x2];
		}
		if (x1 == grid.length - 1 && y1 == grid[0].length - 1) {
			dp[x1][y1][x2] = grid[x1][y1];
			return dp[x1][y1][x2];
		}
		int next = Integer.MIN_VALUE;
		next = Math.max(next, process(grid, x1 + 1, y1, x2 + 1, dp));
		next = Math.max(next, process(grid, x1 + 1, y1, x2, dp));
		next = Math.max(next, process(grid, x1, y1 + 1, x2, dp));
		next = Math.max(next, process(grid, x1, y1 + 1, x2 + 1, dp));
		if (grid[x1][y1] == -1 || grid[x2][x1 + y1 - x2] == -1 || next == -1) {
			dp[x1][y1][x2] = -1;
			return dp[x1][y1][x2];
		}
		if (x1 == x2) {
			dp[x1][y1][x2] = grid[x1][y1] + next;
			return dp[x1][y1][x2];
		}
		dp[x1][y1][x2] = grid[x1][y1] + grid[x2][x1 + y1 - x2] + next;
		return dp[x1][y1][x2];
	}

}