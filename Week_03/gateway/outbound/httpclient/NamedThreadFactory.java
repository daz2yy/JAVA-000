package gateway.outbound.httpclient;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 指定前缀的线程工厂
 */
public class NamedThreadFactory implements ThreadFactory {

    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final String namePrefix;
    private final boolean daemon;

    /**
     *
     * @param namePrefix 线程前缀
     * @param daemon 是否是守护进程
     */
    public NamedThreadFactory(String namePrefix, boolean daemon) {
        this.namePrefix = namePrefix;
        this.daemon = daemon;

        // 获取线程组？
        SecurityManager manager = System.getSecurityManager();
        group = (manager != null) ? manager.getThreadGroup()
                : Thread.currentThread().getThreadGroup();
    }

    public NamedThreadFactory(String namePrefix) {
        this(namePrefix, false);
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r, namePrefix + "-thread-" + threadNumber.getAndIncrement(), 0);
        t.setDaemon(this.daemon);
        return t;
    }
}
