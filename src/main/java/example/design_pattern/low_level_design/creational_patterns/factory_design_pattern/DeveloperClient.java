package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern;

public class DeveloperClient {
    static void main() {
//        Employee employee = new AndroidDeveloper(); it will be tightly coupled

        Employees employees =  EmployeesFactory.getEmployee("ANDROID DEVELOPER");
        assert employees != null;
//        employee.salary();
        int androidSalary = employees.salary();
        System.out.println(" the salary for the android developer is **** "+ androidSalary);
        Employees employees1 = EmployeesFactory.getEmployee("WEB DEVELOPER");
        assert employees1 != null;
        int webSalary = employees1.salary();
        System.out.println(" the salary for the web developer is **** "+webSalary);




    }
}
