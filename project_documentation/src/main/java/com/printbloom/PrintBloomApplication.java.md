# `PrintBloomApplication.java`

## 1. Purpose
This is the starting point of the entire backend application. Every Java program needs a `main` method to run, and this file contains the `main` method that boots up the Spring Boot server.

## 2. Key Concepts

### `@SpringBootApplication`
This is arguably the most important annotation in the entire project. It's actually a combination of three different annotations rolled into one:
1. `@Configuration`: Tells Spring to read this file for setup instructions.
2. `@EnableAutoConfiguration`: Tells Spring to automatically configure things based on what is in the `pom.xml`. (e.g., "I see a MySQL driver in the `pom.xml`, I will automatically set up a database connection").
3. `@ComponentScan`: Tells Spring to automatically scan the `com.printbloom` folder and its sub-folders to find all your `@Controller`, `@Service`, and `@Repository` classes and link them together.

## 3. Code Walkthrough

```java
@SpringBootApplication
public class PrintBloomApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrintBloomApplication.class, args);
    }
}
```

When you click "Run" in your IDE, or run the application from the command line, this is what happens:
1. Java looks for `public static void main`.
2. It executes `SpringApplication.run()`.
3. Spring takes over. It starts the embedded Tomcat web server.
4. It connects to the MySQL database.
5. It loads all your configurations, controllers, and services into memory.
6. The application goes "live" and begins listening for HTTP requests on port 8080.
