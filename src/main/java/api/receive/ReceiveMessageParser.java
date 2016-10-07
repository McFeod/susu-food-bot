package api.receive;

import java.util.HashMap;
import java.util.Map;

public class ReceiveMessageParser {

    public enum MessageKind {ERROR, START, STOP, HELP,
        FEED_POINTS, USER_FEED_POINTS, ADD_FEED_POINT, COMPLAIN,
        MSG_LIST, RUN_OUT,
        ADVICES, ADD_ADVICE}

    public static class MessageResponse {

        private String text;//текст сообщения, расположенный после команды
        private MessageKind kind;//тип сообщения

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

    //словарь, где каждому типу сообщения соответствует массив команд
    private static HashMap<MessageKind, String[]> map;

    public static MessageResponse getKind(String msg) {
        init();

        if (msg == null)
            return new MessageResponse("", MessageKind.ERROR);

        msg = msg.trim();//удаляет пробелы в начале и в конце

        if (msg.isEmpty())
            return new MessageResponse("", MessageKind.ERROR);

        //поиск команды
        for (Map.Entry<MessageKind, String[]> entry : map.entrySet())
            for (String str : entry.getValue()) {
                if (msg.toLowerCase().startsWith(str + " "))
                    return new MessageResponse(msg.substring(str.length() + 1).trim(), entry.getKey());
                if (msg.toLowerCase().equals(str))
                    return new MessageResponse("", entry.getKey());
            }

        //если не нашли, то возвращается ошибка
        return new MessageResponse("", MessageKind.ERROR);
    }

    //заполняет словарь значениями
    private static void init() {
        if (map == null) {
            map = new HashMap<>();
            map.put(MessageKind.START, new String[] {"/start"});
            map.put(MessageKind.STOP, new String[] {"/stop"});
            map.put(MessageKind.HELP, new String[] {"/help"});

            map.put(MessageKind.FEED_POINTS, new String[] {"/feedpoints"});
            map.put(MessageKind.USER_FEED_POINTS, new String[] {"/userfeedpoints"});
            map.put(MessageKind.ADD_FEED_POINT, new String[] {"/addfeedpoint"});
            map.put(MessageKind.COMPLAIN, new String[] {"/complain"});

            map.put(MessageKind.MSG_LIST, new String[] {"/msglist"});
            map.put(MessageKind.RUN_OUT, new String[] {"/runout"});

            map.put(MessageKind.ADVICES, new String[] {"/advices"});
            map.put(MessageKind.ADD_ADVICE, new String[] {"/addadvice"});
        }
    }

}
