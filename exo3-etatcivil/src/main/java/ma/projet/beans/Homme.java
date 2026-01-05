package ma.projet.beans;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="homme")
public class Homme extends Personne {

    @OneToMany(mappedBy="homme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mariage> mariages = new ArrayList<>();

    public Homme() {}
    public Homme(String nom, String prenom, String tel, String adr, LocalDate dn) {
        super(nom, prenom, tel, adr, dn);
    }

    public List<Mariage> getMariages() { return mariages; }
}
