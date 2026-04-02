package com.hospital_thingy.service;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.exception.appointment.AppointmentCreateException;
import com.hospital_thingy.exception.appointment.AppointmentDeleteException;
import com.hospital_thingy.exception.appointment.AppointmentNotFound;
import com.hospital_thingy.exception.appointment.AppointmentUpdateException;
import com.hospital_thingy.repository.AppointmentRepository;

import java.util.List;
import java.util.Optional;

public class AppointmentServices {
    final private AppointmentRepository apptRepo;

    public AppointmentServices(AppointmentRepository repo) {
        apptRepo = repo;
    }

    public List<Appointment> GetAll() {
        return apptRepo.findAll();
    }

    public void CreateAppointment(Appointment entity) {
        try { apptRepo.save(entity); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AppointmentCreateException("Couldn't create the appointment provided " + entity.getId());
        }
    }

    public void UpdateAppointment(Appointment entity) {
        GetById(entity.getId()); // Will throw an exception if failed to find the id

        try { apptRepo.save(entity); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AppointmentUpdateException("Couldn't update the appointment provided " + entity.getId());
        }
    }

    public void DeleteAppointment(Appointment entity) {
        GetById(entity.getId()); // Will throw an exception if failed to find the id

        try { apptRepo.delete(entity); }
        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new AppointmentDeleteException("Couldn't delete the appointment provided " + entity.getId());
        }
    }

    public Appointment GetById(Long id) {
        Optional<Appointment> appt = apptRepo.findById(id);
        if (appt.isPresent())
            return appt.get();
        throw new AppointmentNotFound("Cannot find the requested appointment");
    }
}
