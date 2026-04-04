package example.design_pattern.low_level_design.creational_patterns.abstract_factory_design_pattern;

public class EmployeeFactory {
    public static Employee getEmployee(EmployeeAbstractFactory factory){
        return factory.createEmployee();

    }
}
