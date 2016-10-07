package api.receive;

public interface IMessageReceiver extends ITelegramBotReceiveListener {

    void onMessageError(Long id, String text);


    void onStart(Long id);

    void onStop(Long id);

    void onHelp(Long id);

    //feed points

    void onGetFeedPoints(Long id);

    void onGetUserFeedPoints(Long id);

    void onAddFeedPoint(Long id, String text);

    void onComplainFeedPoint(Long id, String text);

    //user messages

    void onGetMessages(Long id, String text);

    void onRunOut(Long id, String text);

    //user advices

    void onGetAdvices(Long id, String text);

    void onAddAdvice(Long id, String text);
}
