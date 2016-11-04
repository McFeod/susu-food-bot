import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import DAO.ProductDAO;
import DAO.BuffetDatabase;
import DAO.AdviceDAO;
import DAO.ProductsInStockDAO;
import DAO.ProductsNotInStockDAO;
import DAO.UserDAO;
import api.exceptions.FeedPointDoesNotExists;
import feedpointevents.FeedPointEventHandler;
import logic.Product;
import logic.Advice;
import logic.Buffet;
import logic.ProductsInStock;
import logic.ProductsNotInStock;
import logic.User;
import logic.UserState;
import util.HibernateUtil;


public class App {
    public static void main(String[] args) {
        UserDAO u = new UserDAO();
        User us = new User(53,"xren",125,UserState.state1);
        
        
        try {
            u.addUser(us);
            //u.updateUser(us);
            Collection buffets = u.getAllUsers();
            Iterator iterator = buffets.iterator();
            System.out.println(buffets.size());
            while (iterator.hasNext()) {
                us = (User)iterator.next();
                System.out.println(us.getState());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
