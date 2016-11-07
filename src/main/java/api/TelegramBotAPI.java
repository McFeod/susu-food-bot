package api;

import DAO.UserDAO;
import api.receive.ITelegramBotReceiveListener;
import logic.User;
import logic.UserState;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import util.HibernateUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static api.APIConstants.BOT_NAME;
import static api.APIConstants.BOT_TOKEN;

public class TelegramBotAPI implements ITelegramBotAPI {

    private TelegramBotsApi telegramBotsApi;
    private TelegramLongPollingBot telegramLongPollingBot;

    private ITelegramBotReceiveListener receiveListener;
    private ITelegramBotReceiveListener receiveListenerParam1;
    private ITelegramBotReceiveListener receiveListenerParam2;

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
                    if (update != null && update.hasMessage()) {
                        try {
                            UserDAO db = new UserDAO();
                            User user;
                            try {
                                user = db.getUserById(update.getMessage().getChatId());
                            } catch (ObjectNotFoundException e) {
                                user = new User(update.getMessage().getChatId(), "", 0, UserState.WAITING);
                                db.addUser(user);
                            }

                            if (user == null) {
                                user = new User(update.getMessage().getChatId(), "", 0, UserState.WAITING);
                                db.addUser(user);
                            }

                            switch (user.getState()) {
                                case WAITING:
                                    if (receiveListener != null)
                                        receiveListener.onMessageReceive(update.getMessage().getChatId(),
                                                update.getMessage().getText());
                                    break;
                                case ADD_FEED_POINT:
                                case COMPLAIN:
                                case RUN_OUT_BUFFET:
                                case ADD_ADVICE:
                                    if (receiveListenerParam1 != null)
                                        receiveListenerParam1.onMessageReceive(update.getMessage().getChatId(),
                                                update.getMessage().getText());
                                    break;
                                case RUN_OUT_PRODUCT:
                                    if (receiveListenerParam2 != null)
                                        receiveListenerParam2.onMessageReceive(update.getMessage().getChatId(),
                                                update.getMessage().getText());
                                    break;
                            }
                        } catch (SQLException e) {
                        }
                    }
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

    public void setOnReceiveListenerParam1(ITelegramBotReceiveListener receiveListener) {
        this.receiveListenerParam1 = receiveListener;
    }

    public void setOnReceiveListenerParam2(ITelegramBotReceiveListener receiveListener) {
        this.receiveListenerParam2 = receiveListener;
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
