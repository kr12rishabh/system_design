package example.design_pattern.low_level_design.creational_patterns.abstract_factory_design_pattern;


public class Client {
    static void main() {
        // i want to get adroid developer;
        Employee employee1 = EmployeeFactory.getEmployee(new AndroidDevFactory());
        int salary = employee1.salary();
        System.out.println(" the salary is **** "+salary);

        employee1.name();
        Employee employee = EmployeeFactory.getEmployee(new WebDevFactory());
        employee.name();
        Employee e3 = EmployeeFactory.getEmployee(new ManagerFactory());
        e3.name();
        Employee employee2 = EmployeeFactory.getEmployee(new DesignerFactory());
        employee2.name();
    }
}
