package Lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 总结：
 * 分两种情况，一种是直接读取数据返回，通过 finally 释放读锁
 * 另一种情况是，没有值，先释放读锁加写锁再判断是否已写入，再写入；
 *      最后记得加上读锁，因为再最外层的时候我们加载一个读锁
 *      通过 finaly 来释放的
 */
public class ReadWriteData {
    
    final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private Map<String, Object> map = new HashMap<>();
    
    public static void main(String[] args) {
        ReadWriteData object = new ReadWriteData();
        Object value = object.readWrite("key1");
        System.out.println("value: " + value);
    }
    
    public Object readWrite(String key) {
        lock.readLock().lock();
        Object object = null;
        try {
            object = map.get(key);
            if (object == null) {
                lock.readLock().unlock();
                lock.writeLock().lock();
                try {
                    if (object == null) {
                        object = "abc";
                    }
                } finally {
                    lock.writeLock().lock();
                }
                lock.readLock().lock();
            }
        } finally {
            lock.readLock().unlock();
        }
        return object;
    }
}

