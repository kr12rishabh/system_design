# Learning Composite Design Pattern

## Simple Meaning

Composite Design Pattern is used when we want to treat:

```text
Single object
Group of objects
```

in the same way.

In very simple words:

```text
One item and a group of items should be handled using the same parent type.
```

In your code:

```text
One Song
Group of Songs
```

both are handled as:

```java
SongComponent
```

That is the main idea of Composite Design Pattern.

## Real-Life Meaning

Think about a folder system:

```text
Folder
  |
  |-- File
  |-- File
  |-- Folder
        |
        |-- File
        |-- File
```

A folder can contain files.

A folder can also contain another folder.

If we call:

```text
show()
```

on a file, it shows the file.

If we call:

```text
show()
```

on a folder, it shows everything inside the folder.

That is Composite Pattern.

## Your Example

Your example uses songs and song groups.

The structure is:

```text
Song List
  |
  |-- Industrial
  |     |
  |     |-- Teri meri kahani
  |     |-- Dope Shope
  |     |-- papa to band bajaaye
  |     |
  |     |-- Dub Step Music
  |           |
  |           |-- CentiPede
  |           |-- Tetris
  |
  |-- Heavy Metal Music
        |
        |-- War Pigs
        |-- Ace of Spaded
```

Here:

```text
Song = single object
SongGroup = group of objects
```

But both extend:

```java
SongComponent
```

So the client can treat both in the same way.

## Why We Need Composite Pattern

Without Composite Pattern, client code may need to check again and again:

```java
if (object is Song) {
    print song
} else if (object is SongGroup) {
    loop through group
    if child is Song {
        print song
    } else if child is SongGroup {
        loop again
    }
}
```

This becomes complicated when groups can contain more groups.

Composite Pattern solves this by giving both `Song` and `SongGroup` the same parent:

```java
SongComponent
```

Then client code can simply call:

```java
songComponent.displaySongInfo();
```

The object itself decides what to do.

If the object is a `Song`, it prints song details.

If the object is a `SongGroup`, it prints group details and then asks its children to print their details.

## Main Line To Remember

```text
Composite Pattern lets us treat single objects and groups of objects uniformly.
```

In your code:

```text
Song and SongGroup are both SongComponent.
```

## Classes In Your Package

Your package contains:

```text
composite_design_pattern
|
|-- SongComponent.java
|-- Song.java
|-- SongGroup.java
|-- DiscJockey.java
|-- SongListGenerator.java
```

Each class has a specific role in the Composite Pattern.

## Composite Pattern Roles In Your Code

```text
Composite Pattern Role        Your Class
------------------------------------------------
Component                     SongComponent
Leaf                          Song
Composite                     SongGroup
Client                        DiscJockey
Client / Demo builder          SongListGenerator
```

## 1. SongComponent

File:

```text
SongComponent.java
```

This is the component.

It is the common parent class for both:

```text
Song
SongGroup
```

Your class:

```java
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
```

This class defines all operations that may be needed by either a song or a song group.

Some operations are useful for `SongGroup`:

```java
add()
remove()
getComponent()
```

Some operations are useful for `Song`:

```java
getSongName()
getBandName()
getReleaseYear()
```

One operation is useful for both:

```java
displaySongInfo()
```

## Why Methods Throw UnsupportedOperationException

In `SongComponent`, every method throws:

```java
throw new UnsupportedOperationException();
```

This means:

```text
By default, this operation is not supported.
```

Then child classes override only the methods they actually support.

Example:

`SongGroup` supports:

```text
add
remove
getComponent
displaySongInfo
```

So `SongGroup` overrides those methods.

`Song` supports:

```text
getSongName
getBandName
getReleaseYear
displaySongInfo
```

In your code, Lombok generates the getter methods for `Song`, and `Song` directly overrides `displaySongInfo()`.

## Important Point About SongComponent

`SongComponent` lets the client use one common type:

```java
SongComponent
```

Because of this, the client does not need separate variables like:

```java
Song song;
SongGroup songGroup;
```

Instead, it can use:

```java
SongComponent component;
```

That component can be either:

```text
Song
SongGroup
```

This is the foundation of the Composite Pattern.

## 2. Song

File:

```text
Song.java
```

This is the leaf.

Leaf means:

```text
An object that does not contain child objects.
```

A song is a final item.

It does not contain more songs inside it.

Your class:

```java
public class Song extends SongComponent {
    String songName;
    String bandName;
    int releaseYear;

    public void displaySongInfo() {
        System.out.println(getSongName() + " was recorded by  " + getBandName() + " in " + getReleaseYear());
    }
}
```

The `Song` class stores:

```text
songName
bandName
releaseYear
```

Example from your code:

```java
new Song("War Pigs", "Black Sabath", 1970)
```

This means:

```text
Song name    = War Pigs
Band name    = Black Sabath
Release year = 1970
```

When we call:

```java
displaySongInfo()
```

on a `Song`, it prints only that song's details.

Example output:

```text
War Pigs was recorded by Black Sabath in 1970
```

## Why Song Is A Leaf

`Song` is a leaf because it does not have a list of children.

It does not contain:

```java
ArrayList<SongComponent>
```

It only has its own data:

```java
String songName;
String bandName;
int releaseYear;
```

So this is the lowest level object in the tree.

## Lombok In Song

Your `Song` class uses Lombok annotations:

```java
@AllArgsConstructor
@Getter
@Setter
@Data
@Slf4j
```

Important ones here:

```text
@AllArgsConstructor creates a constructor with all fields.
@Getter creates getter methods.
@Setter creates setter methods.
@Data creates getters, setters, toString, equals, and hashCode.
```

Because of `@AllArgsConstructor`, this works:

```java
new Song("Teri meri kahani", "NIN", 2016)
```

Because of getter generation, these methods are available:

```java
getSongName()
getBandName()
getReleaseYear()
```

That is why `displaySongInfo()` can call:

```java
getSongName()
getBandName()
getReleaseYear()
```

## 3. SongGroup

File:

```text
SongGroup.java
```

This is the composite.

Composite means:

```text
An object that can contain other objects.
```

Your class:

```java
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
```

The most important line is:

```java
ArrayList<SongComponent> songComponents = new ArrayList<>();
```

This means:

```text
A SongGroup can contain many SongComponent objects.
```

And because both `Song` and `SongGroup` are `SongComponent`, a `SongGroup` can contain:

```text
Song
SongGroup
```

This is the main power of the Composite Pattern.

## SongGroup Can Contain Songs

Example:

```java
industrialMusic.add(new Song("Teri meri kahani", "NIN", 2016));
industrialMusic.add(new Song("Dope Shope", "Honey Singh", 2013));
```

Here:

```text
industrialMusic is a SongGroup.
It contains Song objects.
```

## SongGroup Can Contain Another SongGroup

Example:

```java
industrialMusic.add(dubStepMusic);
```

Here:

```text
industrialMusic is a SongGroup.
dubStepMusic is also a SongGroup.
industrialMusic contains dubStepMusic.
```

This creates a tree-like structure.

```text
Industrial
  |
  |-- Song
  |-- Song
  |-- Song
  |-- Dub Step Music
        |
        |-- Song
        |-- Song
```

That nested structure is exactly where Composite Pattern is useful.

## How SongGroup Displays Information

The method:

```java
public void displaySongInfo() {
    System.out.println(getGroupName() + "  " + getGroupDescription() + "\n");

    for (SongComponent songInfo : songComponents) {
        songInfo.displaySongInfo();
    }
}
```

works like this:

```text
1. Print the group name and description.
2. Loop through every child inside the group.
3. Call displaySongInfo() on each child.
```

The child can be:

```text
Song
SongGroup
```

If child is `Song`, then song details are printed.

If child is `SongGroup`, then that group prints its own heading and loops through its own children.

This is called recursive behavior.

## What Recursive Behavior Means Here

Recursive means:

```text
The same method keeps calling itself through child objects.
```

In your code:

```java
songInfo.displaySongInfo();
```

can call:

```text
Song.displaySongInfo()
```

or:

```text
SongGroup.displaySongInfo()
```

If it calls `SongGroup.displaySongInfo()`, that group again loops through its children and calls:

```java
songInfo.displaySongInfo();
```

This continues until all songs are printed.

## 4. DiscJockey

File:

```text
DiscJockey.java
```

This is a client class.

Your class:

```java
public class DiscJockey {
    SongComponent songList;

    public void getSongList(){
        songList.displaySongInfo();
    }
}
```

`DiscJockey` has:

```java
SongComponent songList;
```

This is important.

It does not say:

```java
SongGroup songList;
```

It says:

```java
SongComponent songList;
```

So `DiscJockey` can work with any `SongComponent`.

It can work with:

```text
One Song
One SongGroup
A big SongGroup containing many nested SongGroups
```

The method:

```java
public void getSongList(){
    songList.displaySongInfo();
}
```

does not care about the real object type.

It simply calls:

```java
displaySongInfo()
```

Then polymorphism decides which class method will run.

## What Polymorphism Means Here

Polymorphism means:

```text
Same method call, different behavior depending on actual object.
```

Example:

```java
SongComponent component = new Song("Tetris", "Doctor P", 2011);
component.displaySongInfo();
```

This calls:

```text
Song.displaySongInfo()
```

Another example:

```java
SongComponent component = new SongGroup("Dub Step Music", "is a genre of electronic drop music");
component.displaySongInfo();
```

This calls:

```text
SongGroup.displaySongInfo()
```

Same method:

```java
displaySongInfo()
```

Different behavior.

That is why the client code stays simple.

## 5. SongListGenerator

File:

```text
SongListGenerator.java
```

This is the demo class that builds the full tree.

Your code starts by creating groups:

```java
SongComponent industrialMusic = new SongGroup("Industrial",
        "is a style of experimental music..");

SongComponent heavyMetalMusic = new SongGroup("Heavy Metal Music",
        "is the genre of rock that developed in late 1960s");

SongComponent dubStepMusic = new SongGroup("dub Step music",
        "is a genre of electronic drop music");
```

Notice the variable type:

```java
SongComponent
```

Even though the actual object is:

```java
new SongGroup(...)
```

This is good for Composite Pattern because the code depends on the common parent.

Then you create the root group:

```java
SongComponent everySong = new SongGroup("Song List : ", "Every Song Available..");
```

This root group will contain all other groups.

## Building The Song Tree

First, you add `industrialMusic` inside `everySong`:

```java
everySong.add(industrialMusic);
```

Now the tree is:

```text
Song List
  |
  |-- Industrial
```

Then you add songs inside `industrialMusic`:

```java
industrialMusic.add(new Song("Teri meri kahani", "NIN", 2016));
industrialMusic.add(new Song("Dope Shope", "Honey Singh", 2013));
industrialMusic.add(new Song("papa to band bajaaye", "Akshay Kumar", 2011));
```

Now the tree is:

```text
Song List
  |
  |-- Industrial
        |
        |-- Teri meri kahani
        |-- Dope Shope
        |-- papa to band bajaaye
```

Then you add `dubStepMusic` inside `industrialMusic`:

```java
industrialMusic.add(dubStepMusic);
```

Now the tree is:

```text
Song List
  |
  |-- Industrial
        |
        |-- Teri meri kahani
        |-- Dope Shope
        |-- papa to band bajaaye
        |-- Dub Step Music
```

Then you add songs inside `dubStepMusic`:

```java
dubStepMusic.add(new Song("CentiPede", "Knife Party", 2012));
dubStepMusic.add(new Song("Tetris", "Doctor P", 2011));
```

Now the tree is:

```text
Song List
  |
  |-- Industrial
        |
        |-- Teri meri kahani
        |-- Dope Shope
        |-- papa to band bajaaye
        |-- Dub Step Music
              |
              |-- CentiPede
              |-- Tetris
```

Then you add `heavyMetalMusic` inside `everySong`:

```java
everySong.add(heavyMetalMusic);
```

Now the tree is:

```text
Song List
  |
  |-- Industrial
  |-- Heavy Metal Music
```

Then you add songs inside `heavyMetalMusic`:

```java
heavyMetalMusic.add(new Song("War Pigs", "Black Sabath", 1970));
heavyMetalMusic.add(new Song("Ace of Spaded", "Motorhead", 1980));
```

Final tree:

```text
Song List
  |
  |-- Industrial
  |     |
  |     |-- Teri meri kahani
  |     |-- Dope Shope
  |     |-- papa to band bajaaye
  |     |-- Dub Step Music
  |           |
  |           |-- CentiPede
  |           |-- Tetris
  |
  |-- Heavy Metal Music
        |
        |-- War Pigs
        |-- Ace of Spaded
```

## Calling The Client

At the end:

```java
DiscJockey crazyLarry = new DiscJockey(everySong);
crazyLarry.getSongList();
```

This means:

```text
Give the full song tree to DiscJockey.
Ask DiscJockey to print the song list.
```

`DiscJockey` calls:

```java
songList.displaySongInfo();
```

Here `songList` is actually:

```text
everySong
```

and `everySong` is a:

```text
SongGroup
```

So `SongGroup.displaySongInfo()` starts running.

## Complete Execution Flow

Code:

```java
crazyLarry.getSongList();
```

Flow:

```text
1. DiscJockey.getSongList() is called.
2. It calls songList.displaySongInfo().
3. songList is everySong.
4. everySong is a SongGroup.
5. SongGroup.displaySongInfo() prints root group information.
6. It loops through children of everySong.
7. First child is industrialMusic.
8. industrialMusic is also a SongGroup.
9. industrialMusic.displaySongInfo() prints industrial group information.
10. industrialMusic loops through its children.
11. Song children print their own song information.
12. dubStepMusic child is another SongGroup.
13. dubStepMusic.displaySongInfo() prints dub step group information.
14. dubStepMusic loops through its songs and prints them.
15. Control returns back to industrialMusic.
16. Control returns back to everySong.
17. Next child is heavyMetalMusic.
18. heavyMetalMusic.displaySongInfo() prints heavy metal group information.
19. heavyMetalMusic loops through its songs and prints them.
20. Full song list is printed.
```

## Why This Is Composite Pattern

This code is Composite Pattern because:

```text
Song and SongGroup share the same parent: SongComponent.
```

Also:

```text
SongGroup can contain a list of SongComponent.
```

This means a group can contain:

```text
Song
SongGroup
```

So we can create a tree structure.

The client does not need to know whether it is working with a single song or a group.

It just calls:

```java
displaySongInfo()
```

That is the key benefit.

## Class Relationship Diagram

```text
                 SongComponent
                       ^
                       |
          -----------------------------
          |                           |
        Song                      SongGroup
    single item                  group item
                                  |
                                  | contains many
                                  v
                            SongComponent
```

The important part is:

```text
SongGroup contains SongComponent.
```

Because of that, `SongGroup` can contain both:

```text
Song
SongGroup
```

## Object Tree Diagram From Your Code

```text
everySong : SongGroup
|
|-- industrialMusic : SongGroup
|   |
|   |-- Song("Teri meri kahani", "NIN", 2016)
|   |-- Song("Dope Shope", "Honey Singh", 2013)
|   |-- Song("papa to band bajaaye", "Akshay Kumar", 2011)
|   |
|   |-- dubStepMusic : SongGroup
|       |
|       |-- Song("CentiPede", "Knife Party", 2012)
|       |-- Song("Tetris", "Doctor P", 2011)
|
|-- heavyMetalMusic : SongGroup
    |
    |-- Song("War Pigs", "Black Sabath", 1970)
    |-- Song("Ace of Spaded", "Motorhead", 1980)
```

## One Important Method Call

This line in `SongGroup` is very important:

```java
songInfo.displaySongInfo();
```

This is where Composite Pattern becomes powerful.

`songInfo` is declared as:

```java
SongComponent
```

But the actual object can be:

```text
Song
SongGroup
```

Java decides at runtime which method should run.

If it is a song:

```text
Print song details.
```

If it is a group:

```text
Print group details and continue looping inside that group.
```

## How This Code Was Created Step By Step

### Step 1: Identify The Tree Structure

The first question is:

```text
Can one object contain other objects of the same general type?
```

In your example:

```text
A song group can contain songs.
A song group can also contain other song groups.
```

So this is a tree structure.

That is a strong sign that Composite Pattern can be used.

### Step 2: Create A Common Parent

You created:

```java
public abstract class SongComponent
```

This common parent represents both:

```text
Single song
Group of songs
```

The common parent makes it possible to write:

```java
ArrayList<SongComponent> songComponents
```

instead of separate lists like:

```java
ArrayList<Song> songs
ArrayList<SongGroup> songGroups
```

### Step 3: Create The Leaf Class

You created:

```java
public class Song extends SongComponent
```

This is the leaf class.

It stores actual song details:

```text
songName
bandName
releaseYear
```

It implements:

```java
displaySongInfo()
```

to print one song.

### Step 4: Create The Composite Class

You created:

```java
public class SongGroup extends SongComponent
```

This is the composite class.

It stores:

```java
ArrayList<SongComponent> songComponents = new ArrayList<>();
```

This lets the group contain both songs and other groups.

It implements:

```java
add()
remove()
getComponent()
displaySongInfo()
```

### Step 5: Make The Composite Loop Through Children

In `SongGroup.displaySongInfo()`:

```java
for (SongComponent songInfo : songComponents) {
    songInfo.displaySongInfo();
}
```

This allows the group to ask every child to display itself.

The group does not need to know exactly what each child is.

It only knows:

```text
Every child is a SongComponent.
Every SongComponent has displaySongInfo().
```

### Step 6: Build The Object Tree

In `SongListGenerator`, you created groups:

```java
SongComponent industrialMusic = new SongGroup(...);
SongComponent heavyMetalMusic = new SongGroup(...);
SongComponent dubStepMusic = new SongGroup(...);
SongComponent everySong = new SongGroup(...);
```

Then you connected them:

```java
everySong.add(industrialMusic);
industrialMusic.add(dubStepMusic);
everySong.add(heavyMetalMusic);
```

Then you added songs into groups:

```java
industrialMusic.add(new Song(...));
dubStepMusic.add(new Song(...));
heavyMetalMusic.add(new Song(...));
```

This creates the full tree.

### Step 7: Use The Tree Through The Common Parent

Finally:

```java
DiscJockey crazyLarry = new DiscJockey(everySong);
crazyLarry.getSongList();
```

`DiscJockey` does not need to know the full tree structure.

It just calls:

```java
displaySongInfo()
```

on the root component.

## Composite Pattern Vocabulary

### Component

The common parent.

In your code:

```java
SongComponent
```

### Leaf

The single object.

It does not contain children.

In your code:

```java
Song
```

### Composite

The group object.

It contains child components.

In your code:

```java
SongGroup
```

### Client

The code that uses the component.

In your code:

```java
DiscJockey
SongListGenerator
```

## Why This Pattern Makes Your Code Easy

The client code is simple.

It does not need complex checks.

It does not need to know the exact object type.

It does not need separate logic for songs and groups.

It can simply use:

```java
SongComponent
```

and call:

```java
displaySongInfo()
```

This is clean because each class handles its own responsibility.

## Responsibility Of Each Class

```text
SongComponent
  Defines the common operations.

Song
  Stores and displays one song.

SongGroup
  Stores many SongComponent objects and displays them recursively.

DiscJockey
  Uses the root SongComponent and asks it to display the song list.

SongListGenerator
  Creates the tree of song groups and songs.
```

## What Happens If We Add A New Song

To add a new song, you do not need to change `DiscJockey`.

You do not need to change `SongComponent`.

You do not need to change `SongGroup`.

You only add:

```java
industrialMusic.add(new Song("New Song", "New Band", 2026));
```

The existing display logic will still work.

## What Happens If We Add A New Group

To add a new group:

```java
SongComponent popMusic = new SongGroup("Pop Music", "popular music genre");
everySong.add(popMusic);
popMusic.add(new Song("Some Pop Song", "Some Artist", 2020));
```

Again, `DiscJockey` does not change.

The tree handles it naturally.

## What Happens If We Add Group Inside Group

You can also add a group inside another group:

```java
SongComponent classicRock = new SongGroup("Classic Rock", "old rock songs");
heavyMetalMusic.add(classicRock);
classicRock.add(new Song("Some Classic Song", "Some Band", 1975));
```

This works because:

```text
SongGroup accepts SongComponent.
SongGroup itself is also a SongComponent.
```

## Most Important Code Lines

### Common Parent

```java
public abstract class SongComponent
```

This makes songs and groups share the same type.

### Leaf

```java
public class Song extends SongComponent
```

This represents a single song.

### Composite

```java
public class SongGroup extends SongComponent
```

This represents a group.

### Child List

```java
ArrayList<SongComponent> songComponents = new ArrayList<>();
```

This allows a group to store songs and groups.

### Recursive Display

```java
for (SongComponent songInfo : songComponents) {
    songInfo.displaySongInfo();
}
```

This prints everything inside the tree.

### Client Using Common Type

```java
SongComponent songList;
```

This keeps `DiscJockey` independent of the concrete classes.

## Small Notes About Current Code

### Main Method

Your `SongListGenerator` currently has:

```java
static void main()
```

If you want to run it directly as a normal Java application, use:

```java
public static void main(String[] args)
```

### UnsupportedOperationException

Using `UnsupportedOperationException` in `SongComponent` is a common way to write a simple Composite Pattern example.

It means child classes override only the methods that make sense for them.

For example:

```text
SongGroup supports add().
Song does not support add().
```

So if someone tries to call:

```java
someSong.add(anotherSong);
```

it will throw:

```java
UnsupportedOperationException
```

That is expected because a single song should not contain another song.

### Lombok Annotations

In `Song`, `@Data` already creates getters and setters.

So `@Getter` and `@Setter` are not strictly needed there if `@Data` is already used.

But for learning the Composite Pattern, this does not change the pattern logic.

### Unused Imports

Some classes have unused imports, such as:

```java
import org.apache.kafka.common.protocol.types.Field;
```

This import is not needed for the Composite Pattern explanation.

Removing unused imports would make the code cleaner, but the design pattern idea remains the same.

## Final Short Summary

Composite Pattern is useful when you have a tree-like structure.

In your code:

```text
SongGroup can contain Song.
SongGroup can also contain another SongGroup.
```

Both `Song` and `SongGroup` extend:

```java
SongComponent
```

So the client can call:

```java
displaySongInfo()
```

without checking whether the object is a single song or a group.

## One-Line Revision

```text
Composite Pattern lets us treat one Song and a whole SongGroup in the same way using SongComponent.
```

## Remember This

```text
Song is a SongComponent.
SongGroup is a SongComponent.
SongGroup contains many SongComponent objects.
```

That is the heart of your Composite Design Pattern example.
