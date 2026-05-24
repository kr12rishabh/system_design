package example.design_pattern.low_level_design.structural_design_patterns.composite_design_pattern;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class SongGroup extends SongComponent {

    ArrayList<SongComponent> songComponents = new ArrayList<>();
    String groupName;
    String groupDescription;


    public SongGroup(String groupName, String groupDescription) {
        this.groupName = groupName;
        this.groupDescription = groupDescription;
    }

    public void add(SongComponent newSongComponent) {
        songComponents.add(newSongComponent);
    }

    public void remove(SongComponent newSongComponent) {
        songComponents.remove(newSongComponent);
    }

    public SongComponent getComponent(int componentIndex) {
        return songComponents.get(componentIndex);
    }

    public void displaySongInfo() {
        System.out.println(getGroupName() + "  " + getGroupDescription() + "\n");

        for (SongComponent songInfo : songComponents) {
            songInfo.displaySongInfo();
        }
    }


}
