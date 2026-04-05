package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method;

public class VeggiePizza extends Pizza{

    public VeggiePizza(){
        this.name = "Veggie Pizza";
    }

    @Override
    public void preparePizza() {
        System.out.println("Veggie pizza is being prepared now...");
        System.out.println("material related to Veggie pizza is being added...");
    }
}
