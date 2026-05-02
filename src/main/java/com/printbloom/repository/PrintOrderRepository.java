package com.printbloom.repository;

import com.printbloom.model.PrintOrder;
import com.printbloom.model.PrintStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for PrintOrder entities.
 *
 * By extending JpaRepository, Spring Data will automatically provide
 * common CRUD methods (save, findById, findAll, delete, etc.).
 *
 * As a beginner, you usually do NOT write any SQL yourself here.
 * Instead, you define method signatures, and Spring generates queries
 * based on the method names.
 */
@Repository
public interface PrintOrderRepository extends JpaRepository<PrintOrder, Long> {

    /**
     * Find all orders with the given status.
     * Example usage: get all PENDING orders for the print queue.
     */
    List<PrintOrder> findByStatus(PrintStatus status);

    /**
     * Count how many orders have the given status.
     * This is handy for queue position calculations.
     */
    long countByStatus(PrintStatus status);

    /**
     * Find orders by status created before a specific time.
     */
    List<PrintOrder> findByStatusAndCreatedAtBefore(PrintStatus status, java.time.LocalDateTime cutoffTime);
}

