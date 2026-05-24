package example.design_pattern.low_level_design.structural_design_patterns.bridge_design_pattern;

public abstract class EntertainmentDevice {
    public int deviceState;
    public int maxSetting;
    public int volumeLevel = 0;

    public abstract void buttonFivePressed();

    public abstract void buttonSixPressed();

    public void deviceFeedBack() {
        if (deviceState > maxSetting || deviceState < 0) {
            System.out.println("On : " + deviceState);

        }
    }

    public void buttonSevenPressed() {
        volumeLevel++;
        System.out.println(" Volume level at : " + volumeLevel);
    }

    public void buttonEightPressed() {
        volumeLevel--;
        System.out.println("Volume level at : " + volumeLevel);
    }

}
