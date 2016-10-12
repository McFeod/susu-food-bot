package api.exceptions;

public class EmptyUserMessagesList extends BotLogicException{
    @Override
    public String getMessage() {
        return "Пользователи пока ничего не написали.";
    }
}
