package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.simple_factory;

public class NotificationService {
    public void send(String type, String message){
        Notification notification = NotificationFactory.createNotificationObject(type);
        notification.send(message);
    }
}
