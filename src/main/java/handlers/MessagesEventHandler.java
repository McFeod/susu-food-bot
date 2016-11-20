package handlers;

import DAO.BuffetDAO;
import DAO.ProductDAO;
import DAO.ProductsNotInStockDAO;
import api.exceptions.BotLogicException;
import api.exceptions.FeedPointDoesNotExists;
import api.exceptions.NotImplementedException;
import api.exceptions.WrongRunOutParams;
import pojos.Buffet;
import pojos.Product;
import pojos.ProductNotInStock;
import util.StringUtils;

import java.sql.SQLException;
import java.util.*;

public class MessagesEventHandler {
    public static Map<String, List<String>> getMessages(String buffetName) throws BotLogicException {
        Map<String, List<String>> messages = new HashMap<>();
        try {
            ProductsNotInStockDAO productsNotInStockDAO = ProductsNotInStockDAO.getInstance();
            Collection<ProductNotInStock> products;

            if (StringUtils.isNullOrEmpty(buffetName)) {
                products = productsNotInStockDAO.getAllProductsNotInStock();
            } else {
                BuffetDAO buffetDAO = BuffetDAO.getInstance();
                Buffet buffet;
                Iterator<Buffet> bufferIterator = buffetDAO.getBuffetsByName(buffetName).iterator();
                if (bufferIterator.hasNext()) {
                    buffet = bufferIterator.next();
                } else {
                    throw new FeedPointDoesNotExists();
                }
                products = productsNotInStockDAO.getProductsNotInStockByBuffet(buffet);
            }

            if (products == null) {
                throw new FeedPointDoesNotExists();
            }

            for (ProductNotInStock product : products) {
                List<String> list = messages.get(product.getBuffet().getName());
                if (list == null) {
                    messages.put(product.getBuffet().getName(), new ArrayList<String>());
                    list = messages.get(product.getBuffet().getName());
                }
                list.add(product.getProduct().getName());
            }
        } catch (SQLException e) {
            throw new NotImplementedException();
        }
        return messages;
    }

    public static void runOut(String buffetName, String productName) throws BotLogicException {
        try {
            ProductsNotInStockDAO productsNotInStockDAO = ProductsNotInStockDAO.getInstance();
            ProductNotInStock productNotInStock = new ProductNotInStock();

            BuffetDAO buffetDAO = BuffetDAO.getInstance();
            Buffet buffet;
            Iterator<Buffet> buffetsIterator = buffetDAO.getBuffetsByName(buffetName).iterator();
            if (buffetsIterator.hasNext()) {
                buffet = buffetsIterator.next();
            } else {
                throw new WrongRunOutParams();
            }

            ProductDAO productDAO = ProductDAO.getInstance();
            Product product = null;
            Iterator<Product> productsIterator = productDAO.getProductsByName(productName).iterator();
            if (productsIterator.hasNext()) {
                product = productsIterator.next();
            }

            if (product == null) {
                product = new Product();
                product.setName(productName);
                productDAO.addProduct(product);
            }

            productNotInStock.setBuffet(buffet);
            productNotInStock.setProduct(product);

            productsNotInStockDAO.addProductNotInStock(productNotInStock);
        } catch (SQLException e) {
            throw new NotImplementedException();
        }
    }
}
