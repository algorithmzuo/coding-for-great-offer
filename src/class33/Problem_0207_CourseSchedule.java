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

	public static boolean canFinish1(int numCourses, int[][] prerequisites) {
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

	// 和方法1算法过程一样
	// 但是写法优化了
	public static boolean canFinish2(int courses, int[][] relation) {
		if (relation == null || relation.length == 0) {
			return true;
		}
		ArrayList<ArrayList<Integer>> nexts = new ArrayList<>();
		for (int i = 0; i < courses; i++) {
			nexts.add(new ArrayList<>());
		}
		int[] in = new int[courses];
		for (int[] arr : relation) {
			nexts.get(arr[1]).add(arr[0]);
			in[arr[0]]++;
		}
		int[] zero = new int[courses];
		int l = 0;
		int r = 0;
		for (int i = 0; i < courses; i++) {
			if (in[i] == 0) {
				zero[r++] = i;
			}
		}
		int count = 0;
		while (l != r) {
			count++;
			for (int next : nexts.get(zero[l++])) {
				if (--in[next] == 0) {
					zero[r++] = next;
				}
			}
		}
		return count == nexts.size();
	}

}
