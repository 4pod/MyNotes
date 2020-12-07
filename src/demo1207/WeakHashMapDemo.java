package demo1207;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapDemo {
    public static void main(String[] args) {
        myHashMap();
        System.out.println("========================");
        myWeakHashMap();
    }

    private static void myWeakHashMap() {
        Map<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(2);
        String value = "myWeakHashMap";
        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }

    private static void myHashMap() {
        Map<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "myHashMap";
        map.put(key, value);
        System.out.println(map);

        key = null;
        System.out.println(map);

        System.gc();
        System.out.println(map);
    }
    /*
    *   {1=myHashMap}
        {1=myHashMap}
        {1=myHashMap}
        ========================
        {2=myWeakHashMap}
        {2=myWeakHashMap}
        {}
    *
    * */
}
