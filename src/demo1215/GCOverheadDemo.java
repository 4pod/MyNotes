package demo1215;

import java.util.ArrayList;
import java.util.List;

public class GCOverheadDemo {
    public static void main(String[] args) {
        int i = 0;
        List<String> list = new ArrayList<>();
        while (true) {
            list.add(String.valueOf(++i).intern());//Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
        }
    }
}
