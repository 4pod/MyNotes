package demo1129;

import java.util.concurrent.*;

/*
 * 实现多线程的方法：
 *1.继承Threadl类。
 * 2.实现Runnable接口，没有返回值，不会抛出异常。
 * 3.实现Callabel接口，有返回值，会抛出异常。
 * 4.使用线程池。
 * */
//线程池Executor（接口）相当于Collection接口
//一般使用ExecutorService这个接口，相当于List接口
// Executors ThreadPoolExecutor（线程池底层就是这个类）
public class MyThreadPoolDemo {
    public static void main(String[] args) {
        ExecutorService threadPool1 = Executors.newFixedThreadPool(6);//一池6线程
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        //三者的底层全是这个类：ThreadPoolExecutor
        ExecutorService threadPool4 = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
//                new ThreadPoolExecutor.AbortPolicy());
//                new ThreadPoolExecutor.CallerRunsPolicy());
//                new ThreadPoolExecutor.DiscardOldestPolicy());
                new ThreadPoolExecutor.DiscardPolicy());
        /*public ThreadPoolExecutor(int corePoolSize,
                              int maximumPoolSize,
                              long keepAliveTime,
                              TimeUnit unit,
                              BlockingQueue<Runnable> workQueue,
                              ThreadFactory threadFactory,
                              RejectedExecutionHandler handler)*/
        /*ThreadPoolExecutor的七大参数
        1.corePoolSize:线程池中的常驻核心线程数，
        2.maximumPoolSize:线程池能够容纳同时执行的线程数，
        3.keepAliveTime:多余线程的存活时间
        4.TimeUnit：时间单位
        5.workQueue：阻塞队列，类似候客区
        6.threadFactory：线程工厂，使用默认的即可
        7.拒绝策略

        流程：新的请求线程来了，先看当前线程池中线程数量是否大于corePoolSize，若小于，直接开启新的线程处理请求
              若大于，需要看阻塞队列中排队的任务数量是否满了，若没满，将任务排进阻塞队列中
              若满了，需要看线程数量是否达到maximumPoolSize，若小于，需要扩容(经代码调试，每次扩容会+1个线程)
              若满了，则使用拒绝测率拒绝请求。
              当一个线程无事可做超过keepAliveTime，线程池会判断，如果当前线程数量超过corePoolSize，这个线程将被停掉，当线程池的所有任务完成后，它会收缩到corePoolSize大小

         不允许使用Executors创建线程，WHY?
         因为newFixedThreadPool，newSingleThreadExecutor的阻塞队列长度为Integer.Max_VALUE
         newCachedThreadPool的线程池容量为Integer.Max_VALUE，
         均会导致OOM
        * */
        //模拟10个用户来办理业务，每个用户是来自外部的请求线程
        try {
            for (int i = 0; i < 10; i++) {
                threadPool4.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "\t 办理业务！");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool4.shutdown();
        }
    }
}
