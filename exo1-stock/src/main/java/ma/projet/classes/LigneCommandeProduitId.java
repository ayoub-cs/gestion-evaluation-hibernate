package ma.projet.classes;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitId implements Serializable {
    private Integer commandeId;
    private Integer produitId;

    public LigneCommandeProduitId() {}
    public LigneCommandeProduitId(Integer commandeId, Integer produitId) {
        this.commandeId = commandeId; this.produitId = produitId;
    }

    public Integer getCommandeId() { return commandeId; }
    public Integer getProduitId() { return produitId; }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LigneCommandeProduitId that)) return false;
        return Objects.equals(commandeId, that.commandeId) && Objects.equals(produitId, that.produitId);
    }

    @Override public int hashCode() { return Objects.hash(commandeId, produitId); }
}
