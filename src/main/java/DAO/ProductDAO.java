package DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import pojos.Product;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;

public class ProductDAO {

    private static final ProductDAO instance = new ProductDAO();

    public static ProductDAO getInstance() {
        return instance;
    }

    private ProductDAO() {
    }

    public void addProduct(Product product) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<Product> getAllProducts() throws Exception {
        Session session;
        Collection<Product> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            products = session.createCriteria(Product.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return products;
    }

    public void deleteProduct(Product product) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public Product getProductById(Long id) throws Exception {
        Session session;
        Product product = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            product = (Product) session.get(Product.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return product;
    }

    @SuppressWarnings("unchecked")
    public Collection<Product> getProductsByName(String name) throws Exception {
        Session session;
        Collection<Product> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from Product where name = :name")
                    .setString("name", name);
            products = query.list();
            session.getTransaction().commit();

        } catch (Exception e) {
            throw e;
        }
        return products;
    }
}
