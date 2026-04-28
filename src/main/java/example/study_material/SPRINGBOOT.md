
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

**Short interview version:**

If a **singleton bean is injected into a prototype bean**, every new prototype object gets the **same singleton instance**, because singleton scope means one shared bean per container. If a **prototype bean is injected into a singleton bean**, the prototype is created only **once at singleton initialization time**, so the singleton keeps using that same instance unless we use special techniques like `ObjectProvider`, `Provider`, or lookup/method injection. ([Home][1])

**One-line answer:**

Singleton into prototype is fine and the same singleton is shared; prototype into singleton is tricky because normal injection happens only once. ([Home][1])

**Explanation:**

### 1. Singleton bean into prototype bean

This is straightforward.

* Spring creates the singleton bean once for the whole container.
* Each time a new prototype bean is created, Spring injects that same singleton bean reference into it.

So if 10 prototype objects are created, all 10 can use the same singleton dependency. This follows directly from singleton scope being shared and prototype scope creating a new bean per request. ([Home][1])

### 2. Prototype bean into singleton bean

This is the important interview trap.

A singleton bean is created only once, and its dependencies are resolved at that time. So if you inject a prototype bean normally into a singleton bean, Spring creates one prototype instance and injects it once. After that, the singleton keeps using that same instance. It does **not** automatically get a fresh prototype object on every method call. ([Home][1])

### Best interview line

> A singleton injected into a prototype remains shared, but a prototype injected into a singleton does not behave like a fresh object on every use unless we explicitly request a new instance at runtime. ([Home][1])

### How to get a new prototype each time inside a singleton

If a singleton really needs a fresh prototype repeatedly, Spring recommends runtime lookup approaches such as method injection, and in practice common choices are `ObjectProvider` or `javax.inject.Provider`. The docs also describe lookup/method injection and even `ApplicationContext#getBean()` as ways to obtain a new prototype instance when needed. ([Home][2])

**Code:**

```java
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
class SingletonService {
    private final ObjectProvider<PrototypeBean> prototypeProvider;

    public SingletonService(ObjectProvider<PrototypeBean> prototypeProvider) {
        this.prototypeProvider = prototypeProvider;
    }

    public void process() {
        PrototypeBean prototypeBean = prototypeProvider.getObject(); // new instance each time
        prototypeBean.execute();
    }
}

@Component
@Scope("prototype")
class PrototypeBean {
    public void execute() {
        System.out.println("Prototype instance: " + this);
    }
}
```

**How to speak this in interview:**

If a singleton is injected into a prototype, all prototype objects will share the same singleton instance. But if a prototype is injected into a singleton, only one prototype object gets injected during singleton creation. If I need a fresh prototype every time, I would use `ObjectProvider`, `Provider`, or lookup injection.

[1]: https://docs.spring.io/spring-framework/reference/core/beans/factory-scopes.html?utm_source=chatgpt.com "Bean Scopes :: Spring Framework"
[2]: https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-method-injection.html?utm_source=chatgpt.com "Method Injection :: Spring Framework"




---
# 5. @Component vs @Service vs @Repository


---

**Short interview version:**

`@Component`, `@Service`, and `@Repository` are Spring stereotype annotations used to register classes as Spring beans. `@Component` is the generic annotation, `@Service` is used for service/business logic layer, and `@Repository` is used for DAO or database access layer. Functionally, all create Spring beans, but `@Repository` also helps in translating database-related exceptions into Spring’s `DataAccessException`.

**One-line answer:**

`@Component` is generic, `@Service` is for business logic, and `@Repository` is for persistence/database logic.

**Explanation:**

All three annotations tell Spring:

> Create an object of this class and manage it as a bean.

They are detected by component scanning.

### `@Component`

`@Component` is the most generic stereotype annotation.

Use it when the class does not clearly belong to service, repository, or controller layer.

Example:

```java
@Component
public class JwtUtil {
}
```

This could be a utility/helper bean.

### `@Service`

`@Service` is used in the service layer.

It contains business logic.

Example:

```java
@Service
public class UserService {
}
```

Use it for classes where you write application logic like:

* validation
* business rules
* calling repositories
* calling external APIs
* transaction flow

Technically, `@Service` also behaves like `@Component`, but it gives better readability and clear layer separation.

### `@Repository`

`@Repository` is used in the persistence layer.

Example:

```java
@Repository
public class UserRepository {
}
```

It is used for DAO/database-related classes.

Important interview point:

`@Repository` can translate persistence exceptions into Spring’s unchecked `DataAccessException`.

For example, database-specific exceptions can be converted into Spring’s common exception hierarchy.

### Main differences

| Annotation    | Used For      | Main Purpose                              |
| ------------- | ------------- | ----------------------------------------- |
| `@Component`  | Generic bean  | Common Spring-managed class               |
| `@Service`    | Service layer | Business logic                            |
| `@Repository` | DAO layer     | Database access and exception translation |

**Best interview line:**

All three register classes as Spring beans, but we use them according to the application layer. `@Component` is generic, `@Service` represents business logic, and `@Repository` represents persistence logic with database exception translation support.

**Code example:**

```java
@Component
class JwtUtil {
    public String generateToken(String username) {
        return "token-" + username;
    }
}

@Repository
class UserRepository {
    public String findUserById(Long id) {
        return "Rishabh";
    }
}

@Service
class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String getUserToken(Long id) {
        String user = userRepository.findUserById(id);
        return jwtUtil.generateToken(user);
    }
}
```

**Spoken interview answer:**

`@Component` is a generic Spring bean annotation. `@Service` is a specialized component used for business logic classes. `@Repository` is a specialized component used for database or DAO classes, and it also supports exception translation. So technically all are Spring beans, but we use the specific annotation to make the code more readable and layer-oriented.



---
# 6. @RestController vs @Controller

---
**Short interview version:**

`@Controller` is used when we want to return a web page or view, like JSP, Thymeleaf, or HTML. `@RestController` is used when we want to return data directly, usually JSON or XML. In simple words, `@Controller` is for MVC web pages, and `@RestController` is for REST APIs.

**One-line answer:**

`@Controller` returns views, while `@RestController` returns data directly in the response body.

**Explanation:**

`@Controller` is mostly used in traditional Spring MVC applications.

Example:

```java
@Controller
public class PageController {

    @GetMapping("/home")
    public String homePage() {
        return "home";
    }
}
```

Here, `"home"` means Spring will look for a view page named `home`.

So it may open:

```text
home.html
```

or

```text
home.jsp
```

depending on the view setup.

---

`@RestController` is used for REST APIs.

Example:

```java
@RestController
public class UserController {

    @GetMapping("/user")
    public User getUser() {
        return new User(1, "Rishabh");
    }
}
```

Here, Spring does not search for a view page.

It directly returns the object as JSON.

Output:

```json
{
  "id": 1,
  "name": "Rishabh"
}
```

---

**Important point:**

`@RestController` is basically:

```java
@Controller + @ResponseBody
```

That means every method inside `@RestController` automatically returns data in the HTTP response body.

But in `@Controller`, if we want to return data directly, we need to add `@ResponseBody`.

Example:

```java
@Controller
public class UserController {

    @ResponseBody
    @GetMapping("/user")
    public String getUser() {
        return "User data";
    }
}
```

---

**Best spoken answer:**

`@Controller` is used when we want to return a view page from Spring MVC. `@RestController` is used when we are building REST APIs and want to return data directly, mostly JSON. Internally, `@RestController` is a combination of `@Controller` and `@ResponseBody`.



---

# 7. @Transactional and propagation types

---

**Short interview version:**

`@Transactional` is used to run a method inside a database transaction. If everything goes fine, Spring commits the transaction. If an unchecked exception occurs, Spring rolls back the transaction. Propagation tells Spring what to do if a transaction already exists. The default propagation is `REQUIRED`, which means use the existing transaction if present, otherwise create a new one.

**One-line answer:**

`@Transactional` manages commit and rollback, and propagation decides how one transaction behaves when another transaction is already running.

**Explanation:**

Suppose we are transferring money.

```java
debit from account A
credit to account B
save transaction history
```

All these steps should succeed together.

If one step fails, all changes should be rolled back.

That is where `@Transactional` is used.

```java
@Transactional
public void transferMoney() {
    debitAccount();
    creditAccount();
    saveTransaction();
}
```

If the method completes successfully, Spring commits.

If a runtime exception happens, Spring rolls back.

---

## Common propagation types

### 1. `REQUIRED`

This is the default.

It means:

If a transaction already exists, use it.
If not, create a new one.

```java
@Transactional(propagation = Propagation.REQUIRED)
```

Most commonly used.

---

### 2. `REQUIRES_NEW`

Always creates a new transaction.

If an old transaction is already running, it is paused.

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
```

Useful for audit logs.

Example:

Even if main transaction fails, we may still want to save failure logs.

---

### 3. `MANDATORY`

It must run inside an existing transaction.

If no transaction exists, Spring throws an exception.

```java
@Transactional(propagation = Propagation.MANDATORY)
```

Use it when this method should never run alone.

---

### 4. `SUPPORTS`

If a transaction exists, it joins it.

If no transaction exists, it runs without transaction.

```java
@Transactional(propagation = Propagation.SUPPORTS)
```

Mostly used for read-only methods.

---

### 5. `NOT_SUPPORTED`

It always runs without transaction.

If a transaction exists, Spring pauses it.

```java
@Transactional(propagation = Propagation.NOT_SUPPORTED)
```

Used when we do not want transactional behavior.

---

### 6. `NEVER`

It must run without transaction.

If a transaction exists, Spring throws an exception.

```java
@Transactional(propagation = Propagation.NEVER)
```

Rarely used.

---

### 7. `NESTED`

Runs inside a nested transaction using savepoints.

If nested part fails, only that nested part can roll back.

Outer transaction can still continue.

```java
@Transactional(propagation = Propagation.NESTED)
```

This depends on database and transaction manager support.

---

## Most important interview points

By default:

```java
@Transactional
```

means:

```java
@Transactional(propagation = Propagation.REQUIRED)
```

Also, Spring rolls back automatically for unchecked exceptions like:

```java
RuntimeException
NullPointerException
IllegalArgumentException
```

But for checked exceptions, rollback does not happen by default.

For checked exception rollback, we write:

```java
@Transactional(rollbackFor = Exception.class)
```

---

## Best spoken answer:

`@Transactional` is used to manage database transactions. It commits if the method completes successfully and rolls back if a runtime exception occurs. Propagation defines how the method should behave if there is already an active transaction. The default is `REQUIRED`, which joins the existing transaction or creates a new one if none exists. In real projects, `REQUIRED` and `REQUIRES_NEW` are used most commonly.


---

# 8. How do you handle exception mapping globally?

---


**Short interview version:**

Global exception mapping in Spring Boot is usually handled using `@RestControllerAdvice` with `@ExceptionHandler` methods. We create one global handler class and map custom exceptions to proper HTTP status codes and error responses. This keeps controllers clean and gives the same error format across the whole API. Spring also supports global exception handling through `@ControllerAdvice` and `@ExceptionHandler`. ([Home][1])

**One-line answer:**

Use `@RestControllerAdvice` plus `@ExceptionHandler` to handle exceptions globally and return proper API error responses.

**Explanation:**

In a real project, I do not write `try-catch` in every controller.

Instead, I create one global exception handler class.

Example:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex) {
        ApiError error = new ApiError(
                "USER_NOT_FOUND",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleBadRequest(IllegalArgumentException ex) {
        ApiError error = new ApiError(
                "BAD_REQUEST",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex) {
        ApiError error = new ApiError(
                "INTERNAL_SERVER_ERROR",
                "Something went wrong"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
```

Error response class:

```java
public class ApiError {
    private String code;
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
```

Custom exception:

```java
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
```

**How it works:**

When an exception is thrown from any controller or service, Spring checks the global handler.

If the exception matches any `@ExceptionHandler`, that method is called.

Then we return a proper HTTP status and response body.

For example:

```java
throw new UserNotFoundException("User not found with id 10");
```

Response:

```json
{
  "code": "USER_NOT_FOUND",
  "message": "User not found with id 10"
}
```

**Common status mapping:**

* `UserNotFoundException` → `404 NOT_FOUND`
* `IllegalArgumentException` → `400 BAD_REQUEST`
* validation error → `400 BAD_REQUEST`
* access denied → `403 FORBIDDEN`
* unknown error → `500 INTERNAL_SERVER_ERROR`

**Best spoken answer:**

In Spring Boot, I handle exception mapping globally using `@RestControllerAdvice`. Inside that class, I write `@ExceptionHandler` methods for custom and common exceptions. Each handler returns a proper status code and a common error response. This avoids repeated try-catch blocks in controllers and keeps API errors consistent.

[1]: https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-servlet/exceptionhandlers.html?utm_source=chatgpt.com "Exceptions :: Spring Framework"


---
# 9. How do you secure a Spring Boot API?

---

**Short interview version:**

I secure a Spring Boot API using Spring Security. First, I add authentication, usually JWT or OAuth2. Then I add authorization, so only users with the right role can access protected APIs. I also make the API stateless, validate inputs, handle exceptions globally, use HTTPS, configure CORS properly, and never expose sensitive data in responses or logs.

**One-line answer:**

Secure a Spring Boot API with authentication, authorization, stateless JWT/OAuth2 security, proper role checks, input validation, HTTPS, CORS, and global error handling.

**Explanation:**

In a real project, I would secure it in layers.

First, I protect endpoints using Spring Security’s `SecurityFilterChain`. Spring Security uses this filter chain to apply authentication and authorization checks on incoming requests. ([Home][1])

Example:

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(Customizer.withDefaults())
                )
                .build();
    }
}
```

**How I explain this code:**

`/api/auth/**` is public because login and signup should be open.

`/api/admin/**` is only for admin users.

All other APIs need authentication.

I keep the API stateless because REST APIs usually use tokens instead of server sessions. Spring Security also supports OAuth2 Resource Server with JWT bearer tokens. ([Home][2])

**Main things I would do:**

1. **Authentication**
   Check who the user is.
   Usually with JWT, OAuth2, or basic login flow.

2. **Authorization**
   Check what the user can access.
   For example, `ADMIN`, `USER`, or specific permissions.

3. **Password security**
   Store passwords using strong hashing like BCrypt.
   Never store plain text passwords.

4. **Stateless API**
   For JWT-based APIs, avoid server session.
   Each request should carry a valid token.

5. **CORS configuration**
   Allow only trusted frontend domains.
   Do not allow everything in production.

6. **Input validation**
   Use validations like `@NotNull`, `@Email`, `@Size`.
   This protects the API from bad input.

7. **Global exception handling**
   Return clean error messages.
   Do not expose stack traces or internal details.

8. **HTTPS**
   Always use HTTPS in production.

9. **Logging safety**
   Never log passwords, tokens, OTPs, card numbers, or secrets.

**Best spoken answer:**

I secure a Spring Boot API mainly through Spring Security. I configure a security filter chain, keep public APIs open, protect other APIs with authentication, and apply role-based access for sensitive endpoints. For REST APIs, I usually prefer stateless JWT or OAuth2-based security. Along with that, I add input validation, proper CORS, HTTPS, global exception handling, and safe logging so sensitive data is not leaked.

[1]: https://docs.spring.io/spring-security/reference/servlet/architecture.html?utm_source=chatgpt.com "Architecture :: Spring Security"
[2]: https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html?utm_source=chatgpt.com "OAuth2 :: Spring Security"





---

# 10 . How would you implement rate limiting?

---

**Short interview version:**

I would implement rate limiting at the API gateway or application filter level. For a single instance, an in-memory token bucket can work. But for a real distributed Spring Boot system, I would use Redis-based rate limiting, because multiple app instances should share the same request count. I would usually limit by user ID, API key, IP address, or endpoint, and return HTTP `429 Too Many Requests` when the limit is crossed.

**One-line answer:**

Use token bucket or fixed window rate limiting, preferably with Redis in distributed systems, and return `429` when the user crosses the allowed limit.

**Explanation:**

Rate limiting means we control how many requests a user can make in a given time.

Example:

```text
100 requests per minute per user
```

If the user sends more than 100 requests in one minute, we block extra requests.

---

**Where I would implement it:**

In real projects, best places are:

1. **API Gateway**

   * Best for common rate limiting across all services.
   * Example: Spring Cloud Gateway, Nginx, Kong, AWS API Gateway.

2. **Spring Boot Filter / Interceptor**

   * Good when rate limiting is service-specific.

3. **Redis**

   * Best when the app has multiple instances.
   * All instances share the same counter.

---

**Common algorithms:**

### 1. Fixed Window

Example:

```text
User can make 100 requests from 10:00 to 10:01
```

Simple to implement, but traffic spikes can happen at window boundaries.

### 2. Sliding Window

More accurate than fixed window.

It checks request count in the last real time window, like last 60 seconds.

### 3. Token Bucket

Most commonly used.

The bucket has limited tokens.

Each request consumes one token.

Tokens refill over time.

If no token is available, request is rejected.

---

**Simple flow:**

```text
Request comes
   ↓
Get userId or IP
   ↓
Check request count/token from Redis
   ↓
If limit available → allow request
   ↓
If limit crossed → return 429 Too Many Requests
```

---

**HTTP response when limit is crossed:**

```http
429 Too Many Requests
```

Response body:

```json
{
  "message": "Too many requests. Please try again later."
}
```

---

**Best spoken answer:**

In a Spring Boot backend, I would implement rate limiting either at the API gateway or using a filter. For a simple single-instance app, in-memory rate limiting is enough. But for production with multiple instances, I would use Redis so all instances share the same limit data. I would usually use token bucket or sliding window logic and return `429 Too Many Requests` when the limit is exceeded.



---
# 11. How do you externalize config?

---


**Short interview version:**

In Spring Boot, I externalize config by keeping environment-specific values outside the code. I can use `application.properties`, `application.yml`, profile files like `application-dev.yml`, environment variables, command-line arguments, or a config server. This helps me run the same code in dev, test, and production with different settings. Spring Boot supports all these external config sources directly. ([Home][1])

**One-line answer:**

Externalized config means keeping values like DB URL, secrets, ports, and API URLs outside Java code.

**Explanation:**

In real projects, I never hardcode values like this:

```java
String url = "jdbc:mysql://localhost:3306/test";
```

Instead, I keep it in config:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
```

Then Spring Boot reads it automatically.

---

**Common ways to externalize config:**

**1. `application.properties`**

```properties
server.port=8081
app.name=account-service
```

**2. `application.yml`**

```yaml
server:
  port: 8081

app:
  name: account-service
```

**3. Profile-based config**

```text
application-dev.yml
application-prod.yml
```

Run with:

```bash
java -jar app.jar --spring.profiles.active=prod
```

This is useful because dev and prod usually have different DB URLs and API endpoints.

**4. Environment variables**

Example:

```bash
export SERVER_PORT=8081
export SPRING_DATASOURCE_URL=jdbc:mysql://prod-db:3306/app
```

Spring Boot can read environment variables as properties. For example, `SERVER_PORT` can map to `server.port`. ([Home][1])

**5. Command-line arguments**

```bash
java -jar app.jar --server.port=9090
```

Command-line values can override file-based config. ([Home][1])

**6. `@Value`**

```java
@Value("${app.name}")
private String appName;
```

Good for simple values.

**7. `@ConfigurationProperties`**

Better for grouped config.

```java
@ConfigurationProperties(prefix = "payment")
public class PaymentProperties {
    private String baseUrl;
    private int timeout;
}
```

Spring Boot supports binding config values to structured objects using `@ConfigurationProperties`. ([Home][1])

---

**Production-level answer:**

For production, I prefer:

* normal config in `application.yml`
* environment-specific config using profiles
* secrets from environment variables or secret manager
* common config from Spring Cloud Config if many services are involved

Spring Cloud Config gives a central place to manage external properties across environments. ([cloud.spring.io][2])

**Best spoken answer:**

I externalize config by moving environment-specific values out of code and keeping them in properties, YAML files, environment variables, command-line arguments, or config server. In Spring Boot, I usually use profile-based YAML files for dev, test, and prod. For sensitive values like passwords or tokens, I prefer environment variables or secret managers instead of putting them directly in the code or Git.

[1]: https://docs.spring.io/spring-boot/reference/features/external-config.html?utm_source=chatgpt.com "Externalized Configuration :: Spring Boot"
[2]: https://cloud.spring.io/spring-cloud-config/?utm_source=chatgpt.com "Spring Cloud Config"


---

# 12. What is Actuator used for?

---

**Short interview version:**

Spring Boot Actuator is used to monitor and manage a Spring Boot application in production. It gives ready-made endpoints like health, metrics, info, logs, environment details, beans, and thread dump. The most common endpoint is `/actuator/health`, which tells whether the application is up or down.

**One-line answer:**

Actuator helps us check the health, metrics, and runtime details of a Spring Boot application.

**Explanation:**

In real backend projects, we need to know:

Is the app running?
Is the database connected?
How much memory is used?
How many requests are coming?
Are there any performance issues?

Spring Boot Actuator helps with these things.

Common endpoints are:

```text
/actuator/health
/actuator/info
/actuator/metrics
/actuator/env
/actuator/beans
/actuator/loggers
/actuator/threaddump
```

**Most used endpoints:**

`/actuator/health`

Shows app health.

Example:

```json
{
  "status": "UP"
}
```

`/actuator/metrics`

Shows metrics like memory usage, CPU usage, request count, JVM stats, etc.

`/actuator/info`

Shows application info like name, version, build details.

**Production point:**

In production, we should not expose all actuator endpoints publicly.

Usually we expose only safe endpoints like:

```properties
management.endpoints.web.exposure.include=health,info,metrics
```

And sensitive endpoints should be protected with Spring Security.

**Best spoken answer:**

Spring Boot Actuator is used for monitoring and managing an application. It gives production-ready endpoints for health checks, metrics, logs, environment details, and JVM information. In real projects, it is very useful for monitoring tools, load balancers, and debugging production issues.



---

# 13. How do you make a Spring Boot service production-ready?

---
**Short interview version:**

To make a Spring Boot service production-ready, I focus on security, logging, monitoring, health checks, exception handling, validation, database performance, timeouts, retries, config management, and proper deployment setup. A production-ready service should not only work locally. It should be secure, observable, scalable, and easy to debug when something goes wrong.

**One-line answer:**

A production-ready Spring Boot service should be secure, monitored, configurable, fault-tolerant, and properly tested.

**Explanation:**

In real projects, I would check these main areas:

### 1. Proper Configuration

Do not hardcode values.

Use:

```text
application.yml
application-dev.yml
application-prod.yml
environment variables
secret manager
```

Sensitive values like passwords, tokens, and API keys should not be committed in Git.

---

### 2. Security

Use Spring Security.

Protect APIs with:

```text
JWT
OAuth2
role-based access
HTTPS
proper CORS
```

Also never expose sensitive data in logs or API responses.

---

### 3. Global Exception Handling

Use:

```java
@RestControllerAdvice
```

This gives a common error response for all APIs.

It also keeps controller code clean.

---

### 4. Logging

Use proper logs with request id or correlation id.

Logs should tell:

```text
what happened
where it happened
which request failed
why it failed
```

But we should not log secrets like passwords, OTPs, tokens, or card numbers.

---

### 5. Monitoring With Actuator

Enable Spring Boot Actuator.

Important endpoints:

```text
/actuator/health
/actuator/metrics
/actuator/info
```

These help with health checks, monitoring, and alerts.

---

### 6. Metrics And Alerts

Use tools like:

```text
Prometheus
Grafana
ELK
Splunk
CloudWatch
```

Track things like:

```text
API latency
error rate
memory usage
CPU usage
DB connection pool usage
request count
```

---

### 7. Timeouts And Retries

For external API calls, always configure:

```text
connect timeout
read timeout
retry
circuit breaker
fallback
```

Without timeout, one slow downstream service can block the whole application.

---

### 8. Database Readiness

Use proper:

```text
indexes
connection pool configuration
transaction boundaries
query optimization
pagination
```

Also avoid long-running transactions.

---

### 9. Input Validation

Validate request payloads using:

```java
@NotNull
@NotBlank
@Email
@Size
@Valid
```

Bad input should fail early with a clear error message.

---

### 10. Testing

Add proper tests:

```text
unit tests
integration tests
controller tests
database tests
security tests
```

Production code should not depend only on manual testing.

---

### 11. Graceful Shutdown

The app should stop safely.

It should finish active requests if possible and release resources properly.

---

### 12. Proper Deployment Setup

Use:

```text
Docker
CI/CD pipeline
environment-specific config
health check
readiness check
liveness check
```

This helps the service run safely in Kubernetes or cloud environments.

---

**Best spoken answer:**

To make a Spring Boot service production-ready, I make sure it has proper externalized configuration, security, validation, global exception handling, structured logging, actuator health checks, metrics, monitoring, timeout handling, retry or circuit breaker for external calls, database optimization, and proper tests. In short, the service should be easy to secure, monitor, debug, scale, and recover when failures happen.











