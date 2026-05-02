# `PrintStatus.java`

## 1. Purpose
This is a Java **Enum** that defines the complete lifecycle of a print order within the PrintBloom system. It prevents developers from using random strings to track a document's status.

## 2. Code Walkthrough

The enum defines four distinct stages:

1. **`AWAITING_PAYMENT`**: 
   The user has uploaded the file and the cost has been calculated, but the Razorpay transaction hasn't been completed yet. The job is *not* in the live queue.
   
2. **`PENDING`**: 
   The payment was successful. The job is now legitimately waiting in the queue to be printed.
   
3. **`PRINTING`**: 
   An administrator has marked the document as currently coming out of the physical printer.
   
4. **`COMPLETED`**: 
   The print job is entirely finished. (Note: The system also automatically moves `PENDING` jobs to `COMPLETED` after 10 seconds to simulate a quick print cycle).
