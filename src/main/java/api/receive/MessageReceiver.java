package api.receive;

import api.ITelegramBotAPI;
import api.exceptions.*;
import handlers.AdvicesEventHandler;
import handlers.FeedPointEventHandler;
import handlers.MessagesEventHandler;
import util.Pair;
import util.StringUtils;

import java.util.List;

public class MessageReceiver implements IMessageReceiver {

    private ITelegramBotAPI telegramBotAPI;

    public MessageReceiver(ITelegramBotAPI telegramBotAPI) {
        this.telegramBotAPI = telegramBotAPI;
    }

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
        telegramBotAPI.sendMessage(id, "Feed points:\n" + StringUtils.join(feedPoints, "\n"));
    }

    @Override
    public void onGetUserFeedPoints(Long id) throws BotLogicException {
        List<String> feedPoints = FeedPointEventHandler.getUserFeedPoints();
        if (feedPoints.size() == 0)
            throw new EmptyUserFeedPointList();
        telegramBotAPI.sendMessage(id, "User feed points:\n" + StringUtils.join(feedPoints, "\n"));
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
        List<Pair<String, String>> messages = MessagesEventHandler.getMessages(text);

        if (messages.size() == 0)
            throw new EmptyUserMessagesList();

        String msg = "Messages:";
        for (Pair<String, String> entry : messages)
            msg += "\n" + entry.getKey() + " - " + entry.getValue();
        telegramBotAPI.sendMessage(id, msg);
    }

    @Override
    public void onRunOut(Long id, String buffet, String product) throws BotLogicException {
        MessagesEventHandler.runOut(buffet, product);
    }

    @Override
    public void onGetAdvices(Long id, String text) throws BotLogicException {
        List<String> advices = AdvicesEventHandler.getAdvices();
        if (advices.size() == 0)
            throw new EmptyAdviceList();
        telegramBotAPI.sendMessage(id, "Advices:\n" + StringUtils.join(advices, "\n"));
    }

    @Override
    public void onAddAdvice(Long id, String text) throws BotLogicException {
        AdvicesEventHandler.addAdvice(text);
    }

    @Override
    public void onMessageReceive(Long id, String message) {

    }

    @Override
    public void onSendMessage(Long id, String text) {
        telegramBotAPI.sendMessage(id, text);
    }

    @Override
    public void onSendMessage(Long id, String text, List<String> buttons) {
        telegramBotAPI.sendMessage(id, text, buttons);
    }

}
