package Thread;

import java.util.concurrent.Callable;

public class CallerB implements Callable {
    @Override
    public String call() throws Exception {
        for (int i = 0; i < 10; ++i) {
            System.out.println("线程 " + Thread.currentThread().getName() + ", =======> " + i);
        }
        return "Caller B";
    }
}
