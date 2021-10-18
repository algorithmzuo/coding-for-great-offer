package class47;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Problem_0428_SerializeAndDeserializeNaryTree {

	// 不要提交这个类
	public static class Node {
		public int val;
		public List<Node> children;

		public Node() {
			children = new ArrayList<>();
		}

		public Node(int _val) {
			val = _val;
			children = new ArrayList<>();
		}

		public Node(int _val, List<Node> _children) {
			val = _val;
			children = _children;
		}
	};

	// 提交下面这个类
	public class Codec {

		public String serialize(Node root) {
			if (root == null) {
				return "#";
			}
			StringBuilder builder = new StringBuilder();
			serial(builder, root);
			return builder.toString();
		}

		private void serial(StringBuilder builder, Node head) {
			builder.append(head.val + ",");
			if (!head.children.isEmpty()) {
				builder.append("[,");
				for (Node next : head.children) {
					serial(builder, next);
				}
				builder.append("],");
			}
		}

		public Node deserialize(String data) {
			if (data.equals("#")) {
				return null;
			}
			String[] splits = data.split(",");
			Queue<String> queue = new LinkedList<>();
			for (String str : splits) {
				queue.offer(str);
			}
			return deserial(queue);
		}

		private Node deserial(Queue<String> queue) {
			Node cur = new Node(Integer.valueOf(queue.poll()));
			cur.children = new ArrayList<>();
			if (!queue.isEmpty() && queue.peek().equals("[")) {
				queue.poll();
				while (!queue.peek().equals("]")) {
					Node child = deserial(queue);
					cur.children.add(child);
				}
				queue.poll();
			}
			return cur;
		}

	}

//	public static void main(String[] args) {
//		如果想跑以下的code，请把Codec类描述和内部所有方法改成static的
//		Node a = new Node(1);
//		Node b = new Node(2);
//		Node c = new Node(3);
//		Node d = new Node(4);
//		Node e = new Node(5);
//		Node f = new Node(6);
//		Node g = new Node(7);
//		a.children.add(b);
//		a.children.add(c);
//		a.children.add(d);
//		b.children.add(e);
//		b.children.add(f);
//		e.children.add(g);
//		String content = Codec.serialize(a);
//		System.out.println(content);
//		Node head = Codec.deserialize(content);
//		System.out.println(content.equals(Codec.serialize(head)));
//	}

}
