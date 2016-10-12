import api.ITelegramBotAPI;
import api.TelegramBotAPI;
import api.exceptions.BotLogicException;
import api.exceptions.NotImplementedException;
import api.receive.IMessageReceiver;
import api.receive.ITelegramBotReceiveListener;
import api.receive.ReceiverListener;

import java.util.ArrayList;
import java.util.List;

public class TelegramBot {

    public static void main(String[] args) {
        new TelegramBot();
    }

    private ITelegramBotAPI telegramBotAPI;
    private ITelegramBotReceiveListener receiveListener;

    public TelegramBot() {
        telegramBotAPI = new TelegramBotAPI();
        receiveListener = new ReceiverListener(new IMessageReceiver() {
            @Override
            public void onMessageError(Long id, String text) {
                telegramBotAPI.sendMessage(id, "Error: " + text);
            }

            @Override
            public void onStart(Long id) {

            }

            @Override
            public void onStop(Long id) throws BotLogicException {

            }

            @Override
            public void onHelp(Long id) throws BotLogicException {
                telegramBotAPI.sendMessage(id, "I can't help you");
            }

            @Override
            public void onGetFeedPoints(Long id) throws BotLogicException {
                List<String> feedPoints = new ArrayList<>();
                feedPoints.add("123");
                feedPoints.add("456");
                feedPoints.add("789");
                feedPoints.add("qwerty");
                telegramBotAPI.sendMessage(id, "Feed points:", feedPoints);
            }

            @Override
            public void onGetUserFeedPoints(Long id) throws BotLogicException {

            }

            @Override
            public void onAddFeedPoint(Long id, String text) throws BotLogicException {

            }

            @Override
            public void onComplainFeedPoint(Long id, String text) throws BotLogicException {
                 throw new NotImplementedException();
            }

            @Override
            public void onGetMessages(Long id, String text) throws BotLogicException {

            }

            @Override
            public void onRunOut(Long id, String text) throws BotLogicException {

            }

            @Override
            public void onGetAdvices(Long id, String text) throws BotLogicException {

            }

            @Override
            public void onAddAdvice(Long id, String text) throws BotLogicException {

            }

            @Override
            public void onMessageReceive(Long id, String message) {

            }
        });

        telegramBotAPI.setOnReceiveListener(receiveListener);
    }

}
