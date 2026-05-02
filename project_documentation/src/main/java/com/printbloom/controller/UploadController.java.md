# `UploadController.java`

## 1. Purpose
The `UploadController` acts as a quick "preview" or "estimator" endpoint. Unlike the `PrintOrderController` which actually saves a full order to the database, this controller simply receives a file, checks if it's a valid PDF, counts the pages, calculates how much it *would* cost to print, and returns that estimate to the user.

## 2. Key Concepts

### Service Orchestration
A common pattern in Spring Boot is to have a Controller "orchestrate" (coordinate) multiple Services to fulfill a single request. Notice how this controller relies on three different experts:
1. `FileStorageService` to save the file temporarily.
2. `PdfProcessingService` to analyze the PDF.
3. `CostCalculationService` to figure out the price.

### `ResponseEntity<?>`
The `?` is a Java wildcard. It means this method can return different types of data. If there is an error, it returns a plain text error message. If it succeeds, it returns a `Map` containing a structured JSON object.

## 3. Code Walkthrough

**`uploadDocument()`** (Endpoint: `POST /print/upload`):
1. **Validation**: Checks if a file was actually provided.
2. **Storage**: Calls `fileStorageService.storeFile(file)` to save the file to the `uploads/` folder.
3. **Verification**: Asks the `PdfProcessingService` to make sure the file is a healthy, readable PDF.
4. **Analysis**: Asks the `PdfProcessingService` to count the total number of pages.
5. **Estimation**: Hands the page count and the user's preferences (color, copies, duplex) over to the `CostCalculationService` to get a price.
6. **Response**: Bundles the original file name, page count, and estimated cost into a map and sends it back to the browser so the user can see their total before paying.
