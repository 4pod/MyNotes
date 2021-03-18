package demo0318;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

//模拟缓存，能并发的读，提升并发性，读写 写读 写写  不能 共存，读读可以共存
//写操作必须是原子操作
class data2 {
    //保证可见性
    volatile Map<String, Object> map = new HashMap<>();
    //    Lock lock = new ReentrantLock();
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void myWrite(String key, Object value) throws InterruptedException {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t set");
            TimeUnit.SECONDS.sleep(1);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t set完成" + value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void myRead(String key) {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t get");
            TimeUnit.MILLISECONDS.sleep(500);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t get结果" + o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }

    }
}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        data2 data2 = new data2();
        for (int i = 1; i <= 5; i++) {
            if(i%2==1){
                int finalI = i;
                new Thread(() -> {
                    try {
                        data2.myWrite(finalI + " ", finalI + " ");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, String.valueOf(i)).start();
            }
            else {
                int finalI = i-1;
                new Thread(() -> {
                    data2.myRead(finalI + " ");
                }, String.valueOf(i)).start();
            }
        }

//        for (int i = 1; i <= 5; i++) {
//            int finalI = i;
//            new Thread(() -> {
//                data2.myRead(finalI + " ");
//            }, String.valueOf(i)).start();
//        }
    }
}
