import java.util.Collection;
import java.util.Iterator;

import DAO.UserDAO;
import logic.User;
import logic.UserState;


public class App {
    public static void main(String[] args) {
        UserDAO u = new UserDAO();
        User us = new User(53,"xren",125,UserState.WAITING);
        
        
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
