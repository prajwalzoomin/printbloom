package com.printbloom.dto;

/**
 * Data Transfer Object returned to the client
 * right after a file is uploaded and analysed.
 *
 * This does NOT expose internal entity details.
 * It only sends back what the frontend needs to show:
 * - file name
 * - page count
 * - estimated cost
 * - helpful message
 */
public class UploadResponseDTO {

    private String fileName;
    private Integer pageCount;
    private Double estimatedCost;
    private String message;

    public UploadResponseDTO() {
        // Empty constructor for frameworks and easy manual creation.
    }

    public UploadResponseDTO(String fileName, Integer pageCount, Double estimatedCost, String message) {
        this.fileName = fileName;
        this.pageCount = pageCount;
        this.estimatedCost = estimatedCost;
        this.message = message;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(Double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

