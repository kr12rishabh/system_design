package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.simple_factory;

public class EmailNotification implements Notification{
    @Override
    public void send(String message) {
        System.out.println(" sending email to : "+message);
    }
}
