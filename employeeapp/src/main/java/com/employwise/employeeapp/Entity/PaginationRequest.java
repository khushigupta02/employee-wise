package com.employwise.employeeapp.Entity;

import jakarta.validation.constraints.Min;

public class PaginationRequest {
    @Min(value = 0, message = "Page number must be >= 0")
    private int page;

    @Min(value = 1, message = "Page size must be >= 1")
    private int size;
    private String sortBy;
    private String sortOrder;

    // Getters and setters
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortOrder() { return sortOrder; }
    public void setSortOrder(String sortOrder) { this.sortOrder = sortOrder; }
}
