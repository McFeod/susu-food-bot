package api;

import DAO.UserDAO;
import api.receive.ITelegramBotReceiveListener;
import org.hibernate.ObjectNotFoundException;
import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import pojos.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static api.APIConstants.BOT_NAME;
import static api.APIConstants.BOT_TOKEN;

public class TelegramBotAPI implements ITelegramBotAPI {

    private TelegramLongPollingBot telegramLongPollingBot;

    private ITelegramBotReceiveListener receiveListener;
    private ITelegramBotReceiveListener receiveListenerParam1;
    private ITelegramBotReceiveListener receiveListenerParam2;

    public TelegramBotAPI() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramLongPollingBot = new TelegramLongPollingBot() {
                @Override
                public String getBotToken() {
                    return BOT_TOKEN;
                }

                @Override
                public String getBotUsername() {
                    return BOT_NAME;
                }

                @Override
                public void onUpdateReceived(Update update) {
                    if ((update != null) && update.hasMessage()) {
                        try {
                            UserDAO userDAO = UserDAO.getInstance();
                            User user;
                            try {
                                user = userDAO.getUserById(update.getMessage().getChatId());
                            } catch (ObjectNotFoundException e) {
                                user = new User(update.getMessage().getChatId());
                                userDAO.addUser(user);
                            }

                            // все-таки эта проверка нужна, потому что кто-то кидает SQLException
                            // а ловит ObjectNotFoundException
                            if (user == null) {
                                user = new User(update.getMessage().getChatId());
                                userDAO.addUser(user);
                            }

                            switch (user.getState()) {
                                case WAITING:
                                    if (receiveListener != null)
                                        receiveListener.onMessageReceive(
                                                update.getMessage().getChatId(),
                                                update.getMessage().getText());
                                    break;
                                case ADD_ADVICE:
                                case ADD_FEED_POINT:
                                case COMPLAIN:
                                case RUN_OUT_BUFFET:
                                    if (receiveListenerParam1 != null)
                                        receiveListenerParam1.onMessageReceive(
                                                update.getMessage().getChatId(),
                                                update.getMessage().getText());
                                    break;
                                case RUN_OUT_PRODUCT:
                                    if (receiveListenerParam2 != null)
                                        receiveListenerParam2.onMessageReceive(
                                                update.getMessage().getChatId(),
                                                update.getMessage().getText());
                                    break;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            telegramBotsApi.registerBot(telegramLongPollingBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setOnReceiveListener(ITelegramBotReceiveListener receiveListener) {
        this.receiveListener = receiveListener;
    }

    @Override
    public void setOnReceiveListenerParam1(ITelegramBotReceiveListener receiveListener) {
        this.receiveListenerParam1 = receiveListener;
    }

    @Override
    public void setOnReceiveListenerParam2(ITelegramBotReceiveListener receiveListener) {
        this.receiveListenerParam2 = receiveListener;
    }

    @Override
    public void sendMessage(Long id, String text) {
        sendMessage(id, text, null);
    }

    @Override
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
            for (String button : buttons) {
                KeyboardRow row = new KeyboardRow();
                row.add(new KeyboardButton(button));
                rows.add(row);
            }
            keyboard.setKeyboard(rows);
            message.setReplyMarkup(keyboard);
        } else {
            message.setReplyMarkup(new ReplyKeyboardHide());
        }

        try {
            telegramLongPollingBot.sendMessage(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
