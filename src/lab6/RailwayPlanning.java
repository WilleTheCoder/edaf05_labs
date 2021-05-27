package lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class RailwayPlanning {

	private Node[] graph;

	public void init() {

		Scanner read = null;
		try {
			read = new Scanner(new File("./data/lab6/0mini.in"));
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file " + e.getMessage());
		}

		int n, m, stud, routes; //nodes, edges, students, routes
		
		n = read.nextInt();
		m = read.nextInt();
		stud = read.nextInt();
		routes = read.nextInt();

		graph = new Node[n];		//all nodes in the graph
		Edge[] edges = new Edge[m]; 	//list of edges with corresponding index

		for (int i = 0; i < n; i++)
			graph[i] = new Node(i);

		for (int i = 0; i < m; i++) {
			int u = read.nextInt();		//from
			int v = read.nextInt();		//to
			int c = read.nextInt();		//capacity

			edges[i] = graph[u].addEdge(graph[v], c); 
			graph[v].addEdge(graph[u], c);

		}

		
		int[] removePri = new int[routes];	//list of routes sorted in priority of removal
		//removes all paths specified
		for (int i = 0; i < routes; i++) {
			removePri[i] = read.nextInt();

			int n1 = edges[removePri[i]].getSrc().index;
			int n2 = edges[removePri[i]].getDest().index;

			graph[n1].map.remove(graph[n2].index);
			graph[n2].map.remove(graph[n1].index);

		}
		
		read.close();

		int totFlow = 0;
		//adds paths to the graph until totalflow > stud
		for (int j = removePri.length - 1; j >= 0; j--) {
			Edge tmpath = edges[removePri[j]];

			int n1 = edges[removePri[j]].getSrc().index;
			int n2 = edges[removePri[j]].getDest().index;

			graph[n1].map.put(graph[n2].index, new Edge(tmpath.src, tmpath.dest, tmpath.cap));
			graph[n2].map.put(graph[n1].index, new Edge(tmpath.dest, tmpath.src, tmpath.cap));

			int maxflow = Integer.MAX_VALUE;

			while (maxflow != 0) { //aslong as we find an augmented path
				maxflow = bfs(0, n - 1); //calculate flow
				totFlow += maxflow;
			}

			if (totFlow >= stud) {
				System.out.println(j + " " + totFlow);
				return;
			}
		}

	}

	public int bfs(int start, int sink) {
		LinkedList<Integer> q = new LinkedList<>(); //queue
		int bottleneck = Integer.MAX_VALUE;

		q.add(start); //add start node

		//init all nodes not visited
		for (Node n : graph) 
			n.setVisited(false);

		//set start node visited
		graph[start].setVisited(true);

		
		
		while (!q.isEmpty()) {
			int cindex = q.poll();

			Node cNode = graph[cindex]; //current node

			for (Edge edge : cNode.getEdge()) { //go through all neighbours

				Node x = edge.getDest();

				if (!x.visited && edge.flow < edge.cap) {	
					q.add(x.index);
					x.setPred(cNode);
					x.setVisited(true);

					if (x.index == sink) {	//if current node is sink node

						Node n_c = x;
						Edge e_c = edge;

						//find bottleneck in path
						while (n_c.pred != null) {

							e_c = n_c.pred.getSpecEdge(n_c.index);

							int bn_new = e_c.cap - e_c.flow;
							if (bn_new < bottleneck)
								bottleneck = bn_new;

							n_c = n_c.pred;
						}

						n_c = x;
						//backtrack path and update flow
						while (n_c.pred != null) {
							n_c.pred.getSpecEdge(n_c.index).addFlow(bottleneck);
							n_c = n_c.pred;
						}

						return bottleneck;
					}
				}
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		RailwayPlanning rp = new RailwayPlanning();
		rp.init();
	}

	
	class Node {

		private int index;					//index
		private HashMap<Integer, Edge> map; //adjacent nodes
		private Node pred;					//predessor node
		private boolean visited;			//visited

		public Node(int index) {
			this.index = index;
			map = new HashMap<>();
			visited = false;
		}

		public void setVisited(boolean val) {
			visited = val;
		}

		public int getIndex() {
			return index;
		}

		public ArrayList<Edge> getEdge() {
			return new ArrayList<>(map.values());
		}

		public Edge getSpecEdge(int index) {
			return map.get(index);
		}

		public void setPred(Node n) {
			pred = n;
		}

		public Edge addEdge(Node to, int cap) {
			Edge e = new Edge(this, to, cap);
			map.put(to.index, e);
			return e;
		}

	}

	class Edge {
		private int cap;		//capacity
		private int flow;		//flow
		private Node dest;		//destNode
		private Node src;		//srcNode

		public Edge(Node src, Node dest, int cap) {
			this.cap = cap;
			this.dest = dest;
			this.src = src;
			flow = 0;
		}

		public void addFlow(int val) {
			this.flow += val;
		}

		public int getCap() {
			return cap;
		}

		public Node getSrc() {
			return src;
		}

		public Node getDest() {
			return dest;
		}

	}

}
