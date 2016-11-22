package DAO;

import org.hibernate.Session;
import pojos.User;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;

public class UserDAO {

    private static final UserDAO instance = new UserDAO();

    public static UserDAO getInstance() {
        return instance;
    }

    private UserDAO() {
    }

    public void addUser(User user) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<User> getAllUsers() throws Exception {
        Session session;
        Collection<User> users = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            users = session.createCriteria(User.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return users;
    }

    public void deleteUser(User user) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    public void updateUser(User user) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public User getUserById(Long id) throws Exception {
        Session session;
        User user = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            user = (User) session.get(User.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return user;
    }
}
