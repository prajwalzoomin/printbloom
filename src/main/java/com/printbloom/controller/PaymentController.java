package com.printbloom.controller;

import com.printbloom.model.Payment;
import com.printbloom.model.PaymentStatus;
import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintStatus;
import com.printbloom.repository.PrintOrderRepository;
import com.printbloom.service.PrinterService;
import com.printbloom.service.RazorpayService;
import com.razorpay.RazorpayException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final RazorpayService razorpayService;
    private final PrintOrderRepository printOrderRepository;
    private final PrinterService printerService;

    public PaymentController(RazorpayService razorpayService, PrintOrderRepository printOrderRepository, PrinterService printerService) {
        this.razorpayService = razorpayService;
        this.printOrderRepository = printOrderRepository;
        this.printerService = printerService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestParam("orderId") Long orderId) {
        Optional<PrintOrder> orderOpt = printOrderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        PrintOrder order = orderOpt.get();
        double cost = order.getCost();

        try {
            String razorpayOrderId = razorpayService.createRazorpayOrder(cost);
            
            Map<String, String> response = new HashMap<>();
            response.put("razorpayOrderId", razorpayOrderId);
            
            return ResponseEntity.ok(response);
        } catch (RazorpayException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate Razorpay transaction: " + e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(@RequestParam("orderId") Long orderId,
                                           @RequestParam("razorpay_order_id") String razorpayOrderId,
                                           @RequestParam("razorpay_payment_id") String razorpayPaymentId,
                                           @RequestParam("razorpay_signature") String razorpaySignature) {

        boolean isSignatureValid = razorpayService.verifySignature(razorpayOrderId, razorpayPaymentId, razorpaySignature);

        if (!isSignatureValid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Payment Signature");
        }

        Optional<PrintOrder> orderOpt = printOrderRepository.findById(orderId);
        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        PrintOrder printOrder = orderOpt.get();

        // Map the official fields to our Payment Entity
        Payment payment = new Payment();
        payment.setRazorpayOrderId(razorpayOrderId);
        payment.setRazorpayPaymentId(razorpayPaymentId);
        payment.setRazorpaySignature(razorpaySignature);
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setPrintOrder(printOrder);

        // Update PrintOrder
        printOrder.setPayment(payment);
        printOrder.setStatus(PrintStatus.PENDING); // Officially pushes it into the live printing queue

        // The @OneToOne CascadeType.ALL will automatically save the Payment to the payments table
        printOrderRepository.save(printOrder);

        // Trigger physical print
        printerService.printOrderDocument(printOrder);

        // Automatically finalize the order so it doesn't get stuck in the queue
        java.util.concurrent.CompletableFuture.runAsync(() -> {
            try {
                // Short delay so the user can briefly see it in the queue before it finishes
                Thread.sleep(10000); 
                printOrder.setStatus(PrintStatus.COMPLETED);
                printOrderRepository.save(printOrder);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        return ResponseEntity.ok("Payment Verification Successful!");
    }
}
