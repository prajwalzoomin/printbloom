package com.printbloom.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RazorpayService {

    private final RazorpayClient razorpayClient;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public RazorpayService(RazorpayClient razorpayClient) {
        this.razorpayClient = razorpayClient;
    }

    /**
     * Pings the Razorpay Servers to generate an official Order ID locking in the amount.
     */
    public String createRazorpayOrder(Double amount) throws RazorpayException {
        // Mocking for frontend test when placeholder keys are present
        if ("rzp_test_placeholder_key_secret".equals(keySecret)) {
            return "order_dummy_" + UUID.randomUUID().toString().substring(0, 8);
        }

        // Razorpay only accepts integer monetary values in paise (1 INR = 100 Paise)
        int amountInPaise = (int) Math.round(amount * 100);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amountInPaise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "receipt_" + UUID.randomUUID().toString().substring(0, 8));
        
        Order order = razorpayClient.orders.create(orderRequest);
        return order.get("id"); // Returns something like "order_IluGWxBm9U8zJ8"
    }

    /**
     * Mathematically verifies that the payload returning from the frontend actually
     * originated from Razorpay using the HMAC-SHA256 hashing algorithm against our keySecret.
     */
    public boolean verifySignature(String razorpayOrderId, String razorpayPaymentId, String razorpaySignature) {
        if ("rzp_test_placeholder_key_secret".equals(keySecret)) {
            return true; // Mock verification success for placeholder mode
        }

        try {
            JSONObject options = new JSONObject();
            options.put("razorpay_order_id", razorpayOrderId);
            options.put("razorpay_payment_id", razorpayPaymentId);
            options.put("razorpay_signature", razorpaySignature);

            // Utilizing the native Razorpay SDK method for mathematical verification
            return Utils.verifyPaymentSignature(options, keySecret);
        } catch (RazorpayException e) {
            System.err.println("Razorpay signature verification failed: " + e.getMessage());
            return false;
        }
    }
}
