package DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import pojos.Buffet;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;

public class BuffetDAO {

    private static final BuffetDAO instance = new BuffetDAO();

    public static BuffetDAO getInstance() {
        return instance;
    }

    private BuffetDAO() {
    }

    public void addBuffet(Buffet buffet) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(buffet);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Buffet> getAllBuffets() throws Exception {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            buffets = session.createCriteria(Buffet.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return buffets;
    }

    public void deleteBuffet(Buffet buffet) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(buffet);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public Buffet getBuffetById(Long id) throws Exception {
        Session session;
        Buffet buffet = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            buffet = (Buffet) session.get(Buffet.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return buffet;
    }

    @SuppressWarnings("unchecked")
    public Collection<Buffet> getBuffetsByName(String name) throws Exception {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Buffet where name = :name")
                    .setString("name", name);
            buffets = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return buffets;
    }

    @SuppressWarnings("unchecked")
    public Collection<Buffet> getBuffetsByAdmin(Boolean isAdmin) throws Exception {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Buffet where admin = :isAdmin")
                    .setBoolean("isAdmin", isAdmin);
            buffets = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return buffets;
    }

    @SuppressWarnings("unchecked")
    public Collection<Buffet> getBuffetsByComplain(Boolean isComplained) throws Exception {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Buffet where complained = :isComplained")
                    .setBoolean("isComplained", isComplained);
            buffets = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return buffets;
    }

    public void updateBuffet(Buffet buffet) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(buffet);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
}
