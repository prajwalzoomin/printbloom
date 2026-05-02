# `SchedulerConfig.java`

## 1. Purpose
This file has a very specific, simple job: it turns on the background task scheduler in Spring Boot. Sometimes, you want an application to do things automatically in the background on a timer (like deleting old PDF files every night at midnight). This class makes that possible.

## 2. Key Concepts

### `@EnableScheduling`
This is the magic annotation of this file. By adding `@EnableScheduling` to a configuration class, Spring Boot wakes up its internal clock system. Once this is active, you can go to other classes in your application, place a `@Scheduled(cron = "...")` annotation on a method, and Spring will run that method automatically according to the schedule you set.

## 3. Code Walkthrough

```java
@Configuration
@EnableScheduling
public class SchedulerConfig {
    // Enables scheduling support for future cleanup tasks.
}
```
As you can see, the class is completely empty inside! It doesn't need any variables or methods. The combination of `@Configuration` (so Spring reads the file on startup) and `@EnableScheduling` (the actual switch to turn on the timer system) is all that is required.
