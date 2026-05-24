package example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.learn_code_with_durgesh;

public class AdapterCharger implements AppleCharger{
    private AndroidCharger charger;

    public AdapterCharger(AndroidCharger charger) {
        this.charger = charger;
    }

    @Override
    public void chargePhone() {
        charger.chargeAndroidPhone();
        System.out.println("your phone is charging with Adapter....");

    }
}
