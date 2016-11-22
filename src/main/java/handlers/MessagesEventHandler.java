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
            Calendar date = Calendar.getInstance();
            for (ProductNotInStock product : products) {
                if (product.getDate().get(Calendar.YEAR) == date.get(Calendar.YEAR) && 
                        product.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH) &&
                        product.getDate().get(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)){
                    List<String> list = messages.get(product.getBuffet().getName());
                    if (list == null) {
                        messages.put(product.getBuffet().getName(), new ArrayList<String>());
                        list = messages.get(product.getBuffet().getName());
                    }
                    list.add(product.getProduct().getName());
                }
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
            Calendar calendar = Calendar.getInstance();
            BuffetDAO buffetDAO = BuffetDAO.getInstance();
            Buffet buffet;
            
            Iterator<Buffet> buffetsIterator = buffetDAO.getBuffetsByName(buffetName).iterator();
            
            if (buffetsIterator.hasNext()) {
                buffet = buffetsIterator.next();
            } else {
                throw new FeedPointDoesNotExists();
            }
            Iterator<ProductNotInStock> products = productsNotInStockDAO.getProductsNotInStockByBuffet(buffet).iterator();
            while (products.hasNext())
            {
                ProductNotInStock pr = products.next();
                Calendar dateCalendar = Calendar.getInstance();
                dateCalendar = pr.getDate();
                if (pr.getProduct().getName().equals(productName) &&
                    calendar.get(Calendar.YEAR) == dateCalendar.get(Calendar.YEAR)&&
                    calendar.get(Calendar.MONTH) == dateCalendar.get(Calendar.MONTH)&&
                    calendar.get(Calendar.DAY_OF_MONTH) == dateCalendar.get(Calendar.DAY_OF_MONTH))
                    return;
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
            productNotInStock.setDate(calendar);
            productsNotInStockDAO.addProductNotInStock(productNotInStock);
        } catch (SQLException e) {
            throw new NotImplementedException();
        }
    }
}
