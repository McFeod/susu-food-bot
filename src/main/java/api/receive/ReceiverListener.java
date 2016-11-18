package api.receive;


import DAO.BuffetDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import api.exceptions.BotLogicException;
import pojos.Buffet;
import pojos.Product;
import pojos.User;
import pojos.UserState;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ReceiverListener implements ITelegramBotReceiveListener {

    private IMessageReceiver messageReceiver;

    public ReceiverListener(IMessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public void onMessageReceive(Long id, String message) {
        if (messageReceiver == null)
            return;

        if (message == null || message.isEmpty()) {
            messageReceiver.onMessageError(id, "");
            return;
        }

        MessageResponse response = ReceiveMessageParser.getKind(message);
        System.out.println(response.getKind()+" '"+response.getText()+"'");
        try {
            switch (response.getKind()) {
                case ERROR:
                    messageReceiver.onMessageError(id, message);
                    break;

                case START:
                    messageReceiver.onStart(id);
                    break;
                case STOP:
                    messageReceiver.onStop(id);
                    break;
                case HELP:
                    messageReceiver.onHelp(id);
                    break;

                case FEED_POINTS:
                    messageReceiver.onGetFeedPoints(id);
                    break;
                case USER_FEED_POINTS:
                    messageReceiver.onGetUserFeedPoints(id);
                    break;
                case ADD_FEED_POINT:
                    if (response.getText() == null || response.getText().isEmpty()) {
                        UserDAO db = new UserDAO();
                        User user = db.getUserById(id);
                        user.setState(UserState.ADD_FEED_POINT);
                        db.updateUser(user);
                        messageReceiver.onSendMessage(id, "Введите название");
                    } else
                        messageReceiver.onAddFeedPoint(id, response.getText());
                    break;
                case COMPLAIN:
                    if (response.getText() == null || response.getText().isEmpty()) {
                        UserDAO db = new UserDAO();
                        User user = db.getUserById(id);
                        user.setState(UserState.COMPLAIN);
                        db.updateUser(user);

                        BuffetDAO buffetDB = new BuffetDAO();
                        Collection<Buffet> buffets = buffetDB.getAllBuffets();
                        List<String> buttons = new LinkedList<>();
                        for (Buffet buffet : buffets)
                            buttons.add(buffet.getName());

                        messageReceiver.onSendMessage(id, "Введите название", buttons);
                    } else
                        messageReceiver.onComplainFeedPoint(id, response.getText());
                    break;

                case MSG_LIST:
                    messageReceiver.onGetMessages(id, response.getText());
                    break;
                case RUN_OUT:
                    if (response.getText() == null || response.getText().isEmpty()) {
                        UserDAO db = new UserDAO();
                        User user = db.getUserById(id);
                        user.setState(UserState.RUN_OUT_BUFFET);
                        db.updateUser(user);

                        BuffetDAO buffetDB = new BuffetDAO();
                        Collection<Buffet> buffets = buffetDB.getAllBuffets();
                        List<String> buttons = new LinkedList<>();
                        for (Buffet buffet : buffets)
                            buttons.add(buffet.getName());

                        messageReceiver.onSendMessage(id, "Введите название", buttons);
                    } else {
                        UserDAO db = new UserDAO();
                        User user = db.getUserById(id);
                        user.setState(UserState.RUN_OUT_PRODUCT);
                        user.setArgument(response.getText());
                        db.updateUser(user);

                        ProductDAO productDB = new ProductDAO();
                        Collection<Product> products = productDB.getAllProducts();
                        List<String> buttons = new LinkedList<>();
                        for (Product product : products)
                            buttons.add(product.getName());

                        messageReceiver.onSendMessage(id, "Введите название", buttons);
                    }
                    break;

                case ADVICES:
                    messageReceiver.onGetAdvices(id, response.getText());
                    break;
                case ADD_ADVICE:
                    if (response.getText() == null || response.getText().isEmpty()) {
                        UserDAO db = new UserDAO();
                        User user = db.getUserById(id);
                        user.setState(UserState.ADD_ADVICE);
                        db.updateUser(user);
                        messageReceiver.onSendMessage(id, "Введите текст");
                    } else
                        messageReceiver.onAddAdvice(id, response.getText());
                    break;
            }
        }
        catch (BotLogicException e){
            messageReceiver.onMessageError(id, e.getMessage());
        }
        catch (Exception e){
            System.out.println(e);
            messageReceiver.onMessageError(id, "Something unexpected");
        }
    }
}