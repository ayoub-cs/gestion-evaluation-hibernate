package ma.projet.beans;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="mariage")
public class Mariage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="date_debut", nullable=false)
    private LocalDate dateDebut;

    @Column(name="date_fin")
    private LocalDate dateFin; // null => mariage en cours

    @Column(name="nbr_enfant", nullable=false)
    private int nbrEnfant;

    @ManyToOne(optional=false)
    @JoinColumn(name="homme_id")
    private Homme homme;

    @ManyToOne(optional=false)
    @JoinColumn(name="femme_id")
    private Femme femme;

    public Mariage() {}

    public Mariage(Homme homme, Femme femme, LocalDate dateDebut, LocalDate dateFin, int nbrEnfant) {
        this.homme = homme; this.femme = femme;
        this.dateDebut = dateDebut; this.dateFin = dateFin; this.nbrEnfant = nbrEnfant;
    }

    public Integer getId() { return id; }
    public LocalDate getDateDebut() { return dateDebut; }
    public LocalDate getDateFin() { return dateFin; }
    public int getNbrEnfant() { return nbrEnfant; }
    public Homme getHomme() { return homme; }
    public Femme getFemme() { return femme; }

    public boolean estEnCours() { return dateFin == null; }
}
