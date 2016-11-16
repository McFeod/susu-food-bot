package feedpointevents;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import DAO.BuffetDAO;
import api.exceptions.FeedPointDoesNotExists;
import logic.Buffet;

public class FeedPointEventHandler {
    public static ArrayList<String> getFeedPoints() {
        ArrayList<String> feedPoints = new ArrayList<String>();
        try {
            BuffetDAO BDB = new BuffetDAO();
            Collection buffets = BDB.getBuffetsByAdmin(true);
            Iterator iterator = buffets.iterator();
            while (iterator.hasNext()) {
                Buffet buf = (Buffet) iterator.next();
                feedPoints.add(buf.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedPoints;
    }

    public static ArrayList<String> getUserFeedPoints() {
        ArrayList<String> feedPoints = new ArrayList<String>();
        try {
            BuffetDAO BDB = new BuffetDAO();
            Collection buffets = BDB.getBuffetsByAdmin(false);
            Iterator iterator = buffets.iterator();
            while (iterator.hasNext()) {
                Buffet buf = (Buffet) iterator.next();
                feedPoints.add(buf.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedPoints;
    }

    public static void addFeedPoint(String name) {
        BuffetDAO BDB = new BuffetDAO();
        Buffet buf = new Buffet();
        buf.setIsAdmin(false);
        buf.setIsComplained(false);
        buf.setName(name);
        try {
            BDB.addBuffet(buf);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void complainFeedPoint(String name) throws FeedPointDoesNotExists {
        BuffetDAO BDB = new BuffetDAO();
        try {
            Collection buffets = BDB.getBuffetsByName(name);
            Iterator iterator = buffets.iterator();
            if (iterator.hasNext()) {
                Buffet buf = (Buffet) iterator.next();
                buf.setIsComplained(true);
                BDB.updateBuffet(buf);
            } else {
                throw new FeedPointDoesNotExists();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
