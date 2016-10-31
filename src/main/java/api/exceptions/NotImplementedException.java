package api.exceptions;

public class NotImplementedException extends BotLogicException{
    @Override
    public String getMessage() {
        return "Команда пока не реализована. Но скоро будет!";
    }
}

