package example.design_pattern.low_level_design.creational_patterns.builder_design_pattern;

public class BuilderMain {
    static void main() {
        User user  = new
                User.UserBuilder()
                .setEmailId("riskyfollow1@gmail.com")
                .setUserId("triquetra_80")
                .setUserName("RishabhKumar")
                .build();
        System.out.println(user);

    }
}
