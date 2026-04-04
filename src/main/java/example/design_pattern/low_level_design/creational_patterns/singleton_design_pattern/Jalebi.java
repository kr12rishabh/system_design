package example.design_pattern.low_level_design.creational_patterns.singleton_design_pattern;

public class Jalebi {
    private static Jalebi jalebi = new Jalebi();
    public static Jalebi getJalebi(){
        return jalebi;

    }
}
