# `QueueResponseDTO.java`

## 1. Purpose
This is a **Data Transfer Object (DTO)** designed specifically for the live printing queue feature. 

When a user opens the "Queue" page on the frontend, the website asks the server: *"What jobs are currently printing or waiting in line?"* The server responds by sending a list of `QueueResponseDTO` objects. 

## 2. Key Concepts

### Focused Data
The main idea behind DTOs is focus. A user looking at the queue doesn't need to know the file name of someone else's print job, or how much it cost. They only need to know the order IDs, their status, and their position. By using `QueueResponseDTO` instead of the full `PrintOrder` database model, we save internet bandwidth and keep private information secure.

## 3. Code Walkthrough

This DTO holds four pieces of information for a single entry in the queue:
- **`orderId`**: The ID of the print job.
- **`status`**: Is it currently "PRINTING" or is it just "PENDING" (waiting in line)?
- **`queuePosition`**: What number in line is it? (1 means it's next).
- **`estimatedWaitTime`**: A number (usually in minutes) estimating how long until this job is finished.

The class also contains standard Java constructors (both empty and fully-loaded) and standard Getters/Setters to allow Spring Boot to read and write the data easily.
