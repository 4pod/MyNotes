package demo1126;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 * CyclicBarrier让一组线程到达一个屏障（或者叫同步点时阻塞），
 * 直到最后一个线程到达屏障，屏障才会释放，所有被拦截的线程才会继续干活，
 * 线程进入屏障调用CyclicBarrier的awati()方法
 *
 *
 *      简称：集齐七龙珠召唤神龙
 * */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //public CyclicBarrier(int parties, Runnable barrierAction)


        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
            System.out.println("召唤神龙");
        });
        for (int i = 0; i < 7; i++) {
            int tempInt = i+1;
            new Thread(() -> {
                System.out.println("第" + tempInt + "颗龙珠被收集到");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i + 1)).start();
        }
    }
}
