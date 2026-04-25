
# 1. How does Spring Boot auto-configuration work?

----

**Short interview version:**

Spring Boot auto-configuration works by checking the libraries on the classpath, the existing beans in the `ApplicationContext`, and configuration properties, and then automatically creating default beans only when they are needed. It is enabled through `@EnableAutoConfiguration` or, more commonly, `@SpringBootApplication`. Internally, Spring Boot loads auto-configuration classes, and most of them are guarded by conditions such as `@ConditionalOnClass` and `@ConditionalOnMissingBean`, so Boot backs off if you already provide your own bean. ([Home][1])

**One-line answer:**

Spring Boot auto-configuration automatically creates sensible default beans based on classpath, properties, and missing-bean conditions, while still allowing your custom configuration to override it. ([Home][1])

**Explanation:**

Think of it in this flow:

**1. You start the app with `@SpringBootApplication`**
`@SpringBootApplication` includes auto-configuration support, so Boot knows it should try to configure the application automatically. ([Home][2])

**2. Spring Boot finds candidate auto-configuration classes**
In current Spring Boot, auto-configuration classes are regular configuration classes and are discovered from `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`. The API docs also note they are located using `ImportCandidates`. ([Home][3])

**3. It evaluates conditions before creating beans**
Auto-configuration classes are usually protected with conditional annotations like `@ConditionalOnClass`, `@ConditionalOnMissingBean`, property conditions, web conditions, and similar checks. That means Boot configures something only if the required library is present and only if you have not already defined your own bean. ([Home][3])

**4. It registers default beans**
Example: if database-related classes are on the classpath and you have not defined your own `DataSource`, Boot can auto-configure one. The docs use the example that if `HSQLDB` is on the classpath and no database connection beans are defined, Boot auto-configures an in-memory database. ([Home][1])

**5. Your own beans win**
Auto-configuration is non-invasive. If you define your own bean, such as your own `DataSource`, Boot backs away from its default configuration for that part. ([Home][1])

**Best interview line:**

Spring Boot auto-configuration is basically conditional bean registration. It scans what is available on the classpath, checks whether required beans already exist, reads configuration properties, and then creates default beans only when the conditions match. ([Home][1])

**How to verify it in real projects:**

If you run the app with `--debug`, Spring Boot prints a conditions report showing which auto-configurations matched and which did not. With Actuator, you can also inspect the conditions endpoint. ([Home][1])

**Interview-ready example:**

If I add `spring-boot-starter-web`, Boot sees Spring MVC classes on the classpath and auto-configures web-related beans. If I add JPA and a database driver, Boot can auto-configure data access beans. But if I define my own bean manually, Boot usually backs off because of `@ConditionalOnMissingBean`. ([Home][1])

Send the next question exactly like this.

[1]: https://docs.spring.io/spring-boot/reference/using/auto-configuration.html?utm_source=chatgpt.com "Auto-configuration :: Spring Boot"
[2]: https://docs.spring.io/spring-boot/docs/3.2.6/reference/html/using.html?utm_source=chatgpt.com "Developing with Spring Boot"
[3]: https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html?utm_source=chatgpt.com "Creating Your Own Auto-configuration :: Spring Boot"



---

# 2. What is dependency injection?

---

**Short interview version:**

Dependency Injection is a design pattern in which the objects a class depends on are provided from outside instead of the class creating them itself. In Spring, the container creates and manages beans and injects required dependencies into a class. This reduces tight coupling, improves testability, and makes the code easier to maintain.

**One-line answer:**

Dependency Injection means giving a class its required objects from outside instead of letting it create them on its own.

**Explanation:**

Suppose a `UserService` needs a `UserRepository`.

Without dependency injection, `UserService` may do this internally:

```java
UserRepository repo = new UserRepository();
```

This creates **tight coupling** because:

* `UserService` is directly dependent on a specific implementation
* it becomes harder to test
* replacing implementation becomes difficult

With dependency injection, the dependency is provided from outside:

* Spring creates the `UserRepository` bean
* Spring injects it into `UserService`

So the class only focuses on business logic, not object creation.

**Why it is useful:**

* reduces tight coupling
* improves unit testing
* makes code cleaner
* makes changing implementation easier
* follows Inversion of Control

**Types of Dependency Injection in Spring:**

1. **Constructor injection**
   Best and most recommended

2. **Setter injection**
   Used when dependency is optional

3. **Field injection**
   Possible, but generally not preferred in production code

**Best interview line:**

Dependency Injection is a way to achieve loose coupling by moving object creation and dependency wiring to the Spring container.

**Code example:**

```java
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
class UserRepository {
    public String findUser() {
        return "User found";
    }
}

@Service
class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUser() {
        return userRepository.findUser();
    }
}
```

**How to explain this in interview:**

Here `UserService` does not create `UserRepository` by using `new`.
Spring creates the repository bean and injects it through the constructor.
This is constructor-based dependency injection and is the preferred approach.

**Best spoken answer:**

Dependency Injection means a class gets its required dependencies from an external container instead of creating them itself. In Spring, the IoC container manages bean creation and injects dependencies, which helps us achieve loose coupling, better testability, and cleaner design.


---
# 3. Bean scopes
---

**Short interview version:**

Bean scope in Spring defines how many bean instances Spring creates and how long they live in the container. The most common scope is `singleton`, where only one bean instance is created for the whole Spring container. Other important scopes are `prototype`, `request`, `session`, and `application`. In backend interviews, the key point is that Spring beans are singleton by default.

**One-line answer:**

Bean scope tells Spring whether it should create one shared bean or multiple separate bean instances.

**Explanation:**

A bean scope defines the **lifecycle and visibility** of a Spring bean.

### 1. Singleton

This is the **default scope** in Spring.

* only one instance per Spring container
* same bean is returned every time
* most commonly used in services, repositories, controllers

Example:
If `UserService` is singleton, Spring creates it once and reuses it everywhere.

### 2. Prototype

In this scope, Spring creates a **new object every time** the bean is requested.

* one request for bean → one new instance
* useful when bean is stateful or short-lived

### 3. Request

Creates one bean instance per **HTTP request**.

* valid only in web applications
* every new request gets a new bean

### 4. Session

Creates one bean instance per **HTTP session**.

* same bean reused during one user session
* different users get different instances

### 5. Application

Creates one bean per **ServletContext** for the whole web application.

* shared across the entire web app

### 6. WebSocket

Creates one bean per **WebSocket session**.

---

### Most important interview point

By default, Spring beans are **singleton**, not prototype.

That is one of the most asked points.

---

### Best interview line

> Bean scope in Spring decides the lifecycle and number of bean instances. By default, Spring creates singleton beans, but for special cases it supports scopes like prototype, request, and session.

---

### Code example

```java
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
class SingletonBean {
}

@Component
@Scope("prototype")
class PrototypeBean {
}
```

---

### Interview-friendly example

* `UserService` → usually `singleton`
* `FileProcessingContext` with temporary state → can be `prototype`
* `LoggedInUserData` in web app → can be `session`

---

### Very important follow-up point

If a `singleton` bean injects a `prototype` bean directly, the prototype bean is created only once at injection time unless we use special handling like:

* `ObjectProvider`
* `ApplicationContext`
* `@Lookup`

This is a strong interview point if they go deeper.

**Best spoken answer:**

Bean scope defines how Spring manages bean instances. The default scope is singleton, where one bean instance is shared across the container. Other scopes like prototype, request, and session are used when we need new instances per usage, per HTTP request, or per user session.



---
# 4. Singleton bean into prototype bean

---

