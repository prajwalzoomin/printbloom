# `QueueManagementService.java`

## 1. Purpose
The `QueueManagementService` handles the logic for the "Live Print Queue". It calculates how many people are waiting, what position a specific order is in, and estimates how long a user has to wait before their document is printed.

## 2. Key Concepts

### Java Streams
In the `getCurrentQueueSnapshot()` method, you will see `IntStream.range(...)` and `.mapToObj(...)`. This is the Java Streams API. It is a modern, functional way to loop through lists and transform data without using traditional `for` loops.

## 3. Code Walkthrough

1. **`getQueuePosition(Long orderId)`**:
   - First, it checks if the order is even `PENDING`. If it's already `COMPLETED` or just `AWAITING_PAYMENT`, it returns position `0` (not in line).
   - It fetches all `PENDING` orders from the database.
   - It sorts them by the time they were created (oldest first).
   - It loops through the list until it finds the requested `orderId`. If it finds it at index `2`, it returns `3` (because users understand 1st, 2nd, 3rd, not 0th).

2. **`updateOrderStatus()`**:
   - A simple helper method used by the Admin panel to change an order from `PENDING` to `PRINTING` or `COMPLETED`.

3. **`getCurrentQueueSnapshot()`**:
   - This builds the data for the live queue screen.
   - It gets the sorted list of pending orders.
   - It loops through them, calculating their position (1, 2, 3...) and a fake estimated wait time (position * 2 minutes).
   - It packages this into a list of `QueueResponseDTO`s to send to the frontend.
