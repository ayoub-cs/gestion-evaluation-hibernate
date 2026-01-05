package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.LigneCommandeProduitId;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public LigneCommandeProduit create(LigneCommandeProduit o) {
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
    public LigneCommandeProduit update(LigneCommandeProduit o) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            tx = s.beginTransaction();
            LigneCommandeProduit merged = (LigneCommandeProduit) s.merge(o);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public boolean delete(LigneCommandeProduit o) {
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
    public LigneCommandeProduit findById(Object id) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.get(LigneCommandeProduit.class, (LigneCommandeProduitId) id);
        }
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("select l from LigneCommandeProduit l", LigneCommandeProduit.class).getResultList();
        }
    }
}
