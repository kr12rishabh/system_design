package example.learning.lld.foundation.oop;

/**
 * Lesson 1.5 - Polymorphism
 *
 * Same contract, different behaviour.
 */
public class PolymorphismDemo {

    public static void main(String[] args) {
        Notification notification = new EmailNotification();
        notification.send("Order placed");

        notification = new SmsNotification();
        notification.send("Order shipped");
    }

    interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {

        @Override
        public void send(String message) {
            System.out.println("Email: " + message);
        }
    }

    static class SmsNotification implements Notification {

        @Override
        public void send(String message) {
            System.out.println("SMS: " + message);
        }
    }
}
