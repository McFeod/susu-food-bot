package api.receive;

import DAO.BuffetDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import api.exceptions.BotLogicException;
import pojos.Buffet;
import pojos.Product;
import pojos.User;
import pojos.UserState;
import util.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ReceiverListener implements ITelegramBotReceiveListener {

    private IMessageReceiver messageReceiver;

    public ReceiverListener(IMessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public void onMessageReceive(Long id, String message) {
        if (messageReceiver == null) {
            return;
        }

        if (StringUtils.isNullOrEmpty(message)) {
            messageReceiver.onMessageError(id, "");
            return;
        }

        MessageResponse response = ReceiveMessageParser.getKind(message);
        System.out.println(response.getKind() + " \"" + response.getText() + "\"");
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
                    if (StringUtils.isNullOrEmpty(response.getText())) {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.ADD_FEED_POINT);
                        userDAO.updateUser(user);
                        messageReceiver.onSendMessage(id, "Введите название");
                    } else {
                        messageReceiver.onAddFeedPoint(id, response.getText());
                    }
                    break;
                case COMPLAIN:
                    if (StringUtils.isNullOrEmpty(response.getText())) {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.COMPLAIN);
                        userDAO.updateUser(user);

                        BuffetDAO buffetDAO = BuffetDAO.getInstance();
                        Collection<Buffet> buffets = buffetDAO.getAllBuffets();
                        List<String> buttons = new LinkedList<>();
                        for (Buffet buffet : buffets) {
                            buttons.add(buffet.getName());
                        }

                        messageReceiver.onSendMessage(id, "Введите название", buttons);
                    } else {
                        messageReceiver.onComplainFeedPoint(id, response.getText());
                    }
                    break;

                case MSG_LIST:
                    messageReceiver.onGetMessages(id, response.getText());
                    break;
                case RUN_OUT:
                    if (StringUtils.isNullOrEmpty(response.getText())) {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.RUN_OUT_BUFFET);
                        userDAO.updateUser(user);

                        BuffetDAO buffetDAO = BuffetDAO.getInstance();
                        Collection<Buffet> buffets = buffetDAO.getAllBuffets();
                        List<String> buttons = new LinkedList<>();
                        for (Buffet buffet : buffets) {
                            buttons.add(buffet.getName());
                        }

                        messageReceiver.onSendMessage(id, "Введите название", buttons);
                    } else {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.RUN_OUT_PRODUCT);
                        user.setArgument(response.getText());
                        userDAO.updateUser(user);

                        ProductDAO productDAO = ProductDAO.getInstance();
                        Collection<Product> products = productDAO.getAllProducts();
                        List<String> buttons = new LinkedList<>();
                        for (Product product : products) {
                            buttons.add(product.getName());
                        }

                        messageReceiver.onSendMessage(id, "Введите название", buttons);
                    }
                    break;

                case ADVICES:
                    messageReceiver.onGetAdvices(id, response.getText());
                    break;
                case ADD_ADVICE:
                    if (StringUtils.isNullOrEmpty(response.getText())) {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.ADD_ADVICE);
                        userDAO.updateUser(user);
                        messageReceiver.onSendMessage(id, "Введите текст");
                    } else {
                        messageReceiver.onAddAdvice(id, response.getText());
                    }
                    break;
            }
        }
        catch (BotLogicException e){
            messageReceiver.onMessageError(id, e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            messageReceiver.onMessageError(id, "Something unexpected");
        }
    }
}
