# `PaymentRepository.java`

## 1. Purpose
In Spring Boot, the **Repository** layer handles all direct communication with the database. While the `Payment` model defines *what* a payment looks like, the `PaymentRepository` defines *how to find, save, and delete* payments in the MySQL database.

## 2. Key Concepts

### `@Repository`
This tells Spring Boot to treat this interface as a data access component. When the application starts, Spring Boot automatically writes the underlying SQL code for this interface in the background.

### Extending `JpaRepository`
```java
public interface PaymentRepository extends JpaRepository<Payment, Long>
```
By extending `JpaRepository`, this interface instantly inherits dozens of built-in database methods like `save()`, `findAll()`, `findById()`, and `delete()`. The `<Payment, Long>` part tells the repository: *"You are managing the `Payment` table, and its Primary Key is of type `Long`."*

### Custom Query Methods
```java
Optional<Payment> findByPrintOrderId(Long orderId);
```
This is where Spring Data shines. Notice there is no SQL here. Because the method is named using a specific pattern (`findBy` + `PrintOrderId`), Spring Boot automatically translates this method name into the SQL query:
`SELECT * FROM payments WHERE print_order_id = ?`

It returns an `Optional<Payment>` because it's possible a user hasn't paid yet, meaning no payment record exists for that order ID.
