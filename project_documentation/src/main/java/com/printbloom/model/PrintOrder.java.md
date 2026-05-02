# `PrintOrder.java`

## 1. Purpose
This is the most important **Database Entity** in the PrintBloom application. It represents a single print job. It maps directly to the `print_orders` table in the database and holds every piece of information about a document from the moment it is uploaded until the moment the physical paper comes out of the printer.

## 2. Key Concepts

### Column Mapping
```java
@Column(name = "file_name", nullable = false)
private String fileName;
```
The `@Column` annotation allows us to customize how a Java variable looks in the database. 
- `name = "file_name"`: Even though our Java variable is camelCase (`fileName`), we tell the database to use snake_case (`file_name`), which is standard for SQL databases.
- `nullable = false`: This adds a strict rule to the database. It is physically impossible to save a `PrintOrder` if the file name is missing.

### Enums in the Database
```java
@Enumerated(EnumType.STRING)
@Column(name = "status", nullable = false)
private PrintStatus status;
```
By default, Java saves Enums to databases as numbers (0, 1, 2). By adding `@Enumerated(EnumType.STRING)`, we force Java to save the actual word (e.g., `"PENDING"`) into the database. This makes it much easier for humans to read the database directly.

### Cascading Relationships
```java
@OneToOne(mappedBy = "printOrder", cascade = CascadeType.ALL)
private Payment payment;
```
This is the other side of the relationship defined in the `Payment` class. 
`mappedBy` tells Spring that the `Payment` class is already handling the database link.
`cascade = CascadeType.ALL` is a powerful feature: If we delete a `PrintOrder` from the database, Spring will automatically "cascade" that deletion and delete the linked `Payment` as well, keeping the database perfectly synchronized.

## 3. Code Walkthrough

The class stores all the physical and logistical details of a print job:
- **`fileName` & `filePath`**: Where the PDF is located.
- **`pageCount`**: How long the document is.
- **`printType`**: Color or Black & White.
- **`copies` & `isDuplex`**: Printing preferences.
- **`cost`**: The calculated price.
- **`status`**: The current stage in the lifecycle (WAITING, PENDING, COMPLETED).
- **`createdAt`**: When the order was made.

The rest of the file provides constructors to create the object, and Getters/Setters to access its data safely.
