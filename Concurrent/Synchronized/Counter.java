package Synchronized;

/**
 * 1. 多线程情况下，会出现共享变量复制被覆盖
 * 2. volatile 能保证可见性，但是无法保证原子操作
 */
public class Counter {
    private int sum1;
    private volatile int sum2;

    public void add() {
//    public synchronized void add() {
//        sum1++;
        sum1 = sum1 + 1;
//        sum2++;
    }
    public int get() {
        return sum1;
    }

    public static void main(String[] args) throws InterruptedException {
        int loop = 100000;
        Counter c1 = new Counter();

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < loop; ++i) {
                    c1.add();
                }
            }
        });
        threadA.start();

        Counter c2 = new Counter();
        Thread threadB1 = new Thread(() -> {
            for (int i = 0; i < loop/2; ++i) {
                c2.add();
            }
        });
        Thread threadB2 = new Thread(() -> {
            for (int i = 0; i < loop/2; ++i) {
                c2.add();
            }
        });
        threadB1.start();
        threadB2.start();

        threadA.join();
        threadB1.join();
        threadB2.join();
        System.out.println("c1: " + c1.get());
        System.out.println("c1 volatile: " + c1.sum2);
        System.out.println("c2: " + c2.get());
        System.out.println("c2 volatile: " + c2.sum2);
    }
}

