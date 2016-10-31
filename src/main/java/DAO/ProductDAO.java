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
import logic.Product;

public class ProductDAO {
    public void addProduct(Product product) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
            //HibernateUtil.shutdown();
            if (session != null && session.isOpen()) {

                session.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    public Collection getAllBuffets() throws SQLException {
        Session session = null;
        List products = new ArrayList<Product>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            products = session.createCriteria(Product.class).list();
            //HibernateUtil.shutdown();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return products;
    }

    public void deleteProduct(Product product) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Product getProductById(Long product_id) throws SQLException {
        Session session = null;
        Product product = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            product = (Product) session.load(Product.class, product_id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return product;
    }

    public Collection getProductsByName(String name) throws SQLException {
        Session session = null;
        List products = new ArrayList<Product>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Long driver_id = driver.getId();
            Query query = session.createQuery(" from Product " + " where name = :name ").setString("name", name);
            // .setLong("name", name);
            products = (List<Product>) query.list();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return products;
    }
}
