package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.factory_method_object_creation;

import example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.Pizza;

public abstract class PizzaFactoryStore {

    public Pizza orderPizza(){
        Pizza pizza = createPizza();
        System.out.println("\n Order started for "+ pizza.getName()+"-----");
        pizza.preparePizza();
        pizza.bake();
        pizza.cut();
        pizza.box();
        System.out.println("---- Order completed for "+pizza.getName()+"-----\n");
        return pizza;
    }

    protected abstract Pizza createPizza();
}
