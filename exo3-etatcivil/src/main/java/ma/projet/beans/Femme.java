package ma.projet.beans;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="femme")
@NamedNativeQueries({
        @NamedNativeQuery(
                name="Femme.nbEnfantsEntreDeuxDates",
                query="""
      select coalesce(sum(m.nbr_enfant),0)
      from mariage m
      where m.femme_id = :fid
        and m.date_debut between :d1 and :d2
    """,
                resultClass = Long.class
        )
})
@NamedQueries({
        @NamedQuery(
                name="Femme.marieeAuMoinsDeuxFois",
                query="""
      select f
      from Femme f
      where f.id in (
        select m.femme.id from Mariage m
        group by m.femme.id
        having count(m.id) >= 2
      )
    """
        )
})
public class Femme extends Personne {

    @OneToMany(mappedBy="femme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mariage> mariages = new ArrayList<>();

    public Femme() {}
    public Femme(String nom, String prenom, String tel, String adr, LocalDate dn) {
        super(nom, prenom, tel, adr, dn);
    }

    public List<Mariage> getMariages() { return mariages; }
}
