package be.yildiz.launcher.files;

import java.util.Collections;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import be.yildiz.common.collections.Sets;
import be.yildiz.common.resource.xml.XMLParser;

/**
 * Compare 2 lists to retrieve missing or additional files.
 * 
 * @author Van den Borre Grégory
 */
public final class ListComparator {

    /**
     * Expected list of files.
     */
    private final Set<FileDescription> expected;

    /**
     * Current list of files.
     */
    private final Set<FileDescription> existing;

    /**
     * Full constructor.
     * 
     * @param expectedResult
     *            String containing XML expected list of files, must contains at least root element.
     * @param toTest
     *            String containing XML current list of files, must contains at least root element.
     * @throws IllegalArgumentException
     *             if the xml of one of the parameter is invalid.
     */
    public ListComparator(final String expectedResult, final String toTest) {
        super();
        if (expectedResult.isEmpty()) {
            throw new IllegalArgumentException("expectedResult is empty, must at least contains the root element.");
        }
        if (toTest.isEmpty()) {
            throw new IllegalArgumentException("toTest is empty, must at least contains the root element.");
        }
        this.expected = this.buildFromString(expectedResult);
        this.existing = this.buildFromString(toTest);
    }

    /**
     * Retrieve files existing in expected list and not in existing list.
     * 
     * @return The missing files.
     */
    public Set<FileDescription> getMissing() {
        if (this.expected.equals(this.existing)) {
            return Collections.emptySet();
        }
        Set<FileDescription> result = Sets.newSet(this.expected);
        result.removeAll(this.existing);
        return result;
    }

    /**
     * Retrieve the files existing in existing list and not in expected list.
     * 
     * @return The additional files.
     */
    public Set<FileDescription> getAdditional() {
        if (this.expected.equals(this.existing)) {
            return Collections.emptySet();
        }
        Set<FileDescription> result = Sets.newSet(this.existing);
        result.removeAll(this.expected);
        return result;
    }

    /**
     * Build from an XML source.
     * 
     * @param s
     *            XML content.
     * @return The list of file description built from the XML.
     */
    private Set<FileDescription> buildFromString(final String s) {
        Set<FileDescription> set = Sets.newSet();
        Document doc = XMLParser.getDocument(s);
        NodeList fileList = doc.getElementsByTagName("file");
        for (int i = 0; i < fileList.getLength(); i++) {
            Node file = fileList.item(i);
            NodeList fileChildren = file.getChildNodes();
            String name = null;
            long crc = 0;
            long size = 0;
            for (int j = 0; j < fileChildren.getLength(); j++) {
                Node child = fileChildren.item(j);
                if ("name".equals(child.getNodeName())) {
                    name = child.getTextContent();
                } else if ("crc".equals(child.getNodeName())) {
                    crc = Long.parseLong(child.getTextContent());
                } else if ("size".equals(child.getNodeName())) {
                    size = Long.parseLong(child.getTextContent());
                }
            }
            set.add(new FileDescription(name, crc, size));
        }
        return set;
    }

}
