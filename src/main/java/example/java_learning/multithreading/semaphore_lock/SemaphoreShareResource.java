package example.java_learning.multithreading.semaphore_lock;

import java.util.concurrent.Semaphore;

public class SemaphoreShareResource {

    // Only 2 threads can use this resource at the same time
    private final Semaphore semaphore = new Semaphore(2);

    public void accessResource() {

        boolean permitAcquired = false;

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " is trying to acquire permit..."
            );

            semaphore.acquire();

            permitAcquired = true;

            System.out.println(
                    Thread.currentThread().getName()
                            + " acquired permit"
            );

            System.out.println(
                    Thread.currentThread().getName()
                            + " is performing work..."
            );

            // Assume thread is using database/API/resource
            Thread.sleep(20000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        } finally {

            if (permitAcquired) {

                semaphore.release();

                System.out.println(
                        Thread.currentThread().getName()
                                + " released permit"
                );
            }
        }
    }
}
