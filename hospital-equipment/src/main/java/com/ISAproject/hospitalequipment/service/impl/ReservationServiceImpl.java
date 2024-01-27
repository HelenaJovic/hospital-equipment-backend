package com.ISAproject.hospitalequipment.service.impl;

import com.ISAproject.hospitalequipment.domain.*;
import com.ISAproject.hospitalequipment.repository.ReservationRepo;
import com.ISAproject.hospitalequipment.service.AppointmentService;
import com.ISAproject.hospitalequipment.service.EmailService;
import com.ISAproject.hospitalequipment.service.QRCodeService;
import com.ISAproject.hospitalequipment.service.ReservationService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepo reservationRepo;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
   private EmailService emailService;

    public List<Reservation> getAll()
    {
        return reservationRepo.findAll();
    }

    public Boolean isReservationTaken(int idAppointment){
        return reservationRepo.isReservationTaken(idAppointment);
    }

    public Reservation getLast() {
        List<Reservation> reservations = reservationRepo.findAll();

        if (!reservations.isEmpty()) {
            return reservations.get(reservations.size() - 1);
        } else {
            return null;
        }
    }


    public void getDataForQRCode() throws IOException, WriterException {
        List<Reservation> reservations = reservationRepo.findAll();


            Reservation res = reservations.get(reservations.size() - 1);

            Appointment appointment = res.getAppointment();

         //termini
        String startTime = appointment.getStartTime().toString();
        String endTime = appointment.getEndTime().toString();
        String date = appointment.getDate().toString();

        User user = res.getRegisteredUser();

        //korisnik koji preuzima
        String name = user.getFirstname();
        String surname = user.getLastname();

        //admin kompanije
        CompanyAdministrator admin = appointment.getAdministrator();
        String adminName = admin.getFirstname();
        String adminSurname = admin.getLastname();

        //kompanija
        Company company = admin.getCompany();
        String companyName = company.getName();


        String allText="Date" + date + "\n"+
                startTime + " - " + endTime + "\n" +
                "User: " + name + " " + surname + "\n" +
                "Admin: " + adminName + " " + adminSurname + "\n" +
                "Company: " + companyName;



         emailService.SendEmailWithQRCode(allText,user);



    }

    public List<Reservation> findByRegisteredUserId(Long userId){
        return reservationRepo.findByRegisteredUserId(userId);
    }


    public List<Map<String, Object>> getDataForUserQRCode(Long userId,String status) throws WriterException, IOException {
        List<Reservation> reservations = findByRegisteredUserId(userId);
        List<Reservation> filteredRes=new ArrayList<>();
        List<Map<String, Object>> reservationDataList = new ArrayList<>();
        if (status == null || status.trim().isEmpty()) {
            filteredRes = reservations;
        } else {
            for (Reservation res : reservations) {
                if (res.getReservationStatus().toString().equals(status)) {
                    filteredRes.add(res);
                }

            }
        }
        for (Reservation res : filteredRes) {

            Appointment appointment = res.getAppointment();

            //rezervacija


            //termini
            String startTime = appointment.getStartTime().toString();
            String endTime = appointment.getEndTime().toString();
            String date = appointment.getDate().toString();

            User user = res.getRegisteredUser();

            //korisnik koji preuzima
            String name = user.getFirstname();
            String surname = user.getLastname();

            //admin kompanije
            CompanyAdministrator admin = appointment.getAdministrator();
            String adminName = admin.getFirstname();
            String adminSurname = admin.getLastname();

            //kompanija
            Company company = admin.getCompany();
            String companyName = company.getName();

            String allText="Date " + date + "\n"+
                    startTime + " - " + endTime + "\n" +
                    "User: " + name + " " + surname + "\n" +
                    "Admin: " + adminName + " " + adminSurname + "\n" +
                    "Company: " + companyName;

            byte[] qrCodeBytes = qrCodeService.getQRCodeImage(allText, 300, 300);
            String qrCodeBase64 = Base64.getEncoder().encodeToString(qrCodeBytes);



            Map<String, Object> reservationData = new HashMap<>();
            reservationData.put("qrCodeBase64", qrCodeBase64);
            reservationDataList.add(reservationData);
        }

        return reservationDataList;
    }


    public Reservation save(Reservation reservation)
    {
        List<Appointment> appointmentList=appointmentService.findAll();
        Appointment lastAppointment = appointmentList.get(appointmentList.size() - 1);
        reservation.setAppointment(lastAppointment);

        return reservationRepo.save(reservation);
    }

    public Reservation create(Reservation reservation){
        return  reservationRepo.save(reservation);
    }

    public Reservation getById(Long id){
        return reservationRepo.findById(id).get();
    }

    public Reservation saveReservation(Reservation reservation)
    {

        return reservationRepo.save(reservation);
    }



}
