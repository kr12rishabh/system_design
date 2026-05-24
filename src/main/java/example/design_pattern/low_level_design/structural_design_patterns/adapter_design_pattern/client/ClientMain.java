package example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.client;

import example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.adaptee.WeighMachineForBabies;
import example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.adapter.WeightMachineAdapter;
import example.design_pattern.low_level_design.structural_design_patterns.adapter_design_pattern.adapter.WeightMachineInKgsImpl;

public class ClientMain {
    static void main() {
        WeightMachineAdapter weightMachineAdapter = new WeightMachineInKgsImpl(new WeighMachineForBabies());
        System.out.println("the weigh in kg will be for pound 28 is ---->>"+weightMachineAdapter.getWeightInKgs());
    }
}
