package com.printbloom.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Represents a single print order in the system.
 *
 * This class is mapped to the "print_orders" table in the database.
 * Each field corresponds to a column. As a beginner, you will typically
 * just add fields here, and Spring Data JPA will handle most of the
 * SQL for you through the repository layer.
 */
@Entity
@Table(name = "print_orders")
public class PrintOrder {

    /**
     * Primary key for the print order.
     *
     * GenerationType.IDENTITY tells the database to auto-generate the value
     * (e.g. MySQL AUTO_INCREMENT).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Original file name uploaded by the user.
     */
    @Column(name = "file_name", nullable = false)
    private String fileName;

    /**
     * Absolute or server-relative path where the file is stored.
     *
     * Example: "/uploads/1719923434_assignment.pdf"
     */
    @Column(name = "file_path", nullable = false)
    private String filePath;

    /**
     * Total number of pages in the PDF.
     */
    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    /**
     * Type of print requested by the user.
     * Stored as a String in the database for readability.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "print_type", nullable = false)
    private PrintType printType;

    /**
     * Number of copies.
     */
    @Column(name = "copies", nullable = false)
    private Integer copies;

    /**
     * Whether or not to print on both sides (Duplex).
     */
    @Column(name = "is_duplex", nullable = false)
    private Boolean isDuplex;

    /**
     * Total printing cost calculated for this order.
     */
    @Column(name = "cost", nullable = false)
    private Double cost;

    /**
     * Current status of this order in the print queue.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PrintStatus status;

    /**
     * Timestamp when this order was created.
     * This helps for ordering, history, and cleanup logic.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @jakarta.persistence.OneToOne(mappedBy = "printOrder", cascade = jakarta.persistence.CascadeType.ALL)
    private Payment payment;

    // ----------------------------
    // Constructors
    // ----------------------------

    /**
     * Default constructor required by JPA.
     * As a beginner, always keep an empty constructor for entity classes.
     */
    public PrintOrder() {
    }

    /**
     * Convenience constructor you can use manually if needed.
     * You can also generate more constructors later using your IDE.
     */
    public PrintOrder(Long id,
                      String fileName,
                      String filePath,
                      Integer pageCount,
                      PrintType printType,
                      Integer copies,
                      Boolean isDuplex,
                      Double cost,
                      PrintStatus status,
                      LocalDateTime createdAt) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.pageCount = pageCount;
        this.printType = printType;
        this.copies = copies;
        this.isDuplex = isDuplex;
        this.cost = cost;
        this.status = status;
        this.createdAt = createdAt;
    }

    // ----------------------------
    // Getters and Setters
    // ----------------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public PrintType getPrintType() {
        return printType;
    }

    public void setPrintType(PrintType printType) {
        this.printType = printType;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Boolean getIsDuplex() {
        return isDuplex;
    }

    public void setIsDuplex(Boolean isDuplex) {
        this.isDuplex = isDuplex;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public PrintStatus getStatus() {
        return status;
    }

    public void setStatus(PrintStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}

