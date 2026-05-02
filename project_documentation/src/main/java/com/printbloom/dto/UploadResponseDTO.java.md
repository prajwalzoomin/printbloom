# `UploadResponseDTO.java`

## 1. Purpose
This **Data Transfer Object (DTO)** is the immediate response sent back to the user right after they upload a PDF file on the main screen, but *before* they have actually paid or finalized the order.

It acts as an "estimate receipt". It tells the frontend: *"I received your file, here is how many pages it has, and here is how much it will cost to print."*

## 2. Key Concepts

### DTO vs Entity
It's important to remember that `UploadResponseDTO` is not saved to the database. It is a temporary "messenger" object. A true Entity (like `PrintOrder`) is permanently saved to the MySQL database. DTOs are created, sent over the internet, and then discarded.

## 3. Code Walkthrough

This DTO carries the following specific estimation data:
- **`fileName`**: The original name of the PDF the user uploaded (e.g., `resume.pdf`).
- **`pageCount`**: How many pages the backend found inside that PDF.
- **`estimatedCost`**: The total calculated price (based on pages, color vs B&W, and copies).
- **`message`**: An optional text message (like "Upload successful" or "Calculation complete").

Like all DTOs, it includes an empty constructor, a constructor that takes all arguments, and standard Getter and Setter methods to access the private data safely.
