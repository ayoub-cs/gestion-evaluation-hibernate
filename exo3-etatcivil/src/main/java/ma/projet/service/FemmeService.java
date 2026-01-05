package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override public Femme create(Femme o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.persist(o); tx.commit(); return o;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public Femme update(Femme o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); Femme m=(Femme)s.merge(o); tx.commit(); return m;} catch(Exception e){ if(tx!=null)tx.rollback(); throw e;}}
    @Override public boolean delete(Femme o){ Transaction tx=null; try(Session s=HibernateUtil.getSessionFactory().openSession()){ tx=s.beginTransaction(); s.remove(s.contains(o)?o:s.merge(o)); tx.commit(); return true;} catch(Exception e){ if(tx!=null)tx.rollback(); return false;}}
    @Override public Femme findById(Object id){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.get(Femme.class,(Integer)id);} }
    @Override public List<Femme> findAll(){ try(Session s=HibernateUtil.getSessionFactory().openSession()){ return s.createQuery("select f from Femme f",Femme.class).getResultList();} }

    // Requête native nommée => nombre d’enfants d’une femme entre 2 dates
    public long nbEnfantsEntreDeuxDates(int femmeId, LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Long res = (Long) s.createNamedQuery("Femme.nbEnfantsEntreDeuxDates")
                    .setParameter("fid", femmeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getSingleResult();
            return res == null ? 0L : res;
        }
    }

    // Femmes mariées au moins 2 fois (NamedQuery)
    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Femme.marieeAuMoinsDeuxFois", Femme.class).getResultList();
        }
    }

    // Femme la plus âgée (utile pour le test)
    public Femme femmePlusAgee() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("select f from Femme f order by f.dateNaissance asc", Femme.class)
                    .setMaxResults(1).getSingleResult();
        }
    }
}
