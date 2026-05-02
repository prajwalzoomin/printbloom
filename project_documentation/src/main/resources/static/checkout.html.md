# `checkout.html`

## 1. Purpose
This HTML file is the middle-man between the user uploading a file and the user waiting in the queue. It shows them a summary of their order (filename, copies, cost) and provides the critical "Pay Now" button to lock in the transaction.

## 2. Key Concepts

### Placeholders (`--`)
```html
<div class="summary-row">
    <span style="color:#666;">Document:</span>
    <span id="sumFile" style="font-weight:600;">--</span>
</div>
```
If you open this HTML file directly, it will just show `--` for all the values. It is the job of `checkout.js` to run immediately upon load, grab the real values out of the `sessionStorage`, and replace those `--` placeholders with the actual data (like "resume.pdf").

### Razorpay Script Injection
```html
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
```
At the bottom of the page, we don't just load our own `checkout.js`. We also load Razorpay's official checkout library. This is what allows the secure, native payment popup to appear over our webpage when the user clicks the Pay button.

## 3. Code Walkthrough

1. **Inline Styles**: Some specific CSS is included in the `<head>` just for the receipt-looking `summary-box`.
2. **Order Summary Box**: A container holding all the `--` placeholders waiting to be filled by javascript.
3. **Action Buttons**: The "Initialize Razorpay Gateway" button (`payBtn`) and the "Drop this order" button (`cancelBtn`).
4. **Secure Badge**: A nice visual touch at the bottom showing an SVG padlock icon and some text to reassure the user that the checkout is encrypted.
