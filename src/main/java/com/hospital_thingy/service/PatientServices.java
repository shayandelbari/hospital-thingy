package com.hospital_thingy.service;

import com.hospital_thingy.entity.Patient;
import com.hospital_thingy.repository.PatientRepository;

import java.util.List;

public class PatientServices {
    private PatientRepository patientRepo;

    //SELECT everything
    public List<Patient> GetAllPatients(){
        return patientRepo.findAll();
    }

    //Get P

//    //SELECT patient BY ID
    //check error of return value
//    public Patient GetPatientById(Long pId){
//
//       return patientRepo.findById(pId);
//    }
}
