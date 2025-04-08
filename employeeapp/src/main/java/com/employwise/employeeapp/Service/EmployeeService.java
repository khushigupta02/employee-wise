package com.employwise.employeeapp.Service;

import com.employwise.employeeapp.Email.EmailService;
import com.employwise.employeeapp.Entity.Employee;
import com.employwise.employeeapp.Entity.PaginatedEmployeeResponse;
import org.lightcouch.CouchDbClient;
import org.lightcouch.CouchDbProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class EmployeeService {

    private CouchDbClient dbClient;

    @Autowired
    private EmailService emailService;

    public EmployeeService() {
        CouchDbProperties properties = new CouchDbProperties()
                .setDbName("employees")
                .setCreateDbIfNotExist(true)
                .setProtocol("http")
                .setHost("127.0.0.1")
                .setPort(5984)
                .setUsername("admin")
                .setPassword("12345");

        this.dbClient = new CouchDbClient(properties);
    }

    public Employee addEmployee(Employee employee) {
        if (employee.getId() == null || employee.getId().isEmpty()) {
            employee.setId(java.util.UUID.randomUUID().toString());
        }

        dbClient.save(employee);

        // a.  Send Email to Level 1 Manager on New Employee Addition: Advanced Level
        if (employee.getReportsTo() != null && !employee.getReportsTo().isEmpty()) {
            try {
                Employee manager = getEmployeeById(employee.getReportsTo());

                if (manager.getEmail() != null && !manager.getEmail().isEmpty()) {
                    emailService.sendManagerNotification(
                            manager.getEmail(),
                            employee.getEmployeeName(),
                            employee.getPhoneNumber(),
                            employee.getEmail()
                    );
                }
            } catch (Exception e) {
                System.out.println("Could not send email to manager: " + e.getMessage());
            }
        }

        return employee;
    }


    public List<Employee> getAllEmployees() {
        return dbClient.view("_all_docs")
                .includeDocs(true)
                .query(Employee.class);
    }

    public Employee getEmployeeById(String id) {
        try {
            return dbClient.find(Employee.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Employee with ID " + id + " not found.");
        }
    }

    public void deleteEmployee(String id, String rev) {
        if (id == null || rev == null) {
            throw new IllegalArgumentException("Employee ID and revision are required for deletion.");
        }
        dbClient.remove(id, rev);
    }


    public Employee updateEmployee(Employee employee) {
        dbClient.update(employee);
        return employee;
    }
    public Employee getNthLevelManager(String employeeId, int level) {
        Employee current = getEmployeeById(employeeId);

        for (int i = 0; i < level; i++) {
            if (current == null) {
                throw new RuntimeException("Employee not found at level " + (i + 1));
            }

            String managerId = current.getReportsTo();
            if (managerId == null || managerId.equals("null") || managerId.isEmpty()) {
                throw new RuntimeException("No manager found at level " + (i + 1));
            }

            current = getEmployeeById(managerId);
        }

        return current;
    }

    public PaginatedEmployeeResponse getEmployeesWithPaginationAndSorting(int page, int size, String sortBy, String sortOrder) {
        List<Employee> allEmployees = dbClient.view("_all_docs")
                .includeDocs(true)
                .query(Employee.class);

        // Sort
        Comparator<Employee> comparator = Comparator.comparing(emp -> {
            switch (sortBy.toLowerCase()) {
                case "email": return emp.getEmail();
                case "phonenumber": return emp.getPhoneNumber();
                case "employeename":
                default: return emp.getEmployeeName();
            }
        });

        if (sortOrder.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        allEmployees.sort(comparator);

        int start = page * size;
        int end = Math.min(start + size, allEmployees.size());

        List<Employee> paginatedList = (start >= allEmployees.size()) ? new ArrayList<>() : allEmployees.subList(start, end);
        int totalPages = (int) Math.ceil((double) allEmployees.size() / size);

        return new PaginatedEmployeeResponse(paginatedList, page, totalPages, allEmployees.size());
    }


}
