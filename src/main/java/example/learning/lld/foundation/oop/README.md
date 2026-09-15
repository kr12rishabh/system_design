# Lesson 1 - OOP for Low Level Design

This lesson is not about memorizing college definitions.

The goal is to understand how OOP helps us create clean objects for real Low Level Design problems such as Parking Lot, Elevator, Hotel Management, Splitwise and WhatsApp.

---

## The Main LLD Question

While designing a system, keep asking:

> Which object should be responsible for this work?

For example, in a Hotel Management System:

```text
Room    -> room number, type, availability
Guest   -> guest information
Booking -> booking information
Payment -> payment behaviour
```

Do not put every responsibility inside one giant class.

---

# 1. Class and Object

A **class is a blueprint**.

An **object is a real instance created from that blueprint**.

```java
class Car {
    String brand;

    void drive() {
        System.out.println(brand + " is driving");
    }
}
```

```java
Car bmw = new Car();
Car audi = new Car();
```

`Car` is the class. `bmw` and `audi` are objects.

In an LLD interview, requirements help us discover classes such as `User`, `Order`, `Payment`, `Room`, `Vehicle`, `Message`, etc.

Run: `ClassAndObjectDemo.java`

---

# 2. Encapsulation

Simple meaning:

> Protect an object's data and control how that data changes.

Bad design:

```java
class BankAccount {
    public double balance;
}
```

Anybody could write:

```java
account.balance = -50000;
```

Better design:

```java
class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }
}
```

Now `BankAccount` controls its own state.

Run: `EncapsulationDemo.java`

---

# 3. Abstraction

Simple meaning:

> Show what an object can do and hide unnecessary internal details.

```java
interface Payment {
    void pay(double amount);
}
```

The caller only needs to know:

```java
payment.pay(500);
```

The caller does not need to know every bank call or validation happening internally.

Run: `AbstractionDemo.java`

---

# 4. Inheritance

Inheritance represents an **IS-A relationship**.

```java
class Vehicle {
    void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {
}
```

```text
Car IS-A Vehicle
```

Do not use inheritance only because two classes have similar code.

Use it when the child truly represents a specialized form of the parent.

Run: `InheritanceDemo.java`

---

# 5. Polymorphism

Simple meaning:

> Same contract, different behaviour.

```java
Notification notification = new EmailNotification();
notification.send("Hello");

notification = new SmsNotification();
notification.send("Hello");
```

The reference type is the same, but the implementation changes.

Polymorphism helps us remove large `if/else` blocks and is heavily used by design patterns.

Run: `PolymorphismDemo.java`

---

# 6. Interface

An interface is a **contract**.

```java
interface Notification {
    void send(String message);
}
```

Any class implementing it promises to provide `send()`.

Examples:

```text
Notification
    |
    +-- EmailNotification
    +-- SmsNotification
    +-- PushNotification
```

Higher-level code can work with `Notification` instead of depending on one fixed notification type.

Run: `InterfaceDemo.java`

---

# 7. Abstract Class

An abstract class is useful when child classes share common state or common behaviour, but still need to implement some behaviour themselves.

```java
abstract class Vehicle {
    protected String number;

    void printNumber() {
        System.out.println(number);
    }

    abstract void drive();
}
```

Simple way to remember:

```text
Interface
-> mainly describes a contract/capability

Abstract class
-> can provide common state + common code + incomplete behaviour
```

Run: `AbstractClassDemo.java`

---

# 8. IS-A vs HAS-A

## IS-A

```text
Car IS-A Vehicle
Admin IS-A User
Dog IS-A Animal
```

Usually represented with inheritance.

## HAS-A

```text
Car HAS-A Engine
Order HAS-A Payment
Hotel HAS-A Rooms
User HAS-A Address
```

Usually represented by one object containing or using another object.

Run: `IsAHasADemo.java`

---

# 9. Composition Over Inheritance

Composition means building an object using other objects.

Example:

```text
Car HAS-A DriveBehaviour
```

Instead of creating many subclasses just to change driving behaviour, we can inject a behaviour object.

```java
class Car {
    private DriveBehaviour driveBehaviour;
}
```

This makes the design more flexible.

Later this same idea will naturally lead us to the **Strategy Design Pattern**.

Run: `CompositionOverInheritanceDemo.java`

---

# 10. First Combined LLD Example - Payment System

Bad design:

```java
class PaymentService {
    void pay(String type) {
        if (type.equals("UPI")) {
            // UPI logic
        } else if (type.equals("CARD")) {
            // Card logic
        }
    }
}
```

As payment types increase, this class keeps changing.

Better design:

```text
Payment
   |
   +-- UpiPayment
   +-- CreditCardPayment
```

Then `PaymentService` depends on `Payment` instead of checking every payment type itself.

Run: `OopPaymentDemo.java`

---

# What To Remember

```text
Class          = blueprint
Object         = real instance
Encapsulation  = protect and control data
Abstraction    = hide unnecessary details
Inheritance    = IS-A
Composition    = HAS-A / build using other objects
Polymorphism   = same contract, different behaviour
Interface      = contract
Abstract class = common base + incomplete behaviour
```

The important goal is not to remember definitions word-for-word.

The goal is to understand which object should own which responsibility and how objects should work together.

---

# Lesson 1 Practice

## Practice 1 - Notification System

Design a system where Email, SMS and Push Notification all use one common `Notification` contract.

Try it yourself first.

Solution: `practice/NotificationSystemSolution.java`

## Practice 2 - Order HAS-A Payment

Create an `Order` that contains a `Payment` object. The order should be able to checkout using UPI or Card without changing the `Order` class.

Try it yourself first.

Solution: `practice/OrderPaymentCompositionSolution.java`

---

# Files In This Lesson

```text
oop/
|
|-- README.md
|-- ClassAndObjectDemo.java
|-- EncapsulationDemo.java
|-- AbstractionDemo.java
|-- InheritanceDemo.java
|-- PolymorphismDemo.java
|-- InterfaceDemo.java
|-- AbstractClassDemo.java
|-- IsAHasADemo.java
|-- CompositionOverInheritanceDemo.java
|-- OopPaymentDemo.java
|
`-- practice/
    |-- NotificationSystemSolution.java
    `-- OrderPaymentCompositionSolution.java
```

---

# Before Lesson 2

You should be able to explain these in your own simple words:

```text
Why should fields often be private?
What is the difference between IS-A and HAS-A?
Why can interfaces reduce if/else code?
What does polymorphism mean in actual Java code?
Why can composition be more flexible than inheritance?
```

Next lesson: **Class Relationships - Association, Aggregation, Composition, Inheritance and Dependency**.
