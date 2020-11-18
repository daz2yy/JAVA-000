package ObjectOP;

import java.io.IOException;

/**
 * 用一个数字来当作队列即可
 * 注意点：
 * 1. synchronized 修饰方法
 */
public class WaitAndNotify {
    public static void main(String[] args) throws IOException {
        MethodClass q = new MethodClass();
        Thread thread1 = new Thread(() -> {
            try {
                q.product();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                q.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        System.in.read();
        System.out.println("Main Thread is End!");
    }
}

class MethodClass {
    private final int MAX_COUNT = 20;
    int productCount = 0;

    public synchronized void consumer() throws InterruptedException {
        while (true) {
            if (productCount == 0) {
                System.out.println("货舱已空，无需搬货，休息中……");
                this.wait();
            }
            System.out.println("搬运货物：" + productCount);
            productCount--;
            Thread.sleep(10);

            notifyAll();
        }
    }

    public synchronized void product() throws InterruptedException {
        while (true) {
            if (productCount >= MAX_COUNT) {
                System.out.println("货舱已满，无需生产，休息中……");
                this.wait();
            }
            Thread.sleep(10);
            productCount++;
            System.out.println("生产货物：" + productCount);

            notifyAll();
        }
    }
}
