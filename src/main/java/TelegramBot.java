import api.ITelegramBotAPI;
import api.TelegramBotAPI;
import api.receive.*;


public class TelegramBot {

    public static void main(String[] args) {
        new TelegramBot();
    }

    public TelegramBot() {
        ITelegramBotAPI telegramBotAPI = new TelegramBotAPI();
        IMessageReceiver messageReceiver = new MessageReceiver(telegramBotAPI);

        ITelegramBotReceiveListener receiveListener = new ReceiverListener(messageReceiver);
        ITelegramBotReceiveListener receiveListenerParam1 = new ReceiverListenerParam1(messageReceiver);
        ITelegramBotReceiveListener receiveListenerParam2 = new ReceiverListenerParam2(messageReceiver);

        telegramBotAPI.setOnReceiveListener(receiveListener);
        telegramBotAPI.setOnReceiveListenerParam1(receiveListenerParam1);
        telegramBotAPI.setOnReceiveListenerParam2(receiveListenerParam2);
    }

}
