package example.design_pattern.low_level_design.structural_design_patterns.bridge_design_pattern;

public class TvRemoteMute extends RemoteButton{


    public TvRemoteMute(EntertainmentDevice newDevice) {
        super(newDevice);
    }

    @Override
    public void buttonNinePressed() {

        System.out.println("Tv was muted : ");

    }
}
