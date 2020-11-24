package demo1124;

import java.util.concurrent.CountDownLatch;

//      锁门案例
// 和
//      秦灭六国案例
enum CountryEnum {
    ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");
    private Integer retCode;
    private String retMsg;



    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    CountryEnum(Integer retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public static CountryEnum getMsg(int index) {
        CountryEnum[] myArray = CountryEnum.values();
        for (CountryEnum item : myArray) {
            if (index == item.getRetCode()) return item;
        }
        return null;
    }
}

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
//        closeDoor();
        CountDownLatch countDownLatch = new CountDownLatch(6);//6个线程（同学）
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 国，被灭");
                countDownLatch.countDown();
            }, CountryEnum.getMsg(i).getRetMsg()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 秦国，统一华夏");
    }

    private static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);//6个线程（同学）
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 离开教室");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t 班长锁门离开");
    }
}
