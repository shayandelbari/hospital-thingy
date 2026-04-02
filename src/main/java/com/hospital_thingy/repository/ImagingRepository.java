package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Imaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagingRepository extends JpaRepository<Imaging, Integer> {
}
