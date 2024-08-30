package com.example.smartgarage.models;

import com.example.smartgarage.models.enums.CarClass;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "vin")
    private String vin;

    @Column(name = "brand_id")
    private int brand;

    @Column(name = "model_id")
    private int model;

    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(name = "class_id")
    private int carClass;
}
