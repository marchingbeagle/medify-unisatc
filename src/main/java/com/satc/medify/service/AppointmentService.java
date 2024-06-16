package com.satc.medify.service;

import com.satc.medify.model.Appointment.Appointment;
import com.satc.medify.model.Appointment.AppointmentDTO;
import com.satc.medify.model.Appointment.AppointmentRepository;
import com.satc.medify.model.Client.Client;
import com.satc.medify.model.Client.ClientRepository;
import com.satc.medify.model.Employee.Employee;
import com.satc.medify.model.Employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ClientRepository clientRepository;

    public Appointment scheduleAppointment(AppointmentDTO appointmentDTO) {
        Employee employee = employeeRepository.findById(appointmentDTO.employee().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID"));
        Client client = clientRepository.findById(appointmentDTO.client().getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid client ID"));

        Appointment appointment = new Appointment();
        appointment.setEmployee(employee);
        appointment.setClient(client);
        appointment.setAppointmentTime(appointmentDTO.appointmentTime());

        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }
}
