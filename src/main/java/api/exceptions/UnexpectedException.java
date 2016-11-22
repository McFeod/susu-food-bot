package api.exceptions;

import api.Texts;

public class UnexpectedException extends BotLogicException {
    @Override
    public String getMessage() {
        return Texts.UNEXPECTED_ERROR;
    }
}
