package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.factory_method_object_creation;

import example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.ChickenPizza;
import example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.Pizza;

public class ChickenPizzaStore extends PizzaFactoryStore {
    @Override
    protected Pizza createPizza() {
        return new ChickenPizza();
    }
}
