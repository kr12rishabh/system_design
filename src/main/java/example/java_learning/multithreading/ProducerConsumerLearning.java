package example.java_learning.multithreading;

import example.java_learning.multithreading.producer_consumer_problem.SharedResource;

public class ProducerConsumerLearning {

    static void main(String[] args) {
        SharedResource sharedBuffer = new SharedResource(3);

        // creating producer thread using lambda expression...
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 6; i++) {
                    sharedBuffer.produce(i);
                }

            } catch (Exception e) {
//                throw new RuntimeException(e);
            }

        });

        //creating consumer thread using lambda expression...
        Thread consumerThread = new Thread(() -> {

            try {
                for (int i = 1; i <= 6; i++) {
                    sharedBuffer.consume();
                }

            } catch (Exception e) {
//                throw new RuntimeException(e);
            }


        });

        producerThread.start();
        consumerThread.start();


    }
}
