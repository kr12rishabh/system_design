package example.design_pattern.low_level_design.behavioural_patterns.iterator_design_pattern;

public class Main {
    static void main() {

        UserManagement userManagement = new UserManagement();
        userManagement.addUser(new User("Durges", "14"));
        userManagement.addUser(new User("Harsh", "15"));
        userManagement.addUser(new User("ANkit", "16"));
        userManagement.addUser(new User("Rishabh", "17"));
        MyIterator myIterator = userManagement.getIterator();

        System.out.printf("%-12s %-12s%n", "Id :-->", "Name :-->");
        while (myIterator.hasNext()) {
            User user = (User) myIterator.next();
            System.out.printf("%-12s %-12s%n", user.getUserId(), user.getName());
        }

    }
}
