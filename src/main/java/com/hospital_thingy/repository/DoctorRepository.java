package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    Optional<Doctor> findByLicenseNumber(String licenseNumber);
}
