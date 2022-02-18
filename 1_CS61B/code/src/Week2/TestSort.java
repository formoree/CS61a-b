package Week2;

import org.junit.Test;

public class TestSort {
    /*Test a java file -- sort*/
    @Test
    //method should not be static
    public void testsort(){
        String[] input = new String[]{"i"," have"," an","egg"};
        String[] expect = new String[]{"i"," have"," an","egg"};

        Sort.sort(input);
//        for(int i = 0;i < input.length;i++){
//            if(input[i] != expect[i]){
//                System.out.println("The position"+ i + "is different" + "it should be " + input[i]);
//            }
//        }

        /* 上面一段可以替换为*/
        org.junit.Assert.assertArrayEquals(input,expect);
    }
}
