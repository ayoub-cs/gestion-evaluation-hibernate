package ma.projet.test;

import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class ScenarioTestAvance {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        EmployeService employeService = context.getBean(EmployeService.class);
        EmployeTacheService employeTacheService = context.getBean(EmployeTacheService.class);
        ProjetService projetService = context.getBean(ProjetService.class);
        TacheService tacheService = context.getBean(TacheService.class);

        System.out.println("=== INITIALISATION DES DONNÉES — SCÉNARIO DE TEST AVANCÉ ===");

        /* =========================
           1. CONSTITUTION DE L’ÉQUIPE
           ========================= */

        Employe chefProjet = creerEmploye("Alami", "Mohamed", "0661234567", employeService);
        Employe devSenior  = creerEmploye("Bennani", "Fatima", "0662345678", employeService);
        Employe devJunior  = creerEmploye("El Idrissi", "Youssef", "0663456789", employeService);
        Employe designer   = creerEmploye("Tazi", "Aicha", "0664567890", employeService);
        Employe testeur    = creerEmploye("Hamidi", "Omar", "0665678901", employeService);

        System.out.println("✓ Équipe créée : 5 employés");

        /* =========================
           2. CRÉATION DES PROJETS
           ========================= */

        Projet ecommerce = creerProjet(
                "Plateforme E-commerce",
                chefProjet,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 8, 30),
                projetService
        );

        Projet mobile = creerProjet(
                "Application Mobile Banking",
                devSenior,
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 7, 15),
                projetService
        );

        Projet web = creerProjet(
                "Site Web Corporate",
                designer,
                LocalDate.of(2024, 5, 1),
                LocalDate.of(2024, 6, 30),
                projetService
        );

        Projet maintenance = creerProjet(
                "Maintenance Systèmes",
                devSenior,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                projetService
        );

        System.out.println("✓ 4 projets enregistrés");

        /* =========================
           3. DÉFINITION DES TÂCHES
           ========================= */

        Tache analyse = creerTache(
                "Analyse des Exigences",
                ecommerce, 2500,
                LocalDate.of(2024, 1, 20),
                LocalDate.of(2024, 2, 10),
                tacheService
        );

        Tache conception = creerTache(
                "Conception Base de Données",
                ecommerce, 1800,
                LocalDate.of(2024, 2, 11),
                LocalDate.of(2024, 3, 5),
                tacheService
        );

        Tache backend = creerTache(
                "Développement Backend",
                ecommerce, 4500,
                LocalDate.of(2024, 3, 6),
                null,
                tacheService
        );

        Tache frontend = creerTache(
                "Développement Frontend",
                ecommerce, 3500,
                LocalDate.of(2024, 5, 1),
                null,
                tacheService
        );

        Tache uiMobile = creerTache(
                "Design Interface Utilisateur",
                mobile, 1200,
                LocalDate.of(2024, 3, 5),
                LocalDate.of(2024, 3, 25),
                tacheService
        );

        Tache devMobile = creerTache(
                "Développement Mobile",
                mobile, 3200,
                LocalDate.of(2024, 3, 26),
                null,
                tacheService
        );

        Tache designWeb = creerTache(
                "Design Site Web",
                web, 800,
                LocalDate.of(2024, 5, 5),
                LocalDate.of(2024, 5, 20),
                tacheService
        );

        Tache integration = creerTache(
                "Intégration Web",
                web, 950,
                LocalDate.of(2024, 5, 21),
                null,
                tacheService
        );

        Tache bugs = creerTache(
                "Correction Bugs Critiques",
                maintenance, 450,
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 2, 15),
                tacheService
        );

        Tache optimisation = creerTache(
                "Optimisation Performance",
                maintenance, 750,
                LocalDate.of(2024, 4, 1),
                null,
                tacheService
        );

        System.out.println("✓ 10 tâches créées");

        /* =========================
           4. AFFECTATIONS RÉELLES
           ========================= */

        affecter(chefProjet, analyse,
                LocalDate.of(2024, 1, 20),
                LocalDate.of(2024, 2, 10),
                employeTacheService
        );

        affecter(devSenior, backend,
                LocalDate.of(2024, 3, 6),
                null,
                employeTacheService
        );

        affecter(designer, uiMobile,
                LocalDate.of(2024, 3, 5),
                LocalDate.of(2024, 3, 25),
                employeTacheService
        );

        affecter(devJunior, conception,
                LocalDate.of(2024, 2, 11),
                LocalDate.of(2024, 3, 5),
                employeTacheService
        );

        affecter(designer, designWeb,
                LocalDate.of(2024, 5, 5),
                LocalDate.of(2024, 5, 20),
                employeTacheService
        );

        affecter(devSenior, bugs,
                LocalDate.of(2024, 2, 1),
                LocalDate.of(2024, 2, 15),
                employeTacheService
        );

        affecter(testeur, devMobile,
                LocalDate.of(2024, 4, 1),
                null,
                employeTacheService
        );

        System.out.println("✓ 7 affectations employé–tâche créées");

        /* =========================
           5. TESTS & SYNTHÈSE
           ========================= */

        employeService.afficherTachesParEmploye(devSenior.getId());
        employeService.afficherProjetsGeresParEmploye(chefProjet.getId());

        projetService.afficherTachesPlanifieesParProjet(ecommerce.getId());
        projetService.afficherTachesRealiseesParProjet(ecommerce.getId());

        tacheService.afficherTachesPrixSuperieurA1000();
        tacheService.afficherTachesEntreDates(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 3, 31)
        );

        System.out.println("=== SCÉNARIO DE TEST AVANCÉ TERMINÉ AVEC SUCCÈS ===");
        context.close();
    }

    /* =========================
       MÉTHODES UTILITAIRES
       ========================= */

    private static Employe creerEmploye(String nom, String prenom, String tel, EmployeService service) {
        Employe e = new Employe();
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setTelephone(tel);
        service.create(e);
        return e;
    }

    private static Projet creerProjet(String nom, Employe chef,
                                      LocalDate debut, LocalDate fin,
                                      ProjetService service) {
        Projet p = new Projet();
        p.setNom(nom);
        p.setChef(chef);
        p.setDateDebut(debut);
        p.setDateFin(fin);
        service.create(p);
        return p;
    }

    private static Tache creerTache(String nom, Projet projet, double prix,
                                    LocalDate debut, LocalDate fin,
                                    TacheService service) {
        Tache t = new Tache();
        t.setNom(nom);
        t.setProjet(projet);
        t.setPrix(prix);
        t.setDateDebut(debut);
        t.setDateFin(fin);
        service.create(t);
        return t;
    }

    private static void affecter(Employe e, Tache t,
                                 LocalDate debut, LocalDate fin,
                                 EmployeTacheService service) {
        EmployeTache et = new EmployeTache();
        et.setEmploye(e);
        et.setTache(t);
        et.setDateDebutReelle(debut);
        et.setDateFinReelle(fin);
        service.create(et);
    }
}
