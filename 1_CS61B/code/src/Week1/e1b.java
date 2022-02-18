package Week1;

public class e1b {
    public static void main(String[] args) {
        DrawTriangle(7);
    }

    public static void DrawTriangle(int n) {
        int col  = 1;
        while(col < n){
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
