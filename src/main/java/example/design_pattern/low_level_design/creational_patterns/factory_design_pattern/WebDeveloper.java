package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern;

public class WebDeveloper implements Employees {
    @Override
    public int salary() {
        System.out.println("getting web developer salary");
        return 60000;
    }
}
