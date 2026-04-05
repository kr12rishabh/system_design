package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method;

public class MargheritaPizza extends Pizza{

    public MargheritaPizza(){
        this.name = "Margherita Pizza";
    }

    @Override
    public void preparePizza() {
        System.out.println("Margherita pizza is being prepared now...");
        System.out.println("material related to Margherita pizza is being added...");
    }
}
