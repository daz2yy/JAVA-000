package Lock;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

/**
 * ？？？ 没有跑成功
 * 思考：
 * Thread.sleep 与 Object.wait 的区别
 * Thread.sleep 与 Condition.wait 的区别
 * Thread.sleep 与 LockPark.park 的区别
 * Object.wait 与 LockPark.park 的区别
 * 参考：https://blog.csdn.net/tangtong1/article/details/102829724
 */
public class LockParkDemo {
    private static Object lock = new Object();
    static Thread t1 = new TestThread("ThreadA");
    static Thread t2 = new TestThread("ThreadB");
    
    static class TestThread extends Thread {
        public TestThread(String name) {
            super(name);
        }
    
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " =======> get lock");
                LockSupport.park();
                //???
                if (Thread.interrupted()) {
                    System.out.println(Thread.currentThread().getName() + " =======> is interrupt!");
                }
                System.out.println(Thread.currentThread().getName() + " =======> continue");
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        t1.start();
        sleep(1000L);
        t2.start();
        sleep(3000L);
        LockSupport.unpark(t2);
        t1.join();
        t2.join();
    }
}

