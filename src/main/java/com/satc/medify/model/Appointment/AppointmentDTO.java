package com.satc.medify.model.Appointment;

import com.satc.medify.model.Client.Client;
import com.satc.medify.model.Employee.Employee;

import java.time.LocalDateTime;

public record AppointmentDTO(Long id, Employee employee, Client client, LocalDateTime appointmentTime) {
}
