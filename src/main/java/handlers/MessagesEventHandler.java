package handlers;

import DAO.*;
import api.exceptions.FeedPointDoesNotExists;
import logic.Advice;
import logic.Buffet;
import logic.Product;
import logic.ProductsNotInStock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class MessagesEventHandler {

	public static HashMap<String, String> getMessages(String buffetName) {
		HashMap<String, String> messages = new HashMap<>();
		try {
			ProductsNotInStockDAO BDB = new ProductsNotInStockDAO();
			Buffet buffet = new Buffet();
			buffet.setName(buffetName);
			Collection products = (buffetName == null || buffetName.isEmpty())
					? BDB.getAllProductsNotInStock()
					: BDB.getProductsNotInStockByBuffet(buffet);
			Iterator iterator = products.iterator();
			while (iterator.hasNext()) {
				ProductsNotInStock product = (ProductsNotInStock) iterator.next();
				messages.put(product.getBuffet().getName(), product.getProduct().getName());
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return messages;
	}

	public static void runOut(String buffetName, String productName) {
		ProductsNotInStockDAO BDB = new ProductsNotInStockDAO();
		ProductsNotInStock productNotInStock = new ProductsNotInStock();

		Buffet buffet = new Buffet();
		buffet.setIsAdmin(false);
		buffet.setName(buffetName);
		productNotInStock.setBuffet(buffet);

		Product product = new Product();
		product.setName(productName);
		productNotInStock.setProduct(product);

		try {
			BDB.addProductInStock(productNotInStock);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
