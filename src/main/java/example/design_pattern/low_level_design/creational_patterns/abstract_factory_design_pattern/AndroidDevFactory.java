package example.design_pattern.low_level_design.creational_patterns.abstract_factory_design_pattern;

public class AndroidDevFactory extends EmployeeAbstractFactory{
    @Override
    public Employee createEmployee() {
        return new AndroidDeveloper();
    }
}
