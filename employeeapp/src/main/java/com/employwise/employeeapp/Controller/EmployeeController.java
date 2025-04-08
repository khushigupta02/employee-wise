package com.employwise.employeeapp.Controller;

import com.employwise.employeeapp.Entity.Employee;
import com.employwise.employeeapp.Entity.PaginatedEmployeeResponse;
import com.employwise.employeeapp.Entity.PaginationRequest;
import com.employwise.employeeapp.Reponse.ApiResponse;
import com.employwise.employeeapp.Service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin("*")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    // a. Add Employee - Entry Level
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addEmployee(@Valid @RequestBody Employee emp) {
        try {
            Employee saved = service.addEmployee(emp);
            return ResponseEntity.ok(new ApiResponse("Employee added successfully with ID: " + saved.getId(), true));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Error: " + e.getMessage(), false));
        }
    }

    // b. Get All Employees - Entry Level
    @GetMapping("/get")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    // c. Delete Employee by ID - Entry Level
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id) {
        Employee emp = service.getEmployeeById(id);
        service.deleteEmployee(id, emp.getRevision());
        return "Deleted Successfully!";
    }

    // d. Update Employee by ID - Entry Level
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable String id, @Valid @RequestBody Employee emp) {
        try {
            Employee existing = service.getEmployeeById(id);
            emp.setId(existing.getId());
            emp.setRevision(existing.getRevision());
            Employee updated = service.updateEmployee(emp);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Update failed: " + e.getMessage());
        }
    }

    // a. Get nth Level Manager of an Employee - Intermediate Level
    @GetMapping("/{id}/manager/{level}")
    public ResponseEntity<?> getNthLevelManager(@PathVariable String id, @PathVariable int level) {
        try {
            Employee manager = service.getNthLevelManager(id, level);
            return ResponseEntity.ok(manager);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    // b. Get Employees with Pagination and Sorting - Intermediate Level
    @PostMapping("/getPagination")
    public ResponseEntity<PaginatedEmployeeResponse> getEmployeesWithBody(@Valid @RequestBody PaginationRequest request) {
        PaginatedEmployeeResponse response = service.getEmployeesWithPaginationAndSorting(
                request.getPage(),
                request.getSize(),
                request.getSortBy() != null ? request.getSortBy() : "employeeName",
                request.getSortOrder() != null ? request.getSortOrder() : "asc"
        );
        return ResponseEntity.ok(response);
    }

}
