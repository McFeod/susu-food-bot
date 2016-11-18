package api;

import api.receive.ITelegramBotReceiveListener;

import java.util.List;

public interface ITelegramBotAPI {
    /**
     * отправить сообщение
     * @param id идентификатор чата
     * @param text текст
     */
    void sendMessage(Long id, String text);

    /**
     * отправить сообщение
     * @param id идентификатор чата
     * @param text текст
     * @param buttons кнопки для встроенной клавиатуры
     */
    void sendMessage(Long id, String text, List<String> buttons);

    void setOnReceiveListener(ITelegramBotReceiveListener receiveListener);

    void setOnReceiveListenerParam1(ITelegramBotReceiveListener receiveListener);

    void setOnReceiveListenerParam2(ITelegramBotReceiveListener receiveListener);

}
