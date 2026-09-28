package example.java_learning.multithreading.stop_suspend_and_resume_method_impl;

// PROBLEM STATEMENT...

public class SharedResource {
    private boolean isAvailable = false;

    public synchronized void produce(){
        System.out.println("Lock Aquired....");
        isAvailable = true;
        try{
            Thread.sleep(15000);
        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
        }

        System.out.println("Lock released....");
    }
}
