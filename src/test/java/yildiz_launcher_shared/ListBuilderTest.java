package yildiz_launcher_shared;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import be.yildiz.launcher.files.ListBuilder;

public class ListBuilderTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testCreateList() throws IOException {
        folder.create();
        File properties = folder.newFile("test.prp");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(properties))) {
            out.write("aprop = aa\n");
            out.write("other = bb\n");
        }
        ListBuilder builder = new ListBuilder(properties.getParentFile().getPath());
        String result = builder.createList();
        Assert.assertEquals("<files><file><name>" + properties.getAbsolutePath() + "</name><crc>2104821731</crc><size>22</size></file></files>", result);
    }

    @Test
    public void testListBuilder() {
    }

}
