package api;

import api.receive.ITelegramBotReceiveListener;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import java.util.ArrayList;
import java.util.List;

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
        sendMessage(id, text, null);
    }

    public void sendMessage(Long id, String text, List<String> buttons) {
        SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(id.toString());
        message.setText(text);

        if (buttons != null) {
            ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
            keyboard.setResizeKeyboard(true);
            keyboard.setOneTimeKeyboad(true);
            List<KeyboardRow> rows = new ArrayList<>(buttons.size());
            for (String str : buttons) {
                KeyboardRow row = new KeyboardRow();
                row.add(new KeyboardButton(str));
                rows.add(row);
            }
            keyboard.setKeyboard(rows);
            message.setReplyMarkup(keyboard);
        } else
            message.setReplyMarkup(new ReplyKeyboardHide());

        try {
            telegramLongPollingBot.sendMessage(message);
        } catch (TelegramApiException e) {
            System.err.println(e);
        }
    }
}
