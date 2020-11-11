package Thread;

import java.util.concurrent.*;

/**
 * 1. 基本线程的用法
 * 2. 带返回值的线程用法
 *  Future 与 FutureTask 的区别：Task 是任务，可以多次执行
 * 3. Thread 可以当作是一个任务，直接 run，call 也行
 */
public class ThreadMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadA threadA = new ThreadA();

        CallerB callerB = new CallerB();
        FutureTask task = new FutureTask<>(callerB);
        new Thread(task).start();
        System.out.println("得到返回的结果1：" + task.get());

        ExecutorService es = Executors.newFixedThreadPool(1);
        Future<String> future = es.submit(callerB);
        System.out.println("得到返回的结果2：" + future.get());
    }
}
