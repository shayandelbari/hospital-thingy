package com.hospital_thingy.service;

import com.hospital_thingy.DTO.ImagingDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.DTO.VitalSignDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.VitalSign;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.mapper.MedicalRecordMapper;
import com.hospital_thingy.repository.AppointmentRepository;
import com.hospital_thingy.repository.MedicalRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// reference documentation: https://www.javaguides.net/2025/03/spring-boot-architecture.html
@Service
public class MedicalRecordServices {
    private final MedicalRecordRepository medicalRecordRepository;
    private final MedicalRecordMapper medicalRecordMapper;
    private final AppointmentRepository appointmentRepository;

    public MedicalRecordServices(MedicalRecordRepository medicalRecordRepository,
            MedicalRecordMapper medicalRecordMapper, AppointmentRepository appointmentRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.medicalRecordMapper = medicalRecordMapper;
        this.appointmentRepository = appointmentRepository;
    }

    // Example: medical-record service can use a concrete mapper such as
    // ImagingMapper.
    // public ImagingDTO createImagingRecord(ImagingDTO request) {
    // var imaging = medicalRecordMapper.toEntity(request);
    // var savedRecord = medicalRecordRepository.save(imaging);
    // return medicalRecordMapper.toDto((com.hospital_thingy.entity.Imaging)
    // savedRecord);
    // }

    // There is NO delete of update functions
    // once a medical record exist, the clinic cannot change or delete it (in
    // accordance to law)

    public List<MedicalRecordDTO> getAllMedicalRecords() {

        return medicalRecordRepository.findAll()
                .stream()
                .map(medicalRecordMapper::toDto)
                .toList();
    }

    public MedicalRecordDTO getMedicalRecordById(Long id) {
        return medicalRecordRepository.findById(id)
                .map(medicalRecordMapper::toDto)
                .orElseThrow( () -> new EntityNotFoundException("Cannot find the requested Medical Record"));

    }
    /*
    public List<MedicalRecordDTO> getMedicalRecordsByAppointmentId(Long appointmentId) {
    }



        * public List<PatientDTO> getPatientByName(String firstName,  String lastName) {
        Patient probe = new Patient();
        probe.setLastName(lastName);
        probe.setFirstName(firstName);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase();

        Example<Patient> example = Example.of(probe, matcher);

        return patientMapper.toDtoList(patientRepository.findAll(example));

    }
*/


    public MedicalRecordDTO createMedicalRecord(MedicalRecordDTO rec) {

        /*
         * VALIDATIONS:
         * - medical record cannot be created without an associated appointment
         * 
         * Parent:
         * - ID generated in DB → nothing to check
         * - LocalDateTime is made by the constructor → nothing to check
         * - notes can be empty or can hold basically anything, no validations for the
         * parent class
         * 
         * Imaging:
         * - Must provide an image
         * 
         * Vitals:
         * - all are ints that fall in respective ranges
         * - all are optional, one check a Dr might just track weight, and another they
         * might only track HR and BP.
         */
        try {

            Optional<Appointment> appt = appointmentRepository.findById(rec.getAppointmentId());
            if (appt.isEmpty()) {
                throw new EntityCreationException("Medical records MUST be connected to an existing Appointment");
            }

            if (rec instanceof VitalSignDTO) {

                if (((VitalSignDTO) rec).getWeight() != null &&
                        (((VitalSignDTO) rec).getWeight() < 0 ||
                        ((VitalSignDTO) rec).getWeight() > 2000)) {
                    throw new EntityCreationException("Patient's weight must be between 0 and 2000 kg");
                }

                if (((VitalSignDTO) rec).getHeartRate() != null &&
                        (((VitalSignDTO) rec).getHeartRate() < 0 ||
                        ((VitalSignDTO) rec).getHeartRate() > 300)) {
                    throw new EntityCreationException("Patient's heart rate must be between 0 and 300 bmp");
                }

                if (((VitalSignDTO) rec).getSystolicBP() != null &&
                        (((VitalSignDTO) rec).getSystolicBP() < 0 ||
                        ((VitalSignDTO) rec).getSystolicBP() > 400)) {
                    throw new EntityCreationException("Patient's systolic BP must be between 0 and 400 mmHg");
                }

                if (((VitalSignDTO) rec).getDiastolicBP() != null &&
                        (((VitalSignDTO) rec).getDiastolicBP() < 0 ||
                        ((VitalSignDTO) rec).getDiastolicBP() > 400)) {
                    throw new EntityCreationException("Patient's diastolic BP must be between 0 and 400 mmHg");
                }

                if (((VitalSignDTO) rec).getSystolicBP() != null &&
                        ((VitalSignDTO) rec).getDiastolicBP() != null &&
                        (((VitalSignDTO) rec).getDiastolicBP() > ((VitalSignDTO) rec).getSystolicBP())) {
                    throw new EntityCreationException("Patient's diastolic BP cannot be larger than their systolic BP");
                }

                if (((VitalSignDTO) rec).getTemperature() != null &&
                        (((VitalSignDTO) rec).getTemperature() < 10 ||
                        ((VitalSignDTO) rec).getTemperature() > 50)) {
                    throw new EntityCreationException("Patient's temperature must be between 10 and 50 °C");
                }

                if (((VitalSignDTO) rec).getO2Saturation() != null &&
                        (((VitalSignDTO) rec).getO2Saturation() < 0 ||
                        ((VitalSignDTO) rec).getO2Saturation() > 100)) {
                    throw new EntityCreationException("Patient's oxygen saturation must be between 0 and 100 %");
                }

                VitalSign temp = (VitalSign) medicalRecordMapper.toEntity(rec);
                temp.setAppointment(appt.get());

                var saved = medicalRecordRepository.save(temp);
                return medicalRecordMapper.toDto(saved);
            }


            else if  (rec instanceof ImagingDTO)
            {
                throw new EntityCreationException ("Imaging is not supported yet");
            }

            /* {
             *
             * // - accept only the valid types of images (png, jpeg, etc.)
             * // - convert image file to byte[] before passing it to the repository layer
             * 
             * // we decided to not do it this Sprint as the deliverable is only a command
             * line based UI → this will only really make sense to implement once we have a
             * GUI
             * 
             * //when implementing - convert DTO to Imaging Entity
             * Imaging temp = (Imaging) medicalRecordMapper.toEntity(rec);
             * medicalRecordRepository.save(temp);
             * 
             * }
             */
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        throw new EntityCreationException("Cannot create Medical Record");
    }
}