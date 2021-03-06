package api.receive;

import DAO.ProductDAO;
import DAO.UserDAO;
import api.Texts;
import api.exceptions.BotLogicException;
import pojos.Product;
import pojos.User;
import pojos.UserState;
import util.StringUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ReceiverListenerParam1 implements ITelegramBotReceiveListener {

    private IMessageReceiver messageReceiver;

    public ReceiverListenerParam1(IMessageReceiver messageReceiver) {
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

        try {
            UserDAO userDAO = UserDAO.getInstance();
            User user = userDAO.getUserById(id);

            MessageResponse response = ReceiveMessageParser.getKind(message);
            if (response.getKind() == MessageKind.CANCEL) {
                user.setState(UserState.WAITING);
                userDAO.updateUser(user);
                messageReceiver.onCancel(id);
            } else {
                switch (user.getState()) {
                    case ADD_FEED_POINT:
                        user.setState(UserState.ADD_FEED_POINT_PLACE);
                        user.setArgument(message);
                        userDAO.updateUser(user);
                        System.out.println("addfeedpoint " + message);
                        messageReceiver.onSendMessage(id, Texts.FEEDPOINT_PLACE_PROMPT);
                        break;
                    case COMPLAIN:
                        user.setState(UserState.WAITING);
                        userDAO.updateUser(user);
                        System.out.println("complain " + message);
                        messageReceiver.onComplainFeedPoint(id, message);
                        break;
                    case RUN_OUT_BUFFET:
                        user.setState(UserState.RUN_OUT_PRODUCT);
                        user.setArgument(message);
                        userDAO.updateUser(user);
                        System.out.println("runout " + message);

                        ProductDAO productDAO = ProductDAO.getInstance();
                        Collection<Product> products = productDAO.getAllProducts();
                        List<String> buttons = new LinkedList<>();
                        for (Product product : products) {
                            buttons.add(product.getName());
                        }

                        messageReceiver.onSendMessage(id, Texts.PRODUCT_NAME_PROMPT, buttons);
                        break;
                    case ADD_ADVICE:
                        user.setState(UserState.WAITING);
                        userDAO.updateUser(user);
                        System.out.println("addadvice " + message);
                        messageReceiver.onAddAdvice(id, message);
                        break;
                }
            }
        } catch (BotLogicException e) {
            messageReceiver.onMessageError(id, e.getMessage());
        } catch (Exception e) {
            messageReceiver.onMessageError(id, Texts.UNEXPECTED_ERROR);
        }
    }
}
