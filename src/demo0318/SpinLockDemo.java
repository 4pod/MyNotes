package demo0318;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

//自旋锁
//有两个线程，共享一份资源，同一时刻只能一人独占
class data3 {
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void myLock() throws InterruptedException {
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName() + "\t come in lock()!");
        TimeUnit.SECONDS.sleep(3);
        while (atomicReference.compareAndSet(null, thread)) {
            //CAS,当当前线程是null，期望值也是null时，更新为thread
            System.out.println(Thread.currentThread().getName() + "\t 抢到了!");
        }
    }

    public void myUnlock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in unlock()");
        atomicReference.compareAndSet(thread, null);
    }
}

public class SpinLockDemo {
    public static void main(String[] args) throws InterruptedException {
        data3 data3 = new data3();
        new Thread(() -> {
            try {
                data3.myLock();
                TimeUnit.MILLISECONDS.sleep(300);
                data3.myUnlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AAA").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                data3.myLock();
                TimeUnit.MILLISECONDS.sleep(300);
                data3.myUnlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BBB").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            try {
                data3.myLock();
                TimeUnit.MILLISECONDS.sleep(300);
                data3.myUnlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "CCC").start();
    }
}
