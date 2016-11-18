package DAO;

import java.sql.SQLException;
import java.util.Collection;
import java.util.ArrayList;

import pojos.ProductInStock;
import util.HibernateUtil;

import javax.swing.*;

import org.hibernate.Session;
import org.hibernate.Query;

import pojos.Buffet;
import pojos.Product;

public class ProductsInStockDAO {
    public void addProductInStock(ProductInStock productInStock) throws SQLException {
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
    public Collection<ProductInStock> getAllProductsInStock() throws SQLException {
        Session session;
        Collection<ProductInStock> productInStock = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            productInStock = session.createCriteria(ProductInStock.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
        }
        return productInStock;
    }

    public void deleteProductInStock(ProductInStock product) throws SQLException {
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

    public ProductInStock getProductInStockById(Long id) throws SQLException {
        Session session;
        ProductInStock product = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            product = (ProductInStock) session.get(ProductInStock.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return product;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductInStock> getProductsInStockByBuffet(Buffet buffet) throws SQLException {
        Session session;
        Collection<ProductInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long buffet_id = buffet.getId();
            Query query = session.createQuery("from ProductInStock where buffet_id = :buffet_id")
                    .setLong("buffet_id", buffet_id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return products;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductInStock> getProductsInStockByProduct(Product product) throws SQLException {
        Session session;
        Collection<ProductInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long product_id = product.getId();
            Query query = session.createQuery("from ProductInStock where product_id = :product_id")
                    .setLong("product_id", product_id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
        }
        return products;
    }
}
