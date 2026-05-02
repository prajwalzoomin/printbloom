# `checkout.js`

## 1. Purpose
This is arguably the most complex frontend file in the project. It handles the transition from uploading a document to actually paying for it. It reads the order data saved in the browser, displays a summary to the user, and manages the intricate multi-step communication with the Razorpay payment gateway.

## 2. Key Concepts

### `sessionStorage`
When a user uploads a file on the first page, we need to remember the price and the order ID when they go to the second page (checkout). `sessionStorage` is a temporary memory bank built into the browser. It stores data that survives page navigations but is deleted as soon as the user closes the tab.

### The Payment Handshake
Accepting payments is highly regulated. The flow is:
1. **Frontend** asks **Backend** for a unique transaction ID.
2. **Backend** asks **Razorpay** for the ID and sends it to **Frontend**.
3. **Frontend** opens the official Razorpay popup using that ID.
4. User types in their card details.
5. **Razorpay** gives the **Frontend** a mathematical "Signature" proving payment.
6. **Frontend** sends that Signature back to the **Backend** to verify it wasn't forged.

## 3. Code Walkthrough

1. **Hydration**: 
   When the page loads (`DOMContentLoaded`), it reads the variables out of `sessionStorage` and injects them into the HTML (e.g., updating the text that says "Cost: ₹50.00"). If the data is missing (maybe the user navigated here directly by accident), it kicks them back to the upload page.

2. **The "Pay Now" Button**:
   - **Step A:** It calls `/payment/create-order` on our Java backend to get a `razorpayOrderId`.
   - **Mock Flow:** Look at `if (razorpayOrderId.startsWith("order_dummy_"))`. The backend `RazorpayService` creates dummy IDs if the developer hasn't put in real API keys yet. If the frontend sees a dummy ID, it skips the real Razorpay popup, waits a second to simulate it, and automatically verifies it. This is great for testing!
   - **Step B:** It configures the `options` object with the price, currency, and our test API key, and boots up the native Razorpay modal (`rzp.open()`).
   - **Step C:** If the user pays successfully inside the modal, Razorpay calls the `handler` function. This function takes the three pieces of proof (`razorpay_order_id`, `razorpay_payment_id`, `razorpay_signature`) and immediately sends them to our Java backend (`/payment/verify`) for final cryptographic verification.
   - If the backend says "Valid!", it redirects the user to `queue.html`.
