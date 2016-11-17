package main;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import parser.MINIGrammar;
import testsuite.MINIException;

@RunWith(Parameterized.class)
public class ValidMiniProgramTester {

    private File file;

    public ValidMiniProgramTester(File file, String filename) {
        this.file = file;
    }

    @Test
    public void testFile() {
        try {
            MINIGrammar.parse(this.file);
        } catch (MINIException e) {
            // TEST FAILURE
            e.printStackTrace();
            assertFalse("Failed to parse file " + this.file.getAbsolutePath(), true);
        }

    }

    @Parameters(name = "{1}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        File validFolder = new File("res" + File.separator + "example_code" + File.separator + "valid");
        File[] sourceFiles = validFolder.listFiles();
        for (File f : sourceFiles) {
            if (f.isFile() && f.getName().endsWith(".m")) {
                data.add(new Object[] { f, f.getName() });
            }
        }
        return data;
    }
}
