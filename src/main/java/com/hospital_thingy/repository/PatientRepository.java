package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.MedicalRecord;
import com.hospital_thingy.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//Interface that receives the data from the database with queries (built int functions)
//it extends from generic interface JpaRepository which contains the signatures of the built-in functions CRUD
public interface PatientRepository extends JpaRepository<Patient, Long> {

    // save(object) findbyId(id) findall() deleteById(id) (Will be called by
    // services)

    /**
     * Function that uses JPQL built-in understanding on entity relationships
     * to get all the appointments related to a specific Patient's id
     * 
     * @return List of appointments based on patient id
     **/
    @Query("SELECT a FROM Appointment a JOIN a.patient p WHERE p.id = :pId")
    List<Appointment> getPatientAppointments(@Param("pId") Long pId);

    /**
     * Function that uses JPQL built-in understanding on entity relationships
     * to get all the medical records related to a specific Patient's id (accessed
     * through
     * appointments of a specific patient)
     * 
     * @return List of appointments based on patient id
     **/
    @Query("SELECT m FROM Patient p JOIN p.appointments a JOIN a.medicalRecords m WHERE p.id = :pId")
    List<MedicalRecord> getPatientMedicalRecords(@Param("pId") Long pId);

    /**
     * Custom query to check duplicates in patients in case an existing patient is
     * being inserted
     * again. Uses insurance number as it's the only unique-unchangeable value
     * (other attributes
     * can be updated).
     * 
     * @return Patient with corresponding insurance number
     **/
    Optional<Patient> findByInsuranceNumber(String inNum);

    List<Patient> findByDateOfBirth(LocalDate dob);
}
