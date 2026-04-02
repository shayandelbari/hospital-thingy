package com.hospital_thingy.service;

import com.hospital_thingy.entity.MedicalRecord;
import com.hospital_thingy.entity.VitalSign;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.repository.MedicalRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;


// reference documentation: https://www.javaguides.net/2025/03/spring-boot-architecture.html
@Service
public class MedicalRecordServices {
    private final MedicalRecordRepository medicalRecordRepository;
 //   private final MedicalRecordMapper medicalRecordMapper;

    public MedicalRecordServices(MedicalRecordRepository medicalRecordRepository
//            , MedicalRecordMapper medicalRecordMapper
    ) {
        this.medicalRecordRepository = medicalRecordRepository;
//        this.medicalRecordMapper = medicalRecordMapper;
    }

    // Example: medical-record service can use a concrete mapper such as
    // ImagingMapper.
    // public ImagingDTO createImagingRecord(ImagingDTO request) {
    // var imaging = medicalRecordMapper.toEntity(request);
    // var savedRecord = medicalRecordRepository.save(imaging);
    // return medicalRecordMapper.toDto((com.hospital_thingy.entity.Imaging)
    // savedRecord);
    // }



    public List<MedicalRecord> getAllMedicalRecords() {

        return medicalRecordRepository.findAll();
    }


    public void CreateMedicalRecord (MedicalRecord rec) {

        /*
        VALIDATIONS:

        Parent:
         - ID generated in DB → nothing to check
         - LocalDateTime is made by the constructor → nothing to check
         - notes can be empty or can hold basically anything, no validations for the parent class

         Imaging:
         - Must provide an image

         Vitals:
         - all are ints that fall in respective ranges
         - all are optional, one check a Dr might just track weight, and another they might only track HR and BP.
         */

        if (rec instanceof VitalSign) {

            if (((VitalSign) rec).getWeight() != null &
                    ((VitalSign) rec).getWeight() < 0 |
                    ((VitalSign) rec).getWeight() > 2000)
            {
                    throw new EntityCreationException("Patient's weight must be between 0 and 1000 kg");
            }
            }
            if (((VitalSign) rec).getHeartRate() != null &
                    ((VitalSign) rec).getHeartRate() < 0 |
                    ((VitalSign) rec).getHeartRate() > 300)
            {
                throw new EntityCreationException ("Patient's heart rate must be between 0 and 300 bmp");
            }
            if (((VitalSign) rec).getSystolicBP() != null &
                    ((VitalSign) rec).getSystolicBP() < 0 |
                    ((VitalSign) rec).getSystolicBP() > 400)
            {
                throw new EntityCreationException ("Patient's systolic BP must be between 0 and 400 mmHg");
            }
            if (((VitalSign) rec).getDiastolicBP() != null &
                    ((VitalSign) rec).getDiastolicBP() < 0 |
                    ((VitalSign) rec).getDiastolicBP() > 400)
            {
                throw new EntityCreationException ("Patient's diastolic BP must be between 0 and 400 mmHg");
            }
            if (((VitalSign) rec).getSystolicBP() != null &
                    ((VitalSign) rec).getDiastolicBP() != null &
                    (((VitalSign) rec).getDiastolicBP() > ((VitalSign) rec).getSystolicBP()))
            {
                throw new EntityCreationException ("Patient's diastolic BP cannot be larger than their systolic BP");
            }
            if (((VitalSign) rec).getTemperature() != null &
                    ((VitalSign) rec).getTemperature() < 10 |
                    ((VitalSign) rec).getTemperature() > 50)
            {
                throw new EntityCreationException ("Patient's temperature must be between 10 and 50 °C");
            }
            if (((VitalSign) rec).getO2Saturation() != null &
                    ((VitalSign) rec).getO2Saturation() < 0 |
                    ((VitalSign) rec).getO2Saturation() > 100)
            {
                throw new EntityCreationException ("Patient's oxygen saturation must be between 0 and 100 %");
            }

        }
//        else // rec is an instance of Imaging
//        {
//
//        }
    }
