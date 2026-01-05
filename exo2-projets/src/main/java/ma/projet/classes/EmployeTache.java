package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="employe_tache")
public class EmployeTache {
    @EmbeddedId
    private EmployeTacheId id = new EmployeTacheId();

    @ManyToOne(optional=false) @MapsId("employeId")
    @JoinColumn(name="employe_id")
    private Employe employe;

    @ManyToOne(optional=false) @MapsId("tacheId")
    @JoinColumn(name="tache_id")
    private Tache tache;

    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    public EmployeTache() {}
    public EmployeTache(Employe e, Tache t, LocalDate ddr, LocalDate dfr) {
        this.employe = e; this.tache = t; this.dateDebutReelle = ddr; this.dateFinReelle = dfr;
    }

    public Employe getEmploye() { return employe; }
    public Tache getTache() { return tache; }
    public LocalDate getDateDebutReelle() { return dateDebutReelle; }
    public LocalDate getDateFinReelle() { return dateFinReelle; }
}
