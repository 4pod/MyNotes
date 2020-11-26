package demo1126;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

//阻塞队列同步版,不存储元素，生产一个消费一个
//阻塞队列的好处是   在满足某些条件时，自动地阻塞线程，在满足某些条件时，自动地唤醒线程（从手动管理到自动管理）
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t put 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "\t put 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "\t put 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                String res1 = blockingQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take" + res1);
                TimeUnit.SECONDS.sleep(3);
                String res2 = blockingQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take" + res2);
                TimeUnit.SECONDS.sleep(3);
                String res3 = blockingQueue.take();
                System.out.println(Thread.currentThread().getName() + "\t take" + res3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
    }
}
