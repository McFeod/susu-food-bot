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
import logic.ProductsInStock;

public class ProductsInStockDAO {
    public ProductsInStockDAO() {
    }

    public void addProductInStock(ProductsInStock productInStock) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(productInStock);
            session.getTransaction().commit();
            //HibernateUtil.shutdown();
            if (session != null && session.isOpen()) {

                session.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
        } 
    }

    public Collection getAllProductsInStock() throws SQLException {
        Session session = null;
        List productsInStock = new ArrayList<ProductsInStock>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            productsInStock = session.createCriteria(ProductsInStock.class).list();
            //HibernateUtil.shutdown();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        } 
        return productsInStock;
    }

    public void deleteProductInStock(ProductsInStock product) throws SQLException {
        Session session = null;
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
        Session session = null;
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

    public Collection getProductsInStockByBuffet(Buffet buf) throws SQLException {
        Session session = null;
        List products = new ArrayList<ProductsInStock>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long id = buf.getId();
            Query query = session.createQuery(" from ProductsInStock " + " where buffet_id = :id ").setLong("id", id);
            // .setLong("name", name);
            products = (List<ProductsInStock>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } 
        return products;
    }

    public Collection getProductsInStockByProduct(Product pro) throws SQLException {
        Session session = null;
        List products = new ArrayList<ProductsInStock>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long id = pro.getId();
            Query query = session.createQuery(" from ProductsInStock " + " where product_id = :id ").setLong("id", id);
            // .setLong("name", name);
            products = (List<ProductsInStock>) query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        } 
        return products;
    }
}
