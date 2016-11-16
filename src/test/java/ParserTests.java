import api.receive.ReceiveMessageParser;
import org.junit.Assert;
import org.junit.Test;

public class ParserTests {

    @Test
    public void startCommandTest() {
        ReceiveMessageParser.MessageResponse response = ReceiveMessageParser.getKind("/start");
        Assert.assertEquals(response.getKind(), ReceiveMessageParser.MessageKind.START);
    }

    @Test
    public void addFeedPointCommandTest() {
        ReceiveMessageParser.MessageResponse response = ReceiveMessageParser.getKind("/addfeedpoint qwerty");
        Assert.assertEquals(response.getKind(), ReceiveMessageParser.MessageKind.ADD_FEED_POINT);
        Assert.assertEquals(response.getText(), "qwerty");
    }

    @Test
    public void complainCommandTest() {
        ReceiveMessageParser.MessageResponse response = ReceiveMessageParser.getKind("/complain qwerty test");
        Assert.assertEquals(response.getKind(), ReceiveMessageParser.MessageKind.COMPLAIN);
        Assert.assertEquals(response.getText(), "qwerty test");
    }

    @Test
    public void emptyCommandTest() {
        ReceiveMessageParser.MessageResponse response = ReceiveMessageParser.getKind("");
        Assert.assertEquals(response.getKind(), ReceiveMessageParser.MessageKind.ERROR);
    }

    @Test
    public void errorCommandTest() {
        ReceiveMessageParser.MessageResponse response = ReceiveMessageParser.getKind("/stat");
        Assert.assertEquals(response.getKind(), ReceiveMessageParser.MessageKind.ERROR);
    }

}
