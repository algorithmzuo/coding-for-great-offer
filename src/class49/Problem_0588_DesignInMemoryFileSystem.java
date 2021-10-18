package class49;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Problem_0588_DesignInMemoryFileSystem {

	class FileSystem {
		public class Node {
			public String name;
			// content == null 意味着这个节点是目录
			// content != null 意味着这个节点是文件
			public StringBuilder content;
			public TreeMap<String, Node> nexts;

			public Node(String n) {
				name = n;
				content = null;
				nexts = new TreeMap<>();
			}

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
