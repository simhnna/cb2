package main;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import static lib.FileComparator.assertFilesAreEqual;
import frontend.MINIParser;
import testsuite.MINIException;

public class PrettyPrinterTest {

    @Test
    public void test() {
        File in = new File("res" + File.separator + "example_code" + File.separator + "valid"
                + File.separator + "pretty_much_everything.m");
        File out1, out2;
        try {
            out1 = File.createTempFile("prettyPrintTest", ".m");
            out2 = File.createTempFile("prettyPrintTest", ".m");
            out1.deleteOnExit();
            out2.deleteOnExit();
            MINIParser.prettyPrinted(in, out1);
            assertFilesAreEqual(in, out1);
            MINIParser.prettyPrinted(out1, out2);
            assertFilesAreEqual(out1, out2);

        } catch (IOException e) {
            fail("Failed to create temporary File");
        } catch (MINIException e) {
            fail("Failed to parse input File");
        }
    }

}
