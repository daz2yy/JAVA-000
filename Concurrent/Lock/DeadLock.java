package Lock;

public class DeadLock {
    public static void main(String[] args) {
        String a = "lockA";
        String b = "lockB";
        new ThreadA(a, b).start();
        new ThreadA(b, a).start();
        System.out.println("Main Thread End.");
    }
}

class ThreadA extends Thread {
    Object lockA, lockB;
    public ThreadA(Object lockA, Object lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }
    @Override
    public void run() {
        synchronized (this.lockA) {
            try {
                System.out.println(Thread.currentThread().getName() + " => " + lockA);
                sleep(1000L);
                synchronized (this.lockB) {
                    System.out.println(Thread.currentThread().getName() + " => " + lockB);
                }
                System.out.println(Thread.currentThread().getName() + " => release " + lockB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " => release " + lockA);
    }
}
