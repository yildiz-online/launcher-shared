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

import be.yildiz.common.util.Checker;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * A file description just contains metadata for a file: its name, its CRC32 and its size.
 * 
 * @author Van den Borre Grégory
 */
@XmlRootElement(name = "file")
@NoArgsConstructor
@EqualsAndHashCode
@Getter
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
     *            The file CRC value, must be >= 0.
     * @param size
     *            The file size, must be >=0.
     */
    public FileDescription(@NonNull final String name, final long crc, final long size) {
        super();
        Checker.exceptionNotPositive(size);
        Checker.exceptionNotPositive(crc);
        this.name = name;
        this.crc = crc;
        this.size = size;
    }
}
