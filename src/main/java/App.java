import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import DAO.ProductDAO;
import DAO.BuffetDatabase;
import DAO.AdviceDAO;
import DAO.ProductsInStockDAO;
import DAO.ProductsNotInStockDAO;
import logic.Product;
import logic.Advice;
import logic.Buffet;
import logic.ProductsInStock;
import logic.ProductsNotInStock;
import util.HibernateUtil;


public class App 
{
    public static void main( String[] args )
    {
    	Buffet buf = new Buffet(), buf1 = new Buffet();
    	buf.setName("buf3");
    	buf1.setName("buf3");
    	BuffetDatabase BDB = new BuffetDatabase();
    	ProductDAO PDB = new ProductDAO();
    	AdviceDAO ADB = new AdviceDAO();
    	ProductsInStockDAO PIDB = new ProductsInStockDAO();
    	Product pr = new Product(), pr1 = new Product();
    	pr.setName("product1");
    	pr1.setName("product2");
    	Advice adv = new Advice(), adv1 = new Advice();
    	adv.setAdvice("My Advice");
    	adv1.setAdvice("Anither advice");
    	ProductsInStock prin = new ProductsInStock();
    	pr.setId(1);
    	buf.setId(1);
    	adv.setId(3);
    	prin.setBuffet(buf);
    	prin.setProduct(pr);
    	
    	try {
    		//BDB.addBuffet(buf);
    		//PDB.addProduct(pr);
    		//PIDB.addProductInStock(prin);
    	//	PIDB.addProductInStock(prin);
    		//PIDB.addProductInStock(prin);
    	//	PIDB.addProductInStock(prin);
			Collection buffets = PIDB.getProductsInStockByProduct(pr);
			Iterator iterator = buffets.iterator();
			while (iterator.hasNext()) 
			{
			      prin = (ProductsInStock)iterator.next();
			      System.out.println(pr.getName());
			}
    		
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    }
}
