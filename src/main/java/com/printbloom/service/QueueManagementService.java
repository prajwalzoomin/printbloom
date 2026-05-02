package com.printbloom.service;

import com.printbloom.dto.QueueResponseDTO;
import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintStatus;
import com.printbloom.repository.PrintOrderRepository;
import com.printbloom.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QueueManagementService {

    private final PrintOrderRepository printOrderRepository;

    public QueueManagementService(PrintOrderRepository printOrderRepository) {
        this.printOrderRepository = printOrderRepository;
    }

    public int getQueuePosition(Long orderId) {
        PrintOrder targetOrder = printOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("PrintOrder not found with id: " + orderId));

        if (targetOrder.getStatus() != PrintStatus.PENDING) {
            return 0; // Not in the active queue
        }

        // Fetch all pending orders and sort them by creation time
        List<PrintOrder> pendingOrders = printOrderRepository.findByStatus(PrintStatus.PENDING);
        pendingOrders.sort(Comparator.comparing(PrintOrder::getCreatedAt));

        // Find the index of the target order in the sorted list (0-indexed)
        for (int i = 0; i < pendingOrders.size(); i++) {
            if (pendingOrders.get(i).getId().equals(orderId)) {
                return i + 1; // 1-indexed queue position
            }
        }

        return 0; // Fallback
    }

    public void updateOrderStatus(Long orderId, PrintStatus newStatus) {
        PrintOrder order = printOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("PrintOrder not found with id: " + orderId));

        order.setStatus(newStatus);
        printOrderRepository.save(order);
    }

    public List<PrintOrder> getPendingOrders() {
        List<PrintOrder> pending = printOrderRepository.findByStatus(PrintStatus.PENDING);
        pending.sort(Comparator.comparing(PrintOrder::getCreatedAt));
        return pending;
    }

    public List<QueueResponseDTO> getCurrentQueueSnapshot() {
        List<PrintOrder> pendingOrders = getPendingOrders();
        
        return IntStream.range(0, pendingOrders.size())
                .mapToObj(i -> {
                    PrintOrder order = pendingOrders.get(i);
                    int position = i + 1;
                    int estimatedWaitTime = position * 2; // Simple estimate: 2 minutes per order
                    
                    return new QueueResponseDTO(
                            order.getId(),
                            order.getStatus().name(),
                            position,
                            estimatedWaitTime
                    );
                })
                .collect(Collectors.toList());
    }
}
