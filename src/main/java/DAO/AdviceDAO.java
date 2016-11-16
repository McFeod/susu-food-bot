package DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

import util.HibernateUtil;

import javax.swing.*;

import org.hibernate.Session;
import logic.Advice;

public class AdviceDAO {
    public void addAdvice(Advice advice) throws SQLException {
        Session session;
        try {
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(advice);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
    }

    public Collection<Advice> getAllAdvices() throws SQLException {
        Session session;
        Collection<Advice> advices = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            advices = session.createCriteria(Advice.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return advices;
    }

    public void deleteAdvice(Advice advice) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(advice);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        }
    }
}
