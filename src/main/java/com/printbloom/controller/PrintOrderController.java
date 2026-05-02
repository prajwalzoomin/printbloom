package com.printbloom.controller;

import com.printbloom.dto.PrintOrderResponseDTO;
import com.printbloom.dto.QueueResponseDTO;
import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintType;
import com.printbloom.service.PrintOrderService;
import com.printbloom.service.QueueManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/print")
public class PrintOrderController {

    private final PrintOrderService printOrderService;
    private final QueueManagementService queueManagementService;

    public PrintOrderController(PrintOrderService printOrderService,
                                QueueManagementService queueManagementService) {
        this.printOrderService = printOrderService;
        this.queueManagementService = queueManagementService;
    }

    @PostMapping("/order")
    public ResponseEntity<PrintOrderResponseDTO> createOrder(@RequestParam("file") MultipartFile file,
                                                             @RequestParam("printType") PrintType printType,
                                                             @RequestParam(value = "copies", defaultValue = "1") int copies,
                                                             @RequestParam(value = "isDuplex", defaultValue = "false") boolean isDuplex) {
        if (file == null || file.isEmpty() || printType == null || copies < 1) {
            return ResponseEntity.badRequest().build();
        }
        
        PrintOrderResponseDTO dto = printOrderService.createPrintOrder(file, printType, copies, isDuplex);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/queue")
    public ResponseEntity<List<QueueResponseDTO>> getQueue() {
        List<QueueResponseDTO> queueSnapshot = queueManagementService.getCurrentQueueSnapshot();
        return ResponseEntity.ok(queueSnapshot);
    }

    @GetMapping("/history")
    public ResponseEntity<List<PrintOrder>> getHistory() {
        List<PrintOrder> history = printOrderService.getOrderHistory();
        return ResponseEntity.ok(history);
    }

    @PostMapping("/reprint")
    public ResponseEntity<PrintOrderResponseDTO> reprint(@RequestParam("orderId") Long orderId) {
        if (orderId == null) {
            return ResponseEntity.badRequest().build();
        }
        
        PrintOrderResponseDTO dto = printOrderService.reprintOrder(orderId);
        return ResponseEntity.ok(dto);
    }
}
