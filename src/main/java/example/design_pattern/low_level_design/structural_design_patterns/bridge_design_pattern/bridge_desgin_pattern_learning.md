## Summary

**Bridge Design Pattern is used when we want to separate “what we do” from “how we do it”, so both sides can change independently.**

## One-Line Answer

**Bridge pattern separates abstraction and implementation by using composition, so we avoid creating too many classes.**

---

# Very Simple Meaning

Bridge means:

```text
One side = high-level logic
Other side = actual implementation
Bridge = connection between both
```

Simple example:

```text
Remote  --->  TV
Remote  --->  Radio
Remote  --->  Speaker
```

Here:

```text
Remote = abstraction
Device = implementation
```

Remote does not care whether the device is TV, Radio, or Speaker.

It only knows:

```text
device.turnOn()
device.turnOff()
```

That is Bridge Pattern.

---

# Real-Life Example

Suppose you have:

```text
Basic Remote
Advanced Remote
```

And you have:

```text
TV
Radio
Speaker
```

Without Bridge, you may create many classes:

```text
BasicRemoteForTV
BasicRemoteForRadio
BasicRemoteForSpeaker

AdvancedRemoteForTV
AdvancedRemoteForRadio
AdvancedRemoteForSpeaker
```

This becomes messy.

Now imagine more remotes and more devices.

Classes will keep increasing.

Bridge solves this problem.

With Bridge:

```text
Remote has a Device
```

So we can combine any remote with any device.

```text
BasicRemote + TV
BasicRemote + Radio
AdvancedRemote + TV
AdvancedRemote + Speaker
```

No class explosion.

---

# Simple Diagram

```text
            Device Interface
                 ^
                 |
        ---------------------
        |                   |
       TV                 Radio


Remote Abstraction
       |
       | has-a Device
       v

Remote  ---------------->  Device
```

Meaning:

```text
Remote does not extend TV.
Remote contains Device.
```

This is very important.

Bridge pattern mostly uses **has-a relationship**.

---

# Important Line To Remember

```text
Bridge Pattern separates abstraction from implementation.
```

In simple English:

```text
It separates the main control logic from the actual working logic.
```

---

# Main Terms In Bridge Pattern

## 1. Abstraction

This is the high-level part.

Example:

```text
Remote
Notification
Payment
Report
```

It defines what client uses.

---

## 2. Refined Abstraction

This is a more specific version of abstraction.

Example:

```text
BasicRemote
AdvancedRemote
```

or

```text
OtpNotification
PaymentNotification
LoginNotification
```

---

## 3. Implementor

This is the implementation interface.

Example:

```text
Device
MessageSender
PaymentGateway
ReportExporter
```

---

## 4. Concrete Implementor

This is the actual implementation.

Example:

```text
TV
Radio
EmailSender
SmsSender
RazorpayGateway
PayUGateway
PdfExporter
ExcelExporter
```

---

# Java Example: Remote And Device

## Step 1: Implementation Interface

```java
// This is the implementation side.
// Any device should know how to turn on and turn off.
interface Device {
    void turnOn();
    void turnOff();
    void setVolume(int volume);
}
```

---

## Step 2: Concrete Implementations

```java
// Concrete implementation 1
class TV implements Device {

    @Override
    public void turnOn() {
        System.out.println("TV is turned ON");
    }

    @Override
    public void turnOff() {
        System.out.println("TV is turned OFF");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("TV volume set to " + volume);
    }
}
```

```java
// Concrete implementation 2
class Radio implements Device {

    @Override
    public void turnOn() {
        System.out.println("Radio is turned ON");
    }

    @Override
    public void turnOff() {
        System.out.println("Radio is turned OFF");
    }

    @Override
    public void setVolume(int volume) {
        System.out.println("Radio volume set to " + volume);
    }
}
```

---

## Step 3: Abstraction

```java
// This is the abstraction side.
// Remote does not care whether the device is TV or Radio.
// Remote only talks to Device interface.
abstract class Remote {

    protected Device device;

    public Remote(Device device) {
        this.device = device;
    }

    abstract void powerOn();

    abstract void powerOff();
}
```

---

## Step 4: Refined Abstraction

```java
// Basic remote can turn device on and off.
class BasicRemote extends Remote {

    public BasicRemote(Device device) {
        super(device);
    }

    @Override
    void powerOn() {
        device.turnOn();
    }

    @Override
    void powerOff() {
        device.turnOff();
    }
}
```

```java
// Advanced remote has extra feature.
class AdvancedRemote extends Remote {

    public AdvancedRemote(Device device) {
        super(device);
    }

    @Override
    void powerOn() {
        device.turnOn();
    }

    @Override
    void powerOff() {
        device.turnOff();
    }

    public void mute() {
        device.setVolume(0);
        System.out.println("Device muted");
    }
}
```

---

## Step 5: Client Code

```java
public class Main {
    public static void main(String[] args) {

        Device tv = new TV();
        Remote tvRemote = new BasicRemote(tv);

        tvRemote.powerOn();
        tvRemote.powerOff();

        Device radio = new Radio();
        AdvancedRemote radioRemote = new AdvancedRemote(radio);

        radioRemote.powerOn();
        radioRemote.mute();
        radioRemote.powerOff();
    }
}
```

Output:

```text
TV is turned ON
TV is turned OFF
Radio is turned ON
Radio volume set to 0
Device muted
Radio is turned OFF
```

---

# What Is The Benefit Here?

We can change remote types separately.

```text
BasicRemote
AdvancedRemote
SmartRemote
```

We can also change device types separately.

```text
TV
Radio
Speaker
Projector
```

Both sides are independent.

That is the real power of Bridge Pattern.

---

# Backend Example: Notification System

Suppose we have different notification types:

```text
OTP Notification
Payment Notification
Login Notification
```

And different sending channels:

```text
Email
SMS
WhatsApp
```

Without Bridge, we may create many classes:

```text
EmailOtpNotification
SmsOtpNotification
WhatsAppOtpNotification

EmailPaymentNotification
SmsPaymentNotification
WhatsAppPaymentNotification

EmailLoginNotification
SmsLoginNotification
WhatsAppLoginNotification
```

This is class explosion.

Bridge solves this.

---

# Backend Bridge Design

```text
Notification  --->  MessageSender
```

Where:

```text
Notification = abstraction
MessageSender = implementation
```

---

## Sender Interface

```java
interface MessageSender {
    void sendMessage(String to, String message);
}
```

---

## Concrete Senders

```java
class EmailSender implements MessageSender {

    @Override
    public void sendMessage(String to, String message) {
        System.out.println("Sending EMAIL to " + to + ": " + message);
    }
}
```

```java
class SmsSender implements MessageSender {

    @Override
    public void sendMessage(String to, String message) {
        System.out.println("Sending SMS to " + to + ": " + message);
    }
}
```

---

## Notification Abstraction

```java
abstract class Notification {

    protected MessageSender messageSender;

    public Notification(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    abstract void send(String to);
}
```

---

## Specific Notifications

```java
class OtpNotification extends Notification {

    public OtpNotification(MessageSender messageSender) {
        super(messageSender);
    }

    @Override
    void send(String to) {
        messageSender.sendMessage(to, "Your OTP is 123456");
    }
}
```

```java
class PaymentNotification extends Notification {

    public PaymentNotification(MessageSender messageSender) {
        super(messageSender);
    }

    @Override
    void send(String to) {
        messageSender.sendMessage(to, "Your payment was successful");
    }
}
```

---

## Client Code

```java
public class Main {
    public static void main(String[] args) {

        MessageSender emailSender = new EmailSender();
        Notification otpByEmail = new OtpNotification(emailSender);
        otpByEmail.send("rishabh@example.com");

        MessageSender smsSender = new SmsSender();
        Notification paymentBySms = new PaymentNotification(smsSender);
        paymentBySms.send("9876543210");
    }
}
```

Output:

```text
Sending EMAIL to rishabh@example.com: Your OTP is 123456
Sending SMS to 9876543210: Your payment was successful
```

---

# Why This Is Bridge Pattern?

Because we separated two things:

```text
Notification type
```

and

```text
Sending channel
```

Now notification type can grow separately:

```text
OtpNotification
PaymentNotification
LoginNotification
```

Sending channel can grow separately:

```text
EmailSender
SmsSender
WhatsAppSender
PushNotificationSender
```

We can mix them easily.

---

# Bridge Pattern In Spring Boot

In Spring Boot, Bridge Pattern can be useful when your service supports multiple providers.

Example:

```text
PaymentService  --->  PaymentGateway
```

You may have different gateways:

```text
RazorpayGateway
PayUGateway
StripeGateway
BankGateway
```

Your business service should not depend directly on one gateway.

Better design:

```java
interface PaymentGateway {
    void pay(double amount);
}
```

Then:

```java
class RazorpayGateway implements PaymentGateway {
    public void pay(double amount) {
        System.out.println("Paid using Razorpay: " + amount);
    }
}
```

```java
class PayUGateway implements PaymentGateway {
    public void pay(double amount) {
        System.out.println("Paid using PayU: " + amount);
    }
}
```

And abstraction:

```java
abstract class Payment {

    protected PaymentGateway paymentGateway;

    public Payment(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    abstract void makePayment(double amount);
}
```

Specific payment:

```java
class UpiPayment extends Payment {

    public UpiPayment(PaymentGateway paymentGateway) {
        super(paymentGateway);
    }

    @Override
    void makePayment(double amount) {
        paymentGateway.pay(amount);
    }
}
```

Now you can do:

```java
Payment payment = new UpiPayment(new RazorpayGateway());
payment.makePayment(500);
```

Tomorrow:

```java
Payment payment = new UpiPayment(new PayUGateway());
payment.makePayment(500);
```

Your payment type and payment gateway are separate.

---

# Adapter Vs Bridge

This is very important for interviews.

| Point             | Adapter                               | Bridge                                  |
| ----------------- | ------------------------------------- | --------------------------------------- |
| Main use          | Connect incompatible interfaces       | Separate abstraction and implementation |
| Usually used when | Existing code does not match new code | We want flexible design from start      |
| Example           | XML to JSON converter                 | Remote and Device                       |
| Focus             | Compatibility                         | Flexibility                             |
| Relation          | Adapter wraps old class               | Abstraction has implementation          |

Simple memory:

```text
Adapter = converter
Bridge = separator
```

Adapter says:

```text
These two systems cannot talk, so I will convert.
```

Bridge says:

```text
These two parts should grow separately, so I will separate them.
```

---

# Bridge Vs Strategy

Bridge and Strategy can look similar because both use interfaces and composition.

But simple difference:

| Pattern  | Meaning                                   |
| -------- | ----------------------------------------- |
| Bridge   | Separates abstraction from implementation |
| Strategy | Changes algorithm or behavior at runtime  |

Example:

```text
Bridge = Notification type separate from sending channel
Strategy = Choose discount calculation logic
```

---

# When Should We Use Bridge Pattern?

Use Bridge Pattern when:

```text
One class has multiple independent variations.
```

Example:

```text
Notification type can change
Sending channel can also change
```

Use it when:

```text
You are getting too many combinations of classes.
```

Example:

```text
EmailOtpNotification
SmsOtpNotification
EmailPaymentNotification
SmsPaymentNotification
```

Use it when:

```text
You want abstraction and implementation to change independently.
```

Use it when:

```text
You want to avoid tight coupling.
```

---

# When Should We Not Use Bridge Pattern?

Do not use Bridge Pattern when the design is very simple.

If you have only one implementation and it will not change, Bridge may make code extra complex.

Example:

```text
Only one notification type
Only one sender
No future changes
```

Then simple class is enough.

---

# Easy Memory Trick

Remember this:

```text
Bridge = two separate family trees connected by has-a relation.
```

Example:

```text
Remote family        Device family
BasicRemote          TV
AdvancedRemote       Radio
SmartRemote          Speaker
```

Remote has a Device.

That connection is the bridge.

---

# Final Interview-Ready Answer

**Bridge design pattern is a structural design pattern that separates abstraction from implementation so both can change independently. It is useful when a class has multiple independent variations and creating subclasses for every combination would increase the number of classes. Bridge solves this by using composition. The abstraction contains a reference to the implementation interface. For example, a Remote can work with a TV, Radio, or Speaker without creating separate classes like TVRemote or RadioRemote. In backend systems, we can use Bridge for notification systems where notification type and sending channel are separate, like OTP notification with Email, SMS, or WhatsApp sender. This keeps code flexible, reusable, and less tightly coupled.**

---

# Very Short Interview Version

**Bridge pattern separates abstraction from implementation. It is used when two parts of the system can vary independently. Instead of creating many subclasses for every combination, we connect both sides using composition. For example, Notification can use EmailSender, SmsSender, or WhatsAppSender without creating separate classes for every notification-channel combination.**
