//package example.java_learning.multithreading.read_write_lock;
//
//
//import example.java_learning.multithreading.stamped_lock.StampedSharedResource;
//
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
//public class ReadWriteLockMain {
//    static void main() {
//        StampedSharedResource stampedSharedResource = new StampedSharedResource();
//        ReadWriteLock lock = new ReentrantReadWriteLock();
//        Thread th1 = new Thread(() -> {
//            stampedSharedResource.producer(lock);
//        });
//
//
//        Thread th2 = new Thread(() -> {
//            stampedSharedResource.producer(lock);
//        });
//
//
//        StampedSharedResource stampedSharedResource1 = new StampedSharedResource();
//
//
//        Thread th3 = new Thread(() -> {
//            stampedSharedResource1.consumer(lock);
//        });
//
//        th1.start();
//        th2.start();
//        th3.start();
//
//
//    }
//}
