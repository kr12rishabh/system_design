
# 1. REST vs async messaging

---
## Summary

REST and async messaging are two common ways services talk to each other.

**REST is best when you need an immediate response.**
**Async messaging is best when work can happen later and services should not wait for each other.**

## One-Line Answer

REST is synchronous communication, while async messaging is event-based communication where one service sends a message and continues without waiting for the receiver to finish.

---

## REST Means Direct Request And Response

In REST, one service calls another service directly over HTTP.

Example:

```text
Order Service  --->  Payment Service
              waits for response
```

The caller sends a request and waits for the response.

Example:

```http
POST /payments
```

The Payment Service may return:

```json
{
  "status": "SUCCESS",
  "transactionId": "TXN123"
}
```

So REST is very useful when the caller needs the result immediately.

For example:

```text
User clicks "Pay Now"
Order Service calls Payment Service
Order Service needs success or failure immediately
```

---

## Async Messaging Means Send And Continue

In async messaging, one service sends a message to a queue or topic.

The sender does not wait for the receiver to process it immediately.

Example:

```text
Order Service  --->  Kafka / RabbitMQ  --->  Email Service
```

Order Service publishes an event like:

```json
{
  "event": "ORDER_PLACED",
  "orderId": "ORD123",
  "userId": "U101"
}
```

Then it continues its own work.

Later, Email Service, Inventory Service, or Notification Service can consume that event.

This is useful when the work does not need to finish immediately.

---

# Main Difference

## REST

REST is like calling someone on the phone.

You ask something.
You wait for the answer.

```text
Service A calls Service B
Service A waits
Service B replies
```

## Async Messaging

Async messaging is like sending a WhatsApp message.

You send the message.
The other person can reply or act later.

```text
Service A publishes message
Service A continues
Service B processes later
```

---

# REST Example In Backend

Suppose we have an e-commerce app.

When user opens order details:

```text
Frontend calls Order Service
Order Service returns order data
```

Here REST is good because the user is waiting on screen.

Example:

```http
GET /orders/123
```

Response:

```json
{
  "orderId": "123",
  "status": "DELIVERED"
}
```

This should be fast and direct.

---

# Async Messaging Example In Backend

Suppose user places an order.

After order is placed, we may need to do many things:

```text
Send email
Update inventory
Send SMS
Generate invoice
Notify seller
Update analytics
```

If Order Service calls all these services using REST, it becomes slow.

```text
Order Service
   -> Email Service
   -> Inventory Service
   -> Invoice Service
   -> Notification Service
```

If any service is slow, order placement becomes slow.

A better approach:

```text
Order Service publishes ORDER_PLACED event
Other services consume it
```

Now Order Service can return response quickly.

---

# Key Differences

| Point            | REST                         | Async Messaging                |
| ---------------- | ---------------------------- | ------------------------------ |
| Communication    | Direct HTTP call             | Message through queue/topic    |
| Waiting          | Caller waits                 | Caller does not wait           |
| Style            | Synchronous                  | Asynchronous                   |
| Coupling         | More tightly coupled         | Loosely coupled                |
| Speed for user   | Good for immediate response  | Good for background work       |
| Failure handling | Caller gets failure directly | Retry can happen later         |
| Example          | Get order details            | Send email after order placed  |
| Tools            | HTTP, Spring MVC, REST APIs  | Kafka, RabbitMQ, SQS, ActiveMQ |

---

# When To Use REST

Use REST when you need an immediate result.

Good examples:

```text
Login API
Get user profile
Get account balance
Search products
Fetch order details
Validate payment status
```

REST is simple and easy to debug.

It is also easy to test with Postman, curl, or browser.

---

# When To Use Async Messaging

Use async messaging when the task can happen in the background.

Good examples:

```text
Send email
Send SMS
Process invoice
Update analytics
Sync data to another system
Retry failed operations
Process large files
Notify multiple services
```

It is very useful in microservices.

One service can publish an event, and many services can react to it.

---

# Important Point: REST Gives Immediate Consistency

In REST, the caller usually knows the result immediately.

Example:

```text
Payment success or failed
```

So REST is good when the next step depends on the result.

Example:

```text
Do not confirm order until payment is successful
```

---

# Important Point: Async Messaging Gives Eventual Consistency

In async messaging, things may not update immediately.

Example:

```text
Order is placed
Email may be sent after 2 seconds
Inventory may update after some time
Invoice may generate later
```

This is called eventual consistency.

It means the system becomes consistent after some time.

This is normal in distributed systems.

---

# REST Problem In Microservices

REST is simple, but it can create problems when many services depend on each other.

Example:

```text
Order Service calls Payment Service
Payment Service calls Fraud Service
Fraud Service calls User Service
```

If one service is slow, the full request becomes slow.

If one service is down, the request may fail.

This can create a chain failure.

---

# Async Messaging Solves This Better

With messaging, services are not directly dependent on each other.

Example:

```text
Order Service publishes ORDER_CREATED
Payment Service consumes it
Inventory Service consumes it
Notification Service consumes it
```

Even if Notification Service is down, the message can stay in the queue.

When Notification Service comes back, it can process the message.

This improves reliability.

---

# But Async Messaging Is Not Always Easy

Async messaging has its own challenges.

You need to handle:

```text
Duplicate messages
Retry logic
Dead letter queue
Message ordering
Event schema changes
Monitoring
Idempotency
```

Idempotency is very important.

It means if the same message is processed twice, the result should not be wrong.

Example:

```text
Do not send the same refund twice
Do not create duplicate invoices
Do not reduce inventory twice
```

---

# Simple Java/Spring Example

## REST Call Example

```java
@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable String orderId) {
        return new OrderResponse(orderId, "DELIVERED");
    }
}
```

Here the client calls the API and gets response immediately.

---

## Async Event Example

```java
public class OrderPlacedEvent {
    private String orderId;
    private String userId;
    private double amount;

    public OrderPlacedEvent(String orderId, String userId, double amount) {
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }
}
```

Publisher:

```java
@Service
public class OrderService {

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public OrderService(KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void placeOrder(String orderId, String userId, double amount) {
        // Save order in database first

        OrderPlacedEvent event = new OrderPlacedEvent(orderId, userId, amount);

        kafkaTemplate.send("order-placed-topic", orderId, event);
    }
}
```

Consumer:

```java
@Component
public class EmailConsumer {

    @KafkaListener(topics = "order-placed-topic", groupId = "email-service")
    public void sendEmail(OrderPlacedEvent event) {
        System.out.println("Sending email for order: " + event.getOrderId());
    }
}
```

Here Order Service publishes the event.
Email Service processes it separately.

---

# Real-World Best Approach

In real backend systems, we usually use both.

Example:

```text
Frontend -> Order Service using REST
Order Service -> Payment Service using REST
Order Service -> Kafka event for email, invoice, analytics
```

So REST and async messaging are not enemies.

They solve different problems.

---

# Common Interview Trap

Do not say:

```text
Async messaging is always better than REST
```

That is wrong.

A better answer is:

```text
REST is better for immediate response.
Async messaging is better for background processing, decoupling, and reliability.
```

---

# Final Interview-Ready Answer

REST is a synchronous communication style where one service sends an HTTP request and waits for the response. It is best when we need an immediate result, like login, fetching user details, checking payment status, or getting order data. Async messaging is different. In async messaging, one service publishes a message or event to a queue or topic, and other services process it later. The sender does not wait for the receiver. It is useful for background work like sending emails, updating inventory, generating invoices, sending notifications, or processing analytics. REST is simple and good for request-response flows, but it can create tight coupling between services. Async messaging gives loose coupling, better retry support, and better scalability, but it also needs proper handling of duplicate messages, retries, ordering, and dead letter queues. In real microservices, we usually use both. REST is used for immediate user-facing operations, and async messaging is used for event-driven background processing.

---

# 2. Idempotency in APIs

---
## Summary

**Idempotency means one API request can be safely repeated many times, but the final result stays the same.**

It is very important in backend systems.
Mainly when retries happen because of timeout, network issue, or user double-click.

---

## One-Line Answer

**Idempotency means calling the same API multiple times should not create duplicate or wrong results.**

---

# Simple Meaning

Suppose user clicks **Pay Now**.

The payment request goes to the server.

But the network is slow.

The user does not get response.

Now the frontend or mobile app retries the same request.

Without idempotency, this can happen:

```text
Payment charged twice
Order created twice
Money deducted twice
Invoice generated twice
```

That is a serious bug.

With idempotency, even if the same request comes again, the backend understands:

```text
This request was already processed.
Do not process it again.
Return the previous result.
```

---

# Simple Real-Life Example

Think of an elevator button.

You press the same floor button once.
Or you press it five times.

The elevator should still go to that floor only once.

That is idempotent behavior.

Same idea in APIs.

---

# Example Without Idempotency

Imagine this API:

```http
POST /orders
```

Request body:

```json
{
  "productId": "P101",
  "quantity": 1,
  "amount": 999
}
```

If user sends this request twice, the backend may create two orders.

```text
Order 1 created
Order 2 created
```

This is not safe.

---

# Example With Idempotency

Now the client sends an idempotency key.

```http
POST /orders
Idempotency-Key: abc-123
```

Request body:

```json
{
  "productId": "P101",
  "quantity": 1,
  "amount": 999
}
```

Backend checks:

```text
Have I already processed request with key abc-123?
```

If no, process it and save the result.

If yes, return the old result.

So even if the same request comes 5 times, only one order is created.

---

# Why Idempotency Is Needed

Idempotency is needed because failures are normal in real systems.

Common cases:

```text
Network timeout
Mobile app retry
User double-click
Load balancer retry
Payment gateway retry
Message consumer retry
Server response lost
```

The important point is this:

```text
The client may not know whether the server processed the request or not.
```

So the client retries.

Backend must handle that retry safely.

---

# HTTP Methods And Idempotency

Some HTTP methods are naturally idempotent.

## GET

```http
GET /users/101
```

Calling it again and again should only read data.

So GET is idempotent.

---

## PUT

```http
PUT /users/101
```

Request:

```json
{
  "name": "Ravi"
}
```

If we call this many times, the name is still Ravi.

So PUT is idempotent.

---

## DELETE

```http
DELETE /users/101
```

First call deletes the user.

Second call should not delete another user.

The final result is same.

So DELETE is also usually treated as idempotent.

---

## POST

```http
POST /orders
```

POST usually creates a new resource.

So POST is **not idempotent by default**.

That is why we use an idempotency key for important POST APIs.

Examples:

```text
Create order
Make payment
Create refund
Book ticket
Transfer money
Generate invoice
```

---

# Idempotency Key

An idempotency key is a unique value sent by the client.

Example:

```http
Idempotency-Key: 8f1b6a2d-91c2-4c8a-9d77-12345
```

Usually the client generates this key.

For example:

```text
One key per payment attempt
One key per order creation attempt
One key per fund transfer attempt
```

The server stores this key with the request status and response.

---

# How Backend Handles Idempotency

Basic flow:

```text
1. Client sends request with Idempotency-Key
2. Server checks if key already exists
3. If key does not exist, lock/save the key
4. Process the request
5. Save final response against the key
6. Return response
7. If same key comes again, return saved response
```

---

# Simple Database Table

We can create a table like this:

```sql
CREATE TABLE idempotency_keys (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    idempotency_key VARCHAR(100) NOT NULL UNIQUE,
    request_hash VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    response_body TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

Important fields:

| Field           | Meaning                     |
| --------------- | --------------------------- |
| idempotency_key | Unique key from client      |
| request_hash    | Hash of request body        |
| status          | PROCESSING, SUCCESS, FAILED |
| response_body   | Saved API response          |
| created_at      | When key was created        |

---

# Why Request Hash Is Important

Suppose client sends this first request:

```json
{
  "amount": 1000,
  "accountId": "A101"
}
```

With key:

```text
abc-123
```

Later client sends another request with same key but different amount:

```json
{
  "amount": 5000,
  "accountId": "A101"
}
```

This should not be allowed.

Same idempotency key should not be used for different request data.

So backend stores a hash of the request body.

If same key comes with different body, return error.

Example:

```text
409 Conflict
Idempotency key already used with different request
```

---

# Important Status Handling

Idempotency is not only about success.

We should handle different states.

## PROCESSING

Request is already running.

Another same request came at the same time.

Backend can return:

```text
409 Conflict
Request is already being processed
```

Or it can wait and return the final result.

---

## SUCCESS

Request already completed.

Return the saved success response.

---

## FAILED

This depends on business logic.

For some failures, we can return the same failed response.

For temporary failures, we may allow retry.

Example:

```text
Payment gateway timeout can be retried
Invalid account number should not be retried
```

---

# Simple Spring Boot Example

## Controller

```java
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody PaymentRequest request) {

        PaymentResponse response = paymentService.makePayment(idempotencyKey, request);
        return ResponseEntity.ok(response);
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
    public PaymentResponse makePayment(String idempotencyKey, PaymentRequest request) {

        String requestHash = createRequestHash(request);

        Optional<IdempotencyRecord> existingRecord =
                idempotencyRepository.findByIdempotencyKey(idempotencyKey);

        if (existingRecord.isPresent()) {
            IdempotencyRecord record = existingRecord.get();

            if (!record.getRequestHash().equals(requestHash)) {
                throw new IllegalStateException(
                        "Same idempotency key used with different request"
                );
            }

            if ("SUCCESS".equals(record.getStatus())) {
                return convertJsonToResponse(record.getResponseBody());
            }

            if ("PROCESSING".equals(record.getStatus())) {
                throw new IllegalStateException(
                        "Request is already being processed"
                );
            }
        }

        IdempotencyRecord record = new IdempotencyRecord();
        record.setIdempotencyKey(idempotencyKey);
        record.setRequestHash(requestHash);
        record.setStatus("PROCESSING");
        idempotencyRepository.save(record);

        Payment payment = new Payment();
        payment.setAmount(request.getAmount());
        payment.setAccountId(request.getAccountId());
        payment.setStatus("SUCCESS");
        paymentRepository.save(payment);

        PaymentResponse response = new PaymentResponse(
                payment.getId(),
                "SUCCESS",
                "Payment completed successfully"
        );

        record.setStatus("SUCCESS");
        record.setResponseBody(convertResponseToJson(response));
        idempotencyRepository.save(record);

        return response;
    }

    private String createRequestHash(PaymentRequest request) {
        return String.valueOf(Objects.hash(request.getAccountId(), request.getAmount()));
    }

    private PaymentResponse convertJsonToResponse(String responseBody) {
        // Convert JSON string to PaymentResponse using ObjectMapper
        return new PaymentResponse(1L, "SUCCESS", "Payment completed successfully");
    }

    private String convertResponseToJson(PaymentResponse response) {
        // Convert PaymentResponse to JSON string using ObjectMapper
        return response.toString();
    }
}
```

This is a simple example.

In real projects, we should use proper JSON hashing, ObjectMapper, exception handling, and unique constraints.

---

# Very Important Point: Use Database Unique Constraint

Do not only check in Java code.

This is risky:

```text
Check if key exists
Then save key
```

Because two requests may come at the same time.

Both may see that key does not exist.

Both may process the request.

So we need a unique constraint at database level.

Example:

```sql
idempotency_key VARCHAR(100) NOT NULL UNIQUE
```

This protects us from race conditions.

---

# Idempotency In Async Messaging

Idempotency is also very important in Kafka, RabbitMQ, or SQS.

A message can be delivered more than once.

So the consumer should be idempotent.

Example:

```text
OrderPlaced event consumed twice
Inventory should not reduce twice
Email should not send twice if business does not allow it
Invoice should not generate twice
```

A common solution is to store processed message IDs.

Example:

```sql
CREATE TABLE processed_messages (
    message_id VARCHAR(100) PRIMARY KEY,
    processed_at TIMESTAMP
);
```

Before processing message:

```text
Check if message_id already processed
If yes, skip
If no, process and save message_id
```

---

# Idempotency vs Retry

Retry means trying again after failure.

Idempotency makes retry safe.

Without idempotency, retry can be dangerous.

Example:

```text
Retry payment without idempotency = possible double payment
Retry payment with idempotency = safe
```

So both are connected.

---

# Idempotency vs Atomicity

Idempotency means repeated calls do not cause duplicate effects.

Atomicity means a group of operations either fully completes or fully fails.

Example:

```text
Debit account
Credit account
Save transaction
```

These operations should be atomic.

But the API should also be idempotent, so the same transfer request does not happen twice.

---

# Common Mistakes

## Mistake 1: Using User ID As Idempotency Key

Bad idea:

```text
Idempotency-Key: user123
```

Because one user can make many payments.

The key should be unique per operation.

Better:

```text
Idempotency-Key: payment-attempt-unique-id
```

---

## Mistake 2: Not Checking Request Body

Same key with different request body should be rejected.

Otherwise, the system may return wrong response.

---

## Mistake 3: Storing Key After Processing

Wrong flow:

```text
Process payment
Then save idempotency key
```

If server crashes after payment but before saving key, retry can charge again.

Better flow:

```text
Save key as PROCESSING
Process payment
Save final response
```

---

## Mistake 4: No Expiry Policy

Idempotency keys should not stay forever in most systems.

Usually we keep them for some time.

Example:

```text
24 hours
48 hours
7 days
```

It depends on business need.

---

# Best Practices

Use these points in real projects:

```text
Use Idempotency-Key header
Generate unique key per operation
Store key in database or Redis
Use database unique constraint
Store request hash
Store final response
Handle PROCESSING state
Add expiry/TTL
Make message consumers idempotent
Use transaction where needed
```

---

# Where We Usually Use Idempotency

Idempotency is very useful for:

```text
Payment API
Refund API
Order creation API
Ticket booking API
Fund transfer API
Invoice generation API
Wallet recharge API
Kafka consumers
Webhook handling
```

Webhook handling is a very common case.

Payment gateways may send the same webhook multiple times.

Backend should process it only once.

---

# Small Practical Example

Suppose Razorpay, Stripe, or any payment gateway sends payment success webhook twice.

Without idempotency:

```text
Wallet credited twice
Order marked paid twice
Invoice generated twice
```

With idempotency:

```text
Check paymentId
If already processed, ignore
If not processed, update order and save paymentId
```

---

# Interview-Ready Paragraph Answer

Idempotency in APIs means the same request can be safely sent multiple times, but it should not create duplicate or wrong results. It is very important for APIs like payment, refund, order creation, wallet recharge, and fund transfer because retries can happen due to timeout, network failure, or user double-click. For example, if a payment API is retried, the user should not be charged twice. A common way to handle this is by using an idempotency key. The client sends a unique key for one operation, and the server stores that key with the request hash, status, and response. If the same key comes again, the server returns the previous response instead of processing the request again. We should also use a database unique constraint to avoid race conditions. In async systems like Kafka also, consumers should be idempotent because the same message can be delivered more than once. So in simple words, idempotency makes retries safe and protects the system from duplicate side effects.

---

# 3. What status code would you return for X?

---
## Summary

For any API case **X**, choose the status code based on this question:

**Did the request succeed, fail because of client input, fail because of auth, or fail because of server/downstream issue?**

## One-Line Answer

**Return 2xx for success, 4xx when the client/request is wrong, and 5xx when the server or downstream system failed.**

---

# Simple Rule

HTTP status codes are not random.

They tell the client what happened.

```text
2xx  -> Success
3xx  -> Redirection
4xx  -> Client-side problem
5xx  -> Server-side problem
```

For backend APIs, most interview questions focus on:

```text
200
201
202
204
400
401
403
404
409
422
429
500
502
503
504
```

---

# Most Common Status Codes

## 200 OK

Use when the request is successful and you are returning data.

Example:

```http
GET /users/101
```

Response:

```json
{
  "id": 101,
  "name": "Ravi"
}
```

Use **200 OK**.

Also common for successful update when response body is returned.

```http
PUT /users/101
```

---

## 201 Created

Use when a new resource is created.

Example:

```http
POST /users
```

If a new user is created, return:

```http
201 Created
```

Good practice is to also return the created resource or its ID.

```json
{
  "id": 101,
  "message": "User created successfully"
}
```

---

## 202 Accepted

Use when the request is accepted, but processing will happen later.

Example:

```http
POST /reports
```

If report generation is async, return:

```http
202 Accepted
```

Response:

```json
{
  "requestId": "REQ123",
  "status": "PROCESSING"
}
```

This is common for async jobs, queues, batch processing, and long-running tasks.

---

## 204 No Content

Use when the request is successful, but there is no response body.

Example:

```http
DELETE /users/101
```

If delete is successful, return:

```http
204 No Content
```

No body should be sent.

---

# Client Error Status Codes

## 400 Bad Request

Use when request data is invalid or malformed.

Example:

```json
{
  "email": "wrong-email"
}
```

Or missing required field:

```json
{
  "name": ""
}
```

Return:

```http
400 Bad Request
```

Use this when the client sent something wrong.

---

## 401 Unauthorized

Use when authentication is missing or invalid.

Example:

```text
No token
Invalid token
Expired token
```

Return:

```http
401 Unauthorized
```

Simple meaning:

```text
Who are you? Please login first.
```

---

## 403 Forbidden

Use when user is authenticated, but not allowed to perform the action.

Example:

```text
Normal user tries to access admin API
```

Return:

```http
403 Forbidden
```

Simple meaning:

```text
I know who you are, but you do not have permission.
```

---

## 404 Not Found

Use when the resource does not exist.

Example:

```http
GET /users/999
```

If user 999 does not exist, return:

```http
404 Not Found
```

Simple meaning:

```text
The requested resource was not found.
```

---

## 409 Conflict

Use when request conflicts with current system state.

Example:

```text
User tries to register with an email that already exists
Two users try to book the same seat
Same idempotency key used with different request body
Version conflict in optimistic locking
```

Return:

```http
409 Conflict
```

This is very important in real backend systems.

---

## 422 Unprocessable Entity

Use when request format is correct, but business validation fails.

Example:

```json
{
  "amount": 1000000
}
```

But the user has only 5000 balance.

Return:

```http
422 Unprocessable Entity
```

Another example:

```text
Account exists, request JSON is valid, but transfer is not allowed due to business rule.
```

Many teams use **400** instead of **422**.
Both can be acceptable depending on company standard.

But the difference is:

```text
400 -> request itself is bad
422 -> request is syntactically valid, but business rule failed
```

---

## 429 Too Many Requests

Use when rate limit is crossed.

Example:

```text
User calls OTP API 20 times in one minute
```

Return:

```http
429 Too Many Requests
```

Good response:

```json
{
  "message": "Too many requests. Please try again later."
}
```

---

# Server Error Status Codes

## 500 Internal Server Error

Use when something unexpected failed inside our server.

Example:

```text
NullPointerException
Unexpected database error
Code bug
Unknown failure
```

Return:

```http
500 Internal Server Error
```

Do not expose internal error details to the client.

Bad response:

```json
{
  "error": "NullPointerException at PaymentService.java line 45"
}
```

Better response:

```json
{
  "message": "Something went wrong. Please try again later."
}
```

---

## 502 Bad Gateway

Use when our service calls another service, and that service gives an invalid response.

Example:

```text
Order Service calls Payment Gateway
Payment Gateway returns invalid response
```

Return:

```http
502 Bad Gateway
```

---

## 503 Service Unavailable

Use when service is temporarily unavailable.

Example:

```text
Service is under maintenance
Database connection pool is exhausted
Downstream service is unavailable
```

Return:

```http
503 Service Unavailable
```

---

## 504 Gateway Timeout

Use when downstream service did not respond in time.

Example:

```text
Order Service calls Payment Service
Payment Service does not respond before timeout
```

Return:

```http
504 Gateway Timeout
```

---

# Common Interview Scenarios

## User Created Successfully

```http
POST /users
```

Return:

```http
201 Created
```

Because a new resource was created.

---

## User Updated Successfully

```http
PUT /users/101
```

Return:

```http
200 OK
```

If response body is returned.

Return:

```http
204 No Content
```

If no response body is returned.

---

## User Deleted Successfully

```http
DELETE /users/101
```

Return:

```http
204 No Content
```

Because delete succeeded and no body is needed.

---

## User Not Found

```http
GET /users/999
```

Return:

```http
404 Not Found
```

---

## Invalid Request Body

Example:

```json
{
  "email": "abc"
}
```

Return:

```http
400 Bad Request
```

---

## Duplicate Email During Signup

Return:

```http
409 Conflict
```

Because the email already exists and conflicts with current data.

---

## Login With Wrong Password

Usually return:

```http
401 Unauthorized
```

Because authentication failed.

For security, do not say:

```text
Password is wrong
```

Better message:

```text
Invalid username or password
```

---

## Logged-In User Accesses Admin API

Return:

```http
403 Forbidden
```

Because user is logged in but not allowed.

---

## Missing Token

Return:

```http
401 Unauthorized
```

Because authentication is missing.

---

## Expired Token

Return:

```http
401 Unauthorized
```

Because user needs to authenticate again.

---

## Valid Token But No Permission

Return:

```http
403 Forbidden
```

---

## Payment Request Accepted For Processing

Return:

```http
202 Accepted
```

Because processing may happen asynchronously.

---

## Payment Successful Immediately

Return:

```http
200 OK
```

Or sometimes:

```http
201 Created
```

If a new payment resource is created.

In most normal payment APIs, **200 OK** is common for successful payment processing.

---

## Balance Is Low For Transfer

Return:

```http
422 Unprocessable Entity
```

Because request format is valid, but business rule failed.

Some teams may use **400 Bad Request** for this.
But **422** is more specific.

---

## Same Seat Already Booked

Return:

```http
409 Conflict
```

Because the resource state has changed.

---

## Same Idempotency Key Used Again

If same key and same request:

```http
200 OK
```

Return the old saved response.

If same key but different request body:

```http
409 Conflict
```

Because the key conflicts with a different request.

---

## Too Many OTP Requests

Return:

```http
429 Too Many Requests
```

Because rate limit is crossed.

---

## Downstream Payment Gateway Timeout

Return:

```http
504 Gateway Timeout
```

Because our service waited for another service and it timed out.

---

## Downstream Service Is Down

Return:

```http
503 Service Unavailable
```

If the dependency is unavailable.

Sometimes **502 Bad Gateway** is also used when the gateway/downstream response is bad.

---

# Simple Decision Table

| Situation                             | Status Code               |
| ------------------------------------- | ------------------------- |
| Data fetched successfully             | 200 OK                    |
| Resource created                      | 201 Created               |
| Request accepted for async processing | 202 Accepted              |
| Success but no response body          | 204 No Content            |
| Invalid request body                  | 400 Bad Request           |
| Missing or invalid token              | 401 Unauthorized          |
| No permission                         | 403 Forbidden             |
| Resource not found                    | 404 Not Found             |
| Duplicate/conflict                    | 409 Conflict              |
| Business rule failed                  | 422 Unprocessable Entity  |
| Rate limit crossed                    | 429 Too Many Requests     |
| Unexpected server error               | 500 Internal Server Error |
| Bad downstream response               | 502 Bad Gateway           |
| Service temporarily unavailable       | 503 Service Unavailable   |
| Downstream timeout                    | 504 Gateway Timeout       |

---

# 400 vs 422

This is a common interview confusion.

Use **400** when the request is wrong at input level.

Example:

```text
Invalid JSON
Missing required field
Invalid email format
Invalid date format
```

Use **422** when the request is valid, but business cannot process it.

Example:

```text
Insufficient balance
Transfer limit exceeded
Account is blocked
Coupon is valid format but expired
```

In many companies, teams use only **400** for all validation errors.
That is also fine if it is the team standard.

---

# 401 vs 403

This is another common interview point.

Use **401** when authentication failed.

```text
No token
Invalid token
Expired token
```

Use **403** when authorization failed.

```text
Token is valid
User is logged in
But user does not have permission
```

Simple way:

```text
401 -> Who are you?
403 -> I know you, but you cannot access this.
```

---

# 404 vs 204 For Delete

For delete API:

First delete request:

```http
DELETE /users/101
```

Can return:

```http
204 No Content
```

If deleted successfully.

Second delete request for same user:

Some teams return:

```http
404 Not Found
```

Because the user no longer exists.

Some teams still return:

```http
204 No Content
```

Because delete is idempotent and final state is already achieved.

Both are seen in real projects.

For interviews, you can say:

```text
If we strictly say resource is missing, return 404.
If we follow idempotent delete behavior, returning 204 is also acceptable.
The team should keep it consistent.
```

---

# Best Practice For Error Response Body

Do not return only status code.

Return a clean error body.

Example:

```json
{
  "status": 400,
  "errorCode": "INVALID_REQUEST",
  "message": "Email format is invalid",
  "path": "/users",
  "timestamp": "2026-05-03T10:30:00"
}
```

For production APIs, error response should be consistent.

It helps frontend, mobile app, and other services handle errors properly.

---

# Interview-Ready Paragraph Answer

For deciding an HTTP status code, I first check whether the request succeeded or failed. If it succeeded, I use 2xx codes like 200 for successful response, 201 when a new resource is created, 202 when request is accepted for async processing, and 204 when there is no response body. If the client sent something wrong, I use 4xx codes. For example, 400 for invalid request, 401 for missing or invalid authentication, 403 when the user is authenticated but not allowed, 404 when the resource is not found, 409 for duplicate or state conflict, 422 for business validation failure, and 429 for rate limiting. If the problem is from our server or dependency, I use 5xx codes like 500 for unexpected server error, 503 when service is unavailable, and 504 when downstream times out. The main idea is simple: 2xx means success, 4xx means client/request issue, and 5xx means server or dependency issue.

---

# 4. How do you version APIs?

---
## Summary

API versioning means changing an API without breaking existing clients.

**The main goal is backward compatibility.**
Old mobile apps, frontend apps, and partner systems should keep working while new clients can use the new API.

---

## One-Line Answer

**I version APIs by keeping breaking changes in a new version, usually using URL versioning like `/api/v1/users`, and I avoid changing existing API contracts directly.**

---

# Why API Versioning Is Needed

Once an API is used by clients, we cannot change it casually.

Clients can be:

```text
Mobile app
Web frontend
Partner system
Another microservice
Third-party integration
```

If we change request or response format suddenly, clients may break.

Example:

Old response:

```json
{
  "userId": 101,
  "name": "Ravi"
}
```

New response:

```json
{
  "id": 101,
  "fullName": "Ravi Kumar"
}
```

This can break clients that are still reading `userId` and `name`.

So instead of changing the same API, we create a new version.

---

# Common Ways To Version APIs

## 1. URL Versioning

This is the most common and easiest way.

```http
GET /api/v1/users/101
GET /api/v2/users/101
```

Here `v1` is the old API.
`v2` is the new API.

This is easy to understand.
It is also easy to test in Postman.

Example:

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserV1Controller {

    @GetMapping("/{id}")
    public UserV1Response getUser(@PathVariable Long id) {
        return new UserV1Response(id, "Ravi");
    }
}
```

```java
@RestController
@RequestMapping("/api/v2/users")
public class UserV2Controller {

    @GetMapping("/{id}")
    public UserV2Response getUser(@PathVariable Long id) {
        return new UserV2Response(id, "Ravi Kumar", "ACTIVE");
    }
}
```

This is simple and practical.

---

## 2. Header Versioning

In this approach, the URL stays same.
Version is sent in the header.

```http
GET /api/users/101
X-API-Version: 1
```

or

```http
GET /api/users/101
X-API-Version: 2
```

This keeps URLs clean.

But it is slightly harder to test and debug because version is hidden in headers.

It is used in some enterprise systems.

---

## 3. Query Parameter Versioning

Version is passed as a query parameter.

```http
GET /api/users/101?version=1
GET /api/users/101?version=2
```

This is easy to try in browser.

But it is not very clean for large APIs.

I usually avoid this for serious public APIs.

---

## 4. Media Type Versioning

This uses the `Accept` header.

```http
GET /api/users/101
Accept: application/vnd.company.user.v1+json
```

For v2:

```http
Accept: application/vnd.company.user.v2+json
```

This is very REST-friendly.

But it is harder for many teams to understand and maintain.

So I use it only if the organization already follows this standard.

---

# My Preferred Approach

For most backend systems, I prefer **URL versioning**.

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
Easy for frontend and mobile teams
Easy to route in gateway
```

In interviews, this is a very safe and practical answer.

---

# When Do We Need A New API Version?

We need a new version when there is a breaking change.

Breaking changes can be:

```text
Removing a field from response
Renaming a field
Changing data type
Changing request format
Changing response structure
Changing error response format
Changing business meaning of a field
Changing required fields
Changing endpoint behavior heavily
```

Example:

Old API:

```json
{
  "amount": "1000"
}
```

New API:

```json
{
  "amount": 1000
}
```

Here the data type changed from string to number.
This can break clients.

So this should go in a new version.

---

# What Changes Do Not Need A New Version?

Not every change needs a new API version.

Usually safe changes are:

```text
Adding a new optional response field
Adding a new optional request field
Adding a new endpoint
Improving internal logic without changing contract
Improving performance
Adding pagination support in backward-compatible way
```

Example:

Old response:

```json
{
  "userId": 101,
  "name": "Ravi"
}
```

New response:

```json
{
  "userId": 101,
  "name": "Ravi",
  "status": "ACTIVE"
}
```

Adding `status` is usually safe if old clients ignore unknown fields.

So we may not need `/v2`.

---

# Very Important Rule

Do not break existing clients.

That is the heart of API versioning.

Bad approach:

```text
Change v1 response directly
Remove old fields suddenly
Force all clients to update immediately
```

Better approach:

```text
Keep v1 stable
Create v2 for breaking changes
Let clients migrate slowly
Deprecate v1 with proper notice
Remove v1 only after agreed timeline
```

---

# API Versioning Example

Suppose we have this v1 API:

```http
GET /api/v1/customers/101
```

Response:

```json
{
  "id": 101,
  "name": "Ravi",
  "phone": "9876543210"
}
```

Now business wants to split name into first name and last name.

New response:

```json
{
  "id": 101,
  "firstName": "Ravi",
  "lastName": "Kumar",
  "phone": "9876543210"
}
```

This is a breaking change because `name` was removed.

So we should create:

```http
GET /api/v2/customers/101
```

And keep v1 running for old clients.

---

# Versioning In Spring Boot

A simple approach:

```java
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerV1Controller {

    @GetMapping("/{id}")
    public CustomerV1Response getCustomer(@PathVariable Long id) {
        return new CustomerV1Response(id, "Ravi Kumar");
    }
}
```

```java
@RestController
@RequestMapping("/api/v2/customers")
public class CustomerV2Controller {

    @GetMapping("/{id}")
    public CustomerV2Response getCustomer(@PathVariable Long id) {
        return new CustomerV2Response(id, "Ravi", "Kumar");
    }
}
```

DTOs should also be separate:

```java
public class CustomerV1Response {
    private Long id;
    private String name;

    public CustomerV1Response(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters and setters
}
```

```java
public class CustomerV2Response {
    private Long id;
    private String firstName;
    private String lastName;

    public CustomerV2Response(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters and setters
}
```

In real projects, the service layer can be reused.
Only the controller and DTO mapping may differ.

---

# Good Design Practice

Avoid duplicating full business logic in every version.

Bad:

```text
CustomerV1Controller has full logic
CustomerV2Controller has same full logic copied again
```

Better:

```text
Controller handles version contract
Mapper converts response for v1 or v2
Service contains business logic
```

Flow:

```text
Controller v1/v2
        ↓
Mapper v1/v2
        ↓
Common service
        ↓
Repository/database
```

This keeps the code clean.

---

# Deprecating Old APIs

Versioning is not only creating v2.

We also need a plan for old versions.

Good process:

```text
Mark v1 as deprecated
Inform frontend/mobile/partner teams
Add deprecation date in documentation
Add response header if needed
Monitor usage of v1
Move clients to v2
Remove v1 only after migration
```

Example response header:

```http
Deprecation: true
Sunset: Tue, 30 Sep 2026 23:59:59 GMT
```

This tells clients that the API is going away in the future.

---

# API Documentation

Each version should be documented clearly.

Example:

```text
/api/v1/users
/api/v2/users
```

Swagger/OpenAPI should show what changed.

For every version, document:

```text
Request body
Response body
Status codes
Error format
Authentication
Deprecated fields
Migration notes
```

This helps other teams avoid confusion.

---

# Versioning Internal Microservice APIs

For internal microservices, we should still be careful.

Just because the client is another internal service does not mean we can break it.

Good practice:

```text
Use backward-compatible changes when possible
Communicate breaking changes early
Use contract tests
Keep old version until consumers migrate
```

In microservices, API versioning is very important because many services can depend on one API.

---

# Versioning Async Events

API versioning is not only for REST.

Events also need versioning.

Example Kafka event v1:

```json
{
  "eventType": "ORDER_CREATED",
  "orderId": "ORD101",
  "amount": 1000
}
```

Event v2:

```json
{
  "eventType": "ORDER_CREATED",
  "orderId": "ORD101",
  "amount": 1000,
  "currency": "INR"
}
```

Adding `currency` is safe if it is optional.

But if we rename `orderId` to `orderNumber`, consumers may break.

So event contracts also need versioning.

Common ways:

```text
Add schema version in event
Use schema registry
Keep backward compatibility
Avoid removing fields suddenly
```

Example:

```json
{
  "schemaVersion": 2,
  "eventType": "ORDER_CREATED",
  "orderId": "ORD101",
  "amount": 1000,
  "currency": "INR"
}
```

---

# Common Interview Mistakes

## Mistake 1: Versioning Every Small Change

Do not create v2 for every small change.

Adding an optional field usually does not need a new version.

---

## Mistake 2: Breaking v1 Directly

Never remove or rename fields in existing version without migration plan.

---

## Mistake 3: Duplicating Too Much Code

Separate API contract, not full business logic.

Reuse service layer where possible.

---

## Mistake 4: No Deprecation Plan

Creating new version is easy.
Removing old version safely is the hard part.

Always talk about deprecation and client migration.

---

# Best Practices

Use these points in real projects:

```text
Prefer backward-compatible changes
Use new version only for breaking changes
Keep old version stable
Document all versions clearly
Use separate DTOs for each version
Reuse common service logic
Add contract tests
Give deprecation notice
Monitor usage before removing old version
Version events also, not only REST APIs
```

---

# Small Decision Table

| Change                       | Need New Version? |
| ---------------------------- | ----------------- |
| Add optional response field  | Usually no        |
| Add new endpoint             | No                |
| Remove response field        | Yes               |
| Rename response field        | Yes               |
| Change field type            | Yes               |
| Add required request field   | Yes               |
| Change business meaning      | Yes               |
| Improve performance only     | No                |
| Change internal DB logic     | No                |
| Change error response format | Usually yes       |

---

# Final Interview-Ready Answer

API versioning means managing API changes without breaking existing clients. I usually prefer URL versioning like `/api/v1/users` and `/api/v2/users` because it is simple, visible, easy to test, and easy to document. I create a new version only when there is a breaking change, like removing a field, renaming a field, changing a data type, changing the request structure, or changing the behavior of an endpoint. For backward-compatible changes, like adding an optional response field or adding a new endpoint, I usually do not create a new version. In implementation, I keep separate DTOs and controllers for each version if the contract changes, but I try to reuse the same service layer to avoid duplicate business logic. I also document each version, add a deprecation plan for old versions, monitor usage, and remove old APIs only after clients migrate. For event-driven systems, I also version event schemas because Kafka or messaging consumers can break if the event contract changes suddenly.

---

# 5. How do you design a payment API safely?

---
## Summary

A payment API must be designed with one main goal:

**Never charge the user twice. Never mark payment success without proof. Never trust client-side payment data.**

Payment APIs need strong safety around idempotency, validation, security, retries, webhooks, audit logs, and status handling.

## One-Line Answer

**I design a payment API safely by using idempotency keys, server-side amount validation, secure gateway integration, verified webhooks, proper payment states, database transactions, retries, and strong audit logging.**

---

# Basic Payment API Flow

A safe payment flow usually looks like this:

```text
Client
  ↓
Backend Payment API
  ↓
Database
  ↓
Payment Gateway
  ↓
Webhook back to Backend
  ↓
Update final payment status
```

The frontend should not directly decide payment success.

Frontend can start the payment.
But backend should verify the final status.

---

# Important APIs

A simple payment system can have these APIs:

```http
POST /api/v1/payments/initiate
GET  /api/v1/payments/{paymentId}
POST /api/v1/payments/webhook
POST /api/v1/payments/{paymentId}/refund
```

## 1. Initiate Payment

```http
POST /api/v1/payments/initiate
Idempotency-Key: abc-123
```

Request:

```json
{
  "orderId": "ORD123",
  "paymentMethod": "UPI"
}
```

Notice one important thing.

I am not taking the amount from the client.

The backend should calculate amount from the order.

Why?

Because client request can be changed.

Bad design:

```json
{
  "orderId": "ORD123",
  "amount": 1
}
```

If backend trusts this, it is a big security issue.

Better design:

```text
Backend fetches order amount from database.
Backend sends trusted amount to payment gateway.
```

---

# 2. Use Idempotency Key

This is one of the most important parts.

Payment APIs can be retried because of:

```text
Network timeout
User double-click
Mobile app retry
Load balancer retry
Gateway timeout
Response lost
```

So every payment request should have an idempotency key.

Example:

```http
Idempotency-Key: 8f1b6a2d-91c2-4c8a-9d77
```

Backend should store this key.

If the same request comes again, backend should not create a new payment.

It should return the old result.

Simple rule:

```text
Same idempotency key + same request = return previous response
Same idempotency key + different request = reject with 409 Conflict
```

This protects the user from duplicate payments.

---

# 3. Use Proper Payment States

Payment should not be just success or failed.

A real payment has multiple states.

Example:

```text
CREATED
PENDING
PROCESSING
SUCCESS
FAILED
CANCELLED
REFUND_INITIATED
REFUNDED
```

Simple meaning:

| Status           | Meaning                              |
| ---------------- | ------------------------------------ |
| CREATED          | Payment record created in our system |
| PENDING          | Waiting for gateway/user action      |
| PROCESSING       | Gateway is processing                |
| SUCCESS          | Payment confirmed                    |
| FAILED           | Payment failed                       |
| CANCELLED        | User cancelled payment               |
| REFUND_INITIATED | Refund started                       |
| REFUNDED         | Refund completed                     |

Important point:

```text
SUCCESS and FAILED are usually final states.
```

Once payment is successful, do not process it again.

---

# 4. Never Trust Frontend Success

This is a very common mistake.

Bad flow:

```text
Frontend says payment success
Backend marks order as paid
```

This is unsafe.

Frontend can be manipulated.

Safe flow:

```text
Frontend says payment completed
Backend checks payment status with gateway
or waits for verified webhook
Then backend marks payment success
```

The backend should trust only:

```text
Verified payment gateway response
Verified webhook
Gateway status check API
```

---

# 5. Webhook Must Be Verified

Payment gateways usually send webhooks.

Example:

```http
POST /api/v1/payments/webhook
```

Webhook says:

```json
{
  "gatewayPaymentId": "pay_123",
  "orderId": "ORD123",
  "status": "SUCCESS",
  "amount": 100000
}
```

But we should not trust webhook blindly.

We must verify:

```text
Webhook signature
Gateway payment id
Order id
Amount
Currency
Event timestamp
Duplicate event id
```

If signature is invalid, reject it.

```http
401 Unauthorized
```

or

```http
400 Bad Request
```

depending on company standard.

---

# 6. Handle Duplicate Webhooks

Payment gateways can send the same webhook multiple times.

So webhook processing must also be idempotent.

Example:

```text
Webhook event id = evt_123
```

Store processed event IDs.

If the same event comes again:

```text
Do not process again.
Return 200 OK.
```

Why return 200?

Because if we return failure, gateway may keep retrying.

---

# 7. Use Database Transaction

When payment succeeds, multiple things may happen:

```text
Update payment status
Update order status
Save gateway transaction id
Create invoice event
Send notification event
```

Critical database updates should be inside a transaction.

Example:

```java
@Transactional
public void markPaymentSuccess(String paymentId, String gatewayPaymentId) {
    Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found"));

    if ("SUCCESS".equals(payment.getStatus())) {
        return;
    }

    payment.setStatus("SUCCESS");
    payment.setGatewayPaymentId(gatewayPaymentId);

    Order order = orderRepository.findById(payment.getOrderId())
            .orElseThrow(() -> new RuntimeException("Order not found"));

    order.setStatus("PAID");

    paymentRepository.save(payment);
    orderRepository.save(order);
}
```

This is a simple example.

In real systems, also add locking or versioning.

---

# 8. Prevent Race Conditions

Two payment requests can come at the same time.

Example:

```text
User double-clicks Pay button
Two backend requests come together
```

To handle this, use:

```text
Unique constraint on idempotency key
Unique active payment per order
Database lock or optimistic locking
```

Example database constraints:

```sql
ALTER TABLE payments
ADD CONSTRAINT uk_payment_idempotency_key UNIQUE (idempotency_key);

ALTER TABLE payments
ADD CONSTRAINT uk_order_active_payment UNIQUE (order_id, payment_status);
```

The exact constraint depends on design.

But the goal is simple:

```text
One order should not get paid twice.
```

---

# 9. Amount Should Be Server-Side

This is very important.

The backend should calculate:

```text
Order amount
Discount
Tax
Delivery charge
Final payable amount
Currency
```

Do not trust these from frontend.

Also use safe money types.

Good choices:

```text
Store amount in paise/cents as long
or use BigDecimal carefully
```

Avoid floating point types like `double` for money.

Bad:

```java
double amount = 999.99;
```

Better:

```java
BigDecimal amount = new BigDecimal("999.99");
```

Or:

```java
long amountInPaise = 99999;
```

---

# 10. Retry Carefully

Retry is useful.
But blind retry is dangerous in payment.

If gateway timeout happens, we may not know if payment happened or not.

Example:

```text
Backend calls gateway
Gateway charges user
But response times out
```

Now backend should not create a new payment blindly.

Better approach:

```text
Mark payment as PENDING
Call gateway status API using gateway order id
Wait for webhook
Reconcile later if needed
```

For temporary failures, use retry with:

```text
Small retry count
Exponential backoff
Idempotency key
Gateway transaction reference
```

---

# 11. Use Outbox For Side Effects

After payment success, we may need to:

```text
Send email
Send SMS
Generate invoice
Update analytics
Notify seller
```

Do not do all of this inside the main payment request.

Better:

```text
Save payment success in DB
Save event in outbox table
Publish event to Kafka/RabbitMQ
Other services consume it
```

This avoids losing events.

It also keeps payment flow fast and reliable.

---

# 12. Security Points

A payment API must be secure.

Important points:

```text
Use HTTPS only
Use authentication and authorization
Validate every request
Do not log card number, CVV, or sensitive data
Use tokenization for cards
Store secrets in vault or secret manager
Verify gateway signatures
Use rate limiting
Use audit logs
Mask sensitive data in logs
```

Never store CVV.

Usually, do not store raw card details.

Use payment gateway tokens instead.

---

# 13. Audit Logging

Payment systems need strong audit logs.

Log useful details like:

```text
paymentId
orderId
userId
amount
currency
status change
gatewayPaymentId
requestId
idempotencyKey
timestamp
```

But do not log sensitive data like:

```text
card number
CVV
UPI PIN
OTP
full bank details
```

Audit logs help during:

```text
Customer complaint
Refund issue
Duplicate payment check
Reconciliation
Production debugging
```

---

# 14. Reconciliation Is Required

Sometimes webhook may fail.

Sometimes gateway status and our DB status may not match.

So a scheduled reconciliation job is useful.

Example:

```text
Find payments stuck in PENDING for more than 15 minutes
Call gateway status API
Update local payment status
Raise alert if mismatch found
```

This is very important in real payment systems.

---

# 15. Good Response Status Codes

Use clean status codes.

| Situation                                       | Status Code               |
| ----------------------------------------------- | ------------------------- |
| Payment initiated                               | 201 Created               |
| Payment already exists for same idempotency key | 200 OK                    |
| Async payment accepted                          | 202 Accepted              |
| Invalid request                                 | 400 Bad Request           |
| Missing/invalid token                           | 401 Unauthorized          |
| User not allowed                                | 403 Forbidden             |
| Order not found                                 | 404 Not Found             |
| Order already paid                              | 409 Conflict              |
| Same idempotency key with different body        | 409 Conflict              |
| Business validation failed                      | 422 Unprocessable Entity  |
| Too many requests                               | 429 Too Many Requests     |
| Gateway timeout                                 | 504 Gateway Timeout       |
| Gateway unavailable                             | 503 Service Unavailable   |
| Unknown server issue                            | 500 Internal Server Error |

---

# Simple Payment Table Design

```sql
CREATE TABLE payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    payment_id VARCHAR(100) NOT NULL UNIQUE,
    order_id VARCHAR(100) NOT NULL,
    user_id VARCHAR(100) NOT NULL,
    amount_in_paise BIGINT NOT NULL,
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(30) NOT NULL,
    payment_method VARCHAR(30),
    idempotency_key VARCHAR(150) NOT NULL UNIQUE,
    request_hash VARCHAR(255) NOT NULL,
    gateway_order_id VARCHAR(150),
    gateway_payment_id VARCHAR(150),
    failure_reason VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);
```

Webhook event table:

```sql
CREATE TABLE payment_webhook_events (
    event_id VARCHAR(150) PRIMARY KEY,
    gateway_payment_id VARCHAR(150),
    event_type VARCHAR(100),
    processed_at TIMESTAMP NOT NULL
);
```

---

# Simple Initiate Payment Logic

```text
1. Validate user
2. Validate order
3. Check order belongs to user
4. Check order is not already paid
5. Check idempotency key
6. Calculate amount from backend database
7. Create payment record as CREATED or PENDING
8. Call payment gateway with trusted amount
9. Save gateway order id
10. Return payment details to client
```

---

# Simple Webhook Logic

```text
1. Receive webhook
2. Verify signature
3. Check if event already processed
4. Fetch payment using gateway payment id/order id
5. Validate amount and currency
6. Update payment status
7. Update order status if success
8. Save webhook event id
9. Publish PaymentSuccess event
10. Return 200 OK
```

---

# Common Mistakes

## Mistake 1: Trusting Amount From Client

Frontend can be changed.

Always calculate amount on backend.

---

## Mistake 2: No Idempotency

This can cause duplicate charges or duplicate orders.

---

## Mistake 3: Marking Success From Frontend Redirect

Frontend redirect is not proof.

Verify with gateway or webhook.

---

## Mistake 4: Not Handling Timeout Properly

Timeout does not always mean payment failed.

It can be unknown.

Mark it as pending and verify later.

---

## Mistake 5: Not Handling Duplicate Webhooks

Same webhook can come many times.

Consumer must be idempotent.

---

## Mistake 6: Logging Sensitive Data

Never log card number, CVV, OTP, or UPI PIN.

---

# Interview-Ready Paragraph Answer

To design a payment API safely, I first make sure the API is idempotent, secure, and reliable. I use an idempotency key for payment initiation so retries or double-clicks do not create duplicate payments. I never trust the amount coming from the frontend. The backend calculates the final amount from the order, discount, tax, and currency. I create a payment record with states like CREATED, PENDING, SUCCESS, and FAILED. I also verify payment success using the payment gateway response or a signed webhook, not only from the frontend redirect. Webhook processing should also be idempotent because gateways can send duplicate events. I use database transactions, unique constraints, and locking to avoid race conditions. For timeouts, I do not blindly retry the charge. I mark the payment as pending and check the gateway status later. I also keep audit logs, mask sensitive data, use HTTPS, verify signatures, add rate limiting, and run reconciliation jobs for stuck payments. In simple words, a safe payment API should protect against duplicate charges, wrong success status, bad retries, fake requests, and data mismatch with the payment gateway.

---

# 6. How do you handle retries and timeouts?

---
## Summary

Retries and timeouts are used to make backend systems more reliable.

**Timeout means we do not wait forever.**
**Retry means we try again when the failure is temporary.**

But retries must be done carefully.
A bad retry design can overload the system or create duplicate operations.

---

## One-Line Answer

**I handle timeouts by setting clear limits for every external call, and I handle retries only for temporary failures using limited retry count, backoff, jitter, and idempotency.**

---

# Why Timeouts Are Needed

In backend systems, one service often calls another service.

Example:

```text
Order Service -> Payment Service
Order Service -> Inventory Service
Order Service -> Notification Service
```

If Payment Service becomes slow, Order Service should not wait forever.

Without timeout:

```text
Thread keeps waiting
Request hangs
Connection pool gets blocked
User waits too long
System becomes slow
```

So every external call should have a timeout.

Example:

```text
Payment Service timeout: 3 seconds
Inventory Service timeout: 2 seconds
Notification Service timeout: 1 second
```

The exact value depends on the business flow.

---

# Simple Meaning Of Timeout

Timeout means:

```text
I will wait only for a fixed time.
If there is no response within that time, I will stop waiting.
```

Example:

```text
Order Service calls Payment Service.
Timeout is 3 seconds.
If Payment Service does not reply in 3 seconds, Order Service stops waiting.
```

Then we can return an error, retry, fallback, or mark the request as pending.

---

# Why Retries Are Needed

Some failures are temporary.

Example:

```text
Network glitch
Temporary timeout
Service restart
Connection reset
Gateway returned 503
Database deadlock
Message broker temporary issue
```

In these cases, retry may help.

But retry should not be blind.

Bad retry:

```text
Retry forever
Retry immediately
Retry every error
Retry non-idempotent operation without safety
```

This can make the system worse.

---

# Retry Should Be Used Only For Temporary Failures

Good retry candidates:

```text
Timeout
Connection reset
503 Service Unavailable
504 Gateway Timeout
429 Too Many Requests, after delay
Temporary network error
```

Do not retry these usually:

```text
400 Bad Request
401 Unauthorized
403 Forbidden
404 Not Found
422 Business validation error
Duplicate request error
Invalid input
```

Because these are not temporary problems.

If request data is wrong, retrying will not fix it.

---

# Important Rule

```text
Timeout protects waiting.
Retry protects temporary failure.
Circuit breaker protects the system.
Idempotency protects duplicate side effects.
```

All four are connected.

---

# Simple Retry Flow

```text
1. Call external service
2. If success, return response
3. If temporary failure, retry
4. Wait before retrying
5. Retry only limited times
6. If still failing, return fallback or failure
```

Example:

```text
Try 1 -> timeout
Wait 200 ms
Try 2 -> connection error
Wait 500 ms
Try 3 -> success
```

---

# Use Limited Retry Count

Never retry forever.

Example:

```text
Max retries = 2 or 3
```

If we retry too many times, the system becomes slow.

Also, each retry increases load on the failing service.

Example:

```text
1000 requests come in
Each request retries 3 times
Downstream receives 3000 calls
```

This can make the downstream service even more unstable.

This is called a retry storm.

---

# Use Backoff

Backoff means we wait longer before each retry.

Bad:

```text
Retry immediately
Retry immediately
Retry immediately
```

Better:

```text
Retry after 200 ms
Retry after 500 ms
Retry after 1 second
```

This gives the downstream service some time to recover.

---

# Use Jitter

Jitter means adding small randomness to retry delay.

Without jitter:

```text
All requests retry at the same time.
```

That can again overload the service.

With jitter:

```text
Some retry after 200 ms
Some retry after 260 ms
Some retry after 340 ms
```

This spreads the load.

---

# Use Idempotency For Safe Retries

Retries can be dangerous for operations that create or change data.

Example:

```http
POST /payments
```

If this request times out, we do not know what happened.

Maybe payment failed.
Maybe payment succeeded but response was lost.

If we retry without idempotency, user may be charged twice.

So for unsafe operations, use an idempotency key.

Example:

```http
POST /payments
Idempotency-Key: abc-123
```

If the same request is retried, backend should not create duplicate payment.

It should return the previous result.

---

# Timeout Does Not Always Mean Failure

This is very important.

Suppose backend calls payment gateway.

```text
Backend -> Payment Gateway
```

The request times out.

It does not always mean payment failed.

It can mean:

```text
Payment failed
Payment succeeded but response was lost
Gateway is still processing
Network broke after gateway processed it
```

So for payment APIs, do not blindly retry a charge.

Better approach:

```text
Mark payment as PENDING
Check gateway status later
Wait for webhook
Run reconciliation job
```

This is a very good interview point.

---

# Where To Set Timeouts

Timeouts should be set for all external dependencies.

Example:

```text
HTTP client calls
Database queries
Redis calls
Kafka producer calls
Third-party APIs
File uploads
Message processing
```

Do not rely on default timeout values.

Many defaults are too high or unlimited.

---

# Types Of Timeouts

## 1. Connection Timeout

This is the time allowed to create a connection.

Example:

```text
Can I connect to Payment Service within 1 second?
```

If not, fail fast.

---

## 2. Read Timeout

This is the time allowed to wait for response after connection is created.

Example:

```text
I connected to Payment Service.
Now I will wait 3 seconds for response.
```

---

## 3. Total Request Timeout

This is the full time allowed for the whole operation.

Example:

```text
Complete full API call within 5 seconds.
```

This includes connection, request, response, and sometimes retries.

---

# Timeout Values Should Be Practical

Timeouts should not be too low or too high.

Too low:

```text
Valid slow requests fail too often.
```

Too high:

```text
Threads stay blocked.
User waits too long.
System becomes slow.
```

Good timeout depends on:

```text
Business criticality
Downstream SLA
User experience
Average latency
P95/P99 latency
Number of retries
```

Example:

```text
User-facing API total timeout: 2 to 5 seconds
Background job timeout: can be higher
Notification call timeout: can be short
Payment status check: depends on gateway SLA
```

---

# Retry With Circuit Breaker

If downstream service is already failing, retries can make it worse.

So we use a circuit breaker.

Simple idea:

```text
If many calls are failing, stop calling for some time.
```

Circuit breaker states:

```text
CLOSED      -> calls are allowed
OPEN        -> calls are blocked
HALF_OPEN   -> allow few test calls
```

Example:

```text
Payment Service is down.
Order Service stops calling it for 30 seconds.
After that, it sends a few test calls.
If they pass, circuit closes again.
```

This protects both services.

---

# Retry With Fallback

Sometimes we can return fallback data.

Example:

```text
Recommendation Service is down.
Return default recommendations.
```

But not every system can use fallback.

For payment:

```text
Do not assume payment success.
Mark as pending or failed based on safe rules.
```

For notification:

```text
If SMS fails, save retry event and process later.
```

---

# Retry In Async Messaging

Retries are very common in Kafka, RabbitMQ, and SQS.

Example:

```text
Consumer receives message
Processing fails due to temporary DB issue
Consumer retries
```

Good async retry design:

```text
Retry limited times
Use retry topic or delay queue
Use dead letter queue
Make consumer idempotent
Log failure reason
Alert if too many messages go to DLQ
```

DLQ means Dead Letter Queue.

It stores messages that failed after all retries.

---

# Example In Kafka Consumer

```text
OrderCreated event received
Inventory update fails
Retry 3 times
Still fails
Move message to DLQ
Alert team
```

The consumer should also be idempotent.

Because the same message may be processed more than once.

---

# Retry In REST APIs

For REST calls, retry should usually be done only for safe or idempotent operations.

Good examples:

```text
GET product details
GET payment status
PUT update user profile
DELETE idempotent delete
```

Be careful with:

```text
POST create payment
POST create order
POST transfer money
POST book ticket
```

For POST APIs, retry only if idempotency is implemented.

---

# Good Status Codes For Timeout And Retry Cases

| Situation                         | Status Code               |
| --------------------------------- | ------------------------- |
| Downstream timeout                | 504 Gateway Timeout       |
| Downstream unavailable            | 503 Service Unavailable   |
| Too many requests                 | 429 Too Many Requests     |
| Temporary accepted for processing | 202 Accepted              |
| Unknown internal error            | 500 Internal Server Error |
| Business validation failure       | 422 Unprocessable Entity  |
| Bad client request                | 400 Bad Request           |

---

# Spring Boot Example Using Resilience4j

A common way in Spring Boot is Resilience4j.

Example:

```java
@Service
public class PaymentClientService {

    private final PaymentGatewayClient paymentGatewayClient;

    public PaymentClientService(PaymentGatewayClient paymentGatewayClient) {
        this.paymentGatewayClient = paymentGatewayClient;
    }

    @Retry(name = "paymentGateway", fallbackMethod = "paymentFallback")
    @CircuitBreaker(name = "paymentGateway", fallbackMethod = "paymentFallback")
    @TimeLimiter(name = "paymentGateway")
    public CompletableFuture<PaymentStatusResponse> getPaymentStatus(String paymentId) {
        return CompletableFuture.supplyAsync(() ->
                paymentGatewayClient.getPaymentStatus(paymentId)
        );
    }

    public CompletableFuture<PaymentStatusResponse> paymentFallback(
            String paymentId,
            Throwable ex
    ) {
        PaymentStatusResponse response = new PaymentStatusResponse();
        response.setPaymentId(paymentId);
        response.setStatus("PENDING");
        response.setMessage("Payment status is currently unknown. Please check later.");

        return CompletableFuture.completedFuture(response);
    }
}
```

This is a simple example.

In real projects, we also configure retry count, wait duration, timeout duration, and ignored exceptions.

---

# Example Configuration

```yaml
resilience4j:
  retry:
    instances:
      paymentGateway:
        max-attempts: 3
        wait-duration: 500ms
        retry-exceptions:
          - java.net.SocketTimeoutException
          - java.io.IOException
        ignore-exceptions:
          - com.example.InvalidPaymentRequestException

  circuitbreaker:
    instances:
      paymentGateway:
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s
        permitted-number-of-calls-in-half-open-state: 5
        sliding-window-size: 20

  timelimiter:
    instances:
      paymentGateway:
        timeout-duration: 3s
```

Meaning:

```text
Try maximum 3 times
Wait 500 ms between retries
Open circuit if failure rate is high
Timeout after 3 seconds
```

---

# Simple RestTemplate Timeout Example

```java
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        factory.setConnectTimeout(1000);
        factory.setReadTimeout(3000);

        return new RestTemplate(factory);
    }
}
```

Meaning:

```text
Connection timeout = 1 second
Read timeout = 3 seconds
```

---

# Simple WebClient Timeout Example

```java
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(3))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
```

This is common in reactive Spring Boot projects.

---

# Common Mistakes

## Mistake 1: No Timeout

This is dangerous.

A service can wait forever and block threads.

---

## Mistake 2: Retrying Every Error

Do not retry client errors like 400, 401, 403, 404, or validation errors.

They will fail again.

---

## Mistake 3: Retrying Too Many Times

Too many retries can overload the downstream service.

Use limited retry count.

---

## Mistake 4: No Backoff

Immediate retries can create heavy load.

Use backoff and jitter.

---

## Mistake 5: Retrying Payments Without Idempotency

This can create duplicate charges.

For payment or order creation, use idempotency key.

---

## Mistake 6: Timeout Longer Than User Can Wait

If frontend waits only 5 seconds, but backend timeout is 30 seconds, that is bad design.

The backend may keep working even after the user has already left.

---

## Mistake 7: No Logging Or Metrics

Retries and timeouts must be visible.

Log important details:

```text
requestId
downstream service name
attempt number
timeout value
failure reason
final result
```

Track metrics:

```text
retry count
timeout count
success after retry
failure after retry
circuit breaker open count
DLQ count
```

---

# Practical Example

Suppose Order Service calls Inventory Service.

Good approach:

```text
Set timeout to 2 seconds
Retry only 2 times for timeout or 503
Use backoff with jitter
If still failing, return 503 or mark order as pending
Do not retry if inventory says product not found
Log all attempts with requestId
```

For Payment Service:

```text
Set timeout
Use idempotency key
Do not blindly charge again after timeout
Mark as PENDING
Check gateway status
Wait for webhook
Run reconciliation
```

---

# Best Practices

```text
Always set timeouts
Use retries only for temporary failures
Keep retry count small
Use exponential backoff
Add jitter
Use circuit breaker
Use idempotency for write operations
Do not retry bad requests
Use fallback only when safe
Use DLQ for async failures
Log and monitor retry behavior
```

---

# Interview-Ready Paragraph Answer

I handle timeouts by setting clear timeout values for every external call, like HTTP APIs, database calls, Redis, Kafka, or third-party services. This prevents threads from waiting forever and protects the system from slow dependencies. For retries, I retry only temporary failures like timeout, connection reset, 503, 504, or sometimes 429 after some delay. I do not retry client errors like 400, 401, 403, 404, or business validation errors because they will usually fail again. I keep retry count small, use backoff and jitter, and combine retries with circuit breaker so that we do not overload a failing service. For write operations like payment, order creation, or fund transfer, I retry only when idempotency is handled. In payment systems, if a timeout happens, I do not blindly charge again. I mark the payment as pending, check gateway status, or wait for webhook. In async systems, I use retry topics, dead letter queue, and idempotent consumers. So my main rule is simple: timeout fast, retry carefully, avoid duplicate side effects, and monitor everything.

---

# 7. Circuit breaker and fallback

---
## Summary

Circuit breaker and fallback are used to protect a backend system when another service is slow or failing.

**Circuit breaker stops repeated calls to a failing service.**
**Fallback gives a safe alternate response when the main call fails.**

They are mostly used in microservices and external API calls.

---

## One-Line Answer

**A circuit breaker prevents continuous calls to a failing dependency, and fallback provides a safe backup response when the dependency is not available.**

---

# Simple Meaning

Suppose `Order Service` calls `Payment Service`.

```text
Order Service ---> Payment Service
```

If `Payment Service` is down, and `Order Service` keeps calling it again and again, then many things can go wrong.

```text
Threads get blocked
Requests become slow
CPU and memory usage increases
User experience becomes bad
The failing service gets even more load
```

So we use a circuit breaker.

It says:

```text
Payment Service is failing too much.
Stop calling it for some time.
Return fallback or fail fast.
```

---

# Real-Life Example

Think about electricity at home.

If there is too much load, the circuit breaker trips.

It stops the current to protect the wiring.

Backend circuit breaker works in a similar way.

If a service is failing too much, the circuit breaker opens and stops calls to that service for some time.

---

# Circuit Breaker States

A circuit breaker usually has 3 states.

```text
CLOSED
OPEN
HALF_OPEN
```

---

## 1. CLOSED State

This is the normal state.

Calls are allowed.

```text
Order Service ---> Payment Service
```

If calls are successful, circuit remains closed.

But if failures cross a limit, circuit moves to open state.

Example:

```text
50% calls failed in last 20 requests
```

Then circuit opens.

---

## 2. OPEN State

In open state, calls are blocked.

The service does not call the failing dependency.

```text
Order Service -X-> Payment Service
```

Instead, it returns fallback or error quickly.

This is called **fail fast**.

It protects the system from waiting again and again.

---

## 3. HALF_OPEN State

After some waiting time, circuit breaker allows a few test calls.

Example:

```text
Allow 3 test calls
```

If those calls succeed, circuit goes back to closed state.

If they fail, circuit goes back to open state.

Simple flow:

```text
CLOSED -> OPEN -> HALF_OPEN -> CLOSED
```

or

```text
CLOSED -> OPEN -> HALF_OPEN -> OPEN
```

---

# What Is Fallback?

Fallback means a backup response when the main service is not available.

Example:

```text
Recommendation Service is down.
Return default recommendations.
```

Another example:

```text
Product Rating Service is down.
Return product details without rating.
```

Fallback should be safe.

It should not lie to the user.

---

# Simple Example

Suppose we are showing product details.

Main response should include:

```json
{
  "productId": "P101",
  "name": "Laptop",
  "rating": 4.5
}
```

But Rating Service is down.

Fallback response can be:

```json
{
  "productId": "P101",
  "name": "Laptop",
  "rating": null,
  "message": "Rating is currently unavailable"
}
```

This is safe.

The user can still see product details.

---

# When To Use Circuit Breaker

Use circuit breaker when your service depends on another service.

Common cases:

```text
Calling another microservice
Calling payment gateway
Calling SMS/email provider
Calling third-party API
Calling external banking system
Calling search service
Calling recommendation service
```

It is useful when the dependency can become slow or unavailable.

---

# When To Use Fallback

Use fallback when a safe alternate response is possible.

Good fallback examples:

```text
Return cached data
Return default data
Return partial response
Return empty list
Put request in queue for later processing
Mark status as pending
```

Bad fallback examples:

```text
Assume payment is successful
Assume order is delivered
Assume money transfer is complete
Give fake balance
```

Fallback should never create wrong business results.

---

# Important Difference

| Point     | Circuit Breaker                       | Fallback                         |
| --------- | ------------------------------------- | -------------------------------- |
| Main job  | Stop calls to failing service         | Return backup response           |
| Protects  | Caller and dependency                 | User experience                  |
| Example   | Stop calling Payment Service          | Return pending status            |
| Used when | Dependency is failing repeatedly      | Safe alternate response exists   |
| Risk      | Wrong threshold can block valid calls | Bad fallback can give wrong data |

---

# Simple Flow

```text
Client calls Order Service
        ↓
Order Service calls Payment Service
        ↓
Payment Service is slow/failing
        ↓
Circuit breaker detects failures
        ↓
Circuit opens
        ↓
Next calls do not go to Payment Service
        ↓
Fallback response is returned
```

---

# Example In Spring Boot With Resilience4j

## Service Code

```java
@Service
public class PaymentStatusService {

    private final PaymentClient paymentClient;

    public PaymentStatusService(PaymentClient paymentClient) {
        this.paymentClient = paymentClient;
    }

    @CircuitBreaker(name = "paymentService", fallbackMethod = "paymentFallback")
    public PaymentStatusResponse getPaymentStatus(String paymentId) {
        return paymentClient.getPaymentStatus(paymentId);
    }

    public PaymentStatusResponse paymentFallback(String paymentId, Throwable ex) {
        PaymentStatusResponse response = new PaymentStatusResponse();
        response.setPaymentId(paymentId);
        response.setStatus("UNKNOWN");
        response.setMessage("Payment status is temporarily unavailable. Please check again later.");
        return response;
    }
}
```

Here, if `paymentClient.getPaymentStatus()` fails again and again, the circuit breaker opens.

Then the fallback method is called.

---

## Simple Response DTO

```java
public class PaymentStatusResponse {

    private String paymentId;
    private String status;
    private String message;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
```

---

## Example Configuration

```yaml
resilience4j:
  circuitbreaker:
    instances:
      paymentService:
        sliding-window-size: 20
        minimum-number-of-calls: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s
        permitted-number-of-calls-in-half-open-state: 3
```

Meaning:

```text
Look at last 20 calls
Start checking only after minimum 10 calls
If 50% calls fail, open the circuit
Keep circuit open for 30 seconds
Then allow 3 test calls in half-open state
```

---

# Important Config Terms

## sliding-window-size

This means how many recent calls are checked.

Example:

```text
Last 20 calls
```

---

## failure-rate-threshold

This means failure percentage.

Example:

```text
If 50% calls fail, open circuit.
```

---

## wait-duration-in-open-state

This means how long circuit stays open before testing again.

Example:

```text
30 seconds
```

---

## permitted-number-of-calls-in-half-open-state

This means how many test calls are allowed when checking recovery.

Example:

```text
Allow 3 test calls.
```

---

# Circuit Breaker vs Retry

These two are related, but different.

## Retry

Retry says:

```text
Try again. Maybe it was a temporary issue.
```

## Circuit Breaker

Circuit breaker says:

```text
Too many failures are happening.
Stop calling for some time.
```

Good design often uses both.

But be careful.

If retry is not controlled, it can create more load.

Example:

```text
1 request with 3 retries = 4 calls
1000 requests with 3 retries = 4000 calls
```

So retries should be limited.

Use retry with timeout, backoff, and circuit breaker.

---

# Circuit Breaker vs Timeout

Timeout means:

```text
Do not wait forever.
```

Circuit breaker means:

```text
Do not keep calling a service that is already failing.
```

Both are needed.

Example:

```text
Timeout protects one request.
Circuit breaker protects the whole system.
```

---

# Circuit Breaker vs Fallback

Circuit breaker decides whether to allow or block the call.

Fallback decides what response to return when the call fails or is blocked.

Simple:

```text
Circuit breaker = decision to stop calling
Fallback = backup response
```

---

# Good Fallback Examples

## 1. Cached Data

If Product Service is down:

```text
Return last cached product details.
```

But clearly mark data as possibly old if needed.

---

## 2. Empty List

If Recommendation Service is down:

```json
{
  "recommendations": []
}
```

This is better than failing the full page.

---

## 3. Partial Response

If Rating Service is down:

```json
{
  "productId": "P101",
  "name": "Laptop",
  "rating": null
}
```

---

## 4. Queue For Later

If Email Service is down:

```text
Save notification request in database or queue.
Send email later.
```

---

## 5. Pending Status

If Payment Gateway status check fails:

```json
{
  "paymentId": "PAY123",
  "status": "PENDING",
  "message": "Payment status is being verified"
}
```

This is safer than saying success or failed without proof.

---

# Bad Fallback Examples

These are dangerous:

```text
Payment gateway failed, so mark payment as success
Inventory service failed, so assume stock is available
Bank service failed, so show random balance
Auth service failed, so allow access
```

Fallback should never break business safety or security.

---

# Status Codes With Circuit Breaker

It depends on the case.

## Dependency Down

If the API cannot complete because dependency is down:

```http
503 Service Unavailable
```

## Dependency Timeout

```http
504 Gateway Timeout
```

## Partial Response Possible

```http
200 OK
```

But include clear data fields.

Example:

```json
{
  "productId": "P101",
  "name": "Laptop",
  "rating": null,
  "ratingStatus": "UNAVAILABLE"
}
```

## Async Accepted

If request is saved for later processing:

```http
202 Accepted
```

Example:

```json
{
  "requestId": "REQ123",
  "status": "QUEUED"
}
```

---

# Where Circuit Breaker Should Be Used

Usually at the caller side.

Example:

```text
Order Service calls Payment Service.
Circuit breaker should be in Order Service.
```

Because Order Service needs to protect itself from slow Payment Service.

Also, API Gateway can have circuit breakers for some cases.

---

# Common Mistakes

## Mistake 1: Using Fallback Everywhere

Fallback is not always safe.

For critical flows, fail fast or return pending.

---

## Mistake 2: Marking Wrong Success

Never mark payment, refund, order, or transfer as success from fallback.

---

## Mistake 3: No Timeout

Circuit breaker without timeout is weak.

A slow call can still block threads.

Use timeout also.

---

## Mistake 4: Bad Threshold

If threshold is too low, circuit opens too easily.

If threshold is too high, it opens too late.

Tune it using real traffic and metrics.

---

## Mistake 5: No Monitoring

You should monitor:

```text
Circuit open count
Fallback count
Failure rate
Slow call rate
Timeout count
Retry count
```

Without monitoring, you will not know when dependencies are failing.

---

# Best Practices

```text
Use circuit breaker for external calls
Always set timeout also
Use fallback only when it is safe
Keep fallback response honest
Do not hide critical failures
Use retries carefully
Use metrics and alerts
Tune thresholds based on real traffic
Use different configs for different services
Do not use same fallback logic for every dependency
```

---

# Practical Example

Suppose we have an e-commerce product page.

The page needs:

```text
Product details
Price
Ratings
Recommendations
```

If Recommendation Service fails, we can use fallback:

```text
Return empty recommendations
```

If Rating Service fails:

```text
Return product without rating
```

But if Price Service fails:

```text
Do not allow checkout
```

Because wrong price can cause business loss.

So fallback depends on business criticality.

---

# Interview-Ready Paragraph Answer

Circuit breaker is a resilience pattern used when one service calls another service. If the downstream service starts failing or becoming slow, the circuit breaker opens and stops sending more calls for some time. This protects the caller from blocked threads and also protects the failing service from extra load. It usually has three states: closed, open, and half-open. In closed state, calls are allowed. In open state, calls are blocked and we fail fast. After some time, it moves to half-open and allows a few test calls. If those calls succeed, it closes again. Fallback is the backup response we return when the main call fails or circuit is open. For example, we can return cached data, empty recommendations, partial response, queued status, or pending status. But fallback must be safe. We should never mark payment success, allow access, or show fake balance from fallback. In real systems, I use circuit breaker with timeout, limited retries, proper logging, metrics, and alerts.

---

# 8. Correlation IDs and traceability

---
## Summary

Correlation ID and traceability help us track one request across many services.

**Correlation ID tells us which logs belong to the same request.**
**Traceability helps us understand the full journey of that request.**

This is very important in microservices.

---

## One-Line Answer

**I use a correlation ID for every request, pass it across all services, add it in logs, and use tracing tools like OpenTelemetry, Jaeger, Zipkin, or APM to track the full request flow.**

---

# Simple Meaning

Suppose one user places an order.

The request may travel like this:

```text
Frontend
   ↓
API Gateway
   ↓
Order Service
   ↓
Payment Service
   ↓
Inventory Service
   ↓
Notification Service
```

Now imagine something failed.

Without correlation ID, logs will look scattered.

You will not know which log belongs to which user request.

With correlation ID, every log line has the same ID.

Example:

```text
correlationId=abc-123 Order request received
correlationId=abc-123 Payment request started
correlationId=abc-123 Inventory updated
correlationId=abc-123 Notification sent
```

Now debugging becomes much easier.

---

# What Is Correlation ID?

A correlation ID is a unique ID for one request or business flow.

Example:

```text
X-Correlation-ID: 8f1b6a2d-91c2-4c8a-9d77
```

It is added when the request enters the system.

Usually at:

```text
API Gateway
Load Balancer
Backend entry service
```

Then every service passes the same ID to the next service.

---

# What Problem Does It Solve?

In real systems, one API call may touch many services.

Example:

```text
GET /orders/123
```

This may call:

```text
Order DB
Payment Service
Shipment Service
Customer Service
Redis
Kafka
```

If something fails, we need to answer:

```text
Where did it fail?
How much time did each service take?
Which downstream call was slow?
Which logs belong to this request?
Was the issue in our service or another service?
```

Correlation ID helps answer these questions.

---

# Correlation ID vs Trace ID

These terms are close, but not always same.

## Correlation ID

Correlation ID is usually a business-level request ID.

It helps group logs across services.

Example:

```text
X-Correlation-ID: abc-123
```

It is simple and very useful for logging.

---

## Trace ID

Trace ID is used in distributed tracing.

It tracks one full request across services.

Each service call can have a span.

Example:

```text
Trace ID: trace-789
Span 1: API Gateway
Span 2: Order Service
Span 3: Payment Service
Span 4: Database call
```

Trace ID is usually handled by tools like OpenTelemetry.

---

## Simple Difference

```text
Correlation ID = helps find all logs of one request
Trace ID = helps see full request path and timing
```

In many systems, both can be same or linked.

---

# What Is Span ID?

A span means one operation inside a trace.

Example:

```text
Trace ID: T123
```

Inside this trace:

```text
Span 1: API Gateway took 20 ms
Span 2: Order Service took 100 ms
Span 3: Payment Service took 300 ms
Span 4: DB query took 50 ms
```

So:

```text
Trace ID = full journey
Span ID = one step in that journey
```

---

# How Request Flows With Correlation ID

Example request:

```http
GET /api/v1/orders/ORD123
X-Correlation-ID: abc-123
```

Order Service receives it.

Then it calls Payment Service:

```http
GET /api/v1/payments/ORD123
X-Correlation-ID: abc-123
```

Then Payment Service logs:

```text
correlationId=abc-123 Fetching payment for order ORD123
```

So every service keeps the same ID.

---

# What If Client Does Not Send Correlation ID?

Then backend should generate one.

Simple rule:

```text
If request has X-Correlation-ID, use it.
If not, generate a new UUID.
```

Example:

```text
Generated correlationId = 9b7c1d2e-1111-4444-9999
```

Then return it in response header also.

```http
X-Correlation-ID: 9b7c1d2e-1111-4444-9999
```

This helps client support teams too.

If a user reports issue, they can share the correlation ID.

---

# Common Headers

Common header names are:

```text
X-Correlation-ID
X-Request-ID
traceparent
X-B3-TraceId
```

For modern tracing, `traceparent` is used by W3C Trace Context.

For simple app-level logging, many teams use `X-Correlation-ID`.

The exact name depends on company standard.

---

# Where To Add Correlation ID

Add it in:

```text
Incoming request logs
Outgoing HTTP calls
Database-related logs if needed
Kafka messages
Error logs
Audit logs
Response headers
APM traces
```

The most important part is consistency.

If one service forgets to pass it, traceability breaks.

---

# Spring Boot Practical Approach

In Spring Boot, we usually add a filter.

The filter does this:

```text
1. Read X-Correlation-ID from request header
2. If missing, generate new UUID
3. Put it in MDC
4. Continue request
5. Add same ID in response header
6. Clear MDC after request ends
```

MDC means Mapped Diagnostic Context.

It adds values automatically to logs.

---

# Simple Spring Boot Filter Example

```java
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String CORRELATION_ID_KEY = "correlationId";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String correlationId = request.getHeader(CORRELATION_ID_HEADER);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        try {
            MDC.put(CORRELATION_ID_KEY, correlationId);
            response.setHeader(CORRELATION_ID_HEADER, correlationId);

            filterChain.doFilter(request, response);

        } finally {
            MDC.remove(CORRELATION_ID_KEY);
        }
    }
}
```

This is a very common practical solution.

---

# Logback Pattern Example

To print correlation ID in logs, update log pattern.

Example:

```xml
<pattern>
    %d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level correlationId=%X{correlationId} %logger{36} - %msg%n
</pattern>
```

Now logs will look like:

```text
2026-05-03 10:30:20 [http-nio-8080-exec-1] INFO correlationId=abc-123 Order created successfully
```

This makes production debugging much easier.

---

# Passing Correlation ID To Downstream APIs

If Service A calls Service B, Service A must forward the correlation ID.

Example with `RestTemplate` interceptor:

```java
@Bean
public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    restTemplate.getInterceptors().add((request, body, execution) -> {
        String correlationId = MDC.get("correlationId");

        if (correlationId != null) {
            request.getHeaders().add("X-Correlation-ID", correlationId);
        }

        return execution.execute(request, body);
    });

    return restTemplate;
}
```

Now every outgoing REST call carries the same correlation ID.

---

# Passing Correlation ID In Kafka

For async messaging, we should also pass correlation ID.

Example message header:

```text
correlationId=abc-123
```

When publishing event:

```text
Order Service publishes OrderCreated event with correlationId
```

Consumer reads it:

```text
Inventory Service consumes event
Puts correlationId in MDC
Logs with same ID
```

This is very important because async flows are harder to debug.

---

# Example Kafka Flow

```text
Order Service receives request
correlationId=abc-123

Order Service publishes ORDER_CREATED event
event header correlationId=abc-123

Inventory Service consumes event
logs with correlationId=abc-123

Notification Service consumes event
logs with correlationId=abc-123
```

Now one order journey can be traced across sync and async systems.

---

# Traceability In Microservices

Traceability means we can see the full request journey.

Example:

```text
API Gateway        20 ms
Order Service      80 ms
Payment Service    400 ms
Inventory Service  60 ms
Database Query      30 ms
```

This helps find slow parts.

If total API time is 800 ms, tracing can show where time went.

Maybe Payment Service took 400 ms.

So we know where to check.

---

# Tools Used For Traceability

Common tools:

```text
OpenTelemetry
Jaeger
Zipkin
Grafana Tempo
Datadog APM
New Relic
Elastic APM
Dynatrace
```

In modern systems, OpenTelemetry is commonly used for collecting traces, metrics, and logs.

---

# Logs vs Traces vs Metrics

These three are different but connected.

## Logs

Logs tell what happened.

Example:

```text
Order created successfully
Payment gateway timeout
```

## Traces

Traces tell where the request went and how much time each step took.

Example:

```text
Order Service -> Payment Service -> DB
```

## Metrics

Metrics tell numbers over time.

Example:

```text
Error rate = 2%
P95 latency = 450 ms
Payment timeout count = 20
```

A good production system uses all three.

---

# Why Correlation ID Is Important In Production

It helps in:

```text
Debugging production issues
Finding failed request logs
Tracking slow APIs
Connecting frontend issue with backend logs
Finding downstream failure
Auditing important flows
Customer support investigation
Root cause analysis
```

Without it, we waste a lot of time searching logs manually.

---

# Important Best Practices

Use these in real systems:

```text
Generate correlation ID at system entry point
Accept correlation ID from trusted upstream systems
Pass it to all downstream services
Add it in every log line using MDC
Add it in response header
Pass it in Kafka or queue message headers
Clear MDC after request completes
Do not put sensitive data in correlation ID
Use distributed tracing for full request path
Keep naming consistent across services
```

---

# Common Mistakes

## Mistake 1: Generating New ID In Every Service

This breaks the chain.

Bad:

```text
Gateway correlationId = A
Order Service correlationId = B
Payment Service correlationId = C
```

Good:

```text
Gateway correlationId = A
Order Service correlationId = A
Payment Service correlationId = A
```

---

## Mistake 2: Not Passing ID To Downstream Calls

If Service A logs correlation ID but does not send it to Service B, tracing breaks.

---

## Mistake 3: Not Clearing MDC

MDC uses thread-local storage.

If we do not clear it, another request may accidentally reuse old data.

Always clear it in `finally`.

---

## Mistake 4: Logging Sensitive Data

Correlation ID should not contain:

```text
mobile number
email
account number
card number
user password
token
```

Use random UUID or safe generated ID.

---

## Mistake 5: Ignoring Async Flows

Many teams add correlation ID for REST calls but forget Kafka or RabbitMQ.

That creates gaps in tracing.

---

# Example In Real Backend Issue

Suppose user says:

```text
My payment failed but money was deducted.
```

Support team asks for correlation ID.

Then backend team searches logs:

```text
correlationId=pay-abc-123
```

They can see:

```text
Payment initiated
Gateway request sent
Gateway response timeout
Payment marked PENDING
Webhook received after 20 seconds
Payment marked SUCCESS
Order marked PAID
Notification failed
```

Now the team knows payment was successful, but notification failed.

This saves a lot of debugging time.

---

# Interview-Ready Paragraph Answer

Correlation ID is a unique ID used to track one request across multiple services. When a request enters the system, we either read the correlation ID from the header or generate a new one. Then we put it in logs using MDC and pass the same ID to all downstream REST calls, Kafka messages, and response headers. This helps us find all logs related to one user request. Traceability goes one step further. It shows the full path of the request across services and also shows timing for each step. For that, we can use tools like OpenTelemetry, Jaeger, Zipkin, or any APM tool. In production, this is very useful for debugging failures, slow APIs, timeout issues, payment issues, and customer complaints. The key point is that we should use the same ID across the full request flow, avoid sensitive data in it, pass it to async messages also, and clear MDC after the request is completed.

---

# 9. API Gateway usage

---
## Summary

An API Gateway is the single entry point for clients.

It sits in front of backend services and handles common cross-cutting work like:

```text
Authentication
Routing
Rate limiting
Logging
CORS
SSL termination
Request/response transformation
Monitoring
```

## One-Line Answer

**API Gateway is used as a front door for APIs. It routes client requests to the correct backend service and handles common concerns like security, rate limiting, logging, and monitoring.**

---

# Simple Meaning

In microservices, we may have many backend services.

Example:

```text
User Service
Order Service
Payment Service
Inventory Service
Notification Service
```

Without API Gateway, the frontend needs to know all service URLs.

```text
Frontend -> User Service
Frontend -> Order Service
Frontend -> Payment Service
Frontend -> Inventory Service
```

This becomes hard to manage.

With API Gateway:

```text
Frontend -> API Gateway -> Backend Services
```

The frontend calls only one entry point.

Example:

```http
https://api.example.com
```

Then the gateway decides where to send the request.

---

# Example Flow

Suppose frontend calls:

```http
GET /api/v1/orders/101
```

API Gateway receives it.

Then it routes the request to:

```text
Order Service
```

Another request:

```http
POST /api/v1/payments
```

Gateway sends it to:

```text
Payment Service
```

So gateway works like a traffic controller.

---

# Why API Gateway Is Used

## 1. Routing

API Gateway routes requests to the correct backend service.

Example:

```text
/api/users/**      -> User Service
/api/orders/**     -> Order Service
/api/payments/**   -> Payment Service
/api/products/**   -> Product Service
```

This hides internal service details from the client.

---

## 2. Authentication And Authorization

Gateway can check JWT token or access token before sending request to backend.

Example:

```text
Client sends token
Gateway validates token
If token is valid, request goes to service
If token is invalid, return 401
```

This avoids repeating token validation logic in every service.

But important point:

```text
Critical authorization checks should still be done in service also.
```

For example, gateway can check if the user is logged in.

But Order Service should check if the user owns that order.

---

## 3. Rate Limiting

API Gateway can stop too many requests from the same user or IP.

Example:

```text
Only 10 OTP requests allowed per minute
Only 100 API calls allowed per user per minute
```

If limit is crossed, return:

```http
429 Too Many Requests
```

This protects backend services from abuse.

---

## 4. Load Balancing

Gateway can send traffic to multiple instances of the same service.

Example:

```text
Order Service instance 1
Order Service instance 2
Order Service instance 3
```

Gateway distributes requests between them.

This helps with scaling.

---

## 5. SSL Termination

Gateway can handle HTTPS.

Example:

```text
Client -> HTTPS -> API Gateway
API Gateway -> HTTP/HTTPS -> Internal services
```

This makes certificate management easier.

---

## 6. CORS Handling

Frontend apps often need CORS configuration.

Instead of adding CORS logic in every service, gateway can handle it centrally.

Example:

```text
Allow requests from https://app.example.com
Block unknown origins
```

---

## 7. Logging And Monitoring

Gateway can log all incoming requests.

Useful fields:

```text
requestId
correlationId
path
method
status code
latency
client IP
userId
service name
```

This helps in production debugging.

Example:

```text
Which API is slow?
Which client is calling too much?
Which service is failing?
```

---

## 8. Request And Response Transformation

Gateway can modify request or response when needed.

Example:

```text
Add headers
Remove internal headers
Convert old API path to new API path
Change response format slightly
```

But we should not put heavy business logic in gateway.

Gateway should stay lightweight.

---

## 9. API Version Routing

Gateway can route different API versions.

Example:

```text
/api/v1/orders -> Order Service v1
/api/v2/orders -> Order Service v2
```

This helps during migration.

---

## 10. Canary Release Or Blue-Green Deployment

Gateway can split traffic between old and new versions.

Example:

```text
95% traffic -> old version
5% traffic  -> new version
```

This is useful when releasing new features safely.

If new version has issues, traffic can be moved back.

---

# API Gateway In Microservices

Without gateway:

```text
Client needs to know every service
Security logic repeated everywhere
CORS repeated everywhere
Rate limiting repeated everywhere
Monitoring is harder
```

With gateway:

```text
Client calls one endpoint
Common logic is centralized
Internal services are hidden
Traffic is controlled better
```

---

# Common API Gateway Tools

Common tools are:

```text
Kong
NGINX
Spring Cloud Gateway
AWS API Gateway
Azure API Management
Apigee
Traefik
Envoy
```

For Java and Spring Boot projects, **Spring Cloud Gateway** is commonly used.

---

# Simple Spring Cloud Gateway Example

Example route configuration:

```yaml
spring:
  cloud:
    gateway:
      routes:
        - id: order-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/v1/orders/**

        - id: payment-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/v1/payments/**
```

Meaning:

```text
Requests starting with /api/v1/orders go to Order Service.
Requests starting with /api/v1/payments go to Payment Service.
```

---

# API Gateway vs Load Balancer

These two are related, but not same.

| Point       | API Gateway                       | Load Balancer              |
| ----------- | --------------------------------- | -------------------------- |
| Main job    | API-level routing and policies    | Distribute traffic         |
| Works with  | Paths, headers, auth, rate limits | Servers/instances          |
| Example     | `/orders` to Order Service        | Request to instance 1 or 2 |
| Logic level | Higher level                      | Lower level                |

Simple meaning:

```text
Load balancer decides which instance should handle traffic.
API Gateway decides which service and rules should handle API request.
```

Many API gateways also include load balancing.

---

# API Gateway vs Service Mesh

API Gateway handles traffic from outside to inside.

```text
Client -> API Gateway -> Services
```

Service mesh handles service-to-service communication inside the system.

```text
Order Service -> Payment Service
Payment Service -> Inventory Service
```

Simple difference:

```text
API Gateway = north-south traffic
Service Mesh = east-west traffic
```

In simple words:

```text
Gateway protects and manages external API traffic.
Service mesh manages internal service communication.
```

---

# What Should Not Be Put In API Gateway?

This is very important in interviews.

Do not put core business logic in API Gateway.

Bad examples:

```text
Calculate order price
Decide loan eligibility
Process payment success
Apply business discount rules
Update inventory
```

These should stay inside backend services.

Gateway should handle common platform-level concerns.

Good gateway logic:

```text
Authentication
Routing
Rate limiting
CORS
Logging
Header validation
Request size limit
Basic transformation
```

---

# API Gateway Failure Risk

API Gateway is a critical component.

If it goes down, clients may not reach backend services.

So we should run gateway with high availability.

Use:

```text
Multiple gateway instances
Load balancer in front of gateway
Health checks
Autoscaling
Monitoring
Alerts
Timeouts
Circuit breakers
```

API Gateway should not become a single point of failure.

---

# Common Status Codes From Gateway

| Situation                   | Status Code             |
| --------------------------- | ----------------------- |
| Missing or invalid token    | 401 Unauthorized        |
| Valid user but not allowed  | 403 Forbidden           |
| Rate limit crossed          | 429 Too Many Requests   |
| Backend service unavailable | 503 Service Unavailable |
| Backend timeout             | 504 Gateway Timeout     |
| Invalid route               | 404 Not Found           |
| Request too large           | 413 Payload Too Large   |

---

# Practical Example

Suppose we have an e-commerce app.

Frontend calls:

```text
/api/v1/products
/api/v1/orders
/api/v1/payments
/api/v1/users
```

The API Gateway handles:

```text
JWT validation
Rate limiting
CORS
Request logging
Correlation ID
Routing
Timeouts
Fallback for non-critical APIs
```

Then it routes traffic:

```text
/api/v1/products -> Product Service
/api/v1/orders   -> Order Service
/api/v1/payments -> Payment Service
/api/v1/users    -> User Service
```

This keeps frontend simple.

It also keeps backend services protected.

---

# Best Practices

```text
Keep gateway lightweight
Do not add heavy business logic
Use authentication and rate limiting
Pass correlation ID to all services
Set timeout for backend calls
Use circuit breaker where needed
Log request and response metadata
Mask sensitive data in logs
Run multiple gateway instances
Monitor latency, errors, and traffic
Document routes clearly
```

---

# Common Interview Mistakes

## Mistake 1: Saying Gateway Is Only For Routing

Routing is one part.

Gateway also handles security, rate limits, logging, monitoring, CORS, and traffic control.

---

## Mistake 2: Putting Business Logic In Gateway

This makes gateway heavy and hard to maintain.

Business logic should stay in domain services.

---

## Mistake 3: No High Availability

If only one gateway instance is running, it can become a single point of failure.

---

## Mistake 4: Not Passing Headers

Gateway should pass important headers like:

```text
Authorization
X-Correlation-ID
X-Request-ID
User context headers if needed
```

Without this, tracing and security can break.

---

# Interview-Ready Paragraph Answer

API Gateway is used as the single entry point for client requests in a microservices system. Instead of the frontend calling many services directly, it calls the gateway, and the gateway routes the request to the correct backend service. Apart from routing, it handles common concerns like authentication, rate limiting, CORS, SSL termination, request logging, correlation ID propagation, monitoring, and sometimes request or response transformation. It can also help in API version routing, canary release, and traffic control. But I would not put core business logic inside the gateway. Business logic should stay inside the actual service. Gateway should stay lightweight and should mainly handle cross-cutting concerns. Since gateway is a critical component, I would run multiple instances, add health checks, timeouts, monitoring, and alerts so it does not become a single point of failure.

---

# 10. Authentication and authorization

---
## Summary

Authentication and authorization are two different security checks.

**Authentication means checking who the user is.**
**Authorization means checking what the user is allowed to do.**

Both are needed in backend APIs.

## One-Line Answer

**Authentication verifies the user identity, and authorization verifies the user permissions.**

---

# Simple Meaning

## Authentication

Authentication answers this question:

```text
Who are you?
```

Example:

```text
User logs in with username and password.
System verifies the user.
System returns a token.
```

So authentication is about identity.

---

## Authorization

Authorization answers this question:

```text
What are you allowed to access?
```

Example:

```text
User is logged in.
But user is not admin.
So user cannot access admin APIs.
```

So authorization is about permission.

---

# Simple Example

Suppose we have an online banking app.

A user logs in.

That is authentication.

```text
Mobile number + OTP verified
User is authenticated
```

Now the user tries to view an account.

The backend checks:

```text
Does this account belong to this user?
Does this user have permission to view this account?
```

That is authorization.

---

# Easy Real-Life Example

Think about an office.

At the gate, security checks your ID card.

That is authentication.

After entering the office, you may not be allowed to enter every room.

For example:

```text
Employee can enter work area
Manager can enter meeting room
Only finance team can enter finance room
```

That is authorization.

---

# Main Difference

| Point          | Authentication          | Authorization        |
| -------------- | ----------------------- | -------------------- |
| Meaning        | Checks identity         | Checks permission    |
| Question       | Who are you?            | What can you access? |
| Happens        | First                   | After authentication |
| Example        | Login with password/OTP | Check admin role     |
| Failure status | 401 Unauthorized        | 403 Forbidden        |

---

# Common Authentication Methods

## 1. Username And Password

User enters username and password.

Backend verifies the password.

Important point:

```text
Never store plain passwords.
```

Store password as a hash using strong algorithms like BCrypt.

---

## 2. OTP-Based Login

User enters mobile number or email.

System sends OTP.

User enters OTP.

Backend verifies OTP.

Common in banking and fintech apps.

---

## 3. JWT Token

JWT means JSON Web Token.

After login, backend returns a token.

Client sends this token in every request.

Example:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIs...
```

Backend verifies this token.

If token is valid, user is authenticated.

---

## 4. OAuth2 / OpenID Connect

This is used when login is handled by another provider.

Example:

```text
Login with Google
Login with GitHub
Login with Azure AD
```

OAuth2 is mainly for authorization delegation.
OpenID Connect is used for authentication on top of OAuth2.

---

# Common Authorization Methods

## 1. Role-Based Access Control

This is called RBAC.

Access is based on roles.

Example:

```text
ADMIN
MANAGER
CUSTOMER
SUPPORT_USER
```

Example:

```text
ADMIN can create users
CUSTOMER can view own orders
SUPPORT_USER can view tickets
```

Spring example:

```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/admin/users")
public List<UserResponse> getUsers() {
    return userService.getUsers();
}
```

---

## 2. Permission-Based Access Control

This is more fine-grained than roles.

Instead of checking only role, we check exact permission.

Example:

```text
account.view
account.create
payment.refund
user.delete
```

Example:

```java
@PreAuthorize("hasAuthority('payment.refund')")
@PostMapping("/payments/{paymentId}/refund")
public RefundResponse refundPayment(@PathVariable String paymentId) {
    return paymentService.refund(paymentId);
}
```

This is useful in enterprise systems.

---

## 3. Attribute-Based Access Control

This is called ABAC.

Access is based on user attributes, resource attributes, or context.

Example:

```text
User can view account only if account belongs to user.
User can approve loan only if amount is below their approval limit.
User can access data only from their branch.
```

This is common in banking, insurance, and internal tools.

---

# Important Status Codes

## 401 Unauthorized

Use when authentication fails.

Example:

```text
Token missing
Token invalid
Token expired
Login failed
```

Return:

```http
401 Unauthorized
```

Simple meaning:

```text
Please login first.
```

---

## 403 Forbidden

Use when authentication is done, but user has no permission.

Example:

```text
User is logged in.
But user is not admin.
User tries to access admin API.
```

Return:

```http
403 Forbidden
```

Simple meaning:

```text
I know who you are, but you cannot access this.
```

---

# Simple Flow In Backend API

```text
1. Client sends request with token
2. Backend reads token
3. Backend validates token
4. Backend extracts user details
5. Backend checks role or permission
6. If allowed, process request
7. If not allowed, return 403
```

Example:

```http
GET /api/v1/accounts/123
Authorization: Bearer token
```

Backend checks:

```text
Is token valid?
Which user is this?
Does this account belong to this user?
Does user have account.view permission?
```

Only then it returns account data.

---

# JWT Authentication Flow

Simple JWT flow:

```text
1. User logs in
2. Backend validates username/password or OTP
3. Backend creates access token
4. Client stores token safely
5. Client sends token in Authorization header
6. Backend validates token on each request
7. Backend allows or blocks request
```

Example response after login:

```json
{
  "accessToken": "jwt-token-here",
  "tokenType": "Bearer",
  "expiresIn": 900
}
```

---

# Access Token And Refresh Token

Usually we use two tokens.

## Access Token

Used to access APIs.

It should be short-lived.

Example:

```text
15 minutes
30 minutes
```

## Refresh Token

Used to get a new access token.

It should be longer-lived.

Example:

```text
7 days
30 days
```

Important point:

```text
Access token should be short-lived.
Refresh token should be stored safely.
```

---

# Why Access Token Should Be Short-Lived

If someone steals an access token, they can call APIs.

So we keep it short-lived.

If it expires quickly, damage is limited.

For high-security systems, also use:

```text
Refresh token rotation
Device binding
IP/device checks
Logout token invalidation
```

---

# Where To Store Token

For web apps, token storage is important.

Common options:

```text
HttpOnly secure cookie
Browser memory
Local storage
Session storage
```

For sensitive apps, HttpOnly secure cookies are usually safer against XSS stealing.

But cookie-based auth needs CSRF protection.

For mobile apps, tokens are usually stored in secure storage.

Example:

```text
Android Keystore
iOS Keychain
```

---

# Password Safety

If the app uses password login, follow these rules:

```text
Never store plain password
Hash password using BCrypt, Argon2, or PBKDF2
Use salt
Add rate limiting on login
Lock or slow down repeated failed login attempts
Do not reveal whether username or password is wrong
```

Bad response:

```text
Password is wrong
```

Better response:

```text
Invalid username or password
```

This avoids giving hints to attackers.

---

# Authorization Should Also Be Checked In Service Layer

Do not depend only on frontend.

Frontend can hide a button.

But that is not security.

Bad:

```text
Hide "Delete User" button on UI
Assume user cannot delete
```

A user can still call API directly.

Backend must check permission.

Also, do not rely only on API Gateway for deep business checks.

Gateway can check token and high-level roles.

But service should check resource-level permission.

Example:

```text
Gateway checks user is logged in.
Order Service checks order belongs to that user.
```

---

# Example: Resource-Level Authorization

Suppose user calls:

```http
GET /api/v1/orders/ORD123
```

Backend should check:

```text
Is this user allowed to see ORD123?
```

A logged-in user should not access another user’s order by changing ID in URL.

Bad:

```http
GET /api/v1/orders/ORD999
```

If `ORD999` belongs to another user, return:

```http
403 Forbidden
```

Some systems return `404 Not Found` to avoid revealing that the resource exists.

That depends on security policy.

---

# Spring Security Example

## Security Config Example

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
                .requestMatchers("/api/v1/payments/**").hasAuthority("payment.view")
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
Payment APIs need payment.view permission.
All other APIs need login.
JWT token is used for authentication.
```

---

## Method-Level Authorization

```java
@PreAuthorize("hasAuthority('account.view')")
@GetMapping("/accounts/{accountId}")
public AccountResponse getAccount(@PathVariable String accountId) {
    return accountService.getAccount(accountId);
}
```

This checks permission before method execution.

But for ownership check, service logic is still needed.

```java
public AccountResponse getAccount(String accountId) {

    String loggedInUserId = securityContext.getLoggedInUserId();

    Account account = accountRepository.findById(accountId)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

    if (!account.getUserId().equals(loggedInUserId)) {
        throw new AccessDeniedException("You are not allowed to access this account");
    }

    return accountMapper.toResponse(account);
}
```

This protects user data properly.

---

# Authentication In Microservices

In microservices, authentication usually happens at the gateway or auth service.

Simple flow:

```text
Client -> API Gateway -> User Service
```

Gateway validates token.

Then it forwards user identity to backend services.

Example headers:

```text
X-User-ID
X-User-Roles
X-Correlation-ID
```

But services should trust these headers only if they come from the gateway, not directly from the internet.

Better setup:

```text
Only gateway is public.
Internal services are private.
```

---

# Authorization In Microservices

Authorization can happen at different levels.

## Gateway-Level Authorization

Good for common checks:

```text
Is user logged in?
Does user have ADMIN role?
Can user access this API group?
```

## Service-Level Authorization

Needed for business checks:

```text
Does this account belong to user?
Can this manager approve this amount?
Can this support user access this customer profile?
```

So the best design uses both.

---

# Common Security Mistakes

## Mistake 1: Confusing Authentication And Authorization

Login does not mean full access.

A user can be authenticated but still not authorized.

---

## Mistake 2: Only Checking On Frontend

Frontend checks are for user experience.

Backend checks are for security.

---

## Mistake 3: Trusting User ID From Request Body

Bad:

```json
{
  "userId": "101",
  "amount": 5000
}
```

The client can change `userId`.

Better:

```text
Get userId from token/session.
Do not trust userId from request body for sensitive actions.
```

---

## Mistake 4: Not Checking Resource Ownership

This causes serious bugs.

Example:

```text
User changes accountId in URL and sees another user's account.
```

This is broken authorization.

---

## Mistake 5: Long-Lived Access Tokens

If token is stolen, attacker gets long access.

Use short-lived access tokens and refresh tokens.

---

## Mistake 6: Logging Sensitive Data

Do not log:

```text
Password
OTP
Access token
Refresh token
Authorization header
Card details
Secret keys
```

---

## Mistake 7: Weak Password Storage

Never store plain password.

Never use weak hashing like MD5 or SHA-1 for passwords.

Use BCrypt or Argon2.

---

# Best Practices

```text
Use HTTPS everywhere
Use short-lived access tokens
Use refresh token rotation
Store passwords with strong hashing
Validate token on every request
Use RBAC or permission-based access
Check resource ownership in service layer
Never trust userId from client request
Add rate limiting on login and OTP APIs
Log security events safely
Do not log secrets or tokens
Use 401 for authentication failure
Use 403 for authorization failure
Keep internal services private
```

---

# Practical Example

Suppose we have this API:

```http
POST /api/v1/accounts/123/transfer
Authorization: Bearer token
```

Backend should check:

```text
Is token valid?
Which user is logged in?
Does account 123 belong to this user?
Is account active?
Does user have transfer permission?
Is amount within limit?
```

Only after these checks should the transfer happen.

This is safe design.

---

# Authentication vs Authorization In One Simple Example

Login API:

```http
POST /api/v1/login
```

This is authentication.

Transfer API:

```http
POST /api/v1/accounts/123/transfer
```

This needs authorization.

Because even after login, the user should transfer only from their own account and only within allowed limits.

---

# Interview-Ready Paragraph Answer

Authentication and authorization are two different parts of API security. Authentication means verifying who the user is, like login using password, OTP, JWT, or OAuth2. Authorization means checking what the authenticated user is allowed to do. For example, a user may be logged in, but still not allowed to access admin APIs or another user’s account. In backend APIs, I usually validate the token first, extract the user identity, and then check roles, permissions, and resource ownership. For authentication failure, I return 401. For authorization failure, I return 403. I never trust user ID or permissions sent from the frontend. I take user details from the token or security context. For sensitive APIs, I also check ownership in the service layer. In microservices, gateway can handle common authentication, but each service should still handle important business-level authorization. So in simple words, authentication proves identity, and authorization controls access.

---

# 11. How do services communicate securely?

---
## Summary

Services communicate securely by protecting **identity, data, and access**.

In simple words:

**A service should know who is calling, whether that caller is allowed, and the data should be protected while moving between services.**

---

## One-Line Answer

**Services communicate securely using HTTPS or mTLS, service-to-service authentication, authorization, token validation, network restrictions, secret management, and proper logging without sensitive data.**

---

# Simple Meaning

In microservices, one service often calls another service.

Example:

```text
Order Service  ->  Payment Service
Order Service  ->  Inventory Service
Payment Service -> Fraud Service
```

Now the important question is:

```text
How does Payment Service know the call is really coming from Order Service?
How does it know Order Service is allowed to call this API?
How do we protect the data while it is moving?
```

That is where secure service communication comes in.

---

# Main Things Needed For Secure Communication

## 1. Use HTTPS

The first basic rule is:

```text
Use HTTPS instead of HTTP.
```

HTTPS encrypts data while it travels over the network.

This protects data from being read in the middle.

Example:

```text
https://payment-service/api/v1/payments
```

Not:

```text
http://payment-service/api/v1/payments
```

For internal services also, HTTPS is better, especially in cloud or zero-trust systems.

---

## 2. Use mTLS For Strong Service Identity

mTLS means **mutual TLS**.

Normal TLS usually verifies only the server.

mTLS verifies both sides:

```text
Client verifies server
Server verifies client
```

Example:

```text
Order Service calls Payment Service
Payment Service checks certificate of Order Service
Order Service checks certificate of Payment Service
```

This gives strong service-to-service trust.

It answers:

```text
Is this really Order Service?
Is this really Payment Service?
```

mTLS is common in service mesh systems like Istio, Linkerd, or Envoy-based platforms.

---

# 3. Use Service-To-Service Authentication

Every service call should have some identity.

Common options:

```text
JWT token
OAuth2 client credentials
mTLS certificate
Signed request
API key for simple internal cases
```

For serious systems, API keys alone are usually not enough.

Better options are:

```text
mTLS
OAuth2 client credentials
Short-lived JWT tokens
```

---

# 4. Use Authorization Also

Authentication only checks identity.

Authorization checks permission.

Example:

```text
Order Service is authenticated.
But is Order Service allowed to call refund API?
```

Payment Service should not allow every service to call every API.

Example:

```text
Order Service can initiate payment.
Refund Service can create refund.
Notification Service cannot create refund.
```

So each service should check:

```text
Who is calling?
What is the caller allowed to do?
```

---

# 5. Do Not Trust Internal Network Blindly

A common old mistake is:

```text
It is inside our network, so it is safe.
```

That is not a good assumption anymore.

Even internal services should be protected.

Why?

Because if one service is compromised, attackers may try to call other services from inside the network.

Better approach:

```text
Trust nothing by default.
Verify every call.
```

This is called zero-trust style security.

---

# 6. Keep Services Private

Internal services should not be directly exposed to the public internet.

Good setup:

```text
Client
  ↓
API Gateway
  ↓
Internal Services
```

Only API Gateway is public.

Backend services should stay private inside:

```text
Private subnet
VPC
Kubernetes cluster
Internal network
```

This reduces attack surface.

---

# 7. Use API Gateway For External Traffic

For outside client traffic, API Gateway can handle:

```text
Authentication
Rate limiting
Request validation
CORS
SSL termination
Logging
Routing
Threat protection
```

But internal services should still do important authorization checks.

Example:

```text
Gateway checks token is valid.
Order Service checks user owns the order.
Payment Service checks caller can initiate payment.
```

---

# 8. Pass User Context Safely

When one service calls another service, it may need user details.

Example:

```text
Order Service calls Payment Service for user U101.
```

Do not blindly pass userId from request body.

Better:

```text
Extract user identity from verified token.
Pass trusted user context to downstream service.
```

Common headers:

```text
Authorization: Bearer <token>
X-User-ID: U101
X-Correlation-ID: abc-123
```

But be careful.

A downstream service should trust `X-User-ID` only if it comes from a trusted gateway or trusted service.

---

# 9. Use Short-Lived Tokens

For service-to-service calls, tokens should not live forever.

Use short-lived tokens.

Example:

```text
Access token valid for 5 minutes or 15 minutes
```

If a token is leaked, short expiry reduces the damage.

For machine-to-machine communication, OAuth2 client credentials flow is commonly used.

Simple flow:

```text
Service A gets token from Auth Server
Service A calls Service B with token
Service B validates token
Service B checks permissions
```

---

# 10. Protect Secrets Properly

Services need secrets like:

```text
Database password
API secret
JWT signing key
Payment gateway secret
Client secret
Certificate private key
```

Do not store secrets in code.

Bad:

```java
String password = "my-db-password";
```

Better:

```text
Use Vault, AWS Secrets Manager, Azure Key Vault, GCP Secret Manager, or Kubernetes secrets with proper controls.
```

Also rotate secrets regularly.

---

# 11. Validate Input Even From Internal Services

Do not assume internal service input is always valid.

Every service should validate incoming request data.

Example:

```text
Required fields
Amount format
Allowed enum values
Request size
Date format
Business rules
```

This prevents bad data from spreading across services.

---

# 12. Use Rate Limiting And Throttling

Rate limiting is not only for public APIs.

Internal rate limiting can also protect services.

Example:

```text
Order Service should not overload Payment Service.
Notification Service should not overload Email Provider.
```

If too many calls come, return:

```http
429 Too Many Requests
```

or use queue-based async processing.

---

# 13. Use Network Policies

At infrastructure level, restrict which service can talk to which service.

Example:

```text
Order Service can call Payment Service.
Notification Service cannot call Payment Service refund API.
```

In Kubernetes, this can be done using Network Policies.

In cloud, this can be done using security groups, private subnets, firewall rules, and service mesh policies.

This is important because security should not depend only on application code.

---

# 14. Use Secure Messaging For Async Communication

For Kafka, RabbitMQ, SQS, or other message brokers, secure communication is also needed.

Important points:

```text
Use TLS for broker communication
Use authentication for producers and consumers
Use ACLs on topics or queues
Use separate permissions for read and write
Do not put sensitive data in events unless needed
Encrypt sensitive payloads if required
```

Example:

```text
Order Service can publish ORDER_CREATED.
Inventory Service can consume ORDER_CREATED.
Random service should not read payment events.
```

---

# 15. Protect Sensitive Data In Logs

This is very important.

Do not log:

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

Safe logs should contain:

```text
correlationId
requestId
service name
status code
latency
masked user ID if needed
masked account number if needed
```

Bad log:

```text
Authorization=Bearer eyJhbGciOi...
```

Better log:

```text
Authorization=MASKED
```

---

# 16. Use Correlation ID For Traceability

Secure communication is not only about encryption.

We also need traceability.

Every request should have a correlation ID.

Example:

```text
X-Correlation-ID: abc-123
```

Pass it across all services.

This helps debug issues safely.

Example:

```text
Order Service logs correlationId=abc-123
Payment Service logs correlationId=abc-123
Inventory Service logs correlationId=abc-123
```

Now we can track the full request without logging sensitive data.

---

# 17. Handle Timeouts And Retries Safely

Secure communication should also be reliable.

Use:

```text
Timeouts
Retries with backoff
Circuit breaker
Fallback where safe
Idempotency for write operations
```

For example, payment calls should not be retried blindly.

Use idempotency key.

```text
Same payment request repeated should not charge twice.
```

---

# REST Communication Security

For REST-based service calls, use:

```text
HTTPS or mTLS
JWT or OAuth2 token
Authorization checks
Timeouts
Rate limits
Input validation
Correlation ID
Sensitive data masking
```

Example:

```http
POST /api/v1/payments
Authorization: Bearer <token>
X-Correlation-ID: abc-123
Idempotency-Key: payment-req-123
```

---

# Async Messaging Security

For event-based communication, use:

```text
TLS with broker
Producer authentication
Consumer authentication
Topic-level ACLs
Message schema validation
Correlation ID in headers
Idempotent consumers
Dead letter queue
Sensitive data encryption if needed
```

Example:

```text
Payment Service publishes PAYMENT_SUCCESS
Only Order Service and Notification Service can consume it
Other services cannot read this topic
```

---

# Common Mistakes

## Mistake 1: Thinking Internal Calls Are Always Safe

Internal calls also need security.

A compromised internal service can attack other services.

---

## Mistake 2: Using Only API Keys Forever

API keys are simple, but they can be leaked.

If used, they should be rotated and scoped.

For stronger security, use mTLS or short-lived tokens.

---

## Mistake 3: No Authorization Between Services

Authentication is not enough.

A service may be real, but still not allowed to call a specific API.

---

## Mistake 4: Logging Tokens And Secrets

This is a serious issue.

Logs are often accessed by many people and tools.

Never log secrets.

---

## Mistake 5: Not Securing Kafka Or Message Queues

Async communication also needs authentication, authorization, TLS, and topic permissions.

---

## Mistake 6: No Timeout

A secure call can still break the system if it waits forever.

Always set timeouts.

---

# Best Practices

```text
Use HTTPS everywhere
Use mTLS for strong service identity
Use OAuth2/JWT for service authentication
Use authorization for service permissions
Keep internal services private
Use API Gateway for external traffic
Use short-lived tokens
Store secrets in secret manager
Rotate secrets regularly
Validate all inputs
Use network policies
Secure Kafka/RabbitMQ topics and queues
Pass correlation ID
Mask sensitive data in logs
Use timeout, retry, and circuit breaker
Monitor failed auth and suspicious traffic
```

---

# Practical Example

Suppose `Order Service` calls `Payment Service`.

A safe flow would be:

```text
1. Order Service gets a short-lived service token.
2. Order Service calls Payment Service over HTTPS or mTLS.
3. It sends Authorization header and correlation ID.
4. Payment Service validates the token or certificate.
5. Payment Service checks if Order Service can initiate payment.
6. Payment Service validates request data.
7. Payment Service processes the request.
8. Logs are created with correlation ID, but without sensitive data.
```

Example headers:

```http
POST /api/v1/payments
Authorization: Bearer <service-token>
X-Correlation-ID: abc-123
Idempotency-Key: payment-456
```

This is much safer than just allowing any internal service to call Payment Service.

---

# Interview-Ready Paragraph Answer

Services communicate securely by using encrypted communication, strong identity, and proper access control. For REST calls, I would use HTTPS or mTLS so data is protected in transit and services can verify each other. I would also use service-to-service authentication like OAuth2 client credentials, JWT, or certificates, and then check authorization so only allowed services can call specific APIs. Internal services should not be directly exposed to the internet. They should stay in private networks behind an API Gateway or internal load balancer. I would also protect secrets using a secret manager, rotate them, validate all input, and avoid logging sensitive data like tokens, passwords, OTPs, or card details. For async communication like Kafka or RabbitMQ, I would use TLS, authentication, topic-level ACLs, and idempotent consumers. I would also pass correlation IDs for traceability and use timeouts, retries, and circuit breakers for reliability. In simple words, secure service communication means every service call should be encrypted, authenticated, authorized, traceable, and safe from leaking sensitive data.

---

# 12. What happens when downstream service is slow?

---
## Summary

When a downstream service is slow, the caller service also becomes slow.

If we do not handle it properly, one slow dependency can make the whole system slow or even down.

## One-Line Answer

**When a downstream service is slow, threads and connections in the caller service can get blocked, response time increases, users wait longer, and the system can face timeout, retry storm, or cascading failure.**

---

# Simple Meaning

Suppose `Order Service` calls `Payment Service`.

```text
Order Service  --->  Payment Service
```

If `Payment Service` is slow, then `Order Service` also waits.

Now the user is also waiting.

```text
User waits
Order Service waits
Payment Service is slow
```

If this happens for many requests, the problem becomes bigger.

---

# What Problems Can Happen?

## 1. User Response Becomes Slow

If downstream service takes 10 seconds, then our API may also take 10 seconds.

Example:

```text
Frontend calls Order Service
Order Service calls Inventory Service
Inventory Service is slow
Order API becomes slow
```

So the user sees loading for a long time.

---

## 2. Threads Get Blocked

In a normal Spring Boot app, each request uses a thread.

If many requests are waiting for a slow downstream service, many threads get blocked.

Example:

```text
100 requests come
All 100 are waiting for Payment Service
All 100 threads are blocked
```

Now new requests may not get threads.

The whole service becomes slow.

---

## 3. Connection Pool Gets Exhausted

Services usually use HTTP connection pools or DB connection pools.

If downstream calls are slow, connections remain busy for longer.

Then new requests may not get a connection.

Example:

```text
Connection pool size = 50
50 calls are stuck waiting
New calls cannot get connection
```

This creates more failures.

---

## 4. Timeout Can Happen

If downstream service does not respond in time, the caller may get timeout.

Example:

```text
Order Service waits 3 seconds
Payment Service does not respond
Order Service gets timeout
```

Then we may return:

```http
504 Gateway Timeout
```

or

```http
503 Service Unavailable
```

depending on the case.

---

## 5. Retry Storm Can Happen

If many callers start retrying at the same time, the slow downstream service gets even more load.

Example:

```text
1000 requests fail
Each request retries 3 times
Downstream now gets 3000 extra calls
```

This can make the slow service completely down.

That is called a retry storm.

---

## 6. Cascading Failure Can Happen

This is the biggest risk.

One slow service can make other services slow.

Example:

```text
Payment Service is slow
Order Service waits
Checkout API becomes slow
API Gateway waits
Frontend times out
Users retry
More traffic comes
System becomes unstable
```

This is called cascading failure.

One dependency issue spreads to other services.

---

# How To Handle A Slow Downstream Service

## 1. Set Proper Timeouts

Never wait forever.

Every external call should have timeout.

Example:

```text
Connection timeout = 1 second
Read timeout = 3 seconds
Total timeout = 4 seconds
```

Timeout protects the caller service.

It says:

```text
I will wait only for a fixed time.
After that, I will stop waiting.
```

---

## 2. Use Circuit Breaker

If downstream service is failing or slow again and again, circuit breaker stops calling it for some time.

```text
Order Service -X-> Payment Service
```

Instead of waiting again and again, we fail fast.

This protects our service from blocked threads.

Simple states:

```text
CLOSED     -> calls allowed
OPEN       -> calls blocked
HALF_OPEN  -> few test calls allowed
```

---

## 3. Use Fallback Where Safe

Fallback means returning a safe backup response.

Example:

If Recommendation Service is slow:

```json
{
  "recommendations": []
}
```

If Rating Service is slow:

```json
{
  "rating": null,
  "ratingStatus": "UNAVAILABLE"
}
```

But fallback should be safe.

Never do this:

```text
Payment service is slow, so mark payment success
```

That is dangerous.

For payment, better fallback is:

```text
Payment status is pending.
We are verifying it.
```

---

## 4. Use Bulkhead Pattern

Bulkhead means isolating resources.

Do not let one slow dependency consume all threads.

Example:

```text
Payment calls get separate thread pool
Notification calls get separate thread pool
Inventory calls get separate thread pool
```

If Notification Service is slow, it should not block Payment flow.

This protects the main system.

---

## 5. Retry Carefully

Retry only temporary failures.

Do not retry blindly.

Good retry design:

```text
Small retry count
Backoff
Jitter
Timeout
Circuit breaker
Idempotency for write APIs
```

Bad retry:

```text
Retry forever
Retry immediately
Retry every error
```

For payment or order creation, retry only if idempotency is handled.

---

## 6. Use Async Processing For Non-Critical Work

If the downstream work is not needed immediately, do it async.

Example:

Do not block order placement for email.

Bad:

```text
Order Service waits for Email Service
```

Better:

```text
Order Service publishes ORDER_CREATED event
Email Service sends email later
```

This improves user response time.

---

## 7. Return Partial Response If Possible

Sometimes one part of the response is not critical.

Example product page:

```text
Product details are important
Ratings are optional
Recommendations are optional
```

If Recommendation Service is slow, still return product details.

```json
{
  "productId": "P101",
  "name": "Laptop",
  "price": 50000,
  "recommendations": [],
  "recommendationStatus": "UNAVAILABLE"
}
```

This gives a better user experience.

---

## 8. Monitor And Alert

We should track downstream performance.

Important metrics:

```text
Latency
Timeout count
Error rate
Retry count
Circuit breaker open count
Connection pool usage
Thread pool usage
P95 and P99 response time
```

If downstream latency increases, alerts should be triggered.

This helps the team react early.

---

# Example Scenario

Suppose `Order Service` depends on:

```text
Payment Service
Inventory Service
Notification Service
```

If `Notification Service` is slow, we should not fail order placement.

Better design:

```text
Place order
Publish notification event
Return success to user
Notification happens later
```

But if `Payment Service` is slow, we cannot simply say payment success.

Better design:

```text
Mark payment as PENDING
Wait for webhook
Check gateway status
Run reconciliation job
```

So the handling depends on business importance.

---

# Good Status Codes

| Situation                             | Status Code               |
| ------------------------------------- | ------------------------- |
| Downstream timeout                    | 504 Gateway Timeout       |
| Downstream unavailable                | 503 Service Unavailable   |
| Request accepted for later processing | 202 Accepted              |
| Partial response returned safely      | 200 OK                    |
| Too many requests due to throttling   | 429 Too Many Requests     |
| Unknown internal issue                | 500 Internal Server Error |

---

# Simple Spring Boot Example

Using Resilience4j:

```java
@Service
public class ProductService {

    private final RatingClient ratingClient;

    public ProductService(RatingClient ratingClient) {
        this.ratingClient = ratingClient;
    }

    @CircuitBreaker(name = "ratingService", fallbackMethod = "ratingFallback")
    @TimeLimiter(name = "ratingService")
    public CompletableFuture<RatingResponse> getRating(String productId) {
        return CompletableFuture.supplyAsync(() ->
                ratingClient.getRating(productId)
        );
    }

    public CompletableFuture<RatingResponse> ratingFallback(
            String productId,
            Throwable ex
    ) {
        RatingResponse response = new RatingResponse();
        response.setProductId(productId);
        response.setRating(null);
        response.setStatus("UNAVAILABLE");

        return CompletableFuture.completedFuture(response);
    }
}
```

Example config:

```yaml
resilience4j:
  circuitbreaker:
    instances:
      ratingService:
        sliding-window-size: 20
        minimum-number-of-calls: 10
        failure-rate-threshold: 50
        wait-duration-in-open-state: 30s

  timelimiter:
    instances:
      ratingService:
        timeout-duration: 2s
```

Simple meaning:

```text
If Rating Service is slow or failing too much, stop calling it for some time.
If it takes more than 2 seconds, timeout.
Return fallback rating response.
```

---

# Common Mistakes

## Mistake 1: No Timeout

Without timeout, caller service may wait forever.

This can block threads and bring the system down.

---

## Mistake 2: Retrying Too Much

Too many retries increase load on the slow service.

This can create a retry storm.

---

## Mistake 3: Same Thread Pool For Everything

If all downstream calls use the same thread pool, one slow dependency can affect all APIs.

Use isolation where needed.

---

## Mistake 4: Unsafe Fallback

Fallback should never return fake success.

Especially for:

```text
Payment
Refund
Fund transfer
Inventory
Authentication
Authorization
```

---

## Mistake 5: No Monitoring

If we do not monitor downstream latency, we may know about the issue only after users complain.

---

# Best Practices

```text
Set timeout for every downstream call
Use circuit breaker
Use fallback only when safe
Use retries with backoff and jitter
Avoid retrying non-idempotent operations blindly
Use bulkhead isolation
Use async messaging for non-critical work
Return partial response where possible
Monitor latency, errors, and timeouts
Add correlation ID for debugging
```

---

# Interview-Ready Paragraph Answer

When a downstream service is slow, the caller service also starts waiting. This can increase response time, block threads, exhaust connection pools, cause timeouts, and even create cascading failure across services. To handle this, I always set proper timeouts for external calls so we do not wait forever. I use circuit breaker to stop calling a dependency when it is slow or failing repeatedly. I use fallback only when it is safe, like returning cached data, empty recommendations, partial response, or pending status. I also use limited retries with backoff and jitter, and I avoid retrying write operations unless idempotency is handled. For non-critical work like email or notification, I prefer async messaging so the main user flow is not blocked. I also monitor downstream latency, timeout count, retry count, and circuit breaker state. In simple words, one slow downstream service should not be allowed to make the full system slow or unstable.

