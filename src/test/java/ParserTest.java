import api.receive.MessageKind;
import api.receive.MessageResponse;
import api.receive.ReceiveMessageParser;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {

    @Test
    public void startCommandTest() {
        MessageResponse response = ReceiveMessageParser.getKind("/start");
        Assert.assertEquals(response.getKind(), MessageKind.START);
    }

    @Test
    public void addFeedPointCommandTest() {
        MessageResponse response = ReceiveMessageParser.getKind("/addfeedpoint qwerty");
        Assert.assertEquals(response.getKind(), MessageKind.ADD_FEED_POINT);
        Assert.assertEquals(response.getText(), "qwerty");
    }

    @Test
    public void complainCommandTest() {
        MessageResponse response = ReceiveMessageParser.getKind("/complain qwerty test");
        Assert.assertEquals(response.getKind(), MessageKind.COMPLAIN);
        Assert.assertEquals(response.getText(), "qwerty test");
    }

    @Test
    public void emptyCommandTest() {
        MessageResponse response = ReceiveMessageParser.getKind("");
        Assert.assertEquals(response.getKind(), MessageKind.ERROR);
    }

    @Test
    public void errorCommandTest() {
        MessageResponse response = ReceiveMessageParser.getKind("/stat");
        Assert.assertEquals(response.getKind(), MessageKind.ERROR);
    }

}
