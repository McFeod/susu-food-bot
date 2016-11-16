package handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import DAO.BuffetDAO;
import api.exceptions.FeedPointDoesNotExists;
import logic.Buffet;

public class FeedPointEventHandler {
    public static ArrayList<String> getFeedPoints() {
        ArrayList<String> feedPoints = new ArrayList<>();
        try {
            BuffetDAO buffetDAO = new BuffetDAO();
            Collection<Buffet> buffets = buffetDAO.getBuffetsByAdmin(true);
            for (Buffet buffet : buffets) {
                feedPoints.add(buffet.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedPoints;
    }

    public static ArrayList<String> getUserFeedPoints() {
        ArrayList<String> feedPoints = new ArrayList<>();
        try {
            BuffetDAO buffetDAO = new BuffetDAO();
            Collection<Buffet> buffets = buffetDAO.getBuffetsByAdmin(false);
            for (Buffet buffet : buffets) {
                feedPoints.add(buffet.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedPoints;
    }

    public static void addFeedPoint(String name) {
        BuffetDAO buffetDAO = new BuffetDAO();
        Buffet buffet = new Buffet();
        buffet.setIsAdmin(false);
        buffet.setIsComplained(false);
        buffet.setName(name);
        try {
            buffetDAO.addBuffet(buffet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void complainFeedPoint(String name) throws FeedPointDoesNotExists {
        BuffetDAO buffetDAO = new BuffetDAO();
        try {
            Collection<Buffet> buffets = buffetDAO.getBuffetsByName(name);
            Iterator<Buffet> iterator = buffets.iterator();
            if (iterator.hasNext()) {
                Buffet buf = iterator.next();
                buf.setIsComplained(true);
                buffetDAO.updateBuffet(buf);
            } else {
                throw new FeedPointDoesNotExists();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
