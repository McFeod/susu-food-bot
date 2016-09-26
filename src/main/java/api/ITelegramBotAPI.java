package api;

public interface ITelegramBotAPI {

    void sendMessage(Long id, String text);

    void setOnReceiveListener(ITelegramBotReceiveListener receiveListener);

}
