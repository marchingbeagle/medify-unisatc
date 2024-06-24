package com.satc.medify.service;

import com.satc.medify.model.Appointment.Appointment;
import com.satc.medify.model.Appointment.AppointmentDTO;
import com.satc.medify.model.Appointment.AppointmentRepository;
import com.satc.medify.model.Client.Client;
import com.satc.medify.model.Client.ClientDTO;
import com.satc.medify.model.Client.ClientRepository;
import com.satc.medify.model.Employee.Employee;
import com.satc.medify.model.Employee.EmployeeDTO;
import com.satc.medify.model.Employee.EmployeeRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({AppointmentService.class, EmployeeService.class, ClientService.class})
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AppointmentServiceTest {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ClientService clientService;

    @Test
    @DisplayName("Should successfully schedule an appointment")
    void scheduleAppointment() {
        Employee employee = createEmployeeFake(1L, "Employee", "EMPLOYEE_ROLE");
        Client client = createClientFake(1L, "Client", "client@example.com", "12345678900", "123456");

        AppointmentDTO appointmentDTO = new AppointmentDTO(null, employee, client, LocalDateTime.now());
        Appointment appointment = appointmentService.scheduleAppointment(appointmentDTO);

        assertThat(appointment).isNotNull();
        assertThat(appointment.getClient().getId()).isEqualTo(client.getId());
        assertThat(appointment.getClient().getName()).isEqualTo(client.getName());
        assertThat(appointment.getClient().getCpf()).isEqualTo(client.getCpf());
        assertThat(appointment.getClient().getPhoneNumber()).isEqualTo(client.getPhoneNumber());
        assertThat(appointment.getEmployee().getId()).isEqualTo(employee.getId());
        assertThat(appointment.getEmployee().getName()).isEqualTo(employee.getName());
        assertThat(appointment.getEmployee().getRole()).isEqualTo(employee.getRole());
    }

    @Test
    @DisplayName("Should throw exception for invalid employee ID")
    void scheduleAppointmentWithInvalidEmployee() {
        Client client = createClientFake(1L, "Client", "client@example.com", "12345678900", "123456");

        Employee invalidEmployee = new Employee(new EmployeeDTO(999L, "Invalid Employee", "INVALID_ROLE"));
        AppointmentDTO appointmentDTO = new AppointmentDTO(null, invalidEmployee, client, LocalDateTime.now());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> appointmentService.scheduleAppointment(appointmentDTO));
        assertThat(exception.getMessage()).isEqualTo("Invalid employee ID");
    }

    @Test
    @DisplayName("Should throw exception for invalid client ID")
    void scheduleAppointmentWithInvalidClient() {
        Employee employee = createEmployeeFake(1L, "Employee", "EMPLOYEE_ROLE");

        Client invalidClient = new Client(new ClientDTO(999L, "Invalid Client", "invalid@example.com", "12345678900", "123456"));
        AppointmentDTO appointmentDTO = new AppointmentDTO(null, employee, invalidClient, LocalDateTime.now());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> appointmentService.scheduleAppointment(appointmentDTO));
        assertThat(exception.getMessage()).isEqualTo("Invalid client ID");
    }

    @Test
    @DisplayName("Should successfully cancel an appointment")
    void cancelAppointment() {
        Employee employee = createEmployeeFake(1L, "Employee", "EMPLOYEE_ROLE");
        Client client = createClientFake(1L, "Client", "client@example.com", "12345678900", "123456");

        Appointment appointment = new Appointment();
        appointment.setEmployee(employee);
        appointment.setClient(client);
        appointment.setAppointmentTime(LocalDateTime.now());

        appointment = appointmentRepository.save(appointment);
        Long appointmentId = appointment.getId();

        appointmentService.cancelAppointment(appointmentId);

        Optional<Appointment> result = appointmentRepository.findById(appointmentId);
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("Should return all appointments")
    void getAllAppointments() {
        Employee employee = createEmployeeFake(1L, "Employee", "EMPLOYEE_ROLE");
        Client client = createClientFake(1L, "Client", "client@example.com", "12345678900", "123456");

        Appointment appointment = new Appointment();
        appointment.setEmployee(employee);
        appointment.setClient(client);
        appointment.setAppointmentTime(LocalDateTime.now());

        appointmentRepository.save(appointment);

        List<Appointment> appointments = appointmentService.getAllAppointments();

        assertThat(appointments).isNotEmpty();
        assertThat(appointments.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return appointment by ID")
    void getAppointmentById() {
        Employee employee = createEmployeeFake(1L, "Employee", "EMPLOYEE_ROLE");
        Client client = createClientFake(1L, "Client", "client@example.com", "12345678900", "123456");

        Appointment appointment = new Appointment();
        appointment.setEmployee(employee);
        appointment.setClient(client);
        appointment.setAppointmentTime(LocalDateTime.now());

        appointment = appointmentRepository.save(appointment);
        Long appointmentId = appointment.getId();

        Optional<Appointment> result = appointmentService.getAppointmentById(appointmentId);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getId()).isEqualTo(appointmentId);
    }

    private Employee createEmployeeFake(Long id, String name, String role) {
        EmployeeDTO employeeDTO = new EmployeeDTO(id, name, role);
        Employee employee = employeeService.createEmployee(employeeDTO);
        return employee;
    }

    private Client createClientFake(Long id, String name, String email, String cpf, String phone) {
        ClientDTO clientDTO = new ClientDTO(id, name, email, cpf, phone);
        Client client = clientService.createClient(clientDTO);
        return client;
    }
}