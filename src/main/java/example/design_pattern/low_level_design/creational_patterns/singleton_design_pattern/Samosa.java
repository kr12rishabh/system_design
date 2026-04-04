package example.design_pattern.low_level_design.creational_patterns.singleton_design_pattern;

public class Samosa {
    private static Samosa samosa;

    private Samosa() {
        if(samosa!=null){
            throw new RuntimeException("You are breaking the singleton pattern here, two object can't be created.");
        }
        //
    }

    // this is lazy way of initialization
    public static Samosa getSamosa() {
        synchronized (Samosa.class) {
            if (samosa == null) {
                samosa = new Samosa();
            }
        }
        return samosa;
    }

}
