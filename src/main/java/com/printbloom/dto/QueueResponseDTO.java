package com.printbloom.dto;

/**
 * Data Transfer Object representing a single
 * entry in the print queue.
 *
 * This is useful for APIs like:
 * - GET /print/queue
 * where the user wants to see their position and status.
 */
public class QueueResponseDTO {

    private Long orderId;
    private String status;
    private Integer queuePosition;
    private Integer estimatedWaitTime;

    public QueueResponseDTO() {
        // Empty constructor for frameworks and manual creation.
    }

    public QueueResponseDTO(Long orderId, String status, Integer queuePosition, Integer estimatedWaitTime) {
        this.orderId = orderId;
        this.status = status;
        this.queuePosition = queuePosition;
        this.estimatedWaitTime = estimatedWaitTime;
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

    public Integer getQueuePosition() {
        return queuePosition;
    }

    public void setQueuePosition(Integer queuePosition) {
        this.queuePosition = queuePosition;
    }

    public Integer getEstimatedWaitTime() {
        return estimatedWaitTime;
    }

    public void setEstimatedWaitTime(Integer estimatedWaitTime) {
        this.estimatedWaitTime = estimatedWaitTime;
    }
}

