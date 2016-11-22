package handlers;

import DAO.BuffetDAO;
import DAO.UserDAO;
import api.exceptions.BotLogicException;
import api.exceptions.DuplicateFeedPointName;
import api.exceptions.FeedPointDoesNotExists;
import api.exceptions.UnexpectedException;
import pojos.Buffet;
import pojos.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class FeedPointEventHandler {
    public static HashMap<String, String> getFeedPoints() throws BotLogicException {
        HashMap<String, String> feedPoints = new HashMap<>();
        try {
            BuffetDAO buffetDAO = BuffetDAO.getInstance();
            Collection<Buffet> buffets = buffetDAO.getBuffetsByAdmin(true);
            for (Buffet buffet : buffets) {
                feedPoints.put(buffet.getName(), buffet.getPlace());
            }
        } catch (BotLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
        return feedPoints;
    }

    public static HashMap<String, String> getUserFeedPoints() throws BotLogicException {
        HashMap<String, String> feedPoints = new HashMap<>();
        try {
            BuffetDAO buffetDAO = BuffetDAO.getInstance();
            Collection<Buffet> buffets = buffetDAO.getBuffetsByAdmin(false);
            for (Buffet buffet : buffets) {
                feedPoints.put(buffet.getName(), buffet.getPlace());
            }
        } catch (BotLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
        return feedPoints;
    }

    public static void addFeedPoint(long id, String buffetName, String placeName) throws BotLogicException {
        BuffetDAO buffetDAO = BuffetDAO.getInstance();
        try {
            if (buffetDAO.getBuffetsByName(buffetName).size() > 0)
                throw new DuplicateFeedPointName();
        } catch (DuplicateFeedPointName e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
        UserDAO userDAO = UserDAO.getInstance();
        Buffet buffet = new Buffet();
        buffet.setIsAdmin(false);
        buffet.setIsComplained(false);
        try {
            buffet.setUser(userDAO.getUserById(id));
        } catch (Exception e) {
            throw new UnexpectedException();
        }
        buffet.setName(buffetName);
        buffet.setPlace(placeName);
        try {
            buffetDAO.addBuffet(buffet);
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }

    public static void complainFeedPoint(long id, String name) throws BotLogicException {
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
        } catch (BotLogicException e) {
            throw e;
        } catch (Exception e) {
            throw new UnexpectedException();
        }
    }
}
