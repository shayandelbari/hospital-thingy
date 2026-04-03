package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Represents the data access layer through the use of spring boot's Jpa
 * and entity objects which essentially translates SQL queries into
 * runnable methods. Built-in JpaRepository should suffice for now.
 *
 * @author Abdulrahman Mousa
 * @version 1.0
 * @since 2026-04-02
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    /*
     * InBuilt CRUD Fns:
     * save(MedicalRecord); → create
     * findById(id); → read and return one Medical Record
     * findAll(); → read and return ALL Medical Records (as List)
     * deleteById(id) → delete
     */
    List<Appointment> findByDoctorId(Long doctorId);

}
