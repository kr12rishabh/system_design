# Lesson 1 - OOP for Low Level Design

We are not learning OOP only for definitions.

We are learning OOP because almost every LLD problem is made using objects that have:

- data
- behaviour
- relationships with other objects

---

# 1. Class

A class is a blueprint.

Example:

```java
class Car {
    String brand;

    void drive() {
        System.out.println("Car is driving");
    }
}
```

`Car` tells Java what a car object should contain.

---

# 2. Object

An object is a real instance of a class.

```java
Car car = new Car();
```

Here:

- `Car` = class
- `car` = reference variable
- `new Car()` = object creation

---

# 3. Encapsulation

Simple meaning:

> Keep object data protected and control how somebody changes it.

Bad:

```java
class BankAccount {
    public double balance;
}
```

Anybody can do this:

```java
account.balance = -100000;
```

Better:

```java
class BankAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

Now the class controls how balance is changed.

---

# 4. Abstraction

Simple meaning:

> Show what an object can do. Hide unnecessary internal details.

Example:

```java
interface Payment {
    void pay(double amount);
}
```

A user of `Payment` only needs to know that payment can happen.

The user does not need to know all internal UPI or card steps.

---

# 5. Inheritance

Simple meaning:

> One class gets common behaviour from another class.

```java
class Vehicle {
    void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {
}
```

A `Car` IS-A `Vehicle`.

This is called an **IS-A relationship**.

Use inheritance only when the relationship is truly IS-A.

---

# 6. Polymorphism

Simple meaning:

> Same reference type, different behaviour.

```java
Payment payment = new UpiPayment();
payment.pay(500);

payment = new CreditCardPayment();
payment.pay(500);
```

Same `Payment` reference.

Different implementation.

This is extremely important in LLD because it helps us avoid large `if/else` blocks.

---

# 7. Interface

An interface describes a contract.

```java
interface Payment {
    void pay(double amount);
}
```

Any class implementing `Payment` promises that it can perform `pay()`.

```java
class UpiPayment implements Payment {
    @Override
    public void pay(double amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}
```

---

# 8. Abstract Class

An abstract class is useful when multiple child classes share some common state or behaviour but still need their own implementation for some methods.

```java
abstract class Vehicle {

    protected String number;

    public Vehicle(String number) {
        this.number = number;
    }

    public void printNumber() {
        System.out.println(number);
    }

    public abstract void drive();
}
```

---

# 9. IS-A vs HAS-A

This is very important in LLD.

## IS-A

```text
Car IS-A Vehicle
Dog IS-A Animal
```

Normally represented using inheritance.

## HAS-A

```text
Car HAS-A Engine
Order HAS-A Payment
Hotel HAS-A List of Rooms
```

Normally represented using composition.

Example:

```java
class Engine {
    void start() {
        System.out.println("Engine started");
    }
}

class Car {
    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }
}
```

---

# 10. Composition Over Inheritance

In many LLD problems, composition is more flexible than inheritance.

Suppose different cars can have different drive behaviours.

Instead of creating many inheritance combinations, we can give the car a behaviour object.

```text
Car HAS-A DriveStrategy
```

This idea later becomes very useful in the **Strategy Design Pattern**.

---

# First Important LLD Example - Payment System

Bad design:

```java
class PaymentService {

    public void pay(String type, double amount) {
        if (type.equals("UPI")) {
            System.out.println("UPI payment");
        } else if (type.equals("CARD")) {
            System.out.println("Card payment");
        }
    }
}
```

Problem:

Every time we add a new payment type, `PaymentService` must change.

Better idea:

```text
Payment
   |
   +-- UpiPayment
   |
   +-- CreditCardPayment
```

Then `PaymentService` works with the `Payment` interface instead of checking payment types itself.

See `OopPaymentDemo.java` in this package for the runnable example.

---

# What You Should Remember

```text
Class       -> blueprint
Object      -> real instance
Encapsulation -> protect and control data
Abstraction -> show necessary behaviour, hide details
Inheritance -> IS-A
Composition -> HAS-A
Polymorphism -> same contract, different behaviour
Interface   -> contract
```

The most important LLD question is not:

> Which OOP definition do I remember?

The better question is:

> Which object should own this responsibility?

---

# Practice

Before moving ahead, try to design these using only OOP:

1. `Notification` with Email, SMS and Push implementations.
2. `Vehicle` with Car, Bike and Truck.
3. `Shape` with Circle and Rectangle.
4. `Order` that HAS-A Payment object.
5. `Computer` that HAS-A Processor and Memory.

Next lesson: **Class Relationships - Association, Aggregation, Composition, Inheritance and Dependency**.
