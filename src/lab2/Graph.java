package lab2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import lab2.WordLadders.Node;

public class Graph {

	private static Map<Node, List<Node>> map = new HashMap<>();
	private String[] wordList;

	class Node {
		private String word;

		public Node(String word) {
			this.word = word;
		}
	}

	void addNode(String word) {
		Node n = new Node(word);
		map.putIfAbsent(n, new ArrayList<>());
	}

	/*
	 * adds a edge from node1 to node2
	 */
	void addEdge(Node n1, Node n2) {
		map.get(n1).add(n2);
	}

	private boolean isEdge(String w1, String w2) {
		int count = 0;
		String sub = w1.substring(w1.length() - 4);

		for (char c : sub.toCharArray()) {
			if (w2.contains(Character.toString(c))) {
				count++;
			}
		}
		return count < 4 ? false : true;
	}

	private void readFile() throws FileNotFoundException {
		Scanner read = new Scanner(new File("./lab2/1.in"));
		Graph g = new Graph();
		int nw, nq;

		nw = read.nextInt();
		nq = read.nextInt();
		wordList = new String[nw];

		for (int i = 0; i < nw; i++)
			wordList[i] = read.next();

		read.close();
		initGraph(g);
	}

	private void initGraph(Graph g) {

		for (String str : wordList) {
			g.addNode(str);
		}
		map.forEach((k1, v1) -> {

			map.forEach((k2, v2) -> {

				if (k1 != k2) {

					if (g.isEdge(k1.word, k2.word)) {
						g.addEdge(k1, k2);
					}
				}

			});
		});
	}

	private void print() {

		map.forEach((k1, v1) -> {

			if (v1.size() != 0)
				System.out.println(k1.word + " :" + v1.get(0).word);
			else {
				System.out.println(k1.word);
			}

		});
	}
	
	private void bfs(Graph g, Node start, Node end) {
		
	}
	

	public static void main(String[] args) throws FileNotFoundException {
		Graph g = new Graph();
		g.readFile();
		g.print();

	}

}
