package api.receive;


import DAO.UserDAO;
import api.Texts;
import api.exceptions.BotLogicException;
import pojos.User;
import pojos.UserState;
import util.StringUtils;


public class ReceiverListenerParam2 implements ITelegramBotReceiveListener {

    private IMessageReceiver messageReceiver;

    public ReceiverListenerParam2(IMessageReceiver messageReceiver) {
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
                String buffet;
                switch (user.getState()) {
                    case ADD_FEED_POINT_PLACE:
                        user.setState(UserState.WAITING);
                        userDAO.updateUser(user);
                        buffet = user.getArgument();
                        messageReceiver.onAddFeedPoint(id, buffet, message);
                        break;
                    case RUN_OUT_PRODUCT:
                        user.setState(UserState.WAITING);
                        userDAO.updateUser(user);
                        buffet = user.getArgument();
                        messageReceiver.onRunOut(id, buffet, message);
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
