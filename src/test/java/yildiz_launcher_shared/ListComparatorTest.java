package yildiz_launcher_shared;

import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import be.yildiz.launcher.files.FileDescription;
import be.yildiz.launcher.files.ListComparator;

public class ListComparatorTest {

    @Rule
    public final ExpectedException rule = ExpectedException.none();

    private static final String VALID_XML = "<test></test>";

    private static final String INVALID_XML = "test";

    private static final String ALL_FILES_XML = "<files><file><name>test</name><crc>10</crc><size>20</size></file><file><name>test2</name><crc>11</crc><size>25</size></file></files>";

    private static final String NOT_ALL_FILES_XML = "<files><file><name>test</name><crc>10</crc><size>20</size></file></files>";

    @Test
    public void testListComparatorNullArg() {
        this.rule.expect(NullPointerException.class);
        new ListComparator(null, VALID_XML);
    }

    @Test
    public void testListComparatorArgNull() {
        this.rule.expect(NullPointerException.class);
        new ListComparator(VALID_XML, null);
    }

    @Test
    public void testListComparatorArgInvalidArg() {
        this.rule.expect(IllegalArgumentException.class);
        new ListComparator(VALID_XML, INVALID_XML);
    }

    @Test
    public void testListComparatorInvalidArgArg() {
        this.rule.expect(IllegalArgumentException.class);
        new ListComparator(INVALID_XML, VALID_XML);
    }

    @Test
    public void testListComparatorEmptyArgArg() {
        this.rule.expect(IllegalArgumentException.class);
        new ListComparator("", VALID_XML);
    }

    @Test
    public void testListComparatorArgEmptyArg() {
        this.rule.expect(IllegalArgumentException.class);
        new ListComparator(VALID_XML, "");
    }

    @Test
    public void testListComparatorArgArg() {
        new ListComparator(VALID_XML, VALID_XML);
    }

    @Test
    public void testListComparatorWrongXml() {
        // FIXME uncomment
        // fails();
    }

    @Test
    public void testGetMissing() {
        ListComparator c = new ListComparator(ALL_FILES_XML, ALL_FILES_XML);
        Set<FileDescription> result = c.getMissing();
        Assert.assertTrue(result.isEmpty());
        c = new ListComparator(ALL_FILES_XML, NOT_ALL_FILES_XML);
        result = c.getMissing();
        Assert.assertTrue(result.size() == 1);
        FileDescription d = result.iterator().next();
        Assert.assertEquals("test2", d.getName());
        Assert.assertEquals(11, d.getCrc());
        Assert.assertEquals(25, d.getSize());
    }

    @Test
    public void testGetAdditional() {
        ListComparator c = new ListComparator(ALL_FILES_XML, ALL_FILES_XML);
        Set<FileDescription> result = c.getAdditional();
        Assert.assertTrue(result.isEmpty());
        c = new ListComparator(NOT_ALL_FILES_XML, ALL_FILES_XML);
        result = c.getAdditional();
        Assert.assertTrue(result.size() == 1);
        FileDescription d = result.iterator().next();
        Assert.assertEquals("test2", d.getName());
        Assert.assertEquals(11, d.getCrc());
        Assert.assertEquals(25, d.getSize());
    }

}
