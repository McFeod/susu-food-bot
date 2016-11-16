package DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

import util.HibernateUtil;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Query;
import logic.Buffet;


public class BuffetDAO {
    public void addBuffet(Buffet buffet) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(buffet);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
    }

    public Collection<Buffet> getAllBuffets() throws SQLException {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            buffets = session.createCriteria(Buffet.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return buffets;
    }

    public void deleteBuffet(Buffet buffet) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(buffet);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        }
    }

    public Buffet getBuffetById(Long buffet_id) throws SQLException {
        Session session;
        Buffet buffet = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            buffet = (Buffet) session.get(Buffet.class, buffet_id);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return buffet;
    }

    public Collection<Buffet> getBuffetsByName(String name) throws SQLException {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(" from Buffet " + " where name = :name ")
                    .setString("name", name);
            buffets = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return buffets;
    }

    public Collection<Buffet> getBuffetsByAdmin(Boolean isAdmin) throws SQLException {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(" from Buffet " + " where admin = :isAdmin ")
                    .setBoolean("isAdmin", isAdmin);
            buffets = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return buffets;
    }

    public Collection<Buffet> getBuffetsByComplain(Boolean isComplained) throws SQLException {
        Session session;
        Collection<Buffet> buffets = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(" from Buffet " + " where complained = :isComplained ")
                    .setBoolean("isComplained", isComplained);
            buffets = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return buffets;
    }

    public void updateBuffet(Buffet buf) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(buf);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
    }
}
