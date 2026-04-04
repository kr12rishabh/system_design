package example.design_pattern.low_level_design.creational_patterns.abstract_factory_design_pattern;

public class Designer implements Employee{
    @Override
    public int salary() {
        return 20000;
    }

    @Override
    public String name() {
        System.out.println("I am a designer");
        return "DESIGNER";
    }
}
