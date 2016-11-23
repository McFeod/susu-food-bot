package api;

public class Texts {

    public static final String ADVICE_CONFIRMATION      = "След в истории оставлен.";
    public static final String ADVICES_HEADER           = "*Пользователи советуют:*\n";
    public static final String ADVICE_TEXT_PROMPT       = "Что посоветуете?";
    public static final String CANCEL_CONFIRMATION      = "Отменено.";
    public static final String CANCEL_FAILURE           = "Нечего отменять.";
    public static final String COMPLAIN_CONFIRMATION    = "Жалоба принята.";
    public static final String ERROR_HEADER             = "*Ошибочка вышла:*\n";
    public static final String FEEDPOINT_CONFIRMATION   = "Место успешно добавлено.";
    public static final String FEEDPOINTS_HEADER        = "*Где можно поесть:*\n";
    public static final String FEEDPOINT_NAME_PROMPT    = "Введите название";
    public static final String FEEDPOINT_PLACE_PROMPT   = "Где это находится? Опишите, пожалуйста.";
    public static final String HELP_MESSAGE =
        "/help - список команд для бота.\n" +
        "/feedpoints - список проверенных мест, где можно поесть.\n" +
        "/userfeedpoints - список мест, где можно поесть.\n" +
        "/addfeedpoint <место> - добавить новое место, где можно поесть.\n" +
        "/complain <место> - кто-то наврал вам про буфет? Пожалуйтесь, и мы его найдем.\n" +
        "/msglist <место> - свежие новости о наличии вкусностей в ваших любимых буфетах.\n" +
        "/runout <место> - что-то закрылось/закончилось? Сообщите об этом всем.\n" +
        "/advices - узнать, что советуют другие пользователи.\n" +
        "/addadvice <текст> - что вы советуете другим пользователям? Добавьте свой совет.\n" +
        "/cancel - отмена текущей команды";
    public static final String MESSAGES_HEADER          = "*Разведка сообщает:*";
    public static final String MESSAGES_PLACE_DELIMITER = "\nВ \"%s\" закончились продукты:\n";
    public static final String PRODUCT_NAME_PROMPT      = "Что закончилось?";
    public static final String RUNOUT_CONFIRMATION      = "Родина Вас не забудет.";
    public static final String START_PROMPT             = "Привет! Я бот, который поможет узнать, " +
            "где в ЮУрГУ можно перекусить. Наберите /help для ознакомления с моими командами.";
    public static final String UNEXPECTED_ERROR         = "Ой, всё!";
    public static final String USERFEEDPOINTS_HEADER    = "*Где можно поесть по версии пользователей:*\n";
}
