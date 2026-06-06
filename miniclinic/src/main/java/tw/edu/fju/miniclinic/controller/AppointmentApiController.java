package tw.edu.fju.miniclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tw.edu.fju.miniclinic.model.Appointment;
import tw.edu.fju.miniclinic.model.AppointmentRepository;
import tw.edu.fju.miniclinic.model.Doctor;
import tw.edu.fju.miniclinic.model.DoctorRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class AppointmentApiController {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @GetMapping("/api/appointments/count")
    public ResponseEntity<Long> getAppointmentsCount() {
        return ResponseEntity.ok(appointmentRepo.count());
    }

    @GetMapping("/api/appointments")
    public ResponseEntity<List<Appointment>> getAppointments(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String doctorId) {

        if (date != null && !date.isBlank()) {
            LocalDate apptDate = LocalDate.parse(date);
            return ResponseEntity.ok(appointmentRepo.findByApptDate(apptDate));
        }

        if (doctorId != null && !doctorId.isBlank()) {
            Optional<Doctor> doctorOpt = doctorRepo.findById(doctorId);
            if (doctorOpt.isPresent()) {
                return ResponseEntity.ok(appointmentRepo.findByDoctor(doctorOpt.get()));
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok(appointmentRepo.findAll());
    }
}