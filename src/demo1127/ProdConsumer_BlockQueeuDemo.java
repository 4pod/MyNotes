package demo1127;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//volatile+CAS+atomicInteger+BlockingQueue+线程交互
class ShareData {
    private volatile boolean flag = true;//默认开启生产+消费,使用volatile,实现操作可见性
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;//初始化为null，是为了实现接口传参，增加适配性

    public ShareData(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName() + "\t 这个实现类！");
    }

    //生产方法
    public void prod() throws Exception {
        String str = null;
        boolean res;
        while (flag) {
            str = atomicInteger.incrementAndGet() + "";
            res = blockingQueue.offer(str, 2L, TimeUnit.SECONDS);

            if (res) {
                System.out.println(Thread.currentThread().getName() + "\t向生产队列添加了" + str);
            } else {
                System.out.println(Thread.currentThread().getName() + "\t向生产队列添加失败");

            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println("现在flag为false，生产操作停止！");
    }

    public void cons() throws Exception {
        String res = null;
        while (flag) {
            res = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == res || res.equalsIgnoreCase("")) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t退出！");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t消费了" + res);
        }
        System.out.println("FLAG为FALSE，老板叫停，消费也结束");
    }

    public void changeFlag() {
        flag = false;
    }

}

public class ProdConsumer_BlockQueeuDemo {
    public static void main(String[] args) throws InterruptedException {
        ShareData shareData = new ShareData(new ArrayBlockingQueue<String>(10));
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "开始生产！");
                shareData.prod();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "AAA").start();
        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "开始消费！");
                shareData.cons();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "BBB").start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println();
        System.out.println();
        shareData.changeFlag();

    }
}
