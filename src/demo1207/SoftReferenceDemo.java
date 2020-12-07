package demo1207;

import java.lang.ref.SoftReference;

public class SoftReferenceDemo {
    //软引用：JVM内存足够时，不会被垃圾回收，不够时，才会被垃圾回收
    public static void memory_enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        o1 = null;
        System.gc();
        System.out.println("====================");
        System.out.println(o1);
        System.out.println(softReference.get());
        /*
        * ====================
            null
            java.lang.Object@1b6d3586
        * */
    }

    public static void memory_not_enough() {
        Object o1 = new Object();
        SoftReference<Object> softReference = new SoftReference<>(o1);
        o1 = null;
        System.gc();
        try {
            Byte[] bytes = new Byte[500 * 1024 * 1024];
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("====================");
            System.out.println(o1);
            System.out.println(softReference.get());
            /*
            * ====================
            null
            null
            Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
                at demo1207.WeakReferenceDemo.memory_not_enough(WeakReferenceDemo.java:23)
                at demo1207.WeakReferenceDemo.main(WeakReferenceDemo.java:36)
            * */
        }
    }

    public static void main(String[] args) {

        memory_enough();
//        memory_not_enough();
    }
}
