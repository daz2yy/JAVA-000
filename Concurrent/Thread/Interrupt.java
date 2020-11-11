package Thread;

/**
 * 中断：发送一个信号量给线程，设置线程的中断标志为 true，只是改变状态，不会强制中断。
 * 1. 可以用户去检查状态，并处理
 * 2. 线程如果在 Object.wait, Thread.sleep(), Thread.join() 状态中，就会被打断，抛出 InterruptedException 异常
 */
public class Interrupt {
    public static void main(String[] args) throws InterruptedException {

        Runner2 runner2 = new Runner2();
        Thread thread2 = new Thread(runner2);
        thread2.start();

        Thread.sleep(500L);
        thread2.interrupt();
//        thread2.join();
    }

    public static class Runner2 implements Runnable {
        @Override
        public void run() {
            boolean result1 = Thread.currentThread().isInterrupted();
            boolean result2 = Thread.interrupted();
            boolean result3 = Thread.currentThread().isInterrupted();

            System.out.println("Runner2.run result1: " + result1);
            System.out.println("Runner2.run result2: " + result2);
            System.out.println("Runner2.run result3: " + result3);

            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
