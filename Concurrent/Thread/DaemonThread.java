package Thread;

/**
 * 1. 后台线程的使用方式
 * 2. 如果只剩下后台线程，JVM就会认为系统运行结束了
 */
public class DaemonThread {
    public static void main(String[] args) {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread runing ===============");
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        System.out.println("Finish ===============");
    }
}
