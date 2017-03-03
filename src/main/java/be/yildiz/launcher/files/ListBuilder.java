package be.yildiz.launcher.files;

import java.io.IOException;
import java.util.List;

import be.yildiz.common.collections.Lists;
import be.yildiz.common.resource.FileResource;
import be.yildiz.common.resource.FileResource.FileType;
import be.yildiz.common.resource.xml.XMLValueTag;
import be.yildiz.common.resource.xml.XMLWrapTag;
import be.yildiz.launcher.constant.Constants;
import lombok.AllArgsConstructor;

/**
 * Create a list of files containing their name, size and crc32. This list is formatted as an xml with the following tags: <files><file><name></name><crc></crc><size></size></file></files>
 * 
 * @author Van den Borre Grégory
 */
@AllArgsConstructor
public final class ListBuilder {

    /**
     * Base directory.
     */
    private final String directory;

    /**
     * Create an XML String containing a list of files. The files are read from this object instance directory. Thumbs files will be ignored.
     * 
     * @return An XML String containing all files.
     * @throws IOException
     *             If an exception occurs when trying to access the files.
     */
    public String createList() throws IOException {
        List<FileResource> files = Lists.newList();
        new FileResource(this.directory, FileType.DIRECTORY).listFile(files, "Thumbs", Constants.LIST);
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
