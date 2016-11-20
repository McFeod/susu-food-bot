package DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import pojos.Buffet;
import pojos.Product;
import pojos.ProductInStock;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class ProductsInStockDAO {

    private static final ProductsInStockDAO instance = new ProductsInStockDAO();

    public static ProductsInStockDAO getInstance() {
        return instance;
    }

    private ProductsInStockDAO() {
    }

    public void addProductInStock(ProductInStock productInStock) throws SQLException {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(productInStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return products;
    }
}
