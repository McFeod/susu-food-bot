package api.exceptions;

public class MessageTooLongException extends BotLogicException {
    @Override
    public String getMessage() {
        return "Длина сообщения превышает 1024 символа. tl;dr";
    }
}
