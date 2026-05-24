package example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.adapter;

import example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.adaptee.WeightMachine;

public class WeightMachineInKgsImpl implements WeightMachineAdapter{
    WeightMachine weightMachine;
    public WeightMachineInKgsImpl(WeightMachine weightMachine){
        this.weightMachine = weightMachine;
    }
    @Override
    public double getWeightInKgs() {
        double weighInPound = weightMachine.getWeightInPound();
        return weighInPound * 0.45;
    }
}
