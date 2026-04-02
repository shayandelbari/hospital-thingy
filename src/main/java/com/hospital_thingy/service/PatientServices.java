package com.hospital_thingy.service;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Patient;
import com.hospital_thingy.repository.PatientRepository;

import java.util.List;

public class PatientServices {
    private PatientRepository patientRepo;

    //SELECT everything
    public List<Patient> GetAllPatients(){
        return patientRepo.findAll();
    }

    //ADD
    //CHECK of status from filtered inactive patients should be here.
    //If the
//    public void CreatePatient(Patient p){
//        try{
//            if (patientRepo){
//                patientRepo.save(p);
//                return;
//            }
//            throw new ExistingPatientException("That patient already exists");
//        }catch(ExistingPatientException epe){
//
//        }
//
//    }

    //Get Patient Appointments
//    public List<Appointment> GetPatientAppointments(Long id){
//       try {
//            if (patientRepo.existsById(id)){
//                return patientRepo.GetPatientAppointments(id);
//            }
//            throw new PatientDoesntExistException("Patient id doesn't exist");
//        }
//       catch(PatientDoesntExistException pde){
//           System.out.println("Error: "+pde.getMessage());
//           return null;
//       }
    //}

    //deprecated non lazy proxy
    public Patient GetPatientById(Long id){
        return patientRepo.getById(id);
    }


}




