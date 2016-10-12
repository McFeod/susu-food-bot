package api.exceptions;

public class DuplicateFeedPointName extends BotLogicException{
    @Override
    public String getMessage() {
        return "Такое место уже есть.";

    }
}
