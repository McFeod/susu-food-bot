package api;

public interface ITelegramBotReceiveListener {

    void onMessageReceive(Long id, String message);

}
