package api.receive;

import DAO.BuffetDAO;
import DAO.ProductDAO;
import DAO.UserDAO;
import api.Texts;
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
        String responseText = response.getText();
        System.out.println(response.getKind() + (responseText.isEmpty() ? "" : " \"" + responseText + "\""));
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
                    if (StringUtils.isNullOrEmpty(responseText)) {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.ADD_FEED_POINT);
                        userDAO.updateUser(user);
                        messageReceiver.onSendMessage(id, Texts.FEEDPOINT_NAME_PROMPT);
                    } else {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.ADD_FEED_POINT_PLACE);
                        user.setArgument(responseText);
                        userDAO.updateUser(user);
                        messageReceiver.onSendMessage(id, Texts.FEEDPOINT_PLACE_PROMPT);
                    }
                    break;
                case COMPLAIN:
                    if (StringUtils.isNullOrEmpty(responseText)) {
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
                        messageReceiver.onSendMessage(id, Texts.FEEDPOINT_NAME_PROMPT, buttons);
                    } else {
                        messageReceiver.onComplainFeedPoint(id, responseText);
                    }
                    break;

                case MSG_LIST:
                    messageReceiver.onGetMessages(id, responseText);
                    break;
                case RUN_OUT:
                    if (StringUtils.isNullOrEmpty(responseText)) {
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
                        messageReceiver.onSendMessage(id, Texts.FEEDPOINT_NAME_PROMPT, buttons);
                    } else {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.RUN_OUT_PRODUCT);
                        user.setArgument(responseText);
                        userDAO.updateUser(user);

                        ProductDAO productDAO = ProductDAO.getInstance();
                        Collection<Product> products = productDAO.getAllProducts();
                        List<String> buttons = new LinkedList<>();
                        for (Product product : products) {
                            buttons.add(product.getName());
                        }
                        messageReceiver.onSendMessage(id, Texts.PRODUCT_NAME_PROMPT, buttons);
                    }
                    break;

                case ADVICES:
                    messageReceiver.onGetAdvices(id, responseText);
                    break;
                case ADD_ADVICE:
                    if (StringUtils.isNullOrEmpty(responseText)) {
                        UserDAO userDAO = UserDAO.getInstance();
                        User user = userDAO.getUserById(id);
                        user.setState(UserState.ADD_ADVICE);
                        userDAO.updateUser(user);
                        messageReceiver.onSendMessage(id, Texts.ADVICE_TEXT_PROMPT);
                    } else {
                        messageReceiver.onAddAdvice(id, responseText);
                    }
                    break;
                case CANCEL:
                    messageReceiver.onSendMessage(id, Texts.CANCEL_FAILURE);
                    break;
            }
        }
        catch (BotLogicException e){
            messageReceiver.onMessageError(id, e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            messageReceiver.onMessageError(id, Texts.UNEXPECTED_ERROR);
        }
    }
}
