package class30;

import java.util.ArrayList;
import java.util.List;

public class Problem_0140_WordBreakII {

	public static class Node {
		public String path;
		public boolean end;
		public Node[] nexts;

		public Node() {
			path = null;
			end = false;
			nexts = new Node[26];
		}
	}

	public static List<String> wordBreak(String s, List<String> wordDict) {
		char[] str = s.toCharArray();
		Node root = gettrie(wordDict);
		boolean[] dp = getdp(s, root);
		ArrayList<String> path = new ArrayList<>();
		List<String> ans = new ArrayList<>();
		process(str, 0, root, dp, path, ans);
		return ans;
	}

	public static void process(char[] str, int index, Node root, boolean[] dp, ArrayList<String> path,
			List<String> ans) {
		if (index == str.length) {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < path.size() - 1; i++) {
				builder.append(path.get(i) + " ");
			}
			builder.append(path.get(path.size() - 1));
			ans.add(builder.toString());
		} else {
			Node cur = root;
			for (int end = index; end < str.length; end++) {
				int road = str[end] - 'a';
				if (cur.nexts[road] == null) {
					break;
				}
				cur = cur.nexts[road];
				if (cur.end && dp[end + 1]) {
					path.add(cur.path);
					process(str, end + 1, root, dp, path, ans);
					path.remove(path.size() - 1);
				}
			}
		}
	}

	public static Node gettrie(List<String> wordDict) {
		Node root = new Node();
		for (String str : wordDict) {
			char[] chs = str.toCharArray();
			Node node = root;
			int index = 0;
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new Node();
				}
				node = node.nexts[index];
			}
			node.path = str;
			node.end = true;
		}
		return root;
	}

	public static boolean[] getdp(String s, Node root) {
		char[] str = s.toCharArray();
		int N = str.length;
		boolean[] dp = new boolean[N + 1];
		dp[N] = true;
		for (int i = N - 1; i >= 0; i--) {
			Node cur = root;
			for (int end = i; end < N; end++) {
				int path = str[end] - 'a';
				if (cur.nexts[path] == null) {
					break;
				}
				cur = cur.nexts[path];
				if (cur.end && dp[end + 1]) {
					dp[i] = true;
					break;
				}
			}
		}
		return dp;
	}

}
