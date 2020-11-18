package ObjectOP;

import static java.lang.Thread.sleep;

/**
 * 1. join 的基础使用
 * 2. 特殊：synchronized 锁线程对象的时候，thread.join 会释放锁资源
 * 3. 一般情况下，sleep 不会释放锁？
 */
public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new MyThread();
        thread.start();

        synchronized (thread) {
            for (int i = 0; i < 10; ++i) {
                if (i == 5) {
                    thread.join();
                }
                sleep(10);
                System.out.println(Thread.currentThread().getName() + "======> " + i);
            }

        }

        // 线程状态不能恢复到 start，
        // thread.start();

        System.out.println("Main Thread is end!");
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        synchronized (this) {
            for (int i = 0; i < 20; ++i) {
                System.out.println("==========> MyThread run " + i);
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}