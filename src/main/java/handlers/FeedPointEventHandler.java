package handlers;

import DAO.BuffetDAO;
import DAO.UserDAO;
import api.exceptions.FeedPointDoesNotExists;
import pojos.Buffet;
import pojos.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class FeedPointEventHandler {
    public static ArrayList<String> getFeedPoints() {
        ArrayList<String> feedPoints = new ArrayList<>();
        try {
            BuffetDAO buffetDAO = BuffetDAO.getInstance();
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
            BuffetDAO buffetDAO = BuffetDAO.getInstance();
            Collection<Buffet> buffets = buffetDAO.getBuffetsByAdmin(false);
            for (Buffet buffet : buffets) {
                feedPoints.add(buffet.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedPoints;
    }

    public static void addFeedPoint(long id,String name) {
        BuffetDAO buffetDAO = BuffetDAO.getInstance();
        UserDAO userDAO = UserDAO.getInstance();
        Buffet buffet = new Buffet();
        buffet.setIsAdmin(false);
        buffet.setIsComplained(false);
        try {
            buffet.setUser(userDAO.getUserById(id));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        buffet.setName(name);
        try {
            buffetDAO.addBuffet(buffet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void complainFeedPoint(long id,String name) throws FeedPointDoesNotExists {
        BuffetDAO buffetDAO = BuffetDAO.getInstance();
        try {
            Collection<Buffet> buffets = buffetDAO.getBuffetsByName(name);
            Iterator<Buffet> iterator = buffets.iterator();
            if (iterator.hasNext()) {
                Buffet buf = iterator.next();
                buf.setIsComplained(true);
                UserDAO uDAO = UserDAO.getInstance();
                User user = buf.getUser();
                if (user!= null)
                {
                    user.setRating(user.getRating() - 50);
                    uDAO.updateUser(user);
                }
                buffetDAO.updateBuffet(buf);
            } else {
                throw new FeedPointDoesNotExists();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
