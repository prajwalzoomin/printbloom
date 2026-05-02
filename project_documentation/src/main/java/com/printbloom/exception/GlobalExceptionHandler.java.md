# `GlobalExceptionHandler.java`

## 1. Purpose
Without a global handler, if an error happens in your Spring Boot application (like a database crash or a missing file), the server might send back a confusing, ugly HTML error page to the frontend.

The `GlobalExceptionHandler` acts as a safety net that catches *any* error thrown anywhere in the application. It then formats that error into a clean, easy-to-read JSON response so the frontend (and the user) can understand exactly what went wrong.

## 2. Key Concepts

### `@RestControllerAdvice`
This annotation is what makes the class "Global". It tells Spring Boot: *"Hey, keep an eye on every single Controller in the app. If any of them crash and throw an exception, intercept it and bring it here instead."*

### `@ExceptionHandler`
This annotation tells Spring Boot which specific method should handle which specific type of error.

## 3. Code Walkthrough

1. **`handleFileStorageException()`**: 
   If any code throws a `FileStorageException`, this method catches it. It takes the custom error message and packages it into a `400 Bad Request` response.

2. **`handleResourceNotFoundException()`**: 
   If any code throws a `ResourceNotFoundException` (like trying to find a Print Order ID that doesn't exist), this method catches it and packages it into a `404 Not Found` response.

3. **`handleGenericException()`**: 
   This is the ultimate fallback. If an `Exception` (the grand-parent of all errors) happens that we didn't specifically plan for, this catches it. It hides the messy technical details from the user and just returns a generic *"An unexpected error occurred"* message with a `500 Internal Server Error` status.

4. **`buildErrorResponse()`**: 
   A private helper method. To avoid writing the same code over and over, this method takes a message and a status code, and builds a standard JSON map containing a timestamp, the HTTP status, the error type, and the message.
