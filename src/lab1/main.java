package lab1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class main {

	public static void main(String[] args) {
		
		Queue<Integer> q = new LinkedList<>();
		
		q.add(1);
		q.add(2);
		q.add(3);
		q.add(4);
		
		q.poll();
		q.add(5);
		
		System.out.println(q);
		
		Stack<Integer> s = new Stack<>();
		
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		
		s.pop();	
		s.add(5);
		System.out.println(s);
		
		
		
	}
	
}
