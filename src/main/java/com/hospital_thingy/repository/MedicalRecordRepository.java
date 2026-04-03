package com.hospital_thingy.repository;

import com.hospital_thingy.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    /* InBuilt CRUD Fns:
     * save(MedicalRecord); → create
     * findById(id); → read and return one Medical Record
     * findAll(); → read and return ALL Medical Records (as List)
     * deleteById(id) → delete
     */
}
