Absolutely. Let’s learn the **Factory Method Pattern** properly, in **very simple English**, with a **Pizza example**, and in a way that actually stays in your head.

I’ll teach it like this:

1. what problem it solves
2. what Factory Method really means
3. why normal code becomes messy without it
4. Pizza example step by step
5. UML diagram
6. full Java code with `main` method
7. deep explanation of every class
8. advantages and disadvantages
9. common interview questions
10. mistakes beginners make
11. how to explain it in interviews

---

# Factory Method Pattern

## First Understand The Real Problem

Imagine you are making a pizza app.

A customer can order:

* Margherita Pizza
* Chicken Pizza
* Veggie Pizza

Now suppose your code looks like this:

```java
if (type.equals("margherita")) {
    pizza = new MargheritaPizza();
} else if (type.equals("chicken")) {
    pizza = new ChickenPizza();
} else if (type.equals("veggie")) {
    pizza = new VeggiePizza();
}
```

At first, this feels normal.

But after some time, problems start coming:

* this `if-else` becomes bigger and bigger
* many classes may start creating pizza objects
* every new pizza type means changing old code
* business logic and object creation get mixed together
* code becomes hard to maintain

This is where **Factory Method Pattern** helps.

---

# Simple Definition

## Factory Method Pattern says

**Do not create objects directly in the main business code.**
Instead, create them through a special method called a **factory method**.

And usually:

* the parent class defines the method
* child classes decide which exact object to create

That is the heart of Factory Method.

---

# In One Very Simple Line

## Factory Method Pattern =

**“Let subclasses decide which object should be created.”**

---

# Real Life Analogy

Suppose you go to a pizza store.

You say:

> I want a pizza.

The store does the full process:

* create pizza
* prepare dough
* add toppings
* bake it
* cut it
* pack it

But the exact pizza may be:

* Margherita
* Chicken
* Veggie

So the overall process is common, but the exact pizza object changes.

That is exactly what Factory Method does.

---

# Why It Is Called “Factory Method”

Because there is a **method** whose job is to create objects.

That method is called the **factory method**.

Example:

```java
protected abstract Pizza createPizza();
```

This method is not doing baking or packing.
Its job is only this:

> “Tell me which Pizza object to create.”

---

# Main Idea In Pizza Example

We want this:

* ordering process should be common
* but pizza type should change

So:

* `PizzaStore` will handle the common order flow
* subclasses like `MargheritaPizzaStore` or `ChickenPizzaStore` will decide which pizza object to create

---

# Important Parts Of Factory Method Pattern

There are mainly 4 parts.

## 1. Product

This is the common interface or abstract class.

In our example:

```java
abstract class Pizza
```

All pizzas are pizzas.

---

## 2. Concrete Products

These are real objects.

Examples:

* `MargheritaPizza`
* `ChickenPizza`
* `VeggiePizza`

---

## 3. Creator

This is the parent class that declares the factory method.

Example:

```java
abstract class PizzaStore
```

This class says:

> I know how to process an order, but I do not know which exact pizza to create.

---

## 4. Concrete Creators

These are child classes that override the factory method.

Examples:

* `MargheritaPizzaStore`
* `ChickenPizzaStore`
* `VeggiePizzaStore`

These classes decide which exact pizza object gets returned.

---

# Very Important Point

Many students get confused here.

## The factory method is not the whole pattern by itself

The full pattern is this combination:

* creator class
* factory method
* product hierarchy
* business method using that product

So the pattern is not just:

```java
new MargheritaPizza()
```

No.

The pattern is about **moving object creation to a special method** and letting **subclasses control it**.

---

# Pizza Example Structure

We will build it like this:

```text
Pizza                  -> Product
MargheritaPizza        -> Concrete Product
ChickenPizza           -> Concrete Product
VeggiePizza            -> Concrete Product

PizzaStore             -> Creator
MargheritaPizzaStore   -> Concrete Creator
ChickenPizzaStore      -> Concrete Creator
VeggiePizzaStore       -> Concrete Creator
```

---

# UML Class Diagram

Here is the UML in simple text form.

```text
                    +----------------------+
                    |        Pizza         |
                    |----------------------|
                    | +prepare()           |
                    | +bake()              |
                    | +cut()               |
                    | +box()               |
                    +----------^-----------+
                               |
        -------------------------------------------------
        |                       |                       |
+------------------+  +------------------+  +------------------+
| MargheritaPizza  |  |  ChickenPizza    |  |   VeggiePizza    |
+------------------+  +------------------+  +------------------+


                    +----------------------+
                    |      PizzaStore      |
                    |----------------------|
                    | +orderPizza()        |
                    | #createPizza()       |
                    +----------^-----------+
                               |
        -------------------------------------------------
        |                       |                       |
+-----------------------+ +----------------------+ +----------------------+
| MargheritaPizzaStore  | | ChickenPizzaStore    | | VeggiePizzaStore     |
+-----------------------+ +----------------------+ +----------------------+
| +createPizza()        | | +createPizza()       | | +createPizza()       |
+-----------------------+ +----------------------+ +----------------------+
```

---

# Flow Of The Pattern

When client calls:

```java
store.orderPizza();
```

the flow is:

1. `orderPizza()` starts
2. inside it, `createPizza()` is called
3. child class returns actual pizza object
4. then common steps happen:

    * prepare
    * bake
    * cut
    * box

So the store does not need to know the exact pizza class directly.

---

# Now Let Us First See The Bad Version

Before learning the correct pattern, first understand the bad code.

```java
class PizzaShop {
    public Pizza orderPizza(String type) {
        Pizza pizza;

        if (type.equalsIgnoreCase("margherita")) {
            pizza = new MargheritaPizza();
        } else if (type.equalsIgnoreCase("chicken")) {
            pizza = new ChickenPizza();
        } else if (type.equalsIgnoreCase("veggie")) {
            pizza = new VeggiePizza();
        } else {
            throw new IllegalArgumentException("Invalid pizza type");
        }

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
```

## What is wrong here?

This code works. But design is weak.

Problems:

* `PizzaShop` knows all pizza classes
* adding new pizza means editing this class
* too many conditions will come later
* creator logic and business logic are mixed
* not open for easy extension

This is exactly why Factory Method exists.

---

# Now Let Us Build The Correct Factory Method Pattern

---

# Step 1: Create Product Class

This is the base pizza type.

```java
abstract class Pizza {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract void prepare();

    public void bake() {
        System.out.println(name + " is baking for 20 minutes.");
    }

    public void cut() {
        System.out.println(name + " is being cut into slices.");
    }

    public void box() {
        System.out.println(name + " is packed in the pizza box.");
    }
}
```

## Why abstract class here?

Because all pizzas share some common behavior:

* bake
* cut
* box

But each pizza prepares differently.

So `prepare()` is abstract.

That means every pizza must define its own preparation.

---

# Step 2: Create Concrete Products

Now let us create real pizza classes.

## Margherita Pizza

```java
class MargheritaPizza extends Pizza {

    public MargheritaPizza() {
        this.name = "Margherita Pizza";
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding dough, tomato sauce, mozzarella cheese, and basil.");
    }
}
```

## Chicken Pizza

```java
class ChickenPizza extends Pizza {

    public ChickenPizza() {
        this.name = "Chicken Pizza";
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding dough, spicy sauce, cheese, grilled chicken, and onions.");
    }
}
```

## Veggie Pizza

```java
class VeggiePizza extends Pizza {

    public VeggiePizza() {
        this.name = "Veggie Pizza";
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding dough, tomato sauce, cheese, capsicum, olives, onions, and mushrooms.");
    }
}
```

These are called **Concrete Products**.

Each one is a real pizza object.

---

# Step 3: Create Creator Class

This is the most important part.

```java
abstract class PizzaStore {

    public Pizza orderPizza() {
        Pizza pizza = createPizza();

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    protected abstract Pizza createPizza();
}
```

---

# Why Is This Class So Important?

Because this class contains the **common business flow**.

This method:

```java
public Pizza orderPizza()
```

is saying:

> No matter which pizza it is, the process is mostly same:
> create it, prepare it, bake it, cut it, box it.

But this class does **not** know which exact pizza to create.

That is why we have:

```java
protected abstract Pizza createPizza();
```

This is the **factory method**.

---

# Step 4: Create Concrete Creators

Now child classes decide the exact pizza object.

## Margherita Pizza Store

```java
class MargheritaPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza() {
        return new MargheritaPizza();
    }
}
```

## Chicken Pizza Store

```java
class ChickenPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza() {
        return new ChickenPizza();
    }
}
```

## Veggie Pizza Store

```java
class VeggiePizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza() {
        return new VeggiePizza();
    }
}
```

Now each store decides what pizza object will be created.

That is the core Factory Method behavior.

---

# Step 5: Full Working Code With Main Method

Now I will give you the **complete Java code** in one place.

```java
abstract class Pizza {
    protected String name;

    public String getName() {
        return name;
    }

    public abstract void prepare();

    public void bake() {
        System.out.println(name + " is baking for 20 minutes.");
    }

    public void cut() {
        System.out.println(name + " is being cut into slices.");
    }

    public void box() {
        System.out.println(name + " is packed in the pizza box.");
    }
}

class MargheritaPizza extends Pizza {
    public MargheritaPizza() {
        this.name = "Margherita Pizza";
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding dough, tomato sauce, mozzarella cheese, and basil.");
    }
}

class ChickenPizza extends Pizza {
    public ChickenPizza() {
        this.name = "Chicken Pizza";
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding dough, spicy sauce, cheese, grilled chicken, and onions.");
    }
}

class VeggiePizza extends Pizza {
    public VeggiePizza() {
        this.name = "Veggie Pizza";
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding dough, tomato sauce, cheese, capsicum, olives, onions, and mushrooms.");
    }
}

abstract class PizzaStore {

    public Pizza orderPizza() {
        Pizza pizza = createPizza();

        System.out.println("\n--- Order started for " + pizza.getName() + " ---");
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        System.out.println("--- Order completed for " + pizza.getName() + " ---\n");

        return pizza;
    }

    protected abstract Pizza createPizza();
}

class MargheritaPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza() {
        return new MargheritaPizza();
    }
}

class ChickenPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza() {
        return new ChickenPizza();
    }
}

class VeggiePizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza() {
        return new VeggiePizza();
    }
}

public class FactoryMethodPizzaDemo {
    public static void main(String[] args) {
        PizzaStore margheritaStore = new MargheritaPizzaStore();
        Pizza margherita = margheritaStore.orderPizza();

        PizzaStore chickenStore = new ChickenPizzaStore();
        Pizza chicken = chickenStore.orderPizza();

        PizzaStore veggieStore = new VeggiePizzaStore();
        Pizza veggie = veggieStore.orderPizza();
    }
}
```

---

# Expected Output

This will print something like:

```text
--- Order started for Margherita Pizza ---
Preparing Margherita Pizza
Adding dough, tomato sauce, mozzarella cheese, and basil.
Margherita Pizza is baking for 20 minutes.
Margherita Pizza is being cut into slices.
Margherita Pizza is packed in the pizza box.
--- Order completed for Margherita Pizza ---

--- Order started for Chicken Pizza ---
Preparing Chicken Pizza
Adding dough, spicy sauce, cheese, grilled chicken, and onions.
Chicken Pizza is baking for 20 minutes.
Chicken Pizza is being cut into slices.
Chicken Pizza is packed in the pizza box.
--- Order completed for Chicken Pizza ---

--- Order started for Veggie Pizza ---
Preparing Veggie Pizza
Adding dough, tomato sauce, cheese, capsicum, olives, onions, and mushrooms.
Veggie Pizza is baking for 20 minutes.
Veggie Pizza is being cut into slices.
Veggie Pizza is packed in the pizza box.
--- Order completed for Veggie Pizza ---
```

---

# Deep Explanation Of Every Line And Concept

Now let us go deeper.

---

## `Pizza` is the Product

```java
abstract class Pizza
```

This is the parent type.

Why do we need it?

Because `PizzaStore` should work with **one common type**, not with many different classes.

That means this line becomes possible:

```java
Pizza pizza = createPizza();
```

No matter whether actual object is:

* `MargheritaPizza`
* `ChickenPizza`
* `VeggiePizza`

it can still be stored in `Pizza` reference.

This is called **polymorphism**.

---

## `MargheritaPizza`, `ChickenPizza`, `VeggiePizza` are Concrete Products

These are real classes.

They each define:

```java
public void prepare()
```

because preparation is different for each pizza.

That is realistic too:

* Margherita has basil and mozzarella
* Chicken pizza has chicken and spicy toppings
* Veggie pizza has vegetables

So their internal logic changes, but externally they all behave like `Pizza`.

---

## `PizzaStore` is the Creator

```java
abstract class PizzaStore
```

This is the class that defines the framework or flow.

It says:

> I know how an order works.

But it does **not** say:

> I always create MargheritaPizza.

That responsibility is left to child classes.

---

## `orderPizza()` is the business method

This method is not the factory method.

This is very important.

```java
public Pizza orderPizza()
```

This is the **main workflow**:

* create pizza
* prepare
* bake
* cut
* box

So this is business logic.

---

## `createPizza()` is the factory method

```java
protected abstract Pizza createPizza();
```

This method only does one thing:

> create and return the correct Pizza object

That is why it is called the factory method.

And because it is abstract, child classes must define it.

---

## Concrete Stores are Concrete Creators

```java
class ChickenPizzaStore extends PizzaStore
```

This child class says:

> when someone asks me to create pizza, I will return ChickenPizza.

Example:

```java
@Override
protected Pizza createPizza() {
    return new ChickenPizza();
}
```

This is the exact point where the pattern works.

---

# Why Is This Better Than Normal `if-else` Code?

Let us compare.

## Without Factory Method

```java
if (type.equals("margherita")) ...
else if (type.equals("chicken")) ...
else if (type.equals("veggie")) ...
```

Problems:

* class knows too many details
* old class changes again and again
* high coupling
* messy when new types come

---

## With Factory Method

Each store handles one creation logic.

Benefits:

* creator does not depend on many concrete classes
* easy to add new type
* common workflow stays same
* code looks cleaner
* follows design principles better

---

# Which SOLID Principles Are Used Here?

This is a very common interview topic.

## 1. Open/Closed Principle

A class should be **open for extension** but **closed for modification**.

What does it mean here?

If you want a new pizza like `PaneerPizza`, you do not need to change the old `PizzaStore` flow.

You can extend the system by adding:

* `PaneerPizza`
* `PaneerPizzaStore`

So the system grows by extension.

---

## 2. Single Responsibility Principle

`PizzaStore` handles order process.
Concrete pizza classes handle pizza details.
Concrete store classes handle creation choice.

Responsibility is more cleanly divided.

---

## 3. Dependency On Abstraction

The creator uses:

```java
Pizza pizza = createPizza();
```

It depends on `Pizza`, not directly on `ChickenPizza` or `MargheritaPizza`.

This is better design.

---

# Where Is Polymorphism Here?

Polymorphism is happening here:

```java
Pizza pizza = createPizza();
```

At runtime, `pizza` may become:

* `MargheritaPizza`
* `ChickenPizza`
* `VeggiePizza`

Same reference type, different actual objects.

That is runtime polymorphism.

---

# What Happens Internally Step By Step?

Suppose this code runs:

```java
PizzaStore store = new ChickenPizzaStore();
store.orderPizza();
```

Now the flow is:

1. `store` reference type is `PizzaStore`
2. actual object is `ChickenPizzaStore`
3. `orderPizza()` runs from parent class
4. inside `orderPizza()`, it calls `createPizza()`
5. since actual object is `ChickenPizzaStore`, overridden method runs
6. that method returns `new ChickenPizza()`
7. then `prepare()`, `bake()`, `cut()`, `box()` run on that pizza

So the parent flow is fixed, but object creation is dynamic.

That is the beauty of this pattern.

---

# Add One More Pizza Type To Prove Extensibility

Suppose tomorrow your manager says:

> add Paneer Pizza

With Factory Method, you can do this easily.

## New Product

```java
class PaneerPizza extends Pizza {
    public PaneerPizza() {
        this.name = "Paneer Pizza";
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name);
        System.out.println("Adding dough, tomato sauce, cheese, paneer cubes, onions, and capsicum.");
    }
}
```

## New Concrete Creator

```java
class PaneerPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza() {
        return new PaneerPizza();
    }
}
```

Done.

Old `PizzaStore` class remains unchanged.

That is a big win.

---

# Very Important Interview Point

## Factory Method does not always mean one separate class per pizza in real projects

In real code, sometimes people use configuration, registry, dependency injection, or enum-based creation.

But if interview asks specifically about **Factory Method Pattern**, then this subclass-based design is the classic answer.

---

# Factory Method vs Simple Factory

This is one of the most asked questions.

## Simple Factory

A single class creates objects using conditions.

Example:

```java
class PizzaFactory {
    public static Pizza createPizza(String type) {
        if (type.equals("margherita")) {
            return new MargheritaPizza();
        } else if (type.equals("chicken")) {
            return new ChickenPizza();
        } else if (type.equals("veggie")) {
            return new VeggiePizza();
        }
        throw new IllegalArgumentException("Invalid type");
    }
}
```

This is useful, but this is usually called **Simple Factory**.

---

## Factory Method

Here, parent class defines creation method, and child classes override it.

Example:

```java
abstract class PizzaStore {
    protected abstract Pizza createPizza();
}
```

and child class:

```java
class ChickenPizzaStore extends PizzaStore {
    protected Pizza createPizza() {
        return new ChickenPizza();
    }
}
```

So the main difference is:

### Simple Factory

centralized creation using conditions

### Factory Method

subclasses decide the creation

---

# Factory Method vs Abstract Factory

Another interview favorite.

## Factory Method

Creates **one product type** through a method.

In our example:

* one pizza object

## Abstract Factory

Creates **a family of related objects**.

Example in pizza world:

* pizza
* garlic bread
* cold drink
* dessert

If one factory creates a whole meal combo, that is more like Abstract Factory.

---

# Why Not Just Use Constructor Directly?

Interviewers ask this a lot.

Suppose inside `PizzaStore` you directly write:

```java
Pizza pizza = new ChickenPizza();
```

Then `PizzaStore` is now tightly linked to `ChickenPizza`.

Tomorrow if you want Margherita or Veggie, you must change that class.

That breaks flexibility.

Factory Method removes this direct dependency.

---

# Advantages Of Factory Method Pattern

## 1. Reduces tight coupling

Main logic does not directly depend on concrete classes.

## 2. Easy to extend

New pizza types can be added with new subclasses.

## 3. Clean separation

Creation logic is separated from business flow.

## 4. Supports polymorphism

Code works with common parent type.

## 5. Good for frameworks

The parent class defines flow, children customize creation.

---

# Disadvantages Of Factory Method Pattern

## 1. More classes

You may create many classes:

* product classes
* creator classes

## 2. More abstraction

For small problems, it may feel too much.

## 3. Harder for beginners

Because it uses inheritance and polymorphism together.

So you should use it when flexibility is actually needed.

---

# When Should You Use Factory Method?

Use it when:

* object type can vary
* you want to avoid big `if-else`
* business flow is same but object changes
* you want to follow good extensible design
* a framework or parent class should allow subclasses to decide object type

---

# When Should You Avoid It?

Avoid it when:

* only one object type exists
* there is no future extension
* adding many classes would be unnecessary
* a simple constructor is enough

Do not force this pattern everywhere.

That is also a sign of design maturity.

---

# Common Beginner Mistakes

## Mistake 1

Thinking any class with word “Factory” is Factory Method.

Not true.

---

## Mistake 2

Thinking `orderPizza()` is the factory method.

No.

In our example:

* `orderPizza()` = business method
* `createPizza()` = factory method

This is a very important distinction.

---

## Mistake 3

Putting all logic in one big factory class and calling it Factory Method.

That is usually Simple Factory, not Factory Method.

---

## Mistake 4

Not using abstraction

If you do not have a common parent like `Pizza`, then the design becomes weak.

---

## Mistake 5

Using pattern even when problem is tiny

That becomes overengineering.

---

# Most Important Interview Questions And Answers

Now let us do interview preparation.

---

## Q1. What is Factory Method Pattern?

**Answer:**

Factory Method Pattern is a creational design pattern in which a parent class defines a method for creating an object, and child classes decide which concrete object should be created.

---

## Q2. What problem does it solve?

**Answer:**

It solves the problem of tight coupling between business logic and object creation. It helps avoid direct use of concrete classes in the main code.

---

## Q3. In the pizza example, what is the factory method?

**Answer:**

`createPizza()` is the factory method.

---

## Q4. What is the creator class in the pizza example?

**Answer:**

`PizzaStore` is the creator class.

---

## Q5. What are the concrete creators?

**Answer:**

* `MargheritaPizzaStore`
* `ChickenPizzaStore`
* `VeggiePizzaStore`

---

## Q6. What is the product in the pizza example?

**Answer:**

`Pizza` is the product.

---

## Q7. What are the concrete products?

**Answer:**

* `MargheritaPizza`
* `ChickenPizza`
* `VeggiePizza`

---

## Q8. Why do we need Factory Method if constructors already exist?

**Answer:**

Constructors create concrete objects directly. Factory Method helps move object creation out of main business logic and makes the system easier to extend and maintain.

---

## Q9. How is Factory Method different from Simple Factory?

**Answer:**

Simple Factory uses one central method with conditions to create objects. Factory Method uses inheritance, where child classes override a factory method to decide the object.

---

## Q10. What are the main benefits?

**Answer:**

* low coupling
* better extensibility
* cleaner code
* supports polymorphism
* separates creation and usage

---

## Q11. What are the disadvantages?

**Answer:**

* more classes
* more complexity
* may be unnecessary for very small programs

---

## Q12. Which SOLID principles are supported?

**Answer:**

Mostly:

* Open/Closed Principle
* Single Responsibility Principle
* dependency on abstraction

---

## Q13. Can Factory Method return existing objects instead of new ones?

**Answer:**

Yes. It can return cached or reused objects too. It does not always have to create a brand new object.

---

## Q14. Is Factory Method based on inheritance or composition?

**Answer:**

Classic Factory Method is mainly based on inheritance.

---

## Q15. In our example, why is `PizzaStore` abstract?

**Answer:**

Because it defines common flow but does not know the exact pizza type. The exact creation is left to subclasses.

---

# How To Explain This Pattern In Interview In One Minute

You can say this:

> Factory Method Pattern is a creational design pattern where a base class defines a method for creating an object, but subclasses decide which concrete object to return. It helps separate object creation from business logic. In a pizza example, a `PizzaStore` defines `orderPizza()` for common steps like prepare, bake, cut, and box, while subclasses like `ChickenPizzaStore` or `MargheritaPizzaStore` override `createPizza()` to create the correct pizza type.

That is a very strong answer.

---

# Very Easy Memory Trick

Remember this:

## Creator says:

“I know the process.”

## Subclass says:

“I know the exact object.”

That is Factory Method.

---

# Tiny Comparison With Real Store

Think of `PizzaStore` as store manager.

The manager knows:

* take order
* process kitchen flow
* pack order

But the chef assigned may decide the actual pizza type.

That is why the flow is stable, but the object changes.

---

# Relationship With Template Method Pattern

This is a smart interview point.

Look at this method:

```java
public Pizza orderPizza() {
    Pizza pizza = createPizza();
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
    return pizza;
}
```

This is almost like **Template Method style** too, because the overall algorithm is fixed.

And inside it, one step is customizable:

```java
createPizza();
```

So sometimes Factory Method works together with Template Method idea.

This is a very nice advanced point to mention.

---

# One More Better Version With Type Argument

Sometimes interviewers may ask:

> Can one store create multiple pizza types?

Yes, but then the design changes slightly.

Example:

```java
abstract class PizzaStore {
    public Pizza orderPizza(String type) {
        Pizza pizza = createPizza(type);
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }

    protected abstract Pizza createPizza(String type);
}
```

And subclass may decide based on regional style.

Example:

```java
class IndianPizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        if (type.equalsIgnoreCase("margherita")) {
            return new MargheritaPizza();
        } else if (type.equalsIgnoreCase("chicken")) {
            return new ChickenPizza();
        } else if (type.equalsIgnoreCase("veggie")) {
            return new VeggiePizza();
        }
        throw new IllegalArgumentException("Unknown pizza type");
    }
}
```

This is also acceptable in many design discussions.

But for interview clarity, the earlier version is cleaner for understanding the classic pattern.

---

# Final Full Revision

Let us compress everything now.

## Factory Method Pattern means

A parent class defines a method for object creation, but child classes decide which actual object gets created.

## In pizza example

* `Pizza` = product
* `MargheritaPizza`, `ChickenPizza`, `VeggiePizza` = concrete products
* `PizzaStore` = creator
* `createPizza()` = factory method
* `MargheritaPizzaStore`, `ChickenPizzaStore`, `VeggiePizzaStore` = concrete creators
* `orderPizza()` = business workflow

## Main purpose

To separate:

* **what to do with object**
  from
* **how object is created**

---

# Best Short Summary To Remember Forever

Here is the cleanest summary:

**Factory Method Pattern is used when the overall process is same, but the exact object can change.
The parent class keeps the common flow, and subclasses choose the specific object.**

In pizza language:

**Ordering process is same.
Pizza type changes.**

That is the pattern.

---

If you want, I can teach you this next in an even more interview-ready way with:
**hand-drawn style UML + sequence diagram + real interview coding variation + factory method vs abstract factory using pizza restaurant example**
