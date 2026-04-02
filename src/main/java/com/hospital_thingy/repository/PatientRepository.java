package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

//Interface that receives the data from the database with queries (built int functions)
//it extends from generic interface JpaRepository which contains the signatures of the 4 functions CRUD
public interface PatientRepository extends JpaRepository<Patient, Long> {
    //This is a SELECT *, it's the base for all built-in functions
    //    @Query ("SELECT p FROM patient p")
    //    List<Patient> getAllPatients();
    //save(object) findbyId(id) findall() deleteById(id) (Will be called by services)

    //Get all appointments from a patient
    @Query ("SELECT a FROM appointment a JOIN patient p ON a.patient_id = p.id WHERE p.id = pId")
    List<Appointment> GetPatientAppointments(Long pId);

    //Find if a newly created patient already exists but was just inactive
    //Can be done with insurance number as it's unchangeable and unique
    //not with id cuz that's for us people to handle not the user.
    @Query ("SELECT p FROM patient p WHERE insurance_number = inNum")
    Patient GetPatientByInsuranceNum(int inNum);


    //Find patient by name and last name
    //returns list? Do we have a search feature?
    @Query ("SELECT p FROM patient p WHERE first_name = fName AND last_name = lName")
    Patient GetPatientByFullName(String fName, String lName );

    //Handling of lists where? theres no bool or bit how do we check?
    //if they have anb appointment they become inactive
    //if not, then delete so what happened?

    //Update (due to logic, the only updatable field would be the phone number
    //which is the only one that can realistically change
    //maybe the last and first name too?
    @Query ("UPDATE patient p SET phone_number = phone WHERE id = pId")
    void UpdatePatientPhoneNumber(Long pId, BigInteger phone);


}

