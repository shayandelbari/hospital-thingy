package com.hospital_thingy.controller;

import java.util.List;

import com.hospital_thingy.exception.EntityCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.DTO.PatientDTO;
import com.hospital_thingy.service.PatientServices;

@RestController
@RequestMapping("/api/patients")
public class PatientController {
    @Autowired
    private final PatientServices patientService;

    public PatientController(PatientServices patientService) {
        this.patientService = patientService;
    }

    // Query parameter use case: filter patients by active status.
    // GET /api/patients?active=true
    @GetMapping
    public List<PatientDTO> getAllPatients(@RequestParam(required = false) Boolean active) {
        var patients = patientService.getAllPatients();
        if (active == null) {
            return patients;
        }

        return patients.stream()
                .filter(patient -> patient.status != null && patient.status.equals(active))
                .toList();
    }

    @GetMapping("/{patientId}")
    public PatientDTO getPatientById(@PathVariable Long patientId){
        return patientService.FindPatientById(patientId);
    }

    // Dynamic URL example: GET /api/patients/5/appointments
    @GetMapping("/{patientId}/appointments")
    public List<AppointmentDTO> getPatientAppointmentsByPathVariable(@PathVariable Long patientId) {
           return patientService.getPatientAppointments(patientId);
    }

    // Dynamic URL example: GET /api/patients/5/records
    @GetMapping("/{patientId}/records")
    public List<MedicalRecordDTO> getPatientMedicalRecordsByPathVariable(@PathVariable Long patientId) {
        return patientService.getPatientMedicalRecord(patientId);
    }

    @PostMapping
    public PatientDTO createPatient(@RequestBody PatientDTO patient)
    {return patientService.createPatient(patient);}

    @DeleteMapping("/{patientId}")
    public PatientDTO deletePatient(@PathVariable Long patientId)
    {
        return patientService.deletePatient(patientId);
    }

    @PutMapping("/{patientId}")
    public PatientDTO updatePatient(@PathVariable Long patientId, @RequestBody PatientDTO p)
    {
        if (!checkInput(p)){
            throw new EntityCreationException("First and/or last name arguments are not in the correct format.");
        }
        return patientService.updatePatient(patientId, p);
    }

    @PutMapping("/{patientId}/{status}")
    public PatientDTO updatePatientStatus(@PathVariable Long patientId, @PathVariable Boolean status)
    {return patientService.updatePatientStatus(patientId, status);}

    //Make sure that name and last name are in correct format (all other input
    //is checked by json to entity conversion like inserting string for phone number)
    public boolean checkInput(PatientDTO p){
        return p.firstName.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")
                && p.lastName.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$");
    }

}