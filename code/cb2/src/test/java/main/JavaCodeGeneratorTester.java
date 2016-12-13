package main;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import frontend.MINIParser;
import testsuite.MINIException;

@RunWith(Parameterized.class)
public class JavaCodeGeneratorTester {
    
    private final File inputFile;
    
    public JavaCodeGeneratorTester(File inputFile, String fileName) {
        this.inputFile = inputFile;
    }

    private static void runProcess(String command) throws IOException, InterruptedException {
        StringBuilder bldr = new StringBuilder();
        Process pro = Runtime.getRuntime().exec(command);
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(pro.getErrorStream()));
        while ((line = in.readLine()) != null) {
            bldr.append(line);
        }
        pro.waitFor();
        assertEquals("", bldr.toString());
        assertEquals(0, pro.exitValue());
    }


    @Test
    public void test() {
        File out;
        try {
            out = File.createTempFile("codeGenerationTest", ".java");
            out.deleteOnExit();
            MINIParser.printJavaSource(inputFile, out);
            runProcess("javac " + out.getAbsolutePath());
        } catch (IOException e) {
            fail("Failed to create temporary File");
        } catch (MINIException e) {
            fail("Failed to parse input File");
        } catch (Exception e) {
            e.printStackTrace();
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
