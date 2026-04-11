package com.hospital_thingy.repository;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.MedicalRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

/**
 * Represents the data access layer through the use of spring boot's Jpa
 * and entity objects which essentially translates SQL queries into
 * runnable methods. Built-in JpaRepository should suffice for now.
 *
 * @author Abdulrahman Mousa
 * @version 1.0
 * @since 2026-04-02
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
  /*
   * InBuilt CRUD Fns:
   * save(MedicalRecord); → create
   * findById(id); → read and return one Medical Record
   * findAll(); → read and return ALL Medical Records (as List)
   * deleteById(id) → delete
   */
  List<Appointment> findByDoctorId(Long doctorId);

  List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);

  @Query("""
      SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
      FROM Appointment a
      WHERE a.doctor.id = :doctorId
        AND a.date = :date
        AND a.status IN :blockedStatuses
        AND (:excludeAppointmentId IS NULL OR a.id <> :excludeAppointmentId)
        AND a.startTime < :requestedEnd
        AND a.endTime > :requestedStart
      """)
  boolean existsDoctorDoubleBooking(
      @Param("doctorId") Long doctorId,
      @Param("date") LocalDate date,
      @Param("requestedStart") LocalTime requestedStart,
      @Param("requestedEnd") LocalTime requestedEnd,
      @Param("excludeAppointmentId") Long excludeAppointmentId,
      @Param("blockedStatuses") Set<Appointment.Status> blockedStatuses);

  @Query("SELECT m FROM MedicalRecord m WHERE m.appointment.id = :appointmentId")
  List<MedicalRecord> findMedicalRecordsById(Long appointmentId);
}
