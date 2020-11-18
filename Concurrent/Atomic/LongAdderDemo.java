package Atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class LongAdderDemo {
    public static void main(String[] args) throws InterruptedException {
        final LongAdder longAdder = new LongAdder();
        final AtomicLong atomic = new AtomicLong(0);

        for (int i = 0; i < 10; ++i) {
            new Thread(() -> {
                for (int j = 0; j < 10000; ++j) {
                    longAdder.increment();
                    atomic.getAndIncrement();
                }
            }).start();
        }

        Thread.sleep(1000L);

        System.out.println("LongAdder: " + longAdder);
        System.out.println("AtomicLong: " + atomic.get());
    }
}
