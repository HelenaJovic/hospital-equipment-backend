package com.ISAproject.hospitalequipment.repository;

import com.ISAproject.hospitalequipment.domain.Appointment;
import com.ISAproject.hospitalequipment.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment,Long> {
    @Query("SELECT a FROM Appointment a " +
            "WHERE a.company.id = :companyId " +
            "AND a.date = :date " +
            "AND (a.appointmentStatus = 'PREDEFINED' OR a.appointmentStatus ='EXTRAORDINARY')")
    List<Appointment> findFreeAppointmentsByCompanyAndDate(
            @Param("companyId") Long companyId,
            @Param("date") LocalDate date
    );


}
