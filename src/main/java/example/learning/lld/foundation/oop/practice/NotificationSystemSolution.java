package example.learning.lld.foundation.oop.practice;

/**
 * Lesson 1 Practice Solution
 *
 * Problem:
 * Design notifications so Email, SMS and Push can all send a message
 * through one common contract.
 *
 * Concepts used:
 * - interface
 * - abstraction
 * - polymorphism
 * - loose coupling
 */
public class NotificationSystemSolution {

    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        service.send(new EmailNotification(), "Welcome Rishabh");
        service.send(new SmsNotification(), "OTP is 1234");
        service.send(new PushNotification(), "You have a new message");
    }

    interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {

        @Override
        public void send(String message) {
            System.out.println("EMAIL -> " + message);
        }
    }

    static class SmsNotification implements Notification {

        @Override
        public void send(String message) {
            System.out.println("SMS -> " + message);
        }
    }

    static class PushNotification implements Notification {

        @Override
        public void send(String message) {
            System.out.println("PUSH -> " + message);
        }
    }

    static class NotificationService {

        void send(Notification notification, String message) {
            notification.send(message);
        }
    }
}
