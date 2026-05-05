

# 1. OOP pillars

---
## Summary

Java OOP has **4 main pillars**:

```text
1. Encapsulation
2. Abstraction
3. Inheritance
4. Polymorphism
```

These pillars help us write code that is **clean, reusable, secure, flexible, and easy to maintain**.

## One-Line Answer

**The four pillars of OOP are Encapsulation, Abstraction, Inheritance, and Polymorphism.**

---

# 1. Encapsulation

Encapsulation means **wrapping data and methods inside one class** and protecting the data from direct access.

In simple words:

```text
Keep variables private.
Access them using methods.
```

Example:

```java
class BankAccount {

    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

Here, `balance` is private.

So nobody can directly do this:

```java
account.balance = -5000;
```

That protects the data.

## Why Encapsulation Is Useful

```text
It protects data.
It gives control over changes.
It improves security.
It keeps code clean.
```

---

# 2. Abstraction

Abstraction means **showing only important details and hiding internal implementation**.

In simple words:

```text
Show what an object does.
Hide how it does it.
```

Example:

```java
interface Payment {

    void pay(double amount);
}
```

Implementation:

```java
class UpiPayment implements Payment {

    public void pay(double amount) {
        System.out.println("Payment done using UPI: " + amount);
    }
}
```

```java
class CardPayment implements Payment {

    public void pay(double amount) {
        System.out.println("Payment done using Card: " + amount);
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Payment payment = new UpiPayment();
        payment.pay(1000);
    }
}
```

Here, the caller only knows:

```text
payment.pay(1000)
```

The caller does not need to know how UPI payment works internally.

## Why Abstraction Is Useful

```text
It hides complexity.
It makes code easier to use.
It helps in loose coupling.
It makes future changes easier.
```

---

# 3. Inheritance

Inheritance means **one class can reuse properties and methods of another class**.

In simple words:

```text
Child class can use parent class features.
```

Example:

```java
class Vehicle {

    public void start() {
        System.out.println("Vehicle started");
    }
}
```

```java
class Car extends Vehicle {

    public void drive() {
        System.out.println("Car is driving");
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Car car = new Car();

        car.start();
        car.drive();
    }
}
```

Output:

```text
Vehicle started
Car is driving
```

Here, `Car` reused the `start()` method from `Vehicle`.

## Why Inheritance Is Useful

```text
It supports code reuse.
It reduces duplicate code.
It represents parent-child relationship.
```

But inheritance should be used carefully.

Do not use inheritance only to reuse code.
Use it when there is a real **is-a relationship**.

Example:

```text
Car is a Vehicle
Dog is an Animal
SavingsAccount is an Account
```

---

# 4. Polymorphism

Polymorphism means **one thing can behave in many forms**.

In Java, polymorphism mainly happens in two ways:

```text
1. Method overloading
2. Method overriding
```

---

## Method Overloading

Same method name, but different parameters.

Example:

```java
class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

Usage:

```java
Calculator calculator = new Calculator();

System.out.println(calculator.add(10, 20));
System.out.println(calculator.add(10, 20, 30));
```

This is **compile-time polymorphism**.

---

## Method Overriding

Child class gives its own implementation of parent method.

Example:

```java
class Animal {

    public void sound() {
        System.out.println("Animal makes sound");
    }
}
```

```java
class Dog extends Animal {

    @Override
    public void sound() {
        System.out.println("Dog barks");
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Animal animal = new Dog();
        animal.sound();
    }
}
```

Output:

```text
Dog barks
```

This is **runtime polymorphism**.

The reference type is `Animal`, but the actual object is `Dog`.

So Java calls the `Dog` version of `sound()` at runtime.

## Why Polymorphism Is Useful

```text
It makes code flexible.
It reduces if-else conditions.
It helps write generic code.
It supports loose coupling.
```

---

# Quick Comparison

| Pillar        | Simple Meaning                  | Example                             |
| ------------- | ------------------------------- | ----------------------------------- |
| Encapsulation | Protect data inside class       | private fields with getters/setters |
| Abstraction   | Hide implementation details     | interface, abstract class           |
| Inheritance   | Reuse parent class features     | `Car extends Vehicle`               |
| Polymorphism  | Same method, different behavior | overriding and overloading          |

---

# Small Real-Life Example

Think about a payment system.

## Abstraction

```java
interface Payment {
    void pay(double amount);
}
```

The system only depends on `Payment`.

It does not care whether payment is UPI, Card, or Wallet.

## Inheritance / Implementation

```java
class UpiPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid using UPI");
    }
}
```

```java
class CardPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Paid using Card");
    }
}
```

## Polymorphism

```java
Payment payment = new CardPayment();
payment.pay(500);
```

The same `pay()` method behaves differently based on the actual object.

## Encapsulation

```java
class PaymentRequest {

    private double amount;

    public void setAmount(double amount) {
        if (amount > 0) {
            this.amount = amount;
        }
    }

    public double getAmount() {
        return amount;
    }
}
```

The amount is protected from invalid direct changes.

---

# Common Interview Mistakes

## Mistake 1: Saying Encapsulation Means Only Getters And Setters

Getters and setters are one way to do encapsulation.

But the real meaning is:

```text
Protect data and control access.
```

---

## Mistake 2: Confusing Abstraction And Encapsulation

Simple difference:

```text
Encapsulation hides data.
Abstraction hides implementation.
```

Example:

```text
Encapsulation: balance is private.
Abstraction: pay() hides payment logic.
```

---

## Mistake 3: Using Inheritance Everywhere

Inheritance should be used only when there is a real relationship.

Good:

```text
Car is a Vehicle
```

Bad:

```text
UserService extends EmailService
```

That is not a real parent-child relationship.

---

## Mistake 4: Confusing Overloading And Overriding

```text
Overloading = same method name, different parameters.
Overriding = child class changes parent method behavior.
```

---

# Best Interview Points

You can say this in interview:

```text
OOP helps us model real-world problems using classes and objects.
The four main pillars are encapsulation, abstraction, inheritance, and polymorphism.
Encapsulation protects data.
Abstraction hides complexity.
Inheritance helps reuse common behavior.
Polymorphism allows the same method to behave differently based on the object.
```

---

# Interview-Ready Paragraph Answer

OOP has four main pillars: encapsulation, abstraction, inheritance, and polymorphism. Encapsulation means keeping data and methods together inside a class and protecting the data using private fields and controlled access methods. Abstraction means hiding internal implementation and showing only what is needed, usually using interfaces or abstract classes. Inheritance means one class can reuse the properties and methods of another class using `extends`. It is useful when there is a real parent-child relationship. Polymorphism means one method or object can behave in different ways. In Java, it is achieved using method overloading and method overriding. In simple words, encapsulation protects data, abstraction hides complexity, inheritance gives reuse, and polymorphism gives flexibility.

---

# 2. Abstraction vs encapsulation

---
## Summary

**Abstraction and encapsulation are different, but both help us write clean Java code.**

**Abstraction hides implementation details.**
**Encapsulation hides and protects data.**

## One-Line Answer

**Abstraction is about hiding “how it works”, while encapsulation is about hiding “data directly” and giving controlled access to it.**

---

# Simple Difference

Think about a car.

When you drive a car, you use:

```text
Steering
Brake
Accelerator
Gear
```

You do not need to know how the engine works internally.

That is **abstraction**.

Now, you cannot directly touch or change the engine’s internal parts while driving.

The internal parts are protected.

That is **encapsulation**.

---

# What Is Abstraction?

Abstraction means showing only important features and hiding internal details.

In Java, abstraction is mainly achieved using:

```text
Interface
Abstract class
```

Example:

```java
interface Payment {
    void pay(double amount);
}
```

Now different classes can implement it:

```java
class UpiPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}
```

```java
class CardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using card: " + amount);
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Payment payment = new UpiPayment();
        payment.pay(1000);
    }
}
```

Here the caller only knows:

```java
payment.pay(1000);
```

The caller does not know how UPI payment works inside.

That is abstraction.

---

# What Is Encapsulation?

Encapsulation means wrapping data and methods inside a class and protecting data from direct access.

In Java, encapsulation is done by:

```text
Making fields private
Providing public methods for controlled access
```

Example:

```java
class BankAccount {

    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

Here `balance` is private.

So nobody can directly do:

```java
account.balance = -5000;
```

The class controls how balance can change.

That is encapsulation.

---

# Main Difference

| Point               | Abstraction                         | Encapsulation                             |
| ------------------- | ----------------------------------- | ----------------------------------------- |
| Meaning             | Hides implementation details        | Hides data                                |
| Focus               | What an object does                 | How data is protected                     |
| Main purpose        | Reduce complexity                   | Protect object state                      |
| Achieved by         | Interface, abstract class           | Private fields, getters, setters, methods |
| Example             | `Payment.pay()` hides payment logic | `balance` is private in `BankAccount`     |
| Question it answers | What can this object do?            | Who can access or change this data?       |

---

# Very Simple Line

```text
Abstraction hides logic.
Encapsulation protects data.
```

Another simple line:

```text
Abstraction is design-level hiding.
Encapsulation is implementation-level protection.
```

---

# Real Java Example With Both

```java
interface AccountService {
    void deposit(double amount);
    void withdraw(double amount);
    double getBalance();
}
```

This is abstraction.

It tells what operations are available.

Now implementation:

```java
class SavingsAccount implements AccountService {

    private double balance;

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
        }
    }

    @Override
    public double getBalance() {
        return balance;
    }
}
```

Here:

```text
AccountService interface = abstraction
private balance field = encapsulation
```

The user of this class only uses methods.
They do not directly access the balance.

---

# Common Confusion

Many people say:

```text
Both hide data.
```

That is not fully correct.

Better answer:

```text
Abstraction hides unnecessary implementation details from the user.
Encapsulation hides internal data and protects it from invalid changes.
```

Example:

```text
Abstraction: I can call pay(), but I do not know the internal payment process.
Encapsulation: I cannot directly change balance because it is private.
```

---

# Common Interview Mistake

Do not say:

```text
Encapsulation is only getters and setters.
```

That is incomplete.

Getters and setters are just one way.

Real encapsulation means:

```text
Keep data private.
Expose only controlled methods.
Protect object state from invalid changes.
```

For example, this setter is weak:

```java
public void setBalance(double balance) {
    this.balance = balance;
}
```

Because anyone can set negative balance.

Better:

```java
public void deposit(double amount) {
    if (amount > 0) {
        balance = balance + amount;
    }
}
```

This gives controlled access.

---

# Interview-Ready Paragraph Answer

Abstraction and encapsulation are both OOP concepts, but they solve different problems. Abstraction means hiding internal implementation details and showing only the required behavior. In Java, we usually achieve abstraction using interfaces and abstract classes. For example, a `Payment` interface can expose a `pay()` method, while the actual logic can be different for UPI, card, or wallet payment. Encapsulation means hiding and protecting data inside a class. We usually make fields private and expose controlled methods to access or modify them. For example, in a `BankAccount` class, the `balance` field should be private, and it should be changed only through methods like `deposit()` and `withdraw()`. So in simple words, abstraction hides how something works, while encapsulation protects the data from direct and invalid access.

---

# 3. Interface vs abstract class

---
## Summary

**Interface** and **abstract class** both are used for abstraction in Java.

But they are used for different purposes.

**Interface is used to define a contract.**
**Abstract class is used to share common code with child classes.**

## One-Line Answer

**Use an interface when you want to define what a class should do, and use an abstract class when you want to define common behavior and common state for related classes.**

---

# Simple Meaning

## Interface

An interface says:

```text
Any class that implements me must provide these methods.
```

It focuses on **capability**.

Example:

```java
interface Payment {
    void pay(double amount);
}
```

Now any payment type can implement it:

```java
class UpiPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using UPI: " + amount);
    }
}
```

```java
class CardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println("Paid using Card: " + amount);
    }
}
```

Here, `Payment` is a contract.

It says every payment type must have `pay()`.

---

## Abstract Class

An abstract class is a partial class.

It can have:

```text
Abstract methods
Concrete methods
Instance variables
Constructor
Common logic
```

Example:

```java
abstract class Vehicle {

    protected String brand;

    public Vehicle(String brand) {
        this.brand = brand;
    }

    public void start() {
        System.out.println(brand + " vehicle started");
    }

    public abstract void drive();
}
```

Child class:

```java
class Car extends Vehicle {

    public Car(String brand) {
        super(brand);
    }

    @Override
    public void drive() {
        System.out.println(brand + " car is driving");
    }
}
```

Here, `Vehicle` shares common state and common behavior.

---

# Main Difference

| Point                | Interface                                   | Abstract Class                           |
| -------------------- | ------------------------------------------- | ---------------------------------------- |
| Main purpose         | Define contract                             | Share common code                        |
| Methods              | Abstract, default, static, private methods  | Abstract and concrete methods            |
| Variables            | Public static final constants               | Instance variables allowed               |
| Constructor          | No constructor                              | Constructor allowed                      |
| Multiple inheritance | Class can implement multiple interfaces     | Class can extend only one abstract class |
| State                | Cannot hold object state like normal fields | Can hold object state                    |
| Keyword              | `implements`                                | `extends`                                |
| Best use             | Capability-based design                     | Common base for related classes          |

---

# Interface Example

Suppose different services can send notifications.

```java
interface NotificationSender {
    void send(String message);
}
```

Implementations:

```java
class EmailSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("Email sent: " + message);
    }
}
```

```java
class SmsSender implements NotificationSender {

    @Override
    public void send(String message) {
        System.out.println("SMS sent: " + message);
    }
}
```

Usage:

```java
public class NotificationService {

    private final NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String message) {
        sender.send(message);
    }
}
```

This is useful because `NotificationService` does not depend on concrete classes.

It depends on the interface.

That gives loose coupling.

---

# Abstract Class Example

Suppose many accounts have common fields and common methods.

```java
abstract class BankAccount {

    protected String accountNumber;
    protected double balance;

    public BankAccount(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    public double getBalance() {
        return balance;
    }

    public abstract void withdraw(double amount);
}
```

Child class:

```java
class SavingsAccount extends BankAccount {

    public SavingsAccount(String accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
        }
    }
}
```

Here, all accounts can reuse `deposit()` and `getBalance()`.

But each account type can have its own `withdraw()` rule.

That is a good use of abstract class.

---

# When To Use Interface

Use interface when you want to define a capability or contract.

Good examples:

```text
Payment
NotificationSender
Runnable
Comparable
Repository
CacheProvider
ReportGenerator
```

Example:

```java
interface ReportGenerator {
    void generateReport();
}
```

A class can implement many interfaces.

```java
class PdfReportGenerator implements ReportGenerator, AutoCloseable {

    @Override
    public void generateReport() {
        System.out.println("Generating PDF report");
    }

    @Override
    public void close() {
        System.out.println("Closing resource");
    }
}
```

This is one big advantage of interface.

Java does not allow extending multiple classes.
But it allows implementing multiple interfaces.

---

# When To Use Abstract Class

Use abstract class when classes are closely related and share common state or logic.

Good examples:

```text
Vehicle
BankAccount
BaseEntity
AbstractController
AbstractPaymentProcessor
```

Example:

```java
abstract class BaseEntity {

    protected Long id;
    protected String createdBy;
    protected String updatedBy;

    public Long getId() {
        return id;
    }
}
```

This is useful when many child classes need common fields.

---

# Important Point About Java 8+

Before Java 8, interfaces mostly had only abstract methods and constants.

Now interfaces can also have:

```text
default methods
static methods
private methods
```

Example:

```java
interface Payment {

    void pay(double amount);

    default void printReceipt() {
        System.out.println("Receipt printed");
    }

    static boolean isValidAmount(double amount) {
        return amount > 0;
    }
}
```

But still, interface is mainly for contract.

Do not use interface as a replacement for every abstract class.

---

# Constructor Difference

An abstract class can have a constructor.

```java
abstract class Animal {

    protected String name;

    public Animal(String name) {
        this.name = name;
    }
}
```

Interface cannot have a constructor.

Why?

Because we do not create objects of an interface directly.

Interface only defines behavior.

---

# State Difference

Abstract class can store object state.

```java
abstract class Account {
    protected double balance;
}
```

Interface cannot store normal instance variables.

Interface fields are always:

```text
public static final
```

Example:

```java
interface AppConstants {
    int MAX_RETRY = 3;
}
```

This is a constant, not object state.

---

# Multiple Inheritance Difference

A class can extend only one class.

```java
class Car extends Vehicle {
}
```

A class can implement multiple interfaces.

```java
class SmartPhone implements Camera, MusicPlayer, InternetDevice {
}
```

This is why interfaces are very useful in Java.

---

# Interface vs Abstract Class In Real Backend

In Spring Boot, we often create service interfaces.

```java
public interface UserService {
    UserResponse createUser(CreateUserRequest request);
}
```

Implementation:

```java
@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        // business logic
        return new UserResponse();
    }
}
```

This helps with loose coupling and testing.

Abstract classes are useful when multiple classes share common logic.

Example:

```java
public abstract class BasePaymentProcessor {

    public void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    public abstract void process(double amount);
}
```

Child classes can reuse validation.

---

# Simple Rule To Remember

Use this rule:

```text
Interface = what to do
Abstract class = what to do + some common how to do
```

Another simple rule:

```text
Interface is for capability.
Abstract class is for common base behavior.
```

---

# Common Interview Confusion

## Can Interface Have Method Body?

Yes.

From Java 8, interfaces can have `default` and `static` methods.

Example:

```java
interface Logger {

    default void log(String message) {
        System.out.println(message);
    }
}
```

But interface is still mainly a contract.

---

## Can Abstract Class Have All Concrete Methods?

Yes.

An abstract class can have zero abstract methods.

Example:

```java
abstract class BaseController {

    public void logRequest() {
        System.out.println("Request logged");
    }
}
```

But we still cannot create an object of it directly.

---

## Can Abstract Class Implement Interface?

Yes.

```java
interface Payment {
    void pay(double amount);
}
```

```java
abstract class BasePayment implements Payment {

    public void validate(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
}
```

Then child class completes the implementation.

---

# Common Mistakes

## Mistake 1: Saying Interface Cannot Have Method Body

That was true before Java 8.

Now interfaces can have default and static methods.

---

## Mistake 2: Saying Abstract Class Is Always 100% Abstraction

Not true.

Abstract class can have concrete methods and fields.

Interface gives stronger abstraction in most cases.

---

## Mistake 3: Using Abstract Class Only For Contract

If you only need a contract, prefer interface.

---

## Mistake 4: Using Inheritance Without Real Relationship

Do not create abstract class just to reuse code.

There should be a real relationship.

Example:

```text
SavingsAccount is a BankAccount
Car is a Vehicle
```

---

# Best Practical Answer

Use interface when different classes can provide the same behavior in different ways.

Example:

```text
UPI payment
Card payment
Wallet payment
```

All can implement `Payment`.

Use abstract class when related classes share common data or common logic.

Example:

```text
SavingsAccount
CurrentAccount
SalaryAccount
```

All can extend `BankAccount`.

---

# Interview-Ready Paragraph Answer

Interface and abstract class are both used for abstraction in Java, but their purpose is different. An interface is mainly used to define a contract. It tells what methods a class should provide, but it usually does not hold object state. A class can implement multiple interfaces, so interfaces are good for loose coupling and capability-based design. An abstract class is used when we want to share common fields, constructors, and common methods among related classes. A class can extend only one abstract class. For example, `Payment` can be an interface because UPI, card, and wallet payments all have the capability to pay. But `BankAccount` can be an abstract class because savings account and current account share common fields like balance and account number. So in simple words, I use an interface when I need a contract, and I use an abstract class when I need a common base with shared code and state.

---

# 4. Composition vs inheritance

---
## Summary

**Inheritance** means one class gets behavior from another class using `extends`.

**Composition** means one class uses another class as a field/object.

In simple words:

```text
Inheritance = is-a relationship
Composition = has-a relationship
```

## One-Line Answer

**Use inheritance when there is a true parent-child relationship, and use composition when one class needs to use another class’s behavior.**

---

# What Is Inheritance?

Inheritance means a child class extends a parent class.

Example:

```java
class Vehicle {

    public void start() {
        System.out.println("Vehicle started");
    }
}

class Car extends Vehicle {

    public void drive() {
        System.out.println("Car is driving");
    }
}
```

Usage:

```java
Car car = new Car();
car.start();
car.drive();
```

Here, `Car` inherits `start()` from `Vehicle`.

This makes sense because:

```text
Car is a Vehicle
```

So this is an **is-a relationship**.

---

# What Is Composition?

Composition means a class has another class object inside it.

Example:

```java
class Engine {

    public void start() {
        System.out.println("Engine started");
    }
}

class Car {

    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void startCar() {
        engine.start();
        System.out.println("Car started");
    }
}
```

Usage:

```java
Engine engine = new Engine();
Car car = new Car(engine);

car.startCar();
```

Here, `Car` has an `Engine`.

This makes sense because:

```text
Car has an Engine
```

So this is a **has-a relationship**.

---

# Main Difference

| Point          | Inheritance                      | Composition                         |
| -------------- | -------------------------------- | ----------------------------------- |
| Meaning        | Child class extends parent class | Class contains another class object |
| Relationship   | `is-a`                           | `has-a`                             |
| Keyword        | `extends`                        | Field/object reference              |
| Coupling       | More tightly coupled             | Loosely coupled                     |
| Flexibility    | Less flexible                    | More flexible                       |
| Code reuse     | Through parent class             | Through contained object            |
| Runtime change | Hard                             | Easier                              |
| Example        | `Car extends Vehicle`            | `Car has Engine`                    |

---

# Simple Rule

Use this rule:

```text
Inheritance = is-a
Composition = has-a
```

Example:

```text
Dog is an Animal        -> Inheritance
Car has an Engine       -> Composition
Order has Payment       -> Composition
User has Address        -> Composition
SavingsAccount is Account -> Inheritance
```

---

# Why Composition Is Often Preferred

In real projects, composition is often preferred because it gives more flexibility.

Suppose we use inheritance:

```java
class EmailNotification {

    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

class UserService extends EmailNotification {

    public void registerUser() {
        System.out.println("User registered");
        send("Welcome user");
    }
}
```

This is not a good design.

Why?

Because:

```text
UserService is not an EmailNotification
```

UserService only needs to use email notification.

So composition is better.

```java
class EmailNotification {

    public void send(String message) {
        System.out.println("Sending email: " + message);
    }
}

class UserService {

    private EmailNotification emailNotification;

    public UserService(EmailNotification emailNotification) {
        this.emailNotification = emailNotification;
    }

    public void registerUser() {
        System.out.println("User registered");
        emailNotification.send("Welcome user");
    }
}
```

Now this is better.

Because:

```text
UserService has an EmailNotification dependency
```

This is composition.

---

# Important Interview Line

You may hear this line:

```text
Prefer composition over inheritance.
```

It does not mean inheritance is bad.

It means:

```text
Do not use inheritance just for code reuse.
Use inheritance only when there is a real is-a relationship.
```

---

# Problem With Inheritance

Inheritance can create tight coupling.

If the parent class changes, child classes can be affected.

Example:

```java
class BaseReport {

    public void generate() {
        System.out.println("Generating report");
    }
}

class PdfReport extends BaseReport {
}
```

If tomorrow `BaseReport.generate()` changes heavily, `PdfReport` behavior may also change.

Sometimes this is good.
Sometimes this causes bugs.

Inheritance also makes the child depend strongly on the parent.

---

# Benefit Of Composition

With composition, we can easily change behavior.

Example:

```java
interface NotificationSender {
    void send(String message);
}
```

```java
class EmailSender implements NotificationSender {

    public void send(String message) {
        System.out.println("Email sent: " + message);
    }
}
```

```java
class SmsSender implements NotificationSender {

    public void send(String message) {
        System.out.println("SMS sent: " + message);
    }
}
```

Now service class:

```java
class UserService {

    private NotificationSender notificationSender;

    public UserService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;
    }

    public void registerUser() {
        System.out.println("User registered");
        notificationSender.send("Welcome user");
    }
}
```

Now we can pass any sender:

```java
UserService userService = new UserService(new EmailSender());
userService.registerUser();

UserService userService2 = new UserService(new SmsSender());
userService2.registerUser();
```

This is very flexible.

`UserService` does not care whether notification is sent by email, SMS, or WhatsApp.

---

# Backend Example

Suppose we have a payment service.

Bad inheritance design:

```java
class RazorpayClient {

    public void pay(double amount) {
        System.out.println("Paid using Razorpay");
    }
}

class PaymentService extends RazorpayClient {

    public void makePayment(double amount) {
        pay(amount);
    }
}
```

This is not good because:

```text
PaymentService is not a RazorpayClient
```

Better composition design:

```java
interface PaymentGateway {
    void pay(double amount);
}
```

```java
class RazorpayGateway implements PaymentGateway {

    public void pay(double amount) {
        System.out.println("Paid using Razorpay");
    }
}
```

```java
class PaymentService {

    private PaymentGateway paymentGateway;

    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void makePayment(double amount) {
        paymentGateway.pay(amount);
    }
}
```

Now tomorrow, if we want to use Stripe:

```java
class StripeGateway implements PaymentGateway {

    public void pay(double amount) {
        System.out.println("Paid using Stripe");
    }
}
```

We can switch easily.

This is why composition is powerful in backend systems.

---

# When To Use Inheritance

Use inheritance when there is a real parent-child relationship.

Good examples:

```text
Car is a Vehicle
Dog is an Animal
SavingsAccount is a BankAccount
FullTimeEmployee is an Employee
```

Example:

```java
abstract class BankAccount {

    protected double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
        }
    }

    public abstract void withdraw(double amount);
}
```

```java
class SavingsAccount extends BankAccount {

    @Override
    public void withdraw(double amount) {
        if (amount <= balance) {
            balance = balance - amount;
        }
    }
}
```

This is okay because:

```text
SavingsAccount is a BankAccount
```

---

# When To Use Composition

Use composition when one class needs help from another class.

Good examples:

```text
OrderService has PaymentService
UserService has EmailService
Car has Engine
Order has Address
Employee has Department
PaymentService has PaymentGateway
```

Example:

```java
class Order {

    private Address shippingAddress;
    private Payment payment;

    public Order(Address shippingAddress, Payment payment) {
        this.shippingAddress = shippingAddress;
        this.payment = payment;
    }
}
```

Here:

```text
Order has Address
Order has Payment
```

So composition is correct.

---

# Composition In Spring Boot

Spring Boot uses composition a lot through dependency injection.

Example:

```java
@Service
public class OrderService {

    private final PaymentService paymentService;
    private final InventoryService inventoryService;

    public OrderService(PaymentService paymentService,
                        InventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }

    public void placeOrder(String productId) {
        inventoryService.checkStock(productId);
        paymentService.makePayment();
    }
}
```

Here `OrderService` is not extending `PaymentService`.

It is using `PaymentService`.

That is composition.

This makes code easier to test and maintain.

---

# Composition Helps Testing

With composition, we can mock dependencies easily.

Example:

```java
class OrderService {

    private PaymentGateway paymentGateway;

    public OrderService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }
}
```

In test, we can pass a fake payment gateway.

```java
PaymentGateway fakeGateway = amount -> System.out.println("Fake payment");
OrderService orderService = new OrderService(fakeGateway);
```

This is much easier than testing inheritance-heavy code.

---

# Common Mistake

A common mistake is using inheritance only to reuse methods.

Bad:

```java
class CommonUtils {

    public void log(String message) {
        System.out.println(message);
    }
}

class UserService extends CommonUtils {
}
```

This is bad because:

```text
UserService is not a CommonUtils
```

Better:

```java
class UserService {

    private Logger logger;

    public UserService(Logger logger) {
        this.logger = logger;
    }
}
```

Or simply use a utility class or logging framework.

---

# Best Practical Rule

Ask this question before choosing:

```text
Can I say child is a parent?
```

If yes, inheritance may be fine.

```text
Car is a Vehicle -> yes
```

Use inheritance.

If the answer is:

```text
This class only uses that class
```

then use composition.

```text
OrderService uses PaymentService -> composition
```

---

# Quick Comparison Example

## Inheritance

```java
class Dog extends Animal {
}
```

Meaning:

```text
Dog is an Animal
```

## Composition

```java
class Dog {

    private Collar collar;
}
```

Meaning:

```text
Dog has a Collar
```

---

# Common Interview Points

```text
Inheritance gives code reuse, but it creates tight coupling.
Composition gives flexibility and better maintainability.
Inheritance should be used only for true is-a relationships.
Composition is preferred when behavior can change or dependency can be replaced.
Spring dependency injection is a practical example of composition.
```

---

# Interview-Ready Paragraph Answer

Composition and inheritance are both used for code reuse, but they are different. Inheritance means one class extends another class, so it represents an `is-a` relationship. For example, `Car is a Vehicle`, so `Car extends Vehicle` makes sense. Composition means one class contains another class object, so it represents a `has-a` relationship. For example, `Car has an Engine`, so `Car` should contain an `Engine` object. In real projects, composition is usually preferred because it gives more flexibility, loose coupling, and better testability. Inheritance should not be used only to reuse code. It should be used only when there is a real parent-child relationship. In Spring Boot also, we mostly use composition through dependency injection, where one service uses another service instead of extending it. So in simple words, inheritance is for `is-a`, and composition is for `has-a`.

---

# 5. Overloading vs overriding

---
## Summary

**Overloading** and **overriding** both are related to methods in Java.

But they are different.

```text
Overloading = same method name, different parameters
Overriding = child class changes parent class method behavior
```

## One-Line Answer

**Method overloading happens in the same class with different parameters, while method overriding happens between parent and child classes with the same method signature.**

---

# 1. What Is Method Overloading?

Method overloading means having multiple methods with the same name, but different parameters.

It can be in the same class.

Example:

```java
class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int add(int a, int b, int c) {
        return a + b + c;
    }

    public double add(double a, double b) {
        return a + b;
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        System.out.println(calculator.add(10, 20));
        System.out.println(calculator.add(10, 20, 30));
        System.out.println(calculator.add(10.5, 20.5));
    }
}
```

Output:

```text
30
60
31.0
```

Here all methods are named `add`.

But their parameters are different.

This is called **method overloading**.

---

# 2. What Can Be Changed In Overloading?

In method overloading, we can change:

```text
Number of parameters
Type of parameters
Order of parameters
```

## Example 1: Number Of Parameters

```java
public int add(int a, int b) {
    return a + b;
}

public int add(int a, int b, int c) {
    return a + b + c;
}
```

## Example 2: Type Of Parameters

```java
public int add(int a, int b) {
    return a + b;
}

public double add(double a, double b) {
    return a + b;
}
```

## Example 3: Order Of Parameters

```java
public void print(String name, int age) {
    System.out.println(name + " " + age);
}

public void print(int age, String name) {
    System.out.println(age + " " + name);
}
```

---

# 3. Return Type Alone Cannot Overload A Method

This is a common interview point.

This is not allowed:

```java
class Test {

    public int show() {
        return 10;
    }

    public String show() {
        return "Hello";
    }
}
```

This gives a compile-time error.

Why?

Because method overloading depends on method parameters, not only return type.

The compiler will be confused when we call:

```java
show();
```

It will not know which method to call.

---

# 4. What Is Method Overriding?

Method overriding means child class provides its own implementation of a parent class method.

It happens between parent and child classes.

Example:

```java
class Animal {

    public void sound() {
        System.out.println("Animal makes sound");
    }
}
```

```java
class Dog extends Animal {

    @Override
    public void sound() {
        System.out.println("Dog barks");
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Animal animal = new Dog();
        animal.sound();
    }
}
```

Output:

```text
Dog barks
```

Here, `Dog` overrides the `sound()` method of `Animal`.

The method name and parameters are the same.

But the child class gives its own behavior.

---

# 5. Main Difference

| Point               | Overloading                                          | Overriding                                 |
| ------------------- | ---------------------------------------------------- | ------------------------------------------ |
| Meaning             | Same method name with different parameters           | Child class changes parent method behavior |
| Happens where       | Usually same class                                   | Parent-child class                         |
| Parameters          | Must be different                                    | Must be same                               |
| Return type         | Can be same or different, but parameters must differ | Same or covariant return type              |
| Access modifier     | No strict rule                                       | Cannot reduce visibility                   |
| Binding             | Compile-time                                         | Runtime                                    |
| Polymorphism type   | Compile-time polymorphism                            | Runtime polymorphism                       |
| Inheritance needed? | Not required                                         | Required                                   |
| Example             | `add(int, int)` and `add(int, int, int)`             | `Dog.sound()` overrides `Animal.sound()`   |

---

# 6. Overloading Is Compile-Time Polymorphism

In overloading, the compiler decides which method to call.

Example:

```java
calculator.add(10, 20);
```

The compiler sees two `int` arguments.

So it calls:

```java
add(int a, int b)
```

That is why overloading is called **compile-time polymorphism**.

---

# 7. Overriding Is Runtime Polymorphism

In overriding, the method call is decided at runtime based on the actual object.

Example:

```java
Animal animal = new Dog();
animal.sound();
```

Reference type is:

```text
Animal
```

Actual object is:

```text
Dog
```

So Java calls:

```text
Dog's sound() method
```

That is why overriding is called **runtime polymorphism**.

---

# 8. Rules Of Method Overriding

For overriding:

```text
Method name must be same
Parameters must be same
Return type should be same or covariant
Child method cannot reduce access modifier
Only inherited methods can be overridden
Static methods are not truly overridden
Final methods cannot be overridden
Private methods cannot be overridden
```

---

# 9. Access Modifier Rule In Overriding

Child class cannot make the method more restrictive.

Example:

```java
class Parent {

    public void show() {
        System.out.println("Parent show");
    }
}
```

This is wrong:

```java
class Child extends Parent {

    @Override
    private void show() {
        System.out.println("Child show");
    }
}
```

Why?

Because parent method is `public`.

Child cannot make it `private`.

Allowed access can be same or wider.

---

# 10. Final Method Cannot Be Overridden

Example:

```java
class Parent {

    public final void show() {
        System.out.println("Parent show");
    }
}
```

This is not allowed:

```java
class Child extends Parent {

    @Override
    public void show() {
        System.out.println("Child show");
    }
}
```

Because `final` methods cannot be overridden.

---

# 11. Static Method Is Not Overridden

Static methods belong to the class, not the object.

Example:

```java
class Parent {

    public static void show() {
        System.out.println("Parent static method");
    }
}
```

```java
class Child extends Parent {

    public static void show() {
        System.out.println("Child static method");
    }
}
```

This is called **method hiding**, not overriding.

Example:

```java
Parent obj = new Child();
obj.show();
```

Output:

```text
Parent static method
```

Because static method is decided by reference type, not actual object.

---

# 12. Private Method Is Not Overridden

Private methods are not visible to child classes.

So they cannot be overridden.

Example:

```java
class Parent {

    private void show() {
        System.out.println("Parent private show");
    }
}
```

If child has same method:

```java
class Child extends Parent {

    private void show() {
        System.out.println("Child private show");
    }
}
```

This is not overriding.

It is just a separate private method in child class.

---

# 13. Example With Both Overloading And Overriding

```java
class Payment {

    public void pay(int amount) {
        System.out.println("Paying amount: " + amount);
    }

    public void pay(int amount, String currency) {
        System.out.println("Paying amount: " + amount + " " + currency);
    }
}
```

Here, `pay()` is overloaded.

Now overriding:

```java
class UpiPayment extends Payment {

    @Override
    public void pay(int amount) {
        System.out.println("Paying using UPI: " + amount);
    }
}
```

Usage:

```java
public class Main {
    public static void main(String[] args) {
        Payment payment = new UpiPayment();

        payment.pay(1000);
        payment.pay(1000, "INR");
    }
}
```

Output:

```text
Paying using UPI: 1000
Paying amount: 1000 INR
```

Here:

```text
pay(int amount) is overridden
pay(int amount, String currency) is overloaded
```

---

# 14. Why Overloading Is Useful

Overloading improves readability.

Instead of writing different method names:

```java
addTwoNumbers()
addThreeNumbers()
addDoubleNumbers()
```

We can write:

```java
add()
```

with different parameters.

It makes code cleaner.

---

# 15. Why Overriding Is Useful

Overriding gives runtime flexibility.

Example:

```java
class Payment {
    public void pay() {
        System.out.println("Generic payment");
    }
}

class UpiPayment extends Payment {
    public void pay() {
        System.out.println("UPI payment");
    }
}

class CardPayment extends Payment {
    public void pay() {
        System.out.println("Card payment");
    }
}
```

Now we can write:

```java
Payment payment = new UpiPayment();
payment.pay();
```

Tomorrow we can change it to:

```java
Payment payment = new CardPayment();
payment.pay();
```

The calling code stays almost same.

This is the power of polymorphism.

---

# Common Interview Mistakes

## Mistake 1: Saying Overloading Depends On Return Type

Wrong.

Overloading depends on parameters, not only return type.

---

## Mistake 2: Saying Static Method Is Overridden

Wrong.

Static method is hidden, not overridden.

---

## Mistake 3: Forgetting Runtime Polymorphism

In overriding, Java calls the method based on the actual object at runtime.

Example:

```java
Animal animal = new Dog();
animal.sound();
```

This calls `Dog.sound()`.

---

## Mistake 4: Reducing Access Modifier In Overriding

Child class cannot reduce visibility.

Example:

```text
public cannot become private
protected cannot become private
```

---

# Best Simple Comparison

```text
Overloading:
Same class
Same method name
Different parameters
Compile-time decision

Overriding:
Parent-child class
Same method name
Same parameters
Runtime decision
```

---

# Interview-Ready Paragraph Answer

Method overloading and method overriding are both examples of polymorphism in Java, but they work differently. Overloading means having the same method name with different parameters in the same class. The parameters can differ by number, type, or order. It is resolved at compile time, so it is called compile-time polymorphism. Method overriding happens when a child class provides its own implementation of a parent class method with the same method name and same parameters. It is resolved at runtime based on the actual object, so it is called runtime polymorphism. In overloading, inheritance is not required, but in overriding, inheritance is required. Also, return type alone cannot overload a method, final methods cannot be overridden, private methods are not overridden, and static methods are hidden, not overridden.

---

# 6. HashMap internal working

---
## Summary

`HashMap` stores data in **key-value** format.

Example:

```java
map.put("name", "Ravi");
```

Here:

```text
key   = "name"
value = "Ravi"
```

Internally, `HashMap` uses:

```text
Array + Linked List + Red-Black Tree
```

## One-Line Answer

**HashMap works by calculating the hash of the key, finding the bucket index, and storing the key-value pair in that bucket. If multiple keys go to the same bucket, it handles collision using linked list or tree.**

---

# What Is HashMap?

`HashMap` is a class in Java Collections.

It stores data as key-value pairs.

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(1, "Ravi");
map.put(2, "Amit");
map.put(3, "Neha");
```

Here:

```text
1 -> Ravi
2 -> Amit
3 -> Neha
```

Key should be unique.

Value can be duplicate.

Example:

```java
map.put(1, "Ravi");
map.put(2, "Ravi");
```

This is allowed because keys are different.

---

# Internal Structure Of HashMap

Internally, `HashMap` uses an array of buckets.

You can imagine it like this:

```text
Bucket Array

Index 0 -> null
Index 1 -> Entry
Index 2 -> Entry
Index 3 -> null
Index 4 -> Entry
```

Each bucket can store one or more nodes.

Each node contains:

```text
hash
key
value
next
```

Simple internal node structure:

```java
static class Node<K, V> {
    final int hash;
    final K key;
    V value;
    Node<K, V> next;
}
```

So every entry has:

```text
key
value
hash
reference to next node
```

---

# What Happens When We Call put()?

Example:

```java
map.put("name", "Ravi");
```

Internally, these steps happen:

```text
1. HashMap calculates hashCode of the key.
2. It improves/spreads the hash.
3. It finds bucket index.
4. It checks if bucket is empty.
5. If empty, it stores the new node there.
6. If not empty, it checks existing keys using equals().
7. If same key exists, it replaces old value.
8. If key is different, it stores new node in linked list or tree.
```

---

# Step 1: hashCode() Is Called

For key `"name"`, Java calls:

```java
"name".hashCode();
```

This returns an integer hash value.

Example:

```text
3373707
```

This hash helps HashMap decide where to store the key-value pair.

---

# Step 2: Bucket Index Is Calculated

HashMap has an internal array.

Default initial capacity is usually:

```text
16
```

So there are 16 buckets at first.

HashMap calculates index like this:

```text
index = hash & (capacity - 1)
```

Example:

```text
capacity = 16
index will be between 0 and 15
```

So the key goes into one bucket.

---

# Step 3: Store Node In Bucket

If the bucket is empty, HashMap stores the node directly.

Example:

```text
Index 5 -> ["name", "Ravi"]
```

That is simple.

---

# What Is Collision?

Collision means two different keys go to the same bucket.

Example:

```text
key1 -> index 5
key2 -> index 5
```

This can happen because many keys can produce bucket index from a limited array size.

HashMap must handle this.

---

# How HashMap Handles Collision

Before Java 8, collision was handled using linked list.

Example:

```text
Index 5 -> Node1 -> Node2 -> Node3
```

From Java 8 onwards, if too many nodes are in one bucket, the linked list can become a Red-Black Tree.

So Java 8 HashMap uses:

```text
Array + Linked List + Red-Black Tree
```

---

# Linked List Collision Example

Suppose two keys go to same index.

```java
map.put("Aa", 100);
map.put("BB", 200);
```

These strings are famous because they can produce the same hash code.

The bucket may look like:

```text
Index 3 -> ["Aa", 100] -> ["BB", 200]
```

When we search for `"BB"`, HashMap checks nodes one by one.

It uses:

```text
hashCode()
equals()
```

---

# Why equals() Is Needed?

`hashCode()` helps find the bucket.

But inside the bucket, Java uses `equals()` to find the exact key.

Example:

```java
map.get("name");
```

Steps:

```text
1. Calculate hash of "name"
2. Find bucket index
3. Check keys inside bucket using equals()
4. Return matching value
```

So:

```text
hashCode decides bucket
equals decides exact key
```

This is a very important interview line.

---

# What Happens When We Call get()?

Example:

```java
String value = map.get("name");
```

Internal flow:

```text
1. Calculate hashCode of key.
2. Find bucket index.
3. Go to that bucket.
4. Compare key with existing nodes.
5. If key matches, return value.
6. If not found, return null.
```

Example:

```text
map.get("name") -> "Ravi"
```

If key is not present:

```java
map.get("city");
```

Output:

```text
null
```

---

# What Happens When Same Key Is Inserted Again?

Example:

```java
map.put(1, "Ravi");
map.put(1, "Amit");
```

Here key `1` already exists.

So HashMap does not create a new entry.

It replaces old value.

Final map:

```text
1 -> Amit
```

Because keys are unique.

---

# Example

```java
import java.util.HashMap;
import java.util.Map;

public class HashMapExample {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();

        map.put(1, "Ravi");
        map.put(2, "Amit");
        map.put(1, "Neha");

        System.out.println(map.get(1));
        System.out.println(map.get(2));
    }
}
```

Output:

```text
Neha
Amit
```

Why?

Because key `1` was inserted again.

So old value `"Ravi"` was replaced by `"Neha"`.

---

# Load Factor

Load factor decides when HashMap should resize.

Default load factor is:

```text
0.75
```

Default capacity is:

```text
16
```

Threshold is:

```text
capacity * load factor
```

So:

```text
16 * 0.75 = 12
```

When HashMap size becomes more than 12, it resizes.

---

# What Is Resizing?

Resizing means increasing the internal array size.

Default capacity:

```text
16
```

After resize:

```text
32
```

Then:

```text
64
```

Capacity usually doubles.

During resize, entries are moved to new buckets.

This is called rehashing or redistribution.

That is why resizing is costly.

---

# Why Initial Capacity Matters

If we already know that many records will be stored, we can give initial capacity.

Example:

```java
Map<Integer, String> map = new HashMap<>(1000);
```

This can reduce resizing.

But do not give a very large capacity without reason.

It can waste memory.

---

# Time Complexity

Average case:

```text
put() -> O(1)
get() -> O(1)
remove() -> O(1)
```

Worst case before Java 8:

```text
O(n)
```

Because collision bucket could become a long linked list.

Worst case after Java 8 with tree:

```text
O(log n)
```

When bucket becomes tree.

---

# When Linked List Becomes Tree

In Java 8, if one bucket has too many nodes, it can convert linked list into Red-Black Tree.

Important values:

```text
TREEIFY_THRESHOLD = 8
MIN_TREEIFY_CAPACITY = 64
```

Simple meaning:

If a bucket has more than 8 nodes and HashMap capacity is at least 64, then linked list can become tree.

Why tree?

Because searching in a tree is faster than searching in a long linked list.

---

# Can HashMap Store null?

Yes.

`HashMap` allows:

```text
One null key
Multiple null values
```

Example:

```java
Map<String, String> map = new HashMap<>();

map.put(null, "No Key");
map.put("name", null);
map.put("city", null);

System.out.println(map.get(null));
```

Output:

```text
No Key
```

Only one null key is allowed because keys must be unique.

But multiple null values are allowed.

---

# Is HashMap Thread-Safe?

No.

`HashMap` is not thread-safe.

If multiple threads modify HashMap at the same time, it can cause problems.

Example:

```text
Wrong data
Lost updates
Unexpected behavior
ConcurrentModificationException during iteration
```

For multithreaded use, prefer:

```text
ConcurrentHashMap
Collections.synchronizedMap()
```

In most modern concurrent code, `ConcurrentHashMap` is preferred.

---

# Why Mutable Keys Are Dangerous?

HashMap keys should be immutable or should not change after insertion.

Bad example:

```java
class Employee {
    int id;

    Employee(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        Employee other = (Employee) obj;
        return this.id == other.id;
    }
}
```

Usage:

```java
Map<Employee, String> map = new HashMap<>();

Employee emp = new Employee(1);
map.put(emp, "Ravi");

emp.id = 2;

System.out.println(map.get(emp));
```

This may return:

```text
null
```

Why?

Because the key’s hash changed after insertion.

HashMap stored it in bucket based on old hash.

Now it searches in a different bucket.

That is why keys should be immutable.

Good keys:

```text
String
Integer
Long
UUID
Immutable custom objects
```

---

# equals() And hashCode() Contract

If two objects are equal using `equals()`, they must have the same `hashCode()`.

Example:

```text
obj1.equals(obj2) == true
then
obj1.hashCode() == obj2.hashCode()
```

If this contract is broken, HashMap will not work correctly.

This is very important.

---

# Small Custom Key Example

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Employee {

    private final int id;
    private final String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

public class Main {

    public static void main(String[] args) {

        Map<Employee, String> map = new HashMap<>();

        Employee e1 = new Employee(1, "Ravi");
        Employee e2 = new Employee(1, "Ravi Kumar");

        map.put(e1, "Developer");

        System.out.println(map.get(e2));
    }
}
```

Output:

```text
Developer
```

Why?

Because `e1` and `e2` are equal based on `id`.

Their hash code is also based on `id`.

So HashMap can find the value.

---

# Important Points For Interview

```text
HashMap stores key-value pairs.
It uses hashing internally.
It uses an array of buckets.
Each bucket stores nodes.
Each node has hash, key, value, and next.
hashCode() finds the bucket.
equals() finds the exact key.
Collision is handled using linked list or tree.
Default capacity is 16.
Default load factor is 0.75.
HashMap allows one null key and multiple null values.
HashMap is not thread-safe.
Mutable keys should be avoided.
```

---

# Common Mistakes

## 1. Saying HashMap Stores Data Directly By hashCode

Not exactly.

`hashCode()` helps calculate bucket index.

The data is stored in bucket array.

---

## 2. Saying Collision Does Not Happen

Collision can happen.

Different keys can go to the same bucket.

HashMap handles it using linked list or tree.

---

## 3. Ignoring equals()

`hashCode()` alone is not enough.

`equals()` is used to find exact key inside bucket.

---

## 4. Using Mutable Object As Key

If key changes after insertion, HashMap may not find it again.

Use immutable keys.

---

## 5. Saying HashMap Is Thread-Safe

HashMap is not thread-safe.

For concurrent access, use `ConcurrentHashMap`.

---

# Simple Internal Flow

## put()

```text
map.put(key, value)

1. Calculate hash of key
2. Find bucket index
3. If bucket empty, add new node
4. If bucket not empty, compare keys
5. If same key found, replace value
6. If different key, add node
7. If bucket becomes too large, convert to tree
8. If size crosses threshold, resize
```

## get()

```text
map.get(key)

1. Calculate hash of key
2. Find bucket index
3. Go to bucket
4. Compare keys using equals()
5. If found, return value
6. If not found, return null
```

---

# Interview-Ready Paragraph Answer

`HashMap` stores data in key-value pairs and internally uses hashing. When we call `put(key, value)`, HashMap first calls `hashCode()` on the key, calculates the bucket index, and stores the entry in that bucket. Each entry is stored as a node containing hash, key, value, and next reference. If two keys go to the same bucket, it is called collision. HashMap handles collision using linked list, and from Java 8 onwards, if the bucket becomes too large, the linked list can convert into a Red-Black Tree for better performance. When we call `get(key)`, HashMap again calculates the hash, finds the bucket, and then uses `equals()` to find the exact key. Default capacity is 16 and default load factor is 0.75. When the size crosses the threshold, HashMap resizes and redistributes entries. HashMap allows one null key and multiple null values, but it is not thread-safe. Also, keys should ideally be immutable because changing a key after insertion can make the value unreachable.

---

# 7. ConcurrentHashMap internal working

---
## Summary

`ConcurrentHashMap` is a thread-safe version of `HashMap`.

It allows multiple threads to read and write safely.

Main idea:

```text
HashMap is not thread-safe.
ConcurrentHashMap is thread-safe and better for multi-threaded code.
```

## One-Line Answer

**ConcurrentHashMap works by locking only a small part of the map during updates, while reads are mostly lock-free, so multiple threads can work on it safely and efficiently.**

---

# Why ConcurrentHashMap Is Needed

`HashMap` is not thread-safe.

If multiple threads update a `HashMap` at the same time, it can cause problems like:

```text
Wrong data
Lost updates
Unexpected behavior
ConcurrentModificationException during iteration
```

Example:

```java
Map<Integer, String> map = new HashMap<>();
```

This is not safe when multiple threads modify it.

So we use:

```java
Map<Integer, String> map = new ConcurrentHashMap<>();
```

---

# Basic Meaning

`ConcurrentHashMap` stores data as key-value pairs.

Example:

```java
ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

map.put(1, "Ravi");
map.put(2, "Amit");
map.put(3, "Neha");
```

It works like `HashMap`, but it is safe for concurrent access.

---

# Internal Structure

In Java 8 and above, `ConcurrentHashMap` internally uses:

```text
Array of buckets
Nodes
Linked list
Red-Black Tree for heavy collisions
CAS operations
Small synchronized blocks for updates
```

Like `HashMap`, it stores entries in buckets.

Each node has:

```text
hash
key
value
next
```

Simple internal idea:

```java
static class Node<K, V> {
    final int hash;
    final K key;
    volatile V value;
    volatile Node<K, V> next;
}
```

Important point:

```text
Value and next are volatile so changes are visible to other threads.
```

---

# How put() Works Internally

When we call:

```java
map.put("name", "Ravi");
```

Internally, these steps happen:

```text
1. Calculate hash of the key.
2. Find bucket index.
3. If bucket is empty, insert using CAS.
4. If bucket is not empty, lock that bucket.
5. Check if same key already exists.
6. If same key exists, update value.
7. If key does not exist, add new node.
8. If collision chain becomes large, convert to tree.
9. Resize if needed.
```

---

# What Is CAS?

CAS means **Compare And Swap**.

It is a low-level atomic operation.

Simple meaning:

```text
Set value only if current value is still what I expected.
```

Example idea:

```text
If bucket is still empty, put my node there.
If another thread already inserted there, try again.
```

This avoids locking when the bucket is empty.

So first insertion in an empty bucket is very fast.

---

# What Happens If Bucket Is Empty?

Suppose bucket index is 5.

```text
Index 5 -> empty
```

Thread tries to insert a node.

`ConcurrentHashMap` uses CAS.

If no other thread inserted anything there, insertion succeeds.

```text
Index 5 -> ["name", "Ravi"]
```

No lock is needed here.

---

# What Happens If Bucket Is Not Empty?

Suppose bucket already has one or more nodes.

```text
Index 5 -> Node1 -> Node2
```

Now another thread wants to insert into the same bucket.

In this case, `ConcurrentHashMap` locks only that bucket.

Not the full map.

That is the key point.

```text
Only bucket 5 is locked.
Other buckets are still free.
```

So other threads can still work on other buckets.

---

# How get() Works Internally

When we call:

```java
map.get("name");
```

The flow is:

```text
1. Calculate hash of key.
2. Find bucket index.
3. Read bucket.
4. Compare keys using equals().
5. Return value if found.
6. Return null if not found.
```

Most `get()` operations do not use locking.

That is why reads are very fast.

Important point:

```text
Reads are mostly lock-free in ConcurrentHashMap.
```

---

# How Collision Is Handled

Collision means two different keys go to the same bucket.

Example:

```text
key1 -> index 5
key2 -> index 5
```

Like `HashMap`, `ConcurrentHashMap` handles collision using:

```text
Linked list
Red-Black Tree
```

If too many nodes are stored in the same bucket, it can convert the linked list into a tree.

This improves search time.

```text
Linked list search -> O(n)
Tree search -> O(log n)
```

---

# Java 7 vs Java 8 Difference

This is a good interview point.

## Java 7 ConcurrentHashMap

In Java 7, `ConcurrentHashMap` used **segments**.

Simple idea:

```text
Map was divided into multiple segments.
Each segment had its own lock.
```

Example:

```text
Segment 1
Segment 2
Segment 3
Segment 4
```

If one segment was locked, other segments could still be used.

This was called segment locking.

---

## Java 8 ConcurrentHashMap

In Java 8, segment locking was removed.

Now it uses:

```text
CAS for empty bucket insertion
synchronized on bucket head for update
volatile for visibility
Red-Black Tree for heavy collisions
```

So Java 8 locking is more fine-grained.

It locks at bucket level, not segment level.

Simple line:

```text
Java 7 used segment-level locking.
Java 8 uses bucket-level locking with CAS and synchronized.
```

---

# Why It Is Better Than Hashtable

`Hashtable` locks the whole table.

Example:

```text
Thread 1 writes one key
Whole Hashtable is locked
Thread 2 has to wait
```

`ConcurrentHashMap` does not lock the whole map for normal operations.

Example:

```text
Thread 1 updates bucket 5
Thread 2 updates bucket 8
Both can work at the same time
```

That gives better performance.

---

# Null Key And Null Value

`ConcurrentHashMap` does not allow null key or null value.

This is not allowed:

```java
map.put(null, "Ravi");
```

This is also not allowed:

```java
map.put(1, null);
```

Both throw `NullPointerException`.

Why?

Because in concurrent code, `null` creates confusion.

Example:

```java
map.get(1);
```

If it returns `null`, we cannot clearly know:

```text
Key is not present?
Or key is present with null value?
```

So `ConcurrentHashMap` does not allow null.

---

# Example

```java
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1, "Ravi");
        map.put(2, "Amit");

        System.out.println(map.get(1));

        map.put(1, "Neha");

        System.out.println(map.get(1));
    }
}
```

Output:

```text
Ravi
Neha
```

Same key was inserted again.

So old value was replaced.

---

# Thread-Safe Example

```java
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(map.size());
    }
}
```

Output:

```text
1000
```

Both threads put the same keys.

Since keys are unique, final size is `1000`.

The map remains safe.

---

# Important Atomic Methods

`ConcurrentHashMap` gives some very useful atomic methods.

## putIfAbsent()

```java
map.putIfAbsent(1, "Ravi");
```

Meaning:

```text
Put value only if key is not already present.
```

This is thread-safe.

---

## computeIfAbsent()

Very useful for cache-like logic.

```java
map.computeIfAbsent(1, key -> "User-" + key);
```

Meaning:

```text
If key is missing, compute value and put it.
If key already exists, return existing value.
```

---

## replace()

```java
map.replace(1, "Old", "New");
```

Meaning:

```text
Replace only if current value is Old.
```

This is also atomic.

---

# Why Atomic Methods Matter

Bad code:

```java
if (!map.containsKey(1)) {
    map.put(1, "Ravi");
}
```

This is not fully safe in concurrent code.

Why?

Because two threads can both see that key is missing.

Then both can put value.

Better:

```java
map.putIfAbsent(1, "Ravi");
```

This check and put happens atomically.

---

# Iteration In ConcurrentHashMap

`ConcurrentHashMap` iterators are **weakly consistent**.

That means:

```text
They do not throw ConcurrentModificationException.
They may or may not reflect latest changes during iteration.
```

Example:

```java
for (Integer key : map.keySet()) {
    System.out.println(key);
}
```

If another thread modifies the map while this loop is running, it will not fail like `HashMap`.

This is useful in concurrent systems.

---

# Is ConcurrentHashMap Fully Locked?

No.

This is the main advantage.

It does not lock the whole map for every operation.

```text
Reads are mostly lock-free.
Writes lock only small bucket area.
Different buckets can be updated by different threads.
```

That is why it performs better than `Hashtable` and `Collections.synchronizedMap()` in many concurrent cases.

---

# Time Complexity

Average case:

```text
get()    -> O(1)
put()    -> O(1)
remove() -> O(1)
```

Worst case with tree:

```text
O(log n)
```

This happens when too many keys are in the same bucket and bucket becomes tree.

---

# Important Interview Points

```text
ConcurrentHashMap is thread-safe.
It stores data in key-value pairs.
It does not lock the whole map for normal operations.
Reads are mostly lock-free.
Writes lock only the affected bucket.
Java 7 used segment locking.
Java 8 uses CAS and bucket-level locking.
It does not allow null key or null value.
Collision is handled using linked list and tree.
Iterators are weakly consistent.
Use atomic methods like putIfAbsent and computeIfAbsent.
```

---

# Common Mistakes

## Mistake 1: Saying ConcurrentHashMap Locks The Whole Map

Wrong.

It locks only a small part during updates.

---

## Mistake 2: Saying It Allows Null Like HashMap

Wrong.

`HashMap` allows one null key and multiple null values.

`ConcurrentHashMap` does not allow null key or null value.

---

## Mistake 3: Using containsKey() Then put()

This is not the best in concurrent code.

Bad:

```java
if (!map.containsKey(key)) {
    map.put(key, value);
}
```

Better:

```java
map.putIfAbsent(key, value);
```

---

## Mistake 4: Expecting Iterator To Show Exact Latest Data

ConcurrentHashMap iterator is weakly consistent.

It is safe, but it may not show every latest update happening at the same time.

---

## Mistake 5: Saying It Is Always Faster Than HashMap

For single-threaded code, `HashMap` is usually faster.

`ConcurrentHashMap` is useful when multiple threads access the map.

---

# Simple Internal Flow

## put()

```text
1. Calculate hash.
2. Find bucket index.
3. If bucket is empty, insert using CAS.
4. If bucket is not empty, lock that bucket.
5. Compare keys using equals().
6. Replace value if key exists.
7. Add new node if key does not exist.
8. Treeify bucket if collision chain is too large.
9. Resize if needed.
```

## get()

```text
1. Calculate hash.
2. Find bucket index.
3. Read bucket without locking in most cases.
4. Compare keys using equals().
5. Return value if found.
6. Return null if not found.
```

---

# Interview-Ready Paragraph Answer

`ConcurrentHashMap` is a thread-safe map used when multiple threads need to read and update the map at the same time. Internally, it works like `HashMap` because it uses an array of buckets, nodes, linked lists, and trees for collision handling. But the main difference is concurrency control. In Java 8, reads are mostly lock-free, and writes lock only the affected bucket, not the whole map. If a bucket is empty, it can insert using CAS. If the bucket already has nodes, it synchronizes only on that bucket and updates it safely. This allows multiple threads to work on different buckets at the same time. It also provides atomic methods like `putIfAbsent()` and `computeIfAbsent()` to avoid race conditions. Unlike `HashMap`, it does not allow null keys or null values. Its iterators are weakly consistent, so they do not throw `ConcurrentModificationException` during concurrent updates. In simple words, `ConcurrentHashMap` gives thread safety with better performance than locking the whole map.

---

# 8. HashMap vs Hashtable vs ConcurrentHashMap

---
## Summary

`HashMap`, `Hashtable`, and `ConcurrentHashMap` all store data in **key-value** form.

But the main difference is **thread safety and performance**.

```text
HashMap              -> Not thread-safe
Hashtable            -> Thread-safe but slow
ConcurrentHashMap    -> Thread-safe and faster for multithreading
```

## One-Line Answer

**HashMap is not synchronized, Hashtable is synchronized at method level, and ConcurrentHashMap is thread-safe with better performance because it locks only a small part of the map during updates.**

---

# 1. HashMap

`HashMap` is the most commonly used map in Java.

It stores data as key-value pairs.

Example:

```java
Map<Integer, String> map = new HashMap<>();

map.put(1, "Ravi");
map.put(2, "Amit");
```

It is **not thread-safe**.

That means if multiple threads update it at the same time, it can give wrong results.

Use `HashMap` when:

```text
Only one thread is using the map
Or external synchronization is already handled
Or map is local to one method
```

---

# 2. Hashtable

`Hashtable` is an older class.

It is thread-safe because its methods are synchronized.

Example:

```java
Map<Integer, String> map = new Hashtable<>();

map.put(1, "Ravi");
map.put(2, "Amit");
```

But the problem is:

```text
Hashtable locks the whole map for most operations.
```

So if one thread is doing `put()`, another thread may have to wait even for another operation.

That makes it slower in high-concurrency systems.

Use of `Hashtable` is mostly avoided in modern Java code.

---

# 3. ConcurrentHashMap

`ConcurrentHashMap` is designed for multithreaded environments.

Example:

```java
Map<Integer, String> map = new ConcurrentHashMap<>();

map.put(1, "Ravi");
map.put(2, "Amit");
```

It is thread-safe.

But it does not lock the whole map for normal operations.

In Java 8, it uses:

```text
CAS
bucket-level locking
volatile reads
linked list
red-black tree
```

So multiple threads can work on different buckets at the same time.

This makes it much better than `Hashtable` for concurrent applications.

---

# Main Difference

| Point           | HashMap                     | Hashtable                                     | ConcurrentHashMap          |
| --------------- | --------------------------- | --------------------------------------------- | -------------------------- |
| Thread-safe     | No                          | Yes                                           | Yes                        |
| Synchronization | No synchronization          | Full method-level synchronization             | Fine-grained locking       |
| Performance     | Fast in single-thread       | Slow in multithreading                        | Fast in multithreading     |
| Null key        | Allows one null key         | Does not allow null key                       | Does not allow null key    |
| Null values     | Allows multiple null values | Does not allow null values                    | Does not allow null values |
| Introduced      | Java 1.2                    | Legacy class from old Java                    | Java 1.5                   |
| Iterator        | Fail-fast                   | Fail-fast style enumeration/iterator behavior | Weakly consistent          |
| Best use        | Single-threaded code        | Legacy code only                              | Multi-threaded code        |

---

# Null Key And Null Value Difference

## HashMap

`HashMap` allows:

```text
One null key
Multiple null values
```

Example:

```java
Map<String, String> map = new HashMap<>();

map.put(null, "No Key");
map.put("name", null);
map.put("city", null);
```

This is allowed.

---

## Hashtable

`Hashtable` does not allow null key or null value.

Example:

```java
Map<String, String> map = new Hashtable<>();

map.put(null, "Ravi");   // NullPointerException
map.put("name", null);  // NullPointerException
```

---

## ConcurrentHashMap

`ConcurrentHashMap` also does not allow null key or null value.

Example:

```java
Map<String, String> map = new ConcurrentHashMap<>();

map.put(null, "Ravi");   // NullPointerException
map.put("name", null);  // NullPointerException
```

Why?

Because in concurrent code, `null` can create confusion.

Example:

```java
map.get("name");
```

If it returns `null`, it becomes unclear:

```text
Is key not present?
Or is key present with null value?
```

So `ConcurrentHashMap` does not allow null.

---

# Synchronization Difference

## HashMap

No synchronization.

```text
Fast but not thread-safe.
```

If many threads update it together, data can become inconsistent.

---

## Hashtable

Full method-level synchronization.

```text
Thread-safe but slower.
```

Example:

```text
Thread 1 calls put()
Whole Hashtable is locked
Thread 2 has to wait
```

This reduces performance.

---

## ConcurrentHashMap

Fine-grained locking.

```text
Thread-safe and better performance.
```

Example:

```text
Thread 1 updates bucket 5
Thread 2 updates bucket 10
Both can work at the same time
```

This is why `ConcurrentHashMap` is preferred in modern concurrent code.

---

# Internal Working Difference

## HashMap Internal Working

`HashMap` uses:

```text
Array of buckets
Linked list for collision
Red-Black Tree after Java 8 for heavy collision
```

It calculates hash of the key and finds the bucket index.

But it does not handle concurrent updates safely.

---

## Hashtable Internal Working

`Hashtable` is also hash table based.

But its methods are synchronized.

Example:

```java
public synchronized V put(K key, V value)
```

Simple meaning:

```text
Only one thread can access important operation at a time.
```

This makes it safe, but slow.

---

## ConcurrentHashMap Internal Working

`ConcurrentHashMap` also uses buckets.

But it uses better concurrency control.

In Java 8:

```text
Reads are mostly lock-free
Empty bucket insert uses CAS
Non-empty bucket update locks only that bucket
Collision uses linked list or tree
```

So it gives thread safety with better performance.

---

# Iterator Difference

## HashMap Iterator

`HashMap` iterator is fail-fast.

Example:

```java
Map<Integer, String> map = new HashMap<>();
map.put(1, "A");
map.put(2, "B");

for (Integer key : map.keySet()) {
    map.put(3, "C"); // Can throw ConcurrentModificationException
}
```

If the map is structurally modified while iterating, it can throw:

```text
ConcurrentModificationException
```

---

## Hashtable Iterator

Modern `Hashtable` collection-view iterators are also fail-fast.

But old-style `Enumeration` is not fail-fast.

Still, `Hashtable` is considered legacy.

So in interviews, keep the focus on:

```text
Hashtable is synchronized and locks the whole map.
```

---

## ConcurrentHashMap Iterator

`ConcurrentHashMap` iterator is weakly consistent.

It does not throw `ConcurrentModificationException`.

Example:

```java
Map<Integer, String> map = new ConcurrentHashMap<>();
map.put(1, "A");
map.put(2, "B");

for (Integer key : map.keySet()) {
    map.put(3, "C"); // Safe
}
```

It may or may not show the latest update during iteration.

But it will not fail.

---

# Performance Difference

## Single Thread

For single-threaded code:

```text
HashMap is usually fastest.
```

Because it has no locking overhead.

## Multi Thread

For multithreaded code:

```text
ConcurrentHashMap is usually better.
```

Because it allows better concurrency.

## Hashtable

`Hashtable` is usually slower in concurrent code because it locks the full map.

---

# When To Use HashMap

Use `HashMap` when thread safety is not needed.

Example:

```java
Map<String, Integer> wordCount = new HashMap<>();
```

Good cases:

```text
Local method variable
Single-threaded code
Temporary data processing
Read-only map after creation
```

---

# When To Use Hashtable

In modern Java, we usually avoid `Hashtable`.

Use it only if:

```text
You are working with old legacy code
Existing code already uses Hashtable
You cannot change the old design immediately
```

For new code, prefer:

```text
HashMap
ConcurrentHashMap
```

depending on thread safety need.

---

# When To Use ConcurrentHashMap

Use `ConcurrentHashMap` when multiple threads read and write the map.

Good examples:

```text
Cache storage
Session-like temporary data
Rate limiter counters
Shared application state
Multi-threaded services
Concurrent lookup tables
```

Example:

```java
ConcurrentHashMap<String, Integer> loginAttempts = new ConcurrentHashMap<>();
```

For atomic updates, use methods like:

```java
map.putIfAbsent("user1", 1);
map.computeIfAbsent("user1", key -> 1);
map.compute("user1", (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
```

---

# Important Atomic Methods In ConcurrentHashMap

`ConcurrentHashMap` gives atomic methods that are very useful.

## putIfAbsent()

```java
map.putIfAbsent("user1", "ACTIVE");
```

Meaning:

```text
Put only if key is not already present.
```

---

## computeIfAbsent()

```java
map.computeIfAbsent("user1", key -> "ACTIVE");
```

Meaning:

```text
If key is missing, calculate and store value.
```

---

## compute()

```java
map.compute("user1", (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
```

Useful for counters.

This is better than:

```java
if (map.containsKey(key)) {
    map.put(key, map.get(key) + 1);
}
```

Because that code is not atomic.

---

# Simple Example

```java
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) {

        Map<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "Ravi");
        hashMap.put(null, "Null Key");
        hashMap.put(2, null);

        Map<Integer, String> hashtable = new Hashtable<>();
        hashtable.put(1, "Amit");
        // hashtable.put(null, "Test"); // NullPointerException

        Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(1, "Neha");
        // concurrentHashMap.put(null, "Test"); // NullPointerException

        System.out.println(hashMap);
        System.out.println(hashtable);
        System.out.println(concurrentHashMap);
    }
}
```

---

# Collections.synchronizedMap() vs ConcurrentHashMap

We can also make `HashMap` synchronized like this:

```java
Map<Integer, String> map = Collections.synchronizedMap(new HashMap<>());
```

But this usually locks the whole map for operations.

`ConcurrentHashMap` is better for high-concurrency use because it uses finer locking.

Simple difference:

```text
Collections.synchronizedMap() -> whole map lock
ConcurrentHashMap             -> bucket-level locking
```

So for modern concurrent code, prefer `ConcurrentHashMap`.

---

# Common Interview Mistakes

## Mistake 1: Saying HashMap Is Thread-Safe

Wrong.

`HashMap` is not thread-safe.

---

## Mistake 2: Saying Hashtable Is Best For Multithreading

Not really.

It is thread-safe, but it is old and slower because it locks the whole map.

`ConcurrentHashMap` is usually better.

---

## Mistake 3: Saying ConcurrentHashMap Allows Null

Wrong.

`ConcurrentHashMap` does not allow null key or null value.

---

## Mistake 4: Using containsKey() Then put() In Concurrent Code

Bad:

```java
if (!map.containsKey(key)) {
    map.put(key, value);
}
```

This is not atomic.

Better:

```java
map.putIfAbsent(key, value);
```

---

## Mistake 5: Expecting ConcurrentHashMap Iterator To Show Latest Data Exactly

Its iterator is weakly consistent.

It is safe, but it may not show all latest changes happening during iteration.

---

# Best Simple Rule

Use this rule:

```text
Use HashMap when thread safety is not needed.

Avoid Hashtable in new code.

Use ConcurrentHashMap when multiple threads access and update the map.
```

---

# Interview-Ready Paragraph Answer

`HashMap`, `Hashtable`, and `ConcurrentHashMap` all store key-value pairs, but they are different in thread safety and performance. `HashMap` is not thread-safe, so it is best for single-threaded use or local data. It allows one null key and multiple null values. `Hashtable` is thread-safe because its methods are synchronized, but it locks the whole map, so performance is poor in high-concurrency scenarios. It also does not allow null keys or null values. `ConcurrentHashMap` is also thread-safe, but it is much better than `Hashtable` for multithreaded code because it does not lock the whole map. In Java 8, reads are mostly lock-free, and writes lock only the affected bucket. It also does not allow null keys or null values. In modern Java, I use `HashMap` when thread safety is not required, avoid `Hashtable` in new code, and use `ConcurrentHashMap` when multiple threads need to access or update the map safely.

---

# 9. ArrayList vs LinkedList

---
## Summary

`ArrayList` and `LinkedList` both are implementations of the `List` interface in Java.

Both maintain insertion order.
Both allow duplicate values.
Both allow `null`.

But internally they are very different.

```text
ArrayList  -> uses dynamic array
LinkedList -> uses doubly linked list
```

## One-Line Answer

**ArrayList is better for searching and random access, while LinkedList is better when frequent insertions or deletions happen in the middle, if we already have the node position.**

---

# 1. What Is ArrayList?

`ArrayList` internally uses a **dynamic array**.

Example:

```java
List<String> names = new ArrayList<>();

names.add("Ravi");
names.add("Amit");
names.add("Neha");
```

Internally, it stores data like this:

```text
Index:  0       1       2
Value: Ravi    Amit    Neha
```

So every element has an index.

That is why accessing data by index is fast.

Example:

```java
names.get(1);
```

Output:

```text
Amit
```

---

# 2. What Is LinkedList?

`LinkedList` internally uses a **doubly linked list**.

Each node stores three things:

```text
Previous node address
Data
Next node address
```

Simple structure:

```text
null <- Ravi <-> Amit <-> Neha -> null
```

Each element is connected to the previous and next element.

It does not store data in continuous memory like an array.

---

# Main Difference

| Point                | ArrayList                    | LinkedList                            |
| -------------------- | ---------------------------- | ------------------------------------- |
| Internal structure   | Dynamic array                | Doubly linked list                    |
| Random access        | Fast                         | Slow                                  |
| `get(index)`         | Fast, O(1)                   | Slow, O(n)                            |
| Add at end           | Usually fast                 | Fast                                  |
| Add/remove in middle | Slow because shifting needed | Faster if node is already found       |
| Memory usage         | Less memory                  | More memory                           |
| Cache performance    | Better                       | Poor compared to ArrayList            |
| Best use             | Read-heavy operations        | Frequent add/remove from start/middle |
| Implements           | List                         | List, Deque                           |

---

# 3. Random Access

## ArrayList

ArrayList is very fast for random access.

Example:

```java
names.get(2);
```

It directly goes to index `2`.

Time complexity:

```text
O(1)
```

Why?

Because array index access is direct.

---

## LinkedList

LinkedList is slow for random access.

Example:

```java
names.get(2);
```

It has to start from the first or last node and move one by one.

Time complexity:

```text
O(n)
```

So if you need a lot of `get(index)`, use `ArrayList`.

---

# 4. Insertion At End

## ArrayList

Adding at the end is usually fast.

```java
list.add("Ravi");
```

Time complexity is usually:

```text
O(1)
```

But when internal array becomes full, ArrayList creates a bigger array and copies old elements.

That resize operation is costly.

Still, average time is considered:

```text
O(1)
```

---

## LinkedList

Adding at the end is also fast.

```java
list.add("Ravi");
```

Because LinkedList keeps reference to first and last node.

Time complexity:

```text
O(1)
```

---

# 5. Insertion In Middle

## ArrayList

Suppose ArrayList has:

```text
Ravi, Amit, Neha
```

Now we insert `"John"` at index `1`.

```java
list.add(1, "John");
```

ArrayList must shift elements to the right.

```text
Before:
Ravi, Amit, Neha

After:
Ravi, John, Amit, Neha
```

Shifting takes time.

Time complexity:

```text
O(n)
```

---

## LinkedList

In LinkedList, if we already have the node position, insertion is fast.

It only changes links.

```text
Ravi <-> Amit <-> Neha
```

Insert John between Ravi and Amit:

```text
Ravi <-> John <-> Amit <-> Neha
```

But there is one important point.

To reach the middle position, LinkedList still needs traversal.

So:

```text
Finding position -> O(n)
Actual insertion -> O(1)
```

In normal Java `LinkedList`, `add(index, value)` still needs traversal.

So it is not always faster than ArrayList in real use.

---

# 6. Deletion

## ArrayList

Removing from middle needs shifting.

Example:

```java
list.remove(1);
```

If we remove index `1`, all elements after it shift left.

Time complexity:

```text
O(n)
```

---

## LinkedList

If node is already known, deletion is fast.

It just changes links.

Time complexity:

```text
O(1)
```

But if we remove by index:

```java
list.remove(5);
```

It first needs to find that index.

So total can be:

```text
O(n)
```

---

# 7. Memory Usage

`ArrayList` uses less memory.

It stores only actual elements in array.

`LinkedList` uses more memory.

Why?

Because each node stores:

```text
Data
Previous node reference
Next node reference
```

So LinkedList has extra memory overhead.

That is why ArrayList is usually more memory-efficient.

---

# 8. Performance In Real Projects

In most real backend applications, `ArrayList` is used more than `LinkedList`.

Why?

Because most operations are:

```text
Add at end
Iterate list
Get by index
Convert data to response
Process records from database
```

For these cases, `ArrayList` is usually better.

Example:

```java
List<UserResponse> users = new ArrayList<>();
```

This is very common.

---

# 9. When To Use ArrayList

Use `ArrayList` when:

```text
You need fast read by index
You mostly add elements at the end
You iterate over data
You want better memory usage
You want better general performance
```

Good examples:

```text
List of users
List of orders
List of products
List of API response DTOs
List of database results
```

Example:

```java
List<String> cities = new ArrayList<>();

cities.add("Jaipur");
cities.add("Delhi");
cities.add("Pune");

System.out.println(cities.get(1));
```

Output:

```text
Delhi
```

---

# 10. When To Use LinkedList

Use `LinkedList` when:

```text
You need frequent add/remove from beginning
You need queue/deque behavior
You do not need frequent random access
```

Example:

```java
Deque<String> queue = new LinkedList<>();

queue.addLast("Task1");
queue.addLast("Task2");

System.out.println(queue.removeFirst());
```

Output:

```text
Task1
```

But even for queue use cases, Java has better options like:

```text
ArrayDeque
ConcurrentLinkedQueue
BlockingQueue
```

So `LinkedList` is not always the first choice.

---

# 11. Time Complexity

| Operation                 | ArrayList    | LinkedList |
| ------------------------- | ------------ | ---------- |
| Add at end                | O(1) average | O(1)       |
| Get by index              | O(1)         | O(n)       |
| Search by value           | O(n)         | O(n)       |
| Add at beginning          | O(n)         | O(1)       |
| Remove from beginning     | O(n)         | O(1)       |
| Add in middle by index    | O(n)         | O(n)       |
| Remove in middle by index | O(n)         | O(n)       |

Important point:

For `LinkedList`, middle add/remove is only fast if you already have the node or iterator position.

With normal index-based operations, traversal still takes time.

---

# 12. Example Comparing get()

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        for (int i = 0; i < 5; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        System.out.println(arrayList.get(3));
        System.out.println(linkedList.get(3));
    }
}
```

Output:

```text
3
3
```

Both give same output.

But internally:

```text
ArrayList directly goes to index 3.
LinkedList traverses nodes to reach index 3.
```

---

# 13. Example Of Add At Beginning

```java
List<Integer> arrayList = new ArrayList<>();
arrayList.add(0, 10);
arrayList.add(0, 20);
arrayList.add(0, 30);

System.out.println(arrayList);
```

Output:

```text
[30, 20, 10]
```

This works.

But internally, ArrayList shifts elements every time.

For LinkedList:

```java
LinkedList<Integer> linkedList = new LinkedList<>();

linkedList.addFirst(10);
linkedList.addFirst(20);
linkedList.addFirst(30);

System.out.println(linkedList);
```

Output:

```text
[30, 20, 10]
```

Here, adding at beginning is efficient.

---

# 14. ArrayList Growth

ArrayList has an internal array.

When array becomes full, it grows.

Example:

```text
Old capacity is full.
ArrayList creates bigger array.
Old elements are copied to new array.
New element is added.
```

This resize is costly.

But it does not happen on every add.

So average `add()` at end is still considered O(1).

---

# 15. LinkedList Does Not Need Resizing

LinkedList does not need resizing like ArrayList.

Each new element creates a new node.

But each node needs extra memory.

So LinkedList avoids array resizing but uses more memory.

---

# 16. Which One Is Better?

In most cases:

```text
ArrayList is better as default choice.
```

Why?

Because it is fast for reading, uses less memory, and has better real-world performance.

Use LinkedList only when you have a clear reason.

Example:

```text
Frequent add/remove from start
Deque operations
No need for random access
```

Even then, for deque operations, `ArrayDeque` is often better than `LinkedList`.

---

# Common Interview Mistakes

## Mistake 1: Saying LinkedList Is Always Faster For Insertion

Not always.

If insertion is by index, LinkedList first needs traversal.

So it can still be O(n).

---

## Mistake 2: Saying ArrayList Is Always Better

ArrayList is usually better, but not always.

If you need many insertions/removals at the beginning, LinkedList can be better.

---

## Mistake 3: Ignoring Memory

LinkedList uses more memory because every node stores extra references.

---

## Mistake 4: Forgetting Random Access

ArrayList supports fast random access.

LinkedList does not.

This is one of the biggest differences.

---

# Best Practical Rule

Use this simple rule:

```text
Use ArrayList by default.

Use LinkedList only when you need frequent add/remove from beginning or deque-like behavior.
```

For most backend work like DTO lists, database result lists, and response lists, `ArrayList` is the better choice.

---

# Interview-Ready Paragraph Answer

`ArrayList` and `LinkedList` both implement the `List` interface, but internally they work differently. `ArrayList` uses a dynamic array, so accessing elements by index is very fast, usually O(1). It is good when we mostly read data, iterate data, or add elements at the end. But insertion or deletion in the middle can be slow because elements need to be shifted. `LinkedList` uses a doubly linked list, where each node has data, previous reference, and next reference. It is better when we frequently add or remove elements from the beginning or when we need queue/deque behavior. But random access is slow because it has to traverse nodes, so `get(index)` is O(n). Also, LinkedList uses more memory because every node stores extra references. In real backend projects, I usually prefer `ArrayList` as the default choice because it is faster for common use cases and uses less memory.

---

# 10. HashSet internal working

---
## Summary

`HashSet` stores **unique elements only**.

It does not allow duplicate values.

Internally, `HashSet` uses a **HashMap**.

```text
HashSet = internally backed by HashMap
```

## One-Line Answer

**HashSet internally uses HashMap, where the value we add in HashSet becomes the key of HashMap, and a dummy constant object is stored as the value.**

---

# What Is HashSet?

`HashSet` is a collection in Java.

It stores only unique elements.

Example:

```java
Set<String> names = new HashSet<>();

names.add("Ravi");
names.add("Amit");
names.add("Ravi");

System.out.println(names);
```

Output can be:

```text
[Ravi, Amit]
```

`"Ravi"` is added only once.

Because `HashSet` does not allow duplicates.

---

# Internal Working Of HashSet

Internally, `HashSet` uses `HashMap`.

When we write:

```java
set.add("Ravi");
```

Internally, HashSet does something like:

```java
map.put("Ravi", PRESENT);
```

Here:

```text
"Ravi"  -> key in HashMap
PRESENT -> dummy constant value
```

So the value added in HashSet becomes the **key** inside HashMap.

The dummy value is just a fixed object.

---

# Internal Code Idea

Inside HashSet, it is roughly like this:

```java
private transient HashMap<E, Object> map;

private static final Object PRESENT = new Object();

public boolean add(E e) {
    return map.put(e, PRESENT) == null;
}
```

This is the main idea.

So HashSet uniqueness is actually handled by HashMap keys.

---

# Why HashSet Does Not Allow Duplicates?

Because HashMap keys are unique.

Example:

```java
set.add("Ravi");
set.add("Ravi");
```

Internally:

```java
map.put("Ravi", PRESENT);
map.put("Ravi", PRESENT);
```

The second put does not create a new key.

It replaces the value for the same key.

But since the value is always the same dummy object, nothing important changes.

That is why duplicate is not added.

---

# Role Of hashCode() And equals()

HashSet uses two methods to check duplicates:

```text
hashCode()
equals()
```

Simple rule:

```text
hashCode() finds the bucket.
equals() checks the exact object.
```

When we add an element:

```java
set.add("Ravi");
```

Java does this internally:

```text
1. Calculate hashCode of "Ravi"
2. Find bucket index
3. Check if bucket already has same element
4. Use equals() to confirm
5. If same element exists, do not add
6. If not present, add it
```

---

# Example With Custom Object

Suppose we have an `Employee` class.

```java
class Employee {

    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

Now:

```java
Set<Employee> employees = new HashSet<>();

employees.add(new Employee(1, "Ravi"));
employees.add(new Employee(1, "Ravi"));

System.out.println(employees.size());
```

Output:

```text
2
```

Why?

Because we did not override `equals()` and `hashCode()`.

So Java treats both objects as different objects.

Even though data is same.

---

# Correct Custom Object Example

```java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Employee {

    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

public class Main {

    public static void main(String[] args) {

        Set<Employee> employees = new HashSet<>();

        employees.add(new Employee(1, "Ravi"));
        employees.add(new Employee(1, "Ravi Kumar"));

        System.out.println(employees.size());
    }
}
```

Output:

```text
1
```

Why?

Because both employees have the same `id`.

We wrote `equals()` and `hashCode()` based on `id`.

So HashSet treats them as duplicate.

---

# Important Internal Flow Of add()

When we call:

```java
set.add(element);
```

Internal flow is:

```text
1. HashSet calls HashMap.put(element, PRESENT)
2. HashMap calculates hash of element
3. HashMap finds bucket index
4. If bucket is empty, element is added
5. If bucket has elements, collision is checked
6. equals() is used to compare elements
7. If same element exists, add() returns false
8. If new element is added, add() returns true
```

Example:

```java
Set<String> set = new HashSet<>();

System.out.println(set.add("Ravi"));
System.out.println(set.add("Ravi"));
```

Output:

```text
true
false
```

First add returns `true`.

Second add returns `false` because `"Ravi"` is already present.

---

# Collision In HashSet

Collision means two different objects have the same bucket index.

Example:

```text
Object A -> bucket 5
Object B -> bucket 5
```

HashSet handles collision the same way HashMap does.

Internally, HashMap uses:

```text
Array
Linked List
Red-Black Tree after Java 8
```

If many elements go into the same bucket, they are stored in a linked list.

If the bucket becomes too large, it can become a Red-Black Tree.

This improves performance.

---

# Does HashSet Maintain Order?

No.

`HashSet` does not guarantee insertion order.

Example:

```java
Set<String> set = new HashSet<>();

set.add("A");
set.add("B");
set.add("C");

System.out.println(set);
```

Output can be:

```text
[A, B, C]
```

But it is not guaranteed.

It can also come in a different order.

If we want insertion order, use:

```java
LinkedHashSet
```

If we want sorted order, use:

```java
TreeSet
```

---

# Does HashSet Allow null?

Yes.

`HashSet` allows one `null` value.

Example:

```java
Set<String> set = new HashSet<>();

set.add(null);
set.add(null);

System.out.println(set.size());
```

Output:

```text
1
```

Because duplicates are not allowed.

So only one `null` is stored.

---

# Is HashSet Thread-Safe?

No.

`HashSet` is not thread-safe.

If multiple threads modify it at the same time, it can cause problems.

For thread-safe set, we can use:

```java
Set<String> set = ConcurrentHashMap.newKeySet();
```

Or:

```java
Set<String> set = Collections.synchronizedSet(new HashSet<>());
```

In modern concurrent code, `ConcurrentHashMap.newKeySet()` is often a better option.

---

# Time Complexity

Average time complexity:

```text
add()      -> O(1)
remove()   -> O(1)
contains() -> O(1)
```

Worst case before tree:

```text
O(n)
```

With Java 8 tree structure in heavy collision:

```text
O(log n)
```

So normally HashSet is very fast.

---

# contains() Internal Working

Example:

```java
set.contains("Ravi");
```

Internally:

```text
1. Calculate hashCode of "Ravi"
2. Find bucket
3. Use equals() to check exact element
4. Return true or false
```

Since it uses hashing, `contains()` is usually very fast.

---

# remove() Internal Working

Example:

```java
set.remove("Ravi");
```

Internally:

```text
1. HashSet calls HashMap.remove("Ravi")
2. HashMap finds bucket using hashCode()
3. Finds exact key using equals()
4. Removes the key-value pair
```

So remove also depends on `hashCode()` and `equals()`.

---

# HashSet vs ArrayList For Searching

If we need to check whether an element exists, HashSet is usually better.

Example:

```java
set.contains("Ravi");
```

Average time:

```text
O(1)
```

For ArrayList:

```java
list.contains("Ravi");
```

Time:

```text
O(n)
```

Because ArrayList checks elements one by one.

So for frequent search, HashSet is better.

---

# Mutable Objects In HashSet

This is a very important interview point.

Do not change an object after adding it to HashSet if that field is used in `hashCode()` or `equals()`.

Example:

```java
Employee emp = new Employee(1, "Ravi");

Set<Employee> set = new HashSet<>();
set.add(emp);

// If id changes here, HashSet may not find it correctly.
```

Why?

Because HashSet stores the object based on its hash.

If the object’s hash changes after insertion, it may be searched in the wrong bucket.

So HashSet may not find it again.

Best practice:

```text
Use immutable fields for equals() and hashCode()
```

---

# HashSet vs LinkedHashSet vs TreeSet

| Set Type      | Internal Structure | Order               |
| ------------- | ------------------ | ------------------- |
| HashSet       | HashMap            | No guaranteed order |
| LinkedHashSet | LinkedHashMap      | Insertion order     |
| TreeSet       | TreeMap            | Sorted order        |

Use `HashSet` when order does not matter.

Use `LinkedHashSet` when insertion order matters.

Use `TreeSet` when sorted order matters.

---

# Common Interview Mistakes

## Mistake 1: Saying HashSet Uses Hashing Directly Only

Better answer:

```text
HashSet internally uses HashMap.
```

Elements are stored as keys in HashMap.

---

## Mistake 2: Forgetting equals() And hashCode()

HashSet depends on both.

If custom objects do not override them properly, duplicates may not be detected correctly.

---

## Mistake 3: Saying HashSet Maintains Order

Wrong.

HashSet does not guarantee order.

---

## Mistake 4: Saying HashSet Is Thread-Safe

Wrong.

HashSet is not thread-safe.

---

## Mistake 5: Using Mutable Objects Carelessly

If object fields used in `hashCode()` change after insertion, HashSet behavior can break.

---

# Best Simple Explanation

You can remember HashSet like this:

```text
HashSet is like HashMap without values.

The element we add becomes the key.
A dummy object becomes the value.
Since HashMap keys are unique, HashSet elements are also unique.
```

---

# Interview-Ready Paragraph Answer

`HashSet` is used to store unique elements in Java. Internally, it is backed by a `HashMap`. When we add an element to a HashSet, that element is stored as a key inside the internal HashMap, and a dummy constant object is stored as the value. Since HashMap keys are unique, HashSet also does not allow duplicate elements. To check whether an element is duplicate or not, HashSet uses `hashCode()` to find the bucket and `equals()` to compare the exact object. That is why for custom objects, we should properly override both `equals()` and `hashCode()`. HashSet does not maintain insertion order, allows one null value, and is not thread-safe. Its `add`, `remove`, and `contains` operations are usually O(1) on average. In simple words, HashSet gives uniqueness by using HashMap internally.

---

# 11. equals() and hashCode()

---
## Summary

`equals()` and `hashCode()` are very important in Java.

They are mainly used to compare objects correctly, especially in collections like:

```text
HashMap
HashSet
Hashtable
ConcurrentHashMap
```

## One-Line Answer

**`equals()` checks whether two objects are logically equal, and `hashCode()` returns an integer value used to place objects inside hash-based collections.**

---

# What Is equals()?

`equals()` is used to compare two objects.

By default, `equals()` from the `Object` class compares memory address.

That means it checks whether both references point to the same object.

Example:

```java
class Employee {

    int id;
    String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

```java
public class Main {
    public static void main(String[] args) {

        Employee e1 = new Employee(1, "Ravi");
        Employee e2 = new Employee(1, "Ravi");

        System.out.println(e1.equals(e2));
    }
}
```

Output:

```text
false
```

Why?

Because `e1` and `e2` are two different objects in memory.

Even though their data is same, default `equals()` does not compare field values.

---

# Why Override equals()?

In real projects, we usually want to compare objects based on business fields.

Example:

```text
Two Employee objects with same id should be treated as same employee.
```

So we override `equals()`.

Example:

```java
import java.util.Objects;

class Employee {

    private int id;
    private String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) obj;

        return this.id == other.id;
    }
}
```

Now equality is based on `id`.

---

# What Is hashCode()?

`hashCode()` returns an integer value for an object.

Hash-based collections use this value to decide where to store the object.

Example:

```java
Employee e1 = new Employee(1, "Ravi");

System.out.println(e1.hashCode());
```

It may print something like:

```text
12345678
```

The exact number is not important.

Important point:

```text
HashMap and HashSet use hashCode() to find the bucket.
```

---

# Why Override hashCode()?

If we override `equals()`, we must also override `hashCode()`.

This is the most important rule.

Why?

Because collections like `HashMap` and `HashSet` first use `hashCode()` to find the bucket.

Then they use `equals()` to compare exact objects.

Simple line:

```text
hashCode() decides the bucket.
equals() decides the exact match.
```

---

# Correct Example With equals() And hashCode()

```java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Employee {

    private int id;
    private String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) obj;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

public class Main {

    public static void main(String[] args) {

        Set<Employee> employees = new HashSet<>();

        employees.add(new Employee(1, "Ravi"));
        employees.add(new Employee(1, "Ravi Kumar"));

        System.out.println(employees.size());
    }
}
```

Output:

```text
1
```

Why?

Because both objects have the same `id`.

So according to our logic, they are equal.

Also, their hash code is based on the same `id`.

So `HashSet` treats them as duplicate.

---

# What Happens If We Override equals() But Not hashCode()?

This is a common interview trap.

Example:

```java
import java.util.HashSet;
import java.util.Set;

class Employee {

    private int id;
    private String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) obj;

        return this.id == other.id;
    }
}

public class Main {

    public static void main(String[] args) {

        Set<Employee> employees = new HashSet<>();

        employees.add(new Employee(1, "Ravi"));
        employees.add(new Employee(1, "Ravi Kumar"));

        System.out.println(employees.size());
    }
}
```

Output may be:

```text
2
```

Why?

Because `equals()` says both objects are equal.

But `hashCode()` is still coming from `Object` class.

So both objects may get different hash codes.

HashSet may put them in different buckets.

So duplicate detection fails.

That is why:

```text
If equals() is overridden, hashCode() must also be overridden.
```

---

# equals() And hashCode() Contract

Java has a contract for these methods.

## Rule 1

If two objects are equal using `equals()`, then their `hashCode()` must be same.

```text
If a.equals(b) is true,
then a.hashCode() must be equal to b.hashCode().
```

This is mandatory.

---

## Rule 2

If two objects have same hash code, they may or may not be equal.

```text
Same hashCode does not always mean objects are equal.
```

Why?

Because hash collision can happen.

Example:

```text
Object A hashCode = 100
Object B hashCode = 100
```

Still, they can be different objects.

So Java uses `equals()` after hash code check.

---

## Rule 3

If object data does not change, hashCode should return same value during one execution.

Example:

```java
employee.hashCode();
employee.hashCode();
employee.hashCode();
```

If the fields used in hashCode are not changed, it should return the same value.

---

# Very Important Line

Remember this line for interviews:

```text
Equal objects must have same hash code, but same hash code does not guarantee equal objects.
```

This line is very important.

---

# How HashMap Uses equals() And hashCode()

Suppose we do:

```java
map.put(employee, "Developer");
```

Internal flow:

```text
1. HashMap calls employee.hashCode()
2. It finds the bucket index
3. It stores the key-value pair in that bucket
```

Now when we do:

```java
map.get(employee);
```

Internal flow:

```text
1. HashMap again calls employee.hashCode()
2. It goes to the same bucket
3. It compares keys using equals()
4. If key matches, value is returned
```

So both methods are needed.

---

# HashMap Example

```java
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class Employee {

    private int id;
    private String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Employee)) {
            return false;
        }

        Employee other = (Employee) obj;

        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

public class Main {

    public static void main(String[] args) {

        Map<Employee, String> map = new HashMap<>();

        Employee e1 = new Employee(1, "Ravi");
        Employee e2 = new Employee(1, "Ravi Kumar");

        map.put(e1, "Backend Developer");

        System.out.println(map.get(e2));
    }
}
```

Output:

```text
Backend Developer
```

Why?

Because `e1` and `e2` are equal based on `id`.

Their hash code is also same because it is based on `id`.

So HashMap can find the value.

---

# What If hashCode() Is Bad?

Suppose we write:

```java
@Override
public int hashCode() {
    return 1;
}
```

This is technically valid if `equals()` contract is not broken.

But it is bad for performance.

Why?

Because every object gets same hash code.

So all objects go into the same bucket.

Then HashMap or HashSet becomes slow.

Instead of good distribution, everything becomes a collision.

So hashCode should be written properly.

Use:

```java
Objects.hash(id);
```

or include important immutable fields.

---

# Mutable Fields Problem

This is a very important point.

Do not use fields in `equals()` and `hashCode()` that can change after object is added into HashMap or HashSet.

Bad example:

```java
Employee emp = new Employee(1, "Ravi");

Set<Employee> set = new HashSet<>();
set.add(emp);

// Suppose id changes here
emp.setId(2);

System.out.println(set.contains(emp));
```

This may return:

```text
false
```

Why?

Because HashSet stored the object using old hash.

After `id` changed, hash changed.

Now HashSet searches in a different bucket.

So it cannot find the object properly.

Best practice:

```text
Use immutable fields for equals() and hashCode().
```

---

# equals() Rules

A correct `equals()` method should follow these rules:

## 1. Reflexive

An object should be equal to itself.

```java
a.equals(a) == true
```

---

## 2. Symmetric

If `a` equals `b`, then `b` should equal `a`.

```java
a.equals(b) == true
b.equals(a) == true
```

---

## 3. Transitive

If `a` equals `b`, and `b` equals `c`, then `a` should equal `c`.

```java
a.equals(b) == true
b.equals(c) == true
a.equals(c) == true
```

---

## 4. Consistent

If object data does not change, result should remain same.

```java
a.equals(b)
```

should return same result again and again.

---

## 5. Null Check

Any object compared with null should return false.

```java
a.equals(null) == false
```

---

# String Example

`String` class already overrides `equals()` and `hashCode()`.

Example:

```java
String s1 = new String("Ravi");
String s2 = new String("Ravi");

System.out.println(s1 == s2);
System.out.println(s1.equals(s2));
```

Output:

```text
false
true
```

Why?

```text
== compares reference.
equals() compares content.
```

So `s1 == s2` is false because both are different objects.

But `s1.equals(s2)` is true because both have same text.

---

# Difference Between == And equals()

| Point          | `==`                      | `equals()`              |
| -------------- | ------------------------- | ----------------------- |
| Used for       | Reference comparison      | Logical comparison      |
| For primitives | Compares values           | Not used                |
| For objects    | Compares memory reference | Can compare object data |
| Can override   | No                        | Yes                     |
| Example        | `s1 == s2`                | `s1.equals(s2)`         |

Example:

```java
String a = new String("Java");
String b = new String("Java");

System.out.println(a == b);
System.out.println(a.equals(b));
```

Output:

```text
false
true
```

---

# How To Generate equals() And hashCode()

In real projects, we usually do not write these manually every time.

We can generate them using:

```text
IDE
Lombok
Java records
```

Example with Lombok:

```java
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
class Employee {
    private int id;
    private String name;
}
```

Example with Java record:

```java
public record Employee(int id, String name) {
}
```

Records automatically provide `equals()`, `hashCode()`, and `toString()`.

---

# Common Interview Mistakes

## Mistake 1: Overriding equals() But Not hashCode()

This breaks HashMap and HashSet behavior.

Always override both together.

---

## Mistake 2: Thinking Same hashCode Means Same Object

Wrong.

Same hash code can happen for different objects.

That is called collision.

---

## Mistake 3: Using Mutable Fields

If fields used in hashCode change after insertion into HashSet or HashMap, lookup may fail.

---

## Mistake 4: Using == Instead Of equals() For Objects

For object content comparison, use `equals()`.

For reference comparison, use `==`.

---

## Mistake 5: Not Handling null In equals()

`equals()` should return false for null.

It should not throw `NullPointerException`.

---

# Best Practical Rule

Use this rule:

```text
If a class object will be used as a key in HashMap
or stored in HashSet,
then implement equals() and hashCode() carefully.
```

For example:

```text
Employee key in HashMap
Product in HashSet
User object in Set
Account object as key
```

Use stable and unique fields like:

```text
id
email
employeeCode
accountNumber
```

---

# Interview-Ready Paragraph Answer

`equals()` and `hashCode()` are methods from the `Object` class. `equals()` is used to check logical equality between two objects, while `hashCode()` returns an integer value used by hash-based collections like `HashMap` and `HashSet`. By default, `equals()` compares object references, but in real projects we often override it to compare objects based on fields like id or email. If we override `equals()`, we must also override `hashCode()`. The main rule is that if two objects are equal according to `equals()`, then their hash code must also be same. But two objects with the same hash code are not always equal because collisions can happen. HashMap uses `hashCode()` to find the bucket and then uses `equals()` to find the exact key. We should also avoid using mutable fields in `equals()` and `hashCode()` because changing those fields after adding the object to HashMap or HashSet can break lookup.

---

# 12. Fail-fast vs fail-safe iterator

---
## Summary

**Fail-fast iterator** throws `ConcurrentModificationException` if the collection is modified while iterating.

**Fail-safe iterator** does not throw this exception because it usually works on a copy or a weakly consistent view.

```text
Fail-fast  = detects modification and fails quickly
Fail-safe  = does not fail during modification
```

## One-Line Answer

**Fail-fast iterators throw `ConcurrentModificationException` on structural modification, while fail-safe iterators allow concurrent modification without throwing that exception.**

---

# What Is Fail-Fast Iterator?

Fail-fast iterator fails immediately when it detects that the collection is modified while iteration is going on.

Example collections:

```text
ArrayList
HashMap
HashSet
LinkedList
Vector iterator
```

Example:

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();

        names.add("Ravi");
        names.add("Amit");
        names.add("Neha");

        for (String name : names) {
            if (name.equals("Amit")) {
                names.remove(name);
            }
        }

        System.out.println(names);
    }
}
```

Output:

```text
ConcurrentModificationException
```

Why?

Because we are modifying the `ArrayList` while iterating over it.

---

# Why It Throws ConcurrentModificationException

Most fail-fast iterators internally track a modification count.

In collections like `ArrayList`, there is an internal counter called something like:

```text
modCount
```

When we add or remove elements, `modCount` changes.

Iterator stores the expected value.

Example:

```text
expectedModCount = modCount at iterator creation time
```

During iteration, iterator checks:

```text
Is current modCount same as expectedModCount?
```

If not, it throws:

```text
ConcurrentModificationException
```

Simple meaning:

```text
Collection changed while I was iterating.
So I cannot continue safely.
```

---

# What Is Structural Modification?

Structural modification means changing the size or structure of collection.

Examples:

```text
Adding element
Removing element
Clearing collection
Changing map entries by adding/removing keys
```

Example:

```java
list.add("John");
list.remove("Ravi");
map.put(1, "Test");
map.remove(1);
```

These are structural changes.

But updating an existing object’s field may not be structural modification.

---

# Correct Way To Remove While Iterating

If you want to remove elements while iterating, use iterator’s own `remove()` method.

Example:

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();

        names.add("Ravi");
        names.add("Amit");
        names.add("Neha");

        Iterator<String> iterator = names.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();

            if (name.equals("Amit")) {
                iterator.remove();
            }
        }

        System.out.println(names);
    }
}
```

Output:

```text
[Ravi, Neha]
```

This works because we are removing using the iterator itself.

---

# What Is Fail-Safe Iterator?

Fail-safe iterator does not throw `ConcurrentModificationException` if the collection is modified while iteration is going on.

Example collections:

```text
CopyOnWriteArrayList
ConcurrentHashMap
```

But one important point:

```text
Fail-safe is a common interview term.
Java documentation usually uses terms like snapshot iterator or weakly consistent iterator.
```

Still, in interviews, people commonly say “fail-safe iterator”.

---

# CopyOnWriteArrayList Example

`CopyOnWriteArrayList` works on a copy of the list while iterating.

Example:

```java
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {

        CopyOnWriteArrayList<String> names = new CopyOnWriteArrayList<>();

        names.add("Ravi");
        names.add("Amit");
        names.add("Neha");

        Iterator<String> iterator = names.iterator();

        while (iterator.hasNext()) {
            String name = iterator.next();

            if (name.equals("Amit")) {
                names.remove(name);
            }

            System.out.println(name);
        }

        System.out.println("Final List: " + names);
    }
}
```

Output:

```text
Ravi
Amit
Neha
Final List: [Ravi, Neha]
```

Notice this carefully.

The iterator still printed `"Amit"` because it was iterating over the old snapshot.

But the final list does not contain `"Amit"`.

---

# ConcurrentHashMap Iterator Example

`ConcurrentHashMap` iterator is weakly consistent.

It does not throw `ConcurrentModificationException`.

Example:

```java
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {

        Map<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1, "Ravi");
        map.put(2, "Amit");
        map.put(3, "Neha");

        Iterator<Integer> iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            Integer key = iterator.next();

            if (key == 2) {
                map.put(4, "John");
                map.remove(1);
            }

            System.out.println(key);
        }

        System.out.println(map);
    }
}
```

This will not throw `ConcurrentModificationException`.

But the iterator may or may not show the latest changes during iteration.

That is why it is called weakly consistent.

---

# Main Difference

| Point          | Fail-Fast Iterator                | Fail-Safe Iterator                          |
| -------------- | --------------------------------- | ------------------------------------------- |
| Behavior       | Throws exception on modification  | Does not throw exception                    |
| Exception      | `ConcurrentModificationException` | No `ConcurrentModificationException`        |
| Works on       | Original collection               | Copy or weakly consistent view              |
| Examples       | `ArrayList`, `HashMap`, `HashSet` | `CopyOnWriteArrayList`, `ConcurrentHashMap` |
| Performance    | Faster, less memory               | Can use more memory or extra logic          |
| Data freshness | Direct current collection         | May not show latest changes                 |
| Thread safety  | Not thread-safe by default        | Designed for concurrent use                 |

---

# Fail-Fast Example With HashMap

```java
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();

        map.put(1, "Ravi");
        map.put(2, "Amit");
        map.put(3, "Neha");

        for (Integer key : map.keySet()) {
            if (key == 2) {
                map.put(4, "John");
            }
        }

        System.out.println(map);
    }
}
```

Output:

```text
ConcurrentModificationException
```

Because `HashMap` iterator is fail-fast.

---

# Fail-Safe Example With ConcurrentHashMap

```java
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static void main(String[] args) {

        Map<Integer, String> map = new ConcurrentHashMap<>();

        map.put(1, "Ravi");
        map.put(2, "Amit");
        map.put(3, "Neha");

        for (Integer key : map.keySet()) {
            if (key == 2) {
                map.put(4, "John");
            }
        }

        System.out.println(map);
    }
}
```

Output can be:

```text
{1=Ravi, 2=Amit, 3=Neha, 4=John}
```

No exception is thrown.

---

# Important Point About Fail-Safe

Fail-safe does not always mean it shows the latest data.

For example, `CopyOnWriteArrayList` iterator works on a snapshot.

So if you modify the list after iterator creation, the iterator may not see that change.

Example:

```text
Iterator created
List modified
Iterator still reads old copy
```

That is safe, but not always latest.

---

# CopyOnWriteArrayList Performance Point

`CopyOnWriteArrayList` is good when reads are very high and writes are very low.

Why?

Because every write creates a new copy of the array.

Good use case:

```text
Many reads
Very few writes
```

Bad use case:

```text
Frequent writes
Large list
```

Because copying becomes costly.

---

# Fail-Fast Does Not Guarantee 100% Detection

This is another good interview point.

Fail-fast iterator tries to detect modification.

But it is not guaranteed in every possible thread timing case.

So do not use `ConcurrentModificationException` as a control mechanism.

It is mainly to detect bugs early.

---

# How To Avoid ConcurrentModificationException

Use one of these approaches:

```text
Use iterator.remove()
Use removeIf()
Use CopyOnWriteArrayList
Use ConcurrentHashMap
Collect items to remove later
Use proper synchronization
```

Example using `removeIf()`:

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();

        names.add("Ravi");
        names.add("Amit");
        names.add("Neha");

        names.removeIf(name -> name.equals("Amit"));

        System.out.println(names);
    }
}
```

Output:

```text
[Ravi, Neha]
```

This is clean and safe.

---

# Common Interview Mistakes

## Mistake 1: Saying Fail-Safe Is Official Java Term

In interviews, this term is common.

But technically, Java documentation often says:

```text
snapshot iterator
weakly consistent iterator
```

Still, interviewers understand “fail-safe iterator”.

---

## Mistake 2: Saying Fail-Safe Always Shows Latest Data

Wrong.

It may not show latest changes.

`CopyOnWriteArrayList` works on snapshot.

`ConcurrentHashMap` iterator is weakly consistent.

---

## Mistake 3: Removing Directly From ArrayList In For-Each Loop

Bad:

```java
for (String name : names) {
    names.remove(name);
}
```

Use iterator’s `remove()` or `removeIf()`.

---

## Mistake 4: Thinking ConcurrentModificationException Happens Only In Multithreading

Wrong.

It can happen in single-threaded code also.

Example:

```java
for (String name : list) {
    list.remove(name);
}
```

This can throw exception even in one thread.

---

# Best Simple Rule

Use this rule:

```text
Fail-fast iterator detects unsafe modification and throws exception.

Fail-safe iterator allows modification but may not show latest data.
```

---

# Interview-Ready Paragraph Answer

Fail-fast and fail-safe iterators are different in how they behave when a collection is modified during iteration. Fail-fast iterators are used by collections like `ArrayList`, `HashMap`, and `HashSet`. If we structurally modify the collection while iterating, they throw `ConcurrentModificationException`. Internally, they use a modification count like `modCount` to detect changes. Fail-safe iterators do not throw this exception. Collections like `CopyOnWriteArrayList` and `ConcurrentHashMap` are common examples. `CopyOnWriteArrayList` iterates over a snapshot copy, so it may not show the latest changes. `ConcurrentHashMap` iterator is weakly consistent, so it may or may not reflect updates happening during iteration. In simple words, fail-fast fails quickly when the collection changes, while fail-safe continues safely but may not always show fresh data.

---

# 13. Checked vs unchecked exception

---
## Summary

Java exceptions are mainly divided into two types:

```text
Checked exception
Unchecked exception
```

**Checked exceptions are checked at compile time.**
**Unchecked exceptions are checked at runtime.**

## One-Line Answer

**Checked exceptions must be handled or declared using `throws`, while unchecked exceptions are not forced by the compiler and usually happen because of programming mistakes.**

---

# What Is An Exception?

An exception is an unexpected problem that happens while the program is running.

Example:

```text
File not found
Database connection failed
Null value used
Array index out of range
Invalid input
```

Java uses exceptions to handle these problems in a controlled way.

---

# 1. Checked Exception

Checked exceptions are checked by the compiler.

That means the compiler forces us to handle them.

We must either:

```text
Handle it using try-catch
Or declare it using throws
```

Example:

```java
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("test.txt");
        } catch (IOException e) {
            System.out.println("File issue happened");
        }
    }
}
```

Here, `FileReader` can throw `FileNotFoundException`.

So Java forces us to handle it.

---

# Common Checked Exceptions

Examples:

```text
IOException
FileNotFoundException
SQLException
ClassNotFoundException
InterruptedException
ParseException
```

These are checked exceptions.

They usually represent external problems.

Example:

```text
File may not exist
Database may be down
Network may fail
Thread may be interrupted
```

---

# Checked Exception With throws

Instead of handling the exception inside the method, we can declare it.

Example:

```java
import java.io.FileReader;
import java.io.IOException;

public class FileService {

    public void readFile() throws IOException {
        FileReader reader = new FileReader("test.txt");
    }
}
```

Now whoever calls `readFile()` must handle or declare the exception.

---

# 2. Unchecked Exception

Unchecked exceptions are not checked by the compiler.

The compiler does not force us to handle them.

They usually happen because of programming mistakes.

Example:

```java
public class Main {

    public static void main(String[] args) {
        String name = null;
        System.out.println(name.length());
    }
}
```

Output:

```text
NullPointerException
```

The compiler does not force us to catch it.

But at runtime, it fails.

---

# Common Unchecked Exceptions

Examples:

```text
NullPointerException
ArithmeticException
ArrayIndexOutOfBoundsException
IllegalArgumentException
NumberFormatException
ClassCastException
IllegalStateException
```

These are unchecked exceptions.

They are subclasses of `RuntimeException`.

---

# Exception Hierarchy

Simple hierarchy:

```text
Throwable
   |
   |-- Error
   |
   |-- Exception
          |
          |-- RuntimeException
          |      |
          |      |-- NullPointerException
          |      |-- ArithmeticException
          |      |-- IllegalArgumentException
          |
          |-- IOException
          |-- SQLException
          |-- ClassNotFoundException
```

Important point:

```text
Checked exceptions are direct subclasses of Exception, except RuntimeException.

Unchecked exceptions are subclasses of RuntimeException.
```

Also, `Error` is unchecked too.

But we normally do not handle `Error`.

Examples:

```text
OutOfMemoryError
StackOverflowError
```

These are serious JVM-level problems.

---

# Main Difference

| Point                             | Checked Exception             | Unchecked Exception                                |
| --------------------------------- | ----------------------------- | -------------------------------------------------- |
| Checked by compiler               | Yes                           | No                                                 |
| Handling required                 | Yes                           | No                                                 |
| Parent class                      | `Exception`                   | `RuntimeException`                                 |
| Happens due to                    | External conditions           | Programming mistakes                               |
| Examples                          | `IOException`, `SQLException` | `NullPointerException`, `IllegalArgumentException` |
| Must use try-catch/throws         | Yes                           | No                                                 |
| Compile-time error if not handled | Yes                           | No                                                 |

---

# Example Of Checked Exception

```java
import java.io.FileReader;

public class Main {

    public static void main(String[] args) {
        FileReader reader = new FileReader("abc.txt");
    }
}
```

This will not compile.

Because `FileReader` may throw `FileNotFoundException`.

Compiler says:

```text
Unhandled exception: FileNotFoundException
```

So we must handle it:

```java
import java.io.FileReader;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("abc.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
```

---

# Example Of Unchecked Exception

```java
public class Main {

    public static void main(String[] args) {
        int result = 10 / 0;
        System.out.println(result);
    }
}
```

This code compiles.

But at runtime, it throws:

```text
ArithmeticException
```

Because division by zero is a runtime problem.

---

# Why Checked Exceptions Exist

Checked exceptions are used when the caller can reasonably recover from the problem.

Example:

```text
File not found
Database connection issue
Network issue
Invalid file format
```

The caller may handle it by:

```text
Showing error message
Retrying
Using fallback
Asking user to upload file again
Closing resources properly
```

Example:

```java
try {
    fileService.readFile();
} catch (IOException e) {
    System.out.println("Please upload the file again");
}
```

---

# Why Unchecked Exceptions Exist

Unchecked exceptions usually mean code issue or wrong usage.

Example:

```text
Calling method on null object
Passing invalid argument
Accessing invalid array index
Parsing wrong number format
```

Example:

```java
public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive");
    }
}
```

Here `IllegalArgumentException` is unchecked.

Why?

Because caller passed invalid input.

This is a programming or validation issue.

---

# Custom Checked Exception

We create a checked exception by extending `Exception`.

```java
class InvalidFileException extends Exception {

    public InvalidFileException(String message) {
        super(message);
    }
}
```

Usage:

```java
public void processFile(String fileName) throws InvalidFileException {
    if (fileName == null || fileName.isBlank()) {
        throw new InvalidFileException("File name is required");
    }
}
```

Caller must handle it.

---

# Custom Unchecked Exception

We create unchecked exception by extending `RuntimeException`.

```java
class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
```

Usage:

```java
public User getUser(Long id) {
    return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
}
```

Caller is not forced by compiler to catch it.

This is very common in Spring Boot applications.

---

# In Spring Boot, Which One Is Common?

In Spring Boot backend applications, we usually use unchecked custom exceptions.

Example:

```text
UserNotFoundException
BadRequestException
ConflictException
BusinessException
AccessDeniedException
```

These often extend `RuntimeException`.

Why?

Because we handle them centrally using `@RestControllerAdvice`.

Example:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
```

This keeps service methods cleaner.

---

# try-catch vs throws

## try-catch

Use `try-catch` when you want to handle the exception there itself.

```java
try {
    fileService.readFile();
} catch (IOException e) {
    System.out.println("File could not be read");
}
```

## throws

Use `throws` when current method does not want to handle it and wants caller to handle it.

```java
public void readFile() throws IOException {
    FileReader reader = new FileReader("abc.txt");
}
```

---

# Important Point About RuntimeException

Unchecked exceptions can still be caught.

Example:

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
}
```

But compiler does not force us to catch them.

That is the difference.

---

# Best Practices

```text
Use checked exception for recoverable external problems.
Use unchecked exception for programming errors or business validation failures.
Do not catch Exception blindly everywhere.
Do not swallow exceptions silently.
Use meaningful custom exceptions.
Handle exceptions centrally in Spring Boot.
Log important exceptions with context.
Do not expose stack trace to API clients.
```

---

# Common Mistakes

## Mistake 1: Thinking Unchecked Exceptions Cannot Be Handled

Wrong.

They can be handled.

Compiler just does not force us.

---

## Mistake 2: Catching Exception Everywhere

Bad:

```java
try {
    // code
} catch (Exception e) {
    // ignore
}
```

This hides real bugs.

Always handle exceptions properly.

---

## Mistake 3: Returning null Instead Of Throwing Proper Exception

Bad:

```java
public User getUser(Long id) {
    return null;
}
```

Better:

```java
public User getUser(Long id) {
    throw new UserNotFoundException("User not found");
}
```

This makes failure clear.

---

## Mistake 4: Exposing Stack Trace In API Response

Never return stack trace to client.

Bad:

```json
{
  "message": "NullPointerException at UserService.java:34"
}
```

Better:

```json
{
  "errorCode": "INTERNAL_SERVER_ERROR",
  "message": "Something went wrong"
}
```

---

# Interview-Ready Paragraph Answer

Checked exceptions are exceptions that are checked by the compiler. If a method can throw a checked exception, we must either handle it using try-catch or declare it using `throws`. Examples are `IOException`, `SQLException`, and `FileNotFoundException`. These usually happen because of external conditions like file issues, database issues, or network problems. Unchecked exceptions are not checked by the compiler. They are subclasses of `RuntimeException`, like `NullPointerException`, `IllegalArgumentException`, `ArithmeticException`, and `ArrayIndexOutOfBoundsException`. The compiler does not force us to handle them. They usually happen because of programming mistakes or invalid input. In Spring Boot applications, we often create custom unchecked exceptions and handle them centrally using `@RestControllerAdvice`. In simple words, checked exceptions are compile-time checked and must be handled, while unchecked exceptions happen at runtime and are not forced by the compiler.

---

# 14. final, finally, finalize

---
## Summary

`final`, `finally`, and `finalize` look similar, but they are completely different.

```text
final    -> keyword
finally  -> block
finalize -> method
```

## One-Line Answer

**`final` is used to restrict changes, `finally` is used to execute cleanup code, and `finalize()` was used before garbage collection but is now deprecated and should not be used.**

---

# 1. What Is final?

`final` is a keyword in Java.

It can be used with:

```text
Variable
Method
Class
```

The meaning changes depending on where we use it.

---

# final Variable

If a variable is `final`, we cannot reassign it.

Example:

```java
public class Main {
    public static void main(String[] args) {

        final int age = 25;

        // age = 30; // Compile-time error

        System.out.println(age);
    }
}
```

Output:

```text
25
```

Here, `age` cannot be changed after assignment.

---

# final Reference Variable

This is an important interview point.

If we create a final object reference, we cannot point it to a new object.

But we can still change the internal data of the object if the object is mutable.

Example:

```java
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        final List<String> names = new ArrayList<>();

        names.add("Ravi");
        names.add("Amit");

        // names = new ArrayList<>(); // Compile-time error

        System.out.println(names);
    }
}
```

Output:

```text
[Ravi, Amit]
```

Here, `names` is final.

So we cannot assign a new list to it.

But we can add elements to the same list.

So remember:

```text
final reference means reference cannot change.
It does not always mean object is immutable.
```

---

# final Method

If a method is `final`, child classes cannot override it.

Example:

```java
class Parent {

    public final void show() {
        System.out.println("Parent show");
    }
}

class Child extends Parent {

    // Not allowed
    // public void show() {
    //     System.out.println("Child show");
    // }
}
```

Why use final method?

```text
To stop child classes from changing important behavior.
```

---

# final Class

If a class is `final`, no class can extend it.

Example:

```java
final class PaymentUtil {

    public void validatePayment() {
        System.out.println("Payment validated");
    }
}

// Not allowed
// class MyPaymentUtil extends PaymentUtil {
// }
```

A very common example is `String`.

```java
public final class String
```

`String` is final, so nobody can extend it.

This helps make it secure and immutable.

---

# 2. What Is finally?

`finally` is a block used with `try-catch`.

Code inside `finally` usually runs whether exception happens or not.

It is mainly used for cleanup.

Example:

```java
public class Main {
    public static void main(String[] args) {

        try {
            int result = 10 / 2;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Exception happened");
        } finally {
            System.out.println("Finally block executed");
        }
    }
}
```

Output:

```text
5
Finally block executed
```

Now with exception:

```java
public class Main {
    public static void main(String[] args) {

        try {
            int result = 10 / 0;
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
        } finally {
            System.out.println("Finally block executed");
        }
    }
}
```

Output:

```text
Cannot divide by zero
Finally block executed
```

So `finally` runs in both cases.

---

# Why finally Is Used

`finally` is mostly used for cleanup work.

Example:

```text
Close file
Close database connection
Release lock
Close network connection
Clean temporary resource
```

Example:

```java
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        FileReader reader = null;

        try {
            reader = new FileReader("test.txt");
            System.out.println("File opened");
        } catch (IOException e) {
            System.out.println("File error");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    System.out.println("File closed");
                }
            } catch (IOException e) {
                System.out.println("Error while closing file");
            }
        }
    }
}
```

In modern Java, we often use `try-with-resources` instead of manually closing resources in `finally`.

Example:

```java
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        try (FileReader reader = new FileReader("test.txt")) {
            System.out.println("File opened");
        } catch (IOException e) {
            System.out.println("File error");
        }
    }
}
```

This automatically closes the resource.

---

# Does finally Always Execute?

Usually yes.

But there are some cases where `finally` may not execute.

Examples:

```text
JVM shuts down
System.exit() is called
Machine crashes
Process is killed
Infinite loop before finally
```

Example:

```java
public class Main {
    public static void main(String[] args) {

        try {
            System.out.println("Inside try");
            System.exit(0);
        } finally {
            System.out.println("Inside finally");
        }
    }
}
```

Output:

```text
Inside try
```

`finally` does not execute because `System.exit(0)` shuts down the JVM.

---

# finally With return

This is a common interview trap.

Example:

```java
public class Main {

    public static int test() {
        try {
            return 10;
        } finally {
            System.out.println("finally executed");
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

Output:

```text
finally executed
10
```

Even if `try` has return, `finally` runs before the method actually returns.

But avoid returning from `finally`.

Bad example:

```java
public class Main {

    public static int test() {
        try {
            return 10;
        } finally {
            return 20;
        }
    }

    public static void main(String[] args) {
        System.out.println(test());
    }
}
```

Output:

```text
20
```

This is bad practice because `finally` overrides the return value from `try`.

---

# 3. What Is finalize()?

`finalize()` is a method from the `Object` class.

Earlier, it was called by the garbage collector before destroying an object.

Example:

```java
class Demo {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize method called");
    }
}
```

But this is old practice.

Important point:

```text
finalize() is deprecated and should not be used.
```

Why?

Because we cannot depend on when garbage collector will run.

It may run late.
It may not run before program ends.
It can cause performance and cleanup issues.

So in modern Java, we should not use `finalize()` for cleanup.

---

# Why finalize() Is Not Recommended

`finalize()` has many problems:

```text
It is not guaranteed to run quickly.
It is not guaranteed to run before program exits.
It depends on garbage collector.
It can delay object cleanup.
It can cause performance problems.
It makes resource management unreliable.
```

So do not use `finalize()` for important cleanup like:

```text
Closing files
Closing DB connections
Releasing locks
Closing sockets
```

Use `try-with-resources` instead.

---

# Main Difference

| Point        | final                                      | finally                 | finalize                          |
| ------------ | ------------------------------------------ | ----------------------- | --------------------------------- |
| Type         | Keyword                                    | Block                   | Method                            |
| Used for     | Restriction                                | Cleanup after try-catch | Cleanup before garbage collection |
| Used with    | Variable, method, class                    | try-catch               | Object class                      |
| Main purpose | Stop reassignment, overriding, inheritance | Execute cleanup code    | Old GC cleanup hook               |
| Current use  | Very common                                | Common                  | Deprecated, avoid                 |
| Example      | `final int x = 10`                         | `finally {}`            | `finalize()`                      |

---

# Simple Examples Together

```java
final class Demo {

    final int value = 10;

    final void show() {
        System.out.println("Final method");
    }
}
```

Here:

```text
final class cannot be extended.
final method cannot be overridden.
final variable cannot be reassigned.
```

Now `finally`:

```java
try {
    System.out.println("Inside try");
} finally {
    System.out.println("Cleanup code");
}
```

Here:

```text
finally block runs after try.
```

Now `finalize()`:

```java
@Override
protected void finalize() throws Throwable {
    System.out.println("Called before GC");
}
```

Here:

```text
Old garbage collection cleanup method.
Avoid using it now.
```

---

# Common Interview Mistakes

## Mistake 1: Confusing final And finally

`final` is a keyword.

`finally` is a block.

They are not related.

---

## Mistake 2: Saying final Object Cannot Be Changed

Not always true.

Example:

```java
final List<String> list = new ArrayList<>();
list.add("Ravi");
```

This is allowed.

Final means the reference cannot be reassigned.

It does not make the object immutable.

---

## Mistake 3: Saying finally Always Executes 100%

Usually it executes.

But it may not execute in cases like `System.exit()`, JVM crash, or process kill.

---

## Mistake 4: Using return Inside finally

Avoid this.

It can override return value or hide exceptions.

---

## Mistake 5: Using finalize() For Cleanup

Do not use `finalize()` for important cleanup.

Use `try-with-resources` or explicit close methods.

---

# Best Practical Rule

Remember this:

```text
final is for restriction.
finally is for cleanup.
finalize is old GC-related method and should be avoided.
```

---

# Interview-Ready Paragraph Answer

`final`, `finally`, and `finalize()` are completely different in Java. `final` is a keyword. If we use it with a variable, the variable cannot be reassigned. If we use it with a method, the method cannot be overridden. If we use it with a class, the class cannot be extended. `finally` is a block used with `try-catch`, and it is mainly used for cleanup code like closing resources. It usually executes whether an exception occurs or not, but it may not execute if the JVM shuts down, like with `System.exit()`. `finalize()` is a method from the `Object` class that was earlier used before garbage collection, but it is deprecated and should not be used now because its execution is not reliable. In modern Java, we should use `try-with-resources` or explicit cleanup instead of `finalize()`.

---

# 15. Java memory model basics

---
## Summary

Java Memory Model is about **how threads see and share data in memory**.

It explains important things like:

```text
Visibility
Atomicity
Ordering
volatile
synchronized
happens-before
```

## One-Line Answer

**Java Memory Model defines how variables are stored, read, written, and shared between multiple threads in Java.**

---

# Simple Meaning

In Java, every thread can have its own working memory.

Main memory is shared by all threads.

Simple view:

```text
Main Memory
   |
   |-- Thread 1 working memory
   |-- Thread 2 working memory
```

When a thread reads a variable, it may copy the value from main memory into its own working memory.

When it updates the value, the update may not be immediately visible to other threads.

This is why multithreading can become tricky.

---

# Why Java Memory Model Is Needed

Suppose we have this variable:

```java
boolean running = true;
```

One thread is running a loop:

```java
while (running) {
    // do work
}
```

Another thread changes it:

```java
running = false;
```

You may expect the first thread to stop.

But without proper visibility, the first thread may not see the latest value.

It may keep using the old cached value.

That is why Java Memory Model is important.

It defines rules for how changes become visible across threads.

---

# Main Problems In Multithreading

Java Memory Model mainly helps us understand these problems:

```text
Visibility problem
Atomicity problem
Ordering problem
```

---

# 1. Visibility Problem

Visibility means:

```text
If one thread changes a value, other threads should be able to see it.
```

Example problem:

```java
class Task {
    boolean running = true;

    public void run() {
        while (running) {
            // keep running
        }
    }

    public void stop() {
        running = false;
    }
}
```

Here, one thread may call `run()`.

Another thread may call `stop()`.

But the running thread may not immediately see `running = false`.

Why?

Because it may be reading the old value from its local cache.

---

# Fix Using volatile

```java
class Task {
    volatile boolean running = true;

    public void run() {
        while (running) {
            // keep running
        }
    }

    public void stop() {
        running = false;
    }
}
```

Now `volatile` makes sure the latest value is visible to other threads.

Simple meaning:

```text
volatile forces reads and writes to happen from main memory.
```

So when one thread updates `running`, other threads can see the updated value.

---

# 2. Atomicity Problem

Atomicity means:

```text
An operation should happen as one complete step.
```

Example:

```java
count++;
```

This looks like one operation.

But internally, it has multiple steps:

```text
1. Read count
2. Add 1
3. Write count back
```

Now suppose two threads do `count++` at the same time.

Both may read the same old value.

Example:

```text
count = 5

Thread 1 reads 5
Thread 2 reads 5

Thread 1 writes 6
Thread 2 writes 6
```

Expected result was `7`.

Actual result becomes `6`.

This is called a race condition.

---

# Fix Using synchronized

```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
```

`synchronized` makes sure only one thread can execute the method at a time.

So `count++` becomes safe.

---

# Fix Using AtomicInteger

Another good way:

```java
import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
```

`AtomicInteger` is useful for simple atomic counters.

---

# 3. Ordering Problem

Ordering means:

```text
The order in which code is written may not always be the order in which CPU executes it.
```

Compiler and CPU can reorder instructions for performance.

This is fine in single-threaded code.

But in multithreaded code, it can create unexpected results.

Example:

```java
int data = 0;
boolean ready = false;
```

Thread 1:

```java
data = 100;
ready = true;
```

Thread 2:

```java
if (ready) {
    System.out.println(data);
}
```

We expect output:

```text
100
```

But because of reordering or visibility issues, Thread 2 may see `ready = true` but still see old `data`.

Java Memory Model defines rules to avoid these issues using tools like:

```text
volatile
synchronized
locks
final fields
thread start/join rules
```

---

# volatile In Java Memory Model

`volatile` gives two main guarantees:

```text
Visibility
Ordering
```

If a variable is volatile:

```java
volatile boolean ready = false;
```

Then when one thread writes to it, other threads can see the latest value.

Also, Java avoids certain reorderings around volatile reads/writes.

---

# Important Point About volatile

`volatile` does not make compound operations atomic.

Example:

```java
volatile int count = 0;

count++;
```

This is still not thread-safe.

Why?

Because `count++` has read, add, and write steps.

`volatile` gives visibility, not full atomicity for compound operations.

For counters, use:

```text
synchronized
AtomicInteger
Lock
```

---

# synchronized In Java Memory Model

`synchronized` gives two guarantees:

```text
Mutual exclusion
Visibility
```

## Mutual Exclusion

Only one thread can enter synchronized block/method for the same lock.

Example:

```java
public synchronized void increment() {
    count++;
}
```

## Visibility

When one thread exits a synchronized block, changes are flushed to main memory.

When another thread enters synchronized block with the same lock, it sees those changes.

Simple line:

```text
synchronized protects both atomicity and visibility.
```

---

# Happens-Before Relationship

This is an important term in Java Memory Model.

Happens-before means:

```text
If action A happens-before action B, then B can see the effects of A.
```

Simple examples:

```text
Unlock happens-before next lock on the same object.
Volatile write happens-before volatile read of same variable.
Thread start happens-before code inside started thread.
Thread completion happens-before successful join.
```

You do not need to explain it in too much depth in every interview.

But you should know the basic meaning.

It is about visibility and ordering guarantees.

---

# Example: Thread start()

```java
int value = 10;

Thread thread = new Thread(() -> {
    System.out.println(value);
});

thread.start();
```

Anything done before `thread.start()` is visible to the new thread.

This is one happens-before rule.

---

# Example: Thread join()

```java
Thread thread = new Thread(() -> {
    // do some work
});

thread.start();
thread.join();
```

After `join()` completes, the main thread can see the changes done by the child thread.

---

# final Fields And Memory Model

`final` fields have special safety in Java.

Example:

```java
class User {
    private final int id;
    private final String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
```

If an object is properly constructed, other threads can safely see the correct values of final fields.

This is one reason immutable classes are safer in multithreaded code.

---

# Main Tools For Thread Safety

Java gives many ways to handle memory model issues:

```text
volatile
synchronized
Lock
Atomic classes
final fields
Concurrent collections
Thread-safe designs
```

Examples:

```java
volatile boolean flag;
```

```java
synchronized void method() {
}
```

```java
AtomicInteger counter = new AtomicInteger();
```

```java
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
```

---

# volatile vs synchronized

| Point       | volatile                   | synchronized               |
| ----------- | -------------------------- | -------------------------- |
| Visibility  | Yes                        | Yes                        |
| Atomicity   | No for compound operations | Yes                        |
| Locking     | No lock                    | Uses lock                  |
| Performance | Usually lighter            | Heavier than volatile      |
| Best for    | Flags, status variables    | Critical sections          |
| Example     | `volatile boolean running` | `synchronized increment()` |

---

# Simple Example: volatile Is Enough

Use volatile for simple flag.

```java
class Worker {
    private volatile boolean running = true;

    public void run() {
        while (running) {
            // work
        }
    }

    public void stop() {
        running = false;
    }
}
```

Here `volatile` is enough because we are only reading and writing a boolean flag.

---

# Simple Example: volatile Is Not Enough

```java
class Counter {
    private volatile int count = 0;

    public void increment() {
        count++;
    }
}
```

This is not fully thread-safe.

Why?

Because `count++` is not atomic.

Use `AtomicInteger` or `synchronized`.

Correct:

```java
import java.util.concurrent.atomic.AtomicInteger;

class Counter {
    private AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        count.incrementAndGet();
    }
}
```

---

# Common Interview Mistakes

## Mistake 1: Saying volatile Makes Everything Thread-Safe

Wrong.

`volatile` gives visibility.

It does not make `count++` atomic.

---

## Mistake 2: Ignoring Visibility

Many people only think about locking.

But in multithreading, visibility is equally important.

One thread may update a value, but another thread may not see it without proper memory rules.

---

## Mistake 3: Using HashMap In Multithreading

`HashMap` is not thread-safe.

Use:

```text
ConcurrentHashMap
Collections.synchronizedMap
proper locking
```

---

## Mistake 4: Sharing Mutable Objects Without Protection

If many threads share and modify the same object, we need proper synchronization or thread-safe design.

---

# Best Practical Rule

Use this simple rule:

```text
If multiple threads read and write shared data, protect it.
```

You can protect it using:

```text
volatile for simple visibility flags
synchronized or Lock for compound actions
Atomic classes for counters
Concurrent collections for shared maps/lists
Immutable objects where possible
```

---

# Interview-Ready Paragraph Answer

Java Memory Model defines how data is read, written, and shared between threads in Java. In a multithreaded program, each thread can have its own working memory, and shared variables are stored in main memory. Because of this, one thread’s update may not be immediately visible to another thread unless we use proper memory guarantees. The main problems are visibility, atomicity, and ordering. Visibility means one thread should see another thread’s changes. Atomicity means an operation should complete as one unit, like protecting `count++`. Ordering means instructions should be seen in the correct order across threads. Java provides tools like `volatile`, `synchronized`, locks, atomic classes, final fields, and concurrent collections to handle these problems. `volatile` is good for simple flags because it gives visibility, but it does not make compound operations atomic. `synchronized` gives both visibility and atomicity by using locking. In simple words, Java Memory Model is the rulebook that makes multithreaded communication predictable and safe.

---

# 16. Thread lifecycle

---
## Summary

A Java thread goes through different states during its life.

Main thread states are:

```text
NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED
```

## One-Line Answer

**Thread lifecycle means the different states a thread goes through from creation to completion.**

---

# What Is A Thread?

A thread is a small unit of execution inside a program.

In Java, we can create a thread to run some work in parallel.

Example:

```java
Thread thread = new Thread(() -> {
    System.out.println("Thread is running");
});
```

But just creating a thread does not mean it starts running.

To start it, we call:

```java
thread.start();
```

---

# Thread Lifecycle States

Java thread has these main states:

```text
1. NEW
2. RUNNABLE
3. BLOCKED
4. WAITING
5. TIMED_WAITING
6. TERMINATED
```

These states are available in:

```java
Thread.State
```

---

# 1. NEW State

A thread is in `NEW` state when we create the thread object, but do not start it yet.

Example:

```java
Thread thread = new Thread(() -> {
    System.out.println("Running");
});
```

At this point, thread is created.

But it has not started.

State:

```text
NEW
```

Example:

```java
public class Main {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("Thread running");
        });

        System.out.println(thread.getState());
    }
}
```

Output:

```text
NEW
```

---

# 2. RUNNABLE State

A thread enters `RUNNABLE` state after calling `start()`.

Example:

```java
thread.start();
```

Important point:

`RUNNABLE` does not always mean the thread is currently running on CPU.

It means the thread is ready to run or running.

The actual CPU scheduling is decided by the JVM and operating system.

Example:

```java
public class Main {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("Thread running");
        });

        thread.start();

        System.out.println(thread.getState());
    }
}
```

Possible state:

```text
RUNNABLE
```

But sometimes it may finish very fast and show `TERMINATED`.

That depends on timing.

---

# 3. BLOCKED State

A thread goes into `BLOCKED` state when it is waiting to get a lock.

This usually happens with `synchronized`.

Example:

```java
class SharedResource {

    public synchronized void print() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Done");
    }
}
```

If Thread 1 enters `print()`, it gets the lock.

If Thread 2 tries to enter the same method at the same time, it has to wait.

Thread 2 state becomes:

```text
BLOCKED
```

Because it is waiting for the lock.

---

# BLOCKED Example

```java
class SharedResource {

    public synchronized void work() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {

        SharedResource resource = new SharedResource();

        Thread t1 = new Thread(resource::work);
        Thread t2 = new Thread(resource::work);

        t1.start();
        Thread.sleep(100);

        t2.start();
        Thread.sleep(100);

        System.out.println(t2.getState());
    }
}
```

Output can be:

```text
BLOCKED
```

Because `t1` has the lock and `t2` is waiting for it.

---

# 4. WAITING State

A thread goes into `WAITING` state when it waits indefinitely.

It will stay there until another thread wakes it up.

Common ways:

```text
Object.wait()
Thread.join()
LockSupport.park()
```

Example:

```java
thread.join();
```

If main thread calls `join()`, it waits until that thread finishes.

---

# WAITING Example

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        worker.start();

        worker.join();

        System.out.println("Worker completed");
    }
}
```

Here, the main thread waits for worker thread to complete.

During `join()`, main thread can go into waiting state.

---

# wait() Example

```java
class SharedData {

    public synchronized void waitForSignal() throws InterruptedException {
        wait();
        System.out.println("Signal received");
    }

    public synchronized void sendSignal() {
        notify();
    }
}
```

When a thread calls `wait()`, it releases the lock and waits.

Another thread can wake it using:

```java
notify();
```

or:

```java
notifyAll();
```

---

# 5. TIMED_WAITING State

A thread goes into `TIMED_WAITING` state when it waits for a fixed time.

Common ways:

```text
Thread.sleep(time)
Object.wait(time)
Thread.join(time)
LockSupport.parkNanos()
```

Example:

```java
Thread.sleep(2000);
```

This means:

```text
Pause this thread for 2 seconds.
```

During this time, the thread state is:

```text
TIMED_WAITING
```

---

# TIMED_WAITING Example

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        thread.start();

        Thread.sleep(100);

        System.out.println(thread.getState());
    }
}
```

Output:

```text
TIMED_WAITING
```

Because the thread is sleeping for 5 seconds.

---

# 6. TERMINATED State

A thread goes into `TERMINATED` state when its work is completed.

Example:

```java
Thread thread = new Thread(() -> {
    System.out.println("Work done");
});

thread.start();
```

After `run()` method finishes, thread becomes:

```text
TERMINATED
```

A terminated thread cannot be started again.

If we try to call `start()` again, Java throws:

```text
IllegalThreadStateException
```

---

# TERMINATED Example

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            System.out.println("Thread completed");
        });

        thread.start();
        thread.join();

        System.out.println(thread.getState());
    }
}
```

Output:

```text
Thread completed
TERMINATED
```

---

# Simple Lifecycle Flow

```text
NEW
 |
 | start()
 v
RUNNABLE
 |
 | waiting for lock
 v
BLOCKED
 |
 | wait(), join()
 v
WAITING
 |
 | sleep(), wait(time), join(time)
 v
TIMED_WAITING
 |
 | work completed
 v
TERMINATED
```

But a thread can move between these states many times before it finally terminates.

Example:

```text
RUNNABLE -> BLOCKED -> RUNNABLE -> TIMED_WAITING -> RUNNABLE -> TERMINATED
```

---

# start() vs run()

This is a very common interview point.

## start()

`start()` creates a new thread and then internally calls `run()`.

Example:

```java
thread.start();
```

This runs the code in a separate thread.

---

## run()

If we call `run()` directly, it behaves like a normal method call.

Example:

```java
thread.run();
```

This does not create a new thread.

It runs on the current thread.

---

# Example: start() vs run()

```java
public class Main {
    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });

        thread.run();
        thread.start();
    }
}
```

Output can be:

```text
main
Thread-0
```

Why?

```text
thread.run() runs on main thread.
thread.start() runs on new thread.
```

---

# sleep() vs wait()

This is another important interview point.

| Point                     | sleep()              | wait()                      |
| ------------------------- | -------------------- | --------------------------- |
| Belongs to                | `Thread` class       | `Object` class              |
| Releases lock?            | No                   | Yes                         |
| Needs synchronized block? | No                   | Yes                         |
| Wake-up                   | After time completes | notify/notifyAll or timeout |
| Use case                  | Pause thread         | Inter-thread communication  |

Example:

```java
Thread.sleep(1000);
```

This pauses the thread but does not release the lock.

Example:

```java
wait();
```

This releases the lock and waits until notification.

---

# join()

`join()` means one thread waits for another thread to finish.

Example:

```java
Thread worker = new Thread(() -> {
    System.out.println("Worker running");
});

worker.start();
worker.join();

System.out.println("Worker finished, main continues");
```

Here, main thread waits until worker thread completes.

---

# yield()

`yield()` is a hint to the scheduler.

Example:

```java
Thread.yield();
```

It means:

```text
Current thread is ready to pause and let another thread run.
```

But it is only a hint.

The scheduler may ignore it.

We rarely use it in normal backend code.

---

# Important Practical Points

## Thread Scheduling Is Not In Our Full Control

Java thread execution depends on JVM and OS scheduler.

So output order can vary.

Example:

```java
Thread t1 = new Thread(() -> System.out.println("T1"));
Thread t2 = new Thread(() -> System.out.println("T2"));

t1.start();
t2.start();
```

Output can be:

```text
T1
T2
```

or:

```text
T2
T1
```

Do not depend on thread execution order unless you use proper synchronization.

---

# Common Thread States In Backend

In real applications:

```text
RUNNABLE        -> thread is doing work or ready
BLOCKED         -> waiting for synchronized lock
WAITING         -> waiting indefinitely
TIMED_WAITING   -> sleeping, waiting with timeout
TERMINATED      -> completed
```

If many threads are `BLOCKED`, it may mean lock contention.

If many threads are `TIMED_WAITING`, it may mean threads are sleeping or waiting for I/O/timeouts.

Thread dumps are useful to debug this.

---

# Thread Lifecycle Example With States

```java
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.println(thread.getState());

        thread.start();

        Thread.sleep(100);
        System.out.println(thread.getState());

        thread.join();

        System.out.println(thread.getState());
    }
}
```

Possible output:

```text
NEW
TIMED_WAITING
TERMINATED
```

Why?

Before `start()`:

```text
NEW
```

During `sleep()`:

```text
TIMED_WAITING
```

After completion:

```text
TERMINATED
```

---

# Can We Restart A Thread?

No.

Once a thread is terminated, we cannot start it again.

Example:

```java
Thread thread = new Thread(() -> {
    System.out.println("Running");
});

thread.start();
thread.start();
```

This throws:

```text
IllegalThreadStateException
```

If we need to run the task again, create a new thread.

Better in real backend systems:

```text
Use ExecutorService instead of manually creating many threads.
```

---

# Common Interview Mistakes

## Mistake 1: Saying RUNNABLE Means Always Running

Not exactly.

`RUNNABLE` means ready to run or running.

The OS scheduler decides when it actually runs.

---

## Mistake 2: Calling run() Instead Of start()

`run()` does not create a new thread.

Use `start()` to create a new thread.

---

## Mistake 3: Thinking sleep() Releases Lock

Wrong.

`sleep()` does not release lock.

`wait()` releases lock.

---

## Mistake 4: Restarting Same Thread

A terminated thread cannot be started again.

Create a new thread or use `ExecutorService`.

---

## Mistake 5: Ignoring InterruptedException

Bad:

```java
catch (InterruptedException e) {
}
```

Better:

```java
catch (InterruptedException e) {
    Thread.currentThread().interrupt();
}
```

This restores the interrupted status.

---

# Best Practical Rule

Remember this simple flow:

```text
Create thread        -> NEW
Call start()         -> RUNNABLE
Waiting for lock     -> BLOCKED
Waiting forever      -> WAITING
Waiting with timeout -> TIMED_WAITING
Work completed       -> TERMINATED
```

---

# Interview-Ready Paragraph Answer

A Java thread goes through different states during its lifecycle. When we create a thread object but do not start it, it is in the `NEW` state. When we call `start()`, it moves to `RUNNABLE`, which means it is ready to run or currently running, depending on the scheduler. If the thread is waiting to acquire a synchronized lock, it goes into `BLOCKED` state. If it is waiting indefinitely using methods like `wait()` or `join()`, it goes into `WAITING` state. If it is waiting for a fixed time using `sleep()`, `wait(timeout)`, or `join(timeout)`, it goes into `TIMED_WAITING` state. Once the `run()` method completes, the thread enters `TERMINATED` state. A terminated thread cannot be started again. Also, calling `run()` directly does not create a new thread, but calling `start()` does.

---

# 17. synchronized vs ReentrantLock

---
## Summary

`synchronized` and `ReentrantLock` are both used for thread safety in Java.

Both help when multiple threads access shared data.

Main idea:

```text
synchronized  -> simple built-in locking
ReentrantLock -> advanced locking with more control
```

## One-Line Answer

**Use `synchronized` for simple locking, and use `ReentrantLock` when you need advanced features like try-lock, timeout, interruptible lock, or fair locking.**

---

# Why Locking Is Needed

Suppose two threads update the same counter.

```java
count++;
```

This looks like one step.

But internally it has three steps:

```text
1. Read count
2. Add 1
3. Write count back
```

If two threads do this at the same time, data can become wrong.

So we use locking.

Locking makes sure only one thread enters the critical section at a time.

---

# What Is synchronized?

`synchronized` is a Java keyword.

It can be used on:

```text
Method
Block
Static method
```

Example:

```java
class Counter {

    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}
```

Here, only one thread can execute `increment()` on the same object at a time.

---

# synchronized Block Example

Instead of locking the full method, we can lock only important code.

```java
class Counter {

    private int count = 0;

    public void increment() {
        synchronized (this) {
            count++;
        }
    }

    public int getCount() {
        synchronized (this) {
            return count;
        }
    }
}
```

This gives better control than synchronizing the whole method.

---

# What Is ReentrantLock?

`ReentrantLock` is a class from `java.util.concurrent.locks`.

Example:

```java
import java.util.concurrent.locks.ReentrantLock;

class Counter {

    private int count = 0;
    private final ReentrantLock lock = new ReentrantLock();

    public void increment() {
        lock.lock();

        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public int getCount() {
        lock.lock();

        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
```

Important point:

```text
When using ReentrantLock, always unlock inside finally block.
```

Why?

Because if an exception happens, the lock should still be released.

---

# Main Difference

| Point               | synchronized         | ReentrantLock                |
| ------------------- | -------------------- | ---------------------------- |
| Type                | Java keyword         | Java class                   |
| Package             | Built-in keyword     | `java.util.concurrent.locks` |
| Lock release        | Automatic            | Manual using `unlock()`      |
| tryLock support     | No                   | Yes                          |
| Timeout lock        | No                   | Yes                          |
| Interruptible lock  | No                   | Yes                          |
| Fairness option     | No direct option     | Yes                          |
| Condition variables | One wait set         | Multiple `Condition`s        |
| Simplicity          | Very simple          | More flexible but more code  |
| Best use            | Simple thread safety | Advanced locking needs       |

---

# Why It Is Called ReentrantLock

Both `synchronized` and `ReentrantLock` are reentrant.

Reentrant means:

```text
A thread that already holds a lock can acquire the same lock again.
```

Example with `synchronized`:

```java
class Demo {

    public synchronized void method1() {
        method2();
    }

    public synchronized void method2() {
        System.out.println("Inside method2");
    }
}
```

Here, the same thread enters `method1()` and then calls `method2()`.

It already has the lock, but it can enter again.

That is reentrancy.

---

# ReentrantLock Example

```java
import java.util.concurrent.locks.ReentrantLock;

class Demo {

    private final ReentrantLock lock = new ReentrantLock();

    public void method1() {
        lock.lock();

        try {
            method2();
        } finally {
            lock.unlock();
        }
    }

    public void method2() {
        lock.lock();

        try {
            System.out.println("Inside method2");
        } finally {
            lock.unlock();
        }
    }
}
```

The same thread can lock again.

But it must unlock the same number of times.

---

# Important Point About unlock()

With `ReentrantLock`, this is very important:

```text
Every lock() should have a matching unlock().
```

Bad:

```java
lock.lock();

count++;

// forgot unlock
```

This can create a deadlock-like situation.

Other threads may wait forever.

Correct:

```java
lock.lock();

try {
    count++;
} finally {
    lock.unlock();
}
```

---

# Feature 1: tryLock()

This is a big advantage of `ReentrantLock`.

With `tryLock()`, a thread tries to acquire lock.

If lock is not available, it does not wait forever.

Example:

```java
import java.util.concurrent.locks.ReentrantLock;

class PaymentService {

    private final ReentrantLock lock = new ReentrantLock();

    public void processPayment() {
        if (lock.tryLock()) {
            try {
                System.out.println("Processing payment");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not get lock. Try later.");
        }
    }
}
```

This is not possible directly with `synchronized`.

---

# Feature 2: tryLock With Timeout

We can wait for lock only for a fixed time.

Example:

```java
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class ReportService {

    private final ReentrantLock lock = new ReentrantLock();

    public void generateReport() throws InterruptedException {
        boolean locked = lock.tryLock(2, TimeUnit.SECONDS);

        if (locked) {
            try {
                System.out.println("Report generated");
            } finally {
                lock.unlock();
            }
        } else {
            System.out.println("Could not get lock within 2 seconds");
        }
    }
}
```

This is useful when we do not want a thread to wait forever.

---

# Feature 3: lockInterruptibly()

With `ReentrantLock`, a waiting thread can be interrupted.

Example:

```java
lock.lockInterruptibly();

try {
    // critical section
} finally {
    lock.unlock();
}
```

This is useful when we want to cancel a waiting thread.

With `synchronized`, if a thread is waiting to enter synchronized block, it cannot be interrupted in the same flexible way.

---

# Feature 4: Fair Lock

By default, locks may not be fair.

That means any waiting thread can get the lock next.

With `ReentrantLock`, we can create a fair lock.

```java
private final ReentrantLock lock = new ReentrantLock(true);
```

Here, `true` means fair lock.

Simple meaning:

```text
Thread that waited longer gets chance first.
```

This can reduce starvation.

But fair locks can be slower.

So use fairness only when needed.

---

# Feature 5: Multiple Conditions

With `synchronized`, we use:

```text
wait()
notify()
notifyAll()
```

These work on one wait set per object.

With `ReentrantLock`, we can create multiple `Condition` objects.

Example:

```java
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class SharedQueue {

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();
}
```

This is useful for advanced producer-consumer problems.

It gives more control than `wait()` and `notify()`.

---

# synchronized Uses Automatic Lock Release

With `synchronized`, lock release is automatic.

Example:

```java
public synchronized void increment() {
    count++;
}
```

When method finishes, lock is released automatically.

Even if exception happens, Java releases the lock.

This makes `synchronized` safer and simpler.

---

# ReentrantLock Needs Manual Lock Release

With `ReentrantLock`, lock release is manual.

So we must write:

```java
lock.lock();

try {
    // work
} finally {
    lock.unlock();
}
```

If we forget `unlock()`, other threads may get stuck.

So `ReentrantLock` gives more control, but also more responsibility.

---

# Visibility Guarantee

Both `synchronized` and `ReentrantLock` provide visibility.

It means:

```text
When one thread updates shared data inside the lock,
another thread will see the updated value after acquiring the same lock.
```

So both help with:

```text
Atomicity
Visibility
Thread safety
```

---

# When To Use synchronized

Use `synchronized` when locking requirement is simple.

Good examples:

```text
Simple counter update
Small critical section
Simple shared object protection
No timeout needed
No try-lock needed
No fairness needed
```

Example:

```java
class Counter {

    private int count;

    public synchronized void increment() {
        count++;
    }
}
```

This is clean and enough for many cases.

---

# When To Use ReentrantLock

Use `ReentrantLock` when you need advanced control.

Good examples:

```text
Need tryLock()
Need timeout while waiting for lock
Need interruptible lock
Need fair locking
Need multiple Condition objects
Need more complex lock control
```

Example:

```java
if (lock.tryLock()) {
    try {
        // do work
    } finally {
        lock.unlock();
    }
}
```

---

# Practical Backend Example

Suppose only one thread should process a job at a time.

With `synchronized`:

```java
class JobService {

    public synchronized void processJob() {
        System.out.println("Processing job");
    }
}
```

This is simple.

But suppose if lock is not available, we want to skip the job instead of waiting.

Then `ReentrantLock` is better.

```java
import java.util.concurrent.locks.ReentrantLock;

class JobService {

    private final ReentrantLock lock = new ReentrantLock();

    public void processJob() {
        if (!lock.tryLock()) {
            System.out.println("Job already running. Skipping.");
            return;
        }

        try {
            System.out.println("Processing job");
        } finally {
            lock.unlock();
        }
    }
}
```

This kind of control is not easy with `synchronized`.

---

# Common Mistakes

## Mistake 1: Forgetting unlock()

This is the biggest mistake with `ReentrantLock`.

Bad:

```java
lock.lock();
doWork();
lock.unlock();
```

If `doWork()` throws exception, `unlock()` may not run.

Correct:

```java
lock.lock();

try {
    doWork();
} finally {
    lock.unlock();
}
```

---

## Mistake 2: Thinking synchronized Is Bad

`synchronized` is not bad.

It is simple and good for many normal cases.

Use `ReentrantLock` only when you need extra features.

---

## Mistake 3: Locking Too Much Code

Bad:

```java
synchronized void process() {
    callExternalApi();
    updateCounter();
    sendEmail();
}
```

This keeps lock for too long.

Better:

```text
Lock only the shared data part.
```

Keep critical section small.

---

## Mistake 4: Using Different Locks For Same Shared Data

If two methods protect the same shared data, they should use the same lock.

Bad:

```java
private final Object lock1 = new Object();
private final Object lock2 = new Object();
```

If both protect the same variable, this can break thread safety.

---

## Mistake 5: Using Lock Without Need

If there is no shared mutable data, you may not need locking.

Example:

```text
Local variables inside method usually do not need locking.
Immutable objects usually do not need locking.
```

---

# synchronized vs ReentrantLock In Simple Words

```text
synchronized:
Easy to use
Less code
Lock release is automatic
Good for simple cases

ReentrantLock:
More powerful
More control
tryLock and timeout available
Manual unlock needed
Good for complex cases
```

---

# Interview-Ready Paragraph Answer

`synchronized` and `ReentrantLock` are both used to make code thread-safe when multiple threads access shared data. `synchronized` is a Java keyword and is simple to use. We can use it on a method or block, and Java automatically releases the lock when the block or method finishes. It is good for simple locking cases. `ReentrantLock` is a class from `java.util.concurrent.locks` and gives more control. It supports features like `tryLock()`, timeout while waiting for a lock, interruptible locking, fair locking, and multiple `Condition` objects. But with `ReentrantLock`, we must release the lock manually using `unlock()`, usually inside a `finally` block. If we forget to unlock, other threads may wait forever. So in simple words, I use `synchronized` for simple thread safety, and I use `ReentrantLock` when I need advanced locking control.

---

# 18. ExecutorService

---
## Summary

`ExecutorService` is used to manage threads in Java.

Instead of creating threads manually again and again, we give tasks to an executor.

ExecutorService manages:

```text
Thread creation
Thread reuse
Task execution
Task queue
Shutdown
Result handling
```

## One-Line Answer

**ExecutorService is a Java framework used to execute tasks asynchronously using a managed pool of threads.**

---

# Why ExecutorService Is Needed

Without `ExecutorService`, we may create threads manually.

Example:

```java
Thread thread = new Thread(() -> {
    System.out.println("Task running");
});

thread.start();
```

This is okay for one or two threads.

But in real backend systems, we may have many tasks.

Example:

```text
Send email
Process file
Call external API
Generate report
Process payment callback
Run background job
```

Creating a new thread for every task is not good.

Why?

```text
Thread creation is costly
Too many threads can slow the system
Thread management becomes hard
No proper queue management
No easy way to get result
No clean shutdown
```

So Java provides `ExecutorService`.

---

# What Is ExecutorService?

`ExecutorService` is an interface from:

```java
java.util.concurrent
```

It helps us submit tasks and run them using a thread pool.

Simple example:

```java
ExecutorService executorService = Executors.newFixedThreadPool(3);

executorService.submit(() -> {
    System.out.println("Task executed by " + Thread.currentThread().getName());
});

executorService.shutdown();
```

Here, we are not creating thread manually.

We are submitting task to the executor.

The executor decides which thread will run the task.

---

# What Is Thread Pool?

Thread pool means a group of already-created threads.

Example:

```text
Thread pool size = 3

Thread-1
Thread-2
Thread-3
```

If 10 tasks come, only 3 threads run at a time.

Remaining tasks wait in queue.

```text
3 tasks running
7 tasks waiting
```

When one thread finishes a task, it picks the next task from the queue.

This is thread reuse.

---

# Simple Flow

```text
1. Create ExecutorService
2. Submit task
3. Task goes to queue
4. Worker thread picks task
5. Task executes
6. Thread returns to pool
7. Executor can take more tasks
8. Shutdown executor when done
```

---

# Basic Example

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(() -> {
            System.out.println("Task 1 running by " + Thread.currentThread().getName());
        });

        executorService.submit(() -> {
            System.out.println("Task 2 running by " + Thread.currentThread().getName());
        });

        executorService.shutdown();
    }
}
```

Possible output:

```text
Task 1 running by pool-1-thread-1
Task 2 running by pool-1-thread-2
```

---

# Runnable vs Callable

ExecutorService can run both:

```text
Runnable
Callable
```

---

## Runnable

`Runnable` does not return a result.

```java
Runnable task = () -> {
    System.out.println("Task running");
};
```

Submit it:

```java
executorService.submit(task);
```

Use `Runnable` when you only want to run some work.

Example:

```text
Send email
Write log
Publish event
Clean temp file
```

---

## Callable

`Callable` returns a result and can throw checked exception.

```java
Callable<Integer> task = () -> {
    return 10 + 20;
};
```

Submit it:

```java
Future<Integer> future = executorService.submit(task);
```

Get result:

```java
Integer result = future.get();
```

Full example:

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<Integer> task = () -> {
            return 10 + 20;
        };

        Future<Integer> future = executorService.submit(task);

        System.out.println(future.get());

        executorService.shutdown();
    }
}
```

Output:

```text
30
```

---

# What Is Future?

`Future` represents the result of an async task.

When we submit a `Callable`, we get a `Future`.

```java
Future<Integer> future = executorService.submit(task);
```

Using `Future`, we can:

```text
Check if task is done
Get task result
Cancel task
Handle exception from task
```

Common methods:

```java
future.get();
future.isDone();
future.cancel(true);
future.isCancelled();
```

Important point:

```java
future.get();
```

This is blocking.

It waits until the task completes.

---

# Example With Future

```java
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<String> future = executorService.submit(() -> {
            Thread.sleep(1000);
            return "Task completed";
        });

        System.out.println("Doing other work");

        String result = future.get();

        System.out.println(result);

        executorService.shutdown();
    }
}
```

Output:

```text
Doing other work
Task completed
```

The main thread can do other work before calling `get()`.

---

# Common Types Of Executors

Java provides factory methods in `Executors`.

Common ones are:

```text
newFixedThreadPool
newSingleThreadExecutor
newCachedThreadPool
newScheduledThreadPool
```

---

# 1. newFixedThreadPool

Creates a fixed number of threads.

```java
ExecutorService executor = Executors.newFixedThreadPool(5);
```

Meaning:

```text
Maximum 5 tasks run at the same time.
Extra tasks wait in queue.
```

Good for controlled concurrency.

Example use:

```text
Process 5 files in parallel
Call 5 APIs in parallel
Run limited background workers
```

---

# 2. newSingleThreadExecutor

Creates only one worker thread.

```java
ExecutorService executor = Executors.newSingleThreadExecutor();
```

Meaning:

```text
Tasks run one by one in order.
```

Good when order matters.

Example:

```text
Process audit events one by one
Write logs in sequence
Run sequential background tasks
```

---

# 3. newCachedThreadPool

Creates threads as needed and reuses idle threads.

```java
ExecutorService executor = Executors.newCachedThreadPool();
```

It can create many threads if many tasks come.

So use carefully.

Risk:

```text
Too many tasks can create too many threads.
This can overload the system.
```

---

# 4. newScheduledThreadPool

Used to run tasks after delay or repeatedly.

```java
ScheduledExecutorService scheduler =
        Executors.newScheduledThreadPool(2);
```

Example:

```java
scheduler.schedule(() -> {
    System.out.println("Runs after 5 seconds");
}, 5, TimeUnit.SECONDS);
```

For repeated task:

```java
scheduler.scheduleAtFixedRate(() -> {
    System.out.println("Runs every 10 seconds");
}, 0, 10, TimeUnit.SECONDS);
```

Good for:

```text
Cleanup jobs
Retry jobs
Health check jobs
Periodic polling
```

---

# Important Warning About Executors Factory

In interviews, this is a good advanced point.

Factory methods like:

```java
Executors.newFixedThreadPool()
Executors.newCachedThreadPool()
```

are easy to use.

But in production, many teams prefer using `ThreadPoolExecutor` directly.

Why?

Because we can control:

```text
Core pool size
Max pool size
Queue size
Thread name
Rejection policy
Keep alive time
```

Some `Executors` methods use unbounded queues, which can cause memory problems if too many tasks come.

---

# ThreadPoolExecutor

`ThreadPoolExecutor` gives full control.

Example:

```java
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                          // core pool size
                5,                          // max pool size
                60,                         // keep alive time
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), // queue capacity
                new ThreadPoolExecutor.AbortPolicy()
        );

        executor.submit(() -> {
            System.out.println("Task running");
        });

        executor.shutdown();
    }
}
```

This is more production-friendly because queue size is controlled.

---

# Core Pool Size And Max Pool Size

In `ThreadPoolExecutor`:

```text
corePoolSize = normal number of threads
maximumPoolSize = maximum allowed threads
queue = waiting area for tasks
```

Simple flow:

```text
If running threads < corePoolSize
    create new thread

Else if queue is not full
    put task in queue

Else if running threads < maximumPoolSize
    create extra thread

Else
    reject task
```

---

# Rejection Policy

If all threads are busy and queue is full, new task is rejected.

Common rejection policies:

```text
AbortPolicy
CallerRunsPolicy
DiscardPolicy
DiscardOldestPolicy
```

## AbortPolicy

Throws `RejectedExecutionException`.

## CallerRunsPolicy

The thread that submitted the task runs it itself.

This can slow down the producer and reduce load.

## DiscardPolicy

Silently drops the new task.

Use carefully.

## DiscardOldestPolicy

Drops oldest queued task and tries to add new task.

Use carefully.

---

# shutdown() vs shutdownNow()

## shutdown()

```java
executorService.shutdown();
```

It stops accepting new tasks.

But already submitted tasks continue.

Simple meaning:

```text
No new tasks.
Finish existing tasks.
Then stop.
```

---

## shutdownNow()

```java
executorService.shutdownNow();
```

It tries to stop running tasks and returns pending tasks.

Simple meaning:

```text
Stop immediately if possible.
```

But it does not guarantee that running tasks will stop instantly.

Tasks should handle interruption properly.

---

# Why Shutdown Is Important

If we do not shut down ExecutorService, threads may keep running.

The application may not exit.

Bad:

```java
ExecutorService executor = Executors.newFixedThreadPool(2);
executor.submit(task);

// forgot shutdown
```

Good:

```java
executor.shutdown();
```

In Spring Boot, if the executor is managed as a bean, Spring can manage shutdown lifecycle.

---

# awaitTermination()

After shutdown, we can wait for tasks to finish.

```java
executor.shutdown();

try {
    if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
        executor.shutdownNow();
    }
} catch (InterruptedException e) {
    executor.shutdownNow();
    Thread.currentThread().interrupt();
}
```

This is a clean shutdown pattern.

---

# execute() vs submit()

ExecutorService has both `execute()` and `submit()`.

## execute()

```java
executor.execute(() -> {
    System.out.println("Task running");
});
```

It accepts `Runnable`.

It does not return result.

Exceptions are usually handled by thread’s uncaught exception handler.

---

## submit()

```java
Future<?> future = executor.submit(() -> {
    System.out.println("Task running");
});
```

It returns `Future`.

If task throws exception, exception is stored inside `Future`.

You see it when you call:

```java
future.get();
```

---

# Example: Exception With submit()

```java
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(1);

        Future<?> future = executor.submit(() -> {
            throw new RuntimeException("Something failed");
        });

        try {
            future.get();
        } catch (ExecutionException e) {
            System.out.println(e.getCause().getMessage());
        }

        executor.shutdown();
    }
}
```

Output:

```text
Something failed
```

Important point:

```text
With submit(), exception is wrapped inside ExecutionException.
```

---

# ExecutorService In Backend Example

Suppose we have an API where we need to call three independent services:

```text
Customer Service
Account Service
Offer Service
```

We can run them in parallel.

Simple idea:

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

Future<Customer> customerFuture = executor.submit(() -> customerClient.getCustomer(id));
Future<Account> accountFuture = executor.submit(() -> accountClient.getAccount(id));
Future<Offer> offerFuture = executor.submit(() -> offerClient.getOffer(id));

Customer customer = customerFuture.get();
Account account = accountFuture.get();
Offer offer = offerFuture.get();
```

This can reduce total response time.

But we must use timeout carefully.

---

# Future get With Timeout

Never wait forever for async result.

Use timeout:

```java
Customer customer = customerFuture.get(2, TimeUnit.SECONDS);
```

If task does not complete in 2 seconds, it throws `TimeoutException`.

This is useful in backend APIs.

---

# Common Use Cases

ExecutorService is useful for:

```text
Running background tasks
Parallel API calls
File processing
Report generation
Email sending
Batch jobs
Async notification
CPU-bound or IO-bound task execution
```

---

# Choosing Thread Pool Size

Thread pool size depends on the type of task.

## CPU-bound tasks

These tasks use CPU heavily.

Example:

```text
Calculations
Data processing
Image processing
Encryption
```

A common rule:

```text
Pool size near number of CPU cores
```

## IO-bound tasks

These tasks wait for external systems.

Example:

```text
Database call
HTTP call
File read
Network call
```

They can use a larger pool because threads spend time waiting.

But do not make it unlimited.

Always test and monitor.

---

# Common Mistakes

## Mistake 1: Creating Thread Manually For Every Request

Bad:

```java
new Thread(task).start();
```

This can create too many threads.

Use ExecutorService.

---

## Mistake 2: Not Shutting Down ExecutorService

If executor is not shut down, threads may stay alive.

Call:

```java
shutdown()
```

or let Spring manage it as a bean.

---

## Mistake 3: Using newCachedThreadPool Carelessly

It can create many threads.

This can overload the server.

Use bounded thread pools in production.

---

## Mistake 4: Using Unbounded Queue

Unbounded queue can grow too much if tasks come faster than they are processed.

This can cause memory issues.

Use bounded queue when needed.

---

## Mistake 5: Blocking Forever On future.get()

Bad:

```java
future.get();
```

If task hangs, caller waits forever.

Better:

```java
future.get(2, TimeUnit.SECONDS);
```

---

## Mistake 6: Ignoring Exceptions

With `submit()`, exceptions are inside `Future`.

If we never call `future.get()`, we may miss failures.

---

## Mistake 7: Too Large Thread Pool

More threads do not always mean better performance.

Too many threads cause:

```text
Context switching
High memory usage
CPU overhead
Slow performance
```

---

# Best Practices

```text
Use ExecutorService instead of creating threads manually
Use fixed or custom bounded thread pool in production
Prefer ThreadPoolExecutor for more control
Set proper queue size
Set proper rejection policy
Use meaningful thread names
Always shutdown executor if manually created
Use timeout with Future.get()
Handle exceptions properly
Do not block request threads unnecessarily
Choose pool size based on task type
Monitor active threads, queue size, and rejection count
```

---

# Small Production-Style Example

```java
import java.util.concurrent.*;

public class ReportExecutor {

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            4,
            8,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(100),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    public Future<String> generateReport(String reportId) {
        return executor.submit(() -> {
            // report generation logic
            return "Report generated: " + reportId;
        });
    }

    public void shutdown() {
        executor.shutdown();
    }
}
```

This is better than creating unlimited threads.

It has:

```text
Core pool size
Max pool size
Bounded queue
Rejection policy
Shutdown method
```

---

# Interview-Ready Paragraph Answer

`ExecutorService` is a Java concurrency framework used to execute tasks using a managed pool of threads. Instead of creating a new thread manually for every task, we submit tasks to an executor, and it reuses existing threads from the pool. This improves performance and gives better control over thread management. It can execute `Runnable` tasks that do not return a result and `Callable` tasks that return a result through `Future`. Common implementations are fixed thread pool, single thread executor, cached thread pool, and scheduled thread pool. In production, I prefer using `ThreadPoolExecutor` directly when I need control over core pool size, max pool size, queue size, timeout, and rejection policy. It is also important to shut down the executor properly, handle exceptions, and avoid waiting forever on `Future.get()` by using timeouts. In simple words, ExecutorService helps us run async or parallel tasks safely without manually managing threads.

---

# 19. Deadlock and how to avoid it

---
## Summary

**Deadlock** happens when two or more threads wait for each other forever.

No thread can move forward.

Simple idea:

```text
Thread 1 has Lock A and waits for Lock B
Thread 2 has Lock B and waits for Lock A
Both wait forever
```

## One-Line Answer

**Deadlock happens when threads hold some locks and wait for other locks in a circular way, so none of them can continue.**

---

# What Is Deadlock?

Deadlock is a situation where threads are stuck forever.

Each thread is waiting for a resource held by another thread.

Example:

```text
Thread 1 has lock on Account A.
Thread 1 wants lock on Account B.

Thread 2 has lock on Account B.
Thread 2 wants lock on Account A.
```

Now both are waiting.

```text
Thread 1 waits for Thread 2
Thread 2 waits for Thread 1
```

So the program gets stuck.

---

# Simple Real-Life Example

Imagine two people on a narrow road.

Person A says:

```text
You move first, then I will move.
```

Person B says:

```text
No, you move first, then I will move.
```

Both keep waiting.

Nobody moves.

That is like deadlock.

---

# Java Deadlock Example

```java
class DeadlockExample {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            synchronized (LOCK_A) {
                System.out.println("Thread 1 locked A");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (LOCK_B) {
                    System.out.println("Thread 1 locked B");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (LOCK_B) {
                System.out.println("Thread 2 locked B");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                synchronized (LOCK_A) {
                    System.out.println("Thread 2 locked A");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
```

Output can be:

```text
Thread 1 locked A
Thread 2 locked B
```

After that, program may hang.

Why?

Because:

```text
Thread 1 is waiting for LOCK_B.
Thread 2 is waiting for LOCK_A.
```

Both locks are already held by each other.

---

# Conditions For Deadlock

Deadlock usually happens when these four conditions are present.

```text
1. Mutual exclusion
2. Hold and wait
3. No preemption
4. Circular wait
```

---

# 1. Mutual Exclusion

Only one thread can hold a lock at a time.

Example:

```java
synchronized (lock) {
    // only one thread can enter
}
```

This is normal in locking.

---

# 2. Hold And Wait

A thread is holding one lock and waiting for another lock.

Example:

```text
Thread 1 holds Lock A.
Thread 1 waits for Lock B.
```

This creates risk.

---

# 3. No Preemption

A lock cannot be forcefully taken away from a thread.

The thread must release it itself.

Example:

```text
Thread 1 will release Lock A only after work is done.
```

---

# 4. Circular Wait

Threads wait in a circular chain.

Example:

```text
Thread 1 waits for Thread 2
Thread 2 waits for Thread 1
```

Or bigger chain:

```text
Thread 1 waits for Thread 2
Thread 2 waits for Thread 3
Thread 3 waits for Thread 1
```

This is the main deadlock pattern.

---

# How To Avoid Deadlock

## 1. Always Take Locks In Same Order

This is the most important solution.

Bad:

```text
Thread 1: Lock A then Lock B
Thread 2: Lock B then Lock A
```

Good:

```text
Thread 1: Lock A then Lock B
Thread 2: Lock A then Lock B
```

If all threads follow the same lock order, circular wait can be avoided.

---

# Fixed Example

```java
class DeadlockFixedExample {

    private static final Object LOCK_A = new Object();
    private static final Object LOCK_B = new Object();

    public static void main(String[] args) {

        Runnable task = () -> {
            synchronized (LOCK_A) {
                System.out.println(Thread.currentThread().getName() + " locked A");

                synchronized (LOCK_B) {
                    System.out.println(Thread.currentThread().getName() + " locked B");
                }
            }
        };

        Thread thread1 = new Thread(task, "Thread-1");
        Thread thread2 = new Thread(task, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
```

Now both threads take locks in the same order:

```text
LOCK_A first
LOCK_B second
```

So deadlock will not happen.

---

# 2. Keep Lock Scope Small

Do not keep lock for too long.

Bad:

```java
synchronized void processOrder() {
    validateOrder();
    callPaymentGateway();
    sendEmail();
    updateDatabase();
}
```

This is bad because external calls can be slow.

The lock is held for a long time.

Better:

```java
public void processOrder() {
    validateOrder();

    synchronized (this) {
        updateSharedData();
    }

    callPaymentGateway();
    sendEmail();
}
```

Lock only the shared data part.

This reduces deadlock risk and improves performance.

---

# 3. Avoid Nested Locks If Possible

Nested locks increase deadlock risk.

Example:

```java
synchronized (lockA) {
    synchronized (lockB) {
        // risky if other thread takes locks in reverse order
    }
}
```

If possible, avoid taking multiple locks at the same time.

Try to redesign the logic.

---

# 4. Use tryLock() With Timeout

With `ReentrantLock`, we can use `tryLock()`.

This helps avoid waiting forever.

Example:

```java
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class SafeTransferService {

    private final ReentrantLock lockA = new ReentrantLock();
    private final ReentrantLock lockB = new ReentrantLock();

    public void transfer() throws InterruptedException {

        boolean gotLockA = lockA.tryLock(1, TimeUnit.SECONDS);

        if (!gotLockA) {
            System.out.println("Could not get Lock A");
            return;
        }

        try {
            boolean gotLockB = lockB.tryLock(1, TimeUnit.SECONDS);

            if (!gotLockB) {
                System.out.println("Could not get Lock B");
                return;
            }

            try {
                System.out.println("Transfer completed");
            } finally {
                lockB.unlock();
            }

        } finally {
            lockA.unlock();
        }
    }
}
```

Here, the thread does not wait forever.

If it cannot get the lock within the timeout, it exits safely.

---

# 5. Use Higher-Level Concurrency Classes

Instead of managing low-level locks manually, use Java concurrency utilities.

Examples:

```text
ConcurrentHashMap
BlockingQueue
Semaphore
CountDownLatch
ExecutorService
AtomicInteger
ReadWriteLock
```

Example:

```java
ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
```

This is safer than manually locking a normal `HashMap`.

---

# 6. Avoid Calling External APIs Inside Lock

This is very important in backend systems.

Bad:

```java
synchronized (lock) {
    paymentGateway.call();
}
```

Why bad?

Because the external API can be slow or stuck.

The lock stays held.

Other threads keep waiting.

Better:

```java
PaymentResponse response = paymentGateway.call();

synchronized (lock) {
    updatePaymentStatus(response);
}
```

Call external system outside lock.

Lock only when updating shared data.

---

# 7. Avoid Database Lock Deadlocks Too

Deadlock is not only in Java locks.

It can happen in database also.

Example:

```text
Transaction 1 locks row A, then wants row B.
Transaction 2 locks row B, then wants row A.
```

Same problem.

To avoid DB deadlocks:

```text
Update rows in same order
Keep transactions short
Avoid long-running transactions
Use proper indexes
Do not hold DB locks while calling external APIs
Retry transaction if database reports deadlock
```

This is very practical for backend interviews.

---

# 8. Use Thread Dumps To Detect Deadlock

In production, if application is stuck, we can take a thread dump.

Tools:

```text
jstack
VisualVM
Java Mission Control
Actuator thread dump
APM tools
```

Thread dump can show:

```text
Which thread is blocked
Which lock it is waiting for
Which thread owns that lock
Deadlock details
```

Example command:

```bash
jstack <pid>
```

This helps debug real deadlock issues.

---

# Deadlock In Bank Transfer Example

This is a very common interview example.

Bad transfer logic:

```java
class Account {
    private final int id;
    private int balance;

    public Account(int id, int balance) {
        this.id = id;
        this.balance = balance;
    }

    public void debit(int amount) {
        balance = balance - amount;
    }

    public void credit(int amount) {
        balance = balance + amount;
    }

    public int getId() {
        return id;
    }
}
```

Risky transfer:

```java
public void transfer(Account from, Account to, int amount) {
    synchronized (from) {
        synchronized (to) {
            from.debit(amount);
            to.credit(amount);
        }
    }
}
```

Why risky?

If two transfers happen at same time:

```text
Thread 1: transfer A to B
Thread 2: transfer B to A
```

Then:

```text
Thread 1 locks A and waits for B.
Thread 2 locks B and waits for A.
```

Deadlock.

---

# Safe Bank Transfer Using Lock Ordering

```java
public void transfer(Account from, Account to, int amount) {

    Account firstLock;
    Account secondLock;

    if (from.getId() < to.getId()) {
        firstLock = from;
        secondLock = to;
    } else {
        firstLock = to;
        secondLock = from;
    }

    synchronized (firstLock) {
        synchronized (secondLock) {
            from.debit(amount);
            to.credit(amount);
        }
    }
}
```

Here, locks are always taken in the same order based on account ID.

So circular wait is avoided.

This is a very strong interview example.

---

# Deadlock vs Starvation vs Livelock

## Deadlock

Threads are stuck forever because they wait for each other.

```text
Thread A waits for Thread B
Thread B waits for Thread A
```

## Starvation

A thread waits for a long time because it never gets a chance.

Example:

```text
High-priority threads keep getting CPU.
Low-priority thread keeps waiting.
```

## Livelock

Threads are active, but still no progress happens.

Example:

```text
Thread A keeps stepping aside for Thread B.
Thread B keeps stepping aside for Thread A.
Both are moving, but no work completes.
```

Simple difference:

```text
Deadlock  -> threads are stuck
Starvation -> one thread never gets chance
Livelock  -> threads are active but no progress
```

---

# Common Mistakes

## Mistake 1: Taking Locks In Different Order

This is the most common reason for deadlock.

Bad:

```text
Thread 1: A then B
Thread 2: B then A
```

---

## Mistake 2: Holding Locks During Slow Work

Do not hold locks during:

```text
API calls
Database calls if avoidable
File operations
Long calculations
Network calls
Sleep
```

Keep lock scope small.

---

## Mistake 3: Not Using finally With ReentrantLock

Bad:

```java
lock.lock();
doWork();
lock.unlock();
```

If `doWork()` throws exception, lock is not released.

Good:

```java
lock.lock();

try {
    doWork();
} finally {
    lock.unlock();
}
```

---

## Mistake 4: Ignoring Database Deadlocks

Backend systems can get deadlocks in DB also.

Not only Java code.

---

## Mistake 5: Thinking synchronized Always Causes Deadlock

Wrong.

`synchronized` itself is not bad.

Deadlock happens because of bad lock ordering or bad lock design.

---

# Best Practices

```text
Always acquire locks in a fixed order
Keep synchronized blocks small
Avoid nested locks when possible
Use tryLock with timeout when needed
Do not call external APIs while holding locks
Use concurrent collections instead of manual locking
Use atomic classes for simple counters
Keep database transactions short
Update DB rows in consistent order
Use thread dumps to debug deadlocks
```

---

# Interview-Ready Paragraph Answer

Deadlock is a situation where two or more threads wait for each other forever and none of them can proceed. It usually happens when one thread holds one lock and waits for another lock, while another thread holds that second lock and waits for the first one. A common example is two threads transferring money between two accounts in opposite directions. One thread locks account A and waits for account B, while the other locks account B and waits for account A. To avoid deadlock, I make sure locks are always acquired in a fixed order, keep synchronized blocks small, avoid nested locks where possible, and avoid calling slow external APIs while holding a lock. If I use `ReentrantLock`, I prefer `tryLock()` with timeout so the thread does not wait forever. In backend systems, I also keep database transactions short and update rows in a consistent order because deadlocks can happen in the database too.

---

# 20. Immutability

---
## Summary

**Immutability** means once an object is created, its state cannot be changed.

In simple words:

```text
Create once.
Use many times.
Do not modify.
```

A common Java example is:

```java
String
```

`String` is immutable.

---

## One-Line Answer

**An immutable object is an object whose data cannot be changed after creation.**

---

# What Is Immutability?

Immutability means the object does not change after it is created.

Example:

```java
String name = "Ravi";
name.concat(" Kumar");

System.out.println(name);
```

Output:

```text
Ravi
```

Why?

Because `String` is immutable.

`concat()` does not change the original string.
It creates a new string.

Correct usage:

```java
String name = "Ravi";
name = name.concat(" Kumar");

System.out.println(name);
```

Output:

```text
Ravi Kumar
```

Here, `name` now points to a new object.

The old `"Ravi"` object was not changed.

---

# Why Immutability Is Useful

Immutable objects are useful because they are:

```text
Thread-safe
Simple to understand
Safe to share
Good for caching
Good for HashMap keys
Less bug-prone
```

If an object cannot change, many problems disappear.

---

# Example Of Mutable Class

This class is mutable:

```java
class Employee {

    private int id;
    private String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

Usage:

```java
Employee employee = new Employee(1, "Ravi");

employee.setName("Amit");

System.out.println(employee.getName());
```

Output:

```text
Amit
```

The object changed after creation.

So this is mutable.

---

# Example Of Immutable Class

```java
final class Employee {

    private final int id;
    private final String name;

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
```

Now this object cannot be changed after creation.

Why?

```text
Class is final.
Fields are private.
Fields are final.
No setter methods.
Only getters are provided.
```

Usage:

```java
Employee employee = new Employee(1, "Ravi");

System.out.println(employee.getName());
```

Output:

```text
Ravi
```

There is no `setName()` method.

So we cannot change the name after object creation.

---

# Rules To Create Immutable Class

To create an immutable class in Java, follow these rules:

```text
Make class final.
Make fields private and final.
Do not provide setter methods.
Initialize fields using constructor.
Return copies of mutable fields.
Do not expose internal mutable objects directly.
```

---

# Why Class Should Be final

If the class is not final, a child class can extend it and change behavior.

Example:

```java
class Employee {
}
```

Someone can do:

```java
class MutableEmployee extends Employee {
}
```

To avoid this, make class final:

```java
final class Employee {
}
```

This prevents inheritance.

---

# Why Fields Should Be private final

Example:

```java
private final String name;
```

`private` means no direct access from outside.

`final` means value must be assigned once.

This helps make the object safe.

---

# Why No Setters

Setter methods allow changes.

Example:

```java
public void setName(String name) {
    this.name = name;
}
```

If we provide setters, the object can be changed.

So immutable classes should not have setters.

Only getters are allowed.

---

# Problem With Mutable Fields

This is very important.

Suppose immutable class has a mutable field like `List`.

Bad example:

```java
import java.util.List;

final class Student {

    private final String name;
    private final List<String> subjects;

    public Student(String name, List<String> subjects) {
        this.name = name;
        this.subjects = subjects;
    }

    public List<String> getSubjects() {
        return subjects;
    }
}
```

Usage:

```java
List<String> subjects = new ArrayList<>();
subjects.add("Math");

Student student = new Student("Ravi", subjects);

student.getSubjects().add("Science");

System.out.println(student.getSubjects());
```

Output:

```text
[Math, Science]
```

Even though fields are final, the list content changed.

Why?

Because `final` stops reassignment.
It does not make the object inside immutable.

---

# Correct Immutable Class With Mutable Field

We should make defensive copies.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

final class Student {

    private final String name;
    private final List<String> subjects;

    public Student(String name, List<String> subjects) {
        this.name = name;
        this.subjects = new ArrayList<>(subjects);
    }

    public String getName() {
        return name;
    }

    public List<String> getSubjects() {
        return Collections.unmodifiableList(subjects);
    }
}
```

Now outside code cannot directly modify the internal list.

Usage:

```java
List<String> subjects = new ArrayList<>();
subjects.add("Math");

Student student = new Student("Ravi", subjects);

subjects.add("Science");

System.out.println(student.getSubjects());
```

Output:

```text
[Math]
```

Because constructor created a copy.

If someone tries:

```java
student.getSubjects().add("English");
```

It throws:

```text
UnsupportedOperationException
```

Because we returned an unmodifiable list.

---

# Better Modern Java Option

In modern Java, we can also use:

```java
List.copyOf(subjects)
```

Example:

```java
final class Student {

    private final String name;
    private final List<String> subjects;

    public Student(String name, List<String> subjects) {
        this.name = name;
        this.subjects = List.copyOf(subjects);
    }

    public String getName() {
        return name;
    }

    public List<String> getSubjects() {
        return subjects;
    }
}
```

`List.copyOf()` creates an unmodifiable copy.

This is clean and simple.

---

# String Is Immutable

`String` is the most famous immutable class in Java.

Example:

```java
String s1 = "Java";
String s2 = s1.concat(" Developer");

System.out.println(s1);
System.out.println(s2);
```

Output:

```text
Java
Java Developer
```

`s1` did not change.

A new object was created for `"Java Developer"`.

---

# Why String Is Immutable

`String` is immutable because of:

```text
Security
Thread safety
String pool
HashMap key safety
Caching hashCode
```

Example:

```java
String username = "ravi";
```

If strings were mutable, someone could change important values like usernames, file paths, class names, or database URLs.

That would be dangerous.

---

# Immutability And Thread Safety

Immutable objects are naturally thread-safe.

Why?

Because no thread can change the object after creation.

Example:

```java
final class UserProfile {

    private final String userId;
    private final String email;

    public UserProfile(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }
}
```

Many threads can read this object safely.

No synchronization is needed because there is no modification.

---

# Immutability And HashMap Key

Immutable objects are good keys for `HashMap`.

Example:

```java
Map<String, String> map = new HashMap<>();

map.put("user1", "Ravi");
```

`String` is a good key because it is immutable.

If key data changes after insertion, HashMap lookup can break.

Bad idea:

```java
class Employee {

    int id;

    Employee(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        Employee other = (Employee) obj;
        return this.id == other.id;
    }
}
```

Usage:

```java
Map<Employee, String> map = new HashMap<>();

Employee emp = new Employee(1);
map.put(emp, "Ravi");

emp.id = 2;

System.out.println(map.get(emp));
```

This may return:

```text
null
```

Why?

Because the hash changed after insertion.

That is why immutable keys are safer.

---

# Immutable Object vs final Reference

This is a very common interview point.

```java
final List<String> names = new ArrayList<>();

names.add("Ravi");
names.add("Amit");
```

This is allowed.

Why?

Because `final` means the reference cannot point to a new list.

This is not allowed:

```java
names = new ArrayList<>();
```

But the same list can still be modified.

So:

```text
final reference does not always mean immutable object.
```

---

# Immutable Class vs Unmodifiable Collection

These two are related but not exactly same.

Example:

```java
List<String> list = Collections.unmodifiableList(originalList);
```

This prevents modification through this reference.

But if the original list is still available, it can still be changed.

Example:

```java
List<String> original = new ArrayList<>();
original.add("A");

List<String> unmodifiable = Collections.unmodifiableList(original);

original.add("B");

System.out.println(unmodifiable);
```

Output:

```text
[A, B]
```

So unmodifiable view is not always a true immutable copy.

Better:

```java
List<String> immutable = List.copyOf(original);
```

This creates a separate unmodifiable copy.

---

# Java Records And Immutability

Java records are useful for immutable data carriers.

Example:

```java
public record UserResponse(Long id, String name, String email) {
}
```

Records automatically provide:

```text
Constructor
Getters
equals()
hashCode()
toString()
```

Record fields are final.

But one warning:

If a record contains a mutable field like `List`, we still need defensive copy.

Example:

```java
public record Student(String name, List<String> subjects) {

    public Student {
        subjects = List.copyOf(subjects);
    }
}
```

This makes it safer.

---

# Benefits Of Immutability

## 1. Thread Safety

Immutable objects can be shared across threads safely.

No locking is needed for reading.

---

## 2. Easy To Understand

If an object cannot change, debugging becomes easier.

You do not need to ask:

```text
Who changed this value?
When was it changed?
Which thread changed it?
```

---

## 3. Safe HashMap Keys

Immutable keys do not change hash code after insertion.

So lookup remains safe.

---

## 4. Good For Caching

Immutable objects can be cached safely.

Because cached value will not be changed by someone else.

---

## 5. Better Design

Immutable DTOs and value objects reduce bugs.

Example:

```java
public record Money(BigDecimal amount, String currency) {
}
```

This is safer than a mutable money object.

---

# Disadvantages Of Immutability

Immutability also has some trade-offs.

```text
More objects can be created.
Memory usage can increase.
Updating data means creating a new object.
Not always suitable for large changing objects.
```

Example:

```java
String result = "";

for (int i = 0; i < 1000; i++) {
    result = result + i;
}
```

This creates many String objects.

Better:

```java
StringBuilder builder = new StringBuilder();

for (int i = 0; i < 1000; i++) {
    builder.append(i);
}

String result = builder.toString();
```

Use `StringBuilder` for heavy string modification.

---

# String vs StringBuilder vs StringBuffer

| Class         | Mutable?  | Thread-safe?           | Use                                  |
| ------------- | --------- | ---------------------- | ------------------------------------ |
| String        | Immutable | Yes, because immutable | Fixed text                           |
| StringBuilder | Mutable   | No                     | Fast string changes in single thread |
| StringBuffer  | Mutable   | Yes                    | Thread-safe string changes           |

In most single-thread cases, use `StringBuilder`.

---

# Example: Immutable DTO In Backend

```java
public final class UserResponse {

    private final Long id;
    private final String name;
    private final String email;

    public UserResponse(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
```

This response object is safe.

Once created, it cannot be changed.

This is good for API responses.

---

# Common Immutable Classes In Java

Examples:

```text
String
Integer
Long
Double
BigDecimal
BigInteger
LocalDate
LocalDateTime
UUID
```

These are immutable.

Example:

```java
LocalDate date = LocalDate.now();

LocalDate nextDay = date.plusDays(1);
```

`date` does not change.

`plusDays(1)` returns a new `LocalDate`.

---

# Common Interview Mistakes

## Mistake 1: Saying final Object Is Always Immutable

Wrong.

Example:

```java
final List<String> list = new ArrayList<>();
list.add("Ravi");
```

The list can still change.

---

## Mistake 2: Not Handling Mutable Fields

If immutable class has `List`, `Date`, `Map`, or array, use defensive copy.

---

## Mistake 3: Adding Setters

If a class has setters, it is usually not immutable.

---

## Mistake 4: Returning Internal Mutable Object

Bad:

```java
public List<String> getSubjects() {
    return subjects;
}
```

If `subjects` is mutable, outside code can modify it.

Return copy or unmodifiable copy.

---

## Mistake 5: Thinking Immutability Means No New Object

Immutability means old object does not change.

New objects can be created with updated values.

---

# Best Practices

```text
Make class final.
Make fields private final.
Do not provide setters.
Initialize all fields in constructor.
Use defensive copy for mutable inputs.
Return defensive copy or unmodifiable copy.
Use immutable fields where possible.
Use records for simple immutable data.
Use StringBuilder for heavy string modification.
Use immutable objects in multithreaded code where possible.
```

---

# Interview-Ready Paragraph Answer

Immutability means an object cannot be changed after it is created. In Java, `String` is the best example of an immutable class. If we perform an operation on a String, it does not modify the original object. It creates a new object. To create an immutable class, we should make the class final, make fields private and final, initialize fields through the constructor, avoid setters, and return defensive copies for mutable fields like List, Map, Date, or arrays. Immutable objects are useful because they are thread-safe, easy to share, safe for caching, and good as HashMap keys because their hash code does not change after insertion. But immutability can create more objects when frequent changes are needed, so for heavy string modifications we use `StringBuilder`. In simple words, immutable objects make code safer, cleaner, and easier to reason about.

---

# 21. How does ConcurrentHashMap work internally?

---
## Summary

`ConcurrentHashMap` is a thread-safe map.

It is used when multiple threads need to read and update a map safely.

Internally, it is similar to `HashMap`, but with concurrency control.

```text
HashMap              -> not thread-safe
ConcurrentHashMap    -> thread-safe
```

## One-Line Answer

**ConcurrentHashMap works by using bucket-level locking, CAS operations, volatile reads, and atomic methods so multiple threads can safely read and update the map without locking the whole map.**

---

# What Is ConcurrentHashMap?

`ConcurrentHashMap` stores data in key-value format.

Example:

```java
ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

map.put(1, "Ravi");
map.put(2, "Amit");

System.out.println(map.get(1));
```

Output:

```text
Ravi
```

It is mainly used when many threads access the same map.

Example use cases:

```text
Cache
Session-like data
Rate limiter counters
Shared lookup data
Concurrent request tracking
```

---

# Why Not Use HashMap In Multithreading?

`HashMap` is not thread-safe.

If many threads update a `HashMap` at the same time, we can get issues like:

```text
Lost updates
Wrong data
Unexpected behavior
ConcurrentModificationException during iteration
```

So for concurrent access, we use `ConcurrentHashMap`.

---

# Internal Structure

In Java 8 and above, `ConcurrentHashMap` internally uses:

```text
Array of buckets
Node objects
Linked list for collision
Red-Black Tree for heavy collision
CAS
synchronized block on bucket
volatile fields
```

Each bucket can contain nodes.

A node roughly contains:

```text
hash
key
value
next
```

Simple idea:

```java
static class Node<K, V> {
    final int hash;
    final K key;
    volatile V value;
    volatile Node<K, V> next;
}
```

`value` and `next` are volatile so changes are visible to other threads.

---

# How put() Works Internally

When we call:

```java
map.put("name", "Ravi");
```

The internal flow is:

```text
1. Calculate hash of the key.
2. Find bucket index.
3. If bucket is empty, insert using CAS.
4. If bucket is not empty, lock only that bucket.
5. Compare keys using equals().
6. If same key exists, update value.
7. If key does not exist, add new node.
8. If bucket becomes too large, convert linked list into tree.
9. Resize table if needed.
```

The main point is:

```text
ConcurrentHashMap does not lock the whole map.
It locks only the affected bucket during update.
```

---

# What Is CAS?

CAS means **Compare And Swap**.

Simple meaning:

```text
Update this value only if it is still the value I expected.
```

Example:

```text
If bucket is empty, put my node there.
But if another thread already inserted a node, retry.
```

CAS helps insert into empty buckets without locking.

That makes `ConcurrentHashMap` faster.

---

# What Happens When Bucket Is Empty?

Suppose the calculated bucket is empty.

```text
Bucket 5 -> empty
```

Then `ConcurrentHashMap` tries to put the new node using CAS.

If no other thread changed that bucket, insert succeeds.

```text
Bucket 5 -> ["name", "Ravi"]
```

No full map lock is needed.

---

# What Happens When Bucket Is Not Empty?

Suppose bucket already has some nodes.

```text
Bucket 5 -> Node1 -> Node2
```

Now another thread wants to insert into the same bucket.

Then `ConcurrentHashMap` locks only that bucket.

```text
Only bucket 5 is locked.
Other buckets are still free.
```

So another thread can update bucket 7 or bucket 10 at the same time.

This is why it performs better than `Hashtable`.

---

# How get() Works Internally

When we call:

```java
map.get("name");
```

The flow is:

```text
1. Calculate hash of the key.
2. Find bucket index.
3. Read bucket.
4. Compare keys using equals().
5. Return value if found.
6. Return null if not found.
```

Most `get()` operations do not use locking.

So reads are very fast.

Important line:

```text
Reads in ConcurrentHashMap are mostly lock-free.
```

---

# Collision Handling

Collision means two different keys go to the same bucket.

Example:

```text
key1 -> bucket 3
key2 -> bucket 3
```

`ConcurrentHashMap` handles collision using:

```text
Linked list
Red-Black Tree
```

If collision is small, it uses linked list.

```text
Bucket 3 -> Node1 -> Node2 -> Node3
```

If the bucket becomes too large, it can convert into a Red-Black Tree.

That improves search performance.

```text
Linked list search -> O(n)
Tree search        -> O(log n)
```

---

# Java 7 vs Java 8 ConcurrentHashMap

This is a very good interview point.

## Java 7

Java 7 `ConcurrentHashMap` used **segments**.

Simple meaning:

```text
Map was divided into segments.
Each segment had its own lock.
```

Example:

```text
Segment 1
Segment 2
Segment 3
Segment 4
```

If one segment was locked, other segments could still work.

This was called **segment-level locking**.

---

## Java 8

Java 8 removed segment-based locking.

Java 8 uses:

```text
CAS for empty bucket insertion
synchronized on bucket head for updates
volatile for visibility
Red-Black Tree for heavy collision
```

Simple interview line:

```text
Java 7 used segment-level locking.
Java 8 uses CAS and bucket-level locking.
```

---

# Why It Is Better Than Hashtable

`Hashtable` is thread-safe, but it locks the whole map.

Example:

```text
Thread 1 does put()
Whole Hashtable is locked
Thread 2 has to wait
```

`ConcurrentHashMap` is better because it locks only a small part.

Example:

```text
Thread 1 updates bucket 2
Thread 2 updates bucket 8
Both can work at the same time
```

So for high concurrency, `ConcurrentHashMap` is much better.

---

# Null Key And Null Value

`ConcurrentHashMap` does not allow null key or null value.

This is not allowed:

```java
map.put(null, "Ravi");
```

This is also not allowed:

```java
map.put(1, null);
```

Both throw `NullPointerException`.

Why?

Because in concurrent code, `null` can create confusion.

Example:

```java
map.get(1);
```

If it returns `null`, it is unclear:

```text
Key is not present?
Or key is present with null value?
```

So `ConcurrentHashMap` does not allow nulls.

---

# Important Atomic Methods

`ConcurrentHashMap` provides useful atomic methods.

## putIfAbsent()

```java
map.putIfAbsent(1, "Ravi");
```

Meaning:

```text
Put value only if key is not already present.
```

This is thread-safe.

---

## computeIfAbsent()

```java
map.computeIfAbsent(1, key -> "User-" + key);
```

Meaning:

```text
If key is missing, compute value and store it.
If key exists, return old value.
```

Very useful for cache-like code.

---

## compute()

```java
map.compute("user1", (key, oldValue) -> oldValue == null ? 1 : oldValue + 1);
```

This is useful for counters.

Better than this:

```java
if (map.containsKey("user1")) {
    map.put("user1", map.get("user1") + 1);
}
```

Why?

Because `containsKey()` and `put()` are two separate operations.

Another thread can change the map between them.

`compute()` does it atomically.

---

# Example With Multiple Threads

```java
import java.util.concurrent.ConcurrentHashMap;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ConcurrentHashMap<Integer, Integer> map = new ConcurrentHashMap<>();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(map.size());
    }
}
```

Output:

```text
1000
```

Both threads put same keys.

Since keys are unique, final size is `1000`.

The map stays safe.

---

# Iterator Behavior

`ConcurrentHashMap` iterator is **weakly consistent**.

That means:

```text
It does not throw ConcurrentModificationException.
It may or may not show latest changes during iteration.
```

Example:

```java
for (Integer key : map.keySet()) {
    map.put(100, "New Value");
}
```

This will not fail like `HashMap`.

But the iterator may or may not include the newly added value.

That is normal.

---

# Time Complexity

Average time complexity:

```text
get()    -> O(1)
put()    -> O(1)
remove() -> O(1)
```

Worst case with tree:

```text
O(log n)
```

This happens when many keys land in the same bucket and the bucket becomes tree-based.

---

# ConcurrentHashMap vs HashMap

| Point       | HashMap                     | ConcurrentHashMap              |
| ----------- | --------------------------- | ------------------------------ |
| Thread-safe | No                          | Yes                            |
| Null key    | Allows one null key         | Does not allow null key        |
| Null values | Allows multiple null values | Does not allow null values     |
| Reads       | Fast                        | Fast and mostly lock-free      |
| Writes      | Not safe in multithreading  | Safe with bucket-level locking |
| Iterator    | Fail-fast                   | Weakly consistent              |
| Use case    | Single-threaded code        | Multi-threaded code            |

---

# Common Interview Mistakes

## Mistake 1: Saying ConcurrentHashMap Locks The Whole Map

Wrong.

It does not lock the whole map for normal operations.

It locks only the affected bucket during updates.

---

## Mistake 2: Saying It Allows null

Wrong.

`ConcurrentHashMap` does not allow null key or null value.

---

## Mistake 3: Using containsKey() Then put()

This is not atomic.

Bad:

```java
if (!map.containsKey(key)) {
    map.put(key, value);
}
```

Better:

```java
map.putIfAbsent(key, value);
```

---

## Mistake 4: Expecting Iterator To Show Latest Data Exactly

Its iterator is weakly consistent.

It is safe, but it may not always show the latest updates.

---

## Mistake 5: Saying It Is Always Better Than HashMap

Not always.

For single-threaded code, `HashMap` is usually faster.

Use `ConcurrentHashMap` when thread safety is needed.

---

# Best Simple Explanation

Remember this:

```text
ConcurrentHashMap is like HashMap with smart thread safety.

Reads are mostly lock-free.
Writes lock only the required bucket.
It uses CAS when possible.
It does not allow null.
It provides atomic methods for safe updates.
```

---

# Interview-Ready Paragraph Answer

`ConcurrentHashMap` is a thread-safe map used when multiple threads need to access or update the map at the same time. Internally, it works like `HashMap` because it uses an array of buckets, nodes, linked lists, and Red-Black Trees for collision handling. But the main difference is how it handles concurrency. In Java 8, reads are mostly lock-free. For write operations, if the bucket is empty, it uses CAS to insert the node. If the bucket already has nodes, it locks only that bucket, not the whole map. This allows multiple threads to update different buckets at the same time. It also uses volatile fields for visibility. `ConcurrentHashMap` does not allow null keys or null values because null creates confusion in concurrent reads. Its iterators are weakly consistent, so they do not throw `ConcurrentModificationException`. It also provides atomic methods like `putIfAbsent()` and `computeIfAbsent()` to avoid race conditions. In simple words, `ConcurrentHashMap` gives thread safety with better performance by avoiding full-map locking.
