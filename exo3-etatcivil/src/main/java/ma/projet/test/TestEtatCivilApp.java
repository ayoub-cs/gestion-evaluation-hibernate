package ma.projet.test;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;
import ma.projet.util.HibernateUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApplicationTest {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateUtil.class);

        FemmeService femmeService = context.getBean(FemmeService.class);
        HommeService hommeService = context.getBean(HommeService.class);
        MariageService mariageService = context.getBean(MariageService.class);

        System.out.println("=== SYSTÈME DE GESTION DES MARIAGES — TEST COMPLET ===");
        System.out.println("=".repeat(70));

        /* ===============================
           CRÉATION DES DONNÉES DE BASE
           =============================== */

        System.out.println("🚺 Initialisation de la population féminine...");
        List<Femme> femmes = creerFemmes();
        femmes.forEach(femmeService::create);

        System.out.println("🚹 Initialisation de la population masculine...");
        List<Homme> hommes = creerHommes();
        hommes.forEach(hommeService::create);

        System.out.println("✓ Population créée : " + femmes.size() + " femmes, " + hommes.size() + " hommes");

        /* ===============================
           CRÉATION DES MARIAGES
           =============================== */

        System.out.println("\n💒 Enregistrement des mariages...");
        List<Mariage> mariages = creerMariages(hommes, femmes);
        mariages.forEach(mariageService::create);

        System.out.println("✓ " + mariages.size() + " mariages enregistrés");

        /* ===============================
           ANALYSES ET RAPPORTS
           =============================== */

        afficherFemmesParAge(femmeService);
        afficherEpouses(hommeService, hommes.get(0), "Ahmed");
        afficherEpouses(hommeService, hommes.get(1), "Omar");

        analyserMaternite(femmeService, femmes.get(0), "Amina",
                LocalDate.of(2010, 1, 1), LocalDate.of(2025, 12, 31));

        afficherFemmesRemariees(femmeService);
        hommeService.afficherNombreHommesAvecQuatreFemmes(
                LocalDate.of(2000, 1, 1), LocalDate.of(2025, 12, 31));

        mariageService.afficherMariagesAvecDetails(hommes.get(0).getId());
        mariageService.afficherMariagesAvecDetails(hommes.get(1).getId());

        afficherStatistiquesGlobales(femmeService, hommeService, mariageService);

        System.out.println("\n=== TEST TERMINÉ AVEC SUCCÈS ===");
        context.close();
    }

    /* ===============================
       MÉTHODES UTILITAIRES
       =============================== */

    private static List<Femme> creerFemmes() {
        return Arrays.asList(
                new Femme("F001", "Amina", "Benali", LocalDate.of(1985,3,15),"0661234567","Casablanca"),
                new Femme("F002", "Khadija", "Alaoui", LocalDate.of(1990,7,22),"0662345678","Rabat"),
                new Femme("F003", "Fatima", "Tazi", LocalDate.of(1988,11,8),"0663456789","Fès"),
                new Femme("F004", "Aicha", "Cherif", LocalDate.of(1982,4,12),"0664567890","Marrakech"),
                new Femme("F005", "Laila", "Idrissi", LocalDate.of(1995,9,18),"0665678901","Agadir"),
                new Femme("F006", "Zineb", "Fassi", LocalDate.of(1975,12,3),"0666789012","Tanger"),
                new Femme("F007", "Meryem", "Hamidi", LocalDate.of(1992,6,25),"0667890123","Oujda"),
                new Femme("F008", "Nadia", "Zaki", LocalDate.of(1987,1,14),"0668901234","Meknes")
        );
    }

    private static List<Homme> creerHommes() {
        return Arrays.asList(
                new Homme("Ahmed","Bennani","0671234567","Casablanca"),
                new Homme("Omar","Alami","0672345678","Rabat"),
                new Homme("Youssef","Larbi","0673456789","Fès"),
                new Homme("Hassan","Kadiri","0674567890","Marrakech"),
                new Homme("Karim","Amrani","0675678901","Agadir")
        );
    }

    private static List<Mariage> creerMariages(List<Homme> h, List<Femme> f) {
        return Arrays.asList(
                new Mariage(h.get(0), f.get(0), LocalDate.of(2010,5,15), LocalDate.of(2015,8,20), 2),
                new Mariage(h.get(0), f.get(2), LocalDate.of(2016,3,10), null, 3),
                new Mariage(h.get(0), f.get(4), LocalDate.of(2020,7,25), null, 1),
                new Mariage(h.get(1), f.get(1), LocalDate.of(2012,9,5), LocalDate.of(2018,2,14), 1),
                new Mariage(h.get(1), f.get(3), LocalDate.of(2019,11,30), null, 2),
                new Mariage(h.get(1), f.get(6), LocalDate.of(2022,4,18), null, 0),
                new Mariage(h.get(2), f.get(5), LocalDate.of(2008,12,12), null, 4),
                new Mariage(h.get(3), f.get(7), LocalDate.of(2015,6,8), null, 2),
                new Mariage(h.get(3), f.get(0), LocalDate.of(2018,10,22), null, 1)
        );
    }

    private static void afficherFemmesParAge(FemmeService service) {
        List<Femme> femmes = service.findAll();
        femmes.sort((a,b) -> a.getDateNaissance().compareTo(b.getDateNaissance()));
        femmes.forEach(f ->
                System.out.println(f.getPrenom()+" "+f.getNom()+" — "+
                        Period.between(f.getDateNaissance(), LocalDate.now()).getYears()+" ans")
        );
    }

    private static void afficherEpouses(HommeService service, Homme h, String nom) {
        System.out.println("\nÉpouses de " + nom + " :");
        service.afficherEpousesEntreDates(h.getId(),
                LocalDate.of(2000,1,1),
                LocalDate.of(2025,12,31));
    }

    private static void analyserMaternite(FemmeService service, Femme f,
                                          String nom, LocalDate d1, LocalDate d2) {
        int nb = service.nombreEnfantsEntreDates(f.getId(), d1, d2);
        System.out.println("\nMaternité de " + nom + " : " + nb + " enfants");
    }

    private static void afficherFemmesRemariees(FemmeService service) {
        List<Femme> list = service.femmesMarieesAuMoinsDeuxFois();
        System.out.println("\nFemmes remariées :");
        list.forEach(f -> System.out.println(f.getPrenom()+" "+f.getNom()));
    }

    private static void afficherStatistiquesGlobales(FemmeService fs,
                                                     HommeService hs,
                                                     MariageService ms) {

        List<Femme> femmes = fs.findAll();
        List<Homme> hommes = hs.findAll();
        List<Mariage> mariages = ms.findAll();

        int enfants = mariages.stream().mapToInt(Mariage::getNbrEnfant).sum();
        long actifs = mariages.stream().filter(m -> m.getDateFin() == null).count();

        System.out.println("\n📊 STATISTIQUES GLOBALES");
        System.out.println("Population : " + (femmes.size() + hommes.size()));
        System.out.println("Mariages actifs : " + actifs);
        System.out.println("Enfants totaux : " + enfants);

        femmes.stream()
                .collect(Collectors.groupingBy(Femme::getAdresse, Collectors.counting()))
                .forEach((v,c) -> System.out.println(v + " : " + c + " femmes"));
    }
}
