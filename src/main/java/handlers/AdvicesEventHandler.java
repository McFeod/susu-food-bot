package handlers;

import DAO.AdviceDAO;
import api.exceptions.BotLogicException;
import api.exceptions.UnexpectedException;
import pojos.Advice;

import java.util.ArrayList;
import java.util.Collection;

public class AdvicesEventHandler {

    public static ArrayList<String> getAdvices() throws BotLogicException {
        ArrayList<String> messages = new ArrayList<>();
        try {
            AdviceDAO adviceDAO = AdviceDAO.getInstance();
            Collection<Advice> advices = adviceDAO.getAllAdvices();
            for (Advice advice : advices) {
                messages.add(advice.getText());
            }
        } catch (BotLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
        return messages;
    }

    public static void addAdvice(String text) throws BotLogicException {
        AdviceDAO adviceDAO = AdviceDAO.getInstance();
        Advice advice = new Advice();
        advice.setText(text);
        try {
            adviceDAO.addAdvice(advice);
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }
}
