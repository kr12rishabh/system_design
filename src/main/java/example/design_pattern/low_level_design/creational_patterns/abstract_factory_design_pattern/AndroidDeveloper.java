package example.design_pattern.low_level_design.creational_patterns.abstract_factory_design_pattern;

public class AndroidDeveloper implements Employee {
    @Override
    public int salary() {
        System.out.println("getting salary or android developer");
        return 50000;
    }

    @Override
    public String name() {
        System.out.println(" I am android developer");
        return "ANDROID DEVELOPER";
    }
}
