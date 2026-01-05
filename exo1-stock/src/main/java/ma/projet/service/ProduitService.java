package ma.projet.service;

import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public Produit create(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.persist(o);
            tx.commit();
            return o;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public Produit update(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            Produit merged = (Produit) s.merge(o);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(Produit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            s.remove(s.contains(o) ? o : s.merge(o));
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        }
    }

    @Override
    public Produit findById(Object id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(Produit.class, (Integer) id);
        }
    }

    @Override
    public List<Produit> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("select p from Produit p", Produit.class).getResultList();
        }
    }

    // 1) Liste produits par catégorie
    public List<Produit> findByCategorieId(int categorieId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery(
                    "select p from Produit p where p.categorie.id = :cid",
                    Produit.class
            ).setParameter("cid", categorieId).getResultList();
        }
    }

    // 2) Produits commandés entre 2 dates (distinct)
    public List<Produit> produitsCommandesEntre(LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                select distinct l.produit
                from LigneCommandeProduit l
                where l.commande.date between :d1 and :d2
            """, Produit.class).setParameter("d1", d1).setParameter("d2", d2)
                    .getResultList();
        }
    }

    // 3) Produits dans une commande donnée
    public List<Object[]> produitsDansCommande(int commandeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                select l.produit.reference, l.produit.prix, l.quantite
                from LigneCommandeProduit l
                where l.commande.id = :id
            """, Object[].class).setParameter("id", commandeId).getResultList();
        }
    }

    // 4) Produits dont prix > 100 (NamedQuery)
    public List<Produit> produitsPrixSuperieurA100() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Produit.prixSuperieurA100", Produit.class).getResultList();
        }
    }
}
