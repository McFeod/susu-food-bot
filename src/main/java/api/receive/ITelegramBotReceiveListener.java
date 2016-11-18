package api.receive;

// нужен ли этот интерфейс?
public interface ITelegramBotReceiveListener {
    void onMessageReceive(Long id, String message);
}
