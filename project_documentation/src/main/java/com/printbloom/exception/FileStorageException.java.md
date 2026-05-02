# `FileStorageException.java`

## 1. Purpose
In Java, when something goes wrong (like a file fails to save or a disk is full), the program "throws an Exception". A generic exception tells you *that* something went wrong, but a custom exception tells you *exactly what* went wrong.

`FileStorageException` is a custom error specifically created for PrintBloom. It is used whenever there is a problem saving, reading, or processing an uploaded PDF file. 

## 2. Key Concepts

### Extending `RuntimeException`
Notice the code says `public class FileStorageException extends RuntimeException`. 
By "extending" `RuntimeException`, this class inherits all the standard features of a Java error. It means we don't have to write complex error-handling logic from scratch; we just piggyback on Java's built-in system.

## 3. Code Walkthrough

The class only contains two constructors (methods used to create the error object):

1. **`FileStorageException(String message)`**: 
   Used when we want to throw a simple error message. For example, if a file is empty, we might do:
   `throw new FileStorageException("Cannot store empty file.");`

2. **`FileStorageException(String message, Throwable cause)`**: 
   Sometimes, our code fails because a deeper, underlying Java component failed first (the `cause`). This constructor allows us to bundle our custom message together with the original underlying error so that debugging is easier later on.
