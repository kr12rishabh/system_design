package example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.learn_code_with_durgesh;

public class AdapterMain {
    static void main() {
        System.out.println("program started :");
        AndroidCharger androidCharger = new DKCharger();
        AppleCharger charger = new AdapterCharger(androidCharger);
        Iphone13 iphone13 = new Iphone13(charger);
        iphone13.chargeIPhone();

    }
}
