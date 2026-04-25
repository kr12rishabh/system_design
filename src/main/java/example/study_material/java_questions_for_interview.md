# 1. different between arraylist vs linkedList

---
**Short interview version:**

`ArrayList` is backed by a dynamic array, so it provides fast random access and is generally better for read-heavy use cases. `LinkedList` is backed by a doubly linked list, so inserting or deleting elements in the middle can be easier if you already have the node reference, but accessing elements by index is slower. In most real backend cases, `ArrayList` is used more often because it is usually faster and more memory-efficient.

**Detailed explanation:**

The main difference is in the internal data structure.

`ArrayList` stores elements in a resizable array. Because of that, if I want to get an element using an index like `get(5)`, it is very fast. That is why `ArrayList` is preferred when we do frequent reads.

`LinkedList` stores elements as nodes, where each node points to the previous and next node. So it does not support fast random access. To reach index 5, it has to traverse node by node.

In interview terms, the comparison is usually like this:

Access by index:
`ArrayList` is fast.
`LinkedList` is slow.

Insertion at end:
`ArrayList` is usually fast, but sometimes resizing may happen.
`LinkedList` can also add easily at the ends.

Insertion or deletion in middle:
`ArrayList` may need to shift elements, so it is costly.
`LinkedList` can do it more efficiently if the position is already known, but finding that position is still slow.

Memory:
`ArrayList` uses less memory.
`LinkedList` uses more memory because each node stores data plus links.

**When to use which:**

Use `ArrayList` when:
you need frequent reads,
you access elements by index,
and inserts/deletes in the middle are not very frequent.

Use `LinkedList` when:
you have frequent insertions/deletions and
you are already working with positions through iterators or node traversal.

**Practical interview point:**

In real-world Java backend development, `ArrayList` is used far more often than `LinkedList`. Even for many insert/delete scenarios, `ArrayList` often performs better overall because of better cache locality and lower memory overhead.

**One strong line you can say in interview:**

Although `LinkedList` looks better theoretically for insertion and deletion, in practical applications `ArrayList` is usually preferred because random access is fast and overall performance is often better.

Send the next question exactly as asked.

---
# 2.  Tell Hashset internal working??

---

**Short interview version:**

`HashSet` internally uses a `HashMap`. It stores only unique elements, and each element added to the `HashSet` becomes a key in the internal `HashMap`, while a dummy constant object is used as the value. When we add an element, Java calculates its hash, finds the bucket, and then uses `equals()` to check for duplicates. That is why correct implementation of `hashCode()` and `equals()` is very important for `HashSet`.

**Detailed explanation:**

The most important point is this:

`HashSet` is built on top of `HashMap`.

Internally, when you write:

```java
HashSet<String> set = new HashSet<>();
set.add("Java");
```

it actually stores `"Java"` as a key inside an internal `HashMap`, and the value is just a dummy object.

Conceptually it works like this:

```java
private transient HashMap<E, Object> map;
private static final Object PRESENT = new Object();
```

So when we do:

```java
set.add("Java");
```

internally it becomes something like:

```java
map.put("Java", PRESENT);
```

## How add() works

When an element is added:

1. `hashCode()` of the object is calculated.
2. Based on that hash, Java finds the correct bucket.
3. In that bucket, it checks existing elements using `equals()`.
4. If the same element already exists, insertion is ignored.
5. If not, it is added.

That is why `HashSet` stores only unique values.

## Why hashCode() and equals() matter

For duplicate detection:

* `hashCode()` helps find the bucket quickly.
* `equals()` confirms whether two objects are actually equal.

If these are not implemented properly in a custom class, `HashSet` may allow logical duplicates or behave unexpectedly.

Example:

```java
class User {
    int id;
    String name;
}
```

If `equals()` and `hashCode()` are not overridden, two `User` objects with same data may still be treated as different.

## Performance

Average time complexity for:

* `add()` → O(1)
* `remove()` → O(1)
* `contains()` → O(1)

In worst case, it can degrade, but normally it is very fast.

## Null handling

`HashSet` allows only one `null` value, because duplicates are not allowed.

## Ordering

`HashSet` does **not maintain insertion order** and does **not sort elements**.

If interviewer asks follow-up:

* insertion order → use `LinkedHashSet`
* sorted order → use `TreeSet`

**One good interview line:**

`HashSet` achieves uniqueness by using a `HashMap` internally, where set elements are stored as keys, and duplicate detection depends on both `hashCode()` and `equals()`.


---

# 3. ArrayList vs LinkedList

---

**Short interview version:**

`ArrayList` is backed by a dynamic array, so it is better for fast reads and index-based access. `LinkedList` is backed by a doubly linked list, so insertions and deletions can be easier when the position is already known, but random access is slow. In most backend applications, `ArrayList` is preferred because it is faster in practice and uses less memory.

**One-line answer:**

Use `ArrayList` for fast access and general use, and use `LinkedList` only when frequent insertions or deletions are needed at known positions.

**Explanation:**

The main difference is their internal data structure.

`ArrayList` uses a resizable array.
So if I want to access an element by index, like `list.get(5)`, it is very fast.

`LinkedList` uses nodes connected with previous and next pointers.
So to reach index 5, it has to move node by node, which makes random access slower.

**Key differences:**

**1. Internal structure**

* `ArrayList` → dynamic array
* `LinkedList` → doubly linked list

**2. Access by index**

* `ArrayList` → fast, O(1)
* `LinkedList` → slow, O(n)

**3. Insertion/deletion in middle**

* `ArrayList` → slower because elements may need shifting
* `LinkedList` → better if node position is already known

**4. Memory**

* `ArrayList` → less memory
* `LinkedList` → more memory because each node stores links also

**5. Real-world usage**

* `ArrayList` is used much more in real Java backend projects
* `LinkedList` is less common unless there is a specific queue or frequent node-based manipulation use case

**Best interview line:**

Although `LinkedList` looks better for insertion and deletion theoretically, in real applications `ArrayList` is usually preferred because it gives better overall performance and better cache locality.

**Code example:**

```java
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("A");
        arrayList.add("B");
        arrayList.add("C");

        List<String> linkedList = new LinkedList<>();
        linkedList.add("A");
        linkedList.add("B");
        linkedList.add("C");

        System.out.println(arrayList.get(1));   // fast in ArrayList
        System.out.println(linkedList.get(1));  // slower in LinkedList
    }
}
```

**Interviewer follow-up answer:**

In most cases, I would choose `ArrayList` by default. I would use `LinkedList` only when I have a very specific requirement for frequent insertions and deletions at known positions.

---

# 4. equals() and hashCode()

---
**Short interview version:**

`equals()` and `hashCode()` are used to define object equality in Java. `equals()` checks whether two objects are logically equal, and `hashCode()` returns an integer hash value used in hash-based collections like `HashMap`, `HashSet`, and `Hashtable`. If two objects are equal according to `equals()`, they must return the same `hashCode()`. That is why both should be overridden together.

**One-line answer:**

`equals()` decides logical equality, and `hashCode()` helps Java place and find objects efficiently in hash-based collections.

**Explanation:**

By default, both methods come from the `Object` class.

* `equals()` by default checks **reference equality**
* `hashCode()` by default gives a hash based on the object's memory-related identity

So if we create two different objects with the same data, Java will still treat them as different unless we override these methods.

Example:

```java
new User(1, "Rishabh")
new User(1, "Rishabh")
```

These may look same logically, but without overriding `equals()` and `hashCode()`, Java treats them as different objects.

### Why both are needed together

This is the core interview point:

If two objects are equal using `equals()`, then their `hashCode()` **must** be the same.

Because in `HashMap` or `HashSet`:

1. Java first uses `hashCode()` to find the bucket
2. Then it uses `equals()` to compare actual objects inside that bucket

So:

* `hashCode()` is for fast lookup
* `equals()` is for exact comparison

### Important contract

1. If `a.equals(b)` is `true`, then `a.hashCode() == b.hashCode()` must also be true
2. If hash codes are same, objects may or may not be equal
3. If `equals()` is overridden, `hashCode()` should also be overridden

### Real issue if only equals() is overridden

If only `equals()` is overridden and `hashCode()` is not, then hash-based collections may behave incorrectly.

For example, a `HashSet` may store duplicate logical objects because they go into different buckets.

### Where this matters

This is especially important in:

* `HashMap`
* `HashSet`
* `ConcurrentHashMap`

### Good interview line

> `hashCode()` improves performance by narrowing the search space, and `equals()` confirms logical equality. In hash-based collections, both work together.

**Code example:**

```java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class User {
    private int id;
    private String name;

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}

public class Main {
    public static void main(String[] args) {
        Set<User> set = new HashSet<>();

        set.add(new User(1, "Rishabh"));
        set.add(new User(1, "Rishabh"));

        System.out.println(set.size()); // 1
    }
}
```

**Best spoken answer in interview:**

`equals()` is used for logical equality, while `hashCode()` is used by hash-based collections for efficient storage and retrieval. If two objects are equal, they must have the same hash code, so whenever we override `equals()`, we should override `hashCode()` as well.



---
# 5. Why is String immutable?

---

**Short interview version:**

`String` is immutable in Java to improve security, enable string constant pool optimization, make hashing efficient, and ensure thread safety. Because the value cannot change after creation, Java can safely reuse string objects, cache hash codes, and use strings reliably in sensitive places like class loading, file paths, URLs, and database connections.

**One-line answer:**

`String` is immutable so that Java can make it secure, thread-safe, memory-efficient, and reliable for hashing and pooling.

**Explanation:**

In Java, once a `String` object is created, its value cannot be changed.
If we modify it, Java actually creates a new object instead of changing the old one.

Example:

```java
String s = "Java";
s = s.concat(" Backend");
```

Here, the original `"Java"` string is not changed. A new string is created.

### Why Java made String immutable

**1. Security**
Strings are used in many sensitive areas like:

* database URL
* username/password
* file path
* network socket connection
* class loading

If `String` were mutable, one reference could change the value after validation, which could create security problems.

Example:
A file path is checked first, then later changed by another reference. That would be dangerous.

**2. String Constant Pool**
Java stores string literals in the string pool to save memory.

Example:

```java
String a = "hello";
String b = "hello";
```

Both can point to the same object.

This sharing is only safe because strings are immutable.
If one reference changed the value, all others would get affected.

**3. Hash code caching**
Strings are heavily used as keys in `HashMap`, `HashSet`, etc.

Since string value never changes, its `hashCode()` also never changes.
So Java can cache the hash code, which improves performance.

If `String` were mutable, changing the value after inserting into a `HashMap` would break lookup.

**4. Thread safety**
Because strings cannot be modified, they are naturally thread-safe.
Multiple threads can use the same string object without synchronization.

**5. Class loading and JVM reliability**
Strings are used in class names, method names, URLs, and many internal JVM operations.
Immutability makes them stable and predictable.

### Best interview line

> Java made `String` immutable because it is widely used across the JVM, and immutability gives security, thread safety, string pooling, and reliable hashing.

**Code example:**

```java
public class Main {
    public static void main(String[] args) {
        String s1 = "Java";
        String s2 = s1;

        s1 = s1.concat(" Backend");

        System.out.println(s1); // Java Backend
        System.out.println(s2); // Java
    }
}
```

**What this shows:**

* `s1` gets a new object
* original string remains unchanged
* that proves immutability

**Interviewer follow-up answer:**

If interviewer asks, “Can we make mutable strings in Java?”
You can say:

Yes, Java provides `StringBuilder` and `StringBuffer` for mutable string operations.
`StringBuilder` is not thread-safe and faster.
`StringBuffer` is thread-safe and synchronized.


---

# 6. Abstraction vs encapsulation?


---



**Short interview version:**

`Abstraction` means hiding implementation details and showing only essential behavior to the user. `Encapsulation` means wrapping data and methods together in one class and restricting direct access to the internal state. In simple words, abstraction focuses on **what an object does**, while encapsulation focuses on **how data is protected and controlled**.

**One-line answer:**

Abstraction hides complexity, while encapsulation hides data and protects it from direct unauthorized access.

**Explanation:**

These two are related, but they solve different problems.

### Abstraction

Abstraction is about exposing only the required functionality and hiding internal implementation.

For example:
when we use a `car.start()` method, we only know that the car starts.
We do not care how fuel injection, battery, and engine work internally.

In Java, abstraction is mainly achieved using:

* abstract classes
* interfaces

So abstraction reduces complexity for the user.

### Encapsulation

Encapsulation is about binding data and methods together inside a class and controlling access to that data.

For example:
if a class has a private variable `balance`, we do not allow direct access like `account.balance = -1000`.
Instead, we provide controlled methods like `deposit()` and `withdraw()`.

In Java, encapsulation is achieved using:

* private fields
* public getters/setters
* controlled methods

So encapsulation protects data integrity.

### Main difference

* **Abstraction** → hides implementation details
* **Encapsulation** → hides internal data and controls access

### Real interview line

> Abstraction tells the user only what is necessary, while encapsulation protects the object’s internal state by restricting direct access.

**Code example:**

```java
interface PaymentService {
    void pay(double amount); // abstraction
}

class CreditCardPayment implements PaymentService {
    private String cardNumber; // encapsulation

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Payment done: " + amount);
    }

    public String getMaskedCardNumber() {
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }
}
```

### How to explain this code

* `PaymentService` shows only `pay()` method, not internal payment logic → **abstraction**
* `cardNumber` is private and accessed in controlled way → **encapsulation**

**Best spoken answer:**

Abstraction is used to hide implementation complexity and expose only essential features, whereas encapsulation is used to protect data by keeping fields private and allowing controlled access through methods. So abstraction focuses on behavior, and encapsulation focuses on data hiding and protection.

---
# 7. Interface vs abstract class



---
**Short interview version:**

An `interface` is used to define a contract that multiple classes can implement, while an `abstract class` is used when we want to provide a common base class with both abstract and concrete behavior. An interface supports multiple inheritance of type, but an abstract class supports partial implementation and shared state. In modern Java, interfaces can also have `default` and `static` methods, but still they are mainly for capability contracts, whereas abstract classes are for code reuse and common hierarchy.

**One-line answer:**

Use an `interface` for defining behavior contracts, and use an `abstract class` when you want shared code, common state, and partial implementation.

**Explanation:**

Both are used for abstraction, but their purpose is different.

### Interface

An interface defines **what a class should do**.

It is best when different unrelated classes need to follow the same contract.

Example:
`PaymentService`, `NotificationService`, `Runnable`

A class can implement multiple interfaces.

So interface is good for:

* loose coupling
* contract-based design
* multiple inheritance of behavior

### Abstract class

An abstract class defines **what a class is**, and it can also provide some common implementation.

It is best when multiple related classes share common code, fields, or behavior.

A class can extend only one abstract class.

So abstract class is good for:

* code reuse
* common base functionality
* shared fields and constructor logic

### Key differences

**1. Methods**

* Interface can have abstract methods, default methods, and static methods
* Abstract class can have both abstract and concrete methods

**2. Variables**

* Interface fields are by default `public static final`
* Abstract class can have instance variables

**3. Constructors**

* Interface cannot have constructors
* Abstract class can have constructors

**4. Inheritance**

* A class can implement multiple interfaces
* A class can extend only one abstract class

**5. Use case**

* Interface → contract
* Abstract class → common base with shared behavior

### Best interview line

> If I only want to define a capability or contract, I use an interface. If I want to share common code and state among related classes, I use an abstract class.

**Code example:**

```java
interface PaymentService {
    void pay(double amount);

    default void printReceipt() {
        System.out.println("Receipt generated");
    }
}

abstract class BankPayment {
    protected String bankName;

    public BankPayment(String bankName) {
        this.bankName = bankName;
    }

    public void validate() {
        System.out.println("Validating payment from " + bankName);
    }

    abstract void processPayment(double amount);
}

class UpiPayment extends BankPayment implements PaymentService {

    public UpiPayment(String bankName) {
        super(bankName);
    }

    @Override
    public void pay(double amount) {
        System.out.println("UPI payment done: " + amount);
    }

    @Override
    void processPayment(double amount) {
        System.out.println("Processing bank payment: " + amount);
    }
}
```

### How to explain this in interview

* `PaymentService` defines the contract → interface
* `BankPayment` provides common field and shared logic → abstract class
* `UpiPayment` uses both

**Best spoken answer:**

Interface is used when we want to define a contract that can be implemented by different classes, including unrelated ones. Abstract class is used when we want a common parent with shared state and partial implementation for closely related classes. So interface is for behavior contract, and abstract class is for common base design.

---

# 8. Overloading vs overriding

---
**Short interview version:**

`Overloading` means defining multiple methods with the same name in the same class but with different parameter lists. `Overriding` means redefining a parent class method in the child class with the same method signature to provide specific runtime behavior. In simple words, overloading is **compile-time polymorphism**, while overriding is **runtime polymorphism**.

**One-line answer:**

Overloading changes the method parameters, while overriding changes the method implementation in the child class.

**Explanation:**

### Overloading

Overloading happens in the **same class** when multiple methods have the same name but different parameters.

The difference can be in:

* number of parameters
* type of parameters
* order of parameters

Return type alone cannot overload a method.

Example:

```java
add(int a, int b)
add(int a, int b, int c)
add(double a, double b)
```

This is called **compile-time polymorphism** because the compiler decides which method to call based on the arguments.

---

### Overriding

Overriding happens when a **child class** provides its own implementation of a method already present in the parent class.

For overriding:

* method name must be same
* parameters must be same
* return type should be same or covariant
* access modifier cannot be more restrictive

This is called **runtime polymorphism** because at runtime Java decides which method implementation to execute.

---

### Main differences

**1. Where it happens**

* Overloading → same class
* Overriding → parent-child classes

**2. Parameters**

* Overloading → must be different
* Overriding → must be same

**3. Polymorphism type**

* Overloading → compile-time
* Overriding → runtime

**4. Inheritance**

* Overloading → not required
* Overriding → required

**5. Purpose**

* Overloading → same behavior name, different inputs
* Overriding → same method, different implementation

---

### Best interview line

> Overloading gives multiple ways to call a method, while overriding gives specialized behavior in the subclass.

**Code example:**

```java
class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {   // overloading
        return a + b + c;
    }
}

class Animal {
    void sound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    @Override
    void sound() {   // overriding
        System.out.println("Dog barks");
    }
}

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        System.out.println(calc.add(10, 20));
        System.out.println(calc.add(10, 20, 30));

        Animal a = new Dog();
        a.sound();
    }
}
```

**Best spoken answer:**

Overloading means same method name with different parameter lists in the same class, and it is resolved at compile time. Overriding means a child class provides a new implementation of a parent method with the same signature, and it is resolved at runtime.



---

# 9. Checked vs unchecked exceptions

---

**Short interview version:**

`Checked exceptions` are checked at compile time, so the compiler forces us to either handle them using `try-catch` or declare them with `throws`. `Unchecked exceptions` are not checked at compile time and happen mainly due to programming mistakes like null access, wrong index, or invalid logic. In Java, checked exceptions extend `Exception` except `RuntimeException`, while unchecked exceptions extend `RuntimeException`.

**One-line answer:**

Checked exceptions must be handled at compile time, while unchecked exceptions are not forced by the compiler and usually indicate programming errors.

**Explanation:**

### Checked exceptions

These are exceptions that the compiler checks during compilation.

If a method can throw a checked exception, we must do one of these:

* handle it using `try-catch`
* declare it using `throws`

Examples:

* `IOException`
* `SQLException`
* `FileNotFoundException`

These usually represent external problems like:

* file not found
* database issue
* network issue

So checked exceptions are mostly recoverable or expected situations.

---

### Unchecked exceptions

These are not checked by the compiler.

The compiler does not force us to handle them.

They usually happen because of coding mistakes or invalid logic.

Examples:

* `NullPointerException`
* `ArithmeticException`
* `ArrayIndexOutOfBoundsException`
* `IllegalArgumentException`

These usually mean:

* code bug
* invalid input handling
* wrong assumptions in logic

They occur at runtime.

---

### Main differences

**1. Compile-time checking**

* Checked → compiler checks
* Unchecked → compiler does not check

**2. Handling**

* Checked → must handle or declare
* Unchecked → optional to handle

**3. Class hierarchy**

* Checked → subclasses of `Exception` excluding `RuntimeException`
* Unchecked → subclasses of `RuntimeException`

**4. Cause**

* Checked → external/recoverable conditions
* Unchecked → programming bugs or bad logic

---

### Best interview line

> Checked exceptions are used for recoverable conditions that the caller should be aware of, while unchecked exceptions represent programming issues that should usually be fixed in code rather than just caught.

**Code example:**

```java
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    public static void readFile() throws FileNotFoundException { // checked
        FileInputStream fis = new FileInputStream("data.txt");
    }

    public static void main(String[] args) {
        try {
            readFile();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        String name = null;
        System.out.println(name.length()); // unchecked -> NullPointerException
    }
}
```

**Best spoken answer:**

Checked exceptions are validated by the compiler, so we must handle or declare them. They usually represent recoverable external issues like file or database failures. Unchecked exceptions are not enforced by the compiler and generally occur because of programming mistakes like null access or invalid indexing.



---

# 10. synchronized vs Lock

---

**Short interview version:**

`synchronized` is a built-in Java keyword used for basic intrinsic locking, while `Lock` is part of `java.util.concurrent.locks` and provides more advanced features like `tryLock()`, timed lock wait, interruptible locking, and fair locking. If I need simple thread safety, I use `synchronized`. If I need more control over locking behavior, I use `Lock`, especially `ReentrantLock`.

**One-line answer:**

`synchronized` is simpler and automatic, while `Lock` is more flexible and gives finer control in concurrent programming.

**Explanation:**

Both are used to achieve thread safety and prevent multiple threads from accessing critical code at the same time, but they differ in flexibility and control.

### 1. `synchronized`

`synchronized` is a Java keyword.

When a thread enters a synchronized block or method, it acquires the monitor lock of that object, and other threads must wait until the lock is released.

Main points:

* simple to use
* lock is released automatically when block exits
* less error-prone for simple cases
* no explicit unlock needed

Example use cases:

* simple shared counter
* thread-safe method
* small critical section

---

### 2. `Lock`

`Lock` is an interface from `java.util.concurrent.locks`.

Most commonly we use `ReentrantLock`.

It gives more advanced control than `synchronized`.

Features:

* `lock()` and `unlock()`
* `tryLock()` to avoid waiting forever
* `tryLock(time, unit)` for timeout
* `lockInterruptibly()` to allow interruption while waiting
* optional fair locking

This is useful in complex concurrent systems.

---

### Main differences

**1. Type**

* `synchronized` → keyword
* `Lock` → interface / API

**2. Lock release**

* `synchronized` → automatic
* `Lock` → manual using `unlock()`

**3. Flexibility**

* `synchronized` → basic locking
* `Lock` → advanced features like timeout, interrupt, fairness

**4. Error-proneness**

* `synchronized` → safer because unlock is automatic
* `Lock` → more control, but developer must remember `unlock()` in `finally`

**5. Use case**

* `synchronized` → simple thread safety
* `Lock` → complex concurrency handling

---

### Best interview line

> I use `synchronized` for simple mutual exclusion, but I prefer `Lock` when I need advanced concurrency control like timed waiting, interruptible locking, or fair scheduling.

**Code:**

```java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class CounterWithSynchronized {
    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public synchronized int getCount() {
        return count;
    }
}

class CounterWithLock {
    private int count = 0;
    private final Lock lock = new ReentrantLock();

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

**Best spoken answer:**

`synchronized` is the simpler built-in locking mechanism in Java and is good for basic thread-safe blocks or methods. `Lock` gives more control and supports features like `tryLock`, interruptible lock acquisition, and fairness, so it is preferred in more advanced concurrent applications.



---

# 11. ExecutorService and thread pools

---

**Short interview version:**

`ExecutorService` is a Java concurrency framework component used to manage and execute asynchronous tasks without creating threads manually. A thread pool is a group of reusable worker threads managed by `ExecutorService`. Instead of creating a new thread for every task, we submit tasks to the pool, and the pool reuses existing threads, which improves performance, controls resource usage, and makes concurrent programming easier.

**One-line answer:**

`ExecutorService` helps us manage tasks using reusable thread pools, so we avoid manual thread creation and get better performance and control.

**Explanation:**

In Java, creating threads manually using `new Thread()` for every task is not a good approach in real backend systems because:

* thread creation is expensive
* too many threads can exhaust CPU and memory
* thread lifecycle management becomes difficult

So Java provides `ExecutorService`.

### What is ExecutorService

`ExecutorService` is an interface in `java.util.concurrent` that helps us:

* submit tasks
* manage worker threads
* control thread lifecycle
* return results from async tasks
* shut down execution properly

Instead of saying:
create a new thread for every task

we say:
submit the task to the executor

and it decides which pooled thread will execute it.

---

### What is a thread pool

A thread pool is a collection of pre-created reusable threads.

When tasks come in:

* an available thread picks the task
* after finishing, that thread goes back to the pool
* the same thread can execute another task later

This reuse is the main advantage.

---

### Why thread pools are used

**1. Better performance**
Creating threads again and again is costly. Reusing threads is faster.

**2. Resource control**
We can limit how many threads run at once, which prevents system overload.

**3. Easier task management**
We can submit `Runnable` or `Callable` tasks without handling thread creation ourselves.

**4. Scalability**
Very useful in backend applications like:

* request processing
* async email sending
* background jobs
* batch processing
* parallel API calls

---

### Common methods

* `submit()` → submits task and can return `Future`
* `execute()` → submits task but does not return result
* `shutdown()` → stops accepting new tasks, finishes old ones
* `shutdownNow()` → tries to stop immediately

---

### Common thread pool types

#### 1. Fixed thread pool

```java
Executors.newFixedThreadPool(n)
```

* fixed number of threads
* good when we want controlled concurrency

#### 2. Cached thread pool

```java
Executors.newCachedThreadPool()
```

* creates threads as needed
* reuses idle threads
* can grow a lot, so needs care

#### 3. Single thread executor

```java
Executors.newSingleThreadExecutor()
```

* only one thread
* tasks execute sequentially

#### 4. Scheduled thread pool

```java
Executors.newScheduledThreadPool(n)
```

* used for delayed or periodic tasks

---

### Runnable vs Callable

* `Runnable` → no return value
* `Callable` → returns a value and can throw checked exception

---

### Best interview line

> In backend systems, `ExecutorService` is preferred over manual thread creation because it reuses threads through a pool, improves performance, controls concurrency, and makes async task handling cleaner and safer.

**Code:**

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        Runnable task1 = () -> {
            System.out.println("Runnable task executed by: " + Thread.currentThread().getName());
        };

        Callable<String> task2 = () -> {
            return "Callable task executed by: " + Thread.currentThread().getName();
        };

        executor.execute(task1);

        Future<String> future = executor.submit(task2);
        System.out.println(future.get());

        executor.shutdown();
    }
}
```

**How to explain this code in interview:**

Here I created a fixed thread pool of size 3.
Then I submitted one `Runnable` task and one `Callable` task.
The executor assigned pooled threads to run them.
For the `Callable`, I got the result using `Future`.
Finally, I called `shutdown()` to release resources properly.

**Best spoken answer:**

`ExecutorService` is used to manage asynchronous task execution through thread pools. A thread pool reuses a fixed or dynamic set of threads instead of creating new threads for every task. This improves performance, limits resource usage, and is the standard way to handle concurrency in Java backend applications.


---

# 12. map() vs flatMap()

---
**Short interview version:**

`map()` is used when one input element is transformed into exactly one output element. `flatMap()` is used when one input element can produce multiple output elements, and we want to flatten them into a single stream. In simple words, `map()` gives **one-to-one transformation**, while `flatMap()` gives **one-to-many transformation and then flattens the result**.

**One-line answer:**

Use `map()` for simple element transformation, and use `flatMap()` when each element returns a collection/stream and you want a single flattened output.

**Explanation:**

This is mostly asked with Java Streams.

## `map()`

`map()` transforms each element into another element.

Example:
If I have a list of names and I want their lengths:

```java
["Java", "Spring"]  ->  [4, 6]
```

Here:

* one input gives one output
* so `map()` is the correct choice

---

## `flatMap()`

`flatMap()` is used when each element itself produces multiple elements, usually a `Stream`, `List`, or another collection, and then all results are flattened into one single stream.

Example:
If I have:

```java
[
  ["Java", "Spring"],
  ["Kafka", "Redis"]
]
```

and I want:

```java
["Java", "Spring", "Kafka", "Redis"]
```

then I use `flatMap()`.

So:

* one input can give many outputs
* and `flatMap()` merges them into one stream

---

## Main difference

### `map()`

* one element becomes one transformed element
* output remains nested if mapping returns collections

### `flatMap()`

* one element can become multiple elements
* nested structure gets flattened

---

## Best interview line

> `map()` changes the shape of each element, while `flatMap()` first expands each element into multiple values and then combines everything into a single flat stream.

---

## Practical backend example

Suppose I have a list of users, and each user has multiple roles.

* If I want user names → `map()`
* If I want all roles of all users in one list → `flatMap()`

That is a very practical interview example.

---

**Code:**

```java
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MapVsFlatMapDemo {
    public static void main(String[] args) {

        List<String> names = Arrays.asList("Java", "Spring", "Kafka");

        // map() -> one-to-one transformation
        List<Integer> lengths = names.stream()
                .map(String::length)
                .collect(Collectors.toList());

        System.out.println(lengths); // [4, 6, 5]

        List<List<String>> nested = Arrays.asList(
                Arrays.asList("Java", "Spring"),
                Arrays.asList("Kafka", "Redis")
        );

        // flatMap() -> flatten nested structure
        List<String> flatList = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println(flatList); // [Java, Spring, Kafka, Redis]
    }
}
```

**Best spoken answer:**

`map()` is used for one-to-one transformation, like converting a string to its length. `flatMap()` is used when each element produces multiple elements, like a list of lists, and we want one flat stream. So `map()` transforms, while `flatMap()` transforms and flattens.



---

# 13. Optional usage

---

**Short interview version:**

`Optional` is a container object in Java used to represent the presence or absence of a value. It helps avoid `NullPointerException` and makes null handling more explicit and readable. We should mainly use `Optional` as a return type when a value may or may not be present, but we should generally avoid using it for fields, method parameters, or in entity classes.

**One-line answer:**

`Optional` is used to handle possible null values safely and clearly, mainly as a method return type.

**Explanation:**

`Optional` was introduced in Java 8.

Its main purpose is to avoid writing too many null checks like:

```java
if (user != null && user.getEmail() != null)
```

Instead, `Optional` forces the developer to think properly about missing values.

### Why use Optional

It helps in:

* avoiding `NullPointerException`
* making code more readable
* expressing clearly that a value may be absent
* reducing manual null checks

### Common methods of Optional

**1. `of()`**
Used when value is definitely not null.

```java
Optional<String> name = Optional.of("Rishabh");
```

If null is passed here, it throws exception.

---

**2. `ofNullable()`**
Used when value may be null.

```java
Optional<String> name = Optional.ofNullable(getName());
```

---

**3. `empty()`**
Represents no value.

```java
Optional<String> empty = Optional.empty();
```

---

**4. `isPresent()` and `get()`**
Checks whether value exists and then gets it.

```java
if (opt.isPresent()) {
    System.out.println(opt.get());
}
```

But in interview, say that using `get()` too much is not ideal.

---

**5. `orElse()`**
Returns default value if value is absent.

```java
String name = opt.orElse("Default Name");
```

---

**6. `orElseGet()`**
Returns value from supplier if absent.

```java
String name = opt.orElseGet(() -> "Generated Name");
```

---

**7. `orElseThrow()`**
Throws exception if value is not present.

```java
User user = opt.orElseThrow(() -> new RuntimeException("User not found"));
```

This is very common in backend code.

---

**8. `map()`**
Transforms value if present.

```java
String email = opt.map(User::getEmail).orElse("No Email");
```

---

### Best use case in backend

Very common in repository layer:

```java
Optional<User> user = userRepository.findById(id);
```

Then in service layer:

```java
User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));
```

This is a very standard Java Spring Boot interview answer.

### Where not to use Optional

This is an important interview point.

Avoid using `Optional`:

* as entity field
* as DTO field
* as method parameter
* in serialization-heavy models

Because it can make code awkward and is not intended for that primary use.

### Best interview line

> `Optional` should be used to model absent return values clearly, not as a replacement for every null in the application.

**Code:**

```java
import java.util.Optional;

class User {
    private String email;

    public User(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}

public class OptionalDemo {
    public static void main(String[] args) {
        Optional<User> userOpt = Optional.ofNullable(new User("rishabh@example.com"));

        String email = userOpt
                .map(User::getEmail)
                .orElse("Email not available");

        System.out.println(email);

        Optional<User> emptyUser = Optional.empty();

        User user = emptyUser.orElseThrow(() -> new RuntimeException("User not found"));
    }
}
```

**Best spoken answer:**

`Optional` is used to represent a value that may or may not be present. It improves null handling and makes the code more expressive. In backend development, it is mostly used as a return type, especially in repository methods, and then handled using methods like `orElse`, `orElseThrow`, and `map`.


---
# 15. What happens when static method is called via parent reference?


---

**Short interview version:**

Static methods are resolved at **compile time**, not at runtime, so they are **not overridden**, they are only **hidden**. If a static method is called using a parent class reference, the method that gets called depends on the **reference type**, not the actual object type. So even if the reference points to a child object, the parent class static method is called.

**One-line answer:**

When a static method is called via a parent reference, Java calls the method based on the reference type, because static methods use compile-time binding.

**Explanation:**

This is a very common Java interview concept.

Static methods belong to the **class**, not to the object.

So Java does not do dynamic dispatch for static methods like it does for instance methods.

Example:

```java
Parent p = new Child();
p.show();
```

If `show()` is static in both classes, then Java will call:

```java
Parent.show();
```

not `Child.show()`.

Why?

Because static methods are resolved by the **type of reference** at compile time.

---

### Important interview point

* **Instance methods** → runtime polymorphism
* **Static methods** → compile-time binding

So static methods do not participate in overriding.

They are **method hiding**, not overriding.

---

### Code example

```java
class Parent {
    static void show() {
        System.out.println("Parent static method");
    }
}

class Child extends Parent {
    static void show() {
        System.out.println("Child static method");
    }
}

public class Main {
    public static void main(String[] args) {
        Parent p = new Child();
        p.show();
    }
}
```

**Output:**

```java
Parent static method
```

---

### Why this happens

Because `p` is of type `Parent`, and static method resolution happens at compile time using the reference type.

If this were a normal instance method, then Java would call the child version at runtime.

---

### Best interview line

> Static methods are class-level methods, so they are resolved using the reference type at compile time. That is why calling a static method through a parent reference invokes the parent version, even if the object is of the child class.

### Follow-up difference you can say

If interviewer asks why normal methods behave differently:

You can say:

> Normal instance methods are overridden and resolved at runtime based on the actual object, but static methods are hidden and resolved at compile time based on the reference type.


---

# 16. How would you sort employee objects by multiple fields?

---
**Short interview version:**

I would sort employee objects by multiple fields using a `Comparator` chain, usually with `Comparator.comparing()` and `thenComparing()`. For example, I can first sort by department, then by salary, and then by name. This is the cleanest and most readable approach in modern Java, especially with streams or `Collections.sort()`.

**One-line answer:**

Use `Comparator.comparing(...).thenComparing(...)` to sort employee objects by multiple fields in priority order.

**Explanation:**

In Java, when we need multi-field sorting, we define the priority of fields.

For example:

* first by `department`
* then by `salary`
* then by `name`

This means:

* Java first compares department
* if department is same, then it compares salary
* if salary is also same, then it compares name

This is the standard interview approach.

### Best way in modern Java

Use:

* `Comparator.comparing()`
* `thenComparing()`
* `reversed()` if needed
* `nullsFirst()` or `nullsLast()` if null values are possible

### Example use cases

* sort by salary ascending, then name ascending
* sort by department ascending, then age descending
* sort by joining date, then employee id

### Best interview line

> For multiple-field sorting, I use comparator chaining so that fields are compared in sequence based on business priority.

**Code:**

```java
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', department='" + department + "', salary=" + salary + "}";
    }
}

public class Main {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Rishabh", "IT", 80000));
        employees.add(new Employee(2, "Aman", "HR", 60000));
        employees.add(new Employee(3, "Neha", "IT", 75000));
        employees.add(new Employee(4, "Zoya", "IT", 75000));
        employees.add(new Employee(5, "Karan", "HR", 60000));

        employees.sort(
                Comparator.comparing(Employee::getDepartment)
                        .thenComparing(Employee::getSalary)
                        .thenComparing(Employee::getName)
        );

        employees.forEach(System.out::println);
    }
}
```

**If salary should be descending:**

```java
employees.sort(
        Comparator.comparing(Employee::getDepartment)
                .thenComparing(Comparator.comparing(Employee::getSalary).reversed())
                .thenComparing(Employee::getName)
);
```

**Spoken interview answer:**

If I need to sort employee objects by multiple fields, I use comparator chaining in Java. For example, I can sort first by department, then by salary, and then by name using `Comparator.comparing()` and `thenComparing()`. This makes the code clean, readable, and easy to maintain.


---


# 17. 


--