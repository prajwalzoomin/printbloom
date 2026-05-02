# `PaymentStatus.java`

## 1. Purpose
This is a Java **Enum** (short for Enumeration). An enum is a special Java type used to define a collection of constants. 

When you want a variable to only hold one of a few specific values (and absolutely nothing else), you use an enum. This file defines the possible states of a payment transaction.

## 2. Code Walkthrough

```java
public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED
}
```

By defining this enum, we ensure that a payment's status can only ever be `PENDING`, `SUCCESS`, or `FAILED`. If a developer tries to set the status to `"Completed"` or `"Error"`, the Java compiler will immediately flag it as an error before the program even runs. This prevents typos and keeps the database clean.
