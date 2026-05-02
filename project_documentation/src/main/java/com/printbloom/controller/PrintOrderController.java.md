# `PrintOrderController.java`

## 1. Purpose
The `PrintOrderController` handles the core operations of the application from the user's perspective. It receives requests to create new print orders, view the live printing queue, look at past printing history, and reprint previous documents.

## 2. Key Concepts

### Multi-Part File Upload (`@RequestParam("file") MultipartFile file`)
When users upload PDFs, they aren't sending simple text. They are sending binary files. Spring Boot handles this using the `MultipartFile` object. It automatically takes the raw data streaming in from the browser and packages it into an easy-to-use Java object.

### DTOs (Data Transfer Objects)
Notice that this controller frequently returns `ResponseEntity<PrintOrderResponseDTO>` instead of returning the raw `PrintOrder` database model. DTOs are specialized classes designed solely to carry data across the internet safely. They help hide sensitive database information and reduce the size of the data being sent.

## 3. Code Walkthrough

1. **`createOrder()`** (Endpoint: `POST /print/order`):
   - Takes the uploaded PDF file, the print type (Color/B&W), the number of copies, and whether it's double-sided (duplex).
   - Validates that the file isn't empty and the copies are at least 1.
   - Passes all this data to the `PrintOrderService` to do the heavy lifting of saving the file, counting pages, calculating cost, and saving it to the database.
   - Returns the created order details to the user.

2. **`getQueue()`** (Endpoint: `GET /print/queue`):
   - Asks the `QueueManagementService` for a list of all active orders (orders that are currently waiting or printing).
   - Used by the live queue screen to show users their position in line.

3. **`getHistory()`** (Endpoint: `GET /print/history`):
   - Fetches a list of all previous print orders.

4. **`reprint()`** (Endpoint: `POST /print/reprint`):
   - Allows a user to take an old order ID and generate a brand-new order from it, without having to upload the PDF file again.
