package api.exceptions;

public class NotImplementedException extends BotLogicException{
    @Override
    public String getMessage() {
        return "Неизвестная команда. Может, вы опечатались?";
    }
}

