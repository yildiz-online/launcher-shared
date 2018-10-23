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

package be.yildizgames.launcher.shared.files;

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
    public final String name;

    /**
     * File CRC.
     */
    public final long crc;

    /**
     * File size.
     */
    public final long size;

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
        if (size < 0) {
            throw new IllegalArgumentException("Crc is negative.");
        }
        if (crc < 0) {
            throw new IllegalArgumentException("Size is negative.");
        }
        this.name = name.replace("\\", "/");
        this.crc = crc;
        this.size = size;
    }

    public FileDescription() {
        super();
        this.name = "";
        this.crc = 0;
        this.size = 0;
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

        return crc == that.crc && size == that.size && name.equals(that.name);
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
