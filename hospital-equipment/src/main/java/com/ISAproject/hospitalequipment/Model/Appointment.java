package com.ISAproject.hospitalequipment.Model;

import jakarta.persistence.Entity;

import java.util.Date;


public class Appointment {
    public Long Id;
    public String AdminName;
    public String AdminLastName;
    public Date Date;
    public Integer Duration;
}
