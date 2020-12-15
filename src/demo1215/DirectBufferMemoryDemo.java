package demo1215;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class DirectBufferMemoryDemo {
    public static void main(String[] args) {
        System.out.println("配置的maxdirectmemory:" + sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024 + "MB");
        ByteBuffer bb = ByteBuffer.allocateDirect(6 * 1024 * 1024);
    }
}
