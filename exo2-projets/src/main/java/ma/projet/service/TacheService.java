package ma.projet.service;

import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override public Tache create(Tache o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.persist(o); tx.commit(); return o;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public Tache update(Tache o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); Tache m=(Tache)s.merge(o); tx.commit(); return m;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public boolean delete(Tache o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.remove(s.contains(o)?o:s.merge(o)); tx.commit(); return true;} catch(Exception e){ if(tx!=null)tx.rollback(); return false;}}
    @Override public Tache findById(Object id){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.get(Tache.class,(Integer)id);} }
    @Override public List<Tache> findAll(){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.createQuery("select t from Tache t",Tache.class).getResultList();} }

    public List<Tache> tachesPrixSuperieurA1000() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Tache.prixSuperieurA1000", Tache.class).getResultList();
        }
    }

    public List<Object[]> tachesRealiseesEntre(LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                select et.tache.id, et.tache.nom, et.dateDebutReelle, et.dateFinReelle
                from EmployeTache et
                where et.dateDebutReelle between :d1 and :d2
                   or et.dateFinReelle between :d1 and :d2
                order by et.dateDebutReelle
            """, Object[].class).setParameter("d1", d1).setParameter("d2", d2).getResultList();
        }
    }
}
