package api.receive;


import DAO.UserDAO;
import api.exceptions.BotLogicException;
import logic.User;
import logic.UserState;

import java.sql.SQLException;

public class ReceiverListenerParam2 implements ITelegramBotReceiveListener {

    private IMessageReceiver messageReceiver;

    public ReceiverListenerParam2(IMessageReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public void onMessageReceive(Long id, String message) {
        if (messageReceiver == null)
            return;

        if (message == null || message.isEmpty()) {
            messageReceiver.onMessageError(id, "");
            return;
        }

        try {
            UserDAO db = new UserDAO();
            User user = db.getUserById(id);

            switch (user.getState()) {
                case RUN_OUT_PRODUCT:
                    user.setState(UserState.WAITING);
                    db.updateUser(user);
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
