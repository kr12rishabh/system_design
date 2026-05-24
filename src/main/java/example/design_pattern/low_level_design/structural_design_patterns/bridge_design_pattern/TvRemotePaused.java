package example.design_pattern.low_level_design.structural_design_patterns.bridge_design_pattern;

public class TvRemotePaused extends RemoteButton{
    public TvRemotePaused(EntertainmentDevice newDevice) {
        super(newDevice);
    }

    @Override
    public void buttonNinePressed() {
        System.out.println("Tv was paused : ");

    }
}
