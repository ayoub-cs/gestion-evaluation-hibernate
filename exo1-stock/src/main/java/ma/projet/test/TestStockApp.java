package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import ma.projet.util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class TestStockApp {
    public static void main(String[] args) {
        CategorieService categorieService = new CategorieService();
        ProduitService produitService = new ProduitService();
        CommandeService commandeService = new CommandeService();
        LigneCommandeService ligneService = new LigneCommandeService();

        // 1) Catégories
        Categorie c1 = categorieService.create(new Categorie("PC", "Informatique"));
        Categorie c2 = categorieService.create(new Categorie("ACC", "Accessoires"));

        // 2) Produits
        Produit p1 = new Produit("ES12", 120);
        p1.setCategorie(c1);
        Produit p2 = new Produit("ZR85", 100);
        p2.setCategorie(c1);
        Produit p3 = new Produit("EE85", 200);
        p3.setCategorie(c2);

        produitService.create(p1);
        produitService.create(p2);
        produitService.create(p3);

        // 3) Commandes + lignes
        Commande cmd = new Commande(LocalDate.of(2013, 3, 14));
        commandeService.create(cmd);

        LigneCommandeProduit l1 = new LigneCommandeProduit(p1, 7);
        l1.setCommande(cmd);
        ligneService.create(l1);

        LigneCommandeProduit l2 = new LigneCommandeProduit(p2, 14);
        l2.setCommande(cmd);
        ligneService.create(l2);

        LigneCommandeProduit l3 = new LigneCommandeProduit(p3, 5);
        l3.setCommande(cmd);
        ligneService.create(l3);

        // A) Liste produits par catégorie
        System.out.println("\nProduits de la catégorie PC:");
        produitService.findByCategorieId(c1.getId()).forEach(System.out::println);

        // B) Produits commandés entre 2 dates
        System.out.println("\nProduits commandés entre 2013-01-01 et 2013-12-31:");
        produitService.produitsCommandesEntre(LocalDate.of(2013,1,1), LocalDate.of(2013,12,31))
                .forEach(System.out::println);

        // C) Produits dans une commande donnée (affichage demandé)
        System.out.println("\napacheCommande : " + cmd.getId() + "     Date : " + cmd.getDate());
        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix    Quantité");
        List<Object[]> rows = produitService.produitsDansCommande(cmd.getId());
        for (Object[] r : rows) {
            System.out.printf("%-10s %-7.0fDH %-5d%n", r[0], (double) r[1], (int) r[2]);
        }

        // D) NamedQuery prix > 100
        System.out.println("\nProduits dont prix > 100 (NamedQuery):");
        produitService.produitsPrixSuperieurA100().forEach(System.out::println);

        HibernateUtil.shutdown();
    }
}
