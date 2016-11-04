package DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Query;
import logic.Buffet;


public class BuffetDatabase {
    public BuffetDatabase() {
    }

    public void addBuffet(Buffet buffet) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(buffet);
            session.getTransaction().commit();
            //HibernateUtil.shutdown();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
    }

    public Collection getAllBuffets() throws SQLException {
        Session session = null;
        List buffets = new ArrayList<Buffet>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            buffets = session.createCriteria(Buffet.class).list();
            //HibernateUtil.shutdown();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } 
        return buffets;
    }

    public void deleteBuffet(Buffet buffet) throws SQLException {
        Session session = null;
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
        Session session = null;
        Buffet buffet = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            buffet = (Buffet) session.load(Buffet.class, buffet_id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } 
        return buffet;
    }

    public Collection getBuffetsByName(String name) throws SQLException {
        Session session = null;
        List buffets = new ArrayList<Buffet>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            //Long driver_id = driver.getId();
            Query query = session.createQuery(" from Buffet " + " where name = :name ").setString("name", name);
            // .setLong("name", name);
            buffets = (List<Buffet>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } 
        return buffets;
    }

    public Collection getBuffetsByAdmin(Boolean isAdmin) throws SQLException {
        Session session = null;
        List buffets = new ArrayList<Buffet>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            //Long driver_id = driver.getId();
            Query query = session.createQuery(" from Buffet " + " where admin = :isAdmin ").setBoolean("isAdmin", isAdmin);
            // .setLong("name", name);
            buffets = (List<Buffet>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } 
        return buffets;
    }

    public Collection getBuffetsByComplain(Boolean isComplained) throws SQLException {
        Session session = null;
        List buffets = new ArrayList<Buffet>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            //Long driver_id = driver.getId();
            Query query = session.createQuery(" from Buffet " + " where complained = :isComplained ").setBoolean("isComplained", isComplained);
            // .setLong("name", name);
            buffets = (List<Buffet>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } 
        return buffets;
    }

    public void updateBuffet(Buffet buf) throws SQLException {
        Session session = null;
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
