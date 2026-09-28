package example.java_learning.multithreading.producer_consumer_problem;

// PROBLEM STATEMENT...

import java.util.LinkedList;
import java.util.Queue;

/**
 * Problem: Producer-Consumer Problem
 * <p>
 * Implement the classic Producer-Consumer problem using two threads
 * sharing a fixed-size buffer.
 * <p>
 * The Producer continuously generates data and inserts it into the buffer.
 * The Consumer continuously removes and processes data from the buffer.
 * <p>
 * Conditions:
 * - The Producer must wait when the buffer is full.
 * - The Consumer must wait when the buffer is empty.
 * - The shared buffer must be accessed safely by both threads.
 * <p>
 * Requirements:
 * - Use Queue<Integer> as the shared buffer.
 * - Define a fixed buffer capacity.
 * - Create one Producer thread.
 * - Create one Consumer thread.
 * - Start both threads.
 * - Properly synchronize access to the shared buffer.
 * <p>
 * Example:
 * <p>
 * Producer ---> [ 10 ][ 20 ][ 30 ][ 40 ] ---> Consumer
 * <p>
 * If buffer is full:
 * Producer waits.
 * <p>
 * If buffer is empty:
 * Consumer waits.
 */
public class SharedResource {
    private Queue<Integer> sharedBuffer;
    private int bufferSize;

    public SharedResource(int bufferSize) {
        sharedBuffer = new LinkedList<>();
        this.bufferSize = bufferSize;
    }

    public synchronized void produce(int item) throws Exception {
        while (sharedBuffer.size() == bufferSize) {
            System.out.println("Buffer is full, producer is waiting for the consumer...");
            wait();
        }

        sharedBuffer.add(item);
        System.out.println("Produced item : " + item);
        notify();
    }

    public synchronized int consume() throws Exception {
        while (sharedBuffer.isEmpty()) {
            System.out.println("Buffer is empty, consumer is waiting for the producer...");
            wait();
        }
        int item = sharedBuffer.poll();

        System.out.println("Consumed Item : " + item);
        notify();
        return item;
    }

}
