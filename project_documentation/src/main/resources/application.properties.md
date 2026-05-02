# `application.properties`

## 1. Purpose
This is the master configuration file for the entire Spring Boot backend. Instead of hard-coding passwords, URLs, and server settings into Java code, we put them here. It makes it incredibly easy to change settings (like pointing to a new database) without having to recompile the Java code.

## 2. Code Walkthrough

### Database Connection
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/printbloom_db?...
spring.datasource.username=root
spring.datasource.password=Root@1234
spring.jpa.hibernate.ddl-auto=update
```
- **`url`**: Tells Spring exactly where the MySQL server is located (`localhost` port `3306`) and which database to use (`printbloom_db`).
- **`ddl-auto=update`**: This is a magic setting. It tells Hibernate (the database manager): *"Look at my Java `@Entity` classes. If the MySQL tables don't exist yet, create them. If I added a new column to my Java class, update the MySQL table to match."*

### File Storage Limits
```properties
spring.servlet.multipart.max-file-size=25MB
spring.servlet.multipart.max-request-size=25MB
```
By default, Tomcat (the embedded web server) blocks any file upload larger than 1MB. We override that here to allow PDFs up to 25MB.

### Razorpay Keys
```properties
razorpay.key.id=rzp_test_Sk87xWppn3Zqn5
razorpay.key.secret=2A5O2BmujMxAvXzOktIOO1yS
```
These are the API keys used by the `RazorpayConfig` and `RazorpayService`. Because they start with `rzp_test_`, we know they are safe testing keys, not live production keys.
