package ma.projet.test;

import ma.projet.beans.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class TestEtatCivilApp {
    public static void main(String[] args) {
        FemmeService femmeService = new FemmeService();
        HommeService hommeService = new HommeService();
        MariageService mariageService = new MariageService();

        // 1) Créer 10 femmes
        Femme[] femmes = new Femme[10];
        for (int i = 0; i < 10; i++) {
            femmes[i] = femmeService.create(new Femme(
                    "FEMME" + i, "PRENOM" + i, "06" + i, "ADR" + i,
                    LocalDate.of(1970 + i, 1, 1)
            ));
        }

        // 2) Créer 5 hommes
        Homme[] hommes = new Homme[5];
        for (int i = 0; i < 5; i++) {
            hommes[i] = hommeService.create(new Homme(
                    "HOMME" + i, "PRENOM" + i, "07" + i, "ADR" + i,
                    LocalDate.of(1960 + i, 1, 1)
            ));
        }

        // Mariages pour tests (dont un homme avec 4 femmes dans une période)
        // Homme0 -> 4 femmes entre 1990 et 2000
        mariageService.create(new Mariage(hommes[0], femmes[0], LocalDate.of(1990,9,3), null, 4));
        mariageService.create(new Mariage(hommes[0], femmes[1], LocalDate.of(1995,9,3), null, 2));
        mariageService.create(new Mariage(hommes[0], femmes[2], LocalDate.of(2000,11,4), null, 3));
        mariageService.create(new Mariage(hommes[0], femmes[3], LocalDate.of(1992,5,10), LocalDate.of(1993,5,10), 0));

        // femme4 mariée 2 fois (pour NamedQuery)
        mariageService.create(new Mariage(hommes[1], femmes[4], LocalDate.of(2001,1,1), LocalDate.of(2002,1,1), 1));
        mariageService.create(new Mariage(hommes[2], femmes[4], LocalDate.of(2003,1,1), null, 2));

        // A) Afficher la liste des femmes
        System.out.println("\nListe des femmes:");
        femmeService.findAll().forEach(System.out::println);

        // B) Afficher la femme la plus âgée
        System.out.println("\nFemme la plus âgée:");
        System.out.println(femmeService.femmePlusAgee());

        // C) Afficher les épouses d’un homme donné
        System.out.println("\nÉpouses de Homme0:");
        hommeService.epousesEntreDeuxDates(hommes[0].getId(), LocalDate.of(1980,1,1), LocalDate.of(2025,1,1))
                .forEach(f -> System.out.println(f.getNom() + " " + f.getPrenom()));

        // D) Nombre d’enfants d’une femme entre 2 dates (native)
        System.out.println("\nNbr enfants femme0 entre 1989 et 1996:");
        System.out.println(femmeService.nbEnfantsEntreDeuxDates(femmes[0].getId(), LocalDate.of(1989,1,1), LocalDate.of(1996,12,31)));

        // E) Femmes mariées >= 2 fois
        System.out.println("\nFemmes mariées au moins 2 fois:");
        femmeService.femmesMarieesAuMoinsDeuxFois().forEach(System.out::println);

        // F) Hommes mariés à 4 femmes entre 1989 et 2000 (Criteria)
        System.out.println("\nNombre d'hommes mariés à 4 femmes entre 1989 et 2000:");
        System.out.println(mariageService.nbHommesMarieAQuatreFemmesEntre(LocalDate.of(1989,1,1), LocalDate.of(2000,12,31)));

        // G) Afficher les mariages d’un homme avec détails (format demandé)
        System.out.println("\nixNom : " + hommes[0].getNom() + " " + hommes[0].getPrenom());
        List<Object[]> det = hommeService.mariagesDetails(hommes[0].getId());

        System.out.println("Mariages En Cours :");
        int idx = 1;
        for (Object[] r : det) {
            String nomF = (String) r[0]; String prenomF = (String) r[1];
            LocalDate dDebut = (LocalDate) r[2]; LocalDate dFin = (LocalDate) r[3];
            int nb = (int) r[4];
            if (dFin == null) {
                System.out.println(idx++ + ". Femme : " + nomF + " " + prenomF +
                        "   Date Début : " + dDebut + "    Nbr Enfants : " + nb);
            }
        }

        System.out.println("\nMariages échoués :");
        idx = 1;
        for (Object[] r : det) {
            String nomF = (String) r[0]; String prenomF = (String) r[1];
            LocalDate dDebut = (LocalDate) r[2]; LocalDate dFin = (LocalDate) r[3];
            int nb = (int) r[4];
            if (dFin != null) {
                System.out.println(idx++ + ". Femme : " + nomF + " " + prenomF +
                        "  Date Début : " + dDebut + "  Date Fin : " + dFin +
                        "  Nbr Enfants : " + nb);
            }
        }

        HibernateUtil.shutdown();
    }
}
