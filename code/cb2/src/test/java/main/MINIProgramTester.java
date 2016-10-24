package main;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import parser.MINIGrammar;
import testsuite.MINIException;

public class MINIProgramTester {
    @Test
    public void testValidPrograms() {
        File validFolder = new File("res" + File.separator + "example_code" + File.separator + "valid");
        File[] sourceFiles = validFolder.listFiles();
        for (File f : sourceFiles) {
            if (f.isFile() && f.getName().endsWith(".m")) {
                try {
                    MINIGrammar.parse(f);
                } catch (MINIException e) {
                    // TEST FAILURE
                    e.printStackTrace();
                    assertFalse("Failed to parse file " + f.getAbsolutePath(), true);
                }
            }
        }
    }

    @Test
    public void testInvalidPrograms() {
        File validFolder = new File("res" + File.separator + "example_code" + File.separator + "invalid");
        File[] sourceFiles = validFolder.listFiles();
        for (File f : sourceFiles) {
            if (f.isFile() && f.getName().endsWith(".m")) {
                try {
                    MINIGrammar.parse(f);
                    assertFalse("Successfully parsed file that should contain errors " + f.getAbsolutePath(), true);
                } catch (MINIException e) {
                    // TEST SUCCESS
                    String errorLine = f.getName().substring(0, 4);
                    try {
                        int lineNumber = Integer.parseInt(errorLine);
                        assertEquals("The error occurred in an unexpected line", lineNumber, e.getLineNumber());
                    } catch (NumberFormatException ne) {}
                }
            }
        }
    }
}
