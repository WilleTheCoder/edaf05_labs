package lab4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class closestpair {

	List<Player> players = new ArrayList<>();

	void init() {
		Scanner read = null;
		try {
			read = new Scanner(new File("./data/lab4/6huger.in"));
		} catch (FileNotFoundException e) {
			System.out.println("error reading file");
		}

		int n_players = read.nextInt();

		for (int i = 0; i < n_players; i++) {
			int x = read.nextInt();
			int y = read.nextInt();

			players.add(new Player(x, y));
		}

		List<Player> pX = new ArrayList<>(players);
		List<Player> pY = new ArrayList<>(players);
		Collections.sort(pX, new XCompare());
		Collections.sort(pY, new YCompare());

		double distance = closestPair(pX, pY, n_players);
		System.out.println("distance is: " + distance);

		read.close();

	}

	public double closestPair(List<Player> pX, List<Player> pY, int n) {

		if (n <= 3)
			return brute(pX);

		int mid = n / 2;

		List<Player> lX = new ArrayList<>(pX.subList(0, mid));
		List<Player> rX = new ArrayList<>(pX.subList(mid, n));
		List<Player> lY = new ArrayList<>(pX.subList(0, mid));
		List<Player> rY = new ArrayList<>(pX.subList(mid, n));

		double d1 = closestPair(lX, lY, lX.size());
		double d2 = closestPair(rX, rY, rY.size());

		System.out.println(d1 + " : " + d2);

		double d = Math.min(d1, d2);

		List<Player> sY = new ArrayList<>();

		for (Player p : pY) {
			if (Math.abs(pX.get(mid).getX() - p.getX()) < d) {
				sY.add(p);
			}
		}
	
		int rng;
		for (int i = 0; i < sY.size(); i++) {
		rng = Math.min(sY.size(), 16);
			for (int j = i + 1; j < rng; j++) {
				double dist = pX.get(i).dist(pX.get(j));

				if (dist < d)
					d = dist;
			}
		}

		return d;
	}

	
	
	public double brute(List<Player> pX) {

		double minDist = Double.MAX_VALUE;

		for (int i = 0; i < pX.size(); i++) {
			for (int j = i + 1; j < pX.size(); j++) {
				double dist = pX.get(i).dist(pX.get(j));

				if (dist < minDist)
					minDist = dist;
			}
		}

		return minDist;

	}

	public static void main(String[] args) {
		closestpair c = new closestpair();

		c.init();
	}

	class Player {

		private int x;
		private int y;

		public Player(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}

		public double dist(Player p2) {
			return Math.hypot(this.x - p2.x, this.y - p2.y);
		}
	}

	public class XCompare implements Comparator<Player> {

		@Override
		public int compare(Player o1, Player o2) {
			return o1.x - o2.x;
		}

	}

	public class YCompare implements Comparator<Player> {

		@Override
		public int compare(Player o1, Player o2) {
			return o1.y - o2.y;
		}

	}

}
