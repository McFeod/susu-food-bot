import org.junit.Assert;
import org.junit.Test;

public class Tests {

    @Test
    public void testTrue() {
        Assert.assertEquals(2*2, 4);
    }

    @Test
    public void testFalse() {
        Assert.assertEquals(2*2, 5);
    }
}
