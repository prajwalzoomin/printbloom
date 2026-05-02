# PrintBloom: Essential Concepts for Beginners

Welcome to the **PrintBloom** project documentation! If you are new to programming or simply new to this specific set of technologies, this file is the perfect place to start. 

The goal of this folder is to help you understand *exactly* how PrintBloom works, line by line, file by file. Before we look at individual files, let's learn the core concepts that make up the backbone of this project.

---

## 1. What is PrintBloom?

PrintBloom is a full-stack web application. A "full-stack" app means it has two main parts:
1. **Frontend (Client-Side):** What the user sees and interacts with (buttons, forms, pages). In PrintBloom, this is built using standard web technologies: HTML, CSS, and JavaScript.
2. **Backend (Server-Side):** The hidden engine of the app that processes data, connects to a database, handles file uploads, and manages logic. In PrintBloom, this is built using **Java** with the **Spring Boot** framework.

The app allows users to upload PDF documents, calculate printing costs, pay securely online, and queue the documents for physical printing.

---

## 2. Core Technologies Used

### Java & Spring Boot
- **Java:** The programming language used for the backend. It's powerful, strictly structured, and widely used in enterprise applications.
- **Spring Boot:** A framework that makes building Java web applications much easier. Instead of writing thousands of lines of setup code, Spring Boot gives you a ready-to-run environment. It brings powerful tools to manage web requests, database connections, and application configuration automatically.

### HTML, CSS, and JavaScript (Vanilla)
- **HTML (HyperText Markup Language):** Provides the skeleton or structure of the web pages (e.g., text, images, forms).
- **CSS (Cascading Style Sheets):** Makes the HTML look good (colors, layout, spacing).
- **JavaScript (JS):** Makes the web pages interactive. When a user clicks "Upload", JavaScript catches that click, sends the file to the backend, and updates the screen without reloading the page. "Vanilla" means we are using raw JavaScript, not a framework like React or Angular.

### Maven (`pom.xml`)
Maven is a build and dependency management tool for Java. When we need external code libraries (like tools for handling PDFs or processing payments), we just list them in a file called `pom.xml`. Maven automatically downloads them and links them to our project.

### Razorpay
Razorpay is an external payment gateway service. We use it to allow users to pay for their print jobs securely. Our code talks to Razorpay's systems to create an order, verify the payment, and confirm success.

---

## 3. The Architecture: MVC and Layered Design

PrintBloom's backend follows a very common and powerful design pattern called **Layered Architecture**. Think of it like a restaurant:

1. **Controllers (The Waiters):** 
   - Found in the `controller` package.
   - They take the "order" (a web request from the frontend browser) and hand it to the kitchen. They also bring the "food" (the response) back to the user.
2. **Services (The Chefs):** 
   - Found in the `service` package.
   - They contain the "business logic". They do the actual hard work: calculating costs, counting PDF pages, contacting Razorpay, etc.
3. **Repositories (The Pantry/Storage):** 
   - Found in the `repository` package.
   - They communicate directly with the database. If the service needs to save a print order or find an old payment, it asks the repository.
4. **Models / Entities (The Ingredients):**
   - Found in the `model` package.
   - These are the basic data structures. For example, a `PrintOrder` model holds all the details of an order (file name, cost, number of copies).
5. **DTOs (Data Transfer Objects) (The Takeout Boxes):**
   - Found in the `dto` package.
   - Sometimes we don't want to send the entire Database Model to the frontend (maybe it has sensitive info). DTOs are simplified versions of our data specifically packaged to be sent over the internet.

---

## 4. Key Terminology

- **REST API:** A way for two computer systems to talk to each other over the internet. The frontend talks to the backend via REST APIs by making requests to specific URLs (like `/api/upload` or `/api/payment/create`).
- **Dependency Injection (DI):** A core feature of Spring Boot. If Class A needs Class B to do its job, instead of Class A creating Class B itself, Spring Boot "injects" Class B into Class A automatically. You'll often see this done via `@Autowired` or by using a constructor.
- **Annotations:** Special words in Java starting with `@` (like `@RestController`, `@Service`, `@Autowired`). These are instructions to Spring Boot. For example, `@Service` tells Spring Boot: *"Hey, treat this class as a Service component and manage it for me."*

---

## 5. How to Read This Documentation

In this folder, you'll find a mirror of the project's actual folder structure. Inside, every `.java`, `.html`, `.css`, and `.js` file has a corresponding `.md` (Markdown) file.

**When you open one of those files, you will find:**
1. **Purpose:** A high-level summary of what the file does.
2. **Key Concepts:** Explanations of specific annotations or programming tricks used in the file.
3. **Code Walkthrough:** A detailed breakdown of the functions and variables in the file.

### Recommended Reading Order
If you want to read through the project like a story, try this order:
1. **The Core App:** `PrintBloomApplication.java.md`
2. **The Models:** `model/PrintOrder.java.md` -> `model/Payment.java.md`
3. **The Services:** `service/FileStorageService.java.md` -> `service/PrintOrderService.java.md`
4. **The Controllers:** `controller/UploadController.java.md` -> `controller/PaymentController.java.md`
5. **The Frontend:** `static/upload.html.md` -> `static/js/upload.js.md`

Happy learning! Dive into the folders whenever you're ready.
