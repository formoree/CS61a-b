package Week1;

public class e2 {
    public static void max(int[] m){
        int max = 0;
        for (int i:m) {
            if(i > max){
                max = i;
            }
        }
        System.out.println(max);
    }
    public static void main(String[] args) {
        int[] numbers = new int[]{1,2,3,4,5,8,7,9};
        max(numbers);
    }
}
