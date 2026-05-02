# `FileStorageService.java`

## 1. Purpose
When a user uploads a PDF from their browser, that file needs a safe place to live on the server's hard drive. The `FileStorageService` is the "File Manager" of the backend. It validates that the uploaded file isn't too large or the wrong format, generates a safe file name, and physically writes the file to the disk.

## 2. Key Concepts

### Dependency Injection (Config)
```java
public FileStorageService(FileStorageConfig fileStorageConfig) { ... }
```
This service relies heavily on the `FileStorageConfig` we looked at earlier. It asks for the configuration in its constructor so it knows the rules (max size, allowed types, upload directory path).

### Java `nio` (New I/O)
This service uses modern Java file handling techniques found in `java.nio.file`. Classes like `Path`, `Paths`, and `Files` make it much easier to securely create folders and copy data compared to older Java methods.

## 3. Code Walkthrough

1. **`validateFile(MultipartFile file)`**:
   - Checks if the file is empty.
   - Checks if the file size is larger than the limit allowed in the config (e.g., > 25MB).
   - Checks if the MIME type (`contentType`) matches the allowed type (e.g., `application/pdf`).
   - If any of these fail, it throws a `FileStorageException`.

2. **`generateUniqueFilename(String originalFilename)`**:
   - If two users upload a file named `resume.pdf` at the exact same time, we don't want the second one to overwrite the first.
   - This method creates a completely unique, random string (a UUID, like `123e4567-e89b-12d3...`) and attaches it to the front of the original file name. It also sanitizes the name to remove weird characters.

3. **`storeFile(MultipartFile file)`**:
   - Calls the validation method.
   - Creates the destination folder (e.g., `uploads/`) if it doesn't already exist.
   - Generates the unique file name.
   - Copies the raw byte data from the internet request directly onto the hard drive.
   - Returns the exact `Path` where the file was saved so the database can record its location.
