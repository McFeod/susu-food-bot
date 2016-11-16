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
import logic.ProductsInStock;

public class ProductsInStockDAO {
    public void addProductInStock(ProductsInStock productInStock) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(productInStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductsInStock> getAllProductsInStock() throws SQLException {
        Session session;
        Collection<ProductsInStock> productsInStock = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            productsInStock = session.createCriteria(ProductsInStock.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return productsInStock;
    }

    public void deleteProductInStock(ProductsInStock product) throws SQLException {
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

    public ProductsInStock getProductInStockById(Long id) throws SQLException {
        Session session;
        ProductsInStock product = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            product = (ProductsInStock) session.get(ProductsInStock.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return product;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductsInStock> getProductsInStockByBuffet(Buffet buf) throws SQLException {
        Session session;
        Collection<ProductsInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long id = buf.getId();
            Query query = session.createQuery(" from ProductsInStock " + " where buffet_id = :id ")
                    .setLong("id", id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return products;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductsInStock> getProductsInStockByProduct(Product pro) throws SQLException {
        Session session;
        Collection<ProductsInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long id = pro.getId();
            Query query = session.createQuery(" from ProductsInStock " + " where product_id = :id ")
                    .setLong("id", id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return products;
    }
}
