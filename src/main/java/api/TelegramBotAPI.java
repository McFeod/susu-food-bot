package api;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class TelegramBotAPI implements ITelegramBotAPI {

    private final String BOT_TOKEN = "token";
    private final String BOT_NAME = "susufoodbot";

    private TelegramBotsApi telegramBotsApi;
    private TelegramLongPollingBot telegramLongPollingBot;

    private ITelegramBotReceiveListener receiveListener;

    public TelegramBotAPI() {
        telegramBotsApi = new TelegramBotsApi();

        try {
            telegramLongPollingBot = new TelegramLongPollingBot() {
                @Override
                public String getBotToken() {
                    return BOT_TOKEN;
                }

                public String getBotUsername() {
                    return BOT_NAME;
                }

                public void onUpdateReceived(Update update) {
                    if (receiveListener != null && update != null && update.hasMessage())
                        receiveListener.onMessageReceive(update.getMessage().getChatId(),
                                update.getMessage().getText());
                }
            };
            telegramBotsApi.registerBot(telegramLongPollingBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setOnReceiveListener(ITelegramBotReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    public void sendMessage(Long id, String text) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(id.toString());
        message.setText(text);
        try {
            telegramLongPollingBot.sendMessage(message);
        } catch (TelegramApiException e) {
            System.err.println(e);
        }
    }
}
