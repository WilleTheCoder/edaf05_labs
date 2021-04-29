package lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;


public class MakingFriends2 {

	public void run() {

		Map<Integer, Node> map = new HashMap<>();
		PriorityQueue<Edge> q = new PriorityQueue<>();

		Scanner read = new Scanner(System.in);
	
		int N = read.nextInt();
		int M = read.nextInt();

		for (int i = 0; i < N; i++) {
			Node n = new Node(i);
			map.put(i, n);
		}

		for (int i = 0; i < M; i++) {
			int from = read.nextInt();
			int to = read.nextInt();
			int w = read.nextInt();

			map.get(from - 1).addEdge(map.get(to - 1), w);
			map.get(to - 1).addEdge(map.get(from - 1), w);
		}

		boolean[] visited = new boolean[N];

		List<Edge> mst = new ArrayList<>();

		q.addAll(map.get(0).getEdges());
		visited[0] = true;

		Edge c_edge;

		while (!q.isEmpty()) {

			c_edge = q.poll();

			if (visited[c_edge.src.index] && !visited[c_edge.dest.index]) {
				mst.add(c_edge);
				q.addAll(map.get(c_edge.dest.index).getEdges());
				visited[c_edge.dest.index] = true;
			}
		}

		int res = 0;
		for (int i = 0; i < mst.size(); i++) {
			res += mst.get(i).getWeight();
		}

		System.out.println(res);

	}

	class Node {
		private List<Edge> edges;
		private int index;

		public Node(int index) {
			this.index = index;
			edges = new ArrayList<>();
		}

		public int getIndex() {
			return index;
		}

		public void addEdge(Node dest, int w) {
			edges.add(new Edge(this, dest, w));
		}

		public List<Edge> getEdges() {
			return edges;
		}

	}

	class Edge implements Comparable<Edge> {
		private int w;
		private Node dest;
		private Node src;

		public Edge(Node src, Node dest, int weight) {
			this.w = weight;
			this.dest = dest;
			this.src = src;
		}

		public int getWeight() {
			return w;
		}

		public Node getSrc() {
			return src;
		}

		public Node getDest() {
			return dest;
		}

		@Override
		public int compareTo(Edge o) {
			return this.w - o.w;
		}

	}

	public static void main(String[] args) {
		MakingFriends2 mf = new MakingFriends2();
		mf.run();

	}

}
