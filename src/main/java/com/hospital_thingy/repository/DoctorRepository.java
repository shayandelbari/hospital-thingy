package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long>
{
}
