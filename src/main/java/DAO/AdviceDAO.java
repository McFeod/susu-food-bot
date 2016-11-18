package DAO;

import org.hibernate.Session;
import pojos.Advice;
import util.HibernateUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AdviceDAO {

    private static final AdviceDAO instance = new AdviceDAO();

    public static AdviceDAO getInstance() {
        return instance;
    }

    private AdviceDAO() {
    }

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

    @SuppressWarnings("unchecked")
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
