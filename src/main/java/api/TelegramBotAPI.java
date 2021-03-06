package api;

import DAO.UserDAO;
import api.exceptions.BotLogicException;
import api.exceptions.MessageTooLongException;
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
import pojos.User;

import java.util.ArrayList;
import java.util.Arrays;
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
                    if ((update != null) && update.hasMessage() && update.getMessage().hasText()) {
                        Long updateId = update.getMessage().getChatId();
                        String updateText = update.getMessage().getText();
                        try {
                            if (updateText.length() > 1024) {
                                throw new MessageTooLongException();
                            }

                            try {
                                UserDAO userDAO = UserDAO.getInstance();
                                User user;
                                try {
                                    user = userDAO.getUserById(updateId);
                                } catch (Exception e) {
                                    user = new User(updateId);
                                    try {
                                        userDAO.addUser(user);
                                    } catch (Exception e2) {
                                        TelegramBotAPI.this.sendMessage(updateId,
                                            Texts.ERROR_HEADER + Texts.UNEXPECTED_ERROR);
                                        return;
                                    }
                                }

                                // все-таки эта проверка нужна, потому что кто-то кидает SQLException
                                // а ловит ObjectNotFoundException
                                if (user == null) {
                                    user = new User(updateId);
                                    try {
                                        userDAO.addUser(user);
                                    } catch (Exception e) {
                                        TelegramBotAPI.this.sendMessage(updateId,
                                            Texts.ERROR_HEADER + Texts.UNEXPECTED_ERROR);
                                        return;
                                    }
                                }

                                switch (user.getState()) {
                                    case WAITING:
                                        if (receiveListener != null)
                                            receiveListener.onMessageReceive(
                                                updateId,
                                                updateText);
                                        break;
                                    case ADD_ADVICE:
                                    case ADD_FEED_POINT:
                                    case COMPLAIN:
                                    case RUN_OUT_BUFFET:
                                        if (receiveListenerParam1 != null)
                                            receiveListenerParam1.onMessageReceive(
                                                updateId,
                                                updateText);
                                        break;
                                    case ADD_FEED_POINT_PLACE:
                                    case RUN_OUT_PRODUCT:
                                        if (receiveListenerParam2 != null)
                                            receiveListenerParam2.onMessageReceive(
                                                updateId,
                                                updateText);
                                        break;
                                }
                            } catch (Exception e) {
                                TelegramBotAPI.this.sendMessage(updateId,
                                    Texts.ERROR_HEADER + Texts.UNEXPECTED_ERROR);
                            }
                        } catch (BotLogicException e) {
                            TelegramBotAPI.this.sendMessage(updateId, Texts.ERROR_HEADER + e.getMessage());
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
            final int SIZE = 4096;
            List<String> messages = new ArrayList();
            while (text.length() > SIZE)
            {
                messages.add(text.substring(0, SIZE));
                text = text.substring(SIZE-1,text.length());
            }
            messages.add(text);
            for (int i = 0; i < messages.size(); i++)
            {
                message.setText(messages.get(i));
                telegramLongPollingBot.sendMessage(message);
            }
        } catch (TelegramApiException e) {
            //чтобы не вылетел e.printStackTrace();
        }
    }
}
