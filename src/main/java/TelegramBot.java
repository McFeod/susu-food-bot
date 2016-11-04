import api.ITelegramBotAPI;
import api.TelegramBotAPI;
import api.exceptions.*;
import api.receive.IMessageReceiver;
import api.receive.ITelegramBotReceiveListener;
import api.receive.ReceiverListener;
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
                telegramBotAPI.sendMessage(id, "/help - Список команд для бота\n" +
                        "/feedpoints - Где можно поесть\n" +
                        "/userfeedpoints - Где можно поесть (от пользователей)\n" +
                        "/addfeedpoint <текст> - расскажите, где можно поесть. В тексте после команды  кратко опишите, что это и где находится.\n" +
                        "/complain <текст> - кто-то наврал вам про буфет? Пожалуйтесь, и мы его найдем.\n" +
                        "/msglist <место> - свежие новости о наличии вкусностей в ваших любимых буфетах.\n" +
                        "/runout <место> <текст> - что-то закрылось/закончилось? В тексте после команды напишите, где больше нельзя поесть, а если точка еще работает, уточните, что закончилось.\n" +
                        "/advices - узнать, что советуют другие пользователи.\n" +
                        "/addadvice - что вы советуете другим пользователям? Добавьте свой совет.");
            }

            @Override
            public void onGetFeedPoints(Long id) throws BotLogicException {
                List<String> feedPoints = FeedPointEventHandler.getFeedPoints();
                if (feedPoints.size() == 0)
                    throw new EmptyFeedPointList();
                telegramBotAPI.sendMessage(id, "Feed points:\n" + String.join("\n", feedPoints));
            }

            @Override
            public void onGetUserFeedPoints(Long id) throws BotLogicException {
                List<String> feedPoints = FeedPointEventHandler.getUserFeedPoints();
                if (feedPoints.size() == 0)
                    throw new EmptyUserFeedPointList();
                telegramBotAPI.sendMessage(id, "User feed points:\n" + String.join("\n", feedPoints));
            }

            @Override
            public void onAddFeedPoint(Long id, String text) throws BotLogicException {
                FeedPointEventHandler.addFeedPoint(text);
            }

            @Override
            public void onComplainFeedPoint(Long id, String text) throws BotLogicException {
                FeedPointEventHandler.complainFeedPoint(text);
            }

            @Override
            public void onGetMessages(Long id, String text) throws BotLogicException {
                HashMap<String, String> messages = MessagesEventHandler.getMessages(text);

                if (messages.size() == 0)
                    throw new EmptyUserMessagesList();

                String msg = "Messages:";
                for (Map.Entry<String, String> entry : messages.entrySet())
                    msg += "\n" + entry.getKey() + " - " + entry.getValue();
                telegramBotAPI.sendMessage(id, msg);
            }

            @Override
            public void onRunOut(Long id, String text) throws BotLogicException {
                String[] params = text.split(" ");
                if (params.length != 2)
                    throw new WrongRunOutParams();
                MessagesEventHandler.runOut(params[0], params[1]);
            }

            @Override
            public void onGetAdvices(Long id, String text) throws BotLogicException {
                List<String> advices = AdvicesEventHandler.getAdvices();
                if (advices.size() == 0)
                    throw new EmptyAdviceList();
                telegramBotAPI.sendMessage(id, "Advices:\n" + String.join("\n", advices));
            }

            @Override
            public void onAddAdvice(Long id, String text) throws BotLogicException {
                AdvicesEventHandler.addAdvice(text);
            }

            @Override
            public void onMessageReceive(Long id, String message) {

            }
        });

        telegramBotAPI.setOnReceiveListener(receiveListener);
    }

}
