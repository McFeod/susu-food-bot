package api.exceptions;

public class EmptyUserFeedPointList extends BotLogicException{
    @Override
    public String getMessage() {
        return "Пользователи пока не добавили новых точек питания.";
    }
}
