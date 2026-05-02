# `PrintType.java`

## 1. Purpose
This is a simple Java **Enum** used to restrict the types of printing available in the application.

## 2. Code Walkthrough

```java
public enum PrintType {
    BLACK_WHITE,
    COLOR
}
```

By using this Enum, the application guarantees that when a user selects a print type on the frontend, it maps exactly to either `BLACK_WHITE` or `COLOR`. This is heavily used by the `CostCalculationService`, as color pages cost more than black and white ones.
