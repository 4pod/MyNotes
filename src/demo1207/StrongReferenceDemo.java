package demo1207;

public class StrongReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = o1;
        o1 = null;
        System.gc();//o1被回收
        System.out.println(o2);
    }
}
