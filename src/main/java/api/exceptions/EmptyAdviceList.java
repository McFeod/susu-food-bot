package api.exceptions;

public class EmptyAdviceList extends BotLogicException {
    @Override
    public String getMessage() {
        return "Пока никто не добавил подсказок.";
    }
}
