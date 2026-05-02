# `AdminController.java`

## 1. Purpose
In any web application, a "Controller" is the component that receives HTTP requests from the internet (e.g., from a user's web browser) and decides what to do with them. 

The `AdminController` specifically handles requests meant for the administrator of the PrintBloom system. It provides "endpoints" (URLs) that allow an admin to see all print orders, change the status of an order (like marking it "Printed"), and delete orders.

## 2. Key Concepts

### `@RestController` and `@RequestMapping`
- **`@RestController`**: Tells Spring Boot that this class is meant to receive web traffic and that it should send back raw data (usually in JSON format) instead of an HTML web page.
- **`@RequestMapping("/admin")`**: This means every single URL handled by this class will start with `/admin`.

### Constructor Injection
```java
public AdminController(PrintOrderRepository printOrderRepository,
                       QueueManagementService queueManagementService) { ... }
```
This is called "Dependency Injection". The Controller doesn't interact with the database directly; it relies on the Repository and the Service to do the heavy lifting. By asking for them in the constructor, Spring Boot automatically provides them when it builds the Controller.

### `@GetMapping`, `@PutMapping`, `@DeleteMapping`
These annotations correspond to standard HTTP methods:
- **GET**: Used to fetch or read data (e.g., get a list of orders).
- **PUT**: Used to update existing data (e.g., change an order's status).
- **DELETE**: Used to remove data.

## 3. Code Walkthrough

1. **`getAllOrders()`** (Endpoint: `GET /admin/orders`): 
   Fetches every single print order from the database using the repository. It then sorts them so the newest orders appear at the top of the list, and sends that list back to the admin's browser.
   
2. **`updateOrderStatus()`** (Endpoint: `PUT /admin/order/status`): 
   Expects two parameters in the URL: an `orderId` and a new `status`. It checks if they are missing (returning a `400 Bad Request` if so). If they are valid, it passes them to the `QueueManagementService` to actually perform the update.
   
3. **`deleteOrder()`** (Endpoint: `DELETE /admin/order`):
   Expects an `orderId`. It first checks if that order actually exists in the database. If it doesn't, it returns a `404 Not Found` error. If it does exist, it asks the repository to delete it, and then returns a `204 No Content` response (meaning: "Success, but I have no data to send back").
