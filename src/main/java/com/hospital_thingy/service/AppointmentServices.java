package com.hospital_thingy.service;

import com.hospital_thingy.mapper.MedicalRecordMapper;
import java.util.List;
import java.util.Set;
import java.time.LocalTime;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.DTO.MedicalRecordDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Doctor;
import com.hospital_thingy.entity.Patient;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.DuplicateFoundException;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.repository.AppointmentRepository;
import com.hospital_thingy.repository.DoctorRepository;
import com.hospital_thingy.repository.PatientRepository;

/**
 * Represents the business logic layer (BLL) of the appointments.
 * Communicates with the {@link AppointmentRepository} to deliver sanitized
 * interaction
 * with the data access layer (DAL). Like an API.
 *
 * @author Abdulrahman Mousa
 * @version 1.0
 * @since 2026-04-02
 */
@Service
public class AppointmentServices {
    private static final Set<Appointment.Status> BOOKING_BLOCKING_STATUSES = Set.of(
            Appointment.Status.UPCOMING,
            Appointment.Status.IN_PROGRESS);

    private final AppointmentRepository apptRepo;
    private final AppointmentMapper apptMapper;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;
    private final MedicalRecordMapper medicalRecordMapper;

    /**
     * Constructs an AppointmentServices with the given repository.
     * 
     * @param repo   the appointment repository
     * @param mapper the appointment mapper (DTO : Entity)
     */
    public AppointmentServices(AppointmentRepository repo, AppointmentMapper mapper,
            DoctorRepository doctorRepo, PatientRepository patientRepo, MedicalRecordMapper medicalRecordMapper) {
        apptRepo = repo;
        apptMapper = mapper;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
        this.medicalRecordMapper = medicalRecordMapper;
    }

    /**
     * This method will use the repo's .findAll() method to retrieve the
     * appointments from the db
     * and will return a list of all the appointments.
     * 
     * @return List of the appointments
     * @since 1.0
     */
    public List<AppointmentDTO> getAll() {
        return apptMapper.toDtoList(apptRepo.findAll());
    }

    /**
     * Creates a new appointment.
     * 
     * @param entity the appointment to create
     * @throws EntityCreationException if creation fails
     * @since 1.0
     */
    public AppointmentDTO createAppointment(AppointmentDTO entity) {
        if (entity == null) {
            throw new EntityCreationException("Appointment payload is required");
        }
        if (entity.patientId == null || entity.doctorId == null) {
            throw new EntityCreationException("Appointment patientId and doctorId are required");
        }
        validateTimeWindow(entity.date, entity.startTime, entity.endTime);

        if (entity.status != null && entity.status != Appointment.Status.UPCOMING) {
            throw new EntityCreationException("New appointments must be created with UPCOMING status");
        }

        Doctor doctor = getRequiredDoctor(entity.doctorId);
        Patient patient = getRequiredPatient(entity.patientId);

        assertNoDoctorDoubleBooking(
                doctor.getId(),
                entity.date,
                entity.startTime,
                entity.endTime,
                null);

        try {
            Appointment toCreate = apptMapper.toEntity(entity);
            toCreate.setDoctor(doctor);
            toCreate.setPatient(patient);
            toCreate.setStatus(Appointment.Status.UPCOMING);

            Appointment saved = apptRepo.save(toCreate);
            return apptMapper.toDto(saved);
        } catch (Exception e) {
            throw new EntityCreationException("Couldn't create the appointment provided " + entity.id);
        }
    }

    /**
     * Reschedules an existing appointment by updating only date and time fields.
     *
     * @param id     appointment id to reschedule
     * @param entity appointment data containing new date/time
     * @throws EntityNotFoundException if the appointment is not found
     * @throws EntityUpdateException   if update fails
     * @since 1.0
     */
    public AppointmentDTO rescheduleAppointment(Long id, AppointmentDTO entity) {
        if (entity == null) {
            throw new EntityUpdateException("Reschedule payload is required");
        }
        validateTimeWindow(entity.date, entity.startTime, entity.endTime);

        Appointment toReschedule = getRequiredAppointment(id);

        if (toReschedule.getStatus() != Appointment.Status.UPCOMING
                && toReschedule.getStatus() != Appointment.Status.POSTPONED) {
            throw new EntityUpdateException("Only UPCOMING or POSTPONED appointments can be rescheduled");
        }

        assertNoDoctorDoubleBooking(
                toReschedule.getDoctor().getId(),
                entity.date,
                entity.startTime,
                entity.endTime,
                toReschedule.getId());

        toReschedule.setDate(entity.date);
        toReschedule.setStartTime(entity.startTime);
        toReschedule.setEndTime(entity.endTime);
        toReschedule.setStatus(Appointment.Status.UPCOMING);

        try {
            Appointment saved = apptRepo.save(toReschedule);
            return apptMapper.toDto(saved);
        } catch (Exception e) {
            throw new EntityUpdateException("Couldn't reschedule the appointment provided " + id);
        }
    }

    public AppointmentDTO startAppointment(Long id) {
        Appointment toStart = getRequiredAppointment(id);

        if (toStart.getStatus() != Appointment.Status.UPCOMING) {
            throw new EntityUpdateException("Only UPCOMING appointments can be started");
        }

        toStart.setStatus(Appointment.Status.IN_PROGRESS);

        try {
            Appointment saved = apptRepo.save(toStart);
            return apptMapper.toDto(saved);
        } catch (Exception e) {
            throw new EntityUpdateException("Couldn't start the appointment provided " + id);
        }
    }

    /**
     * Marks an appointment as completed.
     *
     * @param id the appointment id
     * @throws EntityNotFoundException if the appointment is not found
     * @throws EntityUpdateException   if status update fails
     * @since 1.0
     */
    public AppointmentDTO completeAppointment(Long id) {
        Appointment toComplete = getRequiredAppointment(id);

        if (toComplete.getStatus() != Appointment.Status.IN_PROGRESS) {
            throw new EntityUpdateException("Only IN_PROGRESS appointments can be completed");
        }

        toComplete.setStatus(Appointment.Status.COMPLETED);

        try {
            Appointment saved = apptRepo.save(toComplete);
            return apptMapper.toDto(saved);
        } catch (Exception e) {
            throw new EntityUpdateException("Couldn't complete the appointment provided " + id);
        }
    }

    /**
     * Cancels an appointment by setting its status to CANCELLED.
     * 
     * @param id the appointment id to cancel
     * @throws EntityNotFoundException if the appointment is not found
     * @throws DeletionFailedException if cancellation fails
     * @since 1.0
     */
    public AppointmentDTO cancelAppointment(Long id) {
        Appointment toCancel = getRequiredAppointment(id);

        if (toCancel.getStatus() == Appointment.Status.COMPLETED) {
            throw new DeletionFailedException("Cannot cancel a completed appointment");
        }

        if (toCancel.getStatus() == Appointment.Status.CANCELLED) {
            throw new DeletionFailedException("Appointment is already cancelled");
        }

        if (toCancel.getStatus() == Appointment.Status.IN_PROGRESS) {
            throw new DeletionFailedException("Cannot cancel an appointment that is already in progress");
        }

        toCancel.setStatus(Appointment.Status.CANCELLED);

        try {
            Appointment saved = apptRepo.save(toCancel);
            return apptMapper.toDto(saved);
        } catch (Exception e) {
            throw new DeletionFailedException("Couldn't cancel the appointment provided " + id);
        }
    }

    public AppointmentDTO postponeAppointment(Long id) {
        Appointment toPostpone = getRequiredAppointment(id);

        if (toPostpone.getStatus() != Appointment.Status.UPCOMING) {
            throw new EntityUpdateException("Only UPCOMING appointments can be postponed");
        }

        toPostpone.setStatus(Appointment.Status.POSTPONED);

        try {
            Appointment saved = apptRepo.save(toPostpone);
            return apptMapper.toDto(saved);
        } catch (Exception e) {
            throw new EntityUpdateException("Couldn't postpone the appointment provided " + id);
        }
    }

    /**
     * Retrieves an appointment by its ID.
     * 
     * @param id the ID of the appointment
     * @return the appointment
     * @throws EntityNotFoundException if the appointment is not found
     * @since 1.0
     */
    public AppointmentDTO getById(Long id) {
        return apptMapper.toDto(getRequiredAppointment(id));
    }

    public List<AppointmentDTO> getDoctorAppointmentsByDate(Long doctorId, LocalDate date) {
        getRequiredDoctor(doctorId);
        return apptMapper.toDtoList(apptRepo.findByDoctorIdAndDate(doctorId, date));
    }

    /**
     * Retrieves the medical records associated with a specific appointment.
     * 
     * @param appointmentId the ID of the appointment
     * @return the list of medical records
     * @throws EntityNotFoundException if the appointment is not found
     */
    public List<MedicalRecordDTO> getAppointmentMedicalRecords(Long appointmentId) {
        getRequiredAppointment(appointmentId);
        return medicalRecordMapper.toDtoList(apptRepo.findMedicalRecordsById(appointmentId));
    }

    private Appointment getRequiredAppointment(Long id) {
        return apptRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find the requested appointment"));
    }

    private Doctor getRequiredDoctor(Long doctorId) {
        return doctorRepo.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor with id " + doctorId + " doesn't exist"));
    }

    private Patient getRequiredPatient(Long patientId) {
        return patientRepo.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient with id " + patientId + " doesn't exist"));
    }

    private void validateTimeWindow(LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (date == null || startTime == null || endTime == null) {
            throw new EntityCreationException("Appointment date, startTime, and endTime are required");
        }
        // Boundary-touching is allowed (e.g., 10:00-11:00 and 11:00-12:00), so strict
        // inequality only applies to each appointment's own range.
        if (!endTime.isAfter(startTime)) {
            throw new EntityCreationException("Appointment endTime must be after startTime");
        }
    }

    private void assertNoDoctorDoubleBooking(Long doctorId, LocalDate date,
            LocalTime startTime, LocalTime endTime, Long excludeAppointmentId) {
        boolean hasConflict = apptRepo.existsDoctorDoubleBooking(
                doctorId,
                date,
                startTime,
                endTime,
                excludeAppointmentId,
                BOOKING_BLOCKING_STATUSES);

        if (hasConflict) {
            throw new DuplicateFoundException("Doctor already has an overlapping appointment in that time window");
        }
    }
}
