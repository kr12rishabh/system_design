package example.design_pattern.low_level_design.structural_design_patterns.composite_design_pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.protocol.types.Field;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Getter
@Setter
@Data
@Slf4j
public class Song extends SongComponent{
    String songName;
    String bandName;
    int releaseYear;
    public void displaySongInfo() {
        System.out.println(getSongName() + " was recorded by  " + getBandName() + " in " + getReleaseYear());
    }
}
