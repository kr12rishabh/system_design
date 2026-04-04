package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.simple_factory;

public class NotificationFactory {
    public static Notification createNotificationObject(String type) {
        return switch (type.toUpperCase()) {
            case "SMS" -> new SmsNotification();
            case "PUSH" -> new PushNotification();
            case "EMAIL" -> new EmailNotification();
            default -> throw new IllegalArgumentException("invalid notification type");
        };
    }
}
