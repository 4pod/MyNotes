package demo1127;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

class MyData {
    private volatile boolean FLAG = true;//默认是true，生产一个消费一个，保证可见性，使用volatile
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;//右边不写，是为了在构造方法中传入BlockQueue接口，这样可以满足很多情况

    public MyData(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    //生产方法
    public void myProd() throws Exception {
        String temp = null;
        boolean retCode;
        while (FLAG) {
            temp = atomicInteger.incrementAndGet() + "";
            retCode = blockingQueue.offer(temp, 2L, TimeUnit.SECONDS);
            if (retCode) {
                System.out.println(Thread.currentThread().getName() + "\t 将" + temp + "添加到队列中");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("FLAG为FALSE，老板叫停，生产结束");
    }

    public void myConsumer() throws Exception {
        String res = null;
        while (FLAG) {
            res = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (res == null || res.equalsIgnoreCase("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过两秒钟没取到，退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 将" + res + "从队列中取出");
        }
        System.out.println("FLAG为FALSE，老板叫停，消费也结束");
    }

    public void changeFlag() {
        FLAG = false;
    }
}

public class ProdConsumer_BlockQueueDemo2 {
    public static void main(String[] args) throws InterruptedException {
        MyData myData = new MyData(new ArrayBlockingQueue<>(10));
//        MyData myData = new MyData(new SynchronousQueue<>());
//        MyData myData = new MyData(new LinkedBlockingDeque<>());
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t开始生产");
                myData.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AAA").start();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "\t开始消费");
                myData.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BBB").start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println();
        System.out.println();
        myData.changeFlag();
    }
}

