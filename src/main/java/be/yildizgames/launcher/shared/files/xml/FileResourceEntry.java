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
package be.yildizgames.launcher.shared.files.xml;

import be.yildizgames.common.file.FileResource;
import be.yildizgames.common.file.xml.XMLValueTag;
import be.yildizgames.common.file.xml.XMLWrapTag;

/**
 * This class create an XML tag representing a FileResource.
 * @author Grégory Van den Borre
 */
public class FileResourceEntry extends XMLWrapTag {

    /**
     * Create a new XMLWrapTag ('file') with 'name', 'crc', and 'size' as tags.
     * @param resource ResourceFile to represent, cannot be null.
     */
    public FileResourceEntry(final FileResource resource) {
        super("file");
        this.addChild(new XMLValueTag("name", resource.getName()));
        this.addChild(new XMLValueTag("crc", resource.getCrc32()));
        this.addChild(new XMLValueTag("size", resource.getSize()));
    }
}
