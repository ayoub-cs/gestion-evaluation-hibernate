package ma.projet.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "produit")
@NamedQueries({
        @NamedQuery(
                name = "Produit.prixSuperieurA100",
                query = "select p from Produit p where p.prix > 100"
        )
})
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true)
    private String reference;

    @Column(nullable=false)
    private double prix;

    @ManyToOne(optional=false)
    @JoinColumn(name="categorie_id")
    private Categorie categorie;

    public Produit() {}
    public Produit(String reference, double prix) { this.reference = reference; this.prix = prix; }

    public Integer getId() { return id; }
    public String getReference() { return reference; }
    public double getPrix() { return prix; }
    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    @Override public String toString() {
        return "Produit{id=" + id + ", ref='" + reference + "', prix=" + prix +
                ", cat=" + (categorie != null ? categorie.getCode() : null) + "}";
    }
}
