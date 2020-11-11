package Synchronized;

/**
 * 1. synchronized 锁住对象，该对象在其它线程里还是能正常使用的，只要不是使用到同步快的代码
 * 2. sleep 是不会释放对象锁的，除非刚好是用了 this，公用了头部字段
 */
public class SyncObject {

    private final static Object lock = new Object();
    class Inner {
        private void m4t1() {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : Inner.m4t1()=" + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }

        private void m4t2() {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : Inner.m4t2()=" + i);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ie) {
//                }
            }
        }

        private void m4t3() {
            int i = 5;
            while (i-- > 0) {
                System.out.println(Thread.currentThread().getName() + " : Inner.m4t3()=" + i);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException ie) {
//                }
            }
        }
    }

    private void m4t1(Inner inner) {
        synchronized (inner) { //使用对象锁
            inner.m4t1();
        }
    }

    private void m4t2(Inner inner) {
        inner.m4t2();
    }

    private void m4t3(Inner inner) {
        synchronized (inner) { //使用对象锁
            inner.m4t3();
        }
    }

    public static void main(String[] args) {
        final SyncObject myt3 = new SyncObject();
        final Inner inner = myt3.new Inner();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                myt3.m4t1(inner);
            }
        }, "t11111");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                myt3.m4t2(inner);
            }
        }, "t22222");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                myt3.m4t3(inner);
            }
        }, "t33333");
        t1.start();
        t2.start();
        t3.start();
    }
}
