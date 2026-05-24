package example.design_pattern.low_level_design.structural_design_patterns.composite_design_pattern;

import org.apache.kafka.common.protocol.types.Field;

public abstract class SongComponent {
    public void add(SongComponent songComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove(SongComponent songComponent) {

        throw new UnsupportedOperationException();


    }

    public SongComponent getComponent(int componentIndex) {

        throw new UnsupportedOperationException();


    }

    public String getSongName() {
        throw new UnsupportedOperationException();

    }

    public String getBandName() {
        throw new UnsupportedOperationException();

    }


    public int getReleaseYear() {
        throw new UnsupportedOperationException();

    }

    public void displaySongInfo() {
        throw new UnsupportedOperationException();

    }
}
