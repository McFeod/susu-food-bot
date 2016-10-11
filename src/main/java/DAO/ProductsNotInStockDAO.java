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
import logic.ProductsNotInStock;

public class ProductsNotInStockDAO {
	public ProductsNotInStockDAO(){}
	public void addProductInStock(ProductsNotInStock productNotInStock) throws SQLException 
	{
	    Session session = null;
	    try 
	    {
	    	session = HibernateUtil.getSessionFactory().openSession();
	    	session.beginTransaction();
	    	session.save(productNotInStock);
	    	session.getTransaction().commit();
	    	//HibernateUtil.shutdown();
	    	if (session != null && session.isOpen()) 
	    	{

	    		session.close();
	    	}
	    } 
	    catch (Exception e) 
	    {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при вставке", JOptionPane.OK_OPTION);
	    } 
	    finally 
	    {
	    	if (session != null && session.isOpen()) 
	    	{

	    		session.close();
	    	}
	    }
	}
	
	public Collection getAllProductsNotInStock() throws SQLException 
	{
	    Session session = null;
	    List productsNotInStock = new ArrayList<ProductsNotInStock>();
	    try 
	    {
	    	session = HibernateUtil.getSessionFactory().openSession();
	    	productsNotInStock = session.createCriteria(ProductsNotInStock.class).list();
	    	//HibernateUtil.shutdown();
	    } 
	    catch (Exception e) 
	    {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'getAll'", JOptionPane.OK_OPTION);
	    } 
	    finally 
	    {
	    	if (session != null && session.isOpen()) 
	    	{
	    		session.close();
	    	}
	    }
	    return productsNotInStock;
	  }
	
	public void deleteProductNotInStock(ProductsNotInStock product) throws SQLException {
	    Session session = null;
	    try 
	    {
	    	session = HibernateUtil.getSessionFactory().openSession();
	    	session.beginTransaction();
	    	session.delete(product);
	    	session.getTransaction().commit();
	    } 
	    catch (Exception e) 
	    {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка при удалении", JOptionPane.OK_OPTION);
	    } 
	    finally 
	    {
	    	if (session != null && session.isOpen()) 
	    	{
	    		session.close();
	    	}
	    }
	}
	public ProductsNotInStock getProductNotInStockById(Long id) throws SQLException 
	{
	    Session session = null;
	    ProductsNotInStock product = null;
	    try 
	    {
	    	session = HibernateUtil.getSessionFactory().openSession();
	    	product = (ProductsNotInStock) session.load(ProductsNotInStock.class, id);
	    } 
	    catch (Exception e) 
	    {
	    	JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка 'findById'", JOptionPane.OK_OPTION);
	    } 
	    finally 
	    {
	    	if (session != null && session.isOpen()) 
	    	{
	    		session.close();
	    	}
	    }
	    return product;
	}
	
	public Collection getProductsNotInStockByBuffet(Buffet buf) throws SQLException {
	    Session session = null;
	    List products = new ArrayList<ProductsNotInStock>();
	    try 
	    {
	    	session = HibernateUtil.getSessionFactory().openSession();
	    	session.beginTransaction();
	    	Long id = buf.getId();
	    	Query query = session.createQuery( " from ProductsNotInStock "+ " where buffet_id = :id ").setLong("id", id);
	         // .setLong("name", name);
	    	products = (List<ProductsNotInStock>) query.list();
	    	session.getTransaction().commit();

	    } 
	    finally 
	    {
	    	if (session != null && session.isOpen()) {
	    		session.close();
	    	}
	    }
	    return products;
	}
	
	public Collection getProductsInStockByProduct(Product pro) throws SQLException {
	    Session session = null;
	    List products = new ArrayList<ProductsInStock>();
	    try 
	    {
	    	session = HibernateUtil.getSessionFactory().openSession();
	    	session.beginTransaction();
	    	Long id = pro.getId();
	    	Query query = session.createQuery( " from PeroductsNotInStock "+ " where product_id = :id ").setLong("id", id);
	         // .setLong("name", name);
	    	products = (List<ProductsInStock>) query.list();
	    	session.getTransaction().commit();

	    } 
	    finally 
	    {
	    	if (session != null && session.isOpen()) {
	    		session.close();
	    	}
	    }
	    return products;
	}
}
