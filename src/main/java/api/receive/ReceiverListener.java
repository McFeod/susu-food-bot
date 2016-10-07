package api.receive;

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

        ReceiveMessageParser.MessageResponse response = ReceiveMessageParser.getKind(message);
        System.out.println(response.getKind()+" '"+response.getText()+"'");
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
                messageReceiver.onAddFeedPoint(id, response.getText());
                break;
            case COMPLAIN:
                messageReceiver.onComplainFeedPoint(id, response.getText());
                break;

            case MSG_LIST:
                messageReceiver.onGetMessages(id, response.getText());
                break;
            case RUN_OUT:
                messageReceiver.onRunOut(id, response.getText());
                break;

            case ADVICES:
                messageReceiver.onGetAdvices(id, response.getText());
                break;
            case ADD_ADVICE:
                messageReceiver.onAddAdvice(id, response.getText());
                break;
        }

    }
}
