package main;

import static org.junit.Assert.assertFalse;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import components.FileNode;
import parser.MINIGrammar;
import testsuite.MINIException;
import visitors.NameAndTypeChecker;

@RunWith(Parameterized.class)
public class ValidTypeTester {

    private File file;

    public ValidTypeTester(File file, String filename) {
        this.file = file;
    }

    @Test
    public void testFile() {
        try {
            FileNode classes = MINIGrammar.parse(file);
            NameAndTypeChecker checker = new NameAndTypeChecker(file);
            classes.accept(checker, null);
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
