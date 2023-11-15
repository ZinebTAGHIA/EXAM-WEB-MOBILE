package ma.taghia.exam_android.beans;

import java.util.Date;

public class Employe {
    private Long id;
    private String nom;
    private String prenom;
    private String date;
    private Service service;

    public Employe(Long id, String nom, String prenom, String date, Service service) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.date = date;
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
