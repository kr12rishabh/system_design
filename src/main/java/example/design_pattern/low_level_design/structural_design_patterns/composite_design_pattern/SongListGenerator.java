package example.design_pattern.low_level_design.structural_design_patterns.composite_design_pattern;

public class SongListGenerator {

    static void main() {
        SongComponent industrialMusic = new SongGroup("Industrial",
                "is a style of experimental music..");
        SongComponent heavyMetalMusic = new SongGroup("Heavy Metal Music",
                "is the genre of rock that developed in late 1960s");
        SongComponent dubStepMusic = new SongGroup("dub Step music",
                "is a genre of electronic drop music");

        SongComponent everySong = new SongGroup("Song List : ", "Every Song Available..");
        everySong.add(industrialMusic);
        industrialMusic.add(new Song("Teri meri kahani", "NIN", 2016));
        industrialMusic.add(new Song("Dope Shope", "Honey Singh", 2013));
        industrialMusic.add(new Song("papa to band bajaaye", "Akshay Kumar", 2011));

        industrialMusic.add(dubStepMusic);

        dubStepMusic.add(new Song("CentiPede", "Knife Party", 2012));
        dubStepMusic.add(new Song("Tetris", "Doctor P", 2011));

        everySong.add(heavyMetalMusic);

        heavyMetalMusic.add(new Song("War Pigs", "Black Sabath", 1970));
        heavyMetalMusic.add(new Song("Ace of Spaded", "Motorhead", 1980));

        DiscJockey crazyLarry = new DiscJockey(everySong);
        crazyLarry.getSongList();



    }
}
