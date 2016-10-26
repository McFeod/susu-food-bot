package handlers;

import DAO.BuffetDatabase;
import DAO.ProductDAO;
import DAO.ProductsNotInStockDAO;
import api.exceptions.BotLogicException;
import api.exceptions.FeedPointDoesNotExists;
import api.exceptions.NotImplementedException;
import api.exceptions.WrongRunOutParams;
import logic.Buffet;
import logic.Product;
import logic.ProductsNotInStock;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class MessagesEventHandler {

	public static HashMap<String, String> getMessages(String buffetName) throws BotLogicException {
		HashMap<String, String> messages = new HashMap<>();
		try {
			ProductsNotInStockDAO BDB = new ProductsNotInStockDAO();
			Collection products;

			if (buffetName == null || buffetName.isEmpty())
				products = BDB.getAllProductsNotInStock();
			else {
				Buffet buffet = null;
				BuffetDatabase buffetDB = new BuffetDatabase();
				Iterator buffetsIterator = buffetDB.getBuffetsByName(buffetName).iterator();
				if (buffetsIterator.hasNext())
					buffet = (Buffet) buffetsIterator.next();

				if (buffet == null)
					throw new FeedPointDoesNotExists();

				products = BDB.getProductsNotInStockByBuffet(buffet);
			}

			if (products == null)
				throw new FeedPointDoesNotExists();

			Iterator iterator = products.iterator();
			while (iterator.hasNext()) {
				ProductsNotInStock productsNotInStock = (ProductsNotInStock) iterator.next();
				messages.put(productsNotInStock.getBuffet().getName(), productsNotInStock.getProduct().getName());
			}
		} catch (FeedPointDoesNotExists e) {
			throw e;
		} catch (SQLException e) {
			throw new NotImplementedException();
		}
		return messages;
	}

	public static void runOut(String buffetName, String productName) throws BotLogicException {
		try {
			ProductsNotInStockDAO BDB = new ProductsNotInStockDAO();
			ProductsNotInStock productNotInStock = new ProductsNotInStock();

			Buffet buffet = null;
			Product product = null;

			BuffetDatabase buffetDB = new BuffetDatabase();
			Iterator buffetsIterator = buffetDB.getBuffetsByName(buffetName).iterator();
			if (buffetsIterator.hasNext())
				buffet = (Buffet) buffetsIterator.next();

			ProductDAO productDB = new ProductDAO();
			Iterator productsIterator = productDB.getProductsByName(productName).iterator();
			if (productsIterator.hasNext())
				product = (Product) productsIterator.next();

			if (product == null) {
				product = new Product();
				product.setName(productName);
				productDB.addProduct(product);
			}

			if (buffet == null)
				throw new WrongRunOutParams();

			productNotInStock.setBuffet(buffet);
			productNotInStock.setProduct(product);

			BDB.addProductInStock(productNotInStock);
		} catch (WrongRunOutParams e) {
			throw e;
		} catch (SQLException e) {
			throw new NotImplementedException();
		}
	}

}
