package lib;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class FileComparator {

    
    public static void assertFilesAreEqual(File expected, File actual) throws AssertionError, IOException {
        String[] firstFile = Files.readAllLines(expected.toPath()).toArray(new String[]{});
        String[] secondFile = Files.readAllLines(actual.toPath()).toArray(new String[]{});
        assertEquals("Files are of different lengths", firstFile.length, secondFile.length);
        for (int i = 0; i < firstFile.length; ++i) {
            assertEquals(firstFile[i], secondFile[i]);
        }
    }
}
