/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2018 Grégory Van den Borre
 *
 *  More infos available: https://www.yildiz-games.be
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

package be.yildiz.launcher.shared.files;

import be.yildiz.common.util.Checker;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A file description just contains metadata for a file: its name, its CRC32 and its size.
 * 
 * @author Van den Borre Grégory
 */
@XmlRootElement(name = "file")
public final class FileDescription {

    /**
     * File name.
     */
    private String name;

    /**
     * File CRC.
     */
    private long crc;

    /**
     * File size.
     */
    private long size;

    /**
     * Create a new File description.
     * 
     * @param name
     *            The file name, cannot be null.
     * @param crc
     *            The file CRC value, must be greater or equals to 0.
     * @param size
     *            The file size, must be greater or equals to 0.
     */
    public FileDescription(final String name, final long crc, final long size) {
        super();
        if(name == null) {
            throw new AssertionError("Name cannot be null");
        }
        Checker.exceptionNotPositive(size);
        Checker.exceptionNotPositive(crc);
        this.name = name.replace("\\", "/");
        this.crc = crc;
        this.size = size;
    }

    public FileDescription() {
        super();
    }

    public String getName() {
        return name;
    }

    public long getCrc() {
        return crc;
    }

    public long getSize() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileDescription that = (FileDescription) o;

        if (crc != that.crc) {
            return false;
        }
        return size == that.size && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (crc ^ (crc >>> 32));
        result = 31 * result + (int) (size ^ (size >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
