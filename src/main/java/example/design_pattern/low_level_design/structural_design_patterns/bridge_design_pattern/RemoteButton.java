package example.design_pattern.low_level_design.structural_design_patterns.bridge_design_pattern;

public abstract class RemoteButton {
    private EntertainmentDevice device;

    public RemoteButton(EntertainmentDevice newDevice) {
        this.device = newDevice;
    }
    public void buttonFivePressed(){
        device.buttonFivePressed();
    }

    public void buttonSixPressed(){
        device.buttonSixPressed();
    }

    public void deviceFeedBack(){
        device.deviceFeedBack();
    }
    public abstract void buttonNinePressed();

}
