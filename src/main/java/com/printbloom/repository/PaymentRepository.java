package com.printbloom.repository;

import com.printbloom.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for Payment entities.
 *
 * Extending JpaRepository gives you basic CRUD operations.
 * Custom finder methods can be added just by defining their
 * signatures following Spring Data naming conventions.
 */
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /**
     * Find a payment record by its related order ID.
     *
     * Optional is used because there might be no payment
     * for a given order yet.
     */
    Optional<Payment> findByPrintOrderId(Long orderId);
}

