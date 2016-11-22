package api.receive;

import api.exceptions.BotLogicException;

import java.util.List;

public interface IMessageReceiver extends ITelegramBotReceiveListener {

    void onMessageError(Long id, String text);

    void onStart(Long id) throws BotLogicException;

    void onStop(Long id);

    void onHelp(Long id) throws BotLogicException;

    // feed points

    void onGetFeedPoints(Long id) throws BotLogicException;

    void onGetUserFeedPoints(Long id) throws BotLogicException;

    void onAddFeedPoint(Long id, String buffet, String place) throws BotLogicException;

    void onComplainFeedPoint(Long id, String text) throws BotLogicException;

    // user messages

    void onGetMessages(Long id, String text) throws BotLogicException;

    void onRunOut(Long id, String buffet, String product) throws BotLogicException;

    // user advices

    void onGetAdvices(Long id, String text) throws BotLogicException;

    void onAddAdvice(Long id, String text) throws BotLogicException;

    //cancel

    void onCancel(Long id);

    // send messages

    void onSendMessage(Long id, String text);

    void onSendMessage(Long id, String text, List<String> buttons);
}
