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
    public void testIsNullOrBlankOnNull() {
        Assert.assertTrue(StringUtils.isNullOrBlank(null));
    }

    @Test
    public void testIsNullOrBlankOnEmpty() {
        String str = "";
        Assert.assertTrue(StringUtils.isNullOrBlank(str));
    }

    @Test
    public void testIsNullOrBlankOnWhitespace() {
        String str = " \t\n";
        Assert.assertTrue(StringUtils.isNullOrBlank(str));
    }

    @Test
    public void testIsNullOrBlankOnNonEmpty() {
        String str = "foobar";
        Assert.assertFalse(StringUtils.isNullOrBlank(str));
    }
}
