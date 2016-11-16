import org.junit.Assert;
import org.junit.Test;
import util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Tests {

    @Test
    public void testTrue() {
        Assert.assertEquals(2*2, 4);
    }


    @Test
    public void testStringUtilsJoin() {
        List<String> strings = new ArrayList<>();
        strings.add("foo");
        strings.add("bar");
        strings.add("baz");
        String joined = StringUtils.join(strings, ", ");
        Assert.assertEquals(joined, "foo, bar, baz");
    }

    @Test
    public void testStringUtilsIsNullOrEmptyOrWhitespaceOnNull() {
        String str = null;
        Assert.assertTrue(StringUtils.isNullOrEmptyOrWhitespace(str));
    }

    @Test
    public void testStringUtilsIsNullOrEmptyOrWhitespaceOnEmpty() {
        String str = "";
        Assert.assertTrue(StringUtils.isNullOrEmptyOrWhitespace(str));
    }

    @Test
    public void testStringUtilsIsNullOrEmptyOrWhitespaceOnWhitespace() {
        String str = " \t\n";
        Assert.assertTrue(StringUtils.isNullOrEmptyOrWhitespace(str));
    }
}
