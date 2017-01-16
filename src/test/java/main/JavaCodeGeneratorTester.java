package main;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frontend.MINIParser;
import testsuite.MINIException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

@RunWith(Parameterized.class)
public class JavaCodeGeneratorTester {

    private final File inputFile;

    public JavaCodeGeneratorTester(File inputFile, String fileName) {
        this.inputFile = inputFile;
    }

    private static void checkJavaCompiler(File input) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        Assume.assumeNotNull(javac);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            javac.run(null, System.out, output, input.getAbsolutePath());
            if (output.size() > 0) {
                throw null;
            }
        } catch (Exception e) {
            fail("Encountered Exception during javac call:\n" + output.toString());
        }
    }


    @Test
    public void test() {
        File out;
        try {
            out = File.createTempFile("codeGenerationTest", ".java");
            out.deleteOnExit();
            MINIParser.printJavaSource(inputFile, out);
            checkJavaCompiler(out);
        } catch (IOException e) {
            fail("Failed to create temporary File");
        } catch (MINIException e) {
            fail("Failed to parse input File");
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
