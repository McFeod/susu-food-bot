package api.exceptions;

public class FeedPointDoesNotExists extends BotLogicException {
    @Override
    public String getMessage() {
        return "Такое место не найдено.";
    }
}
