package handlers;

import DAO.AdviceDAO;
import pojos.Advice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class AdvicesEventHandler {

    public static ArrayList<String> getAdvices() {
        ArrayList<String> messages = new ArrayList<>();
        try {
            AdviceDAO adviceDAO = new AdviceDAO();
            Collection<Advice> advices = adviceDAO.getAllAdvices();
            for (Advice advice : advices) {
                messages.add(advice.getText());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static void addAdvice(String text) {
        AdviceDAO adviceDAO = new AdviceDAO();
        Advice advice = new Advice();
        advice.setText(text);
        try {
            adviceDAO.addAdvice(advice);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
