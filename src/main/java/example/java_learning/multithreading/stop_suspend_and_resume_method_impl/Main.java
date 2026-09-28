package example.java_learning.multithreading.stop_suspend_and_resume_method_impl;

public class Main {

    static void main() {
        SharedResource sharedResource = new SharedResource();
        System.out.println("main thread started : ");

        Thread th1 = new Thread(()->{
            System.out.println("Thread th1 calling produced method...");
            sharedResource.produce();

        });

        Thread th2 = new Thread(()->{

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
            }
            System.out.println("Thread th2 is calling produce method...");
            sharedResource.produce();

        });

        th1.start();
        th2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
//                throw new RuntimeException(e);
        }

        System.out.println("Thread th1 is suspended....");

//        th1.suspend();

    }
}
