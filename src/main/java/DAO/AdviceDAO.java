package DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

import util.HibernateUtil;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Query;
import logic.Advice;

public class AdviceDAO {
    public AdviceDAO() {
    }

    
    public void addAdvice(Advice advice) throws SQLException {
        Session session = null;
        try {
        	session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(advice);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }// finally {
           // if (session != null && session.isOpen()) {

            //    session.close();
           // }
        //}
    }

    public Collection getAllAdvices() throws SQLException {
        Session session = null;
        List advices = new ArrayList<Advice>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            advices = session.createCriteria(Advice.class).list();
            //HibernateUtil.shutdown();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return advices;
    }

    public void deleteAdvice(Advice advice) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(advice);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        } //finally {
           // if (session != null && session.isOpen()) {
           //     session.close();
          //  }
       // }
    }
}
