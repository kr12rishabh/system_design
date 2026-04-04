package example.design_pattern.low_level_design.creational_patterns.singleton_design_pattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Example {
    static void main() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        Samosa samosa1  = Samosa.getSamosa();
//        System.out.println(samosa1.hashCode());
//        Samosa samosa2 = Samosa.getSamosa();
//        System.out.println(samosa2.hashCode());
////        Jalebi jalebi1 = Jalebi.getJalebi();
////        System.out.println(jalebi1.hashCode());
////        Jalebi jalebi2 = Jalebi.getJalebi();
////        System.out.println(jalebi2.hashCode());
        Samosa s1 = Samosa.getSamosa();
        System.out.println(s1.hashCode());

        Constructor<Samosa> constructor = Samosa.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Samosa s2 = constructor.newInstance();
        System.out.println(s2.hashCode());


    }
}
