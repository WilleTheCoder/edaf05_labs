package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

/*
 * Lab 1: Stable Marriage algorithm
 */
public class StableMarriage {

	private int[][] pref_male, pref_female; // male/female preference matrix
	private int[] pairs; // male-female pairs
	private boolean[] free; // free males
	private int n; // number of pairs

	/*
	 * Initialization: Read input file. Create male/female preference list
	 */
	public void init() throws IOException {
		Scanner read = new Scanner(new File("0testsmall.in"));

		n = Integer.parseInt(read.nextLine());

		pref_male = new int[n][n];
		pref_female = new int[n][n];
		int[] temp = new int[n];

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
		pairs = new int[n];
		Arrays.fill(pairs, -1);
		free = new boolean[n];
		Arrays.fill(free, true);
		int n_free = n; // free males

		// while free males loop
		while (n_free != 0) {
			// pick a free male
			loop: for (int i = 0; i < n; i++) {
				if (!free[i]) // if male is not free pick another male
					continue loop;
				// pick a women in m's preference list
				for (int j = 0; j < n; j++) {
					int w = pref_male[i][j];

					// if w is free -> marry
					if (pairs[w] == -1) {
						pairs[w] = i;
						free[i] = false;
						n_free--;
						continue loop;
					}
					// otherwise see if w prefers m over current m'
					else {
						int ci = pairs[w];
						// if w doesnt prefer current m' -> dump n swap
						if (!prefer(w, ci, i)) {
							pairs[w] = i;
							free[ci] = true;
							free[i] = false;
							continue loop;
						}
						// if w prefers current m' -> take another w from m preference list
					}
				}
			}
		}
		// printout result
		for (int i = 0; i < n; i++) {
			System.out.println((pairs[i] + 1));
		}
	}

	/*
	 * Checks if w prefers m1 (current partner) over m2 (competition) returns true
	 * if w prefers current partner otherwise false
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
		StableMarriage sm = new StableMarriage();
		double t0, t1, tot = 0;
		int n = 10;
	
		try {
			sm.init();
		} catch (IOException e) {
			e.printStackTrace();
		}

		t0 = System.nanoTime();
		sm.run();
		t1 = System.nanoTime();

		System.out.println("time taken: " +(t1-t0));

	}
}
