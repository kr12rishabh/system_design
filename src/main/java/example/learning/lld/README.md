# Low Level Design Learning - Java

This package is a separate learning area for **Low Level Design (LLD)** and **Design Patterns in Java**.

It is intentionally kept separate from the existing application code and the older `example.design_pattern` implementations.

The goal is to learn everything from scratch using very simple English.

---

## Package

```text
example.learning.lld
```

Everything we learn in this course will be added below this package.

---

## How We Will Learn

For every topic we will follow this order:

```text
1. Understand the problem
2. See a simple example
3. See bad design
4. Understand why it is bad
5. Improve the design
6. Write Java code
7. Understand the important interview point
8. Solve a practice problem
```

We will NOT memorize design pattern code.

We will first understand the problem that the pattern solves.

---

# Complete Learning Roadmap

## Phase 0 - Foundation

### 1. OOP for LLD

- Class
- Object
- Encapsulation
- Abstraction
- Inheritance
- Polymorphism
- Interface
- Abstract class
- IS-A relationship
- HAS-A relationship
- Composition over inheritance

### 2. Class Relationships

- Association
- Aggregation
- Composition
- Inheritance
- Dependency

### 3. SOLID Principles

- Single Responsibility Principle
- Open Closed Principle
- Liskov Substitution Principle
- Interface Segregation Principle
- Dependency Inversion Principle

### 4. UML Basics

Only the UML needed for LLD interviews.

### 5. How To Approach An LLD Problem

```text
Requirements
    -> Objects
    -> Relationships
    -> Interfaces
    -> Classes
    -> Patterns
    -> Java Code
    -> Edge Cases
    -> Extensibility
```

---

# Phase 1 - Design Patterns

## Creational

1. Singleton
2. Factory Method
3. Builder
4. Abstract Factory
5. Prototype

## Structural

1. Adapter
2. Decorator
3. Facade
4. Proxy
5. Composite
6. Bridge
7. Flyweight

## Behavioral

1. Strategy
2. Observer
3. State
4. Chain of Responsibility
5. Command
6. Template Method
7. Iterator
8. Mediator
9. Memento
10. Visitor
11. Interpreter

---

# Phase 2 - LLD Problems

## Beginner

1. Tic Tac Toe
2. Snake and Ladder
3. Vending Machine
4. Parking Lot
5. Library Management System

## Intermediate

6. Elevator System
7. ATM
8. Coffee Machine
9. Movie Ticket Booking
10. Hotel Management
11. Car Rental
12. Splitwise
13. Food Delivery
14. Cab Booking

## Advanced

15. WhatsApp / Messenger
16. Uber / Ola
17. Amazon
18. Swiggy / Zomato
19. Chess
20. Notification System
21. Logging Framework
22. Cache System
23. Rate Limiter
24. Payment System

---

# Folder Structure

```text
example/learning/lld/
|
|-- README.md
|
|-- foundation/
|   |-- oop/
|   |-- relationships/
|   |-- solid/
|   |-- uml/
|   `-- lldapproach/
|
|-- patterns/
|   |-- creational/
|   |-- structural/
|   `-- behavioral/
|
`-- problems/
    |-- beginner/
    |-- intermediate/
    `-- advanced/
```

We will add code only when we actually learn that topic, so the repository grows along with the learning journey.

---

# Important Rule

Do not think:

```text
I need to remember Strategy Pattern code.
```

Think:

```text
I have multiple behaviours that can change.
How can I keep these behaviours separate?

-> Strategy Pattern may help.
```

That way design patterns become logical instead of something to memorize.
