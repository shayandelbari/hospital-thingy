package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Imaging;
import org.springframework.data.repository.CrudRepository;

public interface ImagingRepository extends CrudRepository<Imaging, Integer> {
}
