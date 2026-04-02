package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.List;


//Interface that receives the data from the database with queries (built int functions)
//it extends from generic interface JpaRepository which contains the signatures of the built-in functions CRUD
public interface PatientRepository extends JpaRepository<Patient, Long> {

    //save(object) findbyId(id) findall() deleteById(id) (Will be called by services)

    /**
    Function that uses JPQL built-in understanding on entity relationships
    to get all the appointments related to a specific Patient's id
     @return List of appointments based on patient id
    **/
    List<Appointment> findByPatientId(Long pId);

    /**
     Custom query to check duplicates in patients in case an existing patient is being inserted
     again. Uses insurance number as it's the only unique-unchangeable value (other attributes
     can be updated).
     @return Patient with corresponding insurance number
     **/
    @Query ("SELECT p FROM Patient p WHERE p.insuranceNumber = :inNum")
    Patient GetPatientByInsuranceNum(@Param("inNum")int inNum);

}

