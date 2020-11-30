package demo1130;
//死锁案例，产生死锁的原因：两个或者多个线程争抢资源，相互等待的情况，若没有外力干涉，将永远互相等待下去

import java.util.concurrent.TimeUnit;

class HoldThread implements Runnable {
    private String lockA;
    private String lockB;

    public HoldThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }


    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 得到了:" + lockA + "，尝试获得:" + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t 得到了:" + lockB + "，尝试获得:" + lockA);
            }
        }
    }
}

public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new HoldThread(lockA, lockB), "AAA").start();
        new Thread(new HoldThread(lockB, lockA), "BBB").start();
        //死锁定位：jps （java版本的ps -ef | grep xxx）
        /*D:\IdeaProjects\workspace\MyNotes>jps
        13076 DeadLockDemo
        17988
        8724 Jps
        23256
        23196 Launcher
        24636 jar*/

        //死锁分析：jstack 线程号
        /*Java stack information for the threads listed above:
        ===================================================
        "BBB":
        at demo1130.HoldThread.run(DeadLockDemo.java:26)
        - waiting to lock <0x00000000d66be7f8> (a java.lang.String)
        - locked <0x00000000d66be830> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:748)
        "AAA":
        at demo1130.HoldThread.run(DeadLockDemo.java:26)
        - waiting to lock <0x00000000d66be830> (a java.lang.String)
        - locked <0x00000000d66be7f8> (a java.lang.String)
        at java.lang.Thread.run(Thread.java:748)
        Found 1 deadlock.
        */

    }
}
