# `queue.js`

## 1. Purpose
This is a small, focused file responsible for driving the "Live Queue" page. It repeatedly asks the backend for the current status of all pending print jobs and updates the screen so users can watch their document move up in line.

## 2. Key Concepts

### Polling
Polling is a technique where a client (the browser) repeatedly asks a server (the backend) for new data at regular intervals. It's a simple way to create a "live updating" interface without using complex technologies like WebSockets.

## 3. Code Walkthrough

1. **`fetchQueue()`**:
   - Sends a `GET` request to `/print/queue` on the backend. (Remember the `QueueManagementService` we looked at earlier? This is the code calling it).
   - The backend returns an array of `QueueResponseDTO` objects.
   - If the array is empty, it displays a friendly message: "The queue is currently empty."
   - If there are items, it creates an HTML list item (`<li>`) for each one.
   - It dynamically inserts the `queuePosition`, `orderId`, `status`, and `estimatedWaitTime` into the HTML string and appends it to the page.

2. **The Loop**:
   - `fetchQueue();` runs it once immediately when the page loads.
   - `setInterval(fetchQueue, 5000);` tells the browser to run it again every 5 seconds, keeping the queue constantly up to date.
