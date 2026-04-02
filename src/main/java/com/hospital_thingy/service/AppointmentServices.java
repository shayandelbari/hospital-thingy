package com.hospital_thingy.service;

import com.hospital_thingy.DTO.AppointmentDTO;
import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.exception.DeletionFailedException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
import com.hospital_thingy.mapper.AppointmentMapper;
import com.hospital_thingy.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

/**
 * Represents the business logic layer (BLL) of the appointments.
 * Communicates with the {@link AppointmentRepository} to deliver sanitized interaction
 * with the data access layer (DAL). Like an API.
 *
 * @author Abdulrahman Mousa
 * @version 1.0
 * @since 2026-04-02
 */
public class AppointmentServices {
    final private AppointmentRepository apptRepo;
    final private AppointmentMapper apptMapper;

    /**
     * Constructs an AppointmentServices with the given repository.
     * @param repo the appointment repository
     * @param mapper the appointment mapper (DTO : Entity)
     */
    public AppointmentServices(AppointmentRepository repo, AppointmentMapper mapper) {
        apptRepo = repo;
        apptMapper = mapper;
    }

    /**
     * This method will use the repo's .findAll() method to retrieve the appointments from the db
     * and will return a list of all the appointments.
     * @return List of the appointments
     * @since 1.0
     */
    public List<AppointmentDTO> GetAll() {
        return apptMapper.toDtoList(apptRepo.findAll());
    }

    /**
     * Creates a new appointment.
     * @param entity the appointment to create
     * @throws EntityCreationException if creation fails
     * @since 1.0
     */
    public void CreateAppointment(AppointmentDTO entity) {
        try { apptRepo.save(apptMapper.toEntity(entity)); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityCreationException("Couldn't create the appointment provided " + entity.id);
        }
    }

    /**
     * Updates an existing appointment.
     * @param entity the appointment to update
     * @throws EntityNotFoundException if the appointment is not found
     * @throws EntityUpdateException if update fails
     * @since 1.0
     */
    public void UpdateAppointment(AppointmentDTO entity) {
        GetById(entity.id); // Will throw an exception if failed to find the id

        try { apptRepo.save(apptMapper.toEntity(entity)); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityUpdateException("Couldn't update the appointment provided " + entity.id);
        }
    }

    /**
     * Cancels an appointment by setting its status to CANCELLED.
     * @param entity the appointment to cancel
     * @throws EntityNotFoundException if the appointment is not found
     * @throws DeletionFailedException if cancellation fails
     * @since 1.0
     */
    public void CancelAppointment(AppointmentDTO entity) {
        AppointmentDTO toCancel = GetById(entity.id); // Will throw an exception if failed to find the id

        toCancel.status = Appointment.Status.CANCELLED;

        try { apptRepo.save(apptMapper.toEntity(entity)); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DeletionFailedException("Couldn't delete the appointment provided " + entity.id);
        }
    }

    /**
     * Retrieves an appointment by its ID.
     * @param id the ID of the appointment
     * @return the appointment
     * @throws EntityNotFoundException if the appointment is not found
     * @since 1.0
     */
    public AppointmentDTO GetById(Long id) {
        Optional<Appointment> appt = apptRepo.findById(id);
        if (appt.isPresent())
            return apptMapper.toDto(appt.get());
        throw new EntityNotFoundException("Cannot find the requested appointment");
    }
}
