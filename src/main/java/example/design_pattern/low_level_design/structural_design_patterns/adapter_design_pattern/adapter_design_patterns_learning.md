## Summary

**Adapter Design Pattern is used when two things cannot work together directly because their interfaces are different. Adapter works like a bridge between them.**

## One-Line Answer

**Adapter pattern converts one interface into another interface that the client expects.**

---

# Your Notes In Simple Words

In your handwritten notes, you wrote:

```text
Adapter works as a bridge between existing interface and expected interface.
```

That is 100% correct.

Meaning:

```text
Client wants something in one format
But existing system gives something in another format
So Adapter converts it
```

Simple diagram:

```text
Client  --->  Adapter  --->  Existing Old System
```

Example from your notes:

```text
Client wants JSON
Old system gives XML

Adapter converts XML to JSON
```

So client does not need to understand XML.

Client only works with JSON.

---

# Very Simple Real-Life Example

Suppose your phone charger has a **2-pin plug**.

But the wall socket supports **3-pin plug**.

Directly, they cannot connect.

So what do we use?

```text
Power Adapter
```

The adapter connects both sides.

```text
2-pin plug  --->  Adapter  --->  3-pin socket
```

The phone charger is not changed.
The socket is not changed.
Only adapter helps them work together.

Same thing happens in code.

---

# Main Idea Of Adapter Pattern

Adapter pattern is used when:

```text
Existing class has one method
But client expects another method
```

So we create an adapter class in between.

The adapter:

1. Accepts request from client.
2. Converts it into the format old class understands.
3. Calls old class.
4. Returns result in the format client expects.

---

# Important Terms

## 1. Client

Client is the code that wants to use some functionality.

Example:

```text
PaymentService
OrderService
Controller
```

Client expects a clean interface.

---

## 2. Target Interface

This is the interface expected by the client.

In your notes, this is written like:

```text
Expected Interface
```

Example:

```java
getWeightInKg()
```

Client wants weight in KG.

---

## 3. Adaptee

Adaptee is the existing old class.

In your notes, this is written like:

```text
Existing Interface
or
Adaptee
```

Example:

```java
getWeightInPounds()
```

Old machine gives weight in pounds.

---

## 4. Adapter

Adapter is the middle class.

It converts old output into expected output.

Example:

```text
Pounds to KG converter
XML to JSON converter
Old API to new API converter
```

---

# Diagram From Your Notes Explained

Your note is basically this:

```text
Client
  |
  v
Expected Interface
  |
  v
Concrete Adapter
  |
  v
Existing Interface / Adaptee
```

In code terms:

```text
Client calls Target interface method
Adapter implements Target interface
Adapter has old Adaptee object
Adapter calls Adaptee method internally
```

Very important line:

```text
ConcreteAdapter implements expected interface
ConcreteAdapter has-a Adaptee
```

This is called **Object Adapter**.

Because adapter contains an object of old class.

---

# Java Example Using Your Weight Machine Idea

Suppose old weighing machine gives weight in **pounds**.

But our application wants weight in **KG**.

## Existing Old Class

```java
// This is old class.
// We cannot change this class.
// It gives weight in pounds.
class WeightMachine {
    public double getWeightInPounds() {
        return 220.0;
    }
}
```

Problem:

```text
Client wants KG
But old machine gives pounds
```

So we create an adapter.

---

## Target Interface

```java
// This is the interface expected by client.
// Client wants weight in KG.
interface WeightMachineAdapter {
    double getWeightInKg();
}
```

---

## Adapter Class

```java
// Adapter class
// It converts pounds into KG.
class WeightMachineAdapterImpl implements WeightMachineAdapter {

    private WeightMachine weightMachine;

    public WeightMachineAdapterImpl(WeightMachine weightMachine) {
        this.weightMachine = weightMachine;
    }

    @Override
    public double getWeightInKg() {

        // Existing machine gives weight in pounds
        double weightInPounds = weightMachine.getWeightInPounds();

        // Convert pounds to kg
        double weightInKg = weightInPounds * 0.453592;

        return weightInKg;
    }
}
```

---

## Client Code

```java
public class Main {
    public static void main(String[] args) {

        WeightMachine oldMachine = new WeightMachine();

        WeightMachineAdapter adapter =
                new WeightMachineAdapterImpl(oldMachine);

        double weight = adapter.getWeightInKg();

        System.out.println("Weight in KG: " + weight);
    }
}
```

Output:

```text
Weight in KG: 99.79024
```

---

# What Happened Here?

Old class gives:

```text
Pounds
```

Client wants:

```text
KG
```

Adapter converts:

```text
Pounds -> KG
```

So client is happy.

Old class is also not changed.

That is Adapter Pattern.

---

# XML To JSON Example From Your Notes

Your note also says:

```text
XML to JSON parser DTO
```

This is a very good backend example.

Suppose old third-party system gives response like this:

```xml
<user>
    <name>Rishabh</name>
    <age>25</age>
</user>
```

But your new service works with JSON:

```json
{
  "name": "Rishabh",
  "age": 25
}
```

Now we should not change the whole new service.

We create an adapter:

```text
Old XML API  --->  Adapter  --->  New JSON-based Service
```

The adapter reads XML and converts it into DTO/JSON format.

---

# Backend Example

Suppose your application expects this:

```java
interface UserProvider {
    UserDto getUser();
}
```

But old system gives XML:

```java
class OldXmlUserService {
    public String getUserXml() {
        return "<user><name>Rishabh</name><age>25</age></user>";
    }
}
```

Now create adapter:

```java
class XmlToUserAdapter implements UserProvider {

    private OldXmlUserService oldXmlUserService;

    public XmlToUserAdapter(OldXmlUserService oldXmlUserService) {
        this.oldXmlUserService = oldXmlUserService;
    }

    @Override
    public UserDto getUser() {

        String xml = oldXmlUserService.getUserXml();

        // In real project, we use XML parser here.
        // For simple understanding, assume we converted XML to DTO.
        UserDto user = new UserDto();
        user.setName("Rishabh");
        user.setAge(25);

        return user;
    }
}
```

Now client uses only this:

```java
UserProvider userProvider = new XmlToUserAdapter(new OldXmlUserService());

UserDto user = userProvider.getUser();
```

Client does not care that old system was XML-based.

That complexity is hidden inside adapter.

---

# Why Adapter Pattern Is Useful

Adapter pattern is useful because:

```text
We can reuse old code without changing it.
```

It helps when:

```text
Old system and new system have different interfaces.
```

It keeps client code clean.

It avoids changing existing tested code.

It is very useful when working with:

```text
Third-party APIs
Legacy systems
Different request/response formats
External libraries
Payment gateways
Banking systems
XML to JSON conversion
DTO mapping
```

---

# Real Project Example

In backend projects, this happens many times.

Example:

Your service expects:

```text
CustomerDto
```

But external API returns:

```text
CustomerXmlResponse
```

So you create:

```text
CustomerAdapter
```

That adapter converts:

```text
CustomerXmlResponse -> CustomerDto
```

Then your business logic only works with `CustomerDto`.

This makes code cleaner.

---

# Adapter Pattern In Spring Boot

In Spring Boot, adapter is commonly used when calling external systems.

Example:

```text
Controller
   |
Service
   |
PaymentGatewayAdapter
   |
External Payment API
```

Your service should not directly depend on external API classes.

Better design:

```java
interface PaymentGateway {
    PaymentResponse pay(PaymentRequest request);
}
```

Then adapter:

```java
class RazorpayPaymentAdapter implements PaymentGateway {
    public PaymentResponse pay(PaymentRequest request) {
        // Convert our request to Razorpay request
        // Call Razorpay API
        // Convert Razorpay response to our response
        return new PaymentResponse();
    }
}
```

Later, if you change gateway:

```text
Razorpay -> PayU -> Stripe
```

your main business service does not change much.

Only adapter changes.

---

# Adapter Pattern Structure

```text
Client
  |
  v
Target Interface
  |
  v
Adapter
  |
  v
Adaptee / Existing Class
```

Meaning:

```text
Client talks to Target Interface.
Adapter implements Target Interface.
Adapter talks to old Existing Class.
```

---

# Easy Memory Trick

Remember this line:

```text
Adapter means converter.
```

Or:

```text
Adapter means bridge between what client wants and what existing system provides.
```

Your notes already have the best line:

```text
Bridge between expected interface and existing interface.
```

---

# Adapter Pattern Example In One Small Diagram

```text
Without Adapter

Client wants JSON  X  Old system gives XML
                  incompatible


With Adapter

Client wants JSON  --->  Adapter  --->  Old system gives XML
                         XML to JSON
```

---

# When Should We Use Adapter Pattern?

Use adapter pattern when:

```text
Two interfaces are not matching.
```

Use it when:

```text
You cannot change old code.
```

Use it when:

```text
You want to reuse legacy code.
```

Use it when:

```text
You want to integrate third-party API.
```

Use it when:

```text
You want to convert one data format into another.
```

---

# When Should We Not Use Adapter Pattern?

Do not use adapter if both classes already work together directly.

Do not use it just to make simple code more complex.

If only one small method name is different and you control both classes, you may simply refactor the code.

---

# Adapter Vs Facade

This is a common interview confusion.

| Pattern | Simple Meaning                                     |
| ------- | -------------------------------------------------- |
| Adapter | Makes incompatible interfaces work together        |
| Facade  | Hides complex internal system behind simple method |

Example:

```text
Adapter = charger converter
Facade = one power button for laptop startup
```

Adapter changes interface.

Facade simplifies usage.

---

# Adapter Vs Decorator

| Pattern   | Simple Meaning      |
| --------- | ------------------- |
| Adapter   | Converts interface  |
| Decorator | Adds extra behavior |

Example:

```text
Adapter = 2-pin to 3-pin converter
Decorator = coffee + milk + sugar
```

Adapter helps compatibility.

Decorator adds features.

---

# Interview-Ready Answer

**Adapter design pattern is a structural design pattern. It is used when two classes or systems cannot work together directly because their interfaces are different. Adapter works as a bridge between the expected interface and the existing interface. The client calls the adapter using the interface it understands, and the adapter internally calls the old or third-party class after converting the request or response. For example, if our application expects JSON but an old system gives XML, we can create an XML-to-JSON adapter. This helps us reuse old code without changing it and keeps the client code clean. In backend systems, adapter is commonly used for third-party API integration, legacy system integration, DTO conversion, and payment gateway integration.**

---

# Very Short Interview Version

**Adapter pattern converts one interface into another interface that the client expects. It is used when two systems are incompatible. It acts like a bridge, for example converting XML response from an old system into JSON or DTO format required by the new system.**
