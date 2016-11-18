import org.junit.Assert;
import org.junit.Test;
import util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UtilsTest {
    @Test
    public void testJoin() {
        List<String> strings = new ArrayList<>();
        strings.add("foo");
        strings.add("bar");
        strings.add("baz");
        String joined = StringUtils.join(strings, ", ");
        Assert.assertEquals(joined, "foo, bar, baz");
    }

    @Test
    public void testIsNullOrEmptyOnNull() {
        Assert.assertTrue(StringUtils.isNullOrEmpty(null));
    }

    @Test
    public void testIsNullOrEmptyOnEmpty() {
        String str = "";
        Assert.assertTrue(StringUtils.isNullOrEmpty(str));
    }

    @Test
    public void testIsNullOrEmptyOnWhitespace() {
        String str = " \t\n";
        Assert.assertFalse(StringUtils.isNullOrEmpty(str));
    }

    @Test
    public void testIsNullOrEmptyOnNonEmpty() {
        String str = "foobar";
        Assert.assertFalse(StringUtils.isNullOrEmpty(str));
    }

    @Test
    public void testIsNullOrWhitespaceOnNull() {
        Assert.assertTrue(StringUtils.isNullOrWhitespace(null));
    }

    @Test
    public void testIsNullOrWhitespaceOnEmpty() {
        String str = "";
        Assert.assertTrue(StringUtils.isNullOrWhitespace(str));
    }

    @Test
    public void testIsNullOrWhitespaceOnWhitespace() {
        String str = " \t\n";
        Assert.assertTrue(StringUtils.isNullOrWhitespace(str));
    }

    @Test
    public void testIsNullOrWhitespaceOnNonEmpty() {
        String str = "foobar";
        Assert.assertFalse(StringUtils.isNullOrWhitespace(str));
    }
}
