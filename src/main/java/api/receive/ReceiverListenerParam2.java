package api.receive;


import DAO.UserDAO;
import api.exceptions.BotLogicException;
import pojos.User;
import pojos.UserState;
import util.StringUtils;

import java.sql.SQLException;

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

            switch (user.getState()) {
                case RUN_OUT_PRODUCT:
                    user.setState(UserState.WAITING);
                    userDAO.updateUser(user);
                    String buffet = user.getArgument();
                    messageReceiver.onRunOut(id, buffet, message);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BotLogicException e) {
            messageReceiver.onMessageError(id, e.getMessage());
        }
    }
}
