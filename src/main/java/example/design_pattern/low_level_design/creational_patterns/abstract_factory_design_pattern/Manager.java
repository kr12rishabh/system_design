package example.design_pattern.low_level_design.creational_patterns.abstract_factory_design_pattern;

public class Manager implements Employee{
    @Override
    public int salary() {
        return 100000;
    }

    @Override
    public String name() {
        System.out.println("I am a manager:");
        return "MANAGER";
    }
}
