package example.design_pattern.low_level_design.creational_patterns.abstract_factory_design_pattern;

public class WebDeveloper implements Employee {
    @Override
    public int salary() {
        return 4000;
    }

    @Override
    public String name() {
        System.out.println("I am web developer: ");
        return "WEB DEVELOPER";
    }
}
