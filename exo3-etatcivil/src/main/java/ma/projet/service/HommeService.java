package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override public Homme create(Homme o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.persist(o); tx.commit(); return o;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public Homme update(Homme o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); Homme m=(Homme)s.merge(o); tx.commit(); return m;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public boolean delete(Homme o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.remove(s.contains(o)?o:s.merge(o)); tx.commit(); return true;} catch(Exception e){ if(tx!=null)tx.rollback(); return false;}}
    @Override public Homme findById(Object id){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.get(Homme.class,(Integer)id);} }
    @Override public List<Homme> findAll(){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.createQuery("select h from Homme h",Homme.class).getResultList();} }

    // Épouses d’un homme entre deux dates (mariages dont dateDebut est dans l’intervalle)
    public List<Femme> epousesEntreDeuxDates(int hommeId, LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                select m.femme
                from Mariage m
                where m.homme.id = :hid
                  and m.dateDebut between :d1 and :d2
            """, Femme.class).setParameter("hid", hommeId).setParameter("d1", d1).setParameter("d2", d2)
                    .getResultList();
        }
    }

    // Mariages d’un homme avec détails (femme, dates, enfants) + séparation en cours/échoués
    public List<Object[]> mariagesDetails(int hommeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
              select f.nom, f.prenom, m.dateDebut, m.dateFin, m.nbrEnfant
              from Mariage m join m.femme f
              where m.homme.id = :hid
              order by m.dateDebut
            """, Object[].class).setParameter("hid", hommeId).getResultList();
        }
    }
}
