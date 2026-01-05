package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class TestProjetsApp {
    public static void main(String[] args) {
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService etService = new EmployeTacheService();

        Employe chef = employeService.create(new Employe("apache", "Projet", "0600000000"));
        Projet proj = projetService.create(new Projet("Gestion de stock",
                LocalDate.of(2013,1,14), LocalDate.of(2013,5,1), chef));

        Tache t1 = tacheService.create(new Tache("Analyse", LocalDate.of(2013,2,1), LocalDate.of(2013,2,20), 800, proj));
        Tache t2 = tacheService.create(new Tache("Conception", LocalDate.of(2013,3,1), LocalDate.of(2013,3,15), 1200, proj));
        Tache t3 = tacheService.create(new Tache("Développement", LocalDate.of(2013,4,10), LocalDate.of(2013,4,25), 3000, proj));

        Employe e1 = employeService.create(new Employe("ALI", "Hassan", "0611111111"));

        etService.create(new EmployeTache(e1, t1, LocalDate.of(2013,2,10), LocalDate.of(2013,2,20)));
        etService.create(new EmployeTache(e1, t2, LocalDate.of(2013,3,10), LocalDate.of(2013,3,15)));
        etService.create(new EmployeTache(e1, t3, LocalDate.of(2013,4,10), LocalDate.of(2013,4,25)));

        System.out.println("\napacheProjet : " + proj.getId() + "      Nom : " + proj.getNom() +
                "     Date début : " + proj.getDateDebut());

        System.out.println("Liste des tâches:");
        System.out.println("Num  Nom            Date Début Réelle   Date Fin Réelle");

        List<Object[]> rows = projetService.tachesRealiseesAvecDatesReelles(proj.getId());
        for (Object[] r : rows) {
            System.out.printf("%-4s %-14s %-16s %-16s%n", r[0], r[1], r[2], r[3]);
        }

        System.out.println("\nTâches prix > 1000 (NamedQuery):");
        tacheService.tachesPrixSuperieurA1000().forEach(System.out::println);

        System.out.println("\nTâches réalisées entre 2013-03-01 et 2013-04-30:");
        tacheService.tachesRealiseesEntre(LocalDate.of(2013,3,1), LocalDate.of(2013,4,30))
                .forEach(r -> System.out.println(r[0] + " " + r[1] + " " + r[2] + " " + r[3]));

        HibernateUtil.shutdown();
    }
}
