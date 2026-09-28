package example.java_learning.multithreading.semaphore_lock;

public class SemaphoreLockMain {

    static void main(String[] args) {

        SemaphoreShareResource resource = new SemaphoreShareResource();


        Thread th1 = new Thread(() -> {
            resource.accessResource();
        });


        Thread th2 = new Thread(() -> {
            resource.accessResource();
        });


        Thread th3 = new Thread(() -> {
            resource.accessResource();
        });


        Thread th4 = new Thread(() -> {
            resource.accessResource();
        });


        th1.start();
        th2.start();
        th3.start();
        th4.start();
    }
}
