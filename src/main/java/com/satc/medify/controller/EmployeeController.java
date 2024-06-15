package com.satc.medify.controller;

import com.satc.medify.model.Employee.Employee;
import com.satc.medify.model.Employee.EmployeeDTO;
import com.satc.medify.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

    @RestController
    @RequestMapping("/api/employees")
    public class EmployeeController {

        @Autowired
        private EmployeeService employeeService;

        @GetMapping
        public List<Employee> getAllEmployees() {
            return employeeService.getAllEmployees();
        }

        @GetMapping("/{id}")
        public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
            Optional<Employee> employee = employeeService.getEmployeeById(id);
            return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
            Employee createdEmployee = employeeService.createEmployee(employeeDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
            Employee updatedEmployee = employeeService.updateEmployee(id, employeeDTO);
            return updatedEmployee != null ? ResponseEntity.ok(updatedEmployee) : ResponseEntity.notFound().build();
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
            boolean deleted = employeeService.deleteEmployee(id);
            return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }
    }