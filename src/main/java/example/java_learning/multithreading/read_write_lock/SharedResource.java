package example.java_learning.multithreading.read_write_lock;

import java.util.concurrent.locks.ReadWriteLock;

public class SharedResource {
    boolean isAvailable = false;

    public void producer(ReadWriteLock lock) {
        try {
            lock.readLock().lock();
            System.out.println("Read Lock Acquired by : " + Thread.currentThread().getName());
//            isAvailable = true;  //only read is performed in sharedlock

            System.out.println("isAvailable : " + isAvailable);
            Thread.sleep(10000);

        } catch (Exception e) {
            Thread.currentThread().interrupt();
//            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
            System.out.println("Read Lock released by : " + Thread.currentThread().getName());
        }
    }


    public void consumer(ReadWriteLock lock) {
        try {
            lock.writeLock().lock();
            System.out.println("Write Lock Acquired by : " + Thread.currentThread().getName());
            isAvailable = true;
//            Thread.sleep(10000);

        } catch (Exception e) {
            ///
        } finally {
            lock.writeLock().unlock();
            System.out.println("Write Lock released by : " + Thread.currentThread().getName());
        }
    }
}
