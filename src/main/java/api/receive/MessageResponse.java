package api.receive;

public class MessageResponse {
    public static MessageResponse error() {
        return new MessageResponse("", MessageKind.ERROR);
    }

    private String      text; // текст сообщения, расположенный после команды
    private MessageKind kind; // тип сообщения

    public MessageResponse(String text, MessageKind kind) {
        this.text = text;
        this.kind = kind;
    }

    public String getText() {
        return text;
    }

    public MessageKind getKind() {
        return kind;
    }
}
