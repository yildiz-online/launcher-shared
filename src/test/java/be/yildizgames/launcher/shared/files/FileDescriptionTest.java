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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileDescriptionTest {

    @Test
    public void testFileDescription() {
        FileDescription d = new FileDescription();
        assertEquals("", d.name);
        assertEquals(0, d.crc);
        assertEquals(0, d.size);
    }

    @Test
    public void testGetName() {
        FileDescription d = new FileDescription("aName", 50, 100);
        assertEquals("aName", d.name);
        assertThrows(AssertionError.class, () -> new FileDescription(null, 50, 100));
    }

    @Test
    public void testGetCrc() {
        FileDescription d = new FileDescription("aName", 50, 100);
        assertEquals(50, d.crc);
        d = new FileDescription("aName", 0, 100);
        assertEquals(0, d.crc);
        assertThrows(IllegalArgumentException.class, () -> new FileDescription("a", -1, 100));
    }

    @Test
    public void testGetSize() {
        FileDescription d = new FileDescription("aName", 50, 100);
        assertEquals(100, d.size);
        d = new FileDescription("aName", 50, 0);
        assertEquals(0, d.size);
        assertThrows(IllegalArgumentException.class, () -> new FileDescription("a", 50, -100));
    }

    @Test
    public void testEquals() {
        FileDescription f1 = new FileDescription("a", 10, 15);
        assertEquals(f1, f1);
        assertNotEquals(f1, null);
        assertNotEquals(f1, "test");
        FileDescription f2 = new FileDescription("a", 10, 15);
        assertEquals(f1, f2);
        FileDescription f3 = new FileDescription("ab", 10, 15);
        assertNotEquals(f1, f3);
        FileDescription f4 = new FileDescription("a", 110, 15);
        assertNotEquals(f1, f4);
        FileDescription f5 = new FileDescription("a", 10, 1);
        assertNotEquals(f1, f5);
    }

    @Test
    public void testEqualsDifferentSeparators() {
        FileDescription f1 = new FileDescription("test/test.txt", 10, 15);
        FileDescription f2 = new FileDescription("test\\test.txt", 10, 15);
        assertEquals(f1, f2);
    }

    @Test
    public void testHashcode() {
        FileDescription f1 = new FileDescription("a", 10, 15);
        assertEquals(f1.hashCode(), f1.hashCode());
        FileDescription f3 = new FileDescription("a", 11, 15);
        assertNotEquals(f1.hashCode(), f3.hashCode());
    }

}
