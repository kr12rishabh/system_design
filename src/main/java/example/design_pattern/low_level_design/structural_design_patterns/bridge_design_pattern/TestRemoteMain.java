package example.design_pattern.low_level_design.structural_design_patterns.bridge_design_pattern;

public class TestRemoteMain {
    static void main() {

        RemoteButton theTv = new TvRemoteMute(new TvDevice(1,200));
        RemoteButton theTv2 = new TvRemotePaused(new TvDevice(1,200));
        System.out.println("Test tv with mute : ");

        theTv.buttonFivePressed();
theTv.buttonSixPressed();
theTv.buttonNinePressed();
        System.out.println("\ntest tv with pause \n");
//        theTv2.buttonFivePressed();
        theTv2.buttonFivePressed();
        theTv2.buttonSixPressed();
        theTv2.buttonSixPressed();
        theTv2.buttonSixPressed();
        theTv2.buttonSixPressed();
        theTv2.buttonSixPressed();
        theTv2.deviceFeedBack();




    }
}
