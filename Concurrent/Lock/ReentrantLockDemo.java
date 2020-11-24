package Lock;

import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class ReentrantLockDemo {
    public static void main(String[] args) {
        int nThreadNum = 5;
        Counter1 counter = new Counter1();
        counter.lock.lock();
        try {
            for (int i = 0; i < nThreadNum; ++i) {
                new Thread(() -> {
                    counter.get();
                }).start();
            }
            for (int i = 0; i < nThreadNum; ++i) {
                new Thread(() -> {
                    counter.read();
                }).start();
            }
        } finally {
            counter.lock.unlock();
        }
        System.out.println("Main Thread End.");
    }
}

class Counter1 {
    final ReentrantLock lock = new ReentrantLock();
    public void get() {
        lock.lock();
        try {
            System.out.println("get() ========== bbbb");
            sleep(1000L);
            System.out.println("get() ========== eeee");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    
    public void read() {
        lock.lock();
        try {
            System.out.println("read() ------- b");
            sleep(1000L);
            System.out.println("read() ------- e");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
/**
 * 运行多次后可能出现的情况：交替在运行
 * get() ========== bbbb
 * get() ========== eeee
 * get() ========== bbbb
 * get() ========== eeee
 * read() ------- b
 * read() ------- e
 * get() ========== bbbb
 * get() ========== eeee
 * read() ------- b
 * read() ------- e
 * get() ========== bbbb
 * get() ========== eeee
 * read() ------- b
 * read() ------- e
 * read() ------- b
 * read() ------- e
 * read() ------- b
 * read() ------- e
 * get() ========== bbbb
 * get() ========== eeee
 */

