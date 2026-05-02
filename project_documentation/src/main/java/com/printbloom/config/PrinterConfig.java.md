# `PrinterConfig.java`

## 1. Purpose
This class holds configuration settings related to the physical printer. Like other configuration classes, it acts as a bridge between your settings file (`application.properties`) and the rest of your Java code.

## 2. Key Concepts

### `@Configuration`
This annotation marks the class as a source of setup instructions for Spring Boot, ensuring it is loaded when the application starts.

### Fallback Values
Notice this specific line:
```java
@Value("${printbloom.printer.preferred-name:}")
private String preferredPrinterName;
```
The colon `:` at the end of `${...:}` means there is a default fallback. But since there is nothing after the colon, the fallback is simply an empty string `""`.

## 3. Code Walkthrough

1. **`preferredPrinterName`**: This variable stores the name of the specific physical printer you want the system to use (e.g., "HP-LaserJet-M404"). 
2. **`getPreferredPrinterName()`**: This is a getter method. Later on, the `PrinterService` will call this method. If it returns a specific name, the service will try to send the document to that exact printer. If it returns an empty string (the default), the service knows it should just use whatever printer is set as the default on the operating system.
