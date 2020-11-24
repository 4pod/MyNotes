package demo1124;

/*
 * 多个线程同时读取一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行
 * 但是
 * 如果右一个线程在写共享资源，其他线程不能对它读或者写
 *
 * 即：
 *       读-读   共存
 *       读-写   不能共存
 *       写-读   不能共存
 *
 *
 *      写操作：原子+独占，整个过程必须是一个完整的统一体，中间不允许被分割，被打断
 * */

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class myCache {
    private volatile Map<String, Object> map = new HashMap<>();//模拟缓存，加volatile保证可见性

    //===================对比来看
    //1.不加锁的情况
    public void put1(String key, Object value) {//写
        System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "\t 写入完成");
    }

    public void get1(String key) {//写
        System.out.println(Thread.currentThread().getName() + "\t 正在读取");
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object res = map.get(key);
        System.out.println(Thread.currentThread().getName() + "\t 读取完成" + res);
    }

    //2.加锁的情况，为了增加细粒度，读写分离，使用读写锁
    ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

    public void put2(String key, Object value) {//写
        rwlock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入" + key);
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlock.writeLock().unlock();
        }

    }

    public void get2(String key) {//写
        rwlock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取");
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object res = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成" + res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rwlock.readLock().unlock();
        }

    }

}

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        myCache myCache = new myCache();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put2(finalI + "", finalI + "");
                myCache.get2(finalI + "");
            }, String.valueOf(i)).start();
        }
    }
}
