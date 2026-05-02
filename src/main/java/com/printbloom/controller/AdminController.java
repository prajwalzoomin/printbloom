package com.printbloom.controller;

import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintStatus;
import com.printbloom.service.QueueManagementService;
import com.printbloom.repository.PrintOrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final PrintOrderRepository printOrderRepository;
    private final QueueManagementService queueManagementService;

    public AdminController(PrintOrderRepository printOrderRepository,
                           QueueManagementService queueManagementService) {
        this.printOrderRepository = printOrderRepository;
        this.queueManagementService = queueManagementService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<PrintOrder>> getAllOrders() {
        List<PrintOrder> allOrders = printOrderRepository.findAll();
        allOrders.sort((o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt())); // Newest first
        return ResponseEntity.ok(allOrders);
    }

    @PutMapping("/order/status")
    public ResponseEntity<Void> updateOrderStatus(@RequestParam("orderId") Long orderId,
                                                  @RequestParam("status") PrintStatus status) {
        if (orderId == null || status == null) {
            return ResponseEntity.badRequest().build();
        }
        
        queueManagementService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/order")
    public ResponseEntity<Void> deleteOrder(@RequestParam("orderId") Long orderId) {
        if (orderId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        if (!printOrderRepository.existsById(orderId)) {
            return ResponseEntity.notFound().build();
        }
        
        printOrderRepository.deleteById(orderId);
        return ResponseEntity.noContent().build();
    }
}
