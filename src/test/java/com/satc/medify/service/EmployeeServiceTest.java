package com.satc.medify.service;

import com.satc.medify.model.Employee.Employee;
import com.satc.medify.model.Employee.EmployeeDTO;
import com.satc.medify.model.Employee.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeServiceTest {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return user passing the id")
    void getEmployeeByIdCase1() {
        Long id = (long) 1;
        EmployeeDTO data = new EmployeeDTO(id, "John", "admin");
        this.createEmployeeFake(data);

        Optional<Employee> result = this.employeeRepository.findById(id);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not return user passing the id")
    void getEmployeeByIdCase2() {
        Long id = (long) 1;

        Optional<Employee> result = this.employeeRepository.findById(id);
        assertThat(result.isEmpty()).isTrue();
    }

        @Test
    void createEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void deleteEmployee() {
    }

    private Employee createEmployeeFake(EmployeeDTO employeeDTO){
        Employee employee = new Employee(employeeDTO);
        this.entityManager.merge(employee);
        this.entityManager.flush();
        return employee;
    }
}