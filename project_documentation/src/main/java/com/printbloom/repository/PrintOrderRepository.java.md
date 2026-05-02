# `PrintOrderRepository.java`

## 1. Purpose
This interface manages the database operations for the `PrintOrder` entity. Whenever the application needs to save a new file upload, look up someone's position in the queue, or delete an old order, it uses this repository.

## 2. Key Concepts

### Magic Naming Conventions
Just like the `PaymentRepository`, this interface relies on Spring Data JPA's method naming conventions to automatically generate SQL queries.

## 3. Code Walkthrough

This repository adds three custom queries beyond the standard `save()` and `findById()`:

1. **`findByStatus(PrintStatus status)`**:
   Returns a list of all print orders that currently have a specific status. For example, passing `PrintStatus.PENDING` will return everyone currently waiting in the live queue.

2. **`countByStatus(PrintStatus status)`**:
   Instead of returning the actual orders, this just returns a number. This is a highly efficient way to calculate queue position. If we want to know how long the queue is, we just ask the database to count the `PENDING` orders.

3. **`findByStatusAndCreatedAtBefore(PrintStatus status, LocalDateTime cutoffTime)`**:
   This is a slightly more complex query used by the automated scheduler. It finds orders that have a specific status AND were created before a specific time. We use this to find "COMPLETED" orders that are older than an hour so we can delete their PDFs to save hard drive space.
