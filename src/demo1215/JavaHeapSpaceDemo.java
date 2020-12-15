package demo1215;

import java.util.Random;

public class JavaHeapSpaceDemo {
    public static void main(String[] args) {
        byte[] bytes = new byte[80 * 1024 * 1024];//Exception in thread "main" java.lang.OutOfMemoryError: Java heap space

        String str = "abc";
        while (true) {
            str += str + new Random().nextInt(8888888) + new Random().nextInt(66666666);
            //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
//            str.intern();
        }
    }
}
