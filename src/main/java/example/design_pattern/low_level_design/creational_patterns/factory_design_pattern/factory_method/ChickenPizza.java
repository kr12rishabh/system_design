package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method;

public class ChickenPizza extends Pizza{

    public ChickenPizza(){
        this.name = "Chicken Pizza";
    }

    @Override
    public void preparePizza() {
        System.out.println("chicken pizza is being prepared now...");
        System.out.println("material related to chicken pizza is being added...");
    }
}
