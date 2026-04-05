package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.factory_method_object_creation;

import example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.MargheritaPizza;
import example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.Pizza;

public class MargheritaPizzaStore extends PizzaFactoryStore {
    @Override
    protected Pizza createPizza() {
        return new MargheritaPizza();
    }
}
