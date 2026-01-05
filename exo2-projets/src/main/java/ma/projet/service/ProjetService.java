package ma.projet.service;

import ma.projet.classes.Projet;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override public Projet create(Projet o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.persist(o); tx.commit(); return o;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public Projet update(Projet o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); Projet m=(Projet)s.merge(o); tx.commit(); return m;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public boolean delete(Projet o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.remove(s.contains(o)?o:s.merge(o)); tx.commit(); return true;} catch(Exception e){ if(tx!=null)tx.rollback(); return false;}}
    @Override public Projet findById(Object id){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.get(Projet.class,(Integer)id);} }
    @Override public List<Projet> findAll(){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.createQuery("select p from Projet p",Projet.class).getResultList();} }

    // Liste des tâches planifiées pour un projet
    public List<Object[]> tachesPlanifiees(int projetId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                select t.id, t.nom, t.dateDebut, t.dateFin
                from Tache t
                where t.projet.id = :pid
                order by t.dateDebut
            """, Object[].class).setParameter("pid", projetId).getResultList();
        }
    }

    // Liste des tâches réalisées avec dates réelles
    public List<Object[]> tachesRealiseesAvecDatesReelles(int projetId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                select t.id, t.nom, et.dateDebutReelle, et.dateFinReelle
                from EmployeTache et
                join et.tache t
                where t.projet.id = :pid
                order by et.dateDebutReelle
            """, Object[].class).setParameter("pid", projetId).getResultList();
        }
    }
}
