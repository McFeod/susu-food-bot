package api.receive;

import java.util.List;

public interface ITelegramBotReceiveListener {

    void onMessageReceive(Long id, String message);

}
