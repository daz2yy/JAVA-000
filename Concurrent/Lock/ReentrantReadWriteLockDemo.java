package Lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * 读锁的时候，其它线程无法写，可以读
 * 写锁的时候，其它线程无法读写
 * 感悟：
 * 读写操作的四种状态：读读、读写、写读、写写
 * 优化了读读，就等于优化了 25% 的性能。
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        int nThreadNum = 5;
        Counter2 counter = new Counter2();
        counter.lock.writeLock().lock();
        try {
            for (int i = 0; i < nThreadNum; ++i) {
                new Thread(() -> {
                    counter.read();
                }).start();
            }
            for (int i = 0; i < nThreadNum; ++i) {
                new Thread(() -> {
                    counter.write();
                }).start();
            }
        } finally {
            counter.lock.writeLock().unlock();
        }
        System.out.println("Main Thread End.");
    }
    
    
}

class Counter2 {
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    
    public void read() {
        lock.readLock().lock();
        try {
            System.out.println("read() ======= bbbb");
            sleep(1000L);
            System.out.println("read() ======= eeee");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }
    
    public void write() {
        lock.writeLock().lock();
        try {
            System.out.println("write() ------- b");
            sleep(1000L);
            System.out.println("write() ------- e");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}