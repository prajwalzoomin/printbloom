package com.printbloom.dto;

/**
 * Data Transfer Object returned when a new print
 * order is created or reprinted.
 *
 * This keeps the API response clean and focused
 * on what the frontend actually needs.
 */
public class PrintOrderResponseDTO {

    private Long orderId;
    private String status;
    private Double cost;
    private Integer queuePosition;
    private Integer copies;
    private Boolean isDuplex;

    public PrintOrderResponseDTO() {
        // Empty constructor for frameworks and manual creation.
    }

    public PrintOrderResponseDTO(Long orderId, String status, Double cost, Integer queuePosition, Integer copies, Boolean isDuplex) {
        this.orderId = orderId;
        this.status = status;
        this.cost = cost;
        this.queuePosition = queuePosition;
        this.copies = copies;
        this.isDuplex = isDuplex;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(Integer queuePosition) {
        this.queuePosition = queuePosition;
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
}

