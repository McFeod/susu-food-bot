package api.exceptions;

public class WrongRunOutParams extends BotLogicException{
    @Override
    public String getMessage() {
        return "Неправильная структура команды. (Добавить описание команды)";
    }
}
