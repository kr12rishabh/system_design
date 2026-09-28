package example.java_learning.multithreading.stamped_lock;

import java.util.concurrent.locks.StampedLock;

public class StampedSharedResource {

    private int a = 10;

    private final StampedLock lock = new StampedLock();


    // Reader
    public void producer() {

        // Optimistic read does NOT block writer
        long stamp = lock.tryOptimisticRead();

        System.out.println(
                "Optimistic Read taken by : "
                        + Thread.currentThread().getName()
        );

        // Read shared value into local variable
        int value = a;

        try {

            Thread.sleep(6000);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();
        }


        /*
         * Check whether any writer acquired write lock
         * after our optimistic read started.
         */
        if (lock.validate(stamp)) {

            System.out.println(
                    "Optimistic Read successful by : "
                            + Thread.currentThread().getName()
            );

            System.out.println("Value of a is : " + value);

        } else {

            System.out.println(
                    "Optimistic Read failed because data was changed"
            );

            /*
             * Data may have changed.
             * So now take a proper read lock
             * and read the latest value again.
             */

            long readStamp = lock.readLock();

            try {

                value = a;

                System.out.println(
                        "Read Lock acquired by : "
                                + Thread.currentThread().getName()
                );

                System.out.println(
                        "Latest value of a is : " + value
                );

            } finally {

                lock.unlockRead(readStamp);

                System.out.println(
                        "Read Lock released by : "
                                + Thread.currentThread().getName()
                );
            }
        }
    }


    // Writer
    public void consumer() {

        long stamp = lock.writeLock();

        try {

            System.out.println(
                    "Write Lock acquired by : "
                            + Thread.currentThread().getName()
            );

            System.out.println("Performing write operation...");

            a = 9;

            System.out.println("Value changed to : " + a);

        } finally {

            lock.unlockWrite(stamp);

            System.out.println(
                    "Write Lock released by : "
                            + Thread.currentThread().getName()
            );
        }
    }
}