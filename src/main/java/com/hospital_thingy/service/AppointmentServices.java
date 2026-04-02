package com.hospital_thingy.service;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.exception.appointment.AppointmentCreateException;
import com.hospital_thingy.exception.appointment.AppointmentDeleteException;
import com.hospital_thingy.exception.appointment.AppointmentNotFound;
import com.hospital_thingy.exception.appointment.AppointmentUpdateException;
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
     * @throws AppointmentCreateException if creation fails
     * @since 1.0
     */
    public void CreateAppointment(Appointment entity) {
        try { apptRepo.save(entity); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AppointmentCreateException("Couldn't create the appointment provided " + entity.getId());
        }
    }

    /**
     * Updates an existing appointment.
     * @param entity the appointment to update
     * @throws AppointmentNotFound if the appointment is not found
     * @throws AppointmentUpdateException if update fails
     * @since 1.0
     */
    public void UpdateAppointment(Appointment entity) {
        GetById(entity.getId()); // Will throw an exception if failed to find the id

        try { apptRepo.save(entity); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AppointmentUpdateException("Couldn't update the appointment provided " + entity.getId());
        }
    }

    /**
     * Cancels an appointment by setting its status to CANCELLED.
     * @param entity the appointment to cancel
     * @throws AppointmentNotFound if the appointment is not found
     * @throws AppointmentDeleteException if cancellation fails
     * @since 1.0
     */
    public void CancelAppointment(Appointment entity) {
        Appointment toCancel = GetById(entity.getId()); // Will throw an exception if failed to find the id

        toCancel.setStatus(Appointment.Status.CANCELLED);

        try { apptRepo.save(toCancel); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AppointmentDeleteException("Couldn't delete the appointment provided " + entity.getId());
        }
    }

    /**
     * Retrieves an appointment by its ID.
     * @param id the ID of the appointment
     * @return the appointment
     * @throws AppointmentNotFound if the appointment is not found
     * @since 1.0
     */
    public Appointment GetById(Long id) {
        Optional<Appointment> appt = apptRepo.findById(id);
        if (appt.isPresent())
            return appt.get();
        throw new AppointmentNotFound("Cannot find the requested appointment");
    }
}
