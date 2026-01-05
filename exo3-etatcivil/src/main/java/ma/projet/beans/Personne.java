package ma.projet.beans;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="personne")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Personne {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable=false) protected String nom;
    @Column(nullable=false) protected String prenom;
    protected String telephone;
    protected String adresse;
    protected LocalDate dateNaissance;

    public Personne() {}

    public Personne(String nom, String prenom, String telephone, String adresse, LocalDate dateNaissance) {
        this.nom = nom; this.prenom = prenom; this.telephone = telephone; this.adresse = adresse; this.dateNaissance = dateNaissance;
    }

    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public LocalDate getDateNaissance() { return dateNaissance; }

    @Override public String toString() {
        return nom + " " + prenom + " (id=" + id + ")";
    }
}
