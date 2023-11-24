import java.util.*;

public class QuickFindUF {
    private int[] id;

    public QuickFindUF(int n) {
        this.id = new int[n];
        for(int i = 0; i < n; i++) {
            this.id[i] = i;
        }
    }

    public void union(int a, int b) {
        for(int i = 0; i < this.id.length; i++) if (i == this.id[b]) this.id[i] = this.id[a];
        System.out.println(Arrays.toString(this.id));
    }

    public int root(int i) {
        return 0;
    }

    public boolean connected(int a, int b) { 
        return this.id[a] == this.id[b];
    }
    

    public String getComponents() {
        return Arrays.toString(this.id);
    }

    public static void main(String args[]) {
        Scanner keyb = new Scanner(System.in);
        System.out.print("Enter the number of nodes to create: ");
        int n = keyb.nextInt();
        keyb.close();
        QuickFindUF uf = new QuickFindUF(n);

         //tests
         uf.union(1,5);
         uf.union(1,7);
         uf.union(0,0);
         uf.union(2,8);
         uf.union(4,0);
         uf.union(3,0);
         System.out.println(uf.getComponents()); //
         System.out.println(uf.connected(1, 7)); //true
         System.out.println(uf.connected(1, 0)); //false;
         System.out.println(uf.connected(8, 3)); // false
 
         //output expected:
         // [[0, 4, 3], [6], [7, 5, 1], [8, 2], [9]] //or other as per d.s. used
         // true
         // false
         // false

    }
}
