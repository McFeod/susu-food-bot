package api.exceptions;

public class EmptyRunOutSubject extends BotLogicException {
    @Override
    public String getMessage() {
        return "Нет сообщений о том, что что-то закончилось.";
    }
}
