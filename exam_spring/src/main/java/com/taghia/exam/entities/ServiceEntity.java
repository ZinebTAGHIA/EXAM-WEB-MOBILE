package com.taghia.exam.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Service")
public class ServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
}
