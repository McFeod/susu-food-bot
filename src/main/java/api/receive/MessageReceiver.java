package api.receive;

import api.ITelegramBotAPI;
import api.exceptions.*;
import handlers.AdvicesEventHandler;
import handlers.FeedPointEventHandler;
import handlers.MessagesEventHandler;
import util.StringUtils;

import java.util.List;
import java.util.Map;

public class MessageReceiver implements IMessageReceiver {

    private ITelegramBotAPI telegramBotAPI;

    public MessageReceiver(ITelegramBotAPI telegramBotAPI) {
        this.telegramBotAPI = telegramBotAPI;
    }

    @Override
    public void onMessageError(Long id, String text) {
        telegramBotAPI.sendMessage(id, "*Error:*\n" + text);
    }

    @Override
    public void onStart(Long id) {
    }

    @Override
    public void onStop(Long id) {
    }

    @Override
    public void onHelp(Long id) throws BotLogicException {
        telegramBotAPI.sendMessage(id,
                "/help - список команд для бота.\n" +
                "/feedpoints - список проверенных мест, можно поесть.\n" +
                "/userfeedpoints - список мест, где можно поесть.\n" +
                "/addfeedpoint <место> - добавить новое место, где можно поесть.\n" +
                "/complain <место> - кто-то наврал вам про буфет? Пожалуйтесь, и мы его найдем.\n" +
                "/msglist <место> - свежие новости о наличии вкусностей в ваших любимых буфетах.\n" +
                "/runout <место> - что-то закрылось/закончилось? Сообщите об этом всем.\n" +
                "/advices - узнать, что советуют другие пользователи.\n" +
                "/addadvice <текст> - что вы советуете другим пользователям? Добавьте свой совет.");
    }

    @Override
    public void onGetFeedPoints(Long id) throws BotLogicException {
        List<String> feedPoints = FeedPointEventHandler.getFeedPoints();
        if (feedPoints.isEmpty()) {
            throw new EmptyFeedPointList();
        }
        telegramBotAPI.sendMessage(id, "*Feed points:*\n" + StringUtils.join(feedPoints, "\n"));
    }

    @Override
    public void onGetUserFeedPoints(Long id) throws BotLogicException {
        List<String> feedPoints = FeedPointEventHandler.getUserFeedPoints();
        if (feedPoints.isEmpty()) {
            throw new EmptyUserFeedPointList();
        }
        telegramBotAPI.sendMessage(id, "*User feed points:*\n" + StringUtils.join(feedPoints, "\n"));
    }

    @Override
    public void onAddFeedPoint(Long id, String text) throws BotLogicException {
        FeedPointEventHandler.addFeedPoint(id,text);
        telegramBotAPI.sendMessage(id, "Место успешно добавлено.");
    }

    @Override
    public void onComplainFeedPoint(Long id, String text) throws BotLogicException {
        FeedPointEventHandler.complainFeedPoint(id,text);
        telegramBotAPI.sendMessage(id, "Жалоба принята.");
    }

    @Override
    public void onGetMessages(Long id, String text) throws BotLogicException {
        Map<String, List<String>> messages = MessagesEventHandler.getMessages(text);

        if (messages.isEmpty()) {
            throw new EmptyUserMessagesList();
        }

        StringBuilder builder = new StringBuilder();
        builder.append("*Messages:*");
        for (Map.Entry<String, List<String>> message : messages.entrySet()) {
            builder.append(String.format("\nВ \"%s\" закончились продукты:\n", message.getKey()));
            for (String product : message.getValue()) {
                builder.append(String.format("• %s\n", product));
            }
        }
        telegramBotAPI.sendMessage(id, builder.toString());
    }

    @Override
    public void onRunOut(Long id, String buffet, String product) throws BotLogicException {
        MessagesEventHandler.runOut(buffet, product);
        telegramBotAPI.sendMessage(id, "Сообщение зарегистрировано.");
    }

    @Override
    public void onGetAdvices(Long id, String text) throws BotLogicException {
        List<String> advices = AdvicesEventHandler.getAdvices();
        if (advices.isEmpty()) {
            throw new EmptyAdviceList();
        }
        telegramBotAPI.sendMessage(id, "*Advices:*\n" + StringUtils.join(advices, "\n\n"));
    }

    @Override
    public void onAddAdvice(Long id, String text) throws BotLogicException {
        AdvicesEventHandler.addAdvice(text);
        telegramBotAPI.sendMessage(id, "Совет успешно добавлен.");
    }

    @Override
    public void onSendMessage(Long id, String text) {
        telegramBotAPI.sendMessage(id, text);
    }

    @Override
    public void onSendMessage(Long id, String text, List<String> buttons) {
        telegramBotAPI.sendMessage(id, text, buttons);
    }

    // нужен ли этот метод?
    @Override
    public void onMessageReceive(Long id, String message) {
    }
}
