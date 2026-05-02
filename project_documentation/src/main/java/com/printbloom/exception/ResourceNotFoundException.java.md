# `ResourceNotFoundException.java`

## 1. Purpose
Similar to `FileStorageException`, this is a custom error class. It is specifically used when the application tries to look up data in the database, but the data doesn't exist.

For example, if a user tries to reprint order `#999`, but order `#999` was deleted or never existed, the service will throw a `ResourceNotFoundException`.

## 2. Key Concepts

### Extending `RuntimeException`
Just like the other custom exceptions, this inherits from Java's built-in `RuntimeException`. This means it can crash the current operation cleanly and be caught by the `GlobalExceptionHandler`.

## 3. Code Walkthrough

```java
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```
This class is extremely minimal. It only has one constructor that takes a `String message`. When thrown, you might write: 
`throw new ResourceNotFoundException("Print order not found with ID: 999");`

The message is passed up to the superclass (`RuntimeException`) so it can be extracted later by the global handler and shown to the user.
