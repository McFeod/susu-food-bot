package api.receive;

import api.exceptions.BotLogicException;

public interface IMessageReceiver extends ITelegramBotReceiveListener {

    void onMessageError(Long id, String text);


    void onStart(Long id);

    void onStop(Long id) throws BotLogicException;

    void onHelp(Long id) throws BotLogicException;

    //feed points

    void onGetFeedPoints(Long id) throws BotLogicException;

    void onGetUserFeedPoints(Long id) throws BotLogicException;

    void onAddFeedPoint(Long id, String text) throws BotLogicException;

    void onComplainFeedPoint(Long id, String text) throws BotLogicException;

    //user messages

    void onGetMessages(Long id, String text) throws BotLogicException;

    void onRunOut(Long id, String text) throws BotLogicException;

    //user advices

    void onGetAdvices(Long id, String text) throws BotLogicException;

    void onAddAdvice(Long id, String text) throws BotLogicException;
}
