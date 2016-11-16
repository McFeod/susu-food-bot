package DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

import util.HibernateUtil;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Query;

import logic.Buffet;
import logic.Product;
import logic.ProductsNotInStock;

public class ProductsNotInStockDAO {
    public void addProductNotInStock(ProductsNotInStock productNotInStock) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(productNotInStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductsNotInStock> getAllProductsNotInStock() throws SQLException {
        Session session;
        Collection<ProductsNotInStock> productsNotInStock = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            productsNotInStock = session.createCriteria(ProductsNotInStock.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return productsNotInStock;
    }

    public void deleteProductNotInStock(ProductsNotInStock product) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
        }
    }

    public ProductsNotInStock getProductNotInStockById(Long id) throws SQLException {
        Session session;
        ProductsNotInStock product = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            product = (ProductsNotInStock) session.get(ProductsNotInStock.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return product;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductsNotInStock> getProductsNotInStockByBuffet(Buffet buf) throws SQLException {
        Session session;
        Collection<ProductsNotInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long id = buf.getId();
            Query query = session.createQuery(" from ProductsNotInStock " + " where buffet_id = :id ")
                    .setLong("id", id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return products;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductsNotInStock> getProductsInStockByProduct(Product pro) throws SQLException {
        Session session;
        Collection<ProductsNotInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long id = pro.getId();
            Query query = session.createQuery(" from PeroductsNotInStock " + " where product_id = :id ")
                    .setLong("id", id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return products;
    }
}
