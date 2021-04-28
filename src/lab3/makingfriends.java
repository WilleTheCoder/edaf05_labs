package lab3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class makingfriends {

	Map<Integer, Node> map = new HashMap<>();
	
	public void readFile() {

		Scanner read = null;
		try {
			read = new Scanner(new File("./lab3/2.in"));
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file..");
		}

		int N = read.nextInt();
		int M = read.nextInt();

	
		for (int i = 0; i < N; i++) {
			map.put(i+1, new Node(i+1));
		}
		
		for (int i = 0; i < M; i++) {
			int from = read.nextInt();
			int to = read.nextInt();
			int w = read.nextInt();
			
			map.get(from).getAdjacent().put(map.get(to), w);
		}
		
		map.forEach((k,v)->{
			System.out.print("node: "+k+" has a arc to ");
			
			v.getAdjacent().forEach((k1,v1)->{
				System.out.println(k1.val + " with a weight of "+v1);
			});
			
			System.out.println("-----------");
		});

	}
	
	public void run() {
		//TODO
		//implement minimum spanning tree algorithm
	}

	class Node {

		private int val;
		private Map<Node, Integer> adj;

		public Node(int val) {
			this.val = val;
			this.adj = new HashMap<>();
		}

		public int getVal() {
			return val;
		}

		public Map<Node, Integer> getAdjacent() {
			return adj;
		}

	}

	public static void main(String[] args) {
		makingfriends mf = new makingfriends();
		mf.readFile();
	}

}
