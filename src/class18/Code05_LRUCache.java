package class18;

import java.util.HashMap;

// 本题测试链接 : https://leetcode.com/problems/lru-cache/
// 提交时把类名和构造方法名改成 : LRUCache
public class Code05_LRUCache {

	public Code05_LRUCache(int capacity) {
		cache = new MyCache<>(capacity);
	}

	private MyCache<Integer, Integer> cache;

	public int get(int key) {
		Integer ans = cache.get(key);
		return ans == null ? -1 : ans;
	}

	public void put(int key, int value) {
		cache.set(key, value);
	}

	public static class Node<K, V> {
		public K key;
		public V value;
		public Node<K, V> last;
		public Node<K, V> next;

		public Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	public static class NodeDoubleLinkedList<K, V> {
		private Node<K, V> head;
		private Node<K, V> tail;

		public NodeDoubleLinkedList() {
			head = null;
			tail = null;
		}

		public void addNode(Node<K, V> newNode) {
			if (newNode == null) {
				return;
			}
			if (head == null) {
				head = newNode;
				tail = newNode;
			} else {
				tail.next = newNode;
				newNode.last = tail;
				tail = newNode;
			}
		}

		public void moveNodeToTail(Node<K, V> node) {
			if (this.tail == node) {
				return;
			}
			if (this.head == node) {
				this.head = node.next;
				this.head.last = null;
			} else {
				node.last.next = node.next;
				node.next.last = node.last;
			}
			node.last = this.tail;
			node.next = null;
			this.tail.next = node;
			this.tail = node;
		}

		public Node<K, V> removeHead() {
			if (this.head == null) {
				return null;
			}
			Node<K, V> res = this.head;
			if (this.head == this.tail) {
				this.head = null;
				this.tail = null;
			} else {
				this.head = res.next;
				res.next = null;
				this.head.last = null;
			}
			return res;
		}

	}

	public static class MyCache<K, V> {
		private HashMap<K, Node<K, V>> keyNodeMap;
		private NodeDoubleLinkedList<K, V> nodeList;
		private final int capacity;

		public MyCache(int cap) {
			keyNodeMap = new HashMap<K, Node<K, V>>();
			nodeList = new NodeDoubleLinkedList<K, V>();
			capacity = cap;
		}

		public V get(K key) {
			if (keyNodeMap.containsKey(key)) {
				Node<K, V> res = keyNodeMap.get(key);
				nodeList.moveNodeToTail(res);
				return res.value;
			}
			return null;
		}

		public void set(K key, V value) {
			if (keyNodeMap.containsKey(key)) {
				Node<K, V> node = keyNodeMap.get(key);
				node.value = value;
				nodeList.moveNodeToTail(node);
			} else {
				Node<K, V> newNode = new Node<K, V>(key, value);
				keyNodeMap.put(key, newNode);
				nodeList.addNode(newNode);
				if (keyNodeMap.size() == capacity + 1) {
					removeMostUnusedCache();
				}
			}
		}

		private void removeMostUnusedCache() {
			Node<K, V> removeNode = nodeList.removeHead();
			keyNodeMap.remove(removeNode.key);
		}

	}

}
