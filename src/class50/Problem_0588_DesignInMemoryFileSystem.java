package class50;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Problem_0588_DesignInMemoryFileSystem {

	class FileSystem {

		public class Node {
			// 文件名、目录名
			public String name;
			// content == null 意味着这个节点是目录
			// content != null 意味着这个节点是文件
			public StringBuilder content;
			public TreeMap<String, Node> nexts;

			// 构造目录
			public Node(String n) {
				name = n;
				content = null;
				nexts = new TreeMap<>();
			}

			// 构造文件，c是文件内容
			public Node(String n, String c) {
				name = n;
				content = new StringBuilder(c);
				nexts = new TreeMap<>();
			}

		}

		public Node head;

		public FileSystem() {
			head = new Node("");
		}

		public List<String> ls(String path) {
			List<String> ans = new ArrayList<>();
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			for (int i = 1; i < n; i++) {
				if (!cur.nexts.containsKey(parts[i])) {
					return ans;
				}
				cur = cur.nexts.get(parts[i]);
			}
			// cur结束了！来到path最后的节点，该返回了
			// ls a/b/c  cur 来到c目录
			// 如果c是目录，那么就要返回c下面所有的东西！
			// 如果c是文件，那么就值返回c
			if (cur.content == null) {
				ans.addAll(cur.nexts.keySet());
			} else {
				ans.add(cur.name);
			}
			return ans;
		}

		public void mkdir(String path) {
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			for (int i = 1; i < n; i++) {
				if (!cur.nexts.containsKey(parts[i])) {
					cur.nexts.put(parts[i], new Node(parts[i]));
				}
				cur = cur.nexts.get(parts[i]);
			}
		}

		public void addContentToFile(String path, String content) {
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			for (int i = 1; i < n - 1; i++) {
				if (!cur.nexts.containsKey(parts[i])) {
					cur.nexts.put(parts[i], new Node(parts[i]));
				}
				cur = cur.nexts.get(parts[i]);
			}
			// 来到的是倒数第二的节点了！注意for！
			if (!cur.nexts.containsKey(parts[n - 1])) {
				cur.nexts.put(parts[n - 1], new Node(parts[n - 1], ""));
			}
			cur.nexts.get(parts[n - 1]).content.append(content);
		}

		public String readContentFromFile(String path) {
			Node cur = head;
			String[] parts = path.split("/");
			int n = parts.length;
			for (int i = 1; i < n; i++) {
				if (!cur.nexts.containsKey(parts[i])) {
					cur.nexts.put(parts[i], new Node(parts[i]));
				}
				cur = cur.nexts.get(parts[i]);
			}
			return cur.content.toString();
		}
	}

}
