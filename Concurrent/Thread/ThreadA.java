package Thread;

public class ThreadA implements Runnable {
    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        String currentThreadName = thread.getName();
        System.out.println("这是线程的名称：" + currentThreadName);
        System.out.println("返回当前线程" + currentThreadName + "的线程组中活动线程的数量:" + thread.getThreadGroup().activeCount());
        System.out.println("返回该线程" + currentThreadName + "的标识符:" + thread.getId());
        System.out.println("返回该线程" + currentThreadName + "的优先级:" + thread.getPriority());
        System.out.println("返回该线程" + currentThreadName + "的状态:" + thread.getState());
        System.out.println("返回该线程" + currentThreadName + "所属的线程组:" + thread.getThreadGroup());
        System.out.println("测试该线程" + currentThreadName + "是否处于活跃状态:" + thread.isAlive());
        System.out.println("测试该线程" + currentThreadName + "是否为守护线程:" + thread.isDaemon());
    }
}
