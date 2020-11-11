package Thread;

/**
 * 1. main 线程组最少有 2个线程在跑
 * 2. 系统线程组有 4 个线程在跑
 *  Reference   引用关系？
 *  Finalizer   gc回收？
 *  Signal      信号分发，发给 Java 业务处理
 *  Attach      ？
 */
public class RunnerMain {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=========> 线程数量: " + Thread.currentThread().getThreadGroup().activeCount());
        System.out.println("=========> list1 ");
        Thread.currentThread().getThreadGroup().list();
        System.out.println("\n\n=========> parent list2 ");
        Thread.currentThread().getThreadGroup().getParent().list();
    }

}
