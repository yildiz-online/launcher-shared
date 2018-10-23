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

import be.yildizgames.common.file.FileResource;
import be.yildizgames.common.file.xml.XMLWrapTag;
import be.yildizgames.launcher.shared.constant.Constants;
import be.yildizgames.launcher.shared.files.xml.FileResourceEntry;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Create a list of files containing their name, size and crc32. This list is formatted as an xml with the following tags: &lt;files&gt;&lt;file&gt;&lt;name&gt;&lt;/name&gt;&lt;crc&gt;&lt;/crc&gt;&lt;size&gt;&lt;/size&gt;&lt;/file&gt;&lt;/files&gt;
 * 
 * @author Van den Borre Grégory
 */
public final class ListBuilder {

    /**
     * Base directory.
     */
    private final Path directory;

    public ListBuilder(final Path directory) {
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
        List<FileResource> files = FileResource.createDirectory(this.directory).listFile("Thumbs", Constants.LIST);
        XMLWrapTag filesTag = new XMLWrapTag("files");
        files.stream()
                .map(FileResourceEntry::new)
                .forEach(filesTag::addChild);
        return filesTag.generate();
    }
}
