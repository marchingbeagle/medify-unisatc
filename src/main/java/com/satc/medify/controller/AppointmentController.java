package com.satc.medify.controller;

import com.satc.medify.model.Appointment.Appointment;
import com.satc.medify.model.Appointment.AppointmentDTO;
import com.satc.medify.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<Appointment> scheduleAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        Appointment appointment = appointmentService.scheduleAppointment(appointmentDTO);
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
