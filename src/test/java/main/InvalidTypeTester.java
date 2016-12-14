package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frontend.MINIParser;
import testsuite.MINIException;
import testsuite.ParseException;
import testsuite.TypeException;

@RunWith(Parameterized.class)
public class InvalidTypeTester {
    private final File file;

    public InvalidTypeTester(File file, String filename) {
        this.file = file;
    }

    @Test
    public void testFile() {
        try {
            MINIParser.isMINI(file);
            assertFalse("Successfully parsed file that should contain errors " + file.getAbsolutePath(), true);
        } catch (ParseException e) {
            fail("Found Syntax Errors in file.");
        } catch (TypeException e) {
            String errorLine = file.getName().substring(0, 3);
            try {
                int lineNumber = Integer.parseInt(errorLine);
                assertEquals("The error occurred in an unexpected line", lineNumber, e.getLineNumber());
            } catch (NumberFormatException ignored) {
            }
        } catch (MINIException e) {
            e.printStackTrace();
        }

    }

    @Parameters(name = "{1}")
    public static Collection<Object[]> data() {
        Collection<Object[]> data = new ArrayList<>();
        File invalidFolder = new File("res" + File.separator + "example_code" + File.separator + "invalid" + File.separator + "type_errors");
        File[] sourceFiles = invalidFolder.listFiles();
        for (File f : sourceFiles) {
            if (f.isFile() && f.getName().endsWith(".m")) {
                data.add(new Object[] { f, f.getName() });
            }
        }
        return data;
    }
}
