# `pom.xml` - Maven Project Configuration

## 1. Purpose of this File
The `pom.xml` (Project Object Model) is the heart of any Maven-based Java project. Maven is a build tool that handles two main jobs:
1. **Dependency Management:** It automatically downloads all the external libraries (like Spring Boot, Razorpay, and MySQL drivers) that your project needs to run. You just tell Maven *what* you want, and it fetches the right versions from the internet.
2. **Build Process:** It helps compile your Java code, run tests, and package everything into an executable `.jar` file.

Think of the `pom.xml` as a grocery list and an instruction manual rolled into one. 

## 2. Key Sections Breakdown

### Project Metadata
```xml
<groupId>com.printbloom</groupId>
<artifactId>printbloom</artifactId>
<version>0.0.1-SNAPSHOT</version>
<name>printbloom</name>
```
- **groupId:** Usually a reversed domain name. It identifies the organization or team creating the project.
- **artifactId:** The unique name of this specific project.
- **version:** The current version. `SNAPSHOT` means it's currently under active development.

### The Parent POM
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.2</version>
</parent>
```
Spring Boot projects typically inherit from a "parent". This parent tells our project which versions of common libraries work well together, saving us from version-conflict headaches. Here, we are using Spring Boot version `3.3.2`.

### Dependencies (The Core Libraries)
This is the most important section. Each `<dependency>` block brings in external code.

- **`spring-boot-starter-web`**: This allows us to create REST APIs and web servers. It includes a built-in Tomcat web server so we can run our app easily.
- **`spring-boot-starter-data-jpa`**: "JPA" stands for Java Persistence API. This library makes it easy to save and retrieve data from a database without writing complex SQL queries by hand.
- **`mysql-connector-j`**: The driver that allows our Java application to talk to a MySQL database.
- **`pdfbox`**: An Apache tool used to work with PDF files. In PrintBloom, this is crucial for opening user-uploaded PDFs and counting how many pages they have.
- **`spring-boot-starter-validation`**: Gives us tools to ensure that data coming from the user is correct (e.g., ensuring an email address is valid, or that a requested number of copies is greater than 0).
- **`lombok`**: A developer tool that automatically generates repetitive Java code like "Getters" and "Setters" in the background, keeping our code clean and short.
- **`razorpay-java`**: The official SDK (Software Development Kit) from Razorpay to handle payments securely.
- **`spring-boot-starter-test`**: Tools used for writing automated tests to verify the code works.

### Build Plugin
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```
This plugin is what allows us to package our entire application (including the web server) into a single, runnable file.
