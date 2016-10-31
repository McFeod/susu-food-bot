package api.exceptions;

public class EmptyFeedPointList extends BotLogicException{
    @Override
    public String getMessage() {
        return "Список точек питания пуст.";
    }
}
