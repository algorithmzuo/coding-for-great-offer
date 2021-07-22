package class26;

import java.util.Comparator;
import java.util.TreeSet;

public class Code05_MinRange {

	// [
	// [3,6,10]
	// [4,5,7,10,14]
	// [2 , 19]
	// ]
	public static int minRange(int[][] matrix) {
		for (int[] arr : matrix) {
			if (arr == null || arr.length == 0) {
				return -1;
			}
		}
		int N = matrix.length;
		TreeSet<Node> set = new TreeSet<>(new NodeComparator());
		for (int i = 0; i < N; i++) {
			set.add(new Node(matrix[i][0], i, 0));
		}
		int min = Integer.MAX_VALUE;
		while (set.size() == N) {
			min = Math.min(min, set.last().value - set.first().value);
			Node pop = set.pollFirst();
			int arri = pop.arrIndex;
			int owni = pop.ownIndex;
			if (owni < matrix[arri].length - 1) {
				set.add(new Node(matrix[arri][owni + 1], arri, owni + 1));
			}
		}
		return min;
	}

	public static class Node {
		public int value;
		public int arrIndex;
		public int ownIndex;

		public Node(int v, int a, int o) {
			value = v;
			arrIndex = a;
			ownIndex = o;
		}
	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.value != o2.value ? (o1.value - o2.value) : (o1.toString().compareTo(o2.toString()));
		}

	}

}
