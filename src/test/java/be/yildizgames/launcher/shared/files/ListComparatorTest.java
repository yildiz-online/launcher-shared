/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.launcher.shared.files;

import be.yildizgames.common.exception.technical.TechnicalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ListComparatorTest {

    private static final String VALID_XML = "<test></test>";

    private static final String INVALID_XML = "test";

    private static final String ALL_FILES_XML = "<files><file><name>test</name><crc>10</crc><size>20</size></file><file><name>test2</name><crc>11</crc><size>25</size></file></files>";

    private static final String NOT_ALL_FILES_XML = "<files><file><name>test</name><crc>10</crc><size>20</size></file></files>";

    @Test
    void testListComparatorNullArg() {
        assertThrows(NullPointerException.class, () -> new ListComparator(null, VALID_XML));
    }

    @Test
    void testListComparatorArgNull() {
        assertThrows(NullPointerException.class, () -> new ListComparator(VALID_XML, null));
    }

    @Test
    void testListComparatorArgInvalidArg() {
        assertThrows(TechnicalException.class, () -> new ListComparator(VALID_XML, INVALID_XML));
    }

    @Test
    void testListComparatorInvalidArgArg() {
        assertThrows(TechnicalException.class, () -> new ListComparator(INVALID_XML, VALID_XML));
    }

    @Test
    void testListComparatorEmptyArgArg() {
        assertThrows(IllegalArgumentException.class, () -> new ListComparator("", VALID_XML));
    }

    @Test
    void testListComparatorArgEmptyArg() {
        assertThrows(IllegalArgumentException.class, () -> new ListComparator(VALID_XML, ""));
    }

    @Test
    void testListComparatorArgArg() {
        new ListComparator(VALID_XML, VALID_XML);
    }

    @Test
    void testListComparatorWrongXml() {
        Assertions.assertThrows(TechnicalException.class, () -> new ListComparator(ALL_FILES_XML, INVALID_XML));
    }

    @Test
    void testGetMissing() {
        ListComparator c = new ListComparator(ALL_FILES_XML, ALL_FILES_XML);
        Set<FileDescription> result = c.getMissing();
        assertTrue(result.isEmpty());
        c = new ListComparator(ALL_FILES_XML, NOT_ALL_FILES_XML);
        result = c.getMissing();
        assertTrue(result.size() == 1);
        FileDescription d = result.iterator().next();
        assertEquals("test2", d.name);
        assertEquals(11, d.crc);
        assertEquals(25, d.size);
    }

    @Test
    void testGetAdditional() {
        ListComparator c = new ListComparator(ALL_FILES_XML, ALL_FILES_XML);
        Set<FileDescription> result = c.getAdditional();
        assertTrue(result.isEmpty());
        c = new ListComparator(NOT_ALL_FILES_XML, ALL_FILES_XML);
        result = c.getAdditional();
        assertEquals(1,result.size());
        FileDescription d = result.iterator().next();
        assertEquals("test2", d.name);
        assertEquals(11, d.crc);
        assertEquals(25, d.size);
    }

}
