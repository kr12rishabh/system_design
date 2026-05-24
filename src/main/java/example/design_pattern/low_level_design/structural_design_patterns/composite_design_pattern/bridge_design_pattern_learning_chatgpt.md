## Summary

**Composite Design Pattern is used when we have objects inside objects, like a tree structure.**

## One-Line Answer

**Composite pattern lets us treat a single object and a group of objects in the same way.**

---

# Very Simple Meaning

Composite means:

```text
Small object + small object + group object = one big structure
```

Example:

```text
Folder contains files
Folder can also contain another folder
That sub-folder can again contain files and folders
```

This can go very deep.

That is why your slide says:

```text
A Composite can contain never ending groupings
```

Meaning:

```text
Group can contain group
That group can contain another group
And this can continue
```

---

# Your Notes In Simple Words

In your notes, you wrote:

```text
Composite pattern helps when we have object inside object.
```

Correct.

You also wrote:

```text
Composite object can have composite object.
Composite object can have leaf.
```

This is the main point.

Example from your notes:

```text
Cabinet
 ├── Hard Disk
 ├── Motherboard
 │    ├── RAM
 │    └── CPU
 └── Peripheral Device
      ├── Mouse
      └── Keyboard
```

Here:

```text
CPU = single object
RAM = single object
Motherboard = group object
Cabinet = bigger group object
```

So one object can contain another object.

---

# Best Real-Life Example: File System

This is the easiest example.

```text
Folder
 ├── File 1
 ├── File 2
 └── Sub Folder
      ├── File 3
      ├── File 4
      └── Another Sub Folder
```

A **file** is a single item.

A **folder** is a group of items.

But we can perform the same operation on both.

Example:

```text
show name
calculate size
delete
move
copy
```

You can delete a file.
You can also delete a folder.

When you delete a folder, all files inside it are also deleted.

This is Composite Pattern.

---

# Main Idea

Composite pattern is used when we have this type of structure:

```text
Tree structure
Parent-child structure
Object inside object
Group inside group
```

Simple diagram:

```text
Component
   |
   |-----------------
   |                |
 Leaf           Composite
 Single item     Group item
                 |
                 |---- Leaf
                 |---- Leaf
                 |---- Composite
```

Meaning:

```text
Leaf = single object
Composite = group object
Component = common interface
```

---

# Important Terms

## 1. Component

Component is the common interface.

Both single object and group object follow this interface.

Example:

```java
interface FileSystemComponent {
    void showDetails();
}
```

Both `File` and `Folder` will implement this.

---

## 2. Leaf

Leaf means single object.

It does not contain child objects.

Example:

```text
File
Song
CPU
RAM
Mouse
Keyboard
```

A file cannot contain another file.

A song cannot contain another song.

So these are leaf objects.

---

## 3. Composite

Composite means group object.

It can contain leaf objects and other composite objects.

Example:

```text
Folder
SongGroup
Cabinet
MenuGroup
Department
```

A folder can contain files and folders.

A song group can contain songs and song groups.

This is the main power of Composite Pattern.

---

## 4. Client

Client is the code that uses these objects.

Client does not care whether it is a single object or group object.

It simply calls the same method.

Example:

```java
component.showDetails();
```

This can work for both:

```text
File
Folder
```

---

# Very Important Line

From your notes:

```text
Whatever operation you perform on leaf nodes,
the same operation should also be performed on composite object.
```

This is exactly the main rule.

Example:

```text
File has size
Folder also has size

File can be shown
Folder can also be shown

File can be deleted
Folder can also be deleted
```

The client uses both in the same way.

---

# Example 1: Song And SongGroup

Your slide has this example:

```text
SongGroup
 ├── Song
 ├── Song
 ├── SongGroup
 │    ├── Song
 │    └── Song
 └── Song
```

Here:

```text
Song = Leaf
SongGroup = Composite
```

A `SongGroup` can contain songs.

A `SongGroup` can also contain another `SongGroup`.

That is why it can create never-ending grouping.

---

# Java Example: Song And SongGroup

## Step 1: Common Component

```java
// Common interface for both Song and SongGroup
interface MusicComponent {
    void play();
}
```

---

## Step 2: Leaf Class

```java
// Leaf class
// Song is a single object.
// It does not contain child songs.
class Song implements MusicComponent {

    private String songName;

    public Song(String songName) {
        this.songName = songName;
    }

    @Override
    public void play() {
        System.out.println("Playing song: " + songName);
    }
}
```

---

## Step 3: Composite Class

```java
import java.util.ArrayList;
import java.util.List;

// Composite class
// SongGroup can contain Song and also another SongGroup.
class SongGroup implements MusicComponent {

    private String groupName;

    private List<MusicComponent> musicComponents = new ArrayList<>();

    public SongGroup(String groupName) {
        this.groupName = groupName;
    }

    public void add(MusicComponent component) {
        musicComponents.add(component);
    }

    public void remove(MusicComponent component) {
        musicComponents.remove(component);
    }

    @Override
    public void play() {
        System.out.println("Playing group: " + groupName);

        // Same operation is applied to all child objects.
        // Child can be Song or SongGroup.
        for (MusicComponent component : musicComponents) {
            component.play();
        }
    }
}
```

---

## Step 4: Client Code

```java
public class Main {
    public static void main(String[] args) {

        Song song1 = new Song("Song 1");
        Song song2 = new Song("Song 2");
        Song song3 = new Song("Song 3");

        SongGroup oldSongs = new SongGroup("Old Songs");
        oldSongs.add(song1);
        oldSongs.add(song2);

        SongGroup allSongs = new SongGroup("All Songs");
        allSongs.add(oldSongs);
        allSongs.add(song3);

        allSongs.play();
    }
}
```

Output:

```text
Playing group: All Songs
Playing group: Old Songs
Playing song: Song 1
Playing song: Song 2
Playing song: Song 3
```

---

# What Happened Here?

This line is important:

```java
component.play();
```

The client does not know whether `component` is:

```text
Song
or
SongGroup
```

But both have the same method:

```java
play()
```

So the same operation works on single object and group object.

That is Composite Pattern.

---

# Example 2: File And Folder

This is the most common interview example.

```text
FileSystemItem
   |
   |-------------|
   |             |
 File          Folder
 Leaf          Composite
```

A file has size.

A folder has size.

Folder size is the sum of all files and sub-folders inside it.

---

## Java Code: File System Example

```java
import java.util.ArrayList;
import java.util.List;

// Component
// Common interface for both File and Folder.
interface FileSystemItem {
    void showName();
    int getSize();
}
```

```java
// Leaf
// File is a single object.
// It does not contain child objects.
class File implements FileSystemItem {

    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public void showName() {
        System.out.println("File: " + name);
    }

    @Override
    public int getSize() {
        return size;
    }
}
```

```java
// Composite
// Folder can contain File and also another Folder.
class Folder implements FileSystemItem {

    private String name;

    private List<FileSystemItem> items = new ArrayList<>();

    public Folder(String name) {
        this.name = name;
    }

    public void add(FileSystemItem item) {
        items.add(item);
    }

    public void remove(FileSystemItem item) {
        items.remove(item);
    }

    @Override
    public void showName() {
        System.out.println("Folder: " + name);

        // Same operation is called on all child items.
        // Child can be File or Folder.
        for (FileSystemItem item : items) {
            item.showName();
        }
    }

    @Override
    public int getSize() {
        int totalSize = 0;

        // If child is File, it returns file size.
        // If child is Folder, it returns folder size recursively.
        for (FileSystemItem item : items) {
            totalSize = totalSize + item.getSize();
        }

        return totalSize;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {

        File file1 = new File("resume.pdf", 10);
        File file2 = new File("photo.jpg", 20);
        File file3 = new File("notes.txt", 5);

        Folder documents = new Folder("Documents");
        documents.add(file1);
        documents.add(file3);

        Folder pictures = new Folder("Pictures");
        pictures.add(file2);

        Folder root = new Folder("Root");
        root.add(documents);
        root.add(pictures);

        root.showName();

        System.out.println("Total size: " + root.getSize() + " MB");
    }
}
```

Output:

```text
Folder: Root
Folder: Documents
File: resume.pdf
File: notes.txt
Folder: Pictures
File: photo.jpg
Total size: 35 MB
```

---

# Why This Is Composite Pattern?

Because `File` and `Folder` are treated in the same way.

Both implement:

```java
FileSystemItem
```

So client can write:

```java
item.showName();
item.getSize();
```

without checking:

```text
Is it a file?
Is it a folder?
```

This removes messy `if-else`.

---

# Without Composite Pattern

Without Composite, code may look like this:

```java
if (item is File) {
    show file name;
} else if (item is Folder) {
    loop through folder;
    again check file or folder;
}
```

This becomes ugly when nesting is deep.

Example:

```text
Folder inside folder inside folder inside folder
```

Composite makes this clean.

---

# With Composite Pattern

Client simply says:

```java
root.showName();
root.getSize();
```

The internal recursion is handled by the objects themselves.

So the client code stays clean.

---

# Backend Example

In real backend systems, Composite Pattern is useful in tree-like data.

Examples:

```text
Menu and submenu
Role and permission tree
Organization hierarchy
Category and sub-category
File/folder system
Comment and reply system
Product bundle
UI component tree
Department and employee hierarchy
```

Example:

```text
Main Menu
 ├── Dashboard
 ├── Account
 │    ├── Balance
 │    └── Statement
 └── Payments
      ├── NEFT
      ├── RTGS
      └── UPI
```

Here:

```text
MenuItem = Leaf
MenuGroup = Composite
```

Both can have same method:

```java
render()
```

---

# Composite Pattern In Spring Boot Style

Suppose you are building menu permissions.

```text
MenuComponent
   |
   |----------------
   |               |
MenuItem        MenuGroup
```

`MenuItem` can be:

```text
View Balance
Download Statement
Make Payment
```

`MenuGroup` can be:

```text
Accounts
Payments
Admin
```

Both can have:

```java
boolean hasAccess(User user);
```

For a single menu item, check one permission.

For a menu group, check if user has access to any child item.

This is a practical backend use case.

---

# When Should We Use Composite Pattern?

Use Composite Pattern when:

```text
You have tree structure.
```

Use it when:

```text
Object can contain another object.
```

Use it when:

```text
Single object and group object should be treated the same way.
```

Use it when:

```text
You want to avoid many if-else checks.
```

Use it when:

```text
You want recursive behavior.
```

---

# When Should We Not Use Composite Pattern?

Do not use Composite Pattern when your structure is simple.

Example:

```text
Only one object
No child objects
No tree structure
No grouping
```

Then Composite will make the code unnecessarily complex.

---

# Easy Memory Trick

Remember this:

```text
Composite = Tree
```

Or:

```text
Composite = File and Folder
```

Or:

```text
Composite = Single object and group object behave same
```

Best memory line:

```text
Leaf is a single item.
Composite is a group of items.
Both follow the same interface.
```

---

# Composite Vs Adapter

| Point | Composite | Adapter |
|---|---|
| Main use | Tree structure | Interface conversion |
| Simple meaning | Object inside object | Converter |
| Example | File and folder | XML to JSON |
| Main benefit | Treat single and group same | Make incompatible systems work together |

---

# Composite Vs Bridge

| Point | Composite | Bridge |
|---|---|
| Main use | Parent-child tree structure | Separate abstraction and implementation |
| Example | Folder and file | Remote and device |
| Focus | Grouping objects | Avoid class explosion |
| Relation | Has children | Has implementation |

Simple memory:

```text
Composite = object tree
Bridge = two separate sides connected
```

---

# Composite Vs Decorator

| Point | Composite | Decorator |
|---|---|
| Main use | Grouping objects | Adding extra behavior |
| Example | Folder contains files | Coffee + milk + sugar |
| Focus | Tree structure | Extra features |
| Object count | One object can contain many children | One object wraps one object |

---

# Common Interview Mistake

Many people say:

```text
Composite means composition.
```

This is not fully correct.

Composition means:

```text
One class has another class object.
```

But Composite Design Pattern specifically means:

```text
Single object and group object follow same interface,
so client can treat both uniformly.
```

That is the important part.

---

# Final Interview-Ready Answer

**Composite Design Pattern is a structural design pattern used to represent tree-like structures where an object can contain other objects. It allows us to treat individual objects and groups of objects in the same way. In this pattern, we have a common component interface, leaf classes for single objects, and composite classes for group objects. For example, in a file system, a File is a leaf and a Folder is a composite because a folder can contain files and other folders. The client can call the same method like `showName()` or `getSize()` on both file and folder without worrying about their internal structure. This pattern is useful for menu trees, file systems, organization hierarchy, categories, and permission structures.**

---

# Very Short Interview Version

**Composite pattern is used for tree-like structures. It lets us treat a single object and a group of objects in the same way. For example, a file and a folder can both have operations like show name, delete, or get size. A folder can contain files and other folders, but the client can use both through the same interface.**
