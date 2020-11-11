package 周四;

import java.util.concurrent.*;

/**
 * 思考有多少种方式，在 main 函数启动一个新线程，运行一个方法，拿到这个方法的返回值后，退出主线程？
 * Thread(Runnable, return)
 * Future
 */
public class Homework {
    public static String result = "Static Var";

    static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // FutureTask
        TestThread1 caller = new TestThread1();
        FutureTask task = new FutureTask(caller);
        new Thread(task).start();
        System.out.println("第一种方法返回结果：" + task.get());

        ExecutorService threadPoll = Executors.newSingleThreadExecutor();
        Future<String> future1 = threadPoll.submit(caller);
        System.out.println("第二种方法返回结果：" + future1.get());

        Future<String> future2 = threadPoll.submit(new Runnable() {
            @Override
            public void run() {
                return;
            }
        }, "Runnable Return");
        System.out.println("第三种方法返回结果：" + future2.get());

        Thread thread4 = new Thread(()-> {
            Homework.result = "Number Four!";
        });
        thread4.start();
        thread4.join();
        System.out.println("第四种方法返回结果：" + Homework.result);

        threadPoll.shutdown();
    }
}

class TestThread1 implements Callable<String> {
    @Override
    public String call() throws Exception {
        return "TestThread1";
    }
}
