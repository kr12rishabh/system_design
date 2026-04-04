package example.design_pattern.low_level_design.creational_patterns.builder_design_pattern;


public class User {
    private final String userId;
    private final String userName;
    private final String emailId;

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", emailId='" + emailId + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public String getEmailId() {
        return emailId;
    }

    private User(UserBuilder builder) {
        this.userName = builder.userName;
        this.userId = builder.userId;
        this.emailId = builder.emailId;
    }


    // inner class to create object
    static class UserBuilder {

        private String userId;
        private String userName;
        private String emailId;

        public UserBuilder(){

        }
        public static UserBuilder builder(){
            return new UserBuilder();
        }

        public UserBuilder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setEmailId(String emailId) {
            this.emailId = emailId;
            return this;

        }
        public User build(){
            User user = new User(this);
            return user;
        }
    }

}
