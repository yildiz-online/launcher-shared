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

public class FileDescriptionTest {

    @Test
    public void testFileDescription() {
        FileDescription d = new FileDescription();
        Assert.assertNull(d.getName());
        Assert.assertEquals(0, d.getCrc());
        Assert.assertEquals(0, d.getSize());
    }

    @Test(expected = NullPointerException.class)
    public void testGetName() {
        FileDescription d = new FileDescription("aName", 50, 100);
        Assert.assertEquals("aName", d.getName());
        new FileDescription(null, 50, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCrc() {
        FileDescription d = new FileDescription("aName", 50, 100);
        Assert.assertEquals(50, d.getCrc());
        d = new FileDescription("aName", 0, 100);
        Assert.assertEquals(0, d.getCrc());
        new FileDescription("a", -1, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetSize() {
        FileDescription d = new FileDescription("aName", 50, 100);
        Assert.assertEquals(100, d.getSize());
        d = new FileDescription("aName", 50, 0);
        Assert.assertEquals(0, d.getSize());
        new FileDescription("a", 50, -100);
    }

    @Test
    public void testEquals() {
        FileDescription f1 = new FileDescription("a", 10, 15);
        Assert.assertEquals(f1, f1);
        Assert.assertNotEquals(f1, null);
        Assert.assertNotEquals(f1, "test");
        FileDescription f2 = new FileDescription("a", 10, 15);
        Assert.assertEquals(f1, f2);
        FileDescription f3 = new FileDescription("ab", 10, 15);
        Assert.assertNotEquals(f1, f3);
        FileDescription f4 = new FileDescription("a", 110, 15);
        Assert.assertNotEquals(f1, f4);
        FileDescription f5 = new FileDescription("a", 10, 1);
        Assert.assertNotEquals(f1, f5);
    }

    @Test
    public void testHashcode() {
        FileDescription f1 = new FileDescription("a", 10, 15);
        Assert.assertEquals(f1.hashCode(), f1.hashCode());
        FileDescription f3 = new FileDescription("a", 11, 15);
        Assert.assertNotEquals(f1.hashCode(), f3.hashCode());
    }

}
