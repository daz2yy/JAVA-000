package Synchronized;

/**
 * 1. synchronized 作用在方法上，对象是 this，和作用在代码块的 this 一样的效果
 * 2. synchronized 对象是 this 的时候，sleep 会释放该锁；
 * 3. 一般情况下，sleep 是不会释放锁的
 */
public class SyncMethodAndCode {
    public synchronized void print1() throws InterruptedException {
        for (int i = 0; i < 5; ++i) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            Thread.sleep(500L);
        }
    }

    public void print2() throws InterruptedException {
        synchronized(this) {
            for (int i = 0; i < 5; ++i) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
                Thread.sleep(500L);
            }
        }
    }

    public static void main(String[] args) {
        SyncMethodAndCode object = new SyncMethodAndCode();
        new Thread(() -> {
            try {
                object.print1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "threadA").start();
        new Thread(() -> {
            try {
                object.print2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "threadB").start();
    }
}