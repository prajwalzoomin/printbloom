# `admin.js`

## 1. Purpose
This JavaScript file powers the Administrator Dashboard. While the backend `AdminController` provides the raw data, this file is responsible for asking for that data and drawing it on the screen so a human can read it and interact with it.

## 2. Key Concepts

### Asynchronous JavaScript (`async` / `await`)
When you request data from a server over the internet, it takes time. If JavaScript waited for the server to reply before doing anything else, the entire webpage would freeze. `async` and `await` are modern keywords that tell the browser: *"Send this request in the background, keep the page responsive, and let me know when the data arrives."*

### DOM Manipulation
DOM stands for Document Object Model (the HTML structure). Code like `document.getElementById('adminBody')` and `document.createElement('tr')` is how JavaScript finds a specific empty table in the HTML and injects rows of data into it dynamically.

## 3. Code Walkthrough

1. **`fetchAllOrders()`**:
   - Uses `fetch('/admin/orders')` to call our Java backend.
   - Clears out the old table (`tbody.innerHTML = ''`).
   - Loops through every order the backend returned.
   - For each order, it generates HTML buttons based on the status. If an order is `PENDING`, it gets a "Set Printing" button. If it's `PRINTING`, it gets a "Finalize" button.
   - It appends this newly generated HTML row to the table.

2. **`updateOrderStatus(orderId, newStatus)`**:
   - This function is triggered when an admin clicks one of the buttons generated above.
   - It sends a `PUT` request to `/admin/order/status` with the new status.
   - If successful, it immediately calls `fetchAllOrders()` to refresh the table.

3. **`deleteOrder(orderId)`**:
   - Pops up a confirmation box (`confirm(...)`) to prevent accidental deletions.
   - If confirmed, it sends a `DELETE` request to the backend.

4. **Polling Loop**:
   - `setInterval(fetchAllOrders, 5000);`
   - This line makes the browser automatically re-run the `fetchAllOrders` function every 5000 milliseconds (5 seconds). This creates a "live" dashboard that updates itself without the admin needing to hit the refresh button.
