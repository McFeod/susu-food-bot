import api.receive.MessageKind;
import api.receive.MessageResponse;
import api.receive.ReceiveMessageParser;
import org.junit.Assert;
import org.junit.Test;

public class ReceiveMessageParserTest {

    @Test
    public void testGetKindOnError() {
        String msg = "/bom foobar";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.ERROR);
        Assert.assertEquals(response.getText(), "");
    }

    @Test
    public void testGetKindOnAddAdvice() {
        String msg = "/addadvice foobar";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.ADD_ADVICE);
        Assert.assertEquals(response.getText(), "foobar");
    }

    @Test
    public void testGetKindOnAddFeedPoint() {
        String msg = "/addfeedpoint foobar";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.ADD_FEED_POINT);
        Assert.assertEquals(response.getText(), "foobar");
    }

    @Test
    public void testGetKindOnAdvices() {
        String msg = "/advices";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.ADVICES);
        Assert.assertEquals(response.getText(), "");
    }

    @Test
    public void testGetKindOnComplain() {
        String msg = "/complain foobar";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.COMPLAIN);
        Assert.assertEquals(response.getText(), "foobar");
    }

    @Test
    public void testGetKindOnFeedPoints() {
        String msg = "/feedpoints";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.FEED_POINTS);
        Assert.assertEquals(response.getText(), "");
    }

    @Test
    public void testGetKindOnHelp() {
        String msg = "/help";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.HELP);
        Assert.assertEquals(response.getText(), "");
    }

    @Test
    public void testGetKindOnMsgList() {
        String msg = "/msglist foobar";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.MSG_LIST);
        Assert.assertEquals(response.getText(), "foobar");
    }

    @Test
    public void testGetKindOnRunOut() {
        String msg = "/runout foobar bombom";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.RUN_OUT);
        Assert.assertEquals(response.getText(), "foobar bombom");
    }

    @Test
    public void testGetKindOnStart() {
        String msg = "/start";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.START);
        Assert.assertEquals(response.getText(), "");
    }

    @Test
    public void testGetKindOnStop() {
        String msg = "/stop";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.STOP);
        Assert.assertEquals(response.getText(), "");
    }

    @Test
    public void testGetKindOnUserFeedPoints() {
        String msg = "/userfeedpoints";
        MessageResponse response = ReceiveMessageParser.getKind(msg);
        Assert.assertEquals(response.getKind(), MessageKind.USER_FEED_POINTS);
        Assert.assertEquals(response.getText(), "");
    }
}
