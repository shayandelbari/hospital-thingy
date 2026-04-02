package com.hospital_thingy.repository;

import com.hospital_thingy.entity.VitalSign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VitalSignRepository extends JpaRepository<VitalSign, Integer> {
}
