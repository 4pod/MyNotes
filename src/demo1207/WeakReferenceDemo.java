package demo1207;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class WeakReferenceDemo {
    public static void main(String[] args) {
        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());

        o1 = null;
        System.gc();

        System.out.println(o1);
        System.out.println(weakReference.get());

        /*
        java.lang.Object@1b6d3586
        java.lang.Object@1b6d3586
        null
        null
        */
//        Map<String, SoftReference<Bitmap>> imageCache = new HashMap<>();
    }
}
