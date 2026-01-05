package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="projet")
public class Projet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false) private String nom;
    private LocalDate dateDebut;
    private LocalDate dateFin;

    @ManyToOne(optional=false)
    @JoinColumn(name="chef_projet_id")
    private Employe chefProjet;

    @OneToMany(mappedBy="projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> taches = new ArrayList<>();

    public Projet() {}
    public Projet(String nom, LocalDate dateDebut, LocalDate dateFin, Employe chefProjet) {
        this.nom = nom; this.dateDebut = dateDebut; this.dateFin = dateFin; this.chefProjet = chefProjet;
    }

    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public LocalDate getDateDebut() { return dateDebut; }
    public Employe getChefProjet() { return chefProjet; }

    @Override public String toString() {
        return "Projet{id=" + id + ", nom='" + nom + "', dateDebut=" + dateDebut + "}";
    }
}
