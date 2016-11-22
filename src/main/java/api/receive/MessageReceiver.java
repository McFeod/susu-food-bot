package api.receive;

import api.ITelegramBotAPI;
import api.Texts;
import api.exceptions.*;
import handlers.AdvicesEventHandler;
import handlers.FeedPointEventHandler;
import handlers.MessagesEventHandler;
import util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageReceiver implements IMessageReceiver {

    private ITelegramBotAPI telegramBotAPI;

    public MessageReceiver(ITelegramBotAPI telegramBotAPI) {
        this.telegramBotAPI = telegramBotAPI;
    }

    @Override
    public void onMessageError(Long id, String text) {
        telegramBotAPI.sendMessage(id, Texts.ERROR_HEADER + text);
    }

    @Override
    public void onStart(Long id) throws BotLogicException {
        telegramBotAPI.sendMessage(id, Texts.START_PROMPT);
    }

    @Override
    public void onStop(Long id) {
    }

    @Override
    public void onHelp(Long id) throws BotLogicException {
        telegramBotAPI.sendMessage(id, Texts.HELP_MESSAGE);
    }

    @Override
    public void onGetFeedPoints(Long id) throws BotLogicException {
        HashMap<String, String> feedPoints = FeedPointEventHandler.getFeedPoints();
        if (feedPoints.isEmpty()) {
            throw new EmptyFeedPointList();
        }
        telegramBotAPI.sendMessage(id, Texts.FEEDPOINTS_HEADER
                + StringUtils.join(feedPoints.entrySet(), "\n"));
    }

    @Override
    public void onGetUserFeedPoints(Long id) throws BotLogicException {
        HashMap<String, String> feedPoints = FeedPointEventHandler.getUserFeedPoints();
        if (feedPoints.isEmpty()) {
            throw new EmptyUserFeedPointList();
        }
        telegramBotAPI.sendMessage(id, Texts.USERFEEDPOINTS_HEADER
                + StringUtils.join(feedPoints.entrySet(), "\n"));
    }

    @Override
    public void onAddFeedPoint(Long id, String buffet, String place) throws BotLogicException {
        FeedPointEventHandler.addFeedPoint(id, buffet, place);
        telegramBotAPI.sendMessage(id, Texts.FEEDPOINT_CONFIRMATION);
    }

    @Override
    public void onComplainFeedPoint(Long id, String text) throws BotLogicException {
        FeedPointEventHandler.complainFeedPoint(id, text);
        telegramBotAPI.sendMessage(id, Texts.COMPLAIN_CONFIRMATION);
    }

    @Override
    public void onGetMessages(Long id, String text) throws BotLogicException {
        Map<String, List<String>> messages = MessagesEventHandler.getMessages(text);

        if (messages.isEmpty()) {
            throw new EmptyUserMessagesList();
        }

        StringBuilder builder = new StringBuilder();
        builder.append(Texts.MESSAGES_HEADER);
        for (Map.Entry<String, List<String>> message : messages.entrySet()) {
            builder.append(String.format(Texts.MESSAGES_PLACE_DELIMITER, message.getKey()));
            for (String product : message.getValue()) {
                builder.append(String.format("• %s\n", product));
            }
        }
        telegramBotAPI.sendMessage(id, builder.toString());
    }

    @Override
    public void onRunOut(Long id, String buffet, String product) throws BotLogicException {
        MessagesEventHandler.runOut(buffet, product);
        telegramBotAPI.sendMessage(id, Texts.RUNOUT_CONFIRMATION);
    }

    @Override
    public void onGetAdvices(Long id, String text) throws BotLogicException {
        List<String> advices = AdvicesEventHandler.getAdvices();
        if (advices.isEmpty()) {
            throw new EmptyAdviceList();
        }
        telegramBotAPI.sendMessage(id, Texts.ADVICES_HEADER + StringUtils.join(advices, "\n\n"));
    }

    @Override
    public void onAddAdvice(Long id, String text) throws BotLogicException {
        AdvicesEventHandler.addAdvice(text);
        telegramBotAPI.sendMessage(id, Texts.ADVICE_CONFIRMATION);
    }

    @Override
    public void onCancel(Long id) {
        telegramBotAPI.sendMessage(id, Texts.CANCEL_CONFIRMATION);
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
