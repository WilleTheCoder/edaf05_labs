package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/*
 * Lab 1: Stable Marriage algorithm
 */
public class sm {

	private int[][] pref_male, pref_female; // male/female preference matrix
	private int n; // number of pairs
	private LinkedList<Integer> men;

	/*
	 * Initialization: Read input file. Create male/female preference list
	 */
	public void init() throws IOException {
		Scanner read = new Scanner(new File("0testsmall.in"));

		n = Integer.parseInt(read.nextLine());

		pref_male = new int[n][n];
		pref_female = new int[n][n];
		int[] temp = new int[n];
		men = new LinkedList<>();

		// while input
		while (read.hasNextInt()) {
			int[] inp = new int[n + 1];
			// read n+1 next ints
			for (int i = 0; i < n + 1; i++) {
				inp[i] = read.nextInt();
			}

			// help variables
			int ctr, c, val;
			ctr = c = val = 0;

			// iterate add to preference list
			for (int i = 0; i < inp.length; i++) {
				// first entry -> male/female
				if (ctr == 0) {

					val = inp[i];
					men.add(val - 1);
					temp[val - 1]++;
					ctr++;
				}

				// add to female preference list
				else if (temp[val - 1] == 1) {
					pref_female[val - 1][c] = inp[i] - 1;
					c++;

					// add to male preference list
				} else {
					pref_male[val - 1][c] = inp[i] - 1;
					c++;
				}
			}
			temp[val - 1]++;
		}
	}

	/*
	 * Run Gale-Shepley algorithm
	 */
	public void run() {
		// init everyone free

		HashMap<Integer, Integer> pairs = new HashMap<>();
		int[] onlyOnce = new int[n];
		int m, w;
		Arrays.fill(onlyOnce, 0);

		loop: while (!men.isEmpty()) {
			m = men.poll();
			
			for (int i = onlyOnce[m]; i < n; i++) {
				onlyOnce[m]++;
				w = pref_male[m][i];

				// if w is free -> marry
				if (!pairs.containsKey(w)) {
					pairs.put(w, m);
					continue loop;
				} else {
					// otherwise see if w prefers m over current m'
					int ci = pairs.get(w);

					// if w doesnt prefer current m' -> dump n swap
					if (prefer(w, m, ci)) {
						pairs.replace(w, m);
						men.offer(ci);
						continue loop;
					} 
					// if w prefers current m' -> take another w from m preference list
				}
				
			}
		}

		// printout result
		for (int i = 0; i < n; i++) {
			System.out.println(pairs.get(i) + 1);
		}
	}

	/*
	 * if w prefers new parner better return true otherwise falses m1 = new partner
	 * m2 = current partner
	 */
	public boolean prefer(int w, int m1, int m2) {
		for (int i = 0; i < n; i++) {

			if (pref_female[w][i] == m1)
				return true;

			if (pref_female[w][i] == m2)
				return false;
		}
		return false;
	}

	/*
	 * Prints out array
	 */
	public void print(int[][] arr) {
		for (int[] is : arr)
			System.out.println(Arrays.toString(is));
	}

	/*
	 * Main method
	 */
	public static void main(String[] args) throws FileNotFoundException {
		sm sm = new sm();

		try {
			sm.init();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("fem");
//		sm.print(sm.pref_female);
//		System.out.println("men");
//		sm.print(sm.pref_male);

		sm.run();

	}
}
