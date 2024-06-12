package com.satc.medify.service;

import com.satc.medify.model.Employee.Employee;
import com.satc.medify.model.Employee.EmployeeDTO;
import com.satc.medify.model.Employee.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EmployeeServiceTest {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should return employee passing the id")
    void getEmployeeByIdCase1() {
        Long id = (long) 1;
        EmployeeDTO data = new EmployeeDTO(id, "John", "admin");
        this.createEmployeeFake(data);

        Optional<Employee> result = this.employeeRepository.findById(id);
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not return employee passing the id")
    void getEmployeeByIdCase2() {
        Long id = (long) 1;

        Optional<Employee> result = this.employeeRepository.findById(id);
        assertThat(result.isEmpty()).isTrue();
    }


    @Test
    @DisplayName("Should successfully create an employee")
    void createEmployee() {
        Long id = (long) 1;

        EmployeeDTO data = new EmployeeDTO(id, "Alice", "developer");
        Employee employee = createEmployeeFake(data);

        Employee result = this.employeeRepository.findById(employee.getId()).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(data.name());
        assertThat(result.getRole()).isEqualTo(data.role());
    }

    @Test
    @DisplayName("Should successfully update an employee")
    void updateEmployee() {
        Long id = (long) 1;
        EmployeeDTO data = new EmployeeDTO(id, "John", "admin");
        createEmployeeFake(data);

        Employee updatedData = new Employee(new EmployeeDTO(id, "John Updated", "admin updated"));
        this.employeeRepository.save(updatedData);

        Employee result = this.employeeRepository.findById(id).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("John Updated");
        assertThat(result.getRole()).isEqualTo("admin updated");
    }

    @Test
    @DisplayName("Should successfully delete an employee")
    void deleteEmployee() {
        Long id = (long) 1;
        EmployeeDTO data = new EmployeeDTO(id, "John", "admin");
        Employee employee = createEmployeeFake(data);

        this.employeeRepository.delete(employee);

        Optional<Employee> result = this.employeeRepository.findById(id);
        assertThat(result.isEmpty()).isTrue();
    }

    private Employee createEmployeeFake(EmployeeDTO employeeDTO){
        Employee employee = new Employee(employeeDTO);
        this.entityManager.merge(employee);
        this.entityManager.flush();
        return employee;
    }
}