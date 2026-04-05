package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method;

import example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.factory_method_object_creation.MargheritaPizzaStore;
import example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method.factory_method_object_creation.PizzaFactoryStore;
import org.apache.logging.log4j.util.Strings;

import java.util.Objects;

import static example.util.JsonUtil.getJsonString;
import static example.util.JsonUtil.isStringEmptyOrNull;

public class FactorMethodDemoMain {
    static void main(){
        PizzaFactoryStore margheritaPizza = new MargheritaPizzaStore();
        Pizza margherita = margheritaPizza.orderPizza();

        System.out.println(getJsonString(margherita));

        System.out.println(" is String empty or null check-->>"+isStringEmptyOrNull(Strings.EMPTY));
        System.out.println(" is Object Null-->>"+ Objects.isNull(margherita));


    }
}
