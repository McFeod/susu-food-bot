import api.ITelegramBotAPI;
import api.ITelegramBotReceiveListener;
import api.TelegramBotAPI;

public class TelegramBot {

    public static void main(String[] args) {
        new TelegramBot();
    }

    private ITelegramBotAPI telegramBotAPI;

    public TelegramBot() {
        telegramBotAPI = new TelegramBotAPI();

        telegramBotAPI.setOnReceiveListener(new ITelegramBotReceiveListener() {
            public void onMessageReceive(Long id, String message) {
                telegramBotAPI.sendMessage(id, "Hello " + message + "\nHello " + message);
            }
        });
    }

}
