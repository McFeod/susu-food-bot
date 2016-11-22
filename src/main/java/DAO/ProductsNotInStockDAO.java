package DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import pojos.Buffet;
import pojos.Product;
import pojos.ProductNotInStock;
import util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collection;

public class ProductsNotInStockDAO {

    private static final ProductsNotInStockDAO instance = new ProductsNotInStockDAO();

    public static ProductsNotInStockDAO getInstance() {
        return instance;
    }

    private ProductsNotInStockDAO() {
    }

    public void addProductNotInStock(ProductNotInStock productNotInStock) throws Exception {
        Session session;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(productNotInStock);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductNotInStock> getAllProductsNotInStock() throws Exception {
        Session session;
        Collection<ProductNotInStock> productNotInStock = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            productNotInStock = session.createCriteria(ProductNotInStock.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return productNotInStock;
    }

    public void deleteProductNotInStock(ProductNotInStock product) throws Exception {
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

    public ProductNotInStock getProductNotInStockById(Long id) throws Exception {
        Session session;
        ProductNotInStock product = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            product = (ProductNotInStock) session.get(ProductNotInStock.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return product;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductNotInStock> getProductsNotInStockByBuffet(Buffet buffet) throws Exception {
        Session session;
        Collection<ProductNotInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long buffet_id = buffet.getId();
            Query query = session.createQuery("from ProductNotInStock where buffet_id = :buffet_id")
                    .setLong("buffet_id", buffet_id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return products;
    }

    @SuppressWarnings("unchecked")
    public Collection<ProductNotInStock> getProductsInStockByProduct(Product product) throws Exception {
        Session session;
        Collection<ProductNotInStock> products = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Long product_id = product.getId();
            Query query = session.createQuery("from ProductNotInStock where product_id = :product_id")
                    .setLong("product_id", product_id);
            products = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
        return products;
    }
}
