package com.taghia.exam.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Employe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private String photo;
    @ManyToOne
    private ServiceEntity serviceEntity;

    @ManyToOne
    private Employe chef;
    @OneToMany(mappedBy = "chef")
    @JsonIgnore
    private List<Employe> employes = new ArrayList<>();
}
