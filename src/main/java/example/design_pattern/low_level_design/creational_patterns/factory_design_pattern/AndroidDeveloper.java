package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern;

public class AndroidDeveloper implements Employees {

    @Override
    public int salary() {
        System.out.println("getting android developers salary");
        return 50000;
    }
}
