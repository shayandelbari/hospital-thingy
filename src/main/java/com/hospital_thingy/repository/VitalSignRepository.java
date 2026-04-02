package com.hospital_thingy.repository;

import com.hospital_thingy.entity.VitalSign;
import org.springframework.data.repository.CrudRepository;

public interface VitalSignRepository extends CrudRepository<VitalSign, Integer> {
}
