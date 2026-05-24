package example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.learn_code_with_durgesh;

public class Iphone13 {
    private AppleCharger appleCharger;
    public void chargeIPhone(){
        appleCharger.chargePhone();

    }

    public Iphone13(AppleCharger appleCharger) {
        this.appleCharger = appleCharger;
    }
}
