package demo0318;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//可重入锁，获得外层锁，自动获得内层锁
class data1 implements Runnable {
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
        get();
    }

    private void get() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t come in get()!");
            set();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private void set() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t come in set()!");
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class ReentrantLockDemo {
    public static void main(String[] args) {
        data1 data1 = new data1();

        new Thread(() -> {
            data1.run();
        }, "AAA").start();
        new Thread(() -> {
            data1.run();
        }, "BBB").start();
    }
}
