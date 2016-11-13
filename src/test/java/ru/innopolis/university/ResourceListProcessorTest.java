package ru.innopolis.university;

import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class ResourceListProcessorTest {
    @Test
    public void testProcess() throws Exception {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        String urls[] = { "file://" + s +"/src/test/resources/test.txt" };
        ResourceListProcessor resourceListProcessor = new ResourceListProcessor(urls, false);
        resourceListProcessor.process();
        sleep(1000);
        assertEquals(resourceListProcessor.getSetSize(), 3);

        String urls1[] = {
                "file://" + s +"/src/test/resources/test.txt",
                "file://" + s +"/src/test/resources/test1.txt"
        };
        ResourceListProcessor resourceListProcessor1 = new ResourceListProcessor(urls1, false);
        resourceListProcessor1.process();
        sleep(1000);
        assertEquals(resourceListProcessor1.getSetSize(), 6);
    }

}