package com.satc.medify.model.Client;


import com.satc.medify.model.Employee.EmployeeDTO;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String cpf;
    @Column(name = "is_active")
    private Boolean isActive;

    public Client(ClientDTO clientDTO) {
        this.id = clientDTO.id();
        this.name = clientDTO.name();
        this.email = clientDTO.email();
        this.phoneNumber = clientDTO.phoneNumber();
        this.cpf = clientDTO.cpf();
        this.isActive = true;
    }
}
