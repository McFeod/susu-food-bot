package api.exceptions;

public class EmptyComplainText extends BotLogicException {
    @Override
    public String getMessage() {
        return "Все-таки ни на кого не жалуетесь?";
    }
}
