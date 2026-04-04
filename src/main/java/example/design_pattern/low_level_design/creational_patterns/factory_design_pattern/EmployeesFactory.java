package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern;

public class EmployeesFactory {
    // get the employee
    public static Employees getEmployee(String employeeType) {
        if (employeeType.trim().equalsIgnoreCase("ANDROID DEVELOPER")) {
            return new AndroidDeveloper();

        } else if (employeeType.trim().equalsIgnoreCase("WEB DEVELOPER")) {
            return new WebDeveloper();

        } else {
            return null;
        }
    }
}
