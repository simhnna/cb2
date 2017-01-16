package main;

import frontend.MINIParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import testsuite.MINIException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class JavaByteCodeGeneratorTester {

    private final File inputFile;

    public JavaByteCodeGeneratorTester(File inputFile, String fileName) {
        this.inputFile = inputFile;
    }

    private static void checkByteCode(File classLocation) {
        try {
            ClassLoader loader = new URLClassLoader(new URL[]{new URL("file://" + classLocation.getPath() + "/")});

            for (File cls: classLocation.listFiles()) {
                if (cls.getName().endsWith(".class")) {
                    loader.loadClass(cls.getName().substring(0, cls.getName().length() - 6));
                }
            }
        } catch (MalformedURLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test() {
        File out;
        try {
            out = Files.createTempDirectory("byteCode").toFile();
            out.deleteOnExit();
            MINIParser.compile(inputFile, out);
            checkByteCode(out);
        } catch (IOException e) {
            fail("Failed to create temporary File");
        } catch (MINIException e) {
            fail("Failed to parse input File");
        } catch (Exception e) {
            e.printStackTrace();
            fail("File caused an exception");
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
