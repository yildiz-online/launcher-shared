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

import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListBuilderTest {


    @Test
    void testCreateList() throws IOException {
        Path path = Files.createTempDirectory("test");
        File file = new File(path.toFile().getAbsolutePath() + "/test.prp");
        file.createNewFile();
        try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
            out.write("aprop = aa\n");
            out.write("other = bb\n");
        }
        ListBuilder builder = new ListBuilder(file.getParentFile().getPath());
        String result = builder.createList();
        assertEquals("<files><file><name>" + file.getAbsolutePath() + "</name><crc>2104821731</crc><size>22</size></file></files>", result);
    }

    @Test
    void testListBuilder() {
    }

}
