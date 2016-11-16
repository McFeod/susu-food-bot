package api.exceptions;

public class EmptyAdviceText extends BotLogicException {
    @Override
    public String getMessage() {
        return "После команды /addadvice не забудьте написать сам совет.";
    }
}
