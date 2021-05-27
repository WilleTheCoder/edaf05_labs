package lab5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class SequenceAlignment {
	private final int delta = -4;
	private String space = "*";
	private String seq, sq0, sq1;
	private Map<Character, Integer> map = new HashMap<>();
	private int mx[][];
	private int [][] opt;

	public SequenceAlignment() {

	}

	public void init() {

		Scanner read = null;
		try {
			read = new Scanner(new File("./data/lab5/4huge.in"));
		} catch (FileNotFoundException e) {
			System.out.println("Error reading file: " + e.getMessage());
		}

		seq = String.join("",read.nextLine().split(" "));
		System.out.println(seq);
		int l = seq.length();
		
		for (int i = 0; i < l; i++)
			map.put(seq.charAt(i), i);

		mx = new int[l][l]; //matrix with cost of match/mismatch

		for (int i = 0; i < mx.length; i++) {
			for (int j = 0; j < mx.length; j++) {
				mx[i][j] = read.nextInt();
			}
		}

		int q = read.nextInt();
		
		for (int i = 0; i < q; i++) {
			sq0 = read.next();
			sq1 = read.next();
			
			runQ(sq0.length(), sq1.length());
		}
	}

	public void runQ(int n, int m) {
		
		opt = new int [n+1][m+1];	
		
		for (int i = 0; i < n+1; i++)
			opt[i][0] = delta*i;
		
		for (int j = 0; j < m+1; j++)
			opt[0][j] = delta*j;
	
		//O(nm)
		for (int i = 1; i < n+1; i++) {
			for (int j = 1; j < m+1; j++) {
				
				int a1 = opt[i-1][j] + delta;
				int a2 = opt[i][j-1] + delta;
				
				int cost = mx[map.get(sq0.charAt(i-1))][map.get(sq1.charAt(j-1))];
				
				int a3 = opt[i-1][j-1] + cost;
				
				opt[i][j] = Math.max(a1, Math.max(a2, a3));	
			}
		}
		
	//	print(opt, n, m);
		
		String ans = opt(n,m);
		System.out.println(ans);
		
	}
	
	public String opt(int n, int m) {
		
		while(true) {
			
		if(n == 0)
			return space.repeat(m) + sq0 + " " + sq1; 
		
		if(m == 0)
			return sq0 + " " + space.repeat(n) + sq1;
			
		
		int cost = mx[map.get(sq0.charAt(n-1))][map.get(sq1.charAt(m-1))];
		
		int a1 = opt[n-1][m] + delta;
		int a2 = opt[n][m-1] + delta;
		int a3 = opt[n-1][m-1] + cost;
		
		if(Math.max(a1, a2) > a3) {
			if(a1 < a2) {
				sq0 = sq0.substring(0, n) + space + sq0.substring(n);
				n++;
			} else {
				sq1 = sq1.substring(0, m) + space + sq1.substring(m);
				m++;
			}
		} 
		n--;
		m--;
		}
	}
	
	
	public void print(int[][] opt, int n, int m) {
		
		for (int i = 0; i < n+1; i++) {
			for (int j = 0; j < m+1; j++) {
				
				System.out.print(opt[i][j]+",\t ");
			}
			System.out.println();
		}
	}
	

	public static void main(String[] args) {
		SequenceAlignment sa = new SequenceAlignment();
		sa.init();
	}

}
