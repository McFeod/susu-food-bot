package api.receive;

public interface ITelegramBotReceiveListener {

    void onMessageReceive(Long id, String message);

}
