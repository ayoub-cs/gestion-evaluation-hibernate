package ma.projet.classes;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="employe")
public class Employe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false) private String nom;
    @Column(nullable=false) private String prenom;
    private String telephone;

    @OneToMany(mappedBy="chefProjet")
    private List<Projet> projetsGeres = new ArrayList<>();

    public Employe() {}
    public Employe(String nom, String prenom, String telephone) {
        this.nom = nom; this.prenom = prenom; this.telephone = telephone;
    }

    public Integer getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }

    @Override public String toString() {
        return "Employe{id=" + id + ", nom='" + nom + "', prenom='" + prenom + "'}";
    }
}
