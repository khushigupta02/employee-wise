package com.employwise.employeeapp.Entity;


import java.util.List;
public class PaginatedEmployeeResponse {
    private List<Employee> employees;
    private int currentPage;
    private int totalPages;
    private int totalElements;

    public PaginatedEmployeeResponse(List<Employee> employees, int currentPage, int totalPages, int totalElements) {
        this.employees = employees;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    // Getters and setters

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }
}
