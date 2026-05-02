# `PaymentController.java`

## 1. Purpose
The `PaymentController` is responsible for handling the financial side of PrintBloom. It manages the communication between our frontend application and the Razorpay payment gateway. It creates secure payment orders and, most importantly, verifies that a payment was actually successful before pushing a document to the live printing queue.

## 2. Key Concepts

### REST API Interaction
This controller acts as a middleman. 
1. The user's browser asks our backend to create an order.
2. Our backend asks Razorpay to create an order.
3. Our backend passes Razorpay's unique Order ID back to the user's browser.
4. The user completes the payment on their screen via a Razorpay popup.
5. The Razorpay popup sends a secret signature back to our backend to prove the payment happened.

### Asynchronous Operations (`CompletableFuture.runAsync`)
Sometimes, we want to start a task in the background without making the user wait. In the `verifyPayment` method, an asynchronous block is used to automatically mark the print job as "COMPLETED" after a 10-second delay. This simulates the time it takes for a real printer to finish the job.

## 3. Code Walkthrough

1. **`createOrder()`** (Endpoint: `POST /payment/create-order`):
   - Takes an `orderId` to find the exact print order in the database.
   - Looks up the cost of that order.
   - Calls the `RazorpayService` to ask Razorpay to generate an official transaction ID for that specific cost.
   - Sends the `razorpayOrderId` back to the frontend.

2. **`verifyPayment()`** (Endpoint: `POST /payment/verify`):
   - This is the most critical method. It receives the proof of payment (`razorpayOrderId`, `paymentId`, and `signature`).
   - It calls `razorpayService.verifySignature()` to mathematically guarantee that the payment proof wasn't faked by a hacker.
   - If it's valid, it creates a new `Payment` record in the database and links it to the `PrintOrder`.
   - It changes the order's status to `PENDING` (which means it shows up in the live queue).
   - It tells the `PrinterService` to start physically printing the file.
   - It sets a 10-second background timer to eventually mark the job as `COMPLETED`.
