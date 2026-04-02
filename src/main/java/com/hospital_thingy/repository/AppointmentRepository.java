package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    /* InBuilt CRUD Fns:
     * save(MedicalRecord); → create
     * findById(id); → read and return one Medical Record
     * findAll(); → read and return ALL Medical Records (as List)
     * deleteById(id) → delete
     */

}
