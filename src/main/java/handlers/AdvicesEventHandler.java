package handlers;

import DAO.AdviceDAO;
import DAO.ProductsNotInStockDAO;
import logic.Advice;
import logic.Buffet;
import logic.Product;
import logic.ProductsNotInStock;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class AdvicesEventHandler {

    public static ArrayList<String> getAdvices() {
        ArrayList<String> messages = new ArrayList<>();
        try {
            AdviceDAO BDB = new AdviceDAO();
            Collection advices = BDB.getAllAdvices();
            Iterator iterator = advices.iterator();
            while (iterator.hasNext()) {
                Advice advice = (Advice) iterator.next();
                messages.add(advice.getAdvice());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static void addAdvice(String text) {
        AdviceDAO BDB = new AdviceDAO();

        Advice advice = new Advice();
        advice.setAdvice(text);

        try {
            BDB.addAdvice(advice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
