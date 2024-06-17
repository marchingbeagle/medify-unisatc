package com.satc.medify.model.Employee;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;

    @Column(name = "is_active")
    private Boolean isActive;

    public Employee(EmployeeDTO employeeDTO) {
        this.id = employeeDTO.id();
        this.name = employeeDTO.name();
        this.role = employeeDTO.role();
        this.isActive = true;
    }
}

