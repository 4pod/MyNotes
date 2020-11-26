package demo1126;
//N个线程来抢占M个资源


import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

//6辆车抢3个车位
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();//抢占到资源
                    System.out.println(Thread.currentThread().getName() + "\t 抢到了车位!");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + "\t停车3秒钟离开车位！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();//离开车位释放资源
                }
            }, String.valueOf(i)).start();
        }
    }
}
