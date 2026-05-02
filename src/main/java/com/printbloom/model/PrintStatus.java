package com.printbloom.model;

public enum PrintStatus {
    AWAITING_PAYMENT, // Order generated but Razorpay transaction incomplete
    PENDING,          // Payment successful, legitimately waiting in the queue
    PRINTING,         // Admin marks the physical document as currently printing
    COMPLETED         // Admin marks as done
}
