package com.ISAproject.hospitalequipment.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.Columns;

import java.io.Serializable;

@Entity
@Table(name="equipment_stock")
public class EquipmentStock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;


    @JoinColumn(name = "equipment_id")
    @ManyToOne
    Equipment equipment;


    @JoinColumn(name="company_id")
    @ManyToOne
    Company company;

    @Column(name = "amount")
    int amount;

    public EquipmentStock(Equipment equipment, Company company, int amount) {
        this.equipment = equipment;
        this.company = company;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public Company getCompany() {
        return company;
    }

    public int getAmount() {
        return amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
