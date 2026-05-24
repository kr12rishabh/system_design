package example.design_pattern.low_level_design.structural_design_patterns.composite_design_pattern;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Setter
@Getter
@Slf4j
public class DiscJockey {
    SongComponent songList;
    public void getSongList(){
        songList.displaySongInfo();
    }

}
