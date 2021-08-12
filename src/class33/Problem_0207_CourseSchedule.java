package class33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0207_CourseSchedule {

	// 一个node，就是一个课程
	// name是课程的编号
	// in是课程的入度
	public static class Node {
		public int name;
		public int in;
		public ArrayList<Node> nexts;

		public Node(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	public static boolean canFinish(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) {
			return true;
		}
		HashMap<Integer, Node> nodes = new HashMap<>();
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Node(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Node(from));
			}
			Node t = nodes.get(to);
			Node f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}
		int needPrerequisiteNums = nodes.size();
		Queue<Node> zeroInQueue = new LinkedList<>();
		for (Node node : nodes.values()) {
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			Node cur = zeroInQueue.poll();
			count++;
			for (Node next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums;
	}

}
