package com.satc.medify.service;

import com.satc.medify.model.Employee.Employee;
import com.satc.medify.model.Employee.EmployeeDTO;
import com.satc.medify.model.Employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee(employeeDTO);
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            Employee updatedEmployee = existingEmployee.get();
            updatedEmployee.setName(employeeDTO.name());
            updatedEmployee.setRole(employeeDTO.role());
            return employeeRepository.save(updatedEmployee);
        }
        return null;
    }

    public boolean deleteEmployee(Long id) {
        Optional<Employee> existingEmployee = employeeRepository.findById(id);
        if (existingEmployee.isPresent()) {
            existingEmployee.get().setIsActive(false);
            employeeRepository.save(existingEmployee.get());
            return true;
        }
        return false;
    }
}