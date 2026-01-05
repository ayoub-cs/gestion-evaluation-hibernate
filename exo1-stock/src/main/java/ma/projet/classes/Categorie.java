package ma.projet.classes;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false, unique=true)
    private String code;

    @Column(nullable=false)
    private String libelle;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produit> produits = new ArrayList<>();

    public Categorie() {}
    public Categorie(String code, String libelle) { this.code = code; this.libelle = libelle; }

    public Integer getId() { return id; }
    public String getCode() { return code; }
    public String getLibelle() { return libelle; }
    public List<Produit> getProduits() { return produits; }

    public void addProduit(Produit p) { produits.add(p); p.setCategorie(this); }

    @Override public String toString() {
        return "Categorie{id=" + id + ", code='" + code + "', libelle='" + libelle + "'}";
    }
}
