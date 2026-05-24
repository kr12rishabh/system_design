# Learning Bridge Design Pattern

## Simple Meaning

Bridge Design Pattern means:

```text
Separate the high-level control from the actual implementation.
```

In very simple words:

```text
Remote controls the device.
Device does the actual work.
```

The remote should not be tightly connected to only one device. A remote should be able to work with different devices like TV, radio, speaker, etc.

That connection between:

```text
Remote side
Device side
```

is called the bridge.

## Why We Need Bridge Pattern

Suppose we have different remotes:

```text
Mute Remote
Pause Remote
Record Remote
```

And different devices:

```text
TV
Radio
Speaker
```

Without Bridge Pattern, we may create many classes:

```text
MuteRemoteForTv
MuteRemoteForRadio
MuteRemoteForSpeaker

PauseRemoteForTv
PauseRemoteForRadio
PauseRemoteForSpeaker

RecordRemoteForTv
RecordRemoteForRadio
RecordRemoteForSpeaker
```

This becomes hard to manage.

Bridge Pattern solves this by saying:

```text
Remote has a Device.
```

So we can combine any remote with any device.

Example:

```text
Mute Remote  + TV
Pause Remote + TV
Mute Remote  + Radio
Pause Remote + Speaker
```

We do not need to create every possible combination as a separate class.

## Important Idea

Bridge Pattern uses composition.

Composition means:

```text
One class contains another class object.
```

In this package:

```java
private EntertainmentDevice device;
```

This line inside `RemoteButton` is the main bridge.

It means:

```text
RemoteButton has an EntertainmentDevice.
```

The remote does not directly know whether the device is a TV, radio, or something else.

It only knows that the device is an `EntertainmentDevice`.

## Your Package Structure

Your package contains these classes:

```text
bridge_design_pattern
|
|-- EntertainmentDevice.java
|-- TvDevice.java
|-- RemoteButton.java
|-- TvRemoteMute.java
|-- TvRemotePaused.java
|-- TestRemoteMain.java
```

Each class has a specific role in the Bridge Pattern.

## Bridge Pattern Roles In Your Code

### 1. EntertainmentDevice

File:

```text
EntertainmentDevice.java
```

This is the implementor side.

It represents a general entertainment device.

Example devices can be:

```text
TV
Radio
Speaker
Projector
```

Your class:

```java
public abstract class EntertainmentDevice {
    public int deviceState;
    public int maxSetting;
    public int volumeLevel = 0;

    public abstract void buttonFivePressed();

    public abstract void buttonSixPressed();
}
```

This class says:

```text
Every entertainment device must define what button five does.
Every entertainment device must define what button six does.
```

But this class does not decide the exact behavior.

For a TV:

```text
button five = channel down
button six = channel up
```

For a radio, it could be:

```text
button five = previous station
button six = next station
```

So `EntertainmentDevice` gives the common structure, but the concrete device decides the real behavior.

It also has common methods:

```java
public void buttonSevenPressed() {
    volumeLevel++;
    System.out.println(" Volume level at : " + volumeLevel);
}

public void buttonEightPressed() {
    volumeLevel--;
    System.out.println("Volume level at : " + volumeLevel);
}
```

These methods are common for all devices.

Meaning:

```text
button seven = volume up
button eight = volume down
```

### 2. TvDevice

File:

```text
TvDevice.java
```

This is the concrete implementor.

It is the actual device implementation.

Your class:

```java
public class TvDevice extends EntertainmentDevice {

    public TvDevice(int newDeviceState, int newMaxSetting) {
        deviceState = newDeviceState;
        maxSetting = newMaxSetting;
    }

    @Override
    public void buttonFivePressed() {
        System.out.println("Channel Down : ");
        deviceState--;
    }

    @Override
    public void buttonSixPressed() {
        System.out.println("Channel Up : ");
        deviceState++;
    }
}
```

This class gives the real TV behavior.

For TV:

```text
button five decreases the channel
button six increases the channel
```

So this class handles the actual device logic.

### 3. RemoteButton

File:

```text
RemoteButton.java
```

This is the abstraction side.

It represents a general remote.

Your class:

```java
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
```

This class contains:

```java
private EntertainmentDevice device;
```

This is the bridge.

The remote has a device.

When we press button five on the remote:

```java
public void buttonFivePressed(){
    device.buttonFivePressed();
}
```

The remote does not perform the TV logic itself.

It simply forwards the call to the device.

So the flow is:

```text
Remote button five pressed
Remote calls device.buttonFivePressed()
Actual device decides what happens
```

For a TV, channel goes down.

For a radio, station could go down.

This is why the remote and device are loosely connected.

### 4. TvRemoteMute

File:

```text
TvRemoteMute.java
```

This is a refined abstraction.

It is a specific type of remote.

Your class:

```java
public class TvRemoteMute extends RemoteButton {

    public TvRemoteMute(EntertainmentDevice newDevice) {
        super(newDevice);
    }

    @Override
    public void buttonNinePressed() {
        System.out.println("Tv was muted : ");
    }
}
```

This remote says:

```text
button nine = mute
```

It still uses the same device bridge from `RemoteButton`.

### 5. TvRemotePaused

File:

```text
TvRemotePaused.java
```

This is also a refined abstraction.

It is another specific type of remote.

Your class:

```java
public class TvRemotePaused extends RemoteButton {

    public TvRemotePaused(EntertainmentDevice newDevice) {
        super(newDevice);
    }

    @Override
    public void buttonNinePressed() {
        System.out.println("Tv was paused : ");
    }
}
```

This remote says:

```text
button nine = pause
```

So now we have two remotes:

```text
TvRemoteMute
TvRemotePaused
```

Both can work with an `EntertainmentDevice`.

## Simple Diagram Of Your Code

```text
Client
  |
  v
RemoteButton --------------------> EntertainmentDevice
  ^                                      ^
  |                                      |
  |                                      |
TvRemoteMute                       TvDevice
TvRemotePaused
```

Meaning:

```text
TvRemoteMute is a RemoteButton.
TvRemotePaused is a RemoteButton.
TvDevice is an EntertainmentDevice.
RemoteButton has an EntertainmentDevice.
```

The most important relation is:

```text
RemoteButton has an EntertainmentDevice.
```

That is the bridge.

## How This Code Was Created Step By Step

### Step 1: Find Two Things That Can Change Separately

In this example, two things can change:

```text
1. Remote type
2. Device type
```

Remote types:

```text
Mute remote
Pause remote
```

Device types:

```text
TV
Radio
Speaker
```

Because both sides can grow independently, Bridge Pattern is useful.

### Step 2: Create The Device Abstraction

First, we create a common parent for all devices:

```java
public abstract class EntertainmentDevice
```

This class defines common device behavior.

It says:

```text
Every device should handle button five.
Every device should handle button six.
```

That is why these methods are abstract:

```java
public abstract void buttonFivePressed();
public abstract void buttonSixPressed();
```

The parent class does not know the exact device.

It only creates a common rule for all devices.

### Step 3: Create The Concrete Device

Then we create the TV device:

```java
public class TvDevice extends EntertainmentDevice
```

Now TV gives real meaning to button five and button six:

```text
button five = channel down
button six = channel up
```

So `TvDevice` implements:

```java
buttonFivePressed()
buttonSixPressed()
```

This is the actual implementation.

### Step 4: Create The Remote Abstraction

Then we create:

```java
public abstract class RemoteButton
```

This class represents a general remote.

It contains:

```java
private EntertainmentDevice device;
```

This is where the remote connects to the device.

The constructor receives a device:

```java
public RemoteButton(EntertainmentDevice newDevice) {
    this.device = newDevice;
}
```

So when we create a remote, we must give it a device.

Example:

```java
new TvRemoteMute(new TvDevice(1, 200));
```

This means:

```text
Create a mute remote.
Connect it with a TV device.
```

### Step 5: Delegate Remote Button Presses To Device

Inside `RemoteButton`, button five is written like this:

```java
public void buttonFivePressed(){
    device.buttonFivePressed();
}
```

This means:

```text
Remote receives the button press.
Remote sends the work to the device.
Device performs the actual action.
```

Same for button six:

```java
public void buttonSixPressed(){
    device.buttonSixPressed();
}
```

This delegation keeps remote logic separate from device logic.

### Step 6: Create Specific Remotes

Now we create different remotes.

Mute remote:

```java
public class TvRemoteMute extends RemoteButton
```

Pause remote:

```java
public class TvRemotePaused extends RemoteButton
```

Both remotes share button five and button six behavior from `RemoteButton`.

But both define their own button nine behavior.

Mute remote:

```java
public void buttonNinePressed() {
    System.out.println("Tv was muted : ");
}
```

Pause remote:

```java
public void buttonNinePressed() {
    System.out.println("Tv was paused : ");
}
```

This shows that the remote side can change independently.

### Step 7: Connect Remote And Device In Client Code

In `TestRemoteMain.java`, you create objects like this:

```java
RemoteButton theTv = new TvRemoteMute(new TvDevice(1, 200));
RemoteButton theTv2 = new TvRemotePaused(new TvDevice(1, 200));
```

First object:

```text
TvRemoteMute + TvDevice
```

Second object:

```text
TvRemotePaused + TvDevice
```

This is the Bridge Pattern in action.

The remote and device are connected at object creation time.

## Execution Flow Example

Code:

```java
RemoteButton theTv = new TvRemoteMute(new TvDevice(1, 200));
theTv.buttonFivePressed();
```

Flow:

```text
1. Client calls theTv.buttonFivePressed()
2. theTv is a TvRemoteMute object
3. TvRemoteMute inherits buttonFivePressed() from RemoteButton
4. RemoteButton calls device.buttonFivePressed()
5. device is a TvDevice object
6. TvDevice.buttonFivePressed() runs
7. TV channel goes down
```

So even though the client uses a remote, the actual work is done by the device.

Another example:

```java
theTv.buttonNinePressed();
```

Flow:

```text
1. Client calls theTv.buttonNinePressed()
2. theTv is a TvRemoteMute object
3. TvRemoteMute.buttonNinePressed() runs
4. Output prints: Tv was muted
```

Here button nine is special remote behavior.

## Why This Is Bridge Pattern

This is Bridge Pattern because:

```text
RemoteButton and EntertainmentDevice are separate hierarchies.
```

Remote hierarchy:

```text
RemoteButton
|
|-- TvRemoteMute
|-- TvRemotePaused
```

Device hierarchy:

```text
EntertainmentDevice
|
|-- TvDevice
```

They are connected using composition:

```java
private EntertainmentDevice device;
```

So the remote side and device side can grow independently.

## How To Add A New Device

Suppose you want to add a radio.

You can create:

```java
public class RadioDevice extends EntertainmentDevice {
    @Override
    public void buttonFivePressed() {
        System.out.println("Previous station");
        deviceState--;
    }

    @Override
    public void buttonSixPressed() {
        System.out.println("Next station");
        deviceState++;
    }
}
```

Then you can use the same remotes:

```java
RemoteButton radioRemote = new TvRemoteMute(new RadioDevice());
```

The remote class does not need to change.

Only a new device class is added.

## How To Add A New Remote

Suppose you want to add a record remote.

You can create:

```java
public class TvRemoteRecord extends RemoteButton {
    public TvRemoteRecord(EntertainmentDevice newDevice) {
        super(newDevice);
    }

    @Override
    public void buttonNinePressed() {
        System.out.println("Recording started");
    }
}
```

Then you can use it with the same TV device:

```java
RemoteButton recordRemote = new TvRemoteRecord(new TvDevice(1, 200));
```

The `TvDevice` class does not need to change.

Only a new remote class is added.

## Main Benefit

Bridge Pattern prevents too many classes.

Instead of this:

```text
MuteRemoteForTv
MuteRemoteForRadio
PauseRemoteForTv
PauseRemoteForRadio
```

We use this:

```text
RemoteButton has EntertainmentDevice
```

Then we can combine objects freely:

```text
new TvRemoteMute(new TvDevice(...))
new TvRemotePaused(new TvDevice(...))
new TvRemoteMute(new RadioDevice(...))
```

## Small Notes About Current Code

### Main Method

Your `TestRemoteMain` currently has:

```java
static void main()
```

If you want to run it directly as a normal Java application, use:

```java
public static void main(String[] args)
```

### deviceFeedBack Method

Your current method:

```java
public void deviceFeedBack() {
    if (deviceState > maxSetting || deviceState < 0) {
        System.out.println("On : " + deviceState);
    }
}
```

This prints only when `deviceState` is greater than `maxSetting` or less than `0`.

So it behaves more like a warning for invalid state.

If you want it to always show the current channel or current state, then the condition would need to be changed.

## One-Line Revision

```text
Bridge Pattern separates remote control logic from device implementation logic by connecting them through composition.
```

## Remember This

```text
RemoteButton has an EntertainmentDevice.
```

That single idea is the heart of this example.
