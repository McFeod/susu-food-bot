package api.exceptions;

public class FeedPointNotFound extends BotLogicException {
    @Override
    public String getMessage() {
        return "Точка питания не найдена.";
    }
}
