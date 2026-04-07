package com.hospital_thingy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.repository.AppointmentRepository;

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
    final private AppointmentRepository apptRepo;
    final private AppointmentMapper apptMapper;

    /**
     * Constructs an AppointmentServices with the given repository.
     * 
     * @param repo   the appointment repository
     * @param mapper the appointment mapper (DTO : Entity)
     */
    public AppointmentServices(AppointmentRepository repo, AppointmentMapper mapper) {
        apptRepo = repo;
        apptMapper = mapper;
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
    public void createAppointment(AppointmentDTO entity) {
        try {
            apptRepo.save(apptMapper.toEntity(entity));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityCreationException("Couldn't create the appointment provided " + entity.id);
        }
    }

    /**
     * Reschedules an existing appointment by updating only date and time fields.
     *
     * @param entity appointment data containing id and new date/time
     * @throws EntityNotFoundException if the appointment is not found
     * @throws EntityUpdateException   if update fails
     * @since 1.0
     */
    public void rescheduleAppointment(AppointmentDTO entity) {
        AppointmentDTO toReschedule = getById(entity.id); // Will throw an exception if failed to find the id

        toReschedule.date = entity.date;
        toReschedule.startTime = entity.startTime;
        toReschedule.endTime = entity.endTime;

        try {
            apptRepo.save(apptMapper.toEntity(toReschedule));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityUpdateException("Couldn't reschedule the appointment provided " + entity.id);
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
    public void completeAppointment(Long id) {
        AppointmentDTO toComplete = getById(id); // Will throw an exception if failed to find the id

        if (toComplete.status == Appointment.Status.CANCELLED) {
            throw new EntityUpdateException("Cannot complete a cancelled appointment");
        }

        toComplete.status = Appointment.Status.COMPLETED;

        try {
            apptRepo.save(apptMapper.toEntity(toComplete));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityUpdateException("Couldn't complete the appointment provided " + id);
        }
    }

    /**
     * Cancels an appointment by setting its status to CANCELLED.
     * 
     * @param entity the appointment to cancel
     * @throws EntityNotFoundException if the appointment is not found
     * @throws DeletionFailedException if cancellation fails
     * @since 1.0
     */
    public void cancelAppointment(AppointmentDTO entity) {
        AppointmentDTO toCancel = getById(entity.id); // Will throw an exception if failed to find the id

        if (toCancel.status == Appointment.Status.COMPLETED) {
            throw new DeletionFailedException("Cannot cancel a completed appointment");
        }

        if (toCancel.status == Appointment.Status.CANCELLED) {
            throw new DeletionFailedException("Appointment is already cancelled");
        }

        toCancel.status = Appointment.Status.CANCELLED;

        try {
            apptRepo.save(apptMapper.toEntity(entity));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DeletionFailedException("Couldn't cancel the appointment provided " + entity.id);
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
        Optional<Appointment> appt = apptRepo.findById(id);
        if (appt.isPresent())
            return apptMapper.toDto(appt.get());
        throw new EntityNotFoundException("Cannot find the requested appointment");
    }
}
