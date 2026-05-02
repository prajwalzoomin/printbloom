# `FileStorageConfig.java`

## 1. Purpose
This file manages the settings for file uploads within the PrintBloom application. When a user uploads a PDF, the application needs to know where to save it, what the maximum allowed size is, and what type of files are acceptable. Instead of hard-coding these values directly into the program logic, we store them in a configuration file (`application.properties`) and use this class to pull those values into the Java code.

## 2. Key Concepts

### `@Configuration` Annotation
At the top of the class, you will see `@Configuration`. This is a Spring Boot annotation that tells the framework: *"Hey, this class contains setup information. Please load it when the application starts up."*

### `@Value` Annotation
The `@Value` annotation is how we read settings from our `application.properties` file. 

Take a look at this line:
```java
@Value("${printbloom.filestorage.upload-directory:uploads}")
private String uploadDirectory;
```
Here's how to read it:
- Look in the properties file for a setting named `printbloom.filestorage.upload-directory`.
- If you find it, store its value in the `uploadDirectory` variable.
- If you **don't** find it, use `"uploads"` as the default fallback value (indicated by the `:`).

## 3. Code Walkthrough

The class defines three main pieces of configuration:

1. **`uploadDirectory`**: Where files should be saved on the hard drive. Default is a folder named `uploads`.
2. **`maximumFileSize`**: The biggest file a user can upload. The default is `26214400` bytes, which equals 25 Megabytes (MB).
3. **`allowedFileTypes`**: A restriction on the kind of files users can upload. Here, it defaults to `application/pdf`, meaning only PDF files are allowed.

Finally, the class provides **Getter methods** (e.g., `getUploadDirectory()`). These methods allow other parts of the application (like the `FileStorageService`) to securely access these settings when they need to process an upload.
