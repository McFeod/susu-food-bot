package handlers;

import DAO.BuffetDAO;
import DAO.ProductDAO;
import DAO.ProductsNotInStockDAO;
import api.exceptions.BotLogicException;
import api.exceptions.FeedPointDoesNotExists;
import api.exceptions.NotImplementedException;
import api.exceptions.WrongRunOutParams;
import logic.Buffet;
import logic.Product;
import logic.ProductsNotInStock;
import util.Pair;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MessagesEventHandler {
    public static List<Pair<String, String>> getMessages(String buffetName) throws BotLogicException {
        List<Pair<String, String>> messages = new ArrayList<>();
        try {
            ProductsNotInStockDAO productsNotInStockDAO = new ProductsNotInStockDAO();
            Collection<ProductsNotInStock> products;

            if (buffetName == null || buffetName.isEmpty())
                products = productsNotInStockDAO.getAllProductsNotInStock();
            else {
                Buffet buffet = null;
                BuffetDAO buffetDAO = new BuffetDAO();
                Iterator<Buffet> buffetsIterator = buffetDAO.getBuffetsByName(buffetName).iterator();
                if (buffetsIterator.hasNext())
                    buffet = buffetsIterator.next();

                if (buffet == null)
                    throw new FeedPointDoesNotExists();

                products = productsNotInStockDAO.getProductsNotInStockByBuffet(buffet);
            }

            if (products == null)
                throw new FeedPointDoesNotExists();

            for (ProductsNotInStock product : products) {
                messages.add(new Pair<>(product.getBuffet().getName(), product.getProduct().getName()));
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
            ProductsNotInStockDAO productsNotInStockDAO = new ProductsNotInStockDAO();
            ProductsNotInStock productNotInStock = new ProductsNotInStock();

            Buffet buffet = null;
            Product product = null;

            BuffetDAO buffetDAO = new BuffetDAO();
            Iterator<Buffet> buffetsIterator = buffetDAO.getBuffetsByName(buffetName).iterator();
            if (buffetsIterator.hasNext())
                buffet = buffetsIterator.next();

            ProductDAO productDAO = new ProductDAO();
            Iterator<Product> productsIterator = productDAO.getProductsByName(productName).iterator();
            if (productsIterator.hasNext())
                product = productsIterator.next();

            if (product == null) {
                product = new Product();
                product.setName(productName);
                productDAO.addProduct(product);
            }

            if (buffet == null)
                throw new WrongRunOutParams();

            productNotInStock.setBuffet(buffet);
            productNotInStock.setProduct(product);

            productsNotInStockDAO.addProductNotInStock(productNotInStock);
        } catch (WrongRunOutParams e) {
            throw e;
        } catch (SQLException e) {
            throw new NotImplementedException();
        }
    }

}
