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

import be.yildiz.common.collections.Lists;
import be.yildiz.common.resource.FileResource;
import be.yildiz.common.resource.xml.XMLValueTag;
import be.yildiz.common.resource.xml.XMLWrapTag;
import be.yildiz.launcher.shared.constant.Constants;

import java.io.IOException;
import java.util.List;

/**
 * Create a list of files containing their name, size and crc32. This list is formatted as an xml with the following tags: <files><file><name></name><crc></crc><size></size></file></files>
 * 
 * @author Van den Borre Grégory
 */
public final class ListBuilder {

    /**
     * Base directory.
     */
    private final String directory;

    public ListBuilder(final String directory) {
        super();
        this.directory = directory;
    }

    /**
     * Create an XML String containing a list of files. The files are read from this object instance directory. Thumbs files will be ignored.
     * 
     * @return An XML String containing all files.
     * @throws IOException
     *             If an exception occurs when trying to access the files.
     */
    public String createList() throws IOException {
        List<FileResource> files = Lists.newList();
        FileResource.createDirectory(this.directory).listFile(files, "Thumbs", Constants.LIST);
        XMLWrapTag filesTag = new XMLWrapTag("files");
        for (FileResource p : files) {
            XMLWrapTag fileTag = new XMLWrapTag("file");
            filesTag.addChild(fileTag);
            fileTag.addChild(new XMLValueTag("name", p.getName()));
            fileTag.addChild(new XMLValueTag("crc", p.getCrc32()));
            fileTag.addChild(new XMLValueTag("size", p.getSize()));
        }
        return filesTag.generate(new StringBuilder());
    }
}
