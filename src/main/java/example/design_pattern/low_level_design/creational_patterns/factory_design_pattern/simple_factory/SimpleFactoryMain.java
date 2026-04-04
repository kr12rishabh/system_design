package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.simple_factory;

public class SimpleFactoryMain {
    static void main() {
        NotificationService notificationService = new NotificationService();
        notificationService.send("EMAIL"," welcome to email notification");
        notificationService.send("SMS"," your otp is 234567");
        notificationService.send("PUSH"," you have new notification to your app");
    }
}
