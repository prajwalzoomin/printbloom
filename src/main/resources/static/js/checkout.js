// js/checkout.js
document.addEventListener("DOMContentLoaded", () => {
    // 1. Recover Metadata securely injected by upload.js
    const fileName = sessionStorage.getItem("printbloom_fileName");
    const orderId = sessionStorage.getItem("printbloom_orderId");
    const printType = sessionStorage.getItem("printbloom_printType");
    const cost = sessionStorage.getItem("printbloom_estimatedCost");
    const copies = sessionStorage.getItem("printbloom_copies") || "1";
    const isDuplex = sessionStorage.getItem("printbloom_isDuplex") === "true";

    // Protection logic
    if (!orderId) {
        alert("Active session missing. Redirecting safely to Upload pipeline.");
        window.location.href = "upload.html"; return;
    }

    // 2. Hydrate DOM
    document.getElementById("sumFile").textContent = fileName;
    document.getElementById("sumOrder").textContent = "#" + orderId;
    document.getElementById("sumType").textContent = printType === "COLOR" ? "Premium Full Color" : "Grayscale Standard";
    document.getElementById("sumCopies").textContent = copies;
    document.getElementById("sumDuplex").textContent = isDuplex ? "Double Sided" : "Single Sided";
    document.getElementById("sumCost").textContent = "₹ " + parseFloat(cost).toFixed(2);

    document.getElementById("payBtn").addEventListener("click", async () => {
        const payBtn = document.getElementById("payBtn");
        payBtn.disabled = true;
        payBtn.textContent = "Connecting to Secure Gateway...";

        try {
            // STEP A: Fetch Razorpay Initial Order Signature from our backend
            const response = await fetch("/payment/create-order", {
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: "orderId=" + orderId
            });

            if (!response.ok) throw new Error("Failed to initialize backend Razorpay handshake.");

            const data = await response.json();
            const razorpayOrderId = data.razorpayOrderId;

            // MOCK FLOW: If backend sends a dummy order ID due to placeholder keys, bypass Razorpay execution.
            if (razorpayOrderId.startsWith("order_dummy_")) {
                document.getElementById("checkoutCard").innerHTML = `
                    <h2>Finalizing Order...</h2>
                    <div class="spinner"></div><p>Simulating secure verification (Placeholder Mode)...</p>
                `;

                const verifyData = new URLSearchParams();
                verifyData.append("orderId", orderId);
                verifyData.append("razorpay_order_id", razorpayOrderId);
                verifyData.append("razorpay_payment_id", "pay_dummy_" + Math.floor(Math.random() * 1000000));
                verifyData.append("razorpay_signature", "dummy_signature_for_testing");

                const verifyCall = await fetch("/payment/verify", {
                    method: "POST",
                    body: verifyData
                });

                if (verifyCall.ok) {
                    window.location.href = "queue.html";
                } else {
                    alert("Cryptographic backend verification failed! Transaction safely dropped.");
                    window.location.href = "upload.html";
                }
                return;
            }

            // STEP B: Boot the official Razorpay Interactive Modal natively
            const options = {
                key: "rzp_test_Sk87xWppn3Zqn5", // Uses the test application.properties key
                amount: parseFloat(cost) * 100,     // Converstion to Paise natively handled
                currency: "INR",
                name: "PrintBloom",
                description: "Campus Printing Payment Framework",
                order_id: razorpayOrderId,
                handler: async function (response) {
                    // STEP C: Successful User Card input causes this block to trigger. We must now VERIFY cryptographically.
                    document.getElementById("checkoutCard").innerHTML = `
                        <h2>Finalizing Order...</h2>
                        <div class="spinner"></div><p>Verifying secure HMAC payload with backend servers...</p>
                    `;

                    const verifyData = new URLSearchParams();
                    verifyData.append("orderId", orderId);
                    verifyData.append("razorpay_order_id", response.razorpay_order_id);
                    verifyData.append("razorpay_payment_id", response.razorpay_payment_id);
                    verifyData.append("razorpay_signature", response.razorpay_signature);

                    const verifyCall = await fetch("/payment/verify", {
                        method: "POST",
                        body: verifyData
                    });

                    if (verifyCall.ok) {
                        // Flips object in the MySQL DB from AWAITING_PAYMENT to PENDING.
                        // Order is now officially injected into Live Queue logic.
                        window.location.href = "queue.html";
                    } else {
                        alert("Cryptographic backend verification failed! Transaction safely dropped.");
                        window.location.href = "upload.html";
                    }
                },
                theme: { color: "#007bff" }
            };

            const rzp = new Razorpay(options);
            rzp.on('payment.failed', function (response) {
                alert("Payment Intentionally Dropped / Failed. Platform Reason: " + response.error.description);
                payBtn.disabled = false;
                payBtn.textContent = "Retry Secure Payment";
            });
            rzp.open();

        } catch (error) {
            alert(error.message);
            payBtn.disabled = false;
            payBtn.textContent = "Initialize Razorpay Gateway";
        }
    });

    document.getElementById("cancelBtn").addEventListener("click", () => {
        if (confirm("Are you absolutely sure you want to drop this order flow?")) {
            sessionStorage.clear();
            window.location.href = "upload.html";
        }
    });
});
