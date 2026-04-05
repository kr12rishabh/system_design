package example.design_pattern.low_level_design.creational_patterns.factory_design_pattern.factory_method;

public abstract class Pizza {

    protected String name;
    public String getName() {
        return name;
    }

    public abstract void preparePizza();

    public void bake(){
        System.out.println("Pizza ->"+name+ " is being baked from 20 minutes.");
    }

    public void cut(){
        System.out.println("Pizza ->"+name+ " is being cut into slices");
    }

    public void box(){
        System.out.println("Pizza ->"+name+ " is prepared now,now it is ready for box it in envelop");
    }


}
