package Week1;

public class e1a {
    public static void main(String[] args) {
        int col  = 1;
        while(col < 6){
            int num = col;
            while(num > 0){
                System.out.print("*");
                num--;
            }
            System.out.println("");
            col++;
        }
    }
}
