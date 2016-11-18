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
import util.Pair;
import util.StringUtils;

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
            Collection<ProductNotInStock> products;

            if (StringUtils.isNullOrEmpty(buffetName)) {
                products = productsNotInStockDAO.getAllProductsNotInStock();
            } else {
                BuffetDAO buffetDAO = new BuffetDAO();
                Buffet buffet;
                Iterator<Buffet> buffetsIterator = buffetDAO.getBuffetsByName(buffetName).iterator();
                if (buffetsIterator.hasNext()) {
                    buffet = buffetsIterator.next();
                } else {
                    throw new FeedPointDoesNotExists();
                }
                products = productsNotInStockDAO.getProductsNotInStockByBuffet(buffet);
            }

            if (products == null) {
                throw new FeedPointDoesNotExists();
            }

            for (ProductNotInStock product : products) {
                messages.add(new Pair<>(product.getBuffet().getName(), product.getProduct().getName()));
            }
        } catch (SQLException e) {
            throw new NotImplementedException();
        }
        return messages;
    }

    public static void runOut(String buffetName, String productName) throws BotLogicException {
        try {
            ProductsNotInStockDAO productsNotInStockDAO = new ProductsNotInStockDAO();
            ProductNotInStock productNotInStock = new ProductNotInStock();

            BuffetDAO buffetDAO = new BuffetDAO();
            Buffet buffet;
            Iterator<Buffet> buffetsIterator = buffetDAO.getBuffetsByName(buffetName).iterator();
            if (buffetsIterator.hasNext()) {
                buffet = buffetsIterator.next();
            } else {
                throw new WrongRunOutParams();
            }

            ProductDAO productDAO = new ProductDAO();
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
