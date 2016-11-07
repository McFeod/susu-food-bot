import api.ITelegramBotAPI;
import api.TelegramBotAPI;
import api.exceptions.*;
import api.receive.*;
import feedpointevents.FeedPointEventHandler;
import handlers.AdvicesEventHandler;
import handlers.MessagesEventHandler;

import java.util.*;


public class TelegramBot {

    public static void main(String[] args) {
        new TelegramBot();
    }

    private ITelegramBotAPI telegramBotAPI;
    private ITelegramBotReceiveListener receiveListener;
    private ITelegramBotReceiveListener receiveListenerParam1;
    private ITelegramBotReceiveListener receiveListenerParam2;

    public TelegramBot() {
        telegramBotAPI = new TelegramBotAPI();
        IMessageReceiver messageReceiver = new MessageReceiver(telegramBotAPI);

        receiveListener = new ReceiverListener(messageReceiver);
        receiveListenerParam1 = new ReceiverListenerParam1(messageReceiver);
        receiveListenerParam2 = new ReceiverListenerParam2(messageReceiver);

        telegramBotAPI.setOnReceiveListener(receiveListener);
        telegramBotAPI.setOnReceiveListenerParam1(receiveListenerParam1);
        telegramBotAPI.setOnReceiveListenerParam2(receiveListenerParam2);
    }

}
