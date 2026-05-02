# `FileCleanupScheduler.java`

## 1. Purpose
PDF files are large. If every uploaded file stayed on the server forever, the hard drive would eventually fill up and crash the server. 
This class is an automated background worker. Its sole job is to wake up every hour, look for old print jobs that have already been completed, and permanently delete the physical PDF files from the server's hard drive to save space.

## 2. Key Concepts

### `@Scheduled` and CRON Expressions
Remember `SchedulerConfig.java`? That file turned the timer system on. *This* file actually uses the timer. 

```java
@Scheduled(cron = "0 0 * * * *")
```
The `@Scheduled` annotation tells Spring to run this method automatically. A "CRON expression" is a standard way in computing to describe time schedules. The expression `"0 0 * * * *"` translates to: *"Run when the second is 0, and the minute is 0, of every hour, every day."* (i.e., at the top of every hour).

## 3. Code Walkthrough

**`cleanupOldFiles()`**:
1. **Determine the Cutoff Time**: It looks at the current clock and subtracts 1 hour (`LocalDateTime.now().minusHours(1)`).
2. **Find Old Orders**: It asks the `PrintOrderRepository` to find every order that is marked `COMPLETED` and was created before that cutoff time.
3. **Delete Files**: It loops through those old orders. For each one, it takes the `filePath` and tells the operating system to physically delete that file from the hard drive (`Files.deleteIfExists()`).
4. **Clean Database**: After the file is gone, it also deletes the order record from the database so the system stays clean.
