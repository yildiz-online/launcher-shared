/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2017 Grégory Van den Borre
 *
 * More infos available: https://www.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 */

package be.yildiz.launcher.shared.files;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class ListComparatorTest {

    private static final String VALID_XML = "<test></test>";

    private static final String INVALID_XML = "test";

    private static final String ALL_FILES_XML = "<files><file><name>test</name><crc>10</crc><size>20</size></file><file><name>test2</name><crc>11</crc><size>25</size></file></files>";

    private static final String NOT_ALL_FILES_XML = "<files><file><name>test</name><crc>10</crc><size>20</size></file></files>";

    @Test(expected = NullPointerException.class)
    public void testListComparatorNullArg() {
        new ListComparator(null, VALID_XML);
    }

    @Test(expected = NullPointerException.class)
    public void testListComparatorArgNull() {
        new ListComparator(VALID_XML, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListComparatorArgInvalidArg() {
        new ListComparator(VALID_XML, INVALID_XML);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListComparatorInvalidArgArg() {
        new ListComparator(INVALID_XML, VALID_XML);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListComparatorEmptyArgArg() {
        new ListComparator("", VALID_XML);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListComparatorArgEmptyArg() {
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
