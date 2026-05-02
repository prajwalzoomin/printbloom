package com.printbloom.scheduler;

import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintStatus;
import com.printbloom.repository.PrintOrderRepository;
import com.printbloom.service.QueueManagementService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Scheduler responsible for periodically cleaning up old files.
 *
 * This class only defines WHEN cleanup should run.
 * The actual cleanup logic will be added later.
 */
@Component
public class FileCleanupScheduler {

    private final QueueManagementService queueManagementService;
    private final PrintOrderRepository printOrderRepository;

    public FileCleanupScheduler(QueueManagementService queueManagementService, PrintOrderRepository printOrderRepository) {
        this.queueManagementService = queueManagementService;
        this.printOrderRepository = printOrderRepository;
    }

    /**
     * Runs once every hour.
     *
     * When implementing this later, you will:
     * - Find all COMPLETED orders older than 24 hours
     * - Delete their files from disk
     * - Optionally delete or archive the database records
     *
     * The cron expression "0 0 * * * *" means:
     * second = 0, minute = 0, every hour, every day, every month, every day-of-week.
     */
    @Scheduled(cron = "0 0 * * * *")
    public void cleanupOldFiles() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(1);
        List<PrintOrder> oldOrders = printOrderRepository.findByStatusAndCreatedAtBefore(PrintStatus.COMPLETED, cutoffTime);
        
        int cleanedCount = 0;
        for (PrintOrder order : oldOrders) {
            try {
                Files.deleteIfExists(Paths.get(order.getFilePath()));
                printOrderRepository.delete(order);
                cleanedCount++;
            } catch (Exception e) {
                System.err.println("Failed to clean up file for order " + order.getId() + ": " + e.getMessage());
            }
        }
        
        if (cleanedCount > 0) {
            System.out.println("Cleaned up " + cleanedCount + " completed orders older than 1 hour.");
        }
    }
}

