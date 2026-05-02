# `PdfProcessingService.java`

## 1. Purpose
The `PdfProcessingService` acts as the "PDF Inspector" for PrintBloom. It uses a third-party library called **Apache PDFBox** to look inside the physical PDF files saved by the `FileStorageService`. Its main jobs are to ensure the file is actually a readable PDF and to count how many pages it contains.

## 2. Key Concepts

### Try-With-Resources
```java
try (PDDocument document = PDDocument.load(file)) {
    return document.getNumberOfPages();
} catch (IOException e) { ... }
```
When you open a file in Java (like `PDDocument.load()`), it locks that file on the operating system. If you forget to close it, no other program can delete or modify it. 

The `try (...)` syntax is a special Java feature. It automatically guarantees that the `document` will be properly closed and unlocked the millisecond the `try` block finishes, even if an error occurs.

### Logging
```java
private static final Logger logger = LoggerFactory.getLogger(PdfProcessingService.class);
```
Instead of using `System.out.println` (which only shows up in the console), this uses an official Logger. If reading a PDF fails, `logger.error(...)` will write a detailed message to an official server log file so developers can investigate later.

## 3. Code Walkthrough

1. **`countPages(Path pdfPath)`**:
   - Takes the path to the saved file.
   - Attempts to load it using Apache PDFBox.
   - Returns `document.getNumberOfPages()`.
   - If it fails (e.g., the file is corrupted), it logs an error and safely returns `0`.

2. **`validatePdf(Path pdfPath)`**:
   - Similar to `countPages`, it attempts to load the file.
   - If it successfully loads, it instantly returns `true` (valid PDF).
   - If the load fails, it catches the error, logs a warning, and returns `false`.
