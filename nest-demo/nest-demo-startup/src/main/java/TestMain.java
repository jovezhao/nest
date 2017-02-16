/**
 * Created by zhaofujun on 2017/2/16.
 */
public class TestMain {
    public static void main(String[] args){
        int a=1;
        int b=2;
        int c=a+b;

        System.out.print(c);
        int d=add(a,c);
        System.out.println(d);
    }
    private static int add(int a, int b){
        return a+b;
    }
}
