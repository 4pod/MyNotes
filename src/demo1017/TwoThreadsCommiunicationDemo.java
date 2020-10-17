package demo1017;

        /*
         * 两个线程操作同一个初始值为0的变量，一个线程+1，另一个-1，来10轮
         * */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class myShareData {
    private int number = 0;

    //老版本写法
    public synchronized void add() throws Exception {
        while (number != 0)
            this.wait();
        System.out.println(Thread.currentThread().getName() + "\t" + (number) + ",现在为:" + (++number));
        this.notifyAll();
    }

    public synchronized void jian() throws Exception {
        while (number != 1)
            this.wait();
        System.out.println(Thread.currentThread().getName() + "\t" + (number) + ",现在为:" + (--number));
        this.notifyAll();
    }

    //新版本写法
    private Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();

    public void increment() {
        lock.lock();
        try {
            //1.判断
            while (number != 0) c1.await();
            //2.干活
            System.out.println(Thread.currentThread().getName() + "\t" + (number) + ",现在为:" + (++number));
            //3.通知
            c2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            //1.判断
            while (number != 1) c2.await();
            //2.干活
            System.out.println(Thread.currentThread().getName() + "\t" + (number) + ",现在为:" + (--number));
            //3.通知
            c1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class TwoThreadsCommiunicationDemo {
    public static void main(String[] args) {
        myShareData myShareData = new myShareData();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myShareData.add();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    myShareData.jian();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, "B").start();
    }
}
