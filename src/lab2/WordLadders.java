package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class WordLadders {
	private Map<String, Node> map = new HashMap();

	private void run() throws FileNotFoundException {
		double r0 = System.currentTimeMillis();
		Scanner read = new Scanner(new File("./lab2/6large2.in"));

		int nw = read.nextInt();
		int nq = read.nextInt();

		for (int i = 0; i < nw; i++) {
			String w = read.next();
			map.put(w, new Node(w));
		}

		for (String w1 : map.keySet()) {

			for (String w2 : map.keySet()) {

				if (w1 != w2) {
					if (last4Letters(w1, w2))
						map.get(w1).getAdjacent().add(map.get(w2));
				}
			}
		}
		double r1 = System.currentTimeMillis();
		System.out.println("read-time: "+(r1-r0)/1000);
		
		// run queries
		double t0 = System.currentTimeMillis();
		
		for (int i = 0; i < nq; i++) {
			String s = read.next();
			String e = read.next();

			if (bfs(s, e, map) < 0)
				System.out.println("Impossible");
			else 
				System.out.println(bfs(s, e, map));
			
		}
		double t1 = System.currentTimeMillis();
		double time1 = t1-t0;
		System.out.println("bfs-time: " + time1/1000);
		
		read.close();
	}

	private void test() {
		map.forEach((k, v) -> {
			System.out.print(k + " : ");
			for (Node s : v.adj) {
				System.out.println(s.getWord());
			}
		});
	}

	private boolean last4Letters(String w1, String w2) {
		int count = 0;
		String sub = w1.substring(1);
		String tmp = w2;
		for (char c : sub.toCharArray()) {

			if (tmp.contains(Character.toString(c))) {
				tmp = tmp.replaceFirst(Character.toString(c), "");
				count++;
			}
		}

		return count < 4 ? false : true;
	}

	private int bfs(String start, String end, Map<String, Node> map) {
		// if start-node equals end-node
		if (start.equals(end))
			return 0;

		Node endNode = map.get(end);

		for (Node n : map.values()) {
			n.setVisited(false);
		}

		Queue<Node> q = new LinkedList<>();

		Node cNode = map.get(start);
		cNode.setVisited(true);
		q.add(cNode);

		while (!q.isEmpty()) {
			cNode = q.poll();

			for (Node adj : cNode.getAdjacent()) {

				if (!adj.getVisited()) {
					adj.setVisited(true);
					q.add(adj);
					adj.setPred(cNode);

					if (adj.getWord().equals(endNode.getWord())) {
						return backtrack(start, adj);
					}
				}
			}
		}
		return -1;
	}

	public int backtrack(String start, Node back) {
		int ret = 1;

		while (!back.getPred().getWord().equals(start)) {
			ret += 1;
			back = back.getPred();
		}
		return ret;
	}

	public static void main(String[] args) throws FileNotFoundException {
		WordLadders wl = new WordLadders();
		
		double p0 = System.currentTimeMillis();
		wl.run();
		double p1 = System.currentTimeMillis();
		double time = p1-p0;
		System.out.println("program-time: " + time/1000);
		// wl.test();
	}

	/*
	 * Class for defining a Node
	 */
	class Node {
		private String word;
		private boolean visited;
		private Node pred;
		private List<Node> adj;

		public Node(String word) {
			this.word = word;
			visited = false;
			adj = new ArrayList<>();
		}

		public String getWord() {
			return word;
		}

		public void setVisited(boolean val) {
			visited = val;
		}

		public boolean getVisited() {
			return visited;
		}

		public void setPred(Node n) {
			pred = n;
		}

		public Node getPred() {
			return pred;
		}

		public List<Node> getAdjacent() {
			return adj;
		}

	}

}
