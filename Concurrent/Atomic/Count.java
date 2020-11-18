package Atomic;

import Synchronized.Counter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. synchronized 不能修饰变量，只能是方法和代码块
 */
public class Count {
    public static void main(String[] args) {
//        AtomicCounter counter = new AtomicCounter();
        SyncCounter counter = new SyncCounter();
//        Counter counter = new Counter();
        for (int i = 0; i < 10; ++i) {
            new Thread(() -> {
                for (int j = 0; j < 10000; ++j) {
                    counter.add();
                }
            }).start();
        }

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Last Value: " + counter.get());
    }
}

/**
 * 原始类型
 */
class NormalCounter {
    private int num = 0;

    public void add() {
        num++;
    }

    public int get() {
        return num;
    }
}

/**
 * 原子类计数器
 */
class AtomicCounter {
    private AtomicInteger num = new AtomicInteger(0);

    public void add() {
        num.getAndIncrement();
    }

    public int get() {
        return num.get();
    }
}

/**
 *
 */
class SyncCounter {
    private int num = 0;

    public synchronized void add() {
        num++;
    }

    public int get() {
        return num;
    }
}