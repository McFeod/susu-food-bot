package api.exceptions;

public class EmptyFeedPoint extends BotLogicException {
    @Override
    public String getMessage() {
        return "Не указано название точки питания.";
    }
}
