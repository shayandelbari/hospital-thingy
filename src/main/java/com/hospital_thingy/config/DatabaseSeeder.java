package com.hospital_thingy.config;

import com.hospital_thingy.entity.Appointment;
import com.hospital_thingy.entity.Doctor;
import com.hospital_thingy.entity.Patient;
import com.hospital_thingy.entity.VitalSign;
import com.hospital_thingy.repository.AppointmentRepository;
import com.hospital_thingy.repository.DoctorRepository;
import com.hospital_thingy.repository.MedicalRecordRepository;
import com.hospital_thingy.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Seeds the database with test data on application startup.
 *
 * This component loads sample patients, doctors, and appointments.
 *
 * To disable seeding for production:
 * - Set spring.seeder.enabled=false in application.properties
 * - Or set SPRING_SEEDER_ENABLED=false in environment variables
 */
@Component
public class DatabaseSeeder implements CommandLineRunner {

        private final PatientRepository patientRepository;
        private final DoctorRepository doctorRepository;
        private final AppointmentRepository appointmentRepository;
        private final MedicalRecordRepository medicalRecordRepository;

        @Value("${spring.seeder.enabled:true}")
        private boolean seederEnabled;

        public DatabaseSeeder(PatientRepository patientRepository,
                        DoctorRepository doctorRepository,
                        AppointmentRepository appointmentRepository,
                        MedicalRecordRepository medicalRecordRepository) {
                this.patientRepository = patientRepository;
                this.doctorRepository = doctorRepository;
                this.appointmentRepository = appointmentRepository;
                this.medicalRecordRepository = medicalRecordRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                if (!seederEnabled) {
                        System.out.println("Database seeding is disabled.");
                        return;
                }

                // Only seed if database is empty
                if (patientRepository.count() > 0) {
                        System.out.println("Database already contains data. Skipping seeding.");
                        return;
                }

                System.out.println("Seeding database with test data...");

                // Create patients
                Patient patient1 = new Patient("John", "Doe", LocalDate.of(1990, 5, 15),
                                5551234567L, "INS123456");

                Patient patient2 = new Patient("Jane", "Smith", LocalDate.of(1985, 8, 20),
                                5559876543L, "INS789012");

                Patient patient3 = new Patient("Robert", "Johnson", LocalDate.of(1978, 3, 10),
                                5555555555L, "INS345678");

                patientRepository.saveAll(Arrays.asList(patient1, patient2, patient3));
                System.out.println("✓ Created 3 patients");

                // Create doctors
                Doctor doctor1 = new Doctor();
                doctor1.setFirstName("Dr. Sarah");
                doctor1.setLastName("Williams");
                doctor1.setLicenseNumber("MD001");
                doctor1.setSpecialities(new HashSet<>(Arrays.asList(
                                Doctor.Speciality.CARDIOLOGY,
                                Doctor.Speciality.ORTHOPEDICS)));

                Doctor doctor2 = new Doctor();
                doctor2.setFirstName("Dr. Michael");
                doctor2.setLastName("Brown");
                doctor2.setLicenseNumber("MD002");
                doctor2.setSpecialities(new HashSet<>(Arrays.asList(
                                Doctor.Speciality.NEUROLOGY,
                                Doctor.Speciality.PSYCHIATRY)));

                Doctor doctor3 = new Doctor();
                doctor3.setFirstName("Dr. Emily");
                doctor3.setLastName("Davis");
                doctor3.setLicenseNumber("MD003");
                doctor3.setSpecialities(new HashSet<>(Arrays.asList(
                                Doctor.Speciality.PEDIATRICS,
                                Doctor.Speciality.GYNECOLOGY)));

                doctorRepository.saveAll(Arrays.asList(doctor1, doctor2, doctor3));
                System.out.println("✓ Created 3 doctors");

                // Create appointments
                Appointment appt1 = new Appointment();
                appt1.setDate(LocalDate.of(2026, 4, 15));
                appt1.setStartTime(LocalTime.of(9, 0));
                appt1.setEndTime(LocalTime.of(10, 0));
                appt1.setStatus(Appointment.Status.UPCOMING);
                appt1.setReasonForVisit("Annual checkup");
                appt1.setPatient(patient1);
                appt1.setDoctor(doctor1);

                Appointment appt2 = new Appointment();
                appt2.setDate(LocalDate.of(2026, 4, 16));
                appt2.setStartTime(LocalTime.of(10, 30));
                appt2.setEndTime(LocalTime.of(11, 30));
                appt2.setStatus(Appointment.Status.UPCOMING);
                appt2.setReasonForVisit("Back pain consultation");
                appt2.setPatient(patient2);
                appt2.setDoctor(doctor1);

                Appointment appt3 = new Appointment();
                appt3.setDate(LocalDate.of(2026, 4, 17));
                appt3.setStartTime(LocalTime.of(14, 0));
                appt3.setEndTime(LocalTime.of(15, 0));
                appt3.setStatus(Appointment.Status.UPCOMING);
                appt3.setReasonForVisit("Headache evaluation");
                appt3.setPatient(patient3);
                appt3.setDoctor(doctor2);

                Appointment appt4 = new Appointment();
                appt4.setDate(LocalDate.of(2026, 4, 20));
                appt4.setStartTime(LocalTime.of(11, 0));
                appt4.setEndTime(LocalTime.of(12, 0));
                appt4.setStatus(Appointment.Status.COMPLETED);
                appt4.setReasonForVisit("Routine vaccination");
                appt4.setPatient(patient1);
                appt4.setDoctor(doctor3);

                List<Appointment> savedAppointments = appointmentRepository
                                .saveAll(Arrays.asList(appt1, appt2, appt3, appt4));
                System.out.println("✓ Created 4 appointments");

                // Create medical records (vital signs) linked to appointments
                VitalSign vitals1 = new VitalSign("Routine annual vitals", 78, 72, 118, 76, 37, 98);
                vitals1.setAppointment(savedAppointments.get(0));

                VitalSign vitals2 = new VitalSign("Back pain intake vitals", 92, 84, 130, 84, 37, 97);
                vitals2.setAppointment(savedAppointments.get(1));

                VitalSign vitals3 = new VitalSign("Headache evaluation vitals", 85, 88, 124, 80, 37, 99);
                vitals3.setAppointment(savedAppointments.get(2));

                VitalSign vitals4 = new VitalSign("Post vaccination follow-up", 79, 70, 116, 74, 37, 99);
                vitals4.setAppointment(savedAppointments.get(3));

                medicalRecordRepository.saveAll(Arrays.asList(vitals1, vitals2, vitals3, vitals4));
                System.out.println("✓ Created 4 medical records (vital signs)");

                System.out.println("✓ Database seeding completed successfully!");
        }
}
