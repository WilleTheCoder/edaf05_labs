package lab1;
import java.util.*;

public class asd{

    static HashMap<Integer, Integer> coupple = new HashMap<Integer, Integer>();

    public static void findMatch(Integer N, Integer[][] mp, Integer[][] wp){

        Integer m;
        Integer w;
    
        Integer count = 0;
        Integer round = 1;

        LinkedList<Integer> p = new LinkedList<Integer>();
        for(int i = 0; i < N; i++) p.add(i);

        while((m = p.pollFirst()) != null) {
            w = mp[m][count];

            if(!coupple.containsKey(w)) coupple.put(w,m);

            else if(wp[w][m] < wp[w][coupple.get(w)]) coupple.replace(w, m);

            else p.add(m);
            round++;
            if(round == N) round = 1 + count++;
        }   
    }

    public static void main(String[] args){

        Integer tmp = null;
        Integer temp = null;

        ArrayList<Integer> used = new ArrayList<Integer>();

        Scanner sc = new Scanner(System.in);
        Integer N = sc.nextInt();

        Integer[][] mp = new Integer[N + 1][N + 1];
        Integer[][] wp = new Integer[N + 1][N + 1];

        while(sc.hasNextInt()) {

            temp = sc.nextInt();
            if(!used.contains(temp)) {
                used.add(temp);
                for(int i = 0; i < N; i++){
                    tmp = sc.nextInt();          
                    wp[temp][tmp] = tmp;
                }
            } else {
                for(int i = 0; i < N; i++){
                    tmp = sc.nextInt();
                    wp[temp][tmp] = tmp;
                }
            }
        }
        sc.close();

        findMatch(N, mp, wp);

        for(int i = 0; i < N; i++) System.out.println(coupple.get(i));
    }
}