package ma.projet.classes;

import jakarta.persistence.*;

@Entity
@Table(name="ligne_commande_produit")
public class LigneCommandeProduit {
    @EmbeddedId
    private LigneCommandeProduitId id = new LigneCommandeProduitId();

    @ManyToOne(optional=false)
    @MapsId("commandeId")
    @JoinColumn(name="commande_id")
    private Commande commande;

    @ManyToOne(optional=false)
    @MapsId("produitId")
    @JoinColumn(name="produit_id")
    private Produit produit;

    @Column(nullable=false)
    private int quantite;

    public LigneCommandeProduit() {}
    public LigneCommandeProduit(Produit produit, int quantite) {
        this.produit = produit;
        this.quantite = quantite;
    }

    public LigneCommandeProduitId getId() { return id; }
    public Commande getCommande() { return commande; }
    public Produit getProduit() { return produit; }
    public int getQuantite() { return quantite; }

    public void setCommande(Commande commande) { this.commande = commande; }
    public void setProduit(Produit produit) { this.produit = produit; }

    @Override public String toString() {
        return "Ligne{prod=" + (produit!=null?produit.getReference():null) + ", qte=" + quantite + "}";
    }
}
