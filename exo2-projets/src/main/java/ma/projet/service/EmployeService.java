package ma.projet.service;

import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override public Employe create(Employe o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.persist(o); tx.commit(); return o;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public Employe update(Employe o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); Employe m=(Employe)s.merge(o); tx.commit(); return m;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public boolean delete(Employe o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.remove(s.contains(o)?o:s.merge(o)); tx.commit(); return true;} catch(Exception e){ if(tx!=null)tx.rollback(); return false;}}
    @Override public Employe findById(Object id){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.get(Employe.class,(Integer)id);} }
    @Override public List<Employe> findAll(){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.createQuery("select e from Employe e",Employe.class).getResultList();} }

    // Afficher la liste des tâches réalisées par un employé
    public List<Object[]> tachesRealiseesParEmploye(int employeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                select et.tache.id, et.tache.nom, et.dateDebutReelle, et.dateFinReelle
                from EmployeTache et
                where et.employe.id = :eid
                order by et.dateDebutReelle
            """, Object[].class).setParameter("eid", employeId).getResultList();
        }
    }

    // Afficher la liste des projets gérés par un employé
    public List<Projet> projetsGeresParEmploye(int employeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("select p from Projet p where p.chefProjet.id = :eid", Projet.class)
                    .setParameter("eid", employeId).getResultList();
        }
    }
}
