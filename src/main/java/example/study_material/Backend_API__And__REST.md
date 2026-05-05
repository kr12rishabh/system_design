

# 1. What is REST?

---
## Summary

REST is a way to design APIs.

It allows client and server to communicate using HTTP.

In backend development, REST APIs are commonly used to expose resources like users, orders, payments, products, and accounts.

## One-Line Answer

**REST is an architectural style for building web APIs where resources are accessed using HTTP methods like GET, POST, PUT, PATCH, and DELETE.**

---

# Simple Meaning

REST stands for **Representational State Transfer**.

In simple words, REST is a set of rules for designing APIs.

It treats everything as a **resource**.

Example resources:

```text
User
Order
Product
Payment
Account
Customer
```

Each resource has a URL.

Example:

```http
GET /users/101
GET /orders/5001
POST /payments
DELETE /products/10
```

The client sends a request.
The server sends a response.

---

# Simple Example

Suppose we have a user resource.

```http
GET /api/v1/users/101
```

This means:

```text
Get the user whose ID is 101.
```

Response:

```json
{
  "id": 101,
  "name": "Ravi",
  "email": "ravi@example.com"
}
```

This is a REST API.

---

# REST Uses HTTP Methods

REST APIs commonly use these HTTP methods:

| Method | Meaning                  | Example             |
| ------ | ------------------------ | ------------------- |
| GET    | Read data                | `GET /users/101`    |
| POST   | Create data              | `POST /users`       |
| PUT    | Replace/update full data | `PUT /users/101`    |
| PATCH  | Update partial data      | `PATCH /users/101`  |
| DELETE | Delete data              | `DELETE /users/101` |

---

# GET

GET is used to fetch data.

Example:

```http
GET /api/v1/orders/123
```

It should not change data.

Good use:

```text
Fetch user profile
Fetch order details
Fetch account summary
```

---

# POST

POST is usually used to create a new resource.

Example:

```http
POST /api/v1/orders
```

Request:

```json
{
  "productId": "P101",
  "quantity": 2
}
```

Response:

```json
{
  "orderId": "ORD123",
  "status": "CREATED"
}
```

POST is not idempotent by default.

That means if we call it twice, it may create two orders.

For important APIs like payment or order creation, we should use idempotency key.

---

# PUT

PUT is used to update or replace a full resource.

Example:

```http
PUT /api/v1/users/101
```

Request:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi@example.com"
}
```

PUT is generally idempotent.

Calling the same PUT request many times should give the same final result.

---

# PATCH

PATCH is used for partial update.

Example:

```http
PATCH /api/v1/users/101
```

Request:

```json
{
  "email": "newemail@example.com"
}
```

Only email is updated.
Other fields remain same.

---

# DELETE

DELETE is used to delete a resource.

Example:

```http
DELETE /api/v1/users/101
```

If delete is successful, API can return:

```http
204 No Content
```

DELETE is also usually treated as idempotent.

---

# REST Is Resource-Based

A good REST API focuses on nouns, not actions.

Bad API design:

```http
GET /getUser
POST /createOrder
POST /deleteProduct
```

Better REST design:

```http
GET /users/101
POST /orders
DELETE /products/10
```

Why?

Because REST treats `users`, `orders`, and `products` as resources.

The HTTP method tells the action.

---

# REST Uses Status Codes

REST APIs should return proper HTTP status codes.

Common examples:

| Status Code               | Meaning                               |
| ------------------------- | ------------------------------------- |
| 200 OK                    | Request successful                    |
| 201 Created               | Resource created                      |
| 202 Accepted              | Request accepted for async processing |
| 204 No Content            | Success, but no response body         |
| 400 Bad Request           | Invalid request                       |
| 401 Unauthorized          | Authentication missing or invalid     |
| 403 Forbidden             | No permission                         |
| 404 Not Found             | Resource not found                    |
| 409 Conflict              | Conflict with current state           |
| 422 Unprocessable Entity  | Business validation failed            |
| 500 Internal Server Error | Server error                          |

Example:

```http
POST /api/v1/users
```

If user is created:

```http
201 Created
```

If email already exists:

```http
409 Conflict
```

---

# REST Is Stateless

This is one of the most important REST principles.

Stateless means:

```text
Server should not depend on previous request memory to understand the current request.
```

Each request should carry enough information.

Example:

```http
GET /api/v1/accounts/123
Authorization: Bearer token
```

The server uses the token to identify the user.

It should not depend on some old request to know who the user is.

This makes REST APIs easier to scale.

---

# REST Usually Uses JSON

REST does not force JSON only.

But in modern APIs, JSON is most common.

Example request:

```json
{
  "name": "Ravi",
  "email": "ravi@example.com"
}
```

Example response:

```json
{
  "id": 101,
  "name": "Ravi",
  "email": "ravi@example.com"
}
```

---

# REST In Spring Boot

Simple REST controller example:

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        return new UserResponse(id, "Ravi", "ravi@example.com");
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        UserResponse response = new UserResponse(101L, request.getName(), request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
```

Simple DTOs:

```java
public class UserRequest {
    private String name;
    private String email;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
```

```java
public class UserResponse {
    private Long id;
    private String name;
    private String email;

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

---

# REST vs SOAP

REST is usually simpler and lightweight.

SOAP is more strict and XML-based.

| Point      | REST                       | SOAP                      |
| ---------- | -------------------------- | ------------------------- |
| Style      | Architectural style        | Protocol                  |
| Format     | Mostly JSON                | Mostly XML                |
| Complexity | Simple                     | More strict               |
| Common use | Web and microservices APIs | Enterprise legacy systems |
| Transport  | Usually HTTP               | HTTP, SMTP, etc.          |

For most modern backend systems, REST is more common.

---

# REST vs GraphQL

REST exposes multiple endpoints.

Example:

```http
GET /users/101
GET /users/101/orders
GET /orders/5001/items
```

GraphQL usually exposes one endpoint.

Example:

```http
POST /graphql
```

Client asks exactly what data it wants.

REST is simpler and widely used.
GraphQL is useful when clients need flexible data fetching.

---

# REST vs Async Messaging

REST is request-response.

Example:

```text
Client calls API and waits for response.
```

Async messaging is event-based.

Example:

```text
Service publishes event and continues.
Other services process it later.
```

Use REST when immediate response is needed.

Use async messaging when work can happen later.

---

# Good REST API Design Practices

```text
Use nouns in URLs
Use correct HTTP methods
Use proper status codes
Keep APIs stateless
Use versioning like /api/v1
Use pagination for list APIs
Validate request body
Return consistent error response
Use authentication and authorization
Use idempotency for critical POST APIs
Do not expose internal database structure directly
```

Example good URLs:

```http
GET /api/v1/products
GET /api/v1/products/101
POST /api/v1/orders
PATCH /api/v1/users/101
DELETE /api/v1/cart/items/20
```

---

# Common Mistakes

## 1. Using Verbs In URLs

Bad:

```http
POST /createUser
GET /getOrder
POST /deleteProduct
```

Better:

```http
POST /users
GET /orders/101
DELETE /products/10
```

---

## 2. Returning 200 For Everything

Bad:

```http
200 OK
```

Even when request failed.

Better:

```text
400 for bad request
401 for unauthenticated
403 for forbidden
404 for not found
409 for conflict
500 for server error
```

---

## 3. Not Using Pagination

Bad:

```http
GET /orders
```

Returning 1 million orders at once.

Better:

```http
GET /orders?page=0&size=20
```

---

## 4. Exposing Sensitive Data

Never return sensitive data like:

```text
password
OTP
access token in logs
card number
CVV
secret keys
```

---

## 5. Breaking API Contract Suddenly

If clients are using an API, do not remove or rename fields suddenly.

Use API versioning for breaking changes.

---

# Interview-Ready Paragraph Answer

REST is an architectural style used to design web APIs. In REST, we treat data as resources, and each resource is identified by a URL. For example, `/users`, `/orders`, and `/payments` are resources. We use HTTP methods to perform actions on these resources. `GET` is used to read data, `POST` is used to create data, `PUT` is used to replace data, `PATCH` is used for partial update, and `DELETE` is used to remove data. REST APIs are usually stateless, which means every request should contain enough information, like an auth token, so the server can process it without depending on previous requests. A good REST API should use proper status codes, clear resource-based URLs, JSON request and response, validation, authentication, authorization, pagination, versioning, and consistent error responses. In simple words, REST is a clean and standard way for clients and servers to communicate over HTTP.

---

# 2. REST vs RPC

---
## Summary

REST and RPC are two ways to design APIs.

**REST focuses on resources.**
**RPC focuses on actions or functions.**

In simple words:

```text
REST = work with things
RPC  = call methods
```

---

## One-Line Answer

**REST is resource-oriented, while RPC is action-oriented. REST uses resources like `/users/101`, and RPC uses operation names like `/getUser` or `/createPayment`.**

---

# Simple Meaning

## REST

REST treats everything as a resource.

Example resources:

```text
Users
Orders
Payments
Products
Accounts
```

REST API examples:

```http
GET /users/101
POST /orders
PATCH /users/101
DELETE /products/10
```

Here, the URL tells the resource.

The HTTP method tells the action.

---

## RPC

RPC stands for **Remote Procedure Call**.

It means calling a function on another service over the network.

RPC API examples:

```http
POST /getUser
POST /createOrder
POST /makePayment
POST /cancelBooking
```

Here, the endpoint itself tells the action.

It feels like calling a method:

```java
getUser(101)
createOrder(orderRequest)
makePayment(paymentRequest)
```

---

# Main Difference

| Point         | REST                   | RPC                              |
| ------------- | ---------------------- | -------------------------------- |
| Main focus    | Resource               | Action/function                  |
| URL style     | Noun-based             | Verb/action-based                |
| Example       | `GET /users/101`       | `POST /getUser`                  |
| HTTP methods  | Used meaningfully      | Often mostly POST                |
| Design style  | Resource-oriented      | Operation-oriented               |
| Best for      | CRUD APIs, public APIs | Internal service calls, commands |
| Example tools | REST over HTTP         | gRPC, JSON-RPC, XML-RPC          |

---

# REST Example

Suppose we want user details.

REST style:

```http
GET /api/v1/users/101
```

Meaning:

```text
Get user resource with ID 101.
```

Response:

```json
{
  "id": 101,
  "name": "Ravi",
  "email": "ravi@example.com"
}
```

Here the resource is:

```text
/users/101
```

And the action is decided by:

```text
GET
```

---

# RPC Example

Same thing in RPC style:

```http
POST /api/v1/getUser
```

Request:

```json
{
  "userId": 101
}
```

Response:

```json
{
  "id": 101,
  "name": "Ravi",
  "email": "ravi@example.com"
}
```

Here the action is directly in the endpoint name:

```text
getUser
```

---

# REST Uses HTTP Methods Properly

REST uses HTTP methods based on meaning.

```http
GET /users/101       -> read user
POST /users          -> create user
PUT /users/101       -> replace user
PATCH /users/101     -> partially update user
DELETE /users/101    -> delete user
```

This makes the API easy to understand.

---

# RPC Usually Uses Method Names

RPC is more like method calling.

```http
POST /createUser
POST /updateUser
POST /deleteUser
POST /getUser
POST /makePayment
```

Many RPC APIs use `POST` for almost everything.

The action is inside the URL or request body.

---

# REST Is Good For CRUD APIs

REST works very well when we are dealing with resources.

Example:

```text
User management
Product catalog
Order details
Account summary
Payment records
Ticket details
```

REST is good when APIs are like:

```text
Create resource
Read resource
Update resource
Delete resource
List resources
```

Example:

```http
GET /products
GET /products/101
POST /products
PATCH /products/101
DELETE /products/101
```

This is clean REST design.

---

# RPC Is Good For Action-Based APIs

RPC works well when the operation does not fit cleanly into CRUD.

Example:

```text
Calculate fare
Generate report
Send OTP
Verify OTP
Run fraud check
Convert currency
Start batch job
Trigger settlement
```

RPC style:

```http
POST /calculateFare
POST /generateReport
POST /sendOtp
POST /verifyOtp
POST /runFraudCheck
```

These are action-based operations.

So RPC can feel more natural here.

---

# Example: Payment API

## REST Style

```http
POST /payments
```

Request:

```json
{
  "orderId": "ORD123",
  "paymentMethod": "UPI"
}
```

This creates a payment resource.

Then we can check status:

```http
GET /payments/PAY123
```

This is good REST design.

---

## RPC Style

```http
POST /makePayment
```

Request:

```json
{
  "orderId": "ORD123",
  "paymentMethod": "UPI"
}
```

This directly calls the `makePayment` operation.

This is action-based.

Both can work.
But REST is usually cleaner for public HTTP APIs.

---

# REST Is More Standard For Public APIs

REST is commonly used for APIs exposed to:

```text
Frontend apps
Mobile apps
Partner systems
Third-party clients
Public developers
```

Why?

Because REST is easy to understand.

It uses common HTTP concepts:

```text
URL
HTTP method
Status code
Headers
JSON body
Caching
```

Example:

```http
GET /api/v1/orders/123
```

Anyone can understand this quickly.

---

# RPC Is Common In Internal Communication

RPC is often used for internal service-to-service calls.

Common example:

```text
gRPC
```

gRPC is a modern RPC framework.

It is fast and uses Protocol Buffers.

Example service definition:

```proto
service PaymentService {
  rpc GetPaymentStatus(PaymentRequest) returns (PaymentResponse);
  rpc CreateRefund(RefundRequest) returns (RefundResponse);
}
```

This feels like calling a method on another service.

---

# REST vs gRPC

gRPC is a type of RPC.

| Point             | REST            | gRPC                        |
| ----------------- | --------------- | --------------------------- |
| Format            | Usually JSON    | Protocol Buffers            |
| Speed             | Good            | Very fast                   |
| Human readability | Easy            | Less readable               |
| Browser support   | Easy            | Needs extra setup           |
| Contract          | OpenAPI/Swagger | `.proto` file               |
| Best for          | Public APIs     | Internal microservice calls |

For example:

```text
Frontend to backend -> REST is common
Service to service -> gRPC can be useful
```

---

# REST Status Codes Are Very Useful

REST uses HTTP status codes clearly.

Example:

```text
200 OK
201 Created
204 No Content
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict
500 Internal Server Error
```

RPC can also use status codes.

But in some RPC-style APIs, the real status is inside the response body.

Example:

```json
{
  "success": false,
  "errorCode": "USER_NOT_FOUND"
}
```

Even if HTTP status is `200`.

That can be confusing if not designed well.

---

# REST Example With Proper Status Code

```http
GET /users/999
```

If user does not exist:

```http
404 Not Found
```

Response:

```json
{
  "message": "User not found"
}
```

This is clean.

---

# RPC Example

```http
POST /getUser
```

Response:

```json
{
  "success": false,
  "errorCode": "USER_NOT_FOUND",
  "message": "User not found"
}
```

This can also work.

But the team must define clear error rules.

---

# REST Is Better For Caching

GET APIs in REST can be cached.

Example:

```http
GET /products/101
```

Product details may be cached by:

```text
Browser
CDN
API Gateway
Reverse proxy
```

RPC APIs often use POST for everything.

POST is not naturally cache-friendly.

So REST is usually better for read-heavy public APIs.

---

# REST Is Not Always Perfect

Sometimes REST becomes awkward.

Example operation:

```text
Cancel an order
```

REST options:

```http
PATCH /orders/123
```

Request:

```json
{
  "status": "CANCELLED"
}
```

Or:

```http
POST /orders/123/cancellation
```

Both are possible.

But RPC feels simpler:

```http
POST /cancelOrder
```

So for command-heavy APIs, RPC can look more direct.

---

# REST And RPC Can Be Mixed

In real projects, many APIs are not 100% pure REST.

Example:

```http
GET /orders/123
POST /orders
POST /orders/123/cancel
POST /payments/verify
```

This is common.

The important thing is:

```text
Keep API clear
Keep behavior predictable
Use proper status codes
Document it well
Do not confuse clients
```

---

# When I Would Use REST

I would use REST when:

```text
API is public-facing
Frontend or mobile app will call it
Resources are clear
CRUD operations are common
Caching is useful
Simple HTTP-based design is enough
```

Examples:

```http
GET /users/101
GET /products
POST /orders
GET /payments/PAY123
DELETE /cart/items/10
```

---

# When I Would Use RPC

I would use RPC when:

```text
Service-to-service communication needs high performance
Operations are action-heavy
Strong contract is needed
Low latency is important
Internal services talk frequently
```

Examples:

```text
gRPC between microservices
Fraud check service
Pricing calculation service
Recommendation service
Internal payment status service
```

---

# Common Mistake

Do not say:

```text
REST is always better than RPC.
```

That is not correct.

Better answer:

```text
REST is better for resource-based public APIs.
RPC is better for action-based internal calls or high-performance service communication.
```

---

# Simple Practical Example

Suppose we are building an e-commerce system.

Good REST APIs:

```http
GET /products
GET /products/101
POST /orders
GET /orders/ORD123
```

Good RPC-style APIs:

```http
POST /calculateDeliveryCharge
POST /verifyCoupon
POST /runFraudCheck
POST /generateInvoice
```

This is practical.

Not every API has to be forced into one style.

---

# Best Practices

```text
Use REST for clean resource-based APIs
Use nouns in REST URLs
Use HTTP methods properly
Use proper status codes
Use RPC for action-heavy internal operations
Document request and response clearly
Handle errors consistently
Do not return 200 for every failure
Use authentication and authorization in both
Use idempotency for critical write operations
```

---

# Interview-Ready Paragraph Answer

REST and RPC are two different API design styles. REST is resource-oriented. It represents things like users, orders, payments, and products as resources, and we use HTTP methods like GET, POST, PUT, PATCH, and DELETE to perform actions on them. For example, `GET /users/101` means fetch user 101, and `POST /orders` means create an order. RPC is action-oriented. It looks like calling a function on another service, like `POST /getUser`, `POST /makePayment`, or `POST /calculateFare`. REST is usually better for public APIs, frontend APIs, CRUD operations, and APIs where HTTP status codes and caching are useful. RPC is better for internal service-to-service calls, command-style operations, and high-performance communication like gRPC. In real systems, I would use REST for resource-based APIs and RPC or gRPC for internal action-based operations where it makes the design simpler and faster.

---

# 3. PUT vs PATCH

---
## Summary

`PUT` and `PATCH` are both used to update data.

**PUT is used when we want to replace the full resource.**
**PATCH is used when we want to update only some fields.**

## One-Line Answer

**PUT updates/replaces the complete resource, while PATCH updates only the given fields of the resource.**

---

# Simple Meaning

Suppose we have a user:

```json
{
  "id": 101,
  "name": "Ravi",
  "email": "ravi@example.com",
  "phone": "9999999999"
}
```

Now we want to update this user.

---

# PUT Example

`PUT` usually means full update.

```http
PUT /api/v1/users/101
```

Request body:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi.kumar@example.com",
  "phone": "8888888888"
}
```

Here we send the full user data.

The server replaces the old resource with this new data.

After update:

```json
{
  "id": 101,
  "name": "Ravi Kumar",
  "email": "ravi.kumar@example.com",
  "phone": "8888888888"
}
```

So `PUT` is good when the client wants to update the whole resource.

---

# PATCH Example

`PATCH` means partial update.

```http
PATCH /api/v1/users/101
```

Request body:

```json
{
  "phone": "8888888888"
}
```

Only the phone number changes.

Other fields remain the same.

After update:

```json
{
  "id": 101,
  "name": "Ravi",
  "email": "ravi@example.com",
  "phone": "8888888888"
}
```

So `PATCH` is good when we want to update only selected fields.

---

# Main Difference

| Point           | PUT                        | PATCH                        |
| --------------- | -------------------------- | ---------------------------- |
| Update type     | Full update                | Partial update               |
| Request body    | Usually full resource      | Only changed fields          |
| Existing fields | Can be replaced            | Usually unchanged            |
| Usage           | Replace user/order/product | Update email/status/phone    |
| Idempotent      | Usually yes                | Can be, depends on operation |

---

# Important Point About Missing Fields

This is the biggest difference.

Suppose current user is:

```json
{
  "name": "Ravi",
  "email": "ravi@example.com",
  "phone": "9999999999"
}
```

Now client sends this with `PUT`:

```json
{
  "name": "Ravi Kumar"
}
```

In strict `PUT`, this means:

```text
Replace the full resource with only this data.
```

So `email` and `phone` may become `null` or removed, depending on implementation.

That is why for `PUT`, the client should send the complete resource.

But with `PATCH`, this request means:

```text
Only update name.
Keep email and phone unchanged.
```

---

# PUT Is Usually Idempotent

Idempotent means if we call the same API many times, final result remains same.

Example:

```http
PUT /api/v1/users/101
```

Body:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi@example.com"
}
```

Call it once or five times.

Final data is still:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi@example.com"
}
```

So `PUT` is idempotent.

---

# PATCH Can Also Be Idempotent

PATCH can be idempotent if we use it like this:

```http
PATCH /api/v1/users/101
```

Body:

```json
{
  "status": "ACTIVE"
}
```

Calling this many times keeps status as `ACTIVE`.

So this is idempotent.

But PATCH may not be idempotent if the operation is like:

```json
{
  "incrementLoginCountBy": 1
}
```

If we call this 5 times, login count increases 5 times.

So this PATCH is not idempotent.

---

# When To Use PUT

Use `PUT` when:

```text
You want to update the full resource
Client has complete data
Missing fields should be treated as removed or null
You want replace-style behavior
```

Example:

```http
PUT /api/v1/users/101
```

Full request:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi.kumar@example.com",
  "phone": "8888888888"
}
```

---

# When To Use PATCH

Use `PATCH` when:

```text
You want to update only a few fields
Client does not have full object
You want to avoid overwriting other fields
You are updating status, email, phone, address, etc.
```

Example:

```http
PATCH /api/v1/orders/ORD123
```

Request:

```json
{
  "status": "CANCELLED"
}
```

Only order status changes.

---

# Spring Boot Example

## PUT API

```java
@PutMapping("/users/{id}")
public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long id,
        @RequestBody UserRequest request
) {
    UserResponse response = userService.updateFullUser(id, request);
    return ResponseEntity.ok(response);
}
```

Service logic:

```java
public UserResponse updateFullUser(Long id, UserRequest request) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPhone(request.getPhone());

    User savedUser = userRepository.save(user);

    return userMapper.toResponse(savedUser);
}
```

Here we expect full request data.

---

## PATCH API

```java
@PatchMapping("/users/{id}")
public ResponseEntity<UserResponse> patchUser(
        @PathVariable Long id,
        @RequestBody UserPatchRequest request
) {
    UserResponse response = userService.updatePartialUser(id, request);
    return ResponseEntity.ok(response);
}
```

Service logic:

```java
public UserResponse updatePartialUser(Long id, UserPatchRequest request) {
    User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (request.getName() != null) {
        user.setName(request.getName());
    }

    if (request.getEmail() != null) {
        user.setEmail(request.getEmail());
    }

    if (request.getPhone() != null) {
        user.setPhone(request.getPhone());
    }

    User savedUser = userRepository.save(user);

    return userMapper.toResponse(savedUser);
}
```

Here only non-null fields are updated.

---

# Status Codes

Common status codes:

| Situation                               | Status Code              |
| --------------------------------------- | ------------------------ |
| Update successful with response body    | 200 OK                   |
| Update successful with no response body | 204 No Content           |
| Resource not found                      | 404 Not Found            |
| Invalid request body                    | 400 Bad Request          |
| Business validation failed              | 422 Unprocessable Entity |
| Version conflict                        | 409 Conflict             |

Example:

```text
PUT /users/101
User updated successfully
Return 200 OK or 204 No Content
```

---

# Common Mistakes

## Mistake 1: Using PUT For Partial Update

If the API accepts only one field and keeps other fields unchanged, then it is more like `PATCH`, not `PUT`.

---

## Mistake 2: Treating PATCH Like Full Replacement

PATCH should not normally replace the full object.

It should update only the fields sent by the client.

---

## Mistake 3: Not Handling Null Properly

In PATCH, null can be confusing.

Example:

```json
{
  "phone": null
}
```

Does this mean:

```text
Ignore phone?
Or clear phone?
```

This should be clearly defined in API contract.

---

## Mistake 4: No Version Check

For important updates, use optimistic locking.

Example:

```text
Two users update same resource at same time.
One update can overwrite the other.
```

To avoid this, use version field or ETag.

---

# Simple Practical Example

For user profile:

```http
PUT /users/101
```

Use when user submits the full profile form.

```http
PATCH /users/101
```

Use when user only updates mobile number.

For order:

```http
PATCH /orders/ORD123
```

Use when only order status changes.

For product:

```http
PUT /products/P101
```

Use when admin replaces the full product details.

---

# Interview-Ready Paragraph Answer

`PUT` and `PATCH` are both used for updating resources, but they are used differently. `PUT` is used for full update or replacement of a resource. So the client usually sends the complete object, and the server replaces the existing data with the new data. `PATCH` is used for partial update. So the client sends only the fields that need to be changed, and the remaining fields stay unchanged. For example, if we update the full user profile, I would use `PUT /users/{id}`. But if we only update the user’s phone number or order status, I would use `PATCH /users/{id}` or `PATCH /orders/{id}`. `PUT` is usually idempotent. `PATCH` can also be idempotent if it sets fixed values, but it may not be idempotent if it performs actions like incrementing a counter. In simple words, use PUT when replacing the whole resource, and use PATCH when changing only part of it.

---

# 4. POST vs PUT

---
## Summary

`POST` and `PUT` are both used to send data to the server.

**POST is mainly used to create a new resource or trigger an action.**
**PUT is mainly used to update or replace a resource at a known URL.**

## One-Line Answer

**POST is used when the server decides the new resource location, while PUT is used when the client knows the exact resource URL and wants to create or replace that resource.**

---

# Simple Meaning

Think of it like this:

```text
POST = Please create something for me.
PUT  = Put this exact data at this exact location.
```

Example:

```http
POST /users
```

Means:

```text
Create a new user.
Server will generate the new user ID.
```

Example:

```http
PUT /users/101
```

Means:

```text
Create or replace user with ID 101.
The client already knows the resource ID.
```

---

# POST Example

Use `POST` when creating a new resource.

```http
POST /api/v1/users
```

Request:

```json
{
  "name": "Ravi",
  "email": "ravi@example.com"
}
```

Response:

```http
201 Created
```

Response body:

```json
{
  "id": 101,
  "name": "Ravi",
  "email": "ravi@example.com"
}
```

Here, the client did not send the user ID.

The server created the user and generated ID `101`.

So `POST` is correct.

---

# PUT Example

Use `PUT` when updating or replacing a resource.

```http
PUT /api/v1/users/101
```

Request:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi.kumar@example.com"
}
```

This means:

```text
Replace user 101 with this data.
```

If user `101` exists, it is updated.

In some systems, if user `101` does not exist, it may be created.

That depends on API design.

---

# Main Difference

| Point               | POST                                  | PUT                                  |
| ------------------- | ------------------------------------- | ------------------------------------ |
| Main use            | Create new resource or trigger action | Create/replace resource at known URL |
| Resource ID         | Usually generated by server           | Usually provided in URL              |
| URL                 | Collection URL                        | Specific resource URL                |
| Example             | `POST /users`                         | `PUT /users/101`                     |
| Idempotent          | No, usually not                       | Yes, usually                         |
| Multiple same calls | Can create duplicates                 | Final result stays same              |
| Success status      | Usually `201 Created`                 | Usually `200 OK` or `204 No Content` |

---

# URL Difference

## POST Uses Collection URL

```http
POST /users
```

This means:

```text
Create a new user inside users collection.
```

The server decides the new ID.

---

## PUT Uses Specific Resource URL

```http
PUT /users/101
```

This means:

```text
Update or replace user 101.
```

The client is targeting one exact resource.

---

# Idempotency Difference

This is the most important interview point.

## POST Is Usually Not Idempotent

If we call this API twice:

```http
POST /orders
```

Request:

```json
{
  "productId": "P101",
  "quantity": 1
}
```

It may create two orders.

```text
First call  -> Order 1 created
Second call -> Order 2 created
```

So `POST` is usually not idempotent.

For critical APIs like payment or order creation, we use an **Idempotency-Key** to avoid duplicates.

---

## PUT Is Usually Idempotent

If we call this API many times:

```http
PUT /users/101
```

Request:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi@example.com"
}
```

Final result remains same.

```text
User 101 name is Ravi Kumar.
```

Calling it once or five times gives the same final state.

So `PUT` is idempotent.

---

# POST Can Also Be Used For Actions

Sometimes the API is not a simple create operation.

Example:

```http
POST /payments/verify
POST /orders/101/cancel
POST /reports/generate
POST /otp/send
```

These are action-based operations.

They do not fit cleanly into simple CRUD.

So `POST` is commonly used.

---

# PUT Should Not Usually Be Used For Actions

This is not clean:

```http
PUT /cancelOrder
```

Better:

```http
POST /orders/101/cancel
```

Or in REST style:

```http
PATCH /orders/101
```

Body:

```json
{
  "status": "CANCELLED"
}
```

---

# When To Use POST

Use `POST` when:

```text
You want to create a new resource.
Server should generate the ID.
The operation is not idempotent.
You are triggering an action.
You are submitting a command.
```

Examples:

```http
POST /users
POST /orders
POST /payments
POST /otp/send
POST /reports/generate
POST /orders/101/cancel
```

---

# When To Use PUT

Use `PUT` when:

```text
You know the exact resource URL.
You want to replace the full resource.
The same request should be safe to repeat.
The client provides the resource ID.
```

Examples:

```http
PUT /users/101
PUT /products/P101
PUT /settings/email-notification
```

---

# POST vs PUT For Create

This is a common confusion.

## Use POST When Server Generates ID

```http
POST /users
```

Request:

```json
{
  "name": "Ravi"
}
```

Server creates:

```text
/users/101
```

So `POST` is correct.

---

## Use PUT When Client Provides ID

```http
PUT /users/101
```

Request:

```json
{
  "name": "Ravi"
}
```

Here client says:

```text
Create or replace user at /users/101.
```

So `PUT` can be used.

But in most normal applications, `POST` is used for creation because the server usually generates the ID.

---

# Status Codes

## POST Status Codes

| Situation                             | Status Code       |
| ------------------------------------- | ----------------- |
| Resource created                      | `201 Created`     |
| Request accepted for async processing | `202 Accepted`    |
| Action completed successfully         | `200 OK`          |
| Invalid request                       | `400 Bad Request` |
| Duplicate/conflict                    | `409 Conflict`    |

Example:

```http
POST /users
```

Success:

```http
201 Created
```

---

## PUT Status Codes

| Situation                                  | Status Code      |
| ------------------------------------------ | ---------------- |
| Updated successfully with response body    | `200 OK`         |
| Updated successfully with no response body | `204 No Content` |
| Created at given URL                       | `201 Created`    |
| Resource not found                         | `404 Not Found`  |
| Version conflict                           | `409 Conflict`   |

Example:

```http
PUT /users/101
```

Success with body:

```http
200 OK
```

Success without body:

```http
204 No Content
```

---

# Spring Boot Example

## POST Example

```java
@PostMapping("/users")
public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
    UserResponse response = userService.createUser(request);

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(response);
}
```

This creates a new user.

---

## PUT Example

```java
@PutMapping("/users/{id}")
public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long id,
        @RequestBody UserRequest request
) {
    UserResponse response = userService.updateUser(id, request);

    return ResponseEntity.ok(response);
}
```

This updates or replaces user with the given ID.

---

# Practical Example

Suppose we have an order system.

Create order:

```http
POST /orders
```

Because server creates a new order ID.

Get order:

```http
GET /orders/ORD123
```

Update full order address:

```http
PUT /orders/ORD123
```

Cancel order:

```http
POST /orders/ORD123/cancel
```

Or:

```http
PATCH /orders/ORD123
```

Body:

```json
{
  "status": "CANCELLED"
}
```

---

# Common Mistakes

## Mistake 1: Using POST For Every Update

Bad:

```http
POST /updateUser
```

Better:

```http
PUT /users/101
```

or:

```http
PATCH /users/101
```

---

## Mistake 2: Using PUT For Partial Update

If you are updating only one field, `PATCH` is usually better.

Example:

```http
PATCH /users/101
```

Body:

```json
{
  "phone": "9999999999"
}
```

---

## Mistake 3: Ignoring Idempotency

For important `POST` APIs like payment, order, refund, or fund transfer, use idempotency key.

Example:

```http
POST /payments
Idempotency-Key: abc-123
```

This prevents duplicate payment on retry.

---

# Best Simple Rule

Use this rule in interviews:

```text
POST /users       -> create new user, server generates ID
PUT /users/101    -> replace/update user 101
PATCH /users/101  -> update only some fields of user 101
```

---

# Interview-Ready Paragraph Answer

`POST` and `PUT` are both used to send data to the server, but their purpose is different. `POST` is mainly used to create a new resource or trigger an action. For example, `POST /users` creates a new user, and the server usually generates the user ID. `PUT` is used when we want to update or replace a resource at a known URL, like `PUT /users/101`. The biggest difference is idempotency. `POST` is usually not idempotent, so calling it multiple times may create duplicate records. `PUT` is usually idempotent, so calling the same request multiple times should leave the resource in the same final state. In real APIs, I use `POST` for create operations, commands, payments, OTP, and report generation. I use `PUT` when the client knows the resource ID and wants to replace or update the full resource.

---

# 5. What status code for validation failure?

---
## Summary

For validation failure, the most common status code is **400 Bad Request**.

But for business validation failure, many teams use **422 Unprocessable Entity**.

## One-Line Answer

**Use `400 Bad Request` for invalid request data, and use `422 Unprocessable Entity` when the request format is correct but business rules fail.**

---

# Simple Answer

If the client sends invalid input, return:

```http
400 Bad Request
```

Example:

```json
{
  "email": "wrong-email",
  "amount": -100
}
```

Here the request data itself is invalid.

So `400 Bad Request` is correct.

---

# When To Use 400 Bad Request

Use `400` when request validation fails at input level.

Examples:

```text
Missing required field
Invalid email format
Invalid mobile number
Invalid date format
Invalid enum value
Invalid JSON
Amount is negative
Name is blank
Request body is malformed
```

Example:

```http
POST /api/v1/users
```

Request:

```json
{
  "name": "",
  "email": "abc"
}
```

Response:

```http
400 Bad Request
```

Response body:

```json
{
  "status": 400,
  "errorCode": "VALIDATION_FAILED",
  "message": "Request validation failed",
  "errors": [
    {
      "field": "name",
      "message": "Name is required"
    },
    {
      "field": "email",
      "message": "Email format is invalid"
    }
  ]
}
```

---

# When To Use 422 Unprocessable Entity

Use `422` when the request is syntactically correct, but business rules fail.

Examples:

```text
Insufficient balance
Transfer limit exceeded
Account is blocked
Coupon code is expired
Order cannot be cancelled after shipping
User is not eligible for this offer
```

Example:

```http
POST /api/v1/transfers
```

Request:

```json
{
  "fromAccount": "A101",
  "toAccount": "A102",
  "amount": 5000
}
```

If the user has only `1000` balance, the JSON is valid.

But business rule fails.

So we can return:

```http
422 Unprocessable Entity
```

Response:

```json
{
  "status": 422,
  "errorCode": "INSUFFICIENT_BALANCE",
  "message": "Account does not have enough balance"
}
```

---

# 400 vs 422

| Situation                     | Status Code                |
| ----------------------------- | -------------------------- |
| Invalid JSON                  | `400 Bad Request`          |
| Missing required field        | `400 Bad Request`          |
| Invalid email format          | `400 Bad Request`          |
| Negative amount               | `400 Bad Request`          |
| Invalid date format           | `400 Bad Request`          |
| Insufficient balance          | `422 Unprocessable Entity` |
| Account blocked               | `422 Unprocessable Entity` |
| Transfer limit exceeded       | `422 Unprocessable Entity` |
| Coupon expired                | `422 Unprocessable Entity` |
| Order cannot be cancelled now | `422 Unprocessable Entity` |

---

# Practical Rule

Use this rule:

```text
400 = request data is wrong
422 = request data is valid, but business cannot process it
```

But one important point:

Many companies use only `400 Bad Request` for all validation failures.

That is also acceptable if it is the team standard.

The main thing is consistency.

---

# Spring Boot Example

For DTO validation failure, `400 Bad Request` is very common.

```java
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    // getters and setters
}
```

Controller:

```java
@PostMapping("/users")
public ResponseEntity<UserResponse> createUser(
        @Valid @RequestBody CreateUserRequest request
) {
    UserResponse response = userService.createUser(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

If validation fails, return:

```http
400 Bad Request
```

---

# Common Mistake

Do not return `500 Internal Server Error` for validation failure.

Validation failure is not a server error.

The client sent invalid data or a request that cannot be processed.

So it should be `4xx`, not `5xx`.

---

# Interview-Ready Paragraph Answer

For validation failure, I usually return `400 Bad Request` when the request input is invalid, like missing required fields, invalid email, invalid date format, invalid enum value, or malformed JSON. If the request format is correct but a business rule fails, like insufficient balance, account blocked, transfer limit exceeded, or order cannot be cancelled, then `422 Unprocessable Entity` is also a good choice. In many companies, teams use `400` for all validation errors, and that is fine if the API standard is consistent. The important point is that validation errors should be in the `4xx` range because the problem is with the request, not the server. I would also return a clear error response with field-level messages so the client can show proper validation errors to the user.

---

# 6. What status code for unauthorized vs forbidden?

---
## Summary

Use **401 Unauthorized** when the user is not authenticated.

Use **403 Forbidden** when the user is authenticated but does not have permission.

## One-Line Answer

**401 means “please login or provide a valid token”, while 403 means “you are logged in, but you are not allowed to access this resource.”**

---

# 401 Unauthorized

Use `401 Unauthorized` when authentication is missing or invalid.

Examples:

```text
No token provided
Invalid token
Expired token
Wrong username/password
Invalid API key
```

Example:

```http
GET /api/v1/accounts/123
```

Without token:

```http
401 Unauthorized
```

Simple meaning:

```text
Who are you? Please authenticate first.
```

---

# 403 Forbidden

Use `403 Forbidden` when authentication is successful, but authorization fails.

Examples:

```text
Normal user tries to access admin API
Customer tries to view another customer's account
User has valid token but missing required permission
Employee can view reports but cannot approve payment
```

Example:

```http
GET /api/v1/admin/users
Authorization: Bearer valid-user-token
```

If the user is not admin:

```http
403 Forbidden
```

Simple meaning:

```text
I know who you are, but you are not allowed to do this.
```

---

# Main Difference

| Case                                     | Status Code        |
| ---------------------------------------- | ------------------ |
| Token missing                            | `401 Unauthorized` |
| Token invalid                            | `401 Unauthorized` |
| Token expired                            | `401 Unauthorized` |
| Login failed                             | `401 Unauthorized` |
| Valid token but no role                  | `403 Forbidden`    |
| Valid token but no permission            | `403 Forbidden`    |
| User tries to access another user’s data | `403 Forbidden`    |
| Admin API accessed by normal user        | `403 Forbidden`    |

---

# Simple Example

Suppose we have this API:

```http
GET /api/v1/accounts/A101
```

## Case 1: No Token

```http
Authorization header missing
```

Return:

```http
401 Unauthorized
```

Because the system does not know who is calling.

---

## Case 2: Invalid Token

```http
Authorization: Bearer wrong-token
```

Return:

```http
401 Unauthorized
```

Because authentication failed.

---

## Case 3: Valid Token But Wrong Account

```http
Authorization: Bearer valid-token-of-user-1
```

But account `A101` belongs to user 2.

Return:

```http
403 Forbidden
```

Because the user is known, but not allowed to access that account.

Some high-security systems may return `404 Not Found` here to hide whether the account exists.

---

# Easy Memory Trick

```text
401 = Authentication problem
403 = Authorization problem
```

Or even simpler:

```text
401 = Who are you?
403 = You cannot access this.
```

---

# Common Error Response

For `401`:

```json
{
  "status": 401,
  "errorCode": "UNAUTHORIZED",
  "message": "Authentication is required"
}
```

For `403`:

```json
{
  "status": 403,
  "errorCode": "FORBIDDEN",
  "message": "You do not have permission to access this resource"
}
```

Do not expose too much security detail in the response.

For example, avoid messages like:

```text
Your token signature is invalid because key id xyz failed
```

Keep it simple and safe.

---

# Interview-Ready Paragraph Answer

For authentication failure, I return `401 Unauthorized`. This means the request does not have valid identity, like missing token, invalid token, expired token, wrong credentials, or invalid API key. For authorization failure, I return `403 Forbidden`. This means the user is already authenticated, but does not have permission to access that resource or perform that action. For example, if a normal user calls an admin API with a valid token, I return `403`. If there is no token at all, I return `401`. So in simple words, `401` means authentication is required or failed, and `403` means authentication passed but access is not allowed.

---

# 7. How do you design a create-user API?

---
## Summary

A create-user API should create a new user safely and return only safe user data.

Main things are:

```text
Validation
Duplicate check
Password hashing
Transaction
Proper status codes
No sensitive data in response
Clear error response
```

## One-Line Answer

**I would design create-user as a `POST /api/v1/users` API that validates input, checks duplicate email/mobile, hashes the password, saves the user in a transaction, and returns `201 Created` with safe user details.**

---

# Basic API Design

Use `POST` because we are creating a new user.

```http
POST /api/v1/users
```

Request body:

```json
{
  "name": "Ravi Kumar",
  "email": "ravi@example.com",
  "mobileNumber": "9876543210",
  "password": "StrongPass@123"
}
```

Success response:

```http
201 Created
```

```json
{
  "userId": "USR123",
  "name": "Ravi Kumar",
  "email": "ravi@example.com",
  "mobileNumber": "9876543210",
  "status": "ACTIVE"
}
```

Important point:

```text
Never return password in response.
Never store password as plain text.
```

---

# Step-By-Step Flow

The backend flow should be like this:

```text
1. Receive create-user request
2. Validate request body
3. Normalize email/mobile
4. Check if email or mobile already exists
5. Hash the password
6. Create user record
7. Assign default role
8. Save user in database inside transaction
9. Publish event if needed
10. Return 201 Created response
```

---

# Required Validations

For create-user API, I would validate these fields:

```text
Name should not be blank
Email should be valid
Mobile number should be valid
Password should be strong
Email should be unique
Mobile number should be unique
```

Example validation errors:

```json
{
  "status": 400,
  "errorCode": "VALIDATION_FAILED",
  "message": "Request validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email format is invalid"
    },
    {
      "field": "password",
      "message": "Password must contain at least 8 characters"
    }
  ]
}
```

For input validation failure, return:

```http
400 Bad Request
```

---

# Duplicate User Handling

If email already exists:

```http
409 Conflict
```

Response:

```json
{
  "status": 409,
  "errorCode": "USER_ALREADY_EXISTS",
  "message": "User already exists with this email"
}
```

Why `409`?

Because the request conflicts with existing data.

Also, database should have a unique constraint.

Example:

```sql
ALTER TABLE users ADD CONSTRAINT uk_users_email UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT uk_users_mobile UNIQUE (mobile_number);
```

Do not depend only on Java check.

Two requests can come at the same time.
Database unique constraint protects us from race conditions.

---

# Password Handling

This is very important.

Never store password like this:

```text
StrongPass@123
```

Store only password hash.

Use strong hashing like:

```text
BCrypt
Argon2
PBKDF2
```

In Spring Boot, `BCryptPasswordEncoder` is commonly used.

Example:

```java
String hashedPassword = passwordEncoder.encode(request.getPassword());
```

Also avoid logging password.

Do not log:

```text
password
OTP
token
Authorization header
```

---

# Status Codes

Use these status codes:

| Situation                    | Status Code                 |
| ---------------------------- | --------------------------- |
| User created successfully    | `201 Created`               |
| Invalid request body         | `400 Bad Request`           |
| Email/mobile already exists  | `409 Conflict`              |
| Unauthorized request         | `401 Unauthorized`          |
| No permission to create user | `403 Forbidden`             |
| Server error                 | `500 Internal Server Error` |

If it is public signup API, auth may not be required.

If it is admin creating users, then auth and permission check are required.

---

# Request And Response DTO

## Request DTO

```java
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;

    // getters and setters
}
```

## Response DTO

```java
public class CreateUserResponse {

    private String userId;
    private String name;
    private String email;
    private String mobileNumber;
    private String status;

    public CreateUserResponse(String userId, String name, String email,
                              String mobileNumber, String status) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.status = status;
    }

    // getters
}
```

Notice this response does not contain password.

---

# Spring Boot Controller Example

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        CreateUserResponse response = userService.createUser(request);

        URI location = URI.create("/api/v1/users/" + response.getUserId());

        return ResponseEntity
                .created(location)
                .body(response);
    }
}
```

Here `created(location)` returns:

```http
201 Created
```

It also adds `Location` header.

Example:

```http
Location: /api/v1/users/USR123
```

---

# Service Layer Example

```java
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {

        String email = request.getEmail().trim().toLowerCase();
        String mobileNumber = request.getMobileNumber().trim();

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        if (userRepository.existsByMobileNumber(mobileNumber)) {
            throw new UserAlreadyExistsException("User already exists with this mobile number");
        }

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(request.getName().trim());
        user.setEmail(email);
        user.setMobileNumber(mobileNumber);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setStatus("ACTIVE");
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(
                savedUser.getUserId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getMobileNumber(),
                savedUser.getStatus()
        );
    }
}
```

---

# Important Security Points

For create-user API, I would keep these points in mind:

```text
Use HTTPS
Validate all input
Hash password
Do not return password
Do not log password
Use unique constraints
Add rate limiting
Use captcha or OTP if public signup
Verify email or mobile if needed
Keep error messages safe
```

For public signup, rate limiting is useful.

Example:

```text
Too many signup attempts from same IP
Return 429 Too Many Requests
```

---

# Should Create-User API Be Idempotent?

Usually `POST /users` is not idempotent.

If the same request comes twice, it can create duplicate users.

But because email/mobile is unique, duplicate creation is blocked.

If the API is used in a system where retry is common, we can also support an idempotency key.

Example:

```http
POST /api/v1/users
Idempotency-Key: abc-123
```

Then the same retry returns the previous response.

This is not always required for normal signup.
But it is useful for highly reliable systems.

---

# Should We Send Welcome Email Inside Same Request?

I would not block user creation because of welcome email.

Better approach:

```text
Create user in database
Publish USER_CREATED event
Email service sends welcome email later
```

This keeps API fast.

If email service is down, user creation should not fail.

---

# Database Table Example

```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    mobile_number VARCHAR(20) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

---

# Common Mistakes

## 1. Returning Password In Response

Never do this.

Bad:

```json
{
  "email": "ravi@example.com",
  "password": "StrongPass@123"
}
```

---

## 2. Storing Plain Password

This is a serious security mistake.

Always store hash.

---

## 3. No Unique Constraint

Only checking duplicate in code is not enough.

Use database unique constraints also.

---

## 4. Returning 200 Instead Of 201

For successful creation, `201 Created` is better.

---

## 5. Doing Too Much In Same API

Do not make user creation wait for email, SMS, analytics, or notification.

Use async event for those things.

---

# Best Practices

```text
Use POST /api/v1/users
Validate request with @Valid
Normalize email before saving
Check duplicates
Use database unique constraints
Hash password using BCrypt
Return 201 Created
Return safe response DTO
Use transaction
Do not log sensitive data
Use rate limiting for public API
Publish user-created event for async tasks
Use consistent error response
```

---

# Interview-Ready Paragraph Answer

To design a create-user API, I would use `POST /api/v1/users` because we are creating a new resource and the server usually generates the user ID. The API should accept fields like name, email, mobile number, and password. First, I would validate the request, like checking required fields, email format, mobile number format, and password rules. Then I would normalize email, check whether email or mobile already exists, and also keep unique constraints in the database to avoid race conditions. I would never store the password in plain text. I would hash it using BCrypt or a similar strong algorithm. After that, I would save the user inside a transaction, assign default status and role, and return `201 Created` with safe user details. I would not return password or sensitive data in the response. For duplicate email or mobile, I would return `409 Conflict`. For validation failure, I would return `400 Bad Request`. If welcome email or notification is needed, I would publish a `USER_CREATED` event and process it asynchronously.

---

# 8. How do you make an API idempotent?

---
## Summary

To make an API idempotent, we design it so that **the same request can be sent many times, but the final result stays the same**.

This is very important for APIs like:

```text
Payment
Refund
Order creation
Ticket booking
Wallet recharge
Fund transfer
Webhook handling
```

## One-Line Answer

**I make an API idempotent by using an idempotency key, storing the request status and response, checking duplicate requests, and returning the previous result instead of processing the same request again.**

---

# Simple Meaning

Suppose a user clicks **Pay Now**.

The request reaches the backend.

Payment is done.

But the response gets lost because of network timeout.

Now the client retries the same request.

Without idempotency:

```text
Payment can happen twice.
Order can be created twice.
Money can be deducted twice.
```

With idempotency:

```text
Backend understands this request was already processed.
It does not process again.
It returns the old response.
```

---

# Basic Idea

The client sends a unique key with the request.

Example:

```http
POST /api/v1/payments
Idempotency-Key: abc-123
```

Request body:

```json
{
  "orderId": "ORD123",
  "amount": 1000,
  "currency": "INR"
}
```

Backend checks:

```text
Have I already seen Idempotency-Key abc-123?
```

If no:

```text
Process request.
Save response.
Return response.
```

If yes:

```text
Return the saved response.
Do not process again.
```

---

# Step-By-Step Flow

A good idempotency flow is:

```text
1. Client sends Idempotency-Key.
2. Backend validates request.
3. Backend creates request hash.
4. Backend checks if key already exists.
5. If key exists with same request hash, return old response.
6. If key exists with different request hash, return 409 Conflict.
7. If key does not exist, save key as PROCESSING.
8. Process the actual business logic.
9. Save final response with SUCCESS or FAILED status.
10. Return response.
```

---

# Why Request Hash Is Needed

Same idempotency key should not be used for different requests.

Example first request:

```json
{
  "orderId": "ORD123",
  "amount": 1000
}
```

With key:

```text
abc-123
```

Second request:

```json
{
  "orderId": "ORD123",
  "amount": 5000
}
```

With same key:

```text
abc-123
```

This should be rejected.

Return:

```http
409 Conflict
```

Because the same key is being used for different data.

---

# Database Table Design

A simple idempotency table can look like this:

```sql
CREATE TABLE idempotency_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    idempotency_key VARCHAR(150) NOT NULL UNIQUE,
    request_hash VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    response_body TEXT,
    http_status INT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

Important fields:

| Field             | Purpose                                   |
| ----------------- | ----------------------------------------- |
| `idempotency_key` | Unique key from client                    |
| `request_hash`    | To detect same key with different request |
| `status`          | PROCESSING, SUCCESS, FAILED               |
| `response_body`   | Old response to return on retry           |
| `http_status`     | Old HTTP status code                      |
| `created_at`      | For expiry/cleanup                        |

---

# Use Unique Constraint

This is very important.

Do not only check in Java code.

Bad flow:

```text
Check if key exists.
If not, process request.
```

Why bad?

Because two same requests can come at the same time.

Both may see that key does not exist.

Both may process the payment.

So we need a database-level unique constraint:

```sql
idempotency_key VARCHAR(150) NOT NULL UNIQUE
```

This protects us from race conditions.

---

# Handle PROCESSING State

Suppose first request is still running.

Second same request comes at the same time.

Backend sees:

```text
Idempotency-Key = abc-123
Status = PROCESSING
```

In this case, we can return:

```http
409 Conflict
```

Response:

```json
{
  "message": "Request is already being processed"
}
```

Or we can wait for the first request to complete and then return the final response.

The choice depends on system design.

For most APIs, returning `409` or `202` is acceptable.

---

# Status Codes

Useful status codes:

| Case                         | Status Code                      |
| ---------------------------- | -------------------------------- |
| First request success        | `200 OK` or `201 Created`        |
| Same request repeated        | Return same old status           |
| Same key with different body | `409 Conflict`                   |
| Request currently processing | `409 Conflict` or `202 Accepted` |
| Missing idempotency key      | `400 Bad Request`                |
| Validation failure           | `400 Bad Request`                |
| Business rule failure        | `422 Unprocessable Entity`       |

---

# Example: Payment API

Request:

```http
POST /api/v1/payments
Idempotency-Key: pay-abc-123
```

Flow:

```text
1. Check idempotency key.
2. If already successful, return old payment response.
3. If new, create payment record.
4. Call payment gateway.
5. Save gateway payment ID.
6. Save final response.
7. Return response.
```

If timeout happens and client retries, backend returns the same result.

It does not charge again.

---

# Important Point For Payment

For payment APIs, timeout does not always mean failure.

Maybe payment succeeded, but response was lost.

So do not blindly retry the charge.

Better approach:

```text
Use idempotency key.
Use gateway transaction reference.
Mark payment as PENDING if status is unknown.
Verify with gateway status API.
Wait for webhook.
Run reconciliation job.
```

This protects users from double payment.

---

# Spring Boot Style Example

## Controller

```java
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody PaymentRequest request
    ) {
        PaymentResponse response = paymentService.createPayment(idempotencyKey, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
```

---

## Service Logic

```java
@Service
public class PaymentService {

    private final IdempotencyRepository idempotencyRepository;
    private final PaymentRepository paymentRepository;

    public PaymentService(IdempotencyRepository idempotencyRepository,
                          PaymentRepository paymentRepository) {
        this.idempotencyRepository = idempotencyRepository;
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public PaymentResponse createPayment(String idempotencyKey, PaymentRequest request) {

        String requestHash = createRequestHash(request);

        Optional<IdempotencyRecord> existing =
                idempotencyRepository.findByIdempotencyKey(idempotencyKey);

        if (existing.isPresent()) {
            IdempotencyRecord record = existing.get();

            if (!record.getRequestHash().equals(requestHash)) {
                throw new ConflictException("Same idempotency key used with different request");
            }

            if ("SUCCESS".equals(record.getStatus())) {
                return convertToPaymentResponse(record.getResponseBody());
            }

            if ("PROCESSING".equals(record.getStatus())) {
                throw new ConflictException("Request is already being processed");
            }
        }

        IdempotencyRecord record = new IdempotencyRecord();
        record.setIdempotencyKey(idempotencyKey);
        record.setRequestHash(requestHash);
        record.setStatus("PROCESSING");
        idempotencyRepository.save(record);

        Payment payment = new Payment();
        payment.setOrderId(request.getOrderId());
        payment.setAmount(request.getAmount());
        payment.setStatus("SUCCESS");

        Payment savedPayment = paymentRepository.save(payment);

        PaymentResponse response = new PaymentResponse(
                savedPayment.getId(),
                savedPayment.getOrderId(),
                savedPayment.getStatus()
        );

        record.setStatus("SUCCESS");
        record.setResponseBody(convertToJson(response));
        idempotencyRepository.save(record);

        return response;
    }

    private String createRequestHash(PaymentRequest request) {
        return String.valueOf(Objects.hash(
                request.getOrderId(),
                request.getAmount()
        ));
    }

    private PaymentResponse convertToPaymentResponse(String responseBody) {
        // In real code, use ObjectMapper
        return new PaymentResponse(1L, "ORD123", "SUCCESS");
    }

    private String convertToJson(PaymentResponse response) {
        // In real code, use ObjectMapper
        return response.toString();
    }
}
```

This is a simple example.

In real projects, use proper JSON hashing, ObjectMapper, exception handling, and database unique constraints.

---

# Idempotency For PUT And DELETE

Some methods are naturally idempotent.

## PUT

```http
PUT /api/v1/users/101
```

Same request many times gives same final result.

So `PUT` is usually idempotent.

## DELETE

```http
DELETE /api/v1/users/101
```

First call deletes the user.

Second call should not delete anything else.

The final result is still same.

So `DELETE` is usually idempotent.

## POST

```http
POST /api/v1/orders
```

This can create duplicate orders.

So for important `POST` APIs, we add idempotency key.

---

# Idempotency In Async Messaging

Idempotency is also needed in Kafka, RabbitMQ, and SQS.

Why?

Because messages can be delivered more than once.

Example:

```text
PaymentSuccess event is consumed twice.
Order should not be marked paid twice.
Invoice should not be generated twice.
Wallet should not be credited twice.
```

Solution:

```text
Store processed message ID.
Before processing, check if message was already processed.
If yes, skip.
If no, process and save message ID.
```

Table:

```sql
CREATE TABLE processed_messages (
    message_id VARCHAR(150) PRIMARY KEY,
    processed_at TIMESTAMP NOT NULL
);
```

---

# Expiry Or TTL

Idempotency records should not stay forever in most systems.

We can keep them for:

```text
24 hours
48 hours
7 days
30 days
```

It depends on business requirement.

Payment and banking systems may keep records longer for audit and dispute handling.

---

# Common Mistakes

## 1. Saving Idempotency Key After Processing

Bad:

```text
Process payment.
Then save idempotency key.
```

If server crashes after payment but before saving key, retry may charge again.

Better:

```text
Save key as PROCESSING.
Process payment.
Save final response.
```

---

## 2. No Unique Constraint

Without unique constraint, two parallel requests can process together.

Always enforce uniqueness in database.

---

## 3. Not Comparing Request Body

Same key with different request body should be rejected.

Otherwise, the API may return wrong response.

---

## 4. Retrying Payment Without Safe Status Check

Do not blindly retry payment after timeout.

Use gateway status check, webhook, and reconciliation.

---

## 5. Not Making Consumers Idempotent

Even if API is safe, async consumers can still duplicate work.

Consumers must also handle duplicate messages.

---

# Best Practices

```text
Use Idempotency-Key header for critical POST APIs
Generate one key per business operation
Store request hash
Store status and response
Use database unique constraint
Save key before processing
Handle PROCESSING state
Return old response for duplicate request
Reject same key with different body
Use TTL or cleanup policy
Make async consumers idempotent
Use correlation ID for debugging
Do not retry unsafe operations blindly
```

---

# Interview-Ready Paragraph Answer

To make an API idempotent, I use an idempotency key for operations that can create side effects, like payment, refund, order creation, or fund transfer. The client sends a unique key with the request. On the backend, I store that key with the request hash, status, and final response. If the same request comes again with the same key and same body, I do not process it again. I return the previously saved response. If the same key comes with a different request body, I return `409 Conflict`. I also keep a unique constraint on the idempotency key in the database to handle parallel duplicate requests safely. I save the key as `PROCESSING` before doing the actual business operation, then update it to `SUCCESS` or `FAILED` after completion. For async systems, I also store processed message IDs so the same message is not processed twice. In simple words, idempotency makes retries safe and protects the system from duplicate payments, duplicate orders, and duplicate side effects.

---

# 9. How do you handle pagination?

---
## Summary

Pagination means returning data in small parts instead of returning everything at once.

It protects the API, database, network, and frontend.

## One-Line Answer

**I handle pagination by accepting page/size or cursor parameters, applying sorting, limiting the result size, and returning metadata like current page, total records, and next page information.**

---

# Why Pagination Is Needed

Suppose we have 10 lakh orders.

This is bad:

```http
GET /api/v1/orders
```

If this API returns all orders at once, many problems can happen:

```text
API becomes slow
Database load increases
Response size becomes huge
Frontend becomes slow
Network usage increases
Memory usage increases
Timeout can happen
```

So we return data in small chunks.

Example:

```http
GET /api/v1/orders?page=0&size=20
```

This means:

```text
Give me first 20 orders.
```

---

# Common Pagination Types

There are mainly two common types:

```text
Offset-based pagination
Cursor-based pagination
```

---

# 1. Offset-Based Pagination

This is the most common and simple approach.

Example:

```http
GET /api/v1/orders?page=0&size=20
```

Here:

```text
page = page number
size = number of records per page
```

Example:

```text
page=0, size=20 -> first 20 records
page=1, size=20 -> next 20 records
page=2, size=20 -> next 20 records
```

In SQL, it looks like this:

```sql
SELECT *
FROM orders
ORDER BY created_at DESC
LIMIT 20 OFFSET 0;
```

For page 2:

```sql
SELECT *
FROM orders
ORDER BY created_at DESC
LIMIT 20 OFFSET 20;
```

---

# Offset Pagination Response Example

Request:

```http
GET /api/v1/orders?page=0&size=20
```

Response:

```json
{
  "content": [
    {
      "orderId": "ORD101",
      "amount": 1200,
      "status": "PAID"
    },
    {
      "orderId": "ORD102",
      "amount": 900,
      "status": "PENDING"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 250,
  "totalPages": 13,
  "first": true,
  "last": false
}
```

This is easy for frontend.

Frontend knows:

```text
Current page
Page size
Total records
Total pages
Whether next page exists
```

---

# Offset Pagination Pros

```text
Easy to implement
Easy for frontend
Good for admin screens
Good when user jumps to page number
Works well for small and medium data
```

Example:

```text
Go to page 1, page 2, page 3
```

---

# Offset Pagination Cons

Offset pagination can become slow for large data.

Example:

```http
GET /api/v1/orders?page=10000&size=20
```

Database may need to skip many records before returning the data.

Also, if new records are inserted while the user is moving between pages, some records can be missed or repeated.

---

# 2. Cursor-Based Pagination

Cursor pagination is better for large and fast-changing data.

Instead of page number, we use a cursor.

Example:

```http
GET /api/v1/orders?limit=20&cursor=2026-05-03T10:30:00
```

Or:

```http
GET /api/v1/orders?limit=20&cursor=ORD105
```

The cursor tells the server:

```text
Give records after this point.
```

Example response:

```json
{
  "content": [
    {
      "orderId": "ORD106",
      "amount": 1500,
      "status": "PAID"
    }
  ],
  "nextCursor": "ORD106",
  "hasNext": true
}
```

Then frontend calls:

```http
GET /api/v1/orders?limit=20&cursor=ORD106
```

---

# Cursor Pagination SQL Example

If we sort by `created_at DESC`, query can be:

```sql
SELECT *
FROM orders
WHERE created_at < '2026-05-03 10:30:00'
ORDER BY created_at DESC
LIMIT 20;
```

This is usually faster than large offset.

---

# Cursor Pagination Pros

```text
Better for large data
Better for infinite scroll
Better for real-time feeds
Avoids large OFFSET cost
Fewer duplicate or missing records
```

Good examples:

```text
Transaction history
Activity feed
Chat messages
Notifications
Audit logs
Large order list
```

---

# Cursor Pagination Cons

```text
Slightly harder to implement
Frontend cannot easily jump to page 50
Needs stable sorting
Cursor must be handled carefully
```

---

# Offset vs Cursor Pagination

| Point                          | Offset Pagination    | Cursor Pagination        |
| ------------------------------ | -------------------- | ------------------------ |
| Params                         | `page` and `size`    | `cursor` and `limit`     |
| Easy to build                  | Yes                  | Medium                   |
| Jump to page number            | Easy                 | Hard                     |
| Large data performance         | Can be slow          | Better                   |
| Infinite scroll                | Okay                 | Best                     |
| Data changes during pagination | Can repeat/miss data | More stable              |
| Use case                       | Admin tables         | Feeds, logs, large lists |

---

# My Practical Choice

I usually choose based on use case.

For normal admin UI:

```http
GET /api/v1/users?page=0&size=20
```

Offset pagination is fine.

For large data or infinite scroll:

```http
GET /api/v1/transactions?limit=20&cursor=txn_123
```

Cursor pagination is better.

---

# Important: Always Add Sorting

Pagination without sorting is risky.

Bad:

```sql
SELECT *
FROM orders
LIMIT 20 OFFSET 0;
```

Why bad?

Because database may return records in different order.

Good:

```sql
SELECT *
FROM orders
ORDER BY created_at DESC, id DESC
LIMIT 20 OFFSET 0;
```

Always use stable sorting.

A good sort field can be:

```text
createdAt
updatedAt
id
transactionTime
orderDate
```

Better to add a unique field also.

Example:

```text
ORDER BY created_at DESC, id DESC
```

This avoids confusion when two records have the same timestamp.

---

# Set Maximum Page Size

Never allow unlimited size.

Bad:

```http
GET /api/v1/orders?page=0&size=100000
```

This can overload the API.

Better rule:

```text
Default size = 20
Maximum size = 100
```

If client sends more than 100, either reject it or cap it to 100.

Example:

```http
400 Bad Request
```

Response:

```json
{
  "status": 400,
  "errorCode": "INVALID_PAGE_SIZE",
  "message": "Page size cannot be greater than 100"
}
```

---

# Validation Rules

For pagination params:

```text
page should not be negative
size should be greater than 0
size should not exceed max limit
sort field should be allowed
sort direction should be ASC or DESC
cursor should be valid
```

Bad request example:

```http
GET /api/v1/orders?page=-1&size=5000
```

Return:

```http
400 Bad Request
```

---

# Spring Boot Offset Pagination Example

Controller:

```java
@GetMapping("/orders")
public ResponseEntity<Page<OrderResponse>> getOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
) {
    Page<OrderResponse> response = orderService.getOrders(page, size);
    return ResponseEntity.ok(response);
}
```

Service:

```java
public Page<OrderResponse> getOrders(int page, int size) {

    if (page < 0) {
        throw new IllegalArgumentException("Page cannot be negative");
    }

    if (size <= 0 || size > 100) {
        throw new IllegalArgumentException("Size must be between 1 and 100");
    }

    Pageable pageable = PageRequest.of(
            page,
            size,
            Sort.by(Sort.Direction.DESC, "createdAt")
                    .and(Sort.by(Sort.Direction.DESC, "id"))
    );

    return orderRepository.findAll(pageable)
            .map(orderMapper::toResponse);
}
```

---

# Custom Response DTO Example

Instead of directly exposing Spring `Page`, many teams prefer custom response.

```java
public class PaginatedResponse<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;

    public PaginatedResponse(List<T> content,
                             int page,
                             int size,
                             long totalElements,
                             int totalPages,
                             boolean first,
                             boolean last) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.first = first;
        this.last = last;
    }

    // getters
}
```

This keeps API response stable.

Even if internal framework changes, API contract remains clean.

---

# Cursor Pagination Example

Request:

```http
GET /api/v1/transactions?limit=20&cursor=TXN105
```

Response:

```json
{
  "content": [
    {
      "transactionId": "TXN106",
      "amount": 500,
      "createdAt": "2026-05-03T10:31:00"
    }
  ],
  "nextCursor": "TXN106",
  "hasNext": true
}
```

Simple service logic:

```text
1. Decode cursor
2. Fetch records after cursor
3. Fetch limit + 1 records
4. If extra record exists, hasNext = true
5. Return only limit records
6. Set nextCursor from last returned record
```

Why fetch `limit + 1`?

Because it helps us know if next page exists.

---

# Cursor Pagination Java Example

```java
public CursorPageResponse<TransactionResponse> getTransactions(
        String cursor,
        int limit
) {
    if (limit <= 0 || limit > 100) {
        throw new IllegalArgumentException("Limit must be between 1 and 100");
    }

    List<Transaction> transactions;

    if (cursor == null || cursor.isBlank()) {
        transactions = transactionRepository.findFirstPage(limit + 1);
    } else {
        transactions = transactionRepository.findNextPage(cursor, limit + 1);
    }

    boolean hasNext = transactions.size() > limit;

    if (hasNext) {
        transactions = transactions.subList(0, limit);
    }

    String nextCursor = null;

    if (!transactions.isEmpty()) {
        nextCursor = transactions.get(transactions.size() - 1).getTransactionId();
    }

    List<TransactionResponse> content = transactions.stream()
            .map(transactionMapper::toResponse)
            .toList();

    return new CursorPageResponse<>(content, nextCursor, hasNext);
}
```

---

# Database Indexing Is Important

Pagination can be slow without indexes.

If we sort by `created_at` and `id`, add index.

Example:

```sql
CREATE INDEX idx_orders_created_at_id
ON orders (created_at, id);
```

For filtered pagination:

```http
GET /api/v1/orders?status=PAID&page=0&size=20
```

Index may be:

```sql
CREATE INDEX idx_orders_status_created_at
ON orders (status, created_at);
```

This depends on query pattern.

Good pagination is not only API design.
Database design also matters.

---

# Pagination With Filters

Real APIs usually have filters.

Example:

```http
GET /api/v1/orders?status=PAID&page=0&size=20
```

Backend should apply filters first, then pagination.

SQL:

```sql
SELECT *
FROM orders
WHERE status = 'PAID'
ORDER BY created_at DESC, id DESC
LIMIT 20 OFFSET 0;
```

Wrong approach:

```text
Fetch all orders
Filter in Java
Then paginate
```

This is bad for large data.

Always filter and paginate at database level.

---

# Pagination With Search

Example:

```http
GET /api/v1/users?search=ravi&page=0&size=20
```

For search APIs, use proper indexes.

For simple search, database indexes may work.

For advanced search, use tools like Elasticsearch or OpenSearch.

Do not load all users into memory and search in Java.

---

# Should We Return Total Count?

For offset pagination, total count is useful.

Example:

```json
{
  "totalElements": 250,
  "totalPages": 13
}
```

But for very large tables, count query can be expensive.

In such cases, we can return:

```json
{
  "hasNext": true
}
```

instead of total count.

Cursor pagination usually returns `hasNext` and `nextCursor`, not total count.

---

# Status Codes

For successful pagination:

```http
200 OK
```

For invalid page or size:

```http
400 Bad Request
```

For unauthorized request:

```http
401 Unauthorized
```

For forbidden access:

```http
403 Forbidden
```

---

# Common Mistakes

## 1. Returning All Records

Never return huge data without pagination.

---

## 2. No Default Limit

Always set default page size.

Example:

```text
Default size = 20
```

---

## 3. No Maximum Limit

Always set max size.

Example:

```text
Max size = 100
```

---

## 4. Pagination Without Sorting

This can return unstable results.

Always sort by stable fields.

---

## 5. Sorting By Any Field From Client

Do not allow client to sort by any database column.

Bad:

```http
GET /users?sort=passwordHash
```

Allow only safe fields:

```text
name
createdAt
email
status
```

---

## 6. Filtering In Java After Fetching All Rows

This kills performance.

Filter, sort, and paginate in the database.

---

## 7. Large OFFSET For Huge Data

For very large data, cursor pagination is better.

---

# Best Practices

```text
Use pagination for list APIs
Use default page size
Set maximum page size
Validate page and size
Use stable sorting
Add database indexes
Apply filtering in database
Return useful metadata
Use cursor pagination for large data
Avoid exposing internal DB fields in sort
Use consistent response format
```

---

# Interview-Ready Paragraph Answer

I handle pagination by returning data in small chunks instead of returning the full list. For normal list APIs, I usually use offset-based pagination with parameters like `page` and `size`, for example `GET /orders?page=0&size=20`. I also return metadata like current page, page size, total records, total pages, and whether it is the last page. For large datasets, feeds, logs, or transaction history, I prefer cursor-based pagination because it performs better than large offset values. I always apply pagination at the database level, not in Java after fetching all records. I also add stable sorting, usually by `createdAt` and `id`, and create proper database indexes for those fields. I set a default page size and maximum page size to protect the API. I also validate page, size, sort fields, and return `400 Bad Request` for invalid input. In simple words, good pagination makes the API fast, safe, predictable, and easy for the frontend to use.

---

# 10. Offset pagination vs cursor pagination

---
## Summary

Offset pagination and cursor pagination are two ways to fetch list data in parts.

**Offset pagination uses page number and size.**
**Cursor pagination uses the last seen record as the starting point for the next page.**

## One-Line Answer

**Offset pagination is simple and good for normal admin tables, while cursor pagination is faster and safer for large or frequently changing data.**

---

# Simple Meaning

Suppose we have many orders.

We do not want to return all orders at once.

So we return records page by page.

There are two common ways:

```text
Offset pagination -> page=0&size=20
Cursor pagination -> cursor=lastRecordId&limit=20
```

---

# What Is Offset Pagination?

Offset pagination uses `page` and `size`.

Example:

```http
GET /api/v1/orders?page=0&size=20
```

This means:

```text
Give me first 20 orders.
```

Next page:

```http
GET /api/v1/orders?page=1&size=20
```

This means:

```text
Skip first 20 orders and give next 20.
```

SQL example:

```sql
SELECT *
FROM orders
ORDER BY created_at DESC
LIMIT 20 OFFSET 20;
```

Here:

```text
LIMIT 20  -> return 20 records
OFFSET 20 -> skip first 20 records
```

---

# Offset Pagination Response

```json
{
  "content": [
    {
      "orderId": "ORD101",
      "status": "PAID"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 250,
  "totalPages": 13,
  "first": true,
  "last": false
}
```

This is easy for frontend.

The frontend can show:

```text
Page 1 of 13
Next
Previous
Go to page 5
```

---

# Benefits Of Offset Pagination

Offset pagination is simple.

It is easy to implement.

It is easy for frontend also.

It supports jumping to any page.

Example:

```http
GET /api/v1/orders?page=10&size=20
```

So offset pagination is good for:

```text
Admin dashboards
User management screens
Reports with small or medium data
Tables where user can jump to page number
```

---

# Problems With Offset Pagination

Offset pagination can become slow for large data.

Example:

```http
GET /api/v1/orders?page=50000&size=20
```

SQL may become:

```sql
SELECT *
FROM orders
ORDER BY created_at DESC
LIMIT 20 OFFSET 1000000;
```

Database may need to skip many records before returning the result.

That can be expensive.

Another problem is data change.

Suppose user is on page 1.

Then new records are inserted.

Now user goes to page 2.

Some records may repeat or get missed.

So offset pagination is not always safe for fast-changing data.

---

# What Is Cursor Pagination?

Cursor pagination does not use page number.

It uses a cursor.

Cursor usually points to the last record from the previous page.

Example first request:

```http
GET /api/v1/orders?limit=20
```

Response:

```json
{
  "content": [
    {
      "orderId": "ORD101",
      "createdAt": "2026-05-03T10:30:00"
    },
    {
      "orderId": "ORD102",
      "createdAt": "2026-05-03T10:20:00"
    }
  ],
  "nextCursor": "2026-05-03T10:20:00_ORD102",
  "hasNext": true
}
```

Next request:

```http
GET /api/v1/orders?limit=20&cursor=2026-05-03T10:20:00_ORD102
```

This means:

```text
Give me records after this cursor.
```

---

# Cursor Pagination SQL Example

If sorting is by `created_at DESC` and `id DESC`, query can look like this:

```sql
SELECT *
FROM orders
WHERE (created_at, id) < ('2026-05-03 10:20:00', 102)
ORDER BY created_at DESC, id DESC
LIMIT 20;
```

This avoids large `OFFSET`.

It directly starts after the last seen record.

That is why cursor pagination performs better for large data.

---

# Cursor Pagination Response

```json
{
  "content": [
    {
      "orderId": "ORD103",
      "status": "PAID",
      "createdAt": "2026-05-03T10:15:00"
    }
  ],
  "nextCursor": "2026-05-03T10:15:00_ORD103",
  "hasNext": true
}
```

Usually cursor pagination returns:

```text
content
nextCursor
hasNext
```

It may not return total pages.

---

# Benefits Of Cursor Pagination

Cursor pagination is better for large tables.

It is faster when data size is huge.

It also works better when data changes frequently.

Good examples:

```text
Transaction history
Chat messages
Activity feed
Notifications
Audit logs
Order history with huge data
Infinite scrolling screens
```

It avoids the cost of skipping millions of rows.

---

# Problems With Cursor Pagination

Cursor pagination is a little harder to implement.

Frontend cannot easily jump to page 50.

It usually supports:

```text
Next page
Previous page, if designed
Infinite scroll
Load more
```

It also needs stable sorting.

For example:

```text
ORDER BY created_at DESC, id DESC
```

Why include `id`?

Because many records can have the same `created_at`.

The `id` makes the order stable.

---

# Main Difference

| Point                  | Offset Pagination       | Cursor Pagination         |
| ---------------------- | ----------------------- | ------------------------- |
| Uses                   | `page` and `size`       | `cursor` and `limit`      |
| Example                | `?page=2&size=20`       | `?cursor=abc&limit=20`    |
| Easy to implement      | Very easy               | Medium                    |
| Jump to page number    | Yes                     | Not easy                  |
| Large data performance | Can be slow             | Better                    |
| Fast-changing data     | Can repeat/miss records | More stable               |
| Total count            | Easy to show            | Usually not shown         |
| Best for               | Admin tables            | Feeds, logs, transactions |
| SQL style              | `LIMIT OFFSET`          | `WHERE id > cursor LIMIT` |

---

# Offset Pagination Example

Use offset when user needs numbered pages.

Example:

```http
GET /api/v1/users?page=0&size=20
GET /api/v1/users?page=1&size=20
GET /api/v1/users?page=2&size=20
```

This is good for admin panel.

The user may want:

```text
Go to page 1
Go to page 5
Go to last page
```

Offset pagination supports this very well.

---

# Cursor Pagination Example

Use cursor when user keeps scrolling.

Example:

```http
GET /api/v1/transactions?limit=20
```

Response gives:

```json
{
  "nextCursor": "TXN123",
  "hasNext": true
}
```

Next call:

```http
GET /api/v1/transactions?limit=20&cursor=TXN123
```

This is good for mobile apps and infinite scroll.

---

# Important Point: Sorting Is Required In Both

Pagination without sorting is dangerous.

Bad:

```sql
SELECT *
FROM orders
LIMIT 20 OFFSET 0;
```

Database may return data in any order.

Good:

```sql
SELECT *
FROM orders
ORDER BY created_at DESC, id DESC
LIMIT 20 OFFSET 0;
```

For cursor pagination, stable sorting is even more important.

Good cursor sort:

```text
createdAt + id
```

Because `createdAt` alone may not be unique.

---

# Performance Difference

Offset pagination becomes slower as page number increases.

Example:

```text
page 1    -> fast
page 100  -> okay
page 50000 -> slow
```

Because database has to skip more records.

Cursor pagination stays more stable.

Example:

```text
first page -> fast
next page  -> fast
next page  -> fast
```

Because it uses indexed fields to move forward.

But cursor pagination needs proper indexes.

Example:

```sql
CREATE INDEX idx_orders_created_at_id
ON orders (created_at, id);
```

---

# Total Count Difference

Offset pagination often returns total count.

Example:

```json
{
  "totalElements": 2500,
  "totalPages": 125
}
```

This is useful for admin UI.

But total count can be expensive on very large tables.

Cursor pagination usually avoids total count.

It returns:

```json
{
  "hasNext": true,
  "nextCursor": "abc123"
}
```

This is faster and enough for infinite scroll.

---

# Which One Should We Use?

Use **offset pagination** when:

```text
Data is small or medium
Admin needs page numbers
User needs jump to page
Total count is required
Implementation should be simple
```

Use **cursor pagination** when:

```text
Data is very large
Records change frequently
API is used for infinite scroll
Performance matters a lot
Duplicate/missing records should be reduced
```

---

# Practical Backend Choice

For normal user listing:

```http
GET /api/v1/users?page=0&size=20
```

Offset is fine.

For transaction history:

```http
GET /api/v1/transactions?limit=20&cursor=TXN123
```

Cursor is better.

For chat messages:

```http
GET /api/v1/chats/{chatId}/messages?limit=50&before=MSG999
```

Cursor is better.

For admin reports:

```http
GET /api/v1/reports?page=2&size=50
```

Offset is usually fine.

---

# Common Mistakes

## Mistake 1: Returning All Data

Never return all records from a large table.

Always paginate list APIs.

---

## Mistake 2: No Maximum Page Size

Bad:

```http
GET /orders?size=100000
```

This can overload the system.

Better:

```text
Default size = 20
Maximum size = 100
```

---

## Mistake 3: No Stable Sorting

Without sorting, records can repeat or appear in random order.

Always sort by stable fields.

---

## Mistake 4: Using Offset For Huge Feeds

For large feeds, activity logs, and transaction history, cursor is usually better.

---

## Mistake 5: Exposing Raw Cursor Carelessly

Cursor should be safe.

Many teams encode cursor as Base64.

Example cursor data:

```json
{
  "createdAt": "2026-05-03T10:20:00",
  "id": 102
}
```

Encoded cursor:

```text
eyJjcmVhdGVkQXQiOiIyMDI2LTA1LTAzVDEwOjIwOjAwIiwiaWQiOjEwMn0=
```

This hides internal structure a bit and keeps URL cleaner.

---

# Best Practices

```text
Always paginate large list APIs
Use offset for simple tables
Use cursor for large and changing data
Always use stable sorting
Set default and max page size
Add database indexes
Validate page, size, limit, and cursor
Do not sort by unsafe fields
Return consistent response format
Avoid expensive total count for very large data
```

---

# Interview-Ready Paragraph Answer

Offset pagination uses `page` and `size`, like `GET /orders?page=0&size=20`. It is simple, easy to implement, and good for admin screens where the user wants page numbers or wants to jump to a specific page. But it can become slow for very large data because the database has to skip many rows using `OFFSET`. It can also repeat or miss records if new data is inserted while the user is moving between pages. Cursor pagination uses a cursor, usually based on the last record from the previous page, like `GET /orders?limit=20&cursor=abc`. It is better for large datasets, infinite scroll, feeds, logs, chat messages, and transaction history because it avoids large offsets and works better with changing data. The trade-off is that cursor pagination is harder to implement and does not easily support jumping to page number 50. In real systems, I use offset pagination for simple admin tables and cursor pagination for large, high-traffic, or frequently changing data.

---

# 11. How do you version APIs?

---
## Summary

API versioning means changing an API safely without breaking old clients.

The main goal is simple:

```text
Old clients should keep working.
New clients should get the new behavior.
```

## One-Line Answer

**I version APIs by creating a new version only for breaking changes, usually using URL versioning like `/api/v1/users` and `/api/v2/users`, while keeping old versions stable until clients migrate.**

---

# Why API Versioning Is Needed

Once an API is used by frontend, mobile app, or another service, we should not change it suddenly.

Example old response:

```json
{
  "userId": 101,
  "name": "Ravi"
}
```

If we suddenly change it to:

```json
{
  "id": 101,
  "fullName": "Ravi Kumar"
}
```

Old clients may break.

Because they may still be reading:

```text
userId
name
```

So instead of changing the existing API directly, we create a new version.

---

# Common Versioning Approaches

## 1. URL Versioning

This is the most common and simple approach.

```http
GET /api/v1/users/101
GET /api/v2/users/101
```

This is easy to understand.

It is also easy to test in Postman.

Example:

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserV1Controller {

    @GetMapping("/{id}")
    public UserV1Response getUser(@PathVariable Long id) {
        return new UserV1Response(id, "Ravi Kumar");
    }
}
```

```java
@RestController
@RequestMapping("/api/v2/users")
public class UserV2Controller {

    @GetMapping("/{id}")
    public UserV2Response getUser(@PathVariable Long id) {
        return new UserV2Response(id, "Ravi", "Kumar", "ACTIVE");
    }
}
```

I normally prefer this approach because it is clear and practical.

---

## 2. Header Versioning

Here the URL stays the same.

Version is passed in header.

```http
GET /api/users/101
X-API-Version: 1
```

or:

```http
GET /api/users/101
X-API-Version: 2
```

This keeps the URL clean.

But it is slightly harder to test and debug because version is hidden in headers.

---

## 3. Query Parameter Versioning

Version is passed as query parameter.

```http
GET /api/users/101?version=1
GET /api/users/101?version=2
```

This is simple.

But I usually avoid it for large production APIs because URL versioning is cleaner.

---

## 4. Media Type Versioning

Version is passed in `Accept` header.

```http
GET /api/users/101
Accept: application/vnd.company.user.v1+json
```

This is more REST-style.

But it is more complex for many teams.

So I use it only if the company already follows this standard.

---

# My Preferred Approach

For most backend systems, I prefer URL versioning.

Example:

```http
/api/v1/orders
/api/v2/orders
```

Because it is:

```text
Simple
Visible
Easy to test
Easy to document
Easy for frontend teams
Easy for API Gateway routing
Easy for Swagger/OpenAPI
```

---

# When Do We Create A New Version?

Create a new version when there is a breaking change.

Breaking changes are:

```text
Removing a response field
Renaming a response field
Changing field data type
Changing request body structure
Adding a new required request field
Changing error response format
Changing API behavior heavily
Changing business meaning of a field
```

Example:

Old response:

```json
{
  "name": "Ravi Kumar"
}
```

New response:

```json
{
  "firstName": "Ravi",
  "lastName": "Kumar"
}
```

This is a breaking change.

So we should create:

```http
GET /api/v2/users/101
```

And keep:

```http
GET /api/v1/users/101
```

for old clients.

---

# What Changes Do Not Need A New Version?

Not every change needs a new version.

These are usually safe changes:

```text
Adding a new optional response field
Adding a new optional request field
Adding a new endpoint
Improving performance
Changing internal database logic
Fixing a bug without changing contract
```

Example:

Old response:

```json
{
  "id": 101,
  "name": "Ravi"
}
```

New response:

```json
{
  "id": 101,
  "name": "Ravi",
  "status": "ACTIVE"
}
```

Adding `status` is usually safe.

Old clients can ignore unknown fields.

So we may not need a new version.

---

# Good API Versioning Design

A clean design is:

```text
Controller handles API version
DTO handles response contract
Service handles business logic
Mapper converts entity to v1/v2 response
```

Flow:

```text
UserV1Controller
        ↓
UserService
        ↓
UserV1Mapper
        ↓
UserV1Response
```

```text
UserV2Controller
        ↓
UserService
        ↓
UserV2Mapper
        ↓
UserV2Response
```

The business logic should not be copied everywhere.

Only API contract should change.

---

# Example

Suppose v1 returns full name.

```json
{
  "id": 101,
  "name": "Ravi Kumar"
}
```

v2 returns first name and last name separately.

```json
{
  "id": 101,
  "firstName": "Ravi",
  "lastName": "Kumar",
  "status": "ACTIVE"
}
```

Then we can have separate DTOs.

```java
public class UserV1Response {

    private Long id;
    private String name;

    public UserV1Response(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters
}
```

```java
public class UserV2Response {

    private Long id;
    private String firstName;
    private String lastName;
    private String status;

    public UserV2Response(Long id, String firstName, String lastName, String status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }

    // getters
}
```

Both can use the same service.

Only response mapping is different.

---

# Deprecation Plan

Versioning is not only creating v2.

We also need to manage old versions.

A good deprecation plan:

```text
Keep v1 running
Mark v1 as deprecated
Inform frontend/mobile/partner teams
Add migration guide
Monitor v1 usage
Set removal date
Remove v1 only after clients migrate
```

Sometimes we can add response headers:

```http
Deprecation: true
Sunset: Wed, 30 Sep 2026 23:59:59 GMT
```

This tells clients that the old API will be removed later.

---

# API Documentation

Each version should be documented clearly.

In Swagger/OpenAPI, document:

```text
Endpoint
Request body
Response body
Status codes
Error response
Auth requirements
Deprecated fields
Migration notes
```

Good documentation avoids confusion for frontend and other teams.

---

# Versioning In Microservices

Internal APIs also need versioning.

Even if another service is the client, we should not break it suddenly.

Good practice:

```text
Keep backward compatibility
Use contract testing
Communicate breaking changes early
Support old version during migration
Remove old version only after consumers move
```

---

# Versioning Events Also

API versioning is not only for REST.

Kafka events also need versioning.

Example v1 event:

```json
{
  "eventType": "ORDER_CREATED",
  "orderId": "ORD123",
  "amount": 1000
}
```

v2 event:

```json
{
  "schemaVersion": 2,
  "eventType": "ORDER_CREATED",
  "orderId": "ORD123",
  "amount": 1000,
  "currency": "INR"
}
```

Adding optional fields is usually safe.

But removing or renaming fields can break consumers.

So events also need schema discipline.

---

# Common Mistakes

## 1. Creating Version For Every Small Change

Do not create `/v2` just because you added one optional field.

That is usually backward compatible.

---

## 2. Breaking Existing Version

Never remove or rename fields in existing API suddenly.

Old clients may fail.

---

## 3. Copying Full Logic In Each Version

Do not duplicate full service logic for v1 and v2.

Keep business logic common.

Change only controller, DTO, and mapper where needed.

---

## 4. No Deprecation Plan

Creating new version is easy.

Removing old version safely is the real work.

---

# Best Practices

```text
Use versioning only for breaking changes
Prefer backward-compatible changes
Keep old versions stable
Use separate DTOs for each version
Reuse common service logic
Document each version clearly
Add deprecation plan
Monitor old version usage
Use contract tests
Version events also
```

---

# Interview-Ready Paragraph Answer

I version APIs to make changes without breaking existing clients. My preferred approach is URL versioning, like `/api/v1/users` and `/api/v2/users`, because it is simple, visible, easy to test, and easy to document. I create a new version only when there is a breaking change, such as removing a field, renaming a field, changing a data type, changing request structure, or changing API behavior. For backward-compatible changes, like adding an optional response field or adding a new endpoint, I usually do not create a new version. In implementation, I keep separate controllers or DTOs for different versions, but I try to reuse the same service layer so business logic is not duplicated. I also document every version, add a deprecation plan for old APIs, monitor usage, and remove old versions only after all clients migrate.

---

# 12. How do you handle API failures?

---
## Summary

API failures should be handled in a controlled way.

The API should not crash silently.
It should return the right status code, clear error response, proper logs, and safe recovery where possible.

## One-Line Answer

**I handle API failures by using proper status codes, global exception handling, clear error responses, logging, retries, timeouts, circuit breakers, fallback, monitoring, and alerts.**

---

# Simple Meaning

An API can fail for many reasons.

Example:

```text
Invalid request
Unauthorized user
Resource not found
Database issue
Downstream timeout
Third-party service failure
Server bug
```

A good backend system should handle these failures properly.

Bad API failure handling looks like this:

```json
{
  "error": "NullPointerException at line 45"
}
```

Good API failure handling looks like this:

```json
{
  "status": 500,
  "errorCode": "INTERNAL_SERVER_ERROR",
  "message": "Something went wrong. Please try again later.",
  "correlationId": "abc-123"
}
```

The client gets a clean response.
The backend team gets enough logs to debug.

---

# Types Of API Failures

API failures can be divided into three main types.

```text
Client-side failures
Server-side failures
Downstream failures
```

---

# 1. Client-Side Failures

These happen because the client sent something wrong.

Examples:

```text
Invalid request body
Missing required field
Invalid email format
Invalid token
No permission
Resource not found
Duplicate request
Business validation failure
```

These should return `4xx` status codes.

Common examples:

| Case                       | Status Code                |
| -------------------------- | -------------------------- |
| Invalid request            | `400 Bad Request`          |
| Missing or invalid token   | `401 Unauthorized`         |
| No permission              | `403 Forbidden`            |
| Resource not found         | `404 Not Found`            |
| Duplicate/conflict         | `409 Conflict`             |
| Business validation failed | `422 Unprocessable Entity` |
| Rate limit crossed         | `429 Too Many Requests`    |

---

# 2. Server-Side Failures

These happen because something failed inside our service.

Examples:

```text
NullPointerException
Unexpected database error
Code bug
Configuration issue
Unknown failure
```

These should return `5xx` status codes.

Most common:

```http
500 Internal Server Error
```

But we should not expose internal details to the client.

Bad response:

```json
{
  "message": "NullPointerException in PaymentService.java line 89"
}
```

Better response:

```json
{
  "status": 500,
  "errorCode": "INTERNAL_SERVER_ERROR",
  "message": "Something went wrong. Please try again later."
}
```

Internal details should go to logs, not API response.

---

# 3. Downstream Failures

These happen when our service calls another service and that service fails.

Examples:

```text
Payment gateway timeout
Inventory service is down
Redis is unavailable
Database connection timeout
Third-party API gives invalid response
```

Common status codes:

| Case                         | Status Code               |
| ---------------------------- | ------------------------- |
| Downstream timeout           | `504 Gateway Timeout`     |
| Downstream unavailable       | `503 Service Unavailable` |
| Bad response from downstream | `502 Bad Gateway`         |
| Request accepted for later   | `202 Accepted`            |

Example:

```text
Order Service calls Payment Service.
Payment Service does not respond in time.
Return 504 or mark payment as pending, depending on business flow.
```

---

# Use Global Exception Handling

In Spring Boot, I would use `@RestControllerAdvice`.

This keeps error handling in one place.

Example:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        List<FieldErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(
                400,
                "VALIDATION_FAILED",
                "Request validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFound(
            ResourceNotFoundException ex
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                404,
                "RESOURCE_NOT_FOUND",
                ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex
    ) {
        ApiErrorResponse response = new ApiErrorResponse(
                500,
                "INTERNAL_SERVER_ERROR",
                "Something went wrong. Please try again later.",
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
```

This makes error responses consistent across APIs.

---

# Standard Error Response

I prefer one common error response format.

Example:

```json
{
  "status": 400,
  "errorCode": "VALIDATION_FAILED",
  "message": "Request validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email format is invalid"
    }
  ],
  "correlationId": "abc-123",
  "timestamp": "2026-05-03T10:30:00"
}
```

Important fields:

```text
status
errorCode
message
field errors
correlationId
timestamp
```

This helps frontend, mobile app, and support team.

---

# Logging API Failures

For every failure, logs should have enough information.

Useful fields:

```text
correlationId
requestId
userId if available
API path
HTTP method
status code
error code
exception class
downstream service name
latency
```

But do not log sensitive data.

Never log:

```text
password
OTP
access token
refresh token
Authorization header
card number
CVV
UPI PIN
secret keys
```

Good log example:

```text
correlationId=abc-123 path=/api/v1/payments status=504 error=PAYMENT_GATEWAY_TIMEOUT latency=3000ms
```

---

# Use Correlation ID

Every API request should have a correlation ID.

Example:

```http
X-Correlation-ID: abc-123
```

This ID should be added to:

```text
Logs
Downstream API calls
Kafka messages
Error response
APM traces
```

Why?

Because one request can travel through many services.

Example:

```text
API Gateway
  ↓
Order Service
  ↓
Payment Service
  ↓
Notification Service
```

Correlation ID helps us find the full request flow in logs.

---

# Handle Downstream Failures Safely

When a downstream service fails, we should not let it break everything.

Use:

```text
Timeout
Retry
Circuit breaker
Fallback
Bulkhead
Async processing
```

---

# Timeout

Every external call should have a timeout.

Example:

```text
Do not wait forever for Payment Service.
Wait only 3 seconds.
```

Without timeout:

```text
Threads get blocked
Connection pool gets full
API becomes slow
System can go down
```

---

# Retry

Retry only temporary failures.

Good retry cases:

```text
Timeout
Connection reset
503 Service Unavailable
504 Gateway Timeout
Temporary network issue
```

Do not retry:

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
422 Business validation error
```

Use limited retries.

Example:

```text
Retry 2 times
Use backoff
Use jitter
```

Do not retry forever.

---

# Circuit Breaker

If a downstream service is failing again and again, circuit breaker stops calls for some time.

Example:

```text
Payment Service is failing.
Order Service stops calling it for 30 seconds.
Fallback or fast failure is returned.
```

This protects the caller service.

---

# Fallback

Fallback means a safe backup response.

Good fallback examples:

```text
Return cached product data
Return empty recommendations
Return partial response
Mark payment as PENDING
Queue request for later processing
```

Bad fallback examples:

```text
Payment failed, so mark payment SUCCESS
Auth service failed, so allow access
Inventory service failed, so assume stock is available
```

Fallback must be safe and honest.

---

# Use Idempotency For Retry-Safe APIs

For APIs like payment, refund, order creation, and fund transfer, retry can be dangerous.

Example:

```text
Payment API times out.
Client retries.
Without idempotency, user may be charged twice.
```

Use idempotency key:

```http
POST /api/v1/payments
Idempotency-Key: abc-123
```

If same request comes again, return old response.
Do not process again.

---

# Handle Partial Failures

Sometimes the main operation succeeds, but side work fails.

Example:

```text
Order created successfully.
Email sending failed.
```

In this case, do not fail the order API only because email failed.

Better:

```text
Save order
Publish ORDER_CREATED event
Email service sends email asynchronously
Retry email later if it fails
```

This keeps the main API stable.

---

# Use Dead Letter Queue For Async Failures

In async systems, message processing can fail.

Example:

```text
Kafka consumer fails to process PaymentSuccess event.
```

Good approach:

```text
Retry limited times
Move to DLQ after failure
Log reason
Alert team
Fix and replay if needed
```

DLQ means Dead Letter Queue.

It stores failed messages for later investigation.

---

# Monitoring And Alerts

Handling failures is not only about returning a response.

We also need to know when failures are increasing.

Track metrics like:

```text
API error rate
5xx count
4xx count
Latency
P95 and P99 response time
Timeout count
Retry count
Circuit breaker open count
Downstream failure count
DLQ message count
```

Add alerts for important issues.

Example:

```text
Payment API 5xx rate > 2%
Order API latency P95 > 2 seconds
DLQ messages > 100
Payment gateway timeout count increasing
```

---

# Do Not Hide All Failures As 200

This is a common bad practice.

Bad response:

```http
200 OK
```

```json
{
  "success": false,
  "message": "User not found"
}
```

Better:

```http
404 Not Found
```

```json
{
  "errorCode": "USER_NOT_FOUND",
  "message": "User not found"
}
```

Use proper HTTP status codes.

---

# Fail Fast Where Needed

If a required dependency is unavailable, do not keep waiting too long.

Example:

```text
Payment gateway is down.
Circuit is open.
Return fast response instead of making user wait.
```

For non-critical dependencies, return partial response.

For critical dependencies, return proper error or pending state.

---

# Security In API Failures

Error responses should not leak sensitive data.

Do not expose:

```text
Stack trace
Database table name
SQL query
Internal service URL
Secret key
Token details
Server path
```

Good message:

```text
Something went wrong. Please try again later.
```

Detailed error should be in internal logs.

---

# Practical Example

Suppose create-order API does these things:

```text
Validate request
Check inventory
Create order
Initiate payment
Send notification
```

Failure handling can be:

```text
Invalid request -> 400
Product not found -> 404
Out of stock -> 409 or 422
Payment timeout -> mark payment PENDING or return 202
Notification failure -> do not fail order, retry async
Unexpected error -> 500
```

This is how real backend APIs are designed.

---

# Common Mistakes

## 1. Returning 500 For Every Error

Validation failure is not 500.
Resource not found is not 500.
Unauthorized access is not 500.

Use correct status codes.

---

## 2. Exposing Stack Trace

Never expose stack trace to clients.

It can leak internal details.

---

## 3. No Timeout

Without timeout, slow downstream calls can block the whole service.

---

## 4. Retrying Everything

Do not retry validation errors or unauthorized requests.

Retry only temporary failures.

---

## 5. No Idempotency

Retrying payment or order creation without idempotency can create duplicate side effects.

---

## 6. No Monitoring

If failures are not monitored, the team finds out only after users complain.

---

# Best Practices

```text
Use global exception handling
Return proper HTTP status codes
Use consistent error response format
Add correlation ID
Log errors with useful context
Do not expose internal details
Set timeouts for all external calls
Retry only temporary failures
Use circuit breaker for unstable dependencies
Use fallback only when safe
Use idempotency for critical write APIs
Use async processing for non-critical side effects
Use DLQ for failed async messages
Monitor errors, latency, and downstream failures
Add alerts for critical APIs
```

---

# Interview-Ready Paragraph Answer

I handle API failures by first classifying the failure properly. If the client sends invalid data, I return a `4xx` status like `400`, `401`, `403`, `404`, `409`, or `422` depending on the case. If the failure is inside our service, I return `500`. If a downstream service is unavailable or times out, I return `503` or `504`, or I use a safe fallback if possible. In Spring Boot, I use global exception handling with `@RestControllerAdvice` so all APIs return a consistent error format with status, error code, message, timestamp, and correlation ID. I also log failures with correlation ID, request path, user ID if available, latency, and exception details, but I never log sensitive data like password, token, OTP, or card details. For downstream failures, I use timeout, limited retry with backoff, circuit breaker, and fallback where safe. For critical write APIs like payment or order creation, I use idempotency so retries do not create duplicate side effects. I also monitor error rate, latency, timeout count, retry count, and circuit breaker state with proper alerts.

---

# 13. How do you retry safely?

---
## Summary

Retry safely means:

**Retry only when the failure is temporary, retry only a few times, wait between retries, and make sure retry does not create duplicate side effects.**

Retries are useful.
But bad retries can make the system worse.

---

## One-Line Answer

**I retry safely by retrying only temporary failures, using limited attempts, timeout, exponential backoff, jitter, circuit breaker, and idempotency for write operations.**

---

# Simple Meaning

Retry means calling again after failure.

Example:

```text
Order Service calls Payment Service
Payment Service times out
Order Service tries again
```

This can be helpful if the issue was temporary.

But retry can also be dangerous.

Example:

```text
Payment request timed out
Backend retries payment again
User gets charged twice
```

So retry must be designed carefully.

---

# 1. Retry Only Temporary Failures

Not every failure should be retried.

## Retry These

Retry is usually safe for temporary problems like:

```text
Network timeout
Connection reset
503 Service Unavailable
504 Gateway Timeout
Temporary DNS issue
Temporary database deadlock
429 Too Many Requests after delay
```

These may succeed after some time.

---

## Do Not Retry These

Do not retry client-side or permanent errors:

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
409 Conflict in many cases
422 Business validation error
Invalid input
Invalid token
Insufficient balance
```

Why?

Because retrying will not fix bad input.

Example:

```text
Email format is wrong
Retrying 5 times will still fail
```

---

# 2. Use Limited Retry Count

Never retry forever.

Bad:

```text
Retry until success
```

Good:

```text
Retry maximum 2 or 3 times
```

Example:

```text
Attempt 1 failed
Attempt 2 failed
Attempt 3 failed
Stop retrying
Return error or fallback
```

Unlimited retry can overload the system.

---

# 3. Use Backoff

Backoff means waiting before retrying.

Bad retry:

```text
Retry immediately
Retry immediately
Retry immediately
```

Good retry:

```text
Retry after 200 ms
Retry after 500 ms
Retry after 1 second
```

This gives the downstream service some time to recover.

---

# 4. Use Jitter

Jitter means adding small random delay.

Without jitter:

```text
1000 requests fail
All retry after exactly 1 second
Downstream gets hit again at the same time
```

With jitter:

```text
Some retry after 800 ms
Some retry after 1.1 seconds
Some retry after 1.4 seconds
```

This spreads the load.

It helps avoid a retry storm.

---

# 5. Use Timeout With Retry

Retry without timeout is dangerous.

Each try should have a timeout.

Also, the full operation should have a total timeout budget.

Example:

```text
Total API timeout = 3 seconds
Attempt 1 timeout = 800 ms
Attempt 2 timeout = 800 ms
Attempt 3 timeout = 800 ms
Some time for backoff
```

Do not design like this:

```text
Frontend waits 5 seconds
Backend retry logic takes 30 seconds
```

That is bad user experience.

---

# 6. Use Idempotency For Write APIs

This is the most important point.

For write operations, retry can create duplicate side effects.

Examples:

```text
Create order
Make payment
Refund payment
Book ticket
Transfer money
Wallet recharge
Generate invoice
```

For these APIs, use idempotency key.

Example:

```http
POST /api/v1/payments
Idempotency-Key: pay-abc-123
```

If the same request comes again, backend should not process it again.

It should return the old result.

Simple rule:

```text
Same key + same request = return old response
Same key + different request = 409 Conflict
```

---

# 7. Do Not Blindly Retry Payments

Payment is a special case.

If payment gateway times out, we do not know what happened.

Maybe payment failed.
Maybe payment succeeded but response was lost.

So do not blindly charge again.

Better flow:

```text
Mark payment as PENDING
Check gateway status
Wait for webhook
Run reconciliation job
```

This avoids double charge.

---

# 8. Use Circuit Breaker

If a downstream service is already failing badly, retries can make it worse.

Circuit breaker protects the system.

Simple meaning:

```text
If many calls are failing, stop calling for some time.
```

Example:

```text
Payment Service is failing
Circuit opens
Order Service stops calling Payment Service for 30 seconds
Fallback or fast failure is returned
```

This avoids retry storm and cascading failure.

---

# 9. Respect Retry-After Header

For `429 Too Many Requests` or some `503` responses, downstream may return:

```http
Retry-After: 30
```

This means:

```text
Try again after 30 seconds.
```

The client should respect it.

Do not retry immediately.

---

# 10. Retry Async Messages Safely

In Kafka, RabbitMQ, or SQS, retries are common.

Good async retry design:

```text
Retry limited times
Use delay between retries
Make consumer idempotent
Move message to DLQ after max retries
Alert the team
```

DLQ means Dead Letter Queue.

It stores messages that failed after all retry attempts.

---

# 11. Make Consumers Idempotent

Messages can be delivered more than once.

So the consumer should handle duplicate messages safely.

Example:

```text
PaymentSuccess event consumed twice
Order should not be marked paid twice
Invoice should not be generated twice
Wallet should not be credited twice
```

Common solution:

```text
Store processed message ID
If same message comes again, skip it
```

---

# 12. Log And Monitor Retries

Retries should be visible.

Log useful details:

```text
correlationId
downstream service name
attempt number
error reason
latency
final result
```

Monitor:

```text
retry count
success after retry
failure after retry
timeout count
circuit breaker open count
DLQ count
```

If retries suddenly increase, it means something is wrong.

---

# Simple Retry Decision Table

| Case                            | Retry?                      |
| ------------------------------- | --------------------------- |
| Network timeout                 | Yes                         |
| Connection reset                | Yes                         |
| 503 Service Unavailable         | Yes                         |
| 504 Gateway Timeout             | Yes                         |
| 429 Too Many Requests           | Yes, after delay            |
| 400 Bad Request                 | No                          |
| 401 Unauthorized                | No                          |
| 403 Forbidden                   | No                          |
| 404 Not Found                   | No                          |
| 422 Validation/business failure | No                          |
| Payment timeout                 | Do not blindly retry charge |

---

# Spring Boot Example With Resilience4j

```java
@Service
public class InventoryClientService {

    private final InventoryClient inventoryClient;

    public InventoryClientService(InventoryClient inventoryClient) {
        this.inventoryClient = inventoryClient;
    }

    @Retry(name = "inventoryService", fallbackMethod = "inventoryFallback")
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
    public InventoryResponse checkInventory(String productId) {
        return inventoryClient.checkInventory(productId);
    }

    public InventoryResponse inventoryFallback(String productId, Throwable ex) {
        InventoryResponse response = new InventoryResponse();
        response.setProductId(productId);
        response.setAvailable(false);
        response.setMessage("Inventory is temporarily unavailable");

        return response;
    }
}
```

Example config:

```yaml
resilience4j:
  retry:
    instances:
      inventoryService:
        max-attempts: 3
        wait-duration: 500ms
        retry-exceptions:
          - java.net.SocketTimeoutException
          - java.io.IOException
        ignore-exceptions:
          - com.example.InvalidRequestException
          - com.example.BusinessValidationException

  circuitbreaker:
    instances:
      inventoryService:
        sliding-window-size: 20
        minimum-number-of-calls: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s
```

Simple meaning:

```text
Try maximum 3 times
Wait 500 ms between retries
Retry only timeout or IO errors
Do not retry validation or business errors
Open circuit if failure rate is too high
```

---

# Common Mistakes

## 1. Retrying Every Error

This is wrong.

Do not retry `400`, `401`, `403`, `404`, or business validation failures.

---

## 2. Retrying Without Idempotency

This can create duplicate orders, duplicate payments, or duplicate refunds.

---

## 3. Retrying Forever

This can overload the system.

Always keep a max retry count.

---

## 4. Retrying Immediately

Immediate retries can hit the failing service again and again.

Use backoff and jitter.

---

## 5. No Circuit Breaker

Retries alone are not enough.

If downstream is down, circuit breaker should stop calls for some time.

---

## 6. No Monitoring

Retry problems are hard to detect without metrics.

Retry count should be monitored.

---

# Best Practices

```text
Retry only temporary failures
Do not retry client errors
Keep retry count small
Use timeout for each attempt
Use total timeout budget
Use exponential backoff
Add jitter
Respect Retry-After header
Use circuit breaker
Use idempotency for write APIs
Make async consumers idempotent
Use DLQ for failed messages
Log retry attempts
Monitor retry metrics
```

---

# Interview-Ready Paragraph Answer

I retry safely by first checking whether the failure is temporary or permanent. I retry only temporary failures like timeout, connection reset, `503`, `504`, or `429` after delay. I do not retry client errors like `400`, `401`, `403`, `404`, or business validation errors like insufficient balance because retrying will not fix them. I keep retry count small, usually two or three attempts, and I use timeout, backoff, and jitter so that retries do not overload the downstream service. I also use circuit breaker when the dependency is failing continuously. For write operations like payment, refund, order creation, or fund transfer, I retry only when idempotency is handled using an idempotency key. For async systems, I use retry topics or delay queues, idempotent consumers, and dead letter queue after max retries. In simple words, safe retry means retry only the right errors, retry only a few times, wait between retries, and never create duplicate side effects.

---

# 14. How do you secure APIs?

---
## Summary

To secure APIs, I protect three things:

```text
Who is calling the API?
Is the caller allowed to do this action?
Is the data safe while moving and while stored?
```

API security is not only login.
It also includes validation, authorization, rate limiting, HTTPS, logging, secrets, and safe error handling.

## One-Line Answer

**I secure APIs using authentication, authorization, HTTPS, input validation, rate limiting, secure headers, token handling, logging, monitoring, and by never exposing sensitive data.**

---

# 1. Use HTTPS

First basic rule:

```text
Always use HTTPS.
```

HTTPS encrypts data between client and server.

Without HTTPS, sensitive data can be exposed.

Example:

```text
Password
Token
OTP
Card details
Personal data
Account data
```

So every production API should run on HTTPS.

---

# 2. Authentication

Authentication means:

```text
Who are you?
```

The API should verify the user before allowing access.

Common authentication methods:

```text
JWT token
Session cookie
OAuth2
OpenID Connect
API key for service calls
mTLS for service-to-service calls
```

For REST APIs, JWT is very common.

Example request:

```http
GET /api/v1/accounts
Authorization: Bearer eyJhbGciOi...
```

Backend validates the token.

If token is missing or invalid:

```http
401 Unauthorized
```

---

# 3. Authorization

Authorization means:

```text
What are you allowed to do?
```

Authentication is not enough.

A user may be logged in, but still not allowed to access everything.

Example:

```text
Normal user should not access admin API.
Customer should not access another customer’s account.
Support user may view data but not delete it.
```

If user is authenticated but not allowed:

```http
403 Forbidden
```

Authorization can be done using:

```text
Roles
Permissions
Resource ownership checks
```

Example:

```java
@PreAuthorize("hasAuthority('account.view')")
@GetMapping("/accounts/{accountId}")
public AccountResponse getAccount(@PathVariable String accountId) {
    return accountService.getAccount(accountId);
}
```

But also check ownership in service layer.

Example:

```java
if (!account.getUserId().equals(loggedInUserId)) {
    throw new AccessDeniedException("Not allowed to access this account");
}
```

This prevents users from changing IDs in URL and accessing others’ data.

---

# 4. Validate Input Properly

Never trust client input.

Validate every request.

Check:

```text
Required fields
Email format
Mobile format
Amount range
Date format
Enum values
Request size
Special characters
Business rules
```

Example:

```java
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must have at least 8 characters")
    private String password;
}
```

For validation failure:

```http
400 Bad Request
```

For business rule failure:

```http
422 Unprocessable Entity
```

---

# 5. Do Not Trust User ID From Client

This is a common security mistake.

Bad request:

```json
{
  "userId": "101",
  "amount": 5000
}
```

The user can change `userId`.

Better:

```text
Get userId from token or security context.
```

For sensitive APIs, never trust user identity from request body.

Example:

```java
String loggedInUserId = securityContext.getLoggedInUserId();
```

Use this user ID for ownership checks.

---

# 6. Use Rate Limiting

Rate limiting protects APIs from abuse.

Example:

```text
Too many login attempts
Too many OTP requests
Too many payment attempts
Too many search requests
```

If limit is crossed:

```http
429 Too Many Requests
```

Example rule:

```text
Only 5 OTP requests per user per 10 minutes
Only 100 API calls per user per minute
```

This helps prevent brute force attacks and system overload.

---

# 7. Protect Passwords

Never store plain passwords.

Bad:

```text
password = "MyPass@123"
```

Good:

```text
Store password hash using BCrypt or Argon2.
```

Spring example:

```java
String passwordHash = passwordEncoder.encode(request.getPassword());
```

Also:

```text
Do not log passwords.
Do not return passwords in response.
Do not send passwords in email.
```

---

# 8. Secure Token Handling

For JWT or access tokens:

```text
Keep access tokens short-lived
Use refresh tokens carefully
Validate signature
Validate expiry
Validate issuer and audience
Do not log tokens
Use token rotation if needed
```

For web apps, tokens should be stored safely.

Common safe option:

```text
HttpOnly Secure Cookie
```

For mobile apps:

```text
Android Keystore
iOS Keychain
```

If token is expired:

```http
401 Unauthorized
```

---

# 9. Use Proper Error Responses

Do not expose internal details.

Bad response:

```json
{
  "message": "NullPointerException at PaymentService.java line 54"
}
```

Better response:

```json
{
  "status": 500,
  "errorCode": "INTERNAL_SERVER_ERROR",
  "message": "Something went wrong. Please try again later.",
  "correlationId": "abc-123"
}
```

Internal details should go to logs, not to the client.

Do not expose:

```text
Stack trace
Database table names
SQL query
Internal service URL
Secret key
Server path
Token details
```

---

# 10. Use Consistent Status Codes

Use correct status codes.

| Case                          | Status Code                 |
| ----------------------------- | --------------------------- |
| Invalid request               | `400 Bad Request`           |
| Missing or invalid token      | `401 Unauthorized`          |
| Valid token but no permission | `403 Forbidden`             |
| Resource not found            | `404 Not Found`             |
| Duplicate/conflict            | `409 Conflict`              |
| Business validation failed    | `422 Unprocessable Entity`  |
| Too many requests             | `429 Too Many Requests`     |
| Server error                  | `500 Internal Server Error` |

Do not return `200 OK` for every failure.

---

# 11. Prevent Injection Attacks

Do not create SQL queries by string concatenation.

Bad:

```java
String sql = "SELECT * FROM users WHERE email = '" + email + "'";
```

Good:

```text
Use JPA, prepared statements, or parameterized queries.
```

Example:

```java
Optional<User> findByEmail(String email);
```

Also validate input before using it in queries.

---

# 12. Protect Sensitive Data In Logs

Logs are very important, but they can become risky.

Never log:

```text
Password
OTP
Access token
Refresh token
Authorization header
Card number
CVV
UPI PIN
Secret keys
Full account number
```

Safe logging:

```text
correlationId
request path
status code
latency
masked user id
masked account number
error code
```

Example:

```text
accountNumber=XXXXXX1234
```

Not:

```text
accountNumber=12345678901234
```

---

# 13. Use API Gateway

API Gateway can handle common security checks.

It can do:

```text
Authentication
Rate limiting
CORS
SSL termination
Request size limit
IP allowlist
Logging
Routing
Basic threat protection
```

But important business authorization should still be checked inside the service.

Example:

```text
Gateway checks token.
Order Service checks order belongs to user.
```

---

# 14. Secure Service-To-Service Calls

Internal APIs should also be secured.

Do not assume internal network is always safe.

Use:

```text
HTTPS or mTLS
Service-to-service tokens
OAuth2 client credentials
Network policies
Private subnets
Topic-level ACLs for Kafka
```

Example:

```text
Order Service should be allowed to call Payment Service.
Notification Service should not be allowed to call refund API.
```

---

# 15. Use CORS Carefully

CORS controls which frontend domains can call your API from browser.

Do not allow everything in production.

Bad:

```text
Access-Control-Allow-Origin: *
```

Better:

```text
Allow only trusted frontend domains.
```

Example:

```text
https://app.example.com
```

---

# 16. Add Idempotency For Critical APIs

For APIs like payment, refund, order creation, and fund transfer, retries can create duplicate actions.

Use idempotency key.

Example:

```http
POST /api/v1/payments
Idempotency-Key: pay-abc-123
```

If the same request comes again, return the previous response.

Do not process again.

This avoids:

```text
Duplicate payment
Duplicate order
Duplicate refund
Duplicate wallet credit
```

---

# 17. Use Auditing

For sensitive actions, keep audit logs.

Example:

```text
Login attempt
Password change
Payment initiated
Refund created
User role changed
Admin action
Account access
```

Audit logs should include:

```text
userId
action
timestamp
IP/device if needed
correlationId
status
```

But again, do not log sensitive secrets.

---

# 18. Monitor And Alert

API security is not complete without monitoring.

Track:

```text
401 count
403 count
429 count
Failed login attempts
Token validation failures
Suspicious IPs
High error rate
Unusual API usage
```

Set alerts for suspicious activity.

Example:

```text
Too many failed login attempts from same IP
Too many OTP requests for same mobile
Sudden spike in 403 errors
```

---

# Simple Spring Security Example

```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/accounts/**").hasAuthority("account.view")
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt());

        return http.build();
    }
}
```

Simple meaning:

```text
Auth APIs are public.
Admin APIs need ADMIN role.
Account APIs need account.view permission.
All other APIs need authentication.
JWT token is used.
```

---

# Common Mistakes

## 1. Only Hiding Buttons On Frontend

Frontend checks are not security.

Backend must check permissions.

---

## 2. Trusting IDs From Request Body

Always get logged-in user details from token or security context.

---

## 3. Logging Tokens And Passwords

This is a serious issue.

Mask sensitive data.

---

## 4. Returning Stack Trace To Client

Never expose internal code details.

---

## 5. No Rate Limiting

Login, OTP, payment, and search APIs can be abused.

---

## 6. No Ownership Check

A user should not access another user’s account just by changing ID in URL.

---

## 7. Weak Password Storage

Do not store plain passwords.
Do not use MD5 or SHA-1 for passwords.

Use BCrypt or Argon2.

---

# Best Practices

```text
Use HTTPS everywhere
Use strong authentication
Use role and permission based authorization
Check resource ownership
Validate all inputs
Do not trust userId from client
Use rate limiting
Hash passwords
Keep tokens short-lived
Do not log sensitive data
Use secure error responses
Use API Gateway
Secure internal service calls
Use CORS carefully
Use idempotency for critical writes
Add audit logs
Monitor suspicious activity
```

---

# Interview-Ready Paragraph Answer

I secure APIs using multiple layers. First, I use HTTPS so data is encrypted in transit. Then I add authentication, usually with JWT, OAuth2, or session-based login, so the API knows who is calling. After authentication, I check authorization using roles, permissions, and resource ownership. For example, a user should not access another user’s account just by changing the ID in the URL. I never trust user ID or permission data from the request body. I validate all input, use proper status codes, and return safe error messages without exposing stack traces or internal details. I also add rate limiting for sensitive APIs like login, OTP, payment, and search. Passwords are stored only as strong hashes like BCrypt, never as plain text. I avoid logging sensitive data like passwords, tokens, OTPs, card numbers, and secrets. For critical APIs like payment or order creation, I use idempotency. For production systems, I also use API Gateway, secure service-to-service communication, audit logs, monitoring, and alerts. In simple words, API security means authenticate the caller, authorize the action, validate the input, protect the data, and monitor misuse.

---

# 15. How do you validate request payloads?

---
## Summary

Request payload validation means checking the request body before processing it.

The goal is simple:

```text
Do not allow bad, missing, unsafe, or invalid data to enter business logic.
```

In Spring Boot, I usually validate payloads using DTO classes, Bean Validation annotations, custom validators, and service-level business validation.

## One-Line Answer

**I validate request payloads by using request DTOs with validation annotations like `@NotNull`, `@NotBlank`, `@Email`, `@Size`, and `@Pattern`, then I handle validation errors globally using `@RestControllerAdvice`.**

---

# Simple Meaning

Suppose the client calls this API:

```http
POST /api/v1/users
```

Request body:

```json
{
  "name": "",
  "email": "wrong-email",
  "age": -5
}
```

This request should not reach the main business logic.

The backend should reject it early with:

```http
400 Bad Request
```

And return a clear error response.

---

# Why Request Validation Is Important

Validation protects the API from bad input.

It helps with:

```text
Data correctness
Security
Cleaner business logic
Better error messages
Avoiding database errors
Avoiding invalid state in system
```

Without validation, bad data may enter the database.

Example:

```text
Blank name
Wrong email
Negative amount
Invalid date
Invalid enum value
Invalid mobile number
Very large text input
```

---

# Types Of Validation

There are mainly two types:

```text
Field-level validation
Business-level validation
```

---

# 1. Field-Level Validation

This checks the basic structure of the request.

Examples:

```text
Name should not be blank
Email should be valid
Amount should be positive
Password should have minimum length
Mobile number should be 10 digits
Date should be in correct format
```

This is usually done in DTO using annotations.

---

# 2. Business-Level Validation

This checks business rules.

Examples:

```text
User email should be unique
Account should be active
Balance should be enough
Order should not already be cancelled
Transfer amount should be within daily limit
User should be eligible for this offer
```

This is usually done in the service layer.

Field validation failure usually returns:

```http
400 Bad Request
```

Business validation failure can return:

```http
422 Unprocessable Entity
```

or sometimes:

```http
400 Bad Request
```

depending on company standard.

---

# Spring Boot Request DTO Example

```java
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name cannot be more than 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email format is invalid")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    private String password;

    // getters and setters
}
```

---

# Controller Example

Use `@Valid` with `@RequestBody`.

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        CreateUserResponse response = userService.createUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
```

Here, Spring validates `CreateUserRequest` before calling the service method.

If validation fails, Spring throws `MethodArgumentNotValidException`.

---

# Common Validation Annotations

| Annotation  | Use                                 |
| ----------- | ----------------------------------- |
| `@NotNull`  | Value must not be null              |
| `@NotBlank` | String must not be null or empty    |
| `@NotEmpty` | Collection/string must not be empty |
| `@Email`    | Valid email format                  |
| `@Size`     | Minimum or maximum length           |
| `@Min`      | Minimum number                      |
| `@Max`      | Maximum number                      |
| `@Positive` | Number must be positive             |
| `@Pattern`  | Must match regex                    |
| `@Past`     | Date must be in past                |
| `@Future`   | Date must be in future              |
| `@Valid`    | Validate nested object              |

---

# Example For Payment Request

```java
public class PaymentRequest {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    @Pattern(regexp = "INR|USD", message = "Currency must be INR or USD")
    private String currency;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    // getters and setters
}
```

For money, avoid `double`.

Use:

```text
BigDecimal
```

or store amount in paise/cents as `long`.

---

# Nested Object Validation

Suppose request has address inside user.

```json
{
  "name": "Ravi",
  "email": "ravi@example.com",
  "address": {
    "city": "",
    "pincode": "123"
  }
}
```

DTO:

```java
public class CreateUserRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @Valid
    @NotNull(message = "Address is required")
    private AddressRequest address;

    // getters and setters
}
```

Address DTO:

```java
public class AddressRequest {

    @NotBlank(message = "City is required")
    private String city;

    @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
    private String pincode;

    // getters and setters
}
```

Important point:

```text
Use @Valid on nested objects.
```

Otherwise nested validation may not run.

---

# List Validation

Suppose request contains multiple items.

```json
{
  "items": [
    {
      "productId": "P101",
      "quantity": 2
    }
  ]
}
```

DTO:

```java
public class CreateOrderRequest {

    @NotEmpty(message = "At least one item is required")
    @Valid
    private List<OrderItemRequest> items;

    // getters and setters
}
```

Item DTO:

```java
public class OrderItemRequest {

    @NotBlank(message = "Product ID is required")
    private String productId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    // getters and setters
}
```

---

# Global Exception Handler

Use `@RestControllerAdvice` to return a clean validation error response.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex
    ) {
        List<FieldErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new FieldErrorResponse(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(
                400,
                "VALIDATION_FAILED",
                "Request validation failed",
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }
}
```

Error DTO:

```java
public class ApiErrorResponse {

    private int status;
    private String errorCode;
    private String message;
    private List<FieldErrorResponse> errors;

    public ApiErrorResponse(int status, String errorCode, String message,
                            List<FieldErrorResponse> errors) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.errors = errors;
    }

    // getters
}
```

Field error DTO:

```java
public class FieldErrorResponse {

    private String field;
    private String message;

    public FieldErrorResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }

    // getters
}
```

---

# Sample Validation Error Response

```json
{
  "status": 400,
  "errorCode": "VALIDATION_FAILED",
  "message": "Request validation failed",
  "errors": [
    {
      "field": "email",
      "message": "Email format is invalid"
    },
    {
      "field": "mobileNumber",
      "message": "Mobile number must be 10 digits"
    }
  ]
}
```

This is much better than returning a generic error.

Frontend can show proper field-level messages.

---

# Custom Validation

Sometimes built-in annotations are not enough.

Example:

```text
Password must contain uppercase, lowercase, number, and special character.
```

We can create a custom annotation.

```java
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StrongPasswordValidator.class)
public @interface StrongPassword {

    String message() default "Password is not strong enough";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
```

Validator:

```java
public class StrongPasswordValidator
        implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isBlank()) {
            return false;
        }

        return password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[^a-zA-Z0-9].*");
    }
}
```

Usage:

```java
@StrongPassword
private String password;
```

---

# Service-Level Business Validation

Some checks should not be in DTO.

Example:

```text
Email already exists
Account is inactive
Amount is greater than available balance
Order cannot be cancelled after shipping
```

These need database or business context.

Example:

```java
@Transactional
public CreateUserResponse createUser(CreateUserRequest request) {

    String email = request.getEmail().trim().toLowerCase();

    if (userRepository.existsByEmail(email)) {
        throw new ConflictException("User already exists with this email");
    }

    User user = new User();
    user.setName(request.getName().trim());
    user.setEmail(email);

    User savedUser = userRepository.save(user);

    return userMapper.toResponse(savedUser);
}
```

For duplicate email, return:

```http
409 Conflict
```

Because it conflicts with existing data.

---

# Validate Headers Also

Payload validation is not enough.

Sometimes we also validate headers.

Example:

```http
Idempotency-Key: abc-123
X-Correlation-ID: req-123
Authorization: Bearer token
```

For critical APIs like payment:

```java
@PostMapping("/payments")
public ResponseEntity<PaymentResponse> createPayment(
        @RequestHeader("Idempotency-Key") String idempotencyKey,
        @Valid @RequestBody PaymentRequest request
) {
    PaymentResponse response = paymentService.createPayment(idempotencyKey, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
}
```

If idempotency key is missing:

```http
400 Bad Request
```

---

# Validate Query Params And Path Variables

Example:

```java
@GetMapping("/orders")
public ResponseEntity<List<OrderResponse>> getOrders(
        @RequestParam(defaultValue = "0") @Min(0) int page,
        @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
) {
    return ResponseEntity.ok(orderService.getOrders(page, size));
}
```

For invalid page or size:

```http
400 Bad Request
```

---

# Security Validation

Validation also helps security.

We should check:

```text
Request size
Allowed file type
Allowed sort fields
Allowed enum values
Special characters where needed
SQL injection safety
XSS-sensitive fields
```

Important point:

```text
Do not build SQL using string concatenation.
Use prepared statements, JPA, or parameterized queries.
```

Bad:

```java
String sql = "SELECT * FROM users WHERE email = '" + email + "'";
```

Good:

```java
Optional<User> findByEmail(String email);
```

---

# Validation Status Codes

| Case                        | Status Code                |
| --------------------------- | -------------------------- |
| Missing required field      | `400 Bad Request`          |
| Invalid email format        | `400 Bad Request`          |
| Invalid JSON                | `400 Bad Request`          |
| Invalid path/query param    | `400 Bad Request`          |
| Duplicate email/mobile      | `409 Conflict`             |
| Business validation failure | `422 Unprocessable Entity` |
| Unauthorized request        | `401 Unauthorized`         |
| Forbidden action            | `403 Forbidden`            |

---

# Common Mistakes

## 1. Validating Entity Directly

Do not expose entity as request payload.

Bad:

```java
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    return userRepository.save(user);
}
```

Better:

```text
Use request DTO.
Map DTO to entity.
Return response DTO.
```

This avoids exposing internal database fields.

---

## 2. Only Validating In Frontend

Frontend validation is useful for user experience.

But backend validation is mandatory.

Anyone can call API directly using Postman or curl.

---

## 3. Not Validating Nested Objects

If request has nested fields, use `@Valid`.

---

## 4. Returning Generic Error

Bad:

```json
{
  "message": "Bad request"
}
```

Better:

```json
{
  "errorCode": "VALIDATION_FAILED",
  "errors": [
    {
      "field": "email",
      "message": "Email format is invalid"
    }
  ]
}
```

---

## 5. Not Trimming Or Normalizing Input

For email, usually normalize before saving.

Example:

```java
String email = request.getEmail().trim().toLowerCase();
```

This avoids duplicate issues like:

```text
RAVI@EXAMPLE.COM
ravi@example.com
```

---

# Best Practices

```text
Use separate request DTOs
Use Bean Validation annotations
Use @Valid in controller
Validate nested objects with @Valid
Validate query params and headers
Use custom validators when needed
Keep business validation in service layer
Return 400 for request validation failure
Return clear field-level errors
Do not expose entity directly
Do not rely only on frontend validation
Normalize input before saving
Never log sensitive payload fields
Use global exception handling
```

---

# Interview-Ready Paragraph Answer

I validate request payloads by using separate request DTOs instead of directly accepting entity objects. In Spring Boot, I add Bean Validation annotations like `@NotBlank`, `@NotNull`, `@Email`, `@Size`, `@Pattern`, `@Min`, and `@Positive` on DTO fields. Then I use `@Valid` with `@RequestBody` in the controller, so Spring validates the payload before it reaches the service layer. For nested objects or lists, I also use `@Valid` inside the DTO. If validation fails, I handle `MethodArgumentNotValidException` in a global exception handler using `@RestControllerAdvice` and return `400 Bad Request` with field-level error messages. For business validations like duplicate email, inactive account, insufficient balance, or order cancellation rules, I handle them in the service layer and return proper status codes like `409 Conflict` or `422 Unprocessable Entity`. In simple words, I validate basic request structure at DTO level and business rules at service level, while keeping the error response clean and consistent.

---

# 16. Design an API for dashboard data where frontend needs filters, sorting, and pagination.

---
## Summary

For dashboard data, I would design a flexible list/search API that supports:

```text
Filters
Sorting
Pagination
Search
Date range
Consistent response format
```

For simple filters, I prefer `GET` with query parameters.
For very complex filters, I prefer `POST /search` with a request body.

## One-Line Answer

**I would design the dashboard API with filter query params, allowed sort fields, page/size pagination, proper validation, database-level filtering, and a paginated response with metadata.**

---

# Basic API Design

For a normal dashboard table, I would create an API like this:

```http
GET /api/v1/dashboard/orders
```

With query parameters:

```http
GET /api/v1/dashboard/orders?status=PAID&fromDate=2026-05-01&toDate=2026-05-03&page=0&size=20&sortBy=createdAt&sortDir=desc
```

This means:

```text
Give me paid orders
between May 1 and May 3
return first 20 records
sort by createdAt descending
```

---

# Example Request

```http
GET /api/v1/dashboard/orders
    ?status=PAID
    &customerType=PREMIUM
    &fromDate=2026-05-01
    &toDate=2026-05-03
    &minAmount=500
    &maxAmount=10000
    &page=0
    &size=20
    &sortBy=createdAt
    &sortDir=desc
```

---

# Example Response

```json
{
  "content": [
    {
      "orderId": "ORD123",
      "customerName": "Ravi Kumar",
      "status": "PAID",
      "amount": 2500,
      "currency": "INR",
      "createdAt": "2026-05-03T10:30:00"
    }
  ],
  "page": 0,
  "size": 20,
  "totalElements": 250,
  "totalPages": 13,
  "first": true,
  "last": false,
  "sortBy": "createdAt",
  "sortDir": "desc",
  "filtersApplied": {
    "status": "PAID",
    "customerType": "PREMIUM",
    "fromDate": "2026-05-01",
    "toDate": "2026-05-03",
    "minAmount": 500,
    "maxAmount": 10000
  }
}
```

This response is frontend-friendly.

The frontend can show:

```text
Table data
Current page
Total records
Total pages
Applied filters
Next/previous buttons
```

---

# Why GET Is Good Here

For dashboard list data, `GET` is usually good because we are reading data.

Example:

```http
GET /api/v1/dashboard/orders?status=PAID&page=0&size=20
```

Benefits:

```text
Easy to test
Easy to bookmark
Easy to cache
Clear REST style
Good for simple filters
```

But if the filters become very large or complex, then `POST /search` is better.

---

# When To Use POST For Dashboard Search

If filters are complex, query params become messy.

Example:

```text
Multiple statuses
Multiple customer types
Nested filters
Advanced search rules
Large selected IDs
Complex date logic
```

Then I would use:

```http
POST /api/v1/dashboard/orders/search
```

Request body:

```json
{
  "filters": {
    "statuses": ["PAID", "PENDING"],
    "customerTypes": ["PREMIUM", "REGULAR"],
    "fromDate": "2026-05-01",
    "toDate": "2026-05-03",
    "minAmount": 500,
    "maxAmount": 10000,
    "searchText": "ravi"
  },
  "page": 0,
  "size": 20,
  "sortBy": "createdAt",
  "sortDir": "desc"
}
```

This is cleaner for advanced dashboards.

---

# Important Design Points

## 1. Apply Filters At Database Level

Do not fetch all records and filter in Java.

Bad approach:

```text
Fetch all orders from DB
Filter in Java
Sort in Java
Paginate in Java
```

This is very bad for performance.

Good approach:

```text
Apply filters in SQL query
Apply sorting in SQL query
Apply pagination in SQL query
Return only required records
```

Example SQL:

```sql
SELECT *
FROM orders
WHERE status = 'PAID'
  AND created_at BETWEEN '2026-05-01' AND '2026-05-03'
ORDER BY created_at DESC
LIMIT 20 OFFSET 0;
```

---

## 2. Validate Pagination

Never allow unlimited data.

Good rules:

```text
Default page = 0
Default size = 20
Maximum size = 100
Page should not be negative
Size should be between 1 and 100
```

Invalid request:

```http
GET /api/v1/dashboard/orders?page=-1&size=5000
```

Return:

```http
400 Bad Request
```

Response:

```json
{
  "status": 400,
  "errorCode": "INVALID_PAGINATION",
  "message": "Page must be 0 or greater and size must be between 1 and 100"
}
```

---

## 3. Allow Only Safe Sort Fields

Do not allow the frontend to sort by any database column.

Bad:

```http
GET /orders?sortBy=passwordHash
```

Better:

```text
Allow only selected fields:
createdAt
amount
status
customerName
orderId
```

If frontend sends invalid sort field:

```http
400 Bad Request
```

Example:

```json
{
  "status": 400,
  "errorCode": "INVALID_SORT_FIELD",
  "message": "Sorting by this field is not allowed"
}
```

---

## 4. Use Stable Sorting

Pagination should always have stable sorting.

Bad:

```sql
ORDER BY created_at DESC
```

Better:

```sql
ORDER BY created_at DESC, id DESC
```

Why?

Because many records can have the same `created_at`.

Adding `id` makes the order stable.

This avoids duplicate or missing records between pages.

---

## 5. Use Indexes

For dashboard APIs, performance depends a lot on indexes.

If filters are:

```text
status
createdAt
customerType
```

Then useful indexes can be:

```sql
CREATE INDEX idx_orders_status_created_at
ON orders (status, created_at);

CREATE INDEX idx_orders_customer_type_created_at
ON orders (customer_type, created_at);
```

If the dashboard supports search by customer name, then we may need:

```text
Full-text search
Elasticsearch
OpenSearch
Database text index
```

Depends on scale.

---

# Offset Or Cursor Pagination?

For most dashboards, offset pagination is fine.

Example:

```http
GET /orders?page=0&size=20
```

Because dashboard users often need:

```text
Page numbers
Total count
Jump to page
Admin table style UI
```

But for very large real-time data like logs, audit trails, or transaction feeds, cursor pagination is better.

Example:

```http
GET /transactions?limit=20&cursor=TXN123
```

So my choice is:

```text
Admin dashboard table -> offset pagination
Huge live feed/logs -> cursor pagination
```

---

# DTO Design For GET API

```java
public class DashboardOrderFilter {

    private String status;
    private String customerType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private String searchText;

    // getters and setters
}
```

Controller:

```java
@GetMapping("/dashboard/orders")
public ResponseEntity<PaginatedResponse<OrderDashboardResponse>> getOrders(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String customerType,
        @RequestParam(required = false) LocalDate fromDate,
        @RequestParam(required = false) LocalDate toDate,
        @RequestParam(required = false) BigDecimal minAmount,
        @RequestParam(required = false) BigDecimal maxAmount,
        @RequestParam(required = false) String searchText,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "createdAt") String sortBy,
        @RequestParam(defaultValue = "desc") String sortDir
) {
    DashboardOrderFilter filter = new DashboardOrderFilter();
    filter.setStatus(status);
    filter.setCustomerType(customerType);
    filter.setFromDate(fromDate);
    filter.setToDate(toDate);
    filter.setMinAmount(minAmount);
    filter.setMaxAmount(maxAmount);
    filter.setSearchText(searchText);

    PaginatedResponse<OrderDashboardResponse> response =
            dashboardService.getOrders(filter, page, size, sortBy, sortDir);

    return ResponseEntity.ok(response);
}
```

---

# Service Layer Logic

```java
public PaginatedResponse<OrderDashboardResponse> getOrders(
        DashboardOrderFilter filter,
        int page,
        int size,
        String sortBy,
        String sortDir
) {
    validatePagination(page, size);
    validateSort(sortBy, sortDir);
    validateFilters(filter);

    Sort sort = buildSort(sortBy, sortDir);

    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Order> orderPage = orderRepository.findDashboardOrders(filter, pageable);

    List<OrderDashboardResponse> content = orderPage.getContent()
            .stream()
            .map(orderMapper::toDashboardResponse)
            .toList();

    return new PaginatedResponse<>(
            content,
            orderPage.getNumber(),
            orderPage.getSize(),
            orderPage.getTotalElements(),
            orderPage.getTotalPages(),
            orderPage.isFirst(),
            orderPage.isLast()
    );
}
```

---

# Validation Logic

```java
private void validatePagination(int page, int size) {
    if (page < 0) {
        throw new BadRequestException("Page cannot be negative");
    }

    if (size <= 0 || size > 100) {
        throw new BadRequestException("Size must be between 1 and 100");
    }
}

private void validateSort(String sortBy, String sortDir) {
    Set<String> allowedSortFields = Set.of(
            "createdAt",
            "amount",
            "status",
            "customerName",
            "orderId"
    );

    if (!allowedSortFields.contains(sortBy)) {
        throw new BadRequestException("Invalid sort field");
    }

    if (!sortDir.equalsIgnoreCase("asc") && !sortDir.equalsIgnoreCase("desc")) {
        throw new BadRequestException("Invalid sort direction");
    }
}
```

---

# Repository Query Approach

For dynamic filters, I would use one of these:

```text
JPA Specification
Criteria API
QueryDSL
Native SQL for complex dashboard queries
Elasticsearch/OpenSearch for heavy search
```

For simple filters, JPA Specification works well.

Example idea:

```text
If status is present, add status condition
If date range is present, add createdAt between condition
If minAmount is present, add amount >= minAmount
If maxAmount is present, add amount <= maxAmount
```

---

# Security Points

Dashboard APIs usually show sensitive data.

So I would add:

```text
Authentication
Authorization
Role/permission check
Resource-level access check
Audit logging for sensitive dashboards
Data masking if needed
```

Example:

```java
@PreAuthorize("hasAuthority('dashboard.order.view')")
@GetMapping("/dashboard/orders")
public ResponseEntity<PaginatedResponse<OrderDashboardResponse>> getOrders(...) {
    ...
}
```

Also, do not expose internal fields like:

```text
passwordHash
internalDbId if not needed
secret keys
full card number
token values
```

---

# Performance Points

Dashboard APIs can become heavy.

So I would consider:

```text
Database indexes
Pagination mandatory
Maximum page size
Avoid N+1 queries
Use projections instead of loading full entity
Cache summary cards if needed
Separate summary API and table API
Use async export for large downloads
```

For example, if dashboard has cards like:

```text
Total orders
Total revenue
Failed payments
Pending refunds
```

I may create a separate summary API:

```http
GET /api/v1/dashboard/orders/summary?fromDate=2026-05-01&toDate=2026-05-03
```

And table API separately:

```http
GET /api/v1/dashboard/orders?page=0&size=20
```

This keeps APIs clean and faster.

---

# Caching

For dashboard data that does not need real-time accuracy, caching can help.

Example:

```text
Summary cards can be cached for 30 seconds or 1 minute
Dropdown filter values can be cached
Static reference data can be cached
```

But for sensitive or real-time financial data, cache carefully.

---

# Export Handling

Dashboards often need export.

Do not return 1 lakh records from the list API.

Better:

```http
POST /api/v1/dashboard/orders/export
```

Response:

```http
202 Accepted
```

```json
{
  "requestId": "EXP123",
  "status": "PROCESSING"
}
```

Then process export async and notify user when ready.

---

# Common Mistakes

## 1. Returning All Records

This can crash the API or frontend.

Always paginate.

## 2. Filtering In Java

Filtering should happen in DB query.

## 3. Allowing Any Sort Field

This can expose internal fields or create slow queries.

Use allowlisted sort fields.

## 4. No Maximum Page Size

Users can request huge data.

Set max size.

## 5. No Indexes

Dashboard APIs become slow without proper indexes.

## 6. Mixing Summary And Table Too Much

Summary cards and table data may need different queries.

Separate them when needed.

---

# Best Practices

```text
Use GET for simple dashboard filters
Use POST /search for complex filters
Apply filters, sorting, and pagination in database
Use default and max page size
Validate all filter values
Allow only safe sort fields
Use stable sorting
Return pagination metadata
Add proper indexes
Use projections for dashboard rows
Protect API with permissions
Use caching for summary data when safe
Use async export for large reports
```

---

# Interview-Ready Paragraph Answer

To design a dashboard API with filters, sorting, and pagination, I would usually expose a `GET` API like `/api/v1/dashboard/orders` with query parameters such as `status`, `fromDate`, `toDate`, `page`, `size`, `sortBy`, and `sortDir`. For complex filters, I would use a `POST /search` API with the filters in the request body. I would always apply filtering, sorting, and pagination at the database level, not after fetching all records in Java. I would set a default page size, a maximum page size, validate page and size, and allow sorting only on safe fields. I would also use stable sorting like `createdAt DESC, id DESC` to avoid duplicate or missing records between pages. The response should include the data list and pagination metadata like current page, size, total elements, total pages, first, and last. For performance, I would add indexes on filter and sort columns, avoid N+1 queries, use projections where possible, and cache summary data if real-time accuracy is not required. In simple words, the API should be flexible for frontend, safe for backend, and fast for database.
