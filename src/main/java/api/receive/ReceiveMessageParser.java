package api.receive;

import util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ReceiveMessageParser {
    // словарь, где каждой команде соответствует тип сообщения
    private static Map<String, MessageKind> commands = new HashMap<>();
    static {
        commands.put("/addadvice"     , MessageKind.ADD_ADVICE);
        commands.put("/addfeedpoint"  , MessageKind.ADD_FEED_POINT);
        commands.put("/advices"       , MessageKind.ADVICES);
        commands.put("/complain"      , MessageKind.COMPLAIN);
        commands.put("/feedpoints"    , MessageKind.FEED_POINTS);
        commands.put("/help"          , MessageKind.HELP);
        commands.put("/msglist"       , MessageKind.MSG_LIST);
        commands.put("/runout"        , MessageKind.RUN_OUT);
        commands.put("/start"         , MessageKind.START);
        commands.put("/stop"          , MessageKind.STOP);
        commands.put("/userfeedpoints", MessageKind.USER_FEED_POINTS);
        commands.put("/cancel", MessageKind.CANCEL);
    }

    public static MessageResponse getKind(String msg) {
        if (StringUtils.isNullOrBlank(msg)) {
            return MessageResponse.error();
        }

        msg = msg.trim();

        String command;
        String rest;

        // разбиваем сообщение на команду и аргументы
        int spaceIndex = msg.indexOf(' ');
        if (spaceIndex == -1) {
            command = msg.toLowerCase();
            rest = "";
        } else {
            command = msg.substring(0, spaceIndex).toLowerCase();
            rest = msg.substring(spaceIndex + 1);
        }

        // поиск команды
        if (commands.containsKey(command)) {
            return new MessageResponse(rest, commands.get(command));
        }

        // если не нашли, то возвращается ошибка
        return MessageResponse.error();
    }
}
