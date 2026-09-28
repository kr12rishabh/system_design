package example.java_learning.multithreading.stamped_lock;


public class StampedLockMain {

    public static void main(String[] args) {

        StampedSharedResource resource =
                new StampedSharedResource();


        Thread th1 = new Thread(() -> {

            resource.producer();

        });


        Thread th2 = new Thread(() -> {

            resource.consumer();

        });


        th1.start();

        /*
         * Optional:
         * Give Thread-0 some time to start optimistic read
         * before Thread-1 performs write.
         */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        th2.start();
    }
}
