package lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class makingfriends {

	public void readFile() {

		Map<Integer, Node> map = new HashMap<>();
		PriorityQueue<Node> q = new PriorityQueue<Node>();

		Scanner read = null;
		try {
			read = new Scanner(new File("./lab3/2med.in"));
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file..");
		}
		
		int N = read.nextInt();
		int M = read.nextInt();

		for (int i = 1; i <= N; i++) {
			Node n = new Node(i);
			q.add(n);
			map.put(i, n);
		}

		for (int i = 0; i < M; i++) {
			int from = read.nextInt();
			int to = read.nextInt();
			int w = read.nextInt();

			map.get(from).addEdge(map.get(to), w);
			map.get(to).addEdge(map.get(from), w);
		}
		
		map.get(1).setVal(0);
		ArrayList<Node> mst = new ArrayList<>();

		while (!q.isEmpty()) {
			Node c_node = q.poll();
			mst.add(c_node);
		

			for (Edge e : c_node.getEdges()) {

				Node i = e.getDest();

				if (q.contains(i) && e.getWeight() < i.getVal()) {
					i.setVal(e.getWeight());
					q.remove(i);
					q.offer(i);
				}
			}
		}
		
		int res = 0;
		
		for (int i = 0; i < mst.size(); i++) {
			res += mst.get(i).val;
		}
		
		System.out.println(res);


	}


	class Node implements Comparable<Node> {

		private int val;
		private List<Edge> edges;
		private int index;

		public Node(int index) {
			this.index = index;
			val = Integer.MAX_VALUE;
			edges = new ArrayList<>();
		}

		public int getIndex() {
			return index;
		}

		public int getVal() {
			return val;
		}

		public void setVal(int val) {
			this.val = val;
		}

		public void addEdge(Node dest, int w) {
			edges.add(new Edge(dest, w));
		}

		public List<Edge> getEdges() {
			return edges;
		}

		@Override
		public int compareTo(Node o) {
			return this.val-o.val;
		}

	}

	class Edge {
		private int w;
		private Node dest;

		public Edge(Node dest, int weight) {
			this.w = weight;
			this.dest = dest;
		}

		public int getWeight() {
			return w;
		}

		public Node getDest() {
			return dest;
		}

	}

	public static void main(String[] args) {
		makingfriends mf = new makingfriends();
		mf.readFile();

	}

}
