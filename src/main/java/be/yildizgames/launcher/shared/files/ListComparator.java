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

import be.yildizgames.common.file.xml.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Compare 2 lists to retrieve missing or additional files.
 * 
 * @author Van den Borre Grégory
 */
public final class ListComparator {

    private final System.Logger logger = System.getLogger(this.getClass().getName());

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
        this.logger.log(System.Logger.Level.DEBUG,"---------------------existing------------------");
        this.existing
                .stream()
                .map(f -> f.name)
                .forEach(s -> logger.log(System.Logger.Level.DEBUG, s));
        this.logger.log(System.Logger.Level.DEBUG, "---------------------expected------------------");
        this.expected
                .stream()
                .map(f -> f.name)
                .forEach(s -> logger.log(System.Logger.Level.DEBUG, s));
        Set<FileDescription> result = new HashSet<>(this.expected);
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
        Set<FileDescription> result = new HashSet<>(this.existing);
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
        Set<FileDescription> set = new HashSet<>();
        Document doc = XMLParser.getDocument(s);
        NodeList fileList = doc.getElementsByTagName("file");
        for (int i = 0; i < fileList.getLength(); i++) {
            Node file = fileList.item(i);
            NodeList fileChildren = file.getChildNodes();
            String name = null;
            long crc = -1;
            long size = -1;
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
            if(name == null || crc == -1 || size == -1) {
                this.logger.log(System.Logger.Level.ERROR, "Invalid value: name: {} crc: {} size: {}", name, crc, size);
            } else {
                set.add(new FileDescription(name, crc, size));
            }
        }
        return set;
    }

}
