package com.hospital_thingy.service;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.exception.EntityCreationException;
import com.hospital_thingy.exception.EntityDeleteException;
import com.hospital_thingy.exception.EntityNotFoundException;
import com.hospital_thingy.exception.EntityUpdateException;
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

    /**
     * Constructs an AppointmentServices with the given repository.
     * @param repo the appointment repository
     */
    public AppointmentServices(AppointmentRepository repo) {
        apptRepo = repo;
    }

    /**
     * This method will use the repo's .findAll() method to retrieve the appointments from the db
     * and will return a list of all the appointments.
     * @return List of the appointments
     * @since 1.0
     */
    public List<Appointment> GetAll() {
        return apptRepo.findAll();
    }

    /**
     * Creates a new appointment.
     * @param entity the appointment to create
     * @throws EntityCreationException if creation fails
     * @since 1.0
     */
    public void CreateAppointment(Appointment entity) {
        try { apptRepo.save(entity); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityCreationException("Couldn't create the appointment provided " + entity.getId());
        }
    }

    /**
     * Updates an existing appointment.
     * @param entity the appointment to update
     * @throws EntityNotFoundException if the appointment is not found
     * @throws EntityUpdateException if update fails
     * @since 1.0
     */
    public void UpdateAppointment(Appointment entity) {
        GetById(entity.getId()); // Will throw an exception if failed to find the id

        try { apptRepo.save(entity); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityUpdateException("Couldn't update the appointment provided " + entity.getId());
        }
    }

    /**
     * Cancels an appointment by setting its status to CANCELLED.
     * @param entity the appointment to cancel
     * @throws EntityNotFoundException if the appointment is not found
     * @throws EntityDeleteException if cancellation fails
     * @since 1.0
     */
    public void CancelAppointment(Appointment entity) {
        Appointment toCancel = GetById(entity.getId()); // Will throw an exception if failed to find the id

        toCancel.setStatus(Appointment.Status.CANCELLED);

        try { apptRepo.save(toCancel); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new EntityDeleteException("Couldn't delete the appointment provided " + entity.getId());
        }
    }

    /**
     * Retrieves an appointment by its ID.
     * @param id the ID of the appointment
     * @return the appointment
     * @throws EntityNotFoundException if the appointment is not found
     * @since 1.0
     */
    public Appointment GetById(Long id) {
        Optional<Appointment> appt = apptRepo.findById(id);
        if (appt.isPresent())
            return appt.get();
        throw new EntityNotFoundException("Cannot find the requested appointment");
    }
}
