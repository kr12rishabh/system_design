package example.learning.lld.foundation.oop;

/**
 * Lesson 1.6 - Interface
 *
 * An interface is a contract.
 */
public class InterfaceDemo {

    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        service.notifyUser(new EmailNotification(), "Welcome!");
        service.notifyUser(new PushNotification(), "New message received");
    }

    interface Notification {
        void send(String message);
    }

    static class EmailNotification implements Notification {

        @Override
        public void send(String message) {
            System.out.println("Email notification: " + message);
        }
    }

    static class PushNotification implements Notification {

        @Override
        public void send(String message) {
            System.out.println("Push notification: " + message);
        }
    }

    static class NotificationService {

        void notifyUser(Notification notification, String message) {
            notification.send(message);
        }
    }
}
